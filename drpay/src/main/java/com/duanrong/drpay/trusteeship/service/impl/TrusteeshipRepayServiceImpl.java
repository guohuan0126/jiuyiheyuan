package com.duanrong.drpay.trusteeship.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import util.ArithUtil;
import util.DateUtil;
import util.Log;
import base.exception.AccountException;
import base.exception.TradeException;
import base.exception.UserAccountException;

import com.alibaba.druid.util.StringUtils;
import com.duanrong.drpay.business.account.service.UserAccountService;
import com.duanrong.drpay.business.loan.LoanConstants;
import com.duanrong.drpay.business.loan.model.Loan;
import com.duanrong.drpay.business.repay.RepayHelper;
import com.duanrong.drpay.business.repay.model.Repay;
import com.duanrong.drpay.business.repay.model.RepayInvest;
import com.duanrong.drpay.business.repay.model.RepayInvestSubLoan;
import com.duanrong.drpay.business.repay.model.RepaySubLoan;
import com.duanrong.drpay.business.repay.service.RepayService;
import com.duanrong.drpay.business.repay.service.RepaySubLoanService;
import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.config.IdUtil;
import com.duanrong.drpay.config.ToType;
import com.duanrong.drpay.jsonservice.handler.TerminalEnum;
import com.duanrong.drpay.trusteeship.constants.TrusteeshipServer;
import com.duanrong.drpay.trusteeship.helper.model.BizType;
import com.duanrong.drpay.trusteeship.helper.model.Generator;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorAsynDetailJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorDetailJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorPreTransactionJSON;
import com.duanrong.drpay.trusteeship.helper.service.TrusteeshipService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipRepayService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipTransactionQueryService;
import com.duanrong.util.jedis.DRJedisDLock;

@Service
public class TrusteeshipRepayServiceImpl implements TrusteeshipRepayService {

	@Resource
	TrusteeshipService trusteeshipService;

	@Resource
	TrusteeshipTransactionQueryService trusteeshipTransactionQueryService;
	
	@Resource
	RepaySubLoanService repaySubLoanService;
	
	@Resource
	UserAccountService userAccountService;
	
	@Resource
	RepayService repayService;

	@Resource
	Log log;
	
	/**
	 * 放款锁
	 */
	private static final String REPAY_LOCK = "repay";
	
	/**
	 * 还款预处理
	 * 将还款数据置为sended
	 * 等待调度执行
	 * 
	 */
	@Override
	public String createSendedRepay(String id,int beforeRepay, TerminalEnum terminalType) 
			throws UserAccountException, TradeException {
		if (beforeRepay==1) {
			Repay repay = new Repay();
			repay.setLoanId(id);
			repay.setStatus("还款中");
			List<Repay> repays = repayService.getByCondition(repay);
			if(CollectionUtils.isEmpty(repays)){
				throw  new TradeException("提前还款异常");
			}
			id = repays.get(0).getId();
		}
		/*********************** 保存还款表 ***********************/
		// 查询还款
		Repay repay =repayService.get(id);
		Loan loan = repay.getLoan();
		if(StringUtils.isEmpty(loan.getStandardOrProject())){
			throw new TradeException("无法判断项目是散标还是理财计划");
		}
		//	判断是否已预处理
		if(StringUtils.isEmpty(repay.getHandleStatus())){
			repay.setIsBeforeRepay(beforeRepay);
			//生成还款明细
			repayService.createRepayInvest(repay);
		}
		return "还款正在处理";
	}
	
	/**
	 * 还款业务的入口（包括散标和投资包）
	 * @throws SchedulerException 
	 * @throws AccountException 
	 * @throws TradeException 
	 * 
	 */
	@Override
	public void handleRepay(String repayId) throws TradeException, AccountException, SchedulerException {
		if (DRJedisDLock.getDLock(REPAY_LOCK, repayId, 180)) {
			try{
				// 查询还款
				Repay repay =repayService.get(repayId);
				if(StringUtils.isEmpty(repay.getHandleStatus())||!"还款中".equals(repay.getStatus())){
					log.errLog("还款错误", "还款状态不正确,repayId:"+repayId+",状态"+repay.getStatus()+","+repay.getHandleStatus());
					throw new TradeException("还款状态不正确");
				}
				Loan loan = repay.getLoan();
				//判断标的类型
				if("standard".equals(loan.getStandardOrProject())){
					handleStandard(repay, loan);
				}else if("project".equals(loan.getStandardOrProject())){
					//通过repayId查询出repaySubloan
					List<RepaySubLoan> list = repaySubLoanService.getRepaySubLoans(repayId);
					boolean flag = true;
					for(RepaySubLoan repaySubLoan:list){
						//如果操作时间超过两小时
						if(new Date().getTime()>DateUtil.addHour(repaySubLoan.getOperationTime(), 2).getTime()){
							compensationProject(repaySubLoan.getRepaySubLoanId());
						}
						//如果未执行状态，冻结该项目资金
						if("sended".equals(repaySubLoan.getHandleStatus())){
							flag = false;
							boolean perFlag = preTransaction(repaySubLoan.getRepaySubLoanId(), repaySubLoan.getUserId(), 
									repaySubLoan.getCorpus()+repaySubLoan.getInterest(), repaySubLoan.getSubloanId(), BizType.IP_REPAYMENT);
							if(perFlag){
								repaySubLoan.setHandleStatus("freeze");
								repaySubLoanService.update(repaySubLoan);
							}
						}
						
						if("freeze".equals(repaySubLoan.getHandleStatus())){
							flag = false;
							boolean proFlag = processRepay(repaySubLoan);
							if(proFlag){
								repaySubLoan.setHandleStatus("processing");
								repaySubLoanService.update(repaySubLoan);
							}
						}
						if("processing".equals(repaySubLoan.getHandleStatus())){
							//再次确认过程
							List<RepayInvestSubLoan> listd = repaySubLoanService.getRepayInvestSubLoans(repaySubLoan.getRepaySubLoanId());
							//校验下面是否都成功
							if(listd.isEmpty()||listd.size()==0){
								repaySubLoan.setHandleStatus("success");
								repaySubLoan.setStatus("完成");
								repaySubLoanService.update(repaySubLoan);
							}else{
								flag = false;
							}
						}
					}
					
					if(flag){
						try {
							List<RepayInvest> repayInvests = repayService.getRepayInvests(repay.getId());
							if (CollectionUtils.isEmpty(repayInvests)) {
								log.errLog("还款确认", "本地未查询到repayInvest，流水号："+repay.getId());
							}else{
								repayService.normalRepay(repay,repayInvests);
							}
						} catch (UserAccountException e) {
							e.printStackTrace();
						}
					}
				}
			}catch (Exception e) {
				log.errLog("还款失败", e);
				throw e;
			} finally {
				DRJedisDLock.releaseDLock(REPAY_LOCK, repayId);
			}
		}
	}
	
	private void handleStandard(Repay repay,Loan loan) throws TradeException, AccountException{
		//判断标的类型
		BizType bizType = loan.getStock()==0?BizType.REPAYMENT:BizType.STOCK_REPAYMENT;
		if("sended".equals(repay.getHandleStatus())){
			Generator generator = trusteeshipTransactionQueryService.queryTransaction("PRE"+repay.getId(), BusinessEnum.repay);
			GeneratorJSON data = generator.getRespData();
			List<GeneratorDetailJSON> details = data.getRecords();
			if ("0".equals(data.getCode()) && !CollectionUtils.isEmpty(details)) {
				GeneratorDetailJSON detail = details.get(0);
				if("FREEZED".equals(detail.getStatus())){
					confirmRepayStandard(repay);
					return;
				}else if("FAIL".equals(detail.getStatus())){
					String newId = RepayHelper.generateRepayId(loan.getId(), repay.getPeriod());
					repay.setOperator(newId);
					repay.setHandleStatus("freezeFail");
					repay.setStatus("失败");
					repayService.updateRepay(repay);
					//新增一条还款记录
					repay.setId(newId);
					repay.setHandleStatus("sended");
					repayService.insertRepay(repay);
				}
			}
			boolean perFlag = preTransaction("PRE"+repay.getId(), repay.getUserId(), 
					repay.getCorpus()+repay.getInterest(), loan.getId(), bizType);
			if(perFlag){
				repay.setHandleStatus("freeze");
				repayService.updateRepay(repay);
			}
		}
		if("freeze".equals(repay.getHandleStatus())){
			Generator generator =trusteeshipTransactionQueryService.queryTransaction(repay.getId(), BusinessEnum.repay_confirm);
			GeneratorJSON data = generator.getRespData();
			List<GeneratorDetailJSON> details = data.getRecords();
			if ("0".equals(data.getCode()) && !CollectionUtils.isEmpty(details)) {
				GeneratorDetailJSON detail = details.get(0);
				String status = detail.getStatus();
				if("SUCCESS".equals(status)){
					List<RepayInvest> list = repayService.getRepayInvests(repay.getId());
					repayService.normalRepay(repay,list);
				}else if("FAIL".equals(status)){
					String newId = RepayHelper.generateRepayId(loan.getId(), repay.getPeriod());
					repay.setOperator(newId);
					repay.setHandleStatus("confirmFail");
					repay.setStatus("失败");
					repayService.updateRepay(repay);
					//新增一条还款记录
					repay.setId(newId);
					repay.setHandleStatus("freeze");
					repayService.insertRepay(repay);
				}
			}else{
				this.confirmRepayStandard(repay);
			}
		}
		
	}
	
	private void confirmRepayStandard(Repay repay) throws TradeException, AccountException{
		String requestNo = repay.getId();
			
				if(!LoanConstants.LoanStatus.REPAYING.equals(repay.getStatus())){
					log.errLog("还款确认失败", "还款状态不处于还款中，还款ID："+repay.getId());
					return;
				}
				List<GeneratorDetailJSON> details = new ArrayList<GeneratorDetailJSON>();
				//通过repayId 查询repay_invest;
				List<RepayInvest> list = repayService.getRepayInvests(repay.getId());
				if (CollectionUtils.isEmpty(list)) {
					log.errLog("还款确认", "本地未查询到repayInvest，流水号："+repay.getId());
					return ;
				}else{
					for (RepayInvest repayInvest : list) {
						// 如果status = 0，正常还款，如果status = -1,不还款。
						if(-1==repayInvest.getStatus())continue;
						GeneratorDetailJSON detail = new GeneratorDetailJSON();
						detail.setPlatformUserNo(repayInvest.getInvestUserId());
						detail.setAmount(repayInvest.getCorpus()+repayInvest.getInterest());
						if(repayInvest.getInterest()>0){
							detail.setIncome(repayInvest.getInterest());
						}
						details.add(detail);
					}
				}
				GeneratorJSON json = new GeneratorJSON();
				json.setRequestNo(repay.getId());
				json.setPreTransactionNo("PRE"+repay.getId());
				json.setProjectNo(repay.getLoanId());
				json.setDetails(details);
				Generator generator = trusteeshipService.execute(json, TrusteeshipServer.CONFIRM_REPAYMENT, GeneratorJSON.class, BusinessEnum.repay_confirm);
				GeneratorJSON data = generator.getRespData();
				if("0".equals(data.getCode())){
					//还款确认成功后，本地还款操作是否加锁？
					repayService.normalRepay(repay,list);
				}else{
					//单笔业务查询一次查看是否正常
					Generator ge =trusteeshipTransactionQueryService.queryTransaction(repay.getId(), BusinessEnum.repay_confirm);
					GeneratorJSON da = ge.getRespData();
					List<GeneratorDetailJSON> de = da.getRecords();
					if ("0".equals(da.getCode()) && !CollectionUtils.isEmpty(de)) {
						GeneratorDetailJSON detail = de.get(0);
						String status = detail.getStatus();
						if("SUCCESS".equals(status)){
							repayService.normalRepay(repay,list);
						}
					}
					log.errLog("还款失败", "还款流水号："+requestNo+"返回参数："+data.toJSON());
				}
			
	}
	
	
	/**
	 * 授权预处理
	 * @return	预处理是否成功
	 */
	private boolean preTransaction(String requestNo,String userId,double amount,String projectNo,BizType bizType) throws TradeException, AccountException{
		GeneratorPreTransactionJSON json = new GeneratorPreTransactionJSON();
		json.setRequestNo(requestNo);
		json.setPlatformUserNo(userId);
		json.setBizType(bizType);
		json.setAmount(ArithUtil.round(amount,2));
		json.setProjectNo(projectNo);
		json.setRemark("借款人还款");
		Generator generator = trusteeshipService.execute(json, 
				TrusteeshipServer.USER_AUTO_PRE_TRANSACTION, GeneratorJSON.class, BusinessEnum.repay);
		GeneratorJSON data = generator.getRespData();
		if("0".equals(data.getCode())){
			return true;
		}else{
			//单笔业务查询一次查看是否正常
			Generator ge =trusteeshipTransactionQueryService.queryTransaction(requestNo, BusinessEnum.repay);
			GeneratorJSON da = ge.getRespData();
			List<GeneratorDetailJSON> de = da.getRecords();
			if ("0".equals(da.getCode()) && !CollectionUtils.isEmpty(de)) {
				GeneratorDetailJSON detail = de.get(0);
				String status = detail.getStatus();
				if("SUCCESS".equals(status)){
					return true;
				}
			}
		}
		log.errLog("预处理失败", data.toJSON(),1);
		return false;
	}

	/**
	 * 理财计划还款
	 * 外层流水号repayId
	 * 内层每个标的流水号为Repay_SubLoan中ID
	 */
	private boolean processRepay(RepaySubLoan repaySubLoan){
		//1所有子标的冻结完成，开始总体调用批量还款
		List<GeneratorDetailJSON> details = new ArrayList<GeneratorDetailJSON>();
		//通过repayId 查询repay_invest; 查询状态都为0的状态
		List<RepayInvestSubLoan> list = repaySubLoanService.getRepayInvestSubLoans(repaySubLoan.getRepaySubLoanId());
		if (CollectionUtils.isEmpty(list)) {
			log.errLog("批量还款", "本地未查询到RepayInvestSubLoan，流水号："+repaySubLoan.getRepaySubLoanId());
			return true;
		}else{
			for (RepayInvestSubLoan repayInvestSubLoan : list) {
				GeneratorDetailJSON detail = new GeneratorDetailJSON();
				detail.setRepaymentRequestNo(repayInvestSubLoan.getRepayInvestSubLoanId());//还款请求流水号
				detail.setPreTransactionNo(repaySubLoan.getRepaySubLoanId());//还款预处理流水号
				detail.setPlatformUserNo(repayInvestSubLoan.getUserId());
				detail.setAmount(ArithUtil.round(repayInvestSubLoan.getCorpus()+repayInvestSubLoan.getInterest(),2));
				detail.setIncome(ArithUtil.round(repayInvestSubLoan.getInterest(),2));
				detail.setProjectNo(repaySubLoan.getSubloanId());//子标的号
				detail.setIntelRequestNo(repayInvestSubLoan.getInvestId());//批量投标请求流水号
				details.add(detail);	
			}
		}
		GeneratorJSON json = new GeneratorJSON();
		json.setRequestNo(IdUtil.generateId(ToType.PLRE));
		json.setDetails(details);
		Generator generator = trusteeshipService.execute(json, TrusteeshipServer.INTELLIGENT_PROJECT_REPAYMENT, 
				GeneratorJSON.class, BusinessEnum.repay_confirm);
		GeneratorJSON data = generator.getRespData();
		if("0".equals(data.getCode())){
			return true;
		}else{
			log.errLog("还款失败", "还款流水号："+repaySubLoan.getRepaySubLoanId()+"返回参数："+data.toJSON());
			return false;
		}
		
	}
	

	/**
	 * 补偿机制
	 * @param repaySubloanId
	 * @throws TradeException
	 * @throws AccountException
	 */
	private void compensationProject(String repaySubloanId) throws TradeException, AccountException{
		List<RepayInvestSubLoan> list = repaySubLoanService.getRepayInvestSubLoans(repaySubloanId);
		if (CollectionUtils.isEmpty(list)) {
			return ;
		}
		for (RepayInvestSubLoan repayInvestSubLoan : list) {
			Generator ge =trusteeshipTransactionQueryService.queryTransaction(repaySubloanId, BusinessEnum.repay_confirm);
			GeneratorJSON da = ge.getRespData();
			List<GeneratorDetailJSON> de = da.getRecords();
			if ("0".equals(da.getCode()) && !CollectionUtils.isEmpty(de)) {
				GeneratorDetailJSON detail = de.get(0);
				String status = detail.getStatus();
				GeneratorAsynDetailJSON generatorAsynDetail = new GeneratorAsynDetailJSON();
				generatorAsynDetail.setAsyncRequestNo(repayInvestSubLoan.getRepayInvestSubLoanId());
				generatorAsynDetail.setStatus(status);
				repayCallback(generatorAsynDetail);	
			}
		}
	}
	/**
	 * 用于理财计划
	 * 主动异步通知
	 * 根据流水号查询子标还款明细
	 * 根据存管返回结果出来本地业务
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void repayCallback(GeneratorAsynDetailJSON detail){
		String requestNo = detail.getAsyncRequestNo();
		//查询RepayInvestSubLoan
		RepayInvestSubLoan repayInvestSubLoan = repaySubLoanService.getRepayInvestSubLoan(requestNo);
		if(repayInvestSubLoan==null||repayInvestSubLoan.getStatus()==1){
			log.errLog("批量还款", "主动回调，未查询到本地RepayInvestSubLoan数据，或状态已成功，id："+requestNo,1);
			return;
		}
		if("SUCCESS".equals(detail.getStatus())){
			repayInvestSubLoan.setStatus(1);
			repaySubLoanService.updateRepayInvestSubLoan(repayInvestSubLoan);
		}else if("FAIL".equals(detail.getStatus())){
			//失败修改子标计划状态，用于再次调度
			RepaySubLoan repaySubLoan = repaySubLoanService.get(repayInvestSubLoan.getRepaySubLoanId());
			repaySubLoan.setHandleStatus("freeze");
			repaySubLoanService.update(repaySubLoan);
			//修改原有数据为失败
			String newId = IdUtil.randomUUID();
			repayInvestSubLoan.setStatus(-1);
			repayInvestSubLoan.setOperator(newId);
			repayInvestSubLoan.setFailReason("code="+detail.getFailCode()+",msg="+detail.getFailReason());
			repaySubLoanService.updateRepayInvestSubLoan(repayInvestSubLoan);
			//失败后生成新的请求记录
			repayInvestSubLoan.setRepayInvestSubLoanId(newId);
			repayInvestSubLoan.setStatus(0);
			repaySubLoanService.insertRepayInvestSubLoan(repayInvestSubLoan);
		}
	}

}
