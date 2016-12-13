package com.duanrong.business.repay.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.Log;
import util.MyCollectionUtils;
import util.MyStringUtils;
import base.exception.InsufficientBalance;
import base.exception.InsufficientBalanceException;
import base.exception.NoOpenAccountException;
import base.exception.RepayException;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.invest.InvestConstants;
import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.loan.LoanConstants;
import com.duanrong.business.loan.dao.LoanDao;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.model.RepayDate;
import com.duanrong.business.loan.service.LoanService;
import com.duanrong.business.platformtransfer.service.PlatformTransferService;
import com.duanrong.business.repay.dao.RepayDao;
import com.duanrong.business.repay.dao.RepayInvestDao;
import com.duanrong.business.repay.model.Repay;
import com.duanrong.business.repay.model.RepayInvest;
import com.duanrong.business.repay.service.RepayService;
import com.duanrong.business.sms.SmsConstants;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.service.InformationService;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.newadmin.controllhelper.RepayHelper;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.InterestUtil;
import com.duanrong.util.jedis.DRJedisDLock;

@Service
public class RepayServiceImpl implements RepayService {

	final Lock lock = new ReentrantLock();

	@Resource
	RepayDao repayDao;

	@Resource
	LoanDao loanDao;

	@Resource
	UserMoneyService userMoneyService;

	@Resource
	UserAccountService userAccountService;

	@Resource
	InvestDao investDao;

	@Resource
	LoanService loanService;

	@Resource
	SmsService smsService;

	@Resource
	InformationService informationService;

	@Resource
	PlatformTransferService platformTransferService;

	@Resource
	TrusteeshipOperationService trusteeshipOperationService;

	@Resource
	Log log;

	@Resource
	RepayInvestDao repayInvestDao;

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
		sum = ArithUtil.round(sum, 2);// TODO 增加小数点取舍判断。
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
		} else if (repayType.equals(LoanConstants.RepayType.CPM)) {
			return InterestUtil.interest(money, rate, deadline, period);
		}
		throw new RuntimeException("未知还款方式：" + repayType);
	}

	/*
	 * @SuppressWarnings("unused") private double
	 * getDQHBFXInterestByPeriod(double money, double rate, int period, int
	 * deadline) { return ArithUtil.round(money * rate / 365 * deadline, 2); }
	 */

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

	public List<Repay> readByCondition(Repay repay) {
		return repayDao.getByCondition(repay);
	}

	/**
	 * 
	 * @description 根据投资ID查询对应的还款信息(该方法暂时适用于还款跟踪(该还款信息中包含(投资人ID,某比投资应得的利息和本金等)))
	 * @author 孙铮
	 * @time 2014-9-16 下午12:22:16
	 * @param invest
	 * @return
	 */
	public List<Repay> getRepaysByInvestId(Invest invest) {
		Loan loan = invest.getLoan();

		// 还款方式
		String repayType = loan.getRepayType();
		// 利率
		Double rate = loan.getRate();
		// 投资金额
		Double money = invest.getMoney();
		// 项目周期
		Integer deadline = loan.getDeadline();
		// 天or月
		String operationType = loan.getOperationType();
		// 共多少天
		Integer day = loan.getDay();

		Repay r = new Repay();
		r.setLoanId(invest.getLoanId());
		// 查处某个项目的还款信息
		List<Repay> repays = readByCondition(r);

		if ("天".equals(operationType)) {
			// 按月付息到期还本
			if (LoanConstants.RepayType.RFCL.equals(repayType)) {
				for (int i = 0; i < repays.size(); i++) {
					Repay repay = repays.get(i);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Integer dayDifference = DateUtil.dayDifference(
							sdf.format(repay.getTime()),
							sdf.format(repay.getRepayDay()));

					repay.setInterest(getRFCLInterestByPeriodDay(money, rate,
							dayDifference));
					if (i == repays.size() - 1) {
						repay.setCorpus(invest.getMoney());
					} else {
						repay.setCorpus(0D);
					}
					repay.setUserId(invest.getInvestUserID());
				}
			} else if (LoanConstants.RepayType.DQHBFX.equals(repayType)) {// 一次性到期还本付息,此处计算利息按照(最低还款期限,例如30+N,就按30天算)
				// 如果本次查询的投资状态为已完成,那么获取的为真实的还款时间
				if (InvestConstants.InvestStatus.COMPLETE.equals(invest
						.getStatus())) {

					for (Repay repay : repays) {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						Integer dayDifference = DateUtil.dayDifference(
								sdf.format(repay.getTime()),
								sdf.format(repay.getRepayDay()));

						repay.setInterest(getDQHBFXInterestByPeriodDay(money,
								rate, dayDifference));
						repay.setCorpus(invest.getMoney());
						repay.setUserId(invest.getInvestUserID());
					}

				} else if (InvestConstants.InvestStatus.REPAYING.equals(invest
						.getStatus())) {
					for (Repay repay : repays) {
						repay.setInterest(getDQHBFXInterestByPeriodDay(money,
								rate, day));
						repay.setCorpus(invest.getMoney());
						repay.setUserId(invest.getInvestUserID());
					}
				}
			}
		} else {
			// 按月付息到期还本
			if (LoanConstants.RepayType.RFCL.equals(repayType)) {
				for (int i = 0; i < repays.size(); i++) {
					Repay repay = repays.get(i);
					repay.setInterest(getRFCLInterestByPeriodMonth(money, rate));
					if (i == repays.size() - 1) {
						repay.setCorpus(invest.getMoney());
					} else {
						repay.setCorpus(0D);
					}
					repay.setUserId(invest.getInvestUserID());
				}
			} else if (LoanConstants.RepayType.DQHBFX.equals(repayType)) {// 一次性到期还本付息,次数计算利息按照真实的还款时间计算
				for (Repay repay : repays) {
					repay.setInterest(getDQHBFXInterestByPeriodMonth(money,
							rate, deadline));
					repay.setCorpus(invest.getMoney());
					repay.setUserId(invest.getInvestUserID());
				}
			}
		}
		return repays;
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
	public Repay read(String id) {
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
			money = ArithUtil.addRound(money,
					getInterest(invest, period, deadline));
		}
		return ArithUtil.round(money, 2);
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
					getCorpus(invest, period, deadline));
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
	public double getCorpus(Invest invest, int period, int deadline) {
		Double money = invest.getMoney();

		if (invest.getLoan().getRepayType()
				.equals(LoanConstants.RepayType.RFCL)) {
			return period == deadline ? money : 0;
		} else if (invest.getLoan().getRepayType()
				.equals(LoanConstants.RepayType.DQHBFX)) {
			return money;
		} else if (invest.getLoan().getRepayType()
				.equals(LoanConstants.RepayType.CPM)) {
			return InterestUtil.corpus(money, invest.getRate(), deadline,
					period);
		}

		return 0;
	}

	/*
	 * public List<Double> repayProcess(String repayId, Loan loan, Integer
	 * deadline, Integer period, Invest invest, Double paidInterest, Double
	 * repayInterest, Double repayCorpus, boolean directTransaction) { Double
	 * paidCorpus = getCorpus(invest, period, deadline); Double money =
	 * ArithUtil.addRound(paidInterest, paidCorpus); Double tempMoney = money;
	 * // 修改已还的本金和利息 Double paidInterestInclueAllowance = paidInterest; if
	 * (period == 1 && directTransaction) { Double investAllowanceInterest =
	 * invest .getInvestAllowanceInterest(); if (investAllowanceInterest !=
	 * null) { paidInterestInclueAllowance = ArithUtil.addRound(paidInterest,
	 * investAllowanceInterest); money = ArithUtil.addRound(money,
	 * investAllowanceInterest); }
	 * 
	 * invest.setPaidInterest(paidInterestInclueAllowance); } else {
	 * invest.setPaidInterest(ArithUtil.addRound(invest.getPaidInterest(),
	 * paidInterest)); }
	 * 
	 * invest.setPaidMoney(ArithUtil.addRound(invest.getPaidMoney(),
	 * paidCorpus)); investDao.update(invest); repayInterest += paidInterest;
	 * repayCorpus += paidCorpus; // 投资人账户
	 * userMoneyService.transferIntoBalance(invest.getInvestUserID(), money,
	 * "项目还款：" + loan.getName(), MyStringUtils.append("还款ID:", repayId,
	 * "  借款ID:", loan.getId(), "  本金：", paidCorpus, "  利息：", paidInterest));
	 * 
	 * List<Double> list = new ArrayList<>(); list.add(tempMoney);
	 * list.add(repayInterest); list.add(repayCorpus); return list; }
	 */

	@Override
	public List<Repay> readRepayPlan(Invest invest) {
		invest = investDao.get(invest.getId());
		if (invest == null) {
			return null;
		}
		String beforeRepay = invest.getLoan().getBeforeRepay();
		if (StringUtils.isBlank(beforeRepay)) {
			beforeRepay = "否";
		}
		List<Repay> repays = getRepaysByInvestId(invest);
		for (Repay repay : repays) {
			repay.setBringForwardRepay(beforeRepay);
		}

		return repays;
	}

	@Override
	public Date readCompleteDate(String loanId) {
		return repayDao.getCompleteDate(loanId);
	}

	@Override
	public void generateRepay(List<Invest> invests, Loan loan) {
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
				rm.setStatus(LoanConstants.RepayStatus.REPAYING);
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
						interest = RepayHelper.getRepayInterestByPeriodDay(
								invests, dayDifference, loan.getRate());
						saveRepay(loan, i, d, repayDate.getRepayDate(), 0D,
								interest);
						d = DateUtil.addDay(repayDate.getRepayDate(), 1);
					} else {
						Double money = loan.getTotalmoney();
						interest = RepayHelper.getRepayInterestByPeriodDay(
								invests, dayDifference, loan.getRate());
						saveRepay(loan, i, d, repayDate.getRepayDate(), money,
								interest);
						loan.setDeadline(loop);
						loanService.update(loan);
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
				rm.setStatus(LoanConstants.RepayStatus.REPAYING);
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
					rm.setStatus(LoanConstants.RepayStatus.REPAYING);
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
					rm.setStatus(LoanConstants.RepayStatus.REPAYING);
					rm.setRepayDay(DateUtil.addMonth(loan.getGiveMoneyTime(), i));
					rm.setUserId(loan.getBorrowMoneyUserID());
					rm.setId(RepayHelper.generateRepayId(loan.getId(), i));
					repayDao.insert(rm);
				}
			}
		}
	}

	/**
	 * 
	 * @description TODO
	 * @author 孙铮
	 * @time 2015-3-20 下午5:13:41
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
		rm.setStatus(LoanConstants.RepayStatus.REPAYING);
		// 放款日就是还款日
		rm.setRepayDay(repayDate);
		rm.setUserId(loan.getBorrowMoneyUserID());
		rm.setTime(time);
		rm.setId(RepayHelper.generateRepayId(loan.getId(), i));
		repayDao.insert(rm);
	}

	@Override
	public void generateRepay(List<Invest> invests, Loan loan, Date date) {
		date = DateUtil.addDay(date, 1);
		if ("天".equals(loan.getOperationType())) {
			// 一次性到期还本付息
			if (loan.getRepayType().equals(LoanConstants.RepayType.DQHBFX)) {
				Repay rm = new Repay();
				rm.setPeriod(1);
				rm.setInterest(getRepayInterestByPeriod(loan, 1, loan.getDay()));
				rm.setCorpus(getRepayCorpusByPeriod(loan, 1, loan.getDeadline()));
				rm.setLoan(loan);
				rm.setLoanId(loan.getId());
				rm.setTime(date);
				rm.setStatus(LoanConstants.RepayStatus.REPAYING);
				rm.setRepayDay(DateUtil.addDay(date, loan.getDay()));
				rm.setUserId(loan.getBorrowMoneyUserID());
				rm.setId(RepayHelper.generateRepayId(loan.getId(), 1));
				rm.setRepayAllowanceInterest(loan.getLoanAllowanceInterest());
				repayDao.insert(rm);
			} else {
				// 按月付息到期还本付息
				// 天标
				Date d = date;
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
						interest = RepayHelper.getRepayInterestByPeriodDay(
								invests, dayDifference, loan.getRate());
						saveRepay(loan, i, d, repayDate.getRepayDate(), 0D,
								interest);
						d = repayDate.getRepayDate();
					} else {
						Double money = loan.getTotalmoney();
						interest = RepayHelper.getRepayInterestByPeriodDay(
								invests, dayDifference, loan.getRate());
						saveRepay(loan, i, d, repayDate.getRepayDate(), money,
								interest);
						loan.setDeadline(loop);
						loanService.update(loan);
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
				rm.setTime(date);
				rm.setStatus(LoanConstants.RepayStatus.REPAYING);
				rm.setRepayDay(DateUtil.addMonth(date, loan.getDeadline()));
				rm.setUserId(loan.getBorrowMoneyUserID());
				rm.setId(RepayHelper.generateRepayId(loan.getId(), 1));
				rm.setRepayAllowanceInterest(loan.getLoanAllowanceInterest());
				repayDao.insert(rm);
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
					rm.setTime(date);
					rm.setStatus(LoanConstants.RepayStatus.REPAYING);
					rm.setRepayDay(DateUtil.addMonth(loan.getGiveMoneyTime(), i));
					rm.setUserId(loan.getBorrowMoneyUserID());
					rm.setId(RepayHelper.generateRepayId(loan.getId(), i));
					repayDao.insert(rm);
				}
			}
		}
	}

	@Override
	public String localRepay(String userId, String loanId, String actionTime,
			String actionMessage, String repayId, String befoRepay)
			throws InsufficientBalance, RepayException {
		if (DRJedisDLock.getDLock("reapy" + repayId, repayId)) {
			try {
				if (StringUtils.isBlank(loanId) || StringUtils.isBlank(repayId)
						&& "否".equals(befoRepay)) {
					return "本地还款失败:时间or是否发送站内信和短信or借款项目idor还款id不正确";
				}
				Loan loan = loanDao.get(loanId);
				if (loan == null) {
					return "要还款的项目不存在";
				}
				if ("是".equals(befoRepay)
						&& !"等额本息".equals(loan.getRepayType())) {
					return "等额本息的才可以提前还款";
				}
				Repay repay = null;
				// 正常还款校验
				if ("否".equals(befoRepay)) {
					repay = repayDao.get(repayId);
					// 查询上一期是否已还款完成
					if (repay.getPeriod() > 1) {
						Repay repayLast = new Repay();
						repayLast.setPeriod(repay.getPeriod() - 1);
						repayLast.setLoanId(loanId);
						repayLast = repayDao.getByCondition(repayLast).get(0);
						if (StringUtils.equals(
								LoanConstants.RepayStatus.REPAYING,
								repayLast.getStatus())) {
							return "本地还款失败:上一期还款还未完成，项目ID:" + loanId;
						}
					}
					// 提前还款查询当前应还期数
				} else {
					repay = repayDao.getCurrentPeriodByLoanId(loanId);
				}

				Date date = new Date();
				if (actionTime != null && ("").equals(actionTime)) {
					date = DateUtil.StringToDate(actionTime);
				}
				// 实际还款的本金和利息
				double repayInterest = 0D;
				double repayCorpus = 0D;

				repay.setStatus(LoanConstants.RepayStatus.COMPLETE);
				repay.setOperationTime(date);

				Invest i = new Invest();
				i.setStatus("!流标");
				i.setLoanId(loanId);
				List<Invest> invests = investDao.getInvestLoan(i);
				loan.setInvests(invests);
				// repay.setLoan(loan);

				RepayInvest repayInvest = new RepayInvest();
				repayInvest.setRepayId(repayId);

				if (MyCollectionUtils.isNotBlank(invests)) {
					// 遍历项目的所有投资人
					for (Invest invest : invests) {
						String investUserID = invest.getInvestUserID();
						repayInvest.setInvestUserId(investUserID);
						repayInvest.setInvestId(invest.getId());
						// 根据条件查询RepayInvest
						repayInvest = repayInvestDao
								.getByCondition(repayInvest);
						Double corpus = repayInvest.getCorpus();
						Double interest = repayInvest.getInterest();
						Double money = ArithUtil.addRound(corpus, interest);

						// 利息 + 补息
						Double interestAddAllowance = interest;
						invest.setPaidInterest(ArithUtil.addRound(
								invest.getPaidInterest(), interest));

						// TODO 提前还款更新预计收益
						double interest2 = 0;
						for (int m = 1; m <= repay.getPeriod(); m++) {
							interest2 += InterestUtil.interest(
									invest.getMoney(), loan.getRate(),
									loan.getDeadline(), m);
						}
						if ("是".equals(befoRepay)) {
							invest.setInterest(ArithUtil.round(interest2
									+ invest.getInvestAllowanceInterest(), 2));
						}

						// 投资表已还本金
						invest.setPaidMoney(corpus);
						investDao.update(invest);

						// 更新投资人账户
						try {
							userAccountService.transferIn(invest
									.getInvestUserID(), money,
									BusinessEnum.repay,
									"项目还款：" + loan.getName(), MyStringUtils
											.append("还款ID:", repayId,
													"  借款ID:", loan.getId(),
													"  本金：", corpus, "  利息：",
													interestAddAllowance),
									repay.getId());
						} catch (NoOpenAccountException e) {
							log.errLog("还款异常",
									"投资人：" + invest.getInvestUserID() + "未开户");
							e.printStackTrace();
						}
						// 发送短信和站内信
						try {
							String content = "虽不常相聚，心却常相系。又有还款啦！您投资的"
									+ loan.getName() + "还款共计" + money
									+ "元，已快马加鞭给您送到了！";
							if ("Y".equals(actionMessage)) {
								smsService.sendSms(investUserID, content,
										SmsConstants.REPAY, date);
								informationService.sendInformation(
										investUserID, "还款通知", content, date);
							}
						} catch (Exception ex) {
							log.errLog("还款短信和站内信发送失败", ex);
						}
						// 计算借款人已还本金利息
						repayCorpus += corpus;
						repayInterest += interest;
					}

				} else {
					log.errLog("本地还款失败", "没有有效投资人");
					return "本地还款失败:没有有效投资人";
				}

				Double payMoney = ArithUtil
						.addRound(repayInterest, repayCorpus);

				// 融资人的账户，扣除还款。
				try {
					userAccountService.transferOut(loan.getBorrowMoneyUserID(),
							payMoney, BusinessEnum.repay,
							"借款：" + loan.getName() + "正常还款",
							MyStringUtils.append("还款ID：", repayId, "，本金：",
									repayCorpus, "，利息：", repayInterest), repay
									.getId());
				} catch (NoOpenAccountException e) {
					log.errLog("还款异常", "借款人：" + loan.getBorrowMoneyUserID()
							+ "未开户");
					e.printStackTrace();
				} catch (InsufficientBalanceException e) {
					log.errLog("还款异常", "借款人：" + loan.getBorrowMoneyUserID()
							+ "余额不足");
					e.printStackTrace();
				}
				if ("是".equals(befoRepay)) {
					repay.setIsBeforeRepay(1);
				}
				repay.setCorpus(repayCorpus);
				repay.setInterest(repayInterest);
				repayDao.update(repay);
				// 提前还款，更新之后的还款计划为已完成，应还利息和本金置0
				if ("是".equals(befoRepay)) {
					Repay repay1 = new Repay();
					loan.setIsBeforeRepay("1");
					repay1.setLoanId(loan.getId());
					repay1.setPeriod(repay.getPeriod());
					repay1.setCorpus(0D);
					repay1.setInterest(0D);
					repay1.setOperationTime(repay.getOperationTime());
					repay1.setStatus("完成");
					repayDao.updateBefoRepay(repay1);
				}
				// 对项目状态和投资状态进行更新
				Repay repay1 = new Repay();
				repay1.setLoanId(loan.getId());
				List<Repay> repays = repayDao.getByCondition(repay1);
				if (MyCollectionUtils.isNotBlank(repays)) {
					for (Repay repayTemp : repays) {
						// 如果有一笔还款状态不是“完成”，则返回false
						if (MyStringUtils.notEquals(repayTemp.getStatus(),
								LoanConstants.RepayStatus.COMPLETE)) {
							return "本地还款成功";
						}
					}
				}
				loan.setStatus(LoanConstants.LoanStatus.COMPLETE);
				loanService.update(loan);
				investDao.updateInvestStatusByLoanId(loan.getId(),
						InvestConstants.InvestStatus.REPAYING,
						InvestConstants.InvestStatus.COMPLETE);
			} finally {
				DRJedisDLock.releaseDLock("reapy" + repayId, repayId);
			}
		}
		return "本地还款成功";
	}

	@Override
	public List<Repay> readRepayByLoan(String loanId) {

		return repayDao.getRepayLoan(loanId);
	}

	@Override
	public void update(Repay repay) {
		repayDao.update(repay);

	}

	@Override
	public Repay readRepayById(String id) {
		return repayDao.getRepayById(id);
	}

	@Override
	public void alertRapay(Repay repay) {
		Loan loan = loanDao.get(repay.getLoanId());
		int day = DateUtil
				.getIntervalDays(repay.getTime(), repay.getRepayDay());
		double interest;
		if (day != -1) {
			interest = getRFCLInterestByPeriodDay(loan.getTotalmoney(),
					loan.getRate(), day);
			repay.setInterest(interest);
		}
		update(repay);
		Repay repay2 = repayDao.getDownRepay(repay);
		if (repay2 != null) {
			repay2.setTime(repay.getRepayDay());
			day = DateUtil.getIntervalDays(repay2.getTime(),
					repay2.getRepayDay());
			if (day != -1) {
				interest = getRFCLInterestByPeriodDay(loan.getTotalmoney(),
						loan.getRate(), day);
				repay2.setInterest(interest);
			}
			update(repay2);
		}
	}

	@Override
	public PageInfo<Repay> readPageInfo(int pageNo, int pageSize, Map map) {
		return repayDao.pageInfo(pageNo, pageSize, map);
	}

	@Override
	public List<Repay> readRepay(Map map) {
		return repayDao.findRepay(map);
	}

	@Override
	public List<RepayInvest> readByReapyId(String id) {
		RepayInvest i = new RepayInvest();
		i.setRepayId(id);
		return repayInvestDao.find(i);
	}

	@Override
	public List<Repay> readRepayList(Date date) {
		return repayDao.getRepayList(date);
	}

	@Override
	public void updateBacthRepayInvest(RepayInvest repayInvest) {
		repayInvestDao.updateBatch(repayInvest);

	}

	@Override
	public void updateByLoanId(Repay repay) {
		repayDao.updateByLoanId(repay);

	}

	@Override
	public Repay readTotalMoney(Map map) {
		// TODO Auto-generated method stub
		return repayDao.getTotalMoney(map);
	}

}
