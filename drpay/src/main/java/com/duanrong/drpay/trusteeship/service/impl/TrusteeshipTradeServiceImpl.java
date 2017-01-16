package com.duanrong.drpay.trusteeship.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import util.ArithUtil;
import util.Log;
import util.SmsHttpClient;
import base.error.ErrorCode;
import base.exception.AccountException;
import base.exception.LoanException;
import base.exception.TradeException;
import base.exception.UserAccountException;
import base.exception.UserInfoException;

import com.duanrong.drpay.business.account.PlatformAccountEnum;
import com.duanrong.drpay.business.account.service.PlatformAccountService;
import com.duanrong.drpay.business.account.service.UserAccountService;
import com.duanrong.drpay.business.invest.InvestConstants;
import com.duanrong.drpay.business.invest.InvestConstants.InvestStatus;
import com.duanrong.drpay.business.invest.model.Invest;
import com.duanrong.drpay.business.invest.model.InvestSubLoan;
import com.duanrong.drpay.business.invest.service.InvestService;
import com.duanrong.drpay.business.loan.LoanConstants;
import com.duanrong.drpay.business.loan.model.Loan;
import com.duanrong.drpay.business.loan.model.SubLoan;
import com.duanrong.drpay.business.loan.service.LoanService;
import com.duanrong.drpay.business.loan.service.SubloanForkService;
import com.duanrong.drpay.business.repay.service.RepayService;
import com.duanrong.drpay.business.repay.service.RepaySubLoanService;
import com.duanrong.drpay.business.user.model.User;
import com.duanrong.drpay.business.user.service.UserService;
import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.config.IdUtil;
import com.duanrong.drpay.config.ToType;
import com.duanrong.drpay.trusteeship.constants.TrusteeshipServer;
import com.duanrong.drpay.trusteeship.helper.model.Generator;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorAsynDetailJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorDetailJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorTradeJSON;
import com.duanrong.drpay.trusteeship.helper.service.TrusteeshipService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipLoanService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipTradeService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipTransactionQueryService;
import com.duanrong.util.jedis.DRJedisDLock;

@Service
public class TrusteeshipTradeServiceImpl implements TrusteeshipTradeService {

	@Resource
	InvestService investService;

	@Resource
	LoanService loanService;

	@Resource
	RepayService repayService;

	@Resource
	RepaySubLoanService repaySubLoanService;

	@Resource
	UserAccountService userAccountService;

	@Resource
	PlatformAccountService platformAccountService;

	@Resource
	TrusteeshipService trusteeshipService;

	@Resource
	TrusteeshipLoanService trusteeshipLoanService;

	@Resource
	SmsHttpClient smsHttpClient;

	@Resource
	UserService userService;
	
	@Resource
	TrusteeshipTransactionQueryService trusteeshipTransactionQueryService;

	@Resource
	SubloanForkService subloanForkService;

	@Resource
	Log log;

	/**
	 * 放款锁
	 */
	private static final String GIVE_MONEY_TO_BORROWER_LOCK = "giveMoneyToBorrower";

	/**
	 * 批量放款异步通知
	 */
	private static final String GIVE_MONEY_TO_BORROWER_LOCAL_LOCK = "giveMoneyToBorrowerLocal";

	
	

	@Override
	public void giveMoneyToBorrower(String loanId) throws TradeException {
		// 放款锁
		if (DRJedisDLock.getDLock(GIVE_MONEY_TO_BORROWER_LOCK, loanId, 180)) {
			try {
				// 放款预处理
				Loan loan = loanService.prepare(loanId);
				// 理财计划放款
				if (LoanConstants.LoanType.STANDARD.equals(loan.getStandardOrProject())) {
					// 散标放款
					this.giveMoneyToBorrower(loan);
				} else {
					this.giveProjectMoneyToBorrower(loan);
				}
			} finally {
				DRJedisDLock.releaseDLock(GIVE_MONEY_TO_BORROWER_LOCK, loanId);
			}
		}

	}

	/**
	 * 理财包放款
	 * 
	 * @param loanId
	 * @param invests
	 */
	private void giveProjectMoneyToBorrower(Loan loan) {
		List<Invest> invests = loan.getInvests();
		try {
			/**
			 * 理财包匹配投资金额
			 */
			subloanForkService.fork(loan);
			// 批量放款
			GeneratorTradeJSON json = new GeneratorTradeJSON();
			json.setRequestNo(IdUtil.generateId(ToType.GMTB));
			List<GeneratorDetailJSON> details = new ArrayList<>();
			for (Invest invest : invests) {
				double sumMoney = investService.getInvestSubloanMoneyByInvestId(invest.getId());
				if (ArithUtil.round(invest.getMoney() - sumMoney, 2) != 0) {
					log.errLog("放款失败", "理财包放款失败，理财包投资金额不等于子标匹配金额之和, loanId: + " + invest.getLoanId() + ", investId: "
							+ invest.getId());
					throw new TradeException(ErrorCode.MoneyRaiseNoTotal);
				}
				List<InvestSubLoan> investSubloans = investService.getInvestSubloanByInvestId(invest.getId());
				for (InvestSubLoan investSubLoan : investSubloans) {
					GeneratorDetailJSON detail = new GeneratorDetailJSON();
					detail.setLoanRequestNo(investSubLoan.getInvestSubloanId());
					detail.setAmount(investSubLoan.getMoney());
					detail.setProjectNo(investSubLoan.getSubloanId());
					detail.setIntelRequestNo(investSubLoan.getInvestId());
					details.add(detail);
				}
			}
			json.setDetails(details);
			Generator generator = trusteeshipService.execute(json, TrusteeshipServer.INTELLIGENT_PROJECT_LOAN,
					GeneratorJSON.class, BusinessEnum.give_money_to_borrower);
			if (generator.getRespData() != null && generator.getRespData().getCode().equals("0")) {
				// 放款受理成功
				// logger.debug(generator.getRespData());
			}
		} catch (Exception e) {
			log.errLog("放款失败", "理财计划自己分配失败loanId: " + loan.getId(), 1);
		}
	}

	
	/**
	 * 检查子标是否已推送至存管，未推送直接进行推送
	 * @param loan
	 * @throws LoanException 
	 * @throws UserInfoException 
	 * @throws UserAccountException 
	 */
//	private void checkSubLoan(Loan loan) throws UserAccountException, UserInfoException, LoanException{
//		List<SubLoan> subloans = new ArrayList<>();
//		if(LoanConstants.Type.VEHICLE.equals(loan.getLoanType())){
//			subloans = loanService.getVehicleByGroupCondition(loan.getId(), 0);
//		}else if(LoanConstants.Type.AGRICULTURE.equals(loan.getLoanType())){
//			subloans = loanService.getAgricultureByGroupCondition(loan.getId(), 0);
//		}
//		boolean isCheck = true;
//		if(!subloans.isEmpty()){
//			for(SubLoan subloan : subloans){
//				LoanParameter loanParam = new LoanParameter();
//				loanParam.setUserId(loan.getBorrowMoneyUserID());
//				loanParam.setLoanId(subloan.getSubloanId());
//				loanParam.setLoanName(subloan.getSubloanName());
//				loanParam.setRate(loan.getRate());
//				loanParam.setRepayType(loan.getRepayType());
//				GeneratorJSON json = trusteeshipLoanService.createLoan(loan.getBorrowMoneyUserID(), loanParam);
//				if(json != null && !"0".equals(json.getCode())){
//					isCheck = false;
//				}else{
//					
//				}
//				
//			}
//			
//			
//		}
//		
//	}
	
	
	@Override
	public void giveMoneyToBorrowerCallback(GeneratorAsynDetailJSON generatorAsynDetailJSON) {
		this.giveMoneyToBorrowerLocal(generatorAsynDetailJSON.getAsyncRequestNo(), generatorAsynDetailJSON.getStatus());	
	}

	
	@Override
	public void giveMoneyToBorrowerCompensate(String investSubloanId) throws TradeException, AccountException {
		InvestSubLoan investSubloan = investService.getInvestSubloan(investSubloanId); 
		if(InvestConstants.InvestStatus.BID_SUCCESS.equals(investSubloan.getStatus())){
			GeneratorJSON json = trusteeshipTransactionQueryService.queryTransaction(investSubloanId, BusinessEnum.invest).getRespData();			
			if(json != null && ("0").equals(json.getCode())){
				List<GeneratorDetailJSON> details = json.getDetails();
				for(GeneratorDetailJSON detail : details){
					this.giveMoneyToBorrowerLocal(investSubloanId, detail.getStatus());
				}
			}else{
				log.errLog("批量放款订单补偿", "investSubloanId" + investSubloanId + ", code: " + json.getCode()+", desc: " + json.getDescription(), 1);
			}
		}
	}

	/**
	 * 本地批量放款
	 * @param investSubloanId
	 * @param status
	 */
	private void giveMoneyToBorrowerLocal(String investSubloanId, String status){
		// 放款锁
		if (DRJedisDLock.getDLock(GIVE_MONEY_TO_BORROWER_LOCAL_LOCK, investSubloanId, 180)) {
			try {
				InvestSubLoan investSubloan = investService.getInvestSubloan(investSubloanId);
				if (status.equals("SUCCESS")) {
					investSubloan.setStatus(InvestStatus.REPAYING);
					investService.updateInvestSubloan(investSubloan);
				} else if (status.equals("FAIL")) {
					investSubloan.setStatus(InvestStatus.CANCEL);
					investService.updateInvestSubloan(investSubloan);
					// 重置理财计划投资关系流水号
					investSubloan.setInvestSubloanId(IdUtil.generateId(ToType.IVSB));
					investSubloan.setStatus(InvestStatus.BID_SUCCESS);
					investService.insertInvestSubloan(investSubloan);
				} else if(status.equals("ERROR")){
					log.errLog("批量放款", "investSubloanId： " + investSubloanId + ", 订单异常， 存管通返回状态 error， 请及时处理", 1);
				}
				List<InvestSubLoan> investSubloans = investService
						.getInvestSubloanByInvestId(investSubloan.getInvestId());
				if (investSubloans.isEmpty()) {
					Invest invest = investService.get(investSubloan.getInvestId());
					if (InvestConstants.InvestStatus.BID_SUCCESS.equals(invest.getStatus())) {
						// 理财计划单笔放款
						loanService.giveMoneyToBorrower(invest);
					}
					String loanId = invest.getLoanId();
					Loan loan = loanService.get(loanId);
					if (investService.getInvestSeccessByLoanId(loanId) == 0) {
						List<SubLoan> subloans = new ArrayList<>();
						if (LoanConstants.LoanType.PROJECT.equals(loan.getStandardOrProject())
								&& LoanConstants.Type.VEHICLE.equals(loan.getLoanType())) {
							subloans = loanService.getVehicleByGroupCondition(loanId, 1);
						} else if (LoanConstants.LoanType.PROJECT.equals(loan.getStandardOrProject())
								&& LoanConstants.Type.AGRICULTURE.equals(loan.getLoanType())) {
							subloans = loanService.getAgricultureByGroupCondition(loanId, 1);
						}
						// 生成子标还款计划
						for (SubLoan subloan : subloans) {
							try {
								Loan l = new Loan();
								l.setId(subloan.getSubloanId());
								l.setStatus(subloan.getSubloanStatus());
								// 跟新存管通项目状态
								if (LoanConstants.LoanStatus.RAISING.equals(subloan.getSubloanStatus())) {
									GeneratorJSON json = trusteeshipLoanService.modifyLoan(l, LoanConstants.LoanStatus.REPAYING);
									if(json != null && "0".equals(json.getCode())){
										SubLoan s = new SubLoan();
										s.setSubloanStatus(LoanConstants.LoanStatus.REPAYING);
										s.setLoanId(loanId);
										s.setSubloanId(subloan.getSubloanId());
										if (LoanConstants.Type.VEHICLE.equals(loan.getLoanType())) {
											loanService.updateVehicle(s);
										} else if (LoanConstants.Type.AGRICULTURE.equals(loan.getLoanType())) {
											loanService.updateAgricultureForkLoans(s);
										}
									}
								}
								if (repaySubLoanService.getRepaySubLoansBySubloanId(subloan.getSubloanId()).isEmpty()) {
									subloan.setBeforeRepay(loan.getBeforeRepay());
									subloan.setOperationType(loan.getOperationType());
									subloan.setBorrowMoneyUserID(loan.getBorrowMoneyUserID());
									subloan.setDay(loan.getDay());
									subloan.setDeadline(loan.getDeadline());
									subloan.setGiveMoneyTime(loan.getGiveMoneyTime());
									subloan.setRepayType(loan.getRepayType());
									subloan.setRate(loan.getRate());
									List<InvestSubLoan> investSubloans2 = investService
											.getInvestSubLoans(subloan.getSubloanId());
									subloan.setInvestSubloans(investSubloans2);
									// 生成子标还款计划
									repayService.saveSubLoanRepay(subloan);
								}
							} catch (Exception e) {
								log.errLog("批量放款异步处理失败", e, 1);
							}
						}
						// 项目是等待复核，执行放款
						if (LoanConstants.LoanStatus.RECHECK.equals(loan.getStatus())) {
							int period = 1;
							if (LoanConstants.RepayType.DQHBFX.equals(loan.getRepayType())) {
								period = 1;
							} else if ("月".equals(loan.getOperationType())
									&& !LoanConstants.RepayType.DQHBFX.equals(loan.getRepayType())) {
								period = loan.getDeadline();
							}
							// 还款计划总数
							int sum = period * subloans.size();
							// 生成理财放款
							if (LoanConstants.Type.VEHICLE.equals(loan.getLoanType())
									&& repaySubLoanService.getSumRepayVehicle(loan.getId()) == sum
									|| LoanConstants.Type.AGRICULTURE.equals(loan.getLoanType())
											&& repaySubLoanService.getSumRepayAgriculture(loan.getId()) == sum) {
								// 生成理财计划的还款计划
								repayService.saveProjectRepay(loan);
								loan.setStatus("还款中");
								loanService.update(loan);
								// 用户放款
								userAccountService.transferIn(loan.getBorrowMoneyUserID(), loan.getTotalmoney(),
										BusinessEnum.give_money_to_borrower, "借款项目:" + loan.getName() + ",已经到账!",
										"借款ID：" + loan.getId(), loan.getId());
								User user = userService.get(loan.getBorrowMoneyUserID());
								smsHttpClient.sendSms(user.getMobileNumber(),
										user.getRealname() + "," + loan.getName() + "," + loan.getTotalmoney() + ",0",
										"give_money_to_borrower_for_borrower");
							}
						}
					}
				}
			} catch (Exception e) {
				log.errLog("放款异步通知本地处理失败", e, 1);
			} finally {
				DRJedisDLock.releaseDLock(GIVE_MONEY_TO_BORROWER_LOCAL_LOCK, investSubloanId);
			}
		}
	}
	
	/**
	 * 散标放款
	 * 
	 * @param loanId
	 * @param invests
	 */
	private void giveMoneyToBorrower(Loan loan) {
		double money = 0D;// 本次放款金额
		double managementExpense = 0D; // 本次放款勾出管理费
		List<Invest> invests = loan.getInvests();
		GeneratorTradeJSON json = new GeneratorTradeJSON();
		json.setRequestNo(IdUtil.generateId(ToType.GMTB));
		json.setProjectNo(loan.getId());
		List<GeneratorDetailJSON> details = new ArrayList<>();
		for (Invest invest : invests) {
			GeneratorDetailJSON detail = new GeneratorDetailJSON();
			detail.setPreTransactionNo(invest.getId());
			detail.setAmount(invest.getMoney());
			details.add(detail);
		}
		json.setDetails(details);
		Generator generator = trusteeshipService.execute(json, TrusteeshipServer.CONFIRM_LOAN, GeneratorJSON.class,
				BusinessEnum.give_money_to_borrower);
		if (generator.getRespData() != null && generator.getRespData().getCode().equals("0")) {
			for (Invest invest : invests) {
				if (InvestConstants.InvestStatus.BID_SUCCESS.equals(invest.getStatus())) {
					invest.setLoan(loan);
					// 单笔放款
					if (loanService.giveMoneyToBorrower(invest)) {
						money += invest.getMoney();
						managementExpense += invest.getManagementExpense();
					}
				}
			}
		}
		try {
			if (investService.getInvestSeccessByLoanId(loan.getId()) == 0) {
				// 跟新存管通项目状态
				trusteeshipLoanService.modifyLoan(loan, LoanConstants.LoanStatus.REPAYING);
				// 生成还款计划
				repayService.saveRepay(loan);
				loan.setStatus("还款中");
				loanService.update(loan);
			}
			money = ArithUtil.sub(money, managementExpense);
			// 用户放款
			userAccountService.transferIn(loan.getBorrowMoneyUserID(), money, BusinessEnum.give_money_to_borrower,
					"借款项目:" + loan.getName() + ",已经到账!", "借款ID：" + loan.getId(), loan.getId());
			if (managementExpense > 0) {
				// 扣除借款管理费
				userAccountService.transferOut(loan.getBorrowMoneyUserID(), managementExpense,
						BusinessEnum.give_money_to_borrower, "借款成功，取出借款管理费", "借款ID：" + loan.getId(), loan.getId());
				platformAccountService.transferIn(PlatformAccountEnum.PLATFORM_SYS, managementExpense,
						BusinessEnum.give_money_to_borrower, "借款管理费", loan.getId());
			}
			if (money > 0) {
				User user = userService.get(loan.getBorrowMoneyUserID());
				smsHttpClient.sendSms(user.getMobileNumber(),
						user.getRealname() + "," + loan.getName() + "," + money + "," + managementExpense,
						"give_money_to_borrower_for_borrower");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog("放款异常", e);
		}
	}


	
	
	
	@Override
	public void bidders(String loanId, String investId, String type) throws Exception {
		// 项目流标
		if (type.equals("loan")) {
			Loan loan = loanService.get(loanId);
			if (loan == null) {
				log.errLog("流标失败", "loanId: " + loanId + ", 不存在");
				throw new LoanException(ErrorCode.LoanNotFind);
			}
			if (loan.getStatus().equals(LoanConstants.LoanStatus.DQGS)
					|| loan.getStatus().equals(LoanConstants.LoanStatus.RAISING)
					|| loan.getStatus().equals(LoanConstants.LoanStatus.RECHECK)) {
				Invest invest = new Invest();
				invest.setLoanId(loanId);
				invest.setStatus("还款中");
				List<Invest> invests = investService.getInvestLoan(invest);
				if (!invests.isEmpty()) {
					log.errLog("流标失败", "loanId: " + loanId + ", 存在还款中投资记录");
					throw new LoanException(ErrorCode.LoanStatusError);
				}
				invest.setStatus("等待确认,投标成功");
				invests = investService.getInvestLoan(invest);
				for (Invest i : invests) {
					try {
						i.setLoan(loan);
						// 理财计划流标
						if (LoanConstants.LoanType.PROJECT.equals(loan.getStandardOrProject())) {
							this.projectBidders(i);
						} else {
							// 散标流标
							this.bidders(i);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				invests = investService.getInvestLoan(invest);
				if (invests.isEmpty()) {
					// 理财计划流标
					if (LoanConstants.LoanType.PROJECT.equals(loan.getStandardOrProject())) {
						List<SubLoan> subloans = new ArrayList<>();
						if (LoanConstants.Type.VEHICLE.equals(loan.getLoanType())) {
							subloans = loanService.getVehicleByGroupCondition(loanId, 1);
						}
						if (LoanConstants.Type.AGRICULTURE.equals(loan.getLoanType())) {
							subloans = loanService.getAgricultureByGroupCondition(loanId, 1);
						}
						Loan l = new Loan();
						boolean b = true;
						for (SubLoan subloan : subloans) {
							try {
								l.setStatus(subloan.getSubloanStatus());
								l.setId(subloan.getSubloanId());
								GeneratorJSON generatorJSON = trusteeshipLoanService.modifyLoan(l,
										LoanConstants.LoanStatus.CANCEL);
								if (!generatorJSON.getCode().equals("0")) {
									b = false;
								}
							} catch (Exception e) {
								log.errLog("流标失败", e);
								b = false;
							}
						}
						if (b) {
							SubLoan sl = new SubLoan();
							sl.setLoanId(loanId);
							sl.setSubloanStatus("流标");
							if (LoanConstants.Type.VEHICLE.equals(loan.getLoanType())) {
								loanService.updateVehicle(sl);
							}
							if (LoanConstants.Type.AGRICULTURE.equals(loan.getLoanType())) {
								loanService.updateAgricultureForkLoans(sl);
							}
							loan.setStatus(LoanConstants.LoanStatus.CANCEL);
							loanService.update(loan);
						}
					} else {
						// 散标流标
						GeneratorJSON generatorJSON = trusteeshipLoanService.modifyLoan(loan,
								LoanConstants.LoanStatus.CANCEL);
						if (generatorJSON.getCode().equals("0")) {
							loan.setStatus(LoanConstants.LoanStatus.CANCEL);
							loanService.update(loan);
						} else {
							log.errLog("流标失败",
									"investId: " + invest.getId() + "存管返回信息" + generatorJSON.getDescription(), 1);
						}
					}
				}
			} else {
				log.errLog("流标失败", "loanId: " + loan.getId() + ", 项目状态不正确， loanStatus: " + loan.getStatus());
				throw new LoanException(ErrorCode.LoanStatusError);
			}
		} else if (type.equals("invest")) {
			Invest invest = investService.get(investId);
			if (invest == null) {
				log.errLog("流标失败", "investId: " + investId + ", 不存在");
				throw new LoanException(ErrorCode.InvestNotFind);
			}
			Loan loan = loanService.get(invest.getLoanId());
			if (loan == null) {
				log.errLog("流标失败", "loanId: " + loanId + ", 不存在");
				throw new LoanException(ErrorCode.LoanNotFind);
			}
			if (invest.getStatus().equals(InvestConstants.InvestStatus.BID_SUCCESS)) {
				// 理财计划流标
				if (LoanConstants.LoanType.PROJECT.equals(loan.getStandardOrProject())) {
					this.projectBidders(invest);
				} else {
					// 散标流标
					this.bidders(invest);
				}
			} else if (invest.getStatus().equals(InvestConstants.InvestStatus.WAIT_AFFIRM)) {
				try {
					investService.failInvest(invest);
				} catch (Exception e) {
					log.errLog("流标失败", "investId: " + invest.getId() + ExceptionUtils.getMessage(e), 1);
					throw e;
				}
			} else {
				log.errLog("流标失败", "investId: " + investId + ", 状态不正确, investStatus: " + invest.getStatus());
				throw new LoanException(ErrorCode.InvestNotFind);
			}
		}
	}

	/**
	 * 单笔流标
	 * 
	 * @param invest
	 * @throws Exception
	 */
	private void bidders(Invest invest) throws Exception {
		GeneratorJSON json = new GeneratorJSON();
		json.setRequestNo(IdUtil.randomUUID());
		json.setAmount(invest.getMoney());
		json.setPreTransactionNo(invest.getId());
		GeneratorJSON generatorJson = this.transactionCancle(invest.getId(), invest.getMoney());
		if(!("0").equals(generatorJson.getCode())){
			log.errLog("流标失败", "investId: " + invest.getId() + "存管返回信息" + generatorJson.getDescription(), 1);
		}else{
			try {
				investService.failInvest(invest);
			} catch (Exception e) {
				log.errLog("流标失败", "investId: " + invest.getId() + ExceptionUtils.getMessage(e), 1);
				throw e;
			}
		}	
	}

	/**
	 * 理财计划流标
	 * 
	 * @param invest
	 * @throws Exception
	 */
	private void projectBidders(Invest invest) throws Exception {
		GeneratorJSON json = new GeneratorJSON();
		json.setRequestNo(IdUtil.randomUUID());
		json.setAmount(invest.getMoney());
		json.setIntelRequestNo(invest.getId());
		Generator generator = trusteeshipService.execute(json, TrusteeshipServer.INTELLIGENT_PROJECT_UNFREEZE,
				GeneratorJSON.class);
		log.errLog("流标失败", "investId: " + invest.getId() + "存管返回信息" + generator.getRespData().getDescription(), 1);
		try {
			investService.failInvest(invest);
		} catch (Exception e) {
			log.errLog("流标失败", "investId: " + invest.getId() + ExceptionUtils.getMessage(e), 1);
			throw e;
		}
	}

	@Override
	public GeneratorJSON transactionCancle(String preTransaction, double money){
		GeneratorJSON json = new GeneratorJSON();
		json.setRequestNo(IdUtil.randomUUID());
		json.setAmount(money);
		json.setPreTransactionNo(preTransaction);
		Generator generator = trusteeshipService.execute(json, TrusteeshipServer.CANCEL_PRE_TRANSACTION,
				GeneratorJSON.class);
		return generator.getRespData();
	}

}
