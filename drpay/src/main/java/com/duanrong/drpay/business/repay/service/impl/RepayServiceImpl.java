package com.duanrong.drpay.business.repay.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import util.ArithUtil;
import util.DateUtil;
import util.Log;
import util.SmsHttpClient;
import base.exception.TradeException;
import base.exception.UserAccountException;

import com.duanrong.drpay.business.account.model.UserAccount;
import com.duanrong.drpay.business.account.service.UserAccountService;
import com.duanrong.drpay.business.invest.InvestConstants;
import com.duanrong.drpay.business.invest.dao.InvestDao;
import com.duanrong.drpay.business.invest.model.Invest;
import com.duanrong.drpay.business.invest.model.InvestSubLoan;
import com.duanrong.drpay.business.loan.LoanConstants;
import com.duanrong.drpay.business.loan.dao.LoanDao;
import com.duanrong.drpay.business.loan.model.Loan;
import com.duanrong.drpay.business.loan.model.SubLoan;
import com.duanrong.drpay.business.repay.RepayDate;
import com.duanrong.drpay.business.repay.RepayHelper;
import com.duanrong.drpay.business.repay.dao.RepayDao;
import com.duanrong.drpay.business.repay.dao.RepayInvestDao;
import com.duanrong.drpay.business.repay.dao.RepaySubLoanDao;
import com.duanrong.drpay.business.repay.model.Repay;
import com.duanrong.drpay.business.repay.model.RepayInvest;
import com.duanrong.drpay.business.repay.model.RepaySubLoan;
import com.duanrong.drpay.business.repay.service.RepayService;
import com.duanrong.drpay.business.repay.service.RepaySubLoanService;
import com.duanrong.drpay.business.user.model.User;
import com.duanrong.drpay.business.user.service.UserService;
import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.config.RedisCacheKey;
import com.duanrong.util.InterestUtil;
import com.duanrong.util.jedis.DRJedisCacheUtil;
import com.duanrong.util.jedis.DRJedisMQ;
import com.duanrong.util.json.FastJsonUtil;

@Service
public class RepayServiceImpl implements RepayService {

	@Resource
	RepayDao repayDao;

	@Resource
	RepaySubLoanDao repaySubloanDao;

	@Resource
	UserAccountService userAccountService;

	@Resource
	InvestDao investDao;

	@Resource
	LoanDao loanDao;

	@Resource
	Log log;

	@Resource
	SmsHttpClient smsHttpClient;

	@Resource
	UserService userService;

	@Resource
	RepayInvestDao repayInvestDao;

	@Resource
	RepaySubLoanService repaySubLoanService;

	@Override
	public Repay prepare(String repayId) throws TradeException,
			UserAccountException {
		Repay repay = repayDao.get(repayId);
		Loan loan = repay.getLoan();
		List<Invest> invests = loan.getInvests();
		String loanId = repay.getLoanId();
		double balance = userAccountService.getUserAccount(repay.getUserId())
				.getAvailableBalance();
		String repayStatus = repay.getStatus();
		// 还款中，投资人不为空
		String repaying = LoanConstants.LoanStatus.REPAYING;
		if (!invests.isEmpty() && StringUtils.equals(repayStatus, repaying)) {
			String operationType = loan.getOperationType();
			// 查询上一期是否已还款完成
			if (repay.getPeriod() > 1) {
				Repay repayLast = new Repay();
				repayLast.setPeriod(repay.getPeriod() - 1);
				repayLast.setLoanId(loan.getId());
				repayLast = repayDao.getByCondition(repayLast).get(0);
				if (StringUtils.equals(repaying, repayLast.getStatus())) {
					throw new TradeException("上一期还款还未完成，项目ID:" + loanId);
				}
			}
			// 按天计息 按月付息 到期还本付息 可提现还款
			if (StringUtils.equals("天", operationType)) {
				// 一次性到期还本付息，可提前还款（天标），主要是为了判断用户账户余额是否充足
				Date repayDay = repay.getRepayDay();
				Date repayTime = repay.getTime();
				int dayInterval = DateUtil.getIntervalDaysNoABS(repayTime,
						repayDay);
				if (StringUtils.equals(loan.getRepayType(),
						LoanConstants.RepayType.DQHBFX)) {
					dayInterval = loan.getDay();
				}
				repay.setInterest(getRepayInterestByPeriod(loan, 1, dayInterval));
			}
			// 余额不足
			if (balance < ArithUtil.addRound(repay.getCorpus(),
					repay.getInterest())) {
				throw new UserAccountException("您的账户余额不足，请先进行充值然后再还款。项目ID:"
						+ loanId);
			}
			return repay;
		} else {
			log.errLog("还款异常", this.getClass() + "-->prepare" + "repayid-->"
					+ repayId);
			throw new TradeException("还款异常，项目ID:" + loanId);
		}
	}

	@Override
	public Double queryRepayMoney(Invest invest, Repay repay, Date now,
			boolean isBeforeRepay) {
		RepayInvest repayInvest = new RepayInvest();
		repayInvest.setRepayId(repay.getId());
		repayInvest.setInvestUserId(invest.getInvestUserID());
		repayInvest.setInvestId(invest.getId());
		// 根据条件查询RepayInvest
		repayInvest = repayInvestDao.getByCondition(repayInvest);
		// 本息和
		Double repayMoney;

		if (repayInvest == null) {
			Integer deadline = repay.getLoan().getDeadline();
			Integer period = repay.getPeriod();

			// 利息
			Double paidInterest;
			// 本金
			Double paidCorpus;
			// 计息方式（按天或按月）
			String operationType = invest.getLoan().getOperationType();

			// 一次性到期还本付息，可提前还款（天标）
			String repayType = invest.getLoan().getRepayType();

			Integer day = invest.getLoan().getDay();
			// 计算应还利息
			if (StringUtils.equals(operationType, "天")) {
				// 计算天差
				Integer dayInterval = DateUtil.getIntervalDaysNoABS(
						repay.getTime(), repay.getRepayDay());
				if (LoanConstants.RepayType.DQHBFX.equals(repayType)) {
					// 在项目初始借款天数内还款，还款的天数为项目初始借款天数
					dayInterval = day;
				}
				paidInterest = getInterest(invest, period, dayInterval);
			} else {
				paidInterest = getInterest(invest, period, deadline);
			}

			// 获得该笔投资的本金
			paidCorpus = getCorpus(invest, period, deadline, isBeforeRepay);
			// 本+息的和
			repayMoney = ArithUtil.addRound(paidInterest, paidCorpus);

			// 记录还款金额
			repayInvest = new RepayInvest();
			repayInvest.setRepayId(repay.getId());
			repayInvest.setInvestId(invest.getId());
			repayInvest.setInvestUserId(invest.getInvestUserID());
			repayInvest.setCorpus(paidCorpus);
			repayInvest.setInterest(ArithUtil.round(paidInterest, 2));
			repayInvest.setInvestAllowanceInterest(0D);
			repayInvest.setRewardMoney(0);
			repayInvestDao.insert(repayInvest);
			// 记录日志
			log.infoLog("项目还款", "投资人：" + invest.getInvestUserID() + "，利息:"
					+ paidInterest + "，本金:" + paidCorpus + "，利息+本金="
					+ repayMoney);
		} else {
			repayMoney = ArithUtil.round(
					repayInvest.getCorpus() + repayInvest.getInterest(), 2);
		}
		return repayMoney;
	}

	public double calculateInvestInterset(String repayType, int deadline,
			double rate, double money) {
		double sum = 0D;
		if (repayType.equals(LoanConstants.RepayType.DQHBFX)) {
			sum = getDQHBFXInterestByPeriod(money, rate, deadline);
		} else {
			for (int i = 1; i <= deadline; i++) {
				sum = ArithUtil
						.add(sum,
								getInterestByPeriod(repayType, rate, money, i,
										deadline));
			}
		}
		sum = ArithUtil.round(sum, 2);
		return sum;
	}

	public double getDQHBFXInterestByPeriod(double money, double rate, int month) {
		double interest = ArithUtil.round(money * rate / 12 * month, 2);
		return interest;
	}

	public double getInterestByPeriod(String repayType, double rate,
			double money, int period, int deadline) {
		if (repayType.equals(LoanConstants.RepayType.DQHBFX)) {
			return getDQHBFXInterestByPeriod(money, rate, deadline);
		} else if (repayType.equals(LoanConstants.RepayType.RFCL)) {
			return getRFCLInterestByPeriod(money, rate);
		}
		throw new RuntimeException("未知还款方式：" + repayType);
	}

	/**
	 * 获取先息后本还款某一期应得的利息 constant payment mortgage（CPM）
	 * 
	 * @param money
	 *            投资金额
	 * @param rate
	 *            投资利率
	 */
	private double getRFCLInterestByPeriod(double money, double rate) {
		return ArithUtil.round(money * rate / 12, 2);

	}

	/**
	 * 
	 * @description 获取按天计息请款下:一次性到期还本付息,某一期的利息计算
	 * @author 孙铮
	 * @time 2014-8-8 下午3:24:01
	 * @param money
	 *            投资金额
	 * @param rate
	 *            利率
	 * @param period
	 *            项目多少天
	 * @return
	 */
	public double getDQHBFXInterestByPeriodDay(double money, double rate,
			int dayTotal) {
		double interest = ArithUtil.round(money * rate / 365 * dayTotal, 2);
		return interest;
	}

	/**
	 * 
	 * @description 获得按月计算情况下:一次性到期还本付息,某一期的利息计算
	 * @author 孙铮
	 * @time 2014-9-16 下午2:38:57
	 * @param money
	 * @param rate
	 * @param deadline
	 * @return
	 */
	public double getDQHBFXInterestByPeriodMonth(double money, double rate,
			Integer deadline) {
		double interest = ArithUtil.round(money * rate / 12 * deadline, 2);
		return interest;
	}

	@Override
	public Repay get(String id) {
		return repayDao.get(id);
	}

	/********************** 利息相关 **********************/
	/**
	 * 应还利息
	 * 
	 * @param loan
	 *            借款项目
	 * @param period
	 *            期数
	 * @param deadline
	 *            截止时间
	 * @return
	 */
	public double getRepayInterestByPeriod(Loan loan, int period, int deadline) {
		double money = 0;
		List<Invest> invests = loan.getInvests();
		for (Invest invest : invests) {
			invest.setLoan(loan);
			// 计算应还利息总和，避免使用项目总金额来计算利息
			money = ArithUtil.addRound(money,
					getInterest(invest, period, deadline));
		}
		return money;
	}

	/********************** 利息相关 **********************/
	/**
	 * 应还利息
	 * 
	 * @param loan
	 *            借款项目
	 * @param period
	 *            期数
	 * @param deadline
	 *            截止时间
	 * @return
	 */
	private double getRepayInterestByPeriod(SubLoan subloan, int period,
			int deadline) {
		double money = 0;
		List<InvestSubLoan> investSubloans = subloan.getInvestSubloans();
		for (InvestSubLoan investSubloan : investSubloans) {
			investSubloan.setSubloan(subloan);
			// 计算应还利息总和，避免使用项目总金额来计算利息
			money = ArithUtil.addRound(money,
					getInterest(investSubloan, period, deadline));
		}
		return money;
	}

	/**
	 * 获得利息
	 * 
	 * @param invest
	 * @param period
	 * @param deadline
	 * @return
	 */
	public double getInterest(InvestSubLoan investSubloan, int period,
			int deadline) {
		SubLoan subloan = investSubloan.getSubloan();
		Double money = investSubloan.getMoney();
		Double rate = subloan.getRate();

		// 按月付息到期还本
		if (StringUtils.equals(subloan.getRepayType(),
				LoanConstants.RepayType.RFCL)) {
			if ("天".equals(subloan.getOperationType())) {
				return getRFCLInterestByPeriodDay(money, rate, deadline);
			} else {
				return getRFCLInterestByPeriod(investSubloan.getMoney(),
						subloan.getRate());
			}
		}

		// 一次性到期还本付息
		if (subloan.getRepayType().equals(LoanConstants.RepayType.DQHBFX)) {
			if ("天".equals(subloan.getOperationType())) {
				return getDQHBFXInterestByPeriodDay(money, rate, deadline);
			} else {
				return getDQHBFXInterestByPeriod(investSubloan.getMoney(),
						subloan.getRate(), subloan.getDeadline());
			}
		}

		if (subloan.getRepayType().equals(LoanConstants.RepayType.CPM)) {
			return InterestUtil.interest(money, rate, deadline, period);
		}
		return 0;
	}

	/**
	 * 获得利息
	 * 
	 * @param invest
	 * @param period
	 * @param deadline
	 * @return
	 */
	public double getInterest(Invest invest, int period, int deadline) {
		Loan loan = invest.getLoan();
		Double money = invest.getMoney();
		Double rate = loan.getRate();

		// 按月付息到期还本
		if (StringUtils.equals(loan.getRepayType(),
				LoanConstants.RepayType.RFCL)) {
			if ("天".equals(loan.getOperationType())) {
				return getRFCLInterestByPeriodDay(money, rate, deadline);
			} else {
				return getRFCLInterestByPeriod(invest.getMoney(),
						invest.getRate());
			}
		}

		// 一次性到期还本付息
		if (loan.getRepayType().equals(LoanConstants.RepayType.DQHBFX)) {
			if ("天".equals(loan.getOperationType())) {
				return getDQHBFXInterestByPeriodDay(money, rate, deadline);
			} else {
				return getDQHBFXInterestByPeriod(invest.getMoney(),
						invest.getRate(), loan.getDeadline());
			}
		}

		if (loan.getRepayType().equals(LoanConstants.RepayType.CPM)) {
			return InterestUtil.interest(money, rate, deadline, period);
		}
		return 0;
	}

	/**
	 * 
	 * @description 获得按天计算情况下:按月付息到期还本,某一期的利息计算
	 * @author 孙铮
	 * @time 2014-8-8 下午3:24:01
	 * @param money
	 *            投资金额
	 * @param rate
	 *            利率
	 * @param period
	 *            项目多少天
	 * @return
	 */
	public double getRFCLInterestByPeriodDay(double money, double rate,
			int period) {
		double interest = ArithUtil.round(money * rate / 365 * period, 2);
		return interest;
	}

	/**
	 * 获得按月计算情况下:按月付息到期还本,某一期的利息计算
	 * 
	 * @param money
	 *            投资金额
	 * @param rate
	 * @param period
	 * @param deadline
	 * @return
	 */
	public double getRFCLInterestByPeriodMonth(double money, double rate) {
		double interest = ArithUtil.round(money * rate / 12, 2);
		return interest;
	}

	/********************** 本金相关 **********************/
	/**
	 * 应还本金
	 * 
	 * @param loan
	 *            借款项目
	 * @param period
	 *            期数
	 * @param deadline
	 *            截止时间
	 * @return
	 */
	public double getRepayCorpusByPeriod(Loan loan, int period, int deadline) {
		List<Invest> invests = loan.getInvests();
		double sumMoney = 0;
		for (Invest invest : invests) {
			invest.setLoan(loan);
			sumMoney = ArithUtil.addRound(sumMoney,
					getCorpus(invest, period, deadline, false));
		}
		return sumMoney;
	}

	/**
	 * 应还本金
	 * 
	 * @param loan
	 *            借款项目
	 * @param period
	 *            期数
	 * @param deadline
	 *            截止时间
	 * @return
	 */
	private double getRepayCorpusByPeriod(SubLoan subloan, int period,
			int deadline) {
		List<InvestSubLoan> investSubloans = subloan.getInvestSubloans();
		double sumMoney = 0;
		for (InvestSubLoan investSubloan : investSubloans) {
			investSubloan.setSubloan(subloan);
			sumMoney = ArithUtil.addRound(sumMoney,
					getCorpus(investSubloan, period, deadline, false));
		}
		return sumMoney;
	}

	/**
	 * 获得本金
	 * 
	 * @param invest
	 * @param period
	 * @param deadline
	 * @return
	 */
	private double getCorpus(InvestSubLoan investSubloan, int period,
			int deadline, boolean isBeforeRepay) {
		Double money = investSubloan.getMoney();
		if (investSubloan.getSubloan().getRepayType()
				.equals(LoanConstants.RepayType.RFCL)) {
			return period == deadline ? money : 0;
		} else if (investSubloan.getSubloan().getRepayType()
				.equals(LoanConstants.RepayType.DQHBFX)) {
			return money;
		} else if (investSubloan.getSubloan().getRepayType()
				.equals(LoanConstants.RepayType.CPM)) {
			if (isBeforeRepay) {
				return InterestUtil.corpus(money, investSubloan.getSubloan()
						.getRate(), deadline, period)
						+ InterestUtil.surplusCorpus(money, investSubloan
								.getSubloan().getRate(), deadline, period);
			}
			return InterestUtil.corpus(money, investSubloan.getSubloan()
					.getRate(), deadline, period);
		}

		return 0;
	}

	/**
	 * 获得本金
	 * 
	 * @param invest
	 * @param period
	 * @param deadline
	 * @return
	 */
	public double getCorpus(Invest invest, int period, int deadline,
			boolean isBeforeRepay) {
		Double money = invest.getMoney();

		if (invest.getLoan().getRepayType()
				.equals(LoanConstants.RepayType.RFCL)) {
			return period == deadline ? money : 0;
		} else if (invest.getLoan().getRepayType()
				.equals(LoanConstants.RepayType.DQHBFX)) {
			return money;
		} else if (invest.getLoan().getRepayType()
				.equals(LoanConstants.RepayType.CPM)) {
			if (isBeforeRepay) {
				return InterestUtil.corpus(money, invest.getRate(), deadline,
						period)
						+ InterestUtil.surplusCorpus(money, invest.getRate(),
								deadline, period);
			}
			return InterestUtil.corpus(money, invest.getRate(), deadline,
					period);
		}

		return 0;
	}

	/**
	 * 单笔还款
	 * 
	 * @param repayId
	 * @param invest
	 * @throws NoOpenAccountException
	 */
	@Transactional
	private void repay(RepayInvest repayInvest, Repay repay)
			throws UserAccountException {
		if (repayInvest.getStatus() == -1) // 未迁移的用户,不作处理，直接发短信通知
		{
			// TODO 发短信，还款 未迁移用户通知短信（已增加）
			try {
				String mobile = repay.getUser().getMobileNumber();
				String realName = repay.getUser().getRealname();
				String loanName = repay.getLoan().getName();
				String money = Double.toString(ArithUtil.add(
						repayInvest.getCorpus(), repayInvest.getInterest()));
				smsHttpClient.sendSms(mobile, realName + "," + loanName + ","
						+ money, "repay_noactive");
			} catch (Exception e) {
				log.errLog("还款失败", "investId:" + repayInvest.getInvestId()
						+ "还款未激活短信发送失败");
			}
			log.errLog("还款失败", "投资人账户未迁移" + repayInvest.getInvestUserId(), 1);
			return;
		}
		if (repayInvest.getStatus() == 1)
			return;
		Invest invest = investDao.get(repayInvest.getInvestId());
		Loan loan = invest.getLoan();
		Double corpus = repayInvest.getCorpus();
		Double interest = repayInvest.getInterest();
		Double money = ArithUtil.addRound(corpus, interest);
		// 利息
		Double interestAddAllowance = interest;
		invest.setPaidInterest(ArithUtil.addRound(invest.getPaidInterest(),
				interest));
		// 投资表已还本金
		invest.setPaidMoney(ArithUtil.addRound(invest.getPaidMoney(), corpus));
		if (repay.getIsBeforeRepay() == 1) {
			invest.setInterest(invest.getPaidInterest());
		}
		// 最后一期更新投资状态
		if (loan.getDeadline() == repay.getPeriod()
				|| repay.getIsBeforeRepay() == 1
				|| LoanConstants.RepayType.DQHBFX.equals(loan.getRepayType())) {
			invest.setStatus(InvestConstants.InvestStatus.COMPLETE);
			try {
				Map<String, Object> map = new HashMap<>();
				map.put("investId", invest.getId());
				map.put("loanId", invest.getLoanId());
				map.put("pushTime", new Date());
				DRJedisMQ.product("pushinvest", FastJsonUtil.objToJson(map));
			} catch (Exception e) {
				log.errLog("还款加入队列", e);
			}
			//TODO 修改investSubLoan
			updateInvestSubloan(loan,invest.getId());
		}
		// 更新投资记录已换金额
		investDao.update(invest);
		// 更新repayinvest还款状态
		repayInvest.setRepayTime(new Date());
		repayInvest.setStatus(1);
		// 更新投资人账户
		userAccountService.transferIn(
				invest.getInvestUserID(),
				money,
				BusinessEnum.repay,
				"项目还款：" + loan.getName(),
				"还款ID:" + repay.getId() + "  借款ID:" + loan.getId() + "  本金："
						+ corpus + "  利息：" + interestAddAllowance
						+ repay.getId(), repay.getId());
		repayInvestDao.update(repayInvest);
		// 发送短信和站内信
		try {
			if (money >= 0.01) {
				User user = userService.get(invest.getInvestUserID());
				UserAccount userAccount = userAccountService
						.getUserAccount(invest.getInvestUserID());
//				double balance = userAccount.getAvailableBalance();
				smsHttpClient.sendSms(user.getMobileNumber(), loan.getName()
						+ "," + money.toString() + "," + userAccount.getAvailableBalance(), "repay");
			}
		} catch (Exception ex) {
			log.errLog("还款短信和站内信发送失败", ex);
		}
	}

	private void updateInvestSubloan(Loan loan,String investId){
		if(LoanConstants.LoanType.PROJECT.equals(loan.getStandardOrProject())){
			investDao.updateInvestSubloanByInvestId(investId);
		}
	}
	@Override
	public void normalRepay(Repay repay, List<RepayInvest> repayInvests)
			throws UserAccountException {

		for (RepayInvest repayInvest : repayInvests) {
			this.repay(repayInvest, repay);
		}
		Loan loan = repay.getLoan();
		repay.setStatus(LoanConstants.LoanStatus.COMPLETE);
		repay.setOperationTime(new Date());
		repay.setHandleStatus("success");
		// 修改所有repay中除了repayId之外未还款的数据
		if (repay.getIsBeforeRepay() == 1) {
			updateOtherRepay(repay);
			loan.setIsBeforeRepay(1);
		}
		repayDao.update(repay);
		// 如果是最后一期，或者是提前还款，对项目状态和投资状态进行更新
		if (loan.getDeadline() == repay.getPeriod()
				|| repay.getIsBeforeRepay() == 1
				|| LoanConstants.RepayType.DQHBFX.equals(loan.getRepayType())) {
			loan.setStatus(LoanConstants.LoanStatus.COMPLETE);
			loanDao.update(loan);
			// 修改子标状态，子标投资状态
			updateSubLoan(loan);
		}
		try {
			DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + repay.getLoanId(),
					RedisCacheKey.LOAN_INFO, RedisCacheKey.LOAN_PROCESS,
					RedisCacheKey.LOAN_INVESTOR);
		} catch (Exception e) {
			log.infoLog("还款加入队列", e);
		}
		// 融资人的账户，扣除还款。
		userAccountService.tofreeze(loan.getBorrowMoneyUserID(),
				ArithUtil.addRound(repay.getCorpus(), repay.getInterest()),
				BusinessEnum.repay, "借款：" + loan.getName() + "正常还款", "还款ID："
						+ repay.getId() + "，本金：" + repay.getCorpus() + "，利息："
						+ repay.getInterest(), repay.getId());

	}

	private void updateOtherRepay(Repay repay) {
		repayDao.updateOtherRepay(repay);
	}

	private void updateSubLoan(Loan loan){
		if(LoanConstants.LoanType.PROJECT.equals(loan.getStandardOrProject())){
			String loanType = loan.getLoanType();
			SubLoan sl = new SubLoan();
			sl.setLoanId(loan.getId());
			sl.setSubloanStatus("完成");
			if(LoanConstants.Type.VEHICLE.equals(loanType)){
				//修改车的子标loan_vehicle
				loanDao.updateVehicle(sl);
			}else if(LoanConstants.Type.AGRICULTURE.equals(loanType)){
				//修改农贷子标agriculture_fork_loans
				loanDao.updateAgricultureForkLoans(sl);
			}
		}
		
	}
	@Override
	@Transactional
	public void saveRepay(Loan loan) {
		if ("天".equals(loan.getOperationType())) {
			// 一次性到期还本付息
			if (loan.getRepayType().equals(LoanConstants.RepayType.DQHBFX)) {
				Repay rm = new Repay();
				rm.setPeriod(1);
				rm.setInterest(getRepayInterestByPeriod(loan, 1, loan.getDay()));
				rm.setCorpus(getRepayCorpusByPeriod(loan, 1, loan.getDeadline()));
				rm.setLoan(loan);
				rm.setLoanId(loan.getId());
				rm.setTime(loan.getGiveMoneyTime());
				rm.setStatus(LoanConstants.LoanStatus.REPAYING);
				rm.setRepayDay(DateUtil.addDay(loan.getGiveMoneyTime(),
						loan.getDay() - 1));
				rm.setUserId(loan.getBorrowMoneyUserID());
				rm.setId(RepayHelper.generateRepayId(loan.getId(), 1));
				rm.setRepayAllowanceInterest(loan.getLoanAllowanceInterest());
				repayDao.insert(rm);
			} else {
				// 按月付息到期还本付息
				// 天标
				Date d = loan.getGiveMoneyTime();
				HashMap<Integer, RepayDate> loopMap = null;
				try {
					loopMap = RepayHelper.Loop(d, loan.getDay());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Integer loop = loopMap.keySet().size();
				for (int i = 1; i <= loop; i++) {
					RepayDate repayDate = loopMap.get(i);
					int dayDifference = repayDate.getDayAmount();
					double interest = 0D;
					if (i <= loop - 1) {
						interest = getRepayInterestByPeriodDay(loan,
								dayDifference, loan.getRate());
						saveRepay(loan, i, d, repayDate.getRepayDate(), 0D,
								interest);
						d = DateUtil.addDay(repayDate.getRepayDate(), 1);
					} else {
						Double money = loan.getTotalmoney();
						interest = getRepayInterestByPeriodDay(loan,
								dayDifference, loan.getRate());
						saveRepay(loan, i, d, repayDate.getRepayDate(), money,
								interest);
						loan.setDeadline(loop);
						loanDao.update(loan);
					}
				}
			}
		} else {
			if (loan.getRepayType().equals(LoanConstants.RepayType.DQHBFX)) {
				Repay rm = new Repay();
				rm.setPeriod(1);
				rm.setInterest(getRepayInterestByPeriod(loan, 1,
						loan.getDeadline()));
				rm.setCorpus(getRepayCorpusByPeriod(loan, 1, loan.getDeadline()));
				rm.setLoan(loan);
				rm.setLoanId(loan.getId());
				rm.setTime(loan.getGiveMoneyTime());
				rm.setStatus(LoanConstants.LoanStatus.REPAYING);
				rm.setRepayDay(DateUtil.addMonth(loan.getGiveMoneyTime(),
						loan.getDeadline()));
				rm.setUserId(loan.getBorrowMoneyUserID());
				rm.setId(RepayHelper.generateRepayId(loan.getId(), 1));
				rm.setRepayAllowanceInterest(loan.getLoanAllowanceInterest());
				repayDao.insert(rm);
			} else if (loan.getRepayType().equals(LoanConstants.RepayType.CPM)) {
				for (int i = 1; i <= loan.getDeadline(); i++) {
					Repay rm = new Repay();
					if (i == 1) {
						rm.setRepayAllowanceInterest(loan
								.getLoanAllowanceInterest());
					}
					rm.setPeriod(i);
					rm.setInterest(getRepayInterestByPeriod(loan, i,
							loan.getDeadline()));
					rm.setCorpus(getRepayCorpusByPeriod(loan, i,
							loan.getDeadline()));
					rm.setLoan(loan);
					rm.setLoanId(loan.getId());
					rm.setTime(loan.getGiveMoneyTime());
					rm.setStatus(LoanConstants.LoanStatus.REPAYING);
					rm.setRepayDay(DateUtil.addMonth(loan.getGiveMoneyTime(), i));
					rm.setUserId(loan.getBorrowMoneyUserID());
					rm.setId(RepayHelper.generateRepayId(loan.getId(), i));
					repayDao.insert(rm);
				}

			} else {
				for (int i = 1; i <= loan.getDeadline(); i++) {
					// 产生还款信息
					Repay rm = new Repay();
					if (i == 1) {
						rm.setRepayAllowanceInterest(loan
								.getLoanAllowanceInterest());
					}
					rm.setPeriod(i);
					rm.setInterest(getRepayInterestByPeriod(loan, i,
							loan.getDeadline()));
					rm.setCorpus(getRepayCorpusByPeriod(loan, i,
							loan.getDeadline()));
					rm.setLoan(loan);
					rm.setLoanId(loan.getId());
					rm.setTime(loan.getGiveMoneyTime());
					rm.setStatus(LoanConstants.LoanStatus.REPAYING);
					rm.setRepayDay(DateUtil.addMonth(loan.getGiveMoneyTime(), i));
					rm.setUserId(loan.getBorrowMoneyUserID());
					rm.setId(RepayHelper.generateRepayId(loan.getId(), i));
					repayDao.insert(rm);
				}
			}
		}
	}

	@Override
	@Transactional
	public void saveProjectRepay(Loan loan) {
		if ("天".equals(loan.getOperationType())) {
			// 一次性到期还本付息
			if (loan.getRepayType().equals(LoanConstants.RepayType.DQHBFX)) {		
				saveProjectRepay(loan, 1, DateUtil.addDay(loan.getGiveMoneyTime(),
						loan.getDay() - 1));
			} else {
				// 按月付息到期还本付息
				// 天标		
				HashMap<Integer, RepayDate> loopMap = null;
				try {
					loopMap = RepayHelper.Loop(loan.getGiveMoneyTime(), loan.getDay());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Integer loop = loopMap.keySet().size();
				for (int i = 1; i <= loop; i++) {
					RepayDate repayDate = loopMap.get(i);
					if (i <= loop - 1) {
						saveProjectRepay(loan, i, repayDate.getRepayDate());
					} else {
						saveProjectRepay(loan, i, repayDate.getRepayDate());
						loan.setDeadline(loop);
						loanDao.update(loan);
					}
				}
			}
		} else {
			if (loan.getRepayType().equals(LoanConstants.RepayType.DQHBFX)) {
				saveProjectRepay(
						loan, 1,
						DateUtil.addMonth(loan.getGiveMoneyTime(),
								loan.getDeadline()));
			} else if (loan.getRepayType().equals(LoanConstants.RepayType.CPM)) {
				for (int i = 1; i <= loan.getDeadline(); i++) {
					saveProjectRepay(loan, i,
							DateUtil.addMonth(loan.getGiveMoneyTime(), i));
				}
			} else {
				for (int i = 1; i <= loan.getDeadline(); i++) {
					saveProjectRepay(loan, i,
							DateUtil.addMonth(loan.getGiveMoneyTime(), i));
				}
			}
		}

	}

	@Override
	@Transactional
	public void saveSubLoanRepay(SubLoan subloan) {
		if ("天".equals(subloan.getOperationType())) {
			// 一次性到期还本付息
			if (subloan.getRepayType().equals(LoanConstants.RepayType.DQHBFX)) {
				RepaySubLoan rbs = new RepaySubLoan();
				rbs.setPeriod(1);
				rbs.setInterest(getRepayInterestByPeriod(subloan, 1,
						subloan.getDay()));
				rbs.setCorpus(getRepayCorpusByPeriod(subloan, 1,
						subloan.getDeadline()));
				rbs.setSubloanId(subloan.getSubloanId());
				rbs.setStartTime(subloan.getGiveMoneyTime());
				rbs.setStatus(LoanConstants.LoanStatus.REPAYING);
				rbs.setEndTime(DateUtil.addDay(subloan.getGiveMoneyTime(),
						subloan.getDay() - 1));
				rbs.setUserId(subloan.getBorrowMoneyUserID());
				rbs.setRepaySubLoanId(RepayHelper.generateRepayId(
						subloan.getSubloanId(), 1));
				repaySubloanDao.insert(rbs);
			} else {
				// 按月付息到期还本付息
				// 天标
				Date d = subloan.getGiveMoneyTime();
				HashMap<Integer, RepayDate> loopMap = null;
				try {
					loopMap = RepayHelper.Loop(d, subloan.getDay());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Integer loop = loopMap.keySet().size();
				for (int i = 1; i <= loop; i++) {
					RepayDate repayDate = loopMap.get(i);
					int dayDifference = repayDate.getDayAmount();
					double interest = 0D;
					if (i <= loop - 1) {
						interest = getRepayInterestByPeriodDay(subloan,
								dayDifference, subloan.getRate());
						saveSubloanRepay(subloan, i, d,
								repayDate.getRepayDate(), 0D, interest);
						d = DateUtil.addDay(repayDate.getRepayDate(), 1);
					} else {
						Double money = subloan.getMoney();
						interest = getRepayInterestByPeriodDay(subloan,
								dayDifference, subloan.getRate());
						saveSubloanRepay(subloan, i, d,
								repayDate.getRepayDate(), money, interest);
					}
				}
			}
		} else {
			if (subloan.getRepayType().equals(LoanConstants.RepayType.DQHBFX)) {
				RepaySubLoan rbs = new RepaySubLoan();
				rbs.setPeriod(1);
				rbs.setInterest(getRepayInterestByPeriod(subloan, 1,
						subloan.getDeadline()));
				rbs.setCorpus(getRepayCorpusByPeriod(subloan, 1,
						subloan.getDeadline()));
				rbs.setSubloanId(subloan.getSubloanId());
				rbs.setStartTime(subloan.getGiveMoneyTime());
				rbs.setStatus(LoanConstants.LoanStatus.REPAYING);
				rbs.setEndTime(DateUtil.addMonth(subloan.getGiveMoneyTime(),
						subloan.getDeadline()));
				rbs.setUserId(subloan.getBorrowMoneyUserID());
				rbs.setRepaySubLoanId(RepayHelper.generateRepayId(
						subloan.getSubloanId(), 1));
				repaySubloanDao.insert(rbs);
			} else if (subloan.getRepayType().equals(
					LoanConstants.RepayType.CPM)) {
				for (int i = 1; i <= subloan.getDeadline(); i++) {
					RepaySubLoan rbs = new RepaySubLoan();
					rbs.setPeriod(i);
					rbs.setInterest(getRepayInterestByPeriod(subloan, i,
							subloan.getDeadline()));
					rbs.setCorpus(getRepayCorpusByPeriod(subloan, i,
							subloan.getDeadline()));
					rbs.setSubloanId(subloan.getSubloanId());
					rbs.setStartTime(subloan.getGiveMoneyTime());
					rbs.setStatus(LoanConstants.LoanStatus.REPAYING);
					rbs.setEndTime(DateUtil.addMonth(
							subloan.getGiveMoneyTime(), i));
					rbs.setUserId(subloan.getBorrowMoneyUserID());
					rbs.setRepaySubLoanId(RepayHelper.generateRepayId(
							subloan.getSubloanId(), i));
					repaySubloanDao.insert(rbs);
				}

			} else {
				for (int i = 1; i <= subloan.getDeadline(); i++) {
					RepaySubLoan rbs = new RepaySubLoan();
					rbs.setPeriod(i);
					rbs.setInterest(getRepayInterestByPeriod(subloan, i,
							subloan.getDeadline()));
					rbs.setCorpus(getRepayCorpusByPeriod(subloan, i,
							subloan.getDeadline()));
					rbs.setSubloanId(subloan.getSubloanId());
					rbs.setStartTime(subloan.getGiveMoneyTime());
					rbs.setStatus(LoanConstants.LoanStatus.REPAYING);
					rbs.setEndTime(DateUtil.addMonth(
							subloan.getGiveMoneyTime(), i));
					rbs.setUserId(subloan.getBorrowMoneyUserID());
					rbs.setRepaySubLoanId(RepayHelper.generateRepayId(
							subloan.getSubloanId(), i));
					repaySubloanDao.insert(rbs);
				}
			}
		}

	}

	/**
	 * 获取应还利息
	 * 
	 * @param loan
	 * @param period
	 * @param rate
	 * @return
	 */
	private double getRepayInterestByPeriodDay(Loan loan, int period,
			double rate) {
		List<Invest> invests = loan.getInvests();
		double sumMoney = 0D;
		for (Invest im : invests) {
			sumMoney = ArithUtil.add(sumMoney,
					getDQHBFXInterestByPeriodDay(im.getMoney(), rate, period));
		}
		return sumMoney;
	}

	/**
	 * 获取应还利息
	 * 
	 * @param loan
	 * @param period
	 * @param rate
	 * @return
	 */
	private double getRepayInterestByPeriodDay(SubLoan subloan, int period,
			double rate) {
		List<InvestSubLoan> investSubloans = subloan.getInvestSubloans();
		double sumMoney = 0D;
		for (InvestSubLoan im : investSubloans) {
			sumMoney = ArithUtil.add(sumMoney,
					getDQHBFXInterestByPeriodDay(im.getMoney(), rate, period));
		}
		return sumMoney;
	}

	/**
	 * 
	 * @param loan
	 *            主要获得借款人id和借款项目id
	 * @param i
	 *            当前第几期
	 * @param d
	 *            repay表中的time字段 天标下,某一期的开始时间
	 * @param repayDate
	 *            repay表中的repayDate字段 天标下,某一期的开始时间
	 * @param corpus
	 *            本金
	 * @param interest
	 *            利息
	 */
	private void saveProjectRepay(Loan loan, Integer i, Date repayDate) {
		Repay rm = new Repay();
		if (i == 1) {
			rm.setRepayAllowanceInterest(loan.getLoanAllowanceInterest());
		}
		rm.setId(RepayHelper.generateRepayId(loan.getId(), i));
		rm.setPeriod(i);
		RepaySubLoan repaySubloan = new RepaySubLoan();
		repaySubloan.setPeriod(rm.getPeriod());
		repaySubloan.setLoanId(loan.getId());
		repaySubloan.setRepayId(rm.getId());
		if (loan.getLoanType().equals("车贷")) {
			repaySubloanDao.updateRepaySubloanByVehicle(repaySubloan);
			repaySubloan = repaySubloanDao
					.getSumMoneyRepayVehicle(repaySubloan);
		} else if (loan.getLoanType().equals("金农宝")) {
			repaySubloanDao.updateRepaySubloanByAgriculture(repaySubloan);
			repaySubloan = repaySubloanDao
					.getSumMoneyRepayAgriculture(repaySubloan);
		}
		// 查询子标还款计划本息
		rm.setInterest(repaySubloan.getInterest());
		rm.setCorpus(repaySubloan.getCorpus());
		rm.setLoan(loan);
		rm.setLoanId(loan.getId());
		// 项目开始放款的时候，需要保存该项目既定的还款信息，状态全是"等待还款"
		rm.setStatus(LoanConstants.LoanStatus.REPAYING);
		// 放款日就是还款日
		rm.setRepayDay(repayDate);
		rm.setUserId(loan.getBorrowMoneyUserID());
		rm.setTime(loan.getGiveMoneyTime());
		repayDao.insert(rm);
	}

	/**
	 * 
	 * @param loan
	 *            主要获得借款人id和借款项目id
	 * @param i
	 *            当前第几期
	 * @param d
	 *            repay表中的time字段 天标下,某一期的开始时间
	 * @param repayDate
	 *            repay表中的repayDate字段 天标下,某一期的开始时间
	 * @param corpus
	 *            本金
	 * @param interest
	 *            利息
	 */
	private void saveRepay(Loan loan, Integer i, Date time, Date repayDate,
			Double corpus, Double interest) {
		Repay rm = new Repay();
		if (i == 1) {
			rm.setRepayAllowanceInterest(loan.getLoanAllowanceInterest());
		}
		rm.setPeriod(i);
		// 金额*利率/365*天数
		rm.setInterest(interest);
		rm.setCorpus(corpus);
		rm.setLoan(loan);
		rm.setLoanId(loan.getId());
		// 项目开始放款的时候，需要保存该项目既定的还款信息，状态全是"等待还款"
		rm.setStatus(LoanConstants.LoanStatus.REPAYING);
		// 放款日就是还款日
		rm.setRepayDay(repayDate);
		rm.setUserId(loan.getBorrowMoneyUserID());
		rm.setTime(time);
		rm.setId(RepayHelper.generateRepayId(loan.getId(), i));
		repayDao.insert(rm);
	}

	/**
	 * 
	 * @param loan
	 *            主要获得借款人id和借款项目id
	 * @param i
	 *            当前第几期
	 * @param d
	 *            repay表中的time字段 天标下,某一期的开始时间
	 * @param repayDate
	 *            repay表中的repayDate字段 天标下,某一期的开始时间
	 * @param corpus
	 *            本金
	 * @param interest
	 *            利息
	 */
	private void saveSubloanRepay(SubLoan subloan, Integer i, Date time,
			Date repayDate, Double corpus, Double interest) {
		RepaySubLoan rbs = new RepaySubLoan();
		rbs.setPeriod(i);
		rbs.setInterest(interest);
		rbs.setCorpus(corpus);
		rbs.setSubloanId(subloan.getSubloanId());
		rbs.setStartTime(subloan.getGiveMoneyTime());
		rbs.setStatus(LoanConstants.LoanStatus.REPAYING);
		rbs.setEndTime(DateUtil.addDay(subloan.getGiveMoneyTime(),
				subloan.getDay() - 1));
		rbs.setUserId(subloan.getBorrowMoneyUserID());
		rbs.setRepaySubLoanId(RepayHelper.generateRepayId(
				subloan.getSubloanId(), 1));
	}

	@Override
	public void deleteRepayInvests(String repayId) {
		repayInvestDao.deleteRepayInvests(repayId);
	}

	@Override
	public List<RepayInvest> getRepayInvests(String repayId) {
		return repayInvestDao.getRepayInvests(repayId);
	}
	
	private void createRepayInvestSubLoan(Loan loan,String repayId) throws UserAccountException,TradeException{
		//如果是理财计划，去生成repayInvestSubloan
		if ("project".equals(loan.getStandardOrProject())) {
			List<RepaySubLoan> list = repaySubLoanService
					.getRepaySubLoans(repayId);
			if(CollectionUtils.isEmpty(list)){
				throw new TradeException("未查询到子标的还款计划");
			}
			for (RepaySubLoan repaySubLoan : list) {
				//当状态为初始状态时，生成子标的还款明细
				if (StringUtils.isBlank(repaySubLoan.getHandleStatus())
						||"undeal".equals(repaySubLoan.getHandleStatus())) {
					repaySubLoan = repaySubLoanService.createRepayInvestSubLoan(
							repaySubLoan, loan);
				}
			}
		}
	}

	/**
	 * 根据一期还款计划
	 * 生成还款明细
	 */
	@Override
	public Repay createRepayInvest(Repay repay) throws UserAccountException,
			TradeException {
		//校验还款
		this.verifyInvest(repay);
		Loan loan = repay.getLoan();
		//如果是理财计划，去生成repayInvestSubloan
		this.createRepayInvestSubLoan(loan, repay.getId());
		
		List<Invest> invests = loan.getInvests();
		Double repayCorpus = 0.0;
		Double repayInterest = 0.0;
		//删除原有数据记录
		this.deleteRepayInvests(repay.getId());
		for (Invest invest : invests) {
			RepayInvest repayInvest = new RepayInvest();
			repayInvest.setRepayId(repay.getId());
			repayInvest.setInvestId(invest.getId());
			repayInvest.setInvestUserId(invest.getInvestUserID());
			repayInvest.setInvestAllowanceInterest(0D);
			repayInvest.setRewardMoney(0);
			//计算本金利息
			this.setCorpusAndInterest(repayInvest, repay, invest,
					loan.getStandardOrProject());
			// 判断投资人账户是否存在 
			UserAccount userAccount = userAccountService.getUserAccount(invest
					.getInvestUserID());
			if (userAccount == null) {
				repayInvest.setStatus(-1);//还款时发短信，本地和托管不做资金操作
			} else {
				repayCorpus += repayInvest.getCorpus();
				repayInterest += repayInvest.getInterest();
			}
			repayInvestDao.insert(repayInvest);
		}
		repay.setCorpus(ArithUtil.round(repayCorpus, 2));
		repay.setInterest(ArithUtil.round(repayInterest, 2));
		repay.setHandleStatus("sended");
		repay.setOperationTime(new Date());
		repayDao.update(repay);
		// 融资人的账户，冻结还款资金。
		userAccountService.freeze(loan.getBorrowMoneyUserID(),
				ArithUtil.addRound(repay.getCorpus(), repay.getInterest()),
				BusinessEnum.repay, "借款：" + loan.getName() + "冻结资金", "还款ID："
						+ repay.getId() + "，本金：" + repay.getCorpus() + "，利息："
						+ repay.getInterest(), repay.getId());
		return repay;
	}

	/**
	 * 按散标、理财计划，计算投资人本期应得本金、利息
	 * @param repayInvest
	 * @param repay
	 * @param invest
	 * @param type
	 */
	private void setCorpusAndInterest(RepayInvest repayInvest, Repay repay,
			Invest invest, String type) {
		Double paidInterest = 0D;
		Double paidCorpus = 0D;
		if ("project".equals(type)) {
			/***理财计划计算***/
			// 根据investID，查询repayInvestSubLoan 计算和
			Map<String, Double> map = repaySubLoanService
					.getCorpusAndInterestByInvestId(invest.getId());
			paidCorpus = ArithUtil.round(map.get("corpus"),2);
			paidInterest = ArithUtil.round(map.get("interest"),2);
		} else {
			/**散标计算***/
			Integer deadline = repay.getLoan().getDeadline();
			Integer period = repay.getPeriod();
			String operationType = invest.getLoan().getOperationType();
			String repayType = invest.getLoan().getRepayType();
			Integer day = invest.getLoan().getDay();
			if (StringUtils.equals(operationType, "天")) {
				Integer dayInterval = DateUtil.getIntervalDaysNoABS(
						repay.getTime(), repay.getRepayDay());
				if (LoanConstants.RepayType.DQHBFX.equals(repayType)) {
					dayInterval = day;
				}
				paidInterest = ArithUtil.round(
						getInterest(invest, period, dayInterval), 2);
			} else {
				paidInterest = ArithUtil.round(
						getInterest(invest, period, deadline), 2);
			}
			// 获得该笔投资的本金
			paidCorpus = ArithUtil.round(
					getCorpus(invest, period, deadline,
							repay.getIsBeforeRepay() == 1), 2);
		}
		repayInvest.setCorpus(paidCorpus);
		repayInvest.setInterest(paidInterest);

	}

	/**
	 * 校验还款
	 * 
	 * @param repay
	 * @throws TradeException
	 */
	private void verifyInvest(Repay repay) throws TradeException {
		Loan loan = repay.getLoan();
		if (!"project".equals(loan.getStandardOrProject())
				&& !"standard".equals(loan.getStandardOrProject())) {
			throw new TradeException("还款异常，无法判断散标或计划，项目ID:" + repay.getId());
		}
		List<Invest> invests = loan.getInvests();
		if (invests.isEmpty()
				|| !StringUtils.equals(repay.getStatus(),
						LoanConstants.LoanStatus.REPAYING)) {
			log.errLog("还款异常", this.getClass() + "-->prepare" + "repayid-->"
					+ repay.getId());
			throw new TradeException("还款异常，项目ID:" + repay.getId());
		}
		// 查询上一期是否已还款完成
		if (repay.getPeriod() > 1) {
			Repay repayLast = new Repay();
			repayLast.setPeriod(repay.getPeriod() - 1);
			repayLast.setLoanId(loan.getId());
			repayLast = repayDao.getByCondition(repayLast).get(0);
			if (StringUtils.equals(LoanConstants.LoanStatus.REPAYING,
					repayLast.getStatus())) {
				throw new TradeException("上一期还款还未完成，项目ID:" + loan.getId());
			}
		}
	}

	@Override
	public void updateRepay(Repay repay) {
		repayDao.update(repay);
	}

	@Override
	public void insertRepay(Repay repay) {
		repayDao.insert(repay);
	}

	@Override
	public List<Repay> getByCondition(Repay repay) {
		return repayDao.getByCondition(repay);
	}

}
