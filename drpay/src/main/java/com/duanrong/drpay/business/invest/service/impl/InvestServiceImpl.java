package com.duanrong.drpay.business.invest.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.ArithUtil;
import util.Log;
import base.error.ErrorCode;
import base.exception.AccountException;
import base.exception.TradeException;
import base.exception.UserAccountException;

import com.duanrong.drpay.business.account.service.UserAccountService;
import com.duanrong.drpay.business.invest.InvestConstants;
import com.duanrong.drpay.business.invest.dao.InvestDao;
import com.duanrong.drpay.business.invest.model.Invest;
import com.duanrong.drpay.business.invest.model.InvestSubLoan;
import com.duanrong.drpay.business.invest.service.InvestService;
import com.duanrong.drpay.business.loan.LoanConstants;
import com.duanrong.drpay.business.loan.model.Loan;
import com.duanrong.drpay.business.loan.service.LoanService;
import com.duanrong.drpay.business.user.service.RedPacketService;
import com.duanrong.drpay.business.user.service.UserService;
import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.config.IdUtil;
import com.duanrong.drpay.config.ToType;
import com.duanrong.util.InterestUtil;
import com.duanrong.util.jedis.DRJedisDLock;

@Service
public class InvestServiceImpl implements InvestService {

	@Resource
	InvestDao investDao;

	@Resource
	LoanService loanService;

	@Resource
	UserAccountService userAccountService;

	@Resource
	RedPacketService redpacketService;

	@Resource
	UserService userService;

	@Resource
	Log log;

	private static final String CREATE_INVEST = "createInvest";

	@Override
	public Invest get(String id) {
		return investDao.get(id);
	}

	@Override
	public void update(Invest invest) {
		investDao.update(invest);
	}

	@Override
	public void verifyInvest(Invest invest) throws TradeException, UserAccountException {
		/*********************** 参数校验 ***********************/
		String userId = invest.getInvestUserID();
		String loanId = invest.getLoanId();

		if (!userService.hasRoleByUserId(userId, "INVESTOR"))
			throw new TradeException(ErrorCode.UserNoInvestor);
		Loan loan = loanService.get(loanId);
		if (null == loan)
			throw new TradeException(ErrorCode.LoanNotFind);

		if (loan.getTextItem().equals("是") && loan.getWhetherInvested() == 0 || loan.getDrpayStatus() != 1) {
			throw new TradeException("该项目不可投");
		}

		if (StringUtils.equals(loan.getBorrowMoneyUserID(), userId)) {
			log.errLog(this.getClass().getName() + ".create(..)", "投资人与融资人ID一致：" + userId);
			throw new TradeException("投资人与借款人不可为同一人");
		}

		if (!StringUtils.equals(loan.getStatus(), LoanConstants.LoanStatus.RAISING)) {
			log.errLog(this.getClass().getName() + ".create(..)", "项目状态不是筹款中，不能投资：" + userId);
			throw new TradeException(ErrorCode.LoanStatusError);
		}

		if (invest.getIsAutoInvest() == null) {
			invest.setIsAutoInvest(false);
		}

		// 手动头标，不可投新手标判断
		if ("是".equals(loan.getNewbieEnjoy())) {
			Long investRecord = 0L;
			if (DRJedisDLock.getDLock(CREATE_INVEST, "invest", 180)) {
				try {
					investRecord = this.getInvestSuccessRecordByUserId(userId);
					if (investRecord != 0) {
						throw new TradeException(ErrorCode.OldUserNotInvestNewbieEnjoy);
					}
				} finally {
					DRJedisDLock.releaseDLock(CREATE_INVEST, "invest");
				}
			}
		}

		// 判断用户投资金额是否大于投资上限金额
		Double personalInvestCeiling = getInvestSumMoneyByUser(invest.getInvestUserID(), loanId);
		if (loan.getMaxInvestMoney() != null && loan.getMaxInvestMoney() != 0
				&& loan.getMaxInvestMoney() - personalInvestCeiling < invest.getMoney()) {
			throw new TradeException("您的投资金额大于个人投资金额上限");
		}

		verifyInvestMoney(loan, invest.getMoney());
		double balance = userAccountService.getUserAccount(invest.getInvestUserID()).getAvailableBalance();
		// 用户填写认购的钱数以后，判断余额，如果余额不够，不能提交，弹出新页面让用户充值
		if (invest.getMoney() > balance) {
			log.errLog("投资金额超过账户余额",
					"userId:" + invest.getInvestUserID() + ",当前账户金额：" + balance + ", 投资金额：" + invest.getMoney());
			throw new UserAccountException("您的账户余额不足，请充值");
		}
		// 设置投资类型
		if (StringUtils.isEmpty(invest.getType())) {
			// invest.setType(InvestConstants.InvestType.NONE);
		}
		// 计算预期利息
		if (invest.getInterest() == null) {
			int periods = 0;
			if ("天".equals(loan.getOperationType())) {
				periods = loan.getDay();
			} else {
				periods = loan.getDeadline();
			}
			invest.setInterest(InterestUtil.getInterestByPeriod(invest.getMoney(), loan.getRate(), periods,
					loan.getOperationType(), loan.getRepayType()));
		}

		// 理财包
		if (LoanConstants.LoanType.PROJECT.equals(loan.getStandardOrProject())) {
			invest.setId(IdUtil.generateId(ToType.IVPJ));
		} else {
			// 投资成功以后，判断项目是否有尚未认购的金额，如果没有，则更改项目状态。。
			// 投资成功以后，判断项目是否有尚未认购的金额，如果没有，则更改项目状态。。
			if (invest.getIsAutoInvest()) {
				invest.setId(IdUtil.generateId(ToType.ZDTB));
			} else {
				invest.setId(IdUtil.generateId(ToType.INVE));
			}
		}
		// 判断红包用到了invest的Rate、Loan，所有放到最后判断
		invest.setLoan(loan);
		if (invest.getRedpacketId() != 0) {
			redpacketService.verifyRedPacket(invest);
		}
	}

	@Override
	public void createInvest(Invest invest) throws TradeException, UserAccountException {
		verifyInvest(invest);
		invest.setStatus(InvestConstants.InvestStatus.WAIT_AFFIRM);
		invest.setTime(new Date());
		Loan loan = loanService.queryWithLock(invest.getLoanId());
		invest.setRate(loan.getRate());
		String userId = invest.getInvestUserID();
		Double investMoney = invest.getMoney();
		// 判断项目尚未认购的金额，如果用户想认购的金额大于此金额，则。。。
		double totalMonay = loan.getTotalmoney();
		double loanRiseMoney = getInvestSumMoneyByLoan(loan.getId());
		double loanNeedMoney = totalMonay - loanRiseMoney;
		if (ArithUtil.round(loanNeedMoney, 2) < ArithUtil.round(investMoney, 2)) {
			throw new TradeException(ErrorCode.InvestMoneyGreaterThanLoanNeedMoney);
		}
		if (ArithUtil.round(loanNeedMoney, 2) == ArithUtil.round(investMoney, 2)) {// 修改项目状态
			loan.setStatus("等待复核");
			loanService.update(loan);
		}
		insert(invest);
		/* createSchule(invest); */
		redpacketService.userRedPacket(invest, "success");
		userAccountService.freeze(userId, investMoney, BusinessEnum.invest, "冻结：投资" + loan.getName(),
				"借款ID：" + loan.getId() + ",投资ID：" + invest.getId(), invest.getId());
	}

	@Override
	public void insert(Invest invest) {
		investDao.insert(invest);
	}

	@Override
	public double getInvestSumMoneyByLoan(String loanId) {
		return investDao.getInvestSumMoneyByLoan(loanId);
	}

	@Override
	public double getInvestSumMoneyByUser(String userId, String loanId) {
		return investDao.getInvestSumMoneyByUser(userId, loanId);
	}

	/**
	 * 投资流标
	 * 
	 * @param invest
	 * @throws TradeException
	 * @throws AccountException
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void failInvest(Invest invest) throws TradeException, AccountException {
		if (InvestConstants.InvestStatus.BID_SUCCESS.equals(invest.getStatus())
				|| InvestConstants.InvestStatus.WAIT_AFFIRM.equals(invest.getStatus())) {
			invest.setStatus(InvestConstants.InvestStatus.CANCEL);
			Loan loan = invest.getLoan();
			if (LoanConstants.LoanStatus.RECHECK.equals(loan.getStatus())) {
				loan.setStatus(LoanConstants.LoanStatus.RAISING);
				loanService.update(loan);
			}
			redpacketService.userRedPacket(invest, "fail");
			invest.setRedpacketId(0); // 加息券使用失败，更新invest表的红包为未使用状态
			update(invest);
			userAccountService.unfreeze(invest.getInvestUserID(), invest.getMoney(), BusinessEnum.bidders,
					"解冻：投资" + invest.getLoan().getName(), "借款ID：" + invest.getLoan().getId(), invest.getId());
		}
	}

	private void verifyInvestMoney(Loan loan, Double money) throws TradeException {
		Double investOriginMoney = loan.getInvestOriginMoney();
		Double increaseMoney = loan.getIncreaseMoney();
		DecimalFormat df2 = new DecimalFormat("###");
		if (money < investOriginMoney) {
			throw new TradeException("请输入" + df2.format(investOriginMoney) + "元或其整数倍的投资金额");
		}

		if (money % increaseMoney != 0) {
			throw new TradeException("请输入" + df2.format(investOriginMoney) + "元或其整数倍的投资金额");
		}
	}

	/**
	 * 生成一个5分钟调度 如果用户5分钟内未支付，投资记录将会流标
	 * 
	 * @param invest
	 * @throws SchedulerException
	 */
	/*
	 * private void createSchule(Invest invest) throws SchedulerException {
	 * SimpleTrigger trigger = (SimpleTrigger) scheduler
	 * .getTrigger(TriggerKey.triggerKey( invest.getId(),
	 * ScheduleConstants.TriggerGroup.CHECK_INVEST_OVER_EXPECT_TIME)); if
	 * (trigger != null) { trigger.getTriggerBuilder()
	 * .withSchedule(SimpleScheduleBuilder.simpleSchedule())
	 * .startAt(DateUtil.addMinute(invest.getTime(), 5)).build();
	 * scheduler.rescheduleJob(trigger.getKey(), trigger); } else { JobDetail
	 * jobDetail = JobBuilder .newJob(CheckInvestOverExpectTime.class)
	 * .withIdentity( invest.getId(),
	 * ScheduleConstants.JobGroup.CHECK_INVEST_OVER_EXPECT_TIME) .build();//
	 * 任务名，任务组，任务执行类 jobDetail.getJobDataMap().put("invest", invest.getId());
	 * trigger = TriggerBuilder .newTrigger() .forJob(jobDetail)
	 * .startAt(DateUtil.addMinute(invest.getTime(), 5))
	 * .withSchedule(SimpleScheduleBuilder.simpleSchedule()) .withIdentity(
	 * invest.getId(),
	 * ScheduleConstants.TriggerGroup.CHECK_INVEST_OVER_EXPECT_TIME) .build();
	 * scheduler.scheduleJob(jobDetail, trigger); }
	 * 
	 * }
	 */

	@Override
	public int getInvestSeccessByLoanId(String loanId) {
		return investDao.getInvestSeccessByLoanId(loanId);
	}

	@Override
	public List<Invest> getInvestLoan(Invest invest) {

		return investDao.getInvestLoan(invest);
	}

	@Override
	public long getInvestSuccessNotNewRecordByUserId(String userId) {
		return investDao.getInvestSuccessNotNewRecordByUserId(userId);
	}

	@Override
	public Long getInvestSuccessRecordByUserId(String userId) {
		return investDao.getInvestSuccessRecordByUserId(userId);
	}

	@Override
	public void insertBatchInvestSubloan(List<InvestSubLoan> investSubLoans) {
		investDao.insertBatchInvestSubloan(investSubLoans);

	}

	@Override
	public double getInvestSubloanMoneyByInvestId(String investId) {
		return investDao.getInvestSubloanMoneyByInvestId(investId);
	}

	@Override
	public List<InvestSubLoan> getInvestSubloanByInvestId(String investId) {
		return investDao.getInvestSubloanByInvestId(investId);
	}

	@Override
	public List<InvestSubLoan> getInvestSubLoans(String subloanId) {
		return investDao.getInvestSubLoans(subloanId);
	}

	@Override
	public InvestSubLoan getInvestSubloan(String investSubloanId) {
		return investDao.getInvestSubLoan(investSubloanId);
	}

	@Override
	public void insertInvestSubloan(InvestSubLoan investSubLoan) {
		investDao.insertInvestSubloan(investSubLoan);
	}

	@Override
	public void updateInvestSubloan(InvestSubLoan investSubLoan) {
		investDao.updateInvestSubloan(investSubLoan);
	}

}