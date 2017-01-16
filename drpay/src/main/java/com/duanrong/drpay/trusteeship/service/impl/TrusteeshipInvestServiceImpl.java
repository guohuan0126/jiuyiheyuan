package com.duanrong.drpay.trusteeship.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import util.DateUtil;
import util.Log;
import util.SmsHttpClient;
import base.error.ErrorCode;
import base.exception.AccountException;
import base.exception.TradeException;

import com.duanrong.drpay.business.account.service.UserAccountService;
import com.duanrong.drpay.business.invest.InvestConstants;
import com.duanrong.drpay.business.invest.model.Invest;
import com.duanrong.drpay.business.invest.service.InvestService;
import com.duanrong.drpay.business.loan.LoanConstants;
import com.duanrong.drpay.business.loan.model.Loan;
import com.duanrong.drpay.business.loan.service.LoanService;
import com.duanrong.drpay.business.user.model.User;
import com.duanrong.drpay.business.user.service.UserService;
import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.jsonservice.handler.TerminalEnum;
import com.duanrong.drpay.trusteeship.constants.TrusteeshipServer;
import com.duanrong.drpay.trusteeship.helper.model.BizType;
import com.duanrong.drpay.trusteeship.helper.model.Generator;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorPreTransactionJSON;
import com.duanrong.drpay.trusteeship.helper.model.NotifyURL;
import com.duanrong.drpay.trusteeship.helper.service.TrusteeshipService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipInvestService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipTransactionQueryService;
import com.duanrong.util.jedis.DRJedisMQ;
import com.duanrong.util.json.FastJsonUtil;

@Service
public class TrusteeshipInvestServiceImpl implements TrusteeshipInvestService {

	@Resource
	TrusteeshipService trusteeshipService;

	@Resource
	InvestService investService;

	@Resource
	TrusteeshipTransactionQueryService trusteeshipTransactionQueryService;
	
	@Resource
	UserService userService;

	@Resource
	UserAccountService userAccountService;

	@Resource
	Log log;

	@Resource
	SmsHttpClient smsHttpClient;
	
	@Resource
	LoanService loanService;
	
	/**
	 * 投资
	 */
	@Override
	public Generator createInvest(String userId, String loanId, double money,
			int redpacketId, int isAutoInvest, TerminalEnum terminalType,
			String investSource) throws TradeException, AccountException {
		// 本地创建投标记录,为等待支付
		Invest invest = new Invest();
		invest.setUserSource(investSource);
		invest.setMoney(money);
		invest.setInvestUserID(userId);
		invest.setLoanId(loanId);
		invest.setRedpacketId(redpacketId);
		invest.setIsAutoInvest(1 == isAutoInvest);
		investService.createInvest(invest);
		Loan loan = invest.getLoan();
		if (LoanConstants.LoanType.PROJECT.equals(loan.getStandardOrProject())) {
			return investProject(invest);
		} else {			
			return autoInvest(invest);
			/*if (invest.getIsAutoInvest()) {
				return autoInvest(invest);
			} else {
				return invest(invest, terminalType);
			}*/
		}
	}

	// 手动投标
	/*private Generator invest(Invest invest, TerminalEnum terminalType) {
		// 调用用户预处理，生成去银行存管的参数,返回前台跳转
		GeneratorPreTransactionJSON json = new GeneratorPreTransactionJSON();
		json.setRequestNo(invest.getId());
		json.setBizType(BizType.TENDER);
		json.setCallbackUrl(NotifyURL.INVE);
		json.setPlatformUserNo(invest.getInvestUserID());
		json.setAmount(invest.getMoney());
		json.setProjectNo(invest.getLoanId());
		json.setExpired(DateUtil.addMinute(invest.getTime(), 5));
		json.setRemark("手动投标");
		json.setSource(terminalType);
		return trusteeshipService.create(json,
				TrusteeshipServer.USER_PRE_TRANSACTION, BusinessEnum.invest);
	}*/

	// 自动投标
	private Generator autoInvest(Invest invest)
			throws TradeException, AccountException {
		GeneratorPreTransactionJSON json = new GeneratorPreTransactionJSON();
		json.setRequestNo(invest.getId());
		json.setBizType(BizType.TENDER);
		json.setPlatformUserNo(invest.getInvestUserID());
		json.setAmount(invest.getMoney());
		json.setProjectNo(invest.getLoanId());
		json.setRemark("自动投标");
		Generator generator = trusteeshipService.execute(json,
				TrusteeshipServer.USER_AUTO_PRE_TRANSACTION,
				GeneratorPreTransactionJSON.class, BusinessEnum.auto_invest);
		GeneratorPreTransactionJSON respData = (GeneratorPreTransactionJSON) generator
				.getRespData();
		investCallback(respData, invest.getId());
		return generator;
	}

	//投资理财计划
	private Generator investProject(Invest invest)
			throws TradeException, AccountException {
		GeneratorPreTransactionJSON json = new GeneratorPreTransactionJSON();		
		json.setRequestNo(invest.getId());
		json.setPlatformUserNo(invest.getInvestUserID());
		json.setAmount(invest.getMoney());
		json.setIntelProjectNo(invest.getLoanId());
		json.setRemark("投资理财计划");
		Generator generator = trusteeshipService.execute(json,
				TrusteeshipServer.PURCHASE_INTELLIGENT_PROJECT,
				GeneratorPreTransactionJSON.class, BusinessEnum.invest_project);
		GeneratorPreTransactionJSON respData = (GeneratorPreTransactionJSON) generator
				.getRespData();
		investCallback(respData, invest.getId());
		return generator;
	}

	@Override
	public Generator investAgain(String id, TerminalEnum terminalType)
			throws TradeException, AccountException {
		// 本地创建投标记录,为等待支付
		Invest invest = investService.get(id);
		// TODO超过五分钟，返回提示

		Date expiredTime = DateUtil.addMinute(invest.getTime(), 5);// 投资时间
		Date nowDate = new Date();
		Long timeInMillis = DateUtil.DayDifference(expiredTime, nowDate);
		if ("流标".equals(invest.getStatus()) || timeInMillis <= 0) {
			throw new TradeException(ErrorCode.InvestOutRepayTime);
		} else if ("投标成功".equals(invest.getStatus())) {
			throw new TradeException(ErrorCode.InvestSuccessNoAgainRepay);
		}
		// 调用用户预处理，生成去银行存管的参数,返回前台跳转
		GeneratorPreTransactionJSON json = new GeneratorPreTransactionJSON();
		json.setRequestNo(id);
		json.setBizType(BizType.TENDER);
		json.setCallbackUrl(NotifyURL.INVE);
		json.setPlatformUserNo(invest.getInvestUserID());
		json.setAmount(invest.getMoney());
		json.setProjectNo(invest.getLoanId());
//		json.setExpired(DateUtil.addMinute(invest.getTime(), 5));
		json.setRemark("用户投资");
		json.setSource(terminalType);
		return trusteeshipService.create(json,
				TrusteeshipServer.USER_PRE_TRANSACTION);
	}

	@Override
	public void investCallback(GeneratorJSON respData,
			String requestNo) throws TradeException, AccountException {
		String code = respData.getCode();
		Invest invest = investService.get(requestNo);
		if (invest == null) {
			log.errLog("投资回调失败", "未找到本地投资记录" + requestNo + "，存管返回数据"
					+ respData.toString());
			throw new TradeException(ErrorCode.InvestNotFind);
		}
		if (!StringUtils.equals(invest.getStatus(),
				InvestConstants.InvestStatus.WAIT_AFFIRM)) {
			log.errLog("投资失败", "状态不为等待确认" + invest.toString());
			throw new TradeException(ErrorCode.InvestStatusError);
		}
		/********** 投资成功（满标后再投资的用户会投资失败） **********/
		if (StringUtils.equals("0", code)) {
			// 修改投资状态
			invest.setStatus(InvestConstants.InvestStatus.BID_SUCCESS);
			investService.update(invest);
			String userId = invest.getInvestUserID();
			// 修改用户投资金额，发送短信站内信
			try {
				Loan loan = invest.getLoan();			
				String operationType = loan.getOperationType();	
				Integer day2 = loan.getDay();		
				if (StringUtils.equals(loan.getRepayType(),
						LoanConstants.RepayType.RFCL)
						&& StringUtils.equals("是", loan.getBeforeRepay())
						&& "天".equals(operationType)) {
					Integer symbol = loan.getSymbol();
					day2 = symbol != null && symbol > 0 ? day2 + symbol
							: day2;
				}
				String loanDeadline = StringUtils.equals(
						loan.getOperationType(), "月") ? loan.getDeadline()
								+ "个月" : day2 + "天";
				// 发送短信
				User user = userService.get(userId);
				smsHttpClient.sendSms(user.getMobileNumber(),
						loan.getName() + "," + loanDeadline+","+ invest.getMoney() + "元"
								, "invest");
				user.setInvestMoneyTotal(user.getInvestMoneyTotal()
						+ invest.getMoney());
				userService.update(user);
			} catch (Exception e) {
				log.errLog("更新用户投资总额失败",
						"userId:" + invest.getInvestUserID()
						+ "investMoney: " + invest.getMoney()
						+ ExceptionUtils.getMessage(e));
			}
			/************ 放入队列，用户第三方推送 ****************/
			try {
				Date nowTime = new Date();
				Map<String, Object> map = new HashMap<>();
				map.put("investId", invest.getId());
				map.put("loanId", invest.getLoanId());
				map.put("pushTime", nowTime);		
				DRJedisMQ.product("pushinvest", FastJsonUtil.objToJson(map));
				DRJedisMQ.product("activity_invest", invest.getId());
				Date endTime = new Date();
				log.infoLog("队列插入时间",
						"毫秒：" + (endTime.getTime() - nowTime.getTime()));
			} catch (Exception e) {
				log.errLog("投资放入队列失败", invest.toString() + "==" + e);
			}
			
			// 删除掉投资时创建的Quartz调度任务
			/*JobKey jobKey = JobKey
					.jobKey(invest.getId(),
							ScheduleConstants.JobGroup.CHECK_INVEST_OVER_EXPECT_TIME);
			scheduler.deleteJob(jobKey);*/
		} else {
			investService.failInvest(invest);
			log.errLog("投标失败", "investId:"+requestNo+"自动投标失败");
			throw new TradeException(ErrorCode.AutoInvestFail);
		}
	}

	@Override
	public void investCompensate(String requestNo) throws TradeException, AccountException {
		Invest invest = investService.get(requestNo);
		if (invest == null) {	
			throw new TradeException(ErrorCode.InvestNotFind);
		}
		if (!StringUtils.equals(invest.getStatus(),
				InvestConstants.InvestStatus.WAIT_AFFIRM)) {
			throw new TradeException(ErrorCode.InvestStatusError);
		}
		Loan loan = loanService.get(invest.getLoanId());
		if(loan == null){
			throw new TradeException(ErrorCode.LoanNotFind);
		}
		GeneratorJSON json = null;
		//查询批量投标订单
		if(LoanConstants.LoanType.PROJECT.equals(loan.getStandardOrProject())){
			json = trusteeshipTransactionQueryService.queryProjectOrder(requestNo);
		}else{
			//查询散表投标订单
			try {
				json = trusteeshipTransactionQueryService
						.queryTransaction(requestNo, BusinessEnum.invest).getRespData();
			} catch (AccountException e) {}
		}
		this.investCallback(json, requestNo);
	}

}