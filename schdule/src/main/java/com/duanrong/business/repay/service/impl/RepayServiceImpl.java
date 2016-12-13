package com.duanrong.business.repay.service.impl;

import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.loan.LoanConstants;
import com.duanrong.business.loan.dao.LoanDao;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.model.RepayDate;
import com.duanrong.business.loan.service.LoanService;
import com.duanrong.business.platformtransfer.service.PlatformTransferService;
import com.duanrong.business.repay.dao.RepayDao;
import com.duanrong.business.repay.model.Repay;
import com.duanrong.business.repay.model.RepayMail;
import com.duanrong.business.repay.service.RepayService;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.service.InformationService;
import com.duanrong.newadmin.controllhelper.RepayHelper;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.InterestUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.Log;

import javax.annotation.Resource;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class RepayServiceImpl implements RepayService {

	final Lock lock = new ReentrantLock();

	@Resource
	RepayDao repayDao;
	@Resource
	LoanDao loanDao;
	
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
		}
		throw new RuntimeException("未知还款方式：" + repayType);
	}


	@Override
	public void updateByLoanId(Repay repay) {
		repayDao.updateByLoanId(repay);
		
	}


	@SuppressWarnings("unused")
	private double getDQHBFXInterestByPeriod(double money, double rate,
			int period, int deadline) {
		return ArithUtil.round(money * rate / 365 * deadline, 2);
	}

	/**
	 * 获取先息后本还款某一期应得的利息 constant payment mortgage（CPM）
	 * 
	 * @param money
	 *            投资金额
	 * @param rate
	 *            投资利率
	 */
	private double getRFCLInterestByPeriod( double money, double rate) {		
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
				return getRFCLInterestByPeriod(invest.getMoney(), invest.getRate());
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
		if(loan.getRepayType().equals(LoanConstants.RepayType.CPM)) {
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
		}else if(invest.getLoan().getRepayType()
				.equals(LoanConstants.RepayType.CPM)) {
			return InterestUtil.corpus(money, invest.getRate(), deadline, period);
		}

		return 0;
	}


	/*public List<Double> repayProcess(String repayId, Loan loan,
			Integer deadline, Integer period, Invest invest,
			Double paidInterest, Double repayInterest, Double repayCorpus,
			boolean directTransaction) {
		Double paidCorpus = getCorpus(invest, period, deadline);
		Double money = ArithUtil.addRound(paidInterest, paidCorpus);
		Double tempMoney = money;
		// 修改已还的本金和利息
		Double paidInterestInclueAllowance = paidInterest;
		if (period == 1 && directTransaction) {
			Double investAllowanceInterest = invest
					.getInvestAllowanceInterest();
			if (investAllowanceInterest != null) {
				paidInterestInclueAllowance = ArithUtil.addRound(paidInterest,
						investAllowanceInterest);
				money = ArithUtil.addRound(money, investAllowanceInterest);
			}

			invest.setPaidInterest(paidInterestInclueAllowance);
		} else {
			invest.setPaidInterest(ArithUtil.addRound(invest.getPaidInterest(),
					paidInterest));
		}

		invest.setPaidMoney(ArithUtil.addRound(invest.getPaidMoney(),
				paidCorpus));
		investDao.update(invest);
		repayInterest += paidInterest;
		repayCorpus += paidCorpus;
		// 投资人账户
		userMoneyService.transferIntoBalance(invest.getInvestUserID(), money,
				"项目还款：" + loan.getName(), MyStringUtils.append("还款ID:",
						repayId, "  借款ID:", loan.getId(), "  本金：", paidCorpus,
						"  利息：", paidInterest));

		List<Double> list = new ArrayList<>();
		list.add(tempMoney);
		list.add(repayInterest);
		list.add(repayCorpus);
		return list;
	}
*/
	

	@Override
	public void generateRepay(Loan loan) {
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
								loan.getInvests(), dayDifference, loan.getRate());
						saveRepay(loan, i, d, repayDate.getRepayDate(), 0D,
								interest);
						d = DateUtil.addDay(repayDate.getRepayDate(), 1);
					} else {
						Double money = loan.getTotalmoney();						
						interest = RepayHelper.getRepayInterestByPeriodDay(
								loan.getInvests(), dayDifference, loan.getRate());
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
			}else if(loan.getRepayType().equals(LoanConstants.RepayType.CPM)){
				for (int i = 1; i <= loan.getDeadline(); i++) {
			        Repay rm = new Repay();
					if (i == 1) {
						rm.setRepayAllowanceInterest(loan
								.getLoanAllowanceInterest());
					}
					rm.setPeriod(i);
					rm.setInterest(getRepayInterestByPeriod(loan, i,loan.getDeadline()));
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
				
			}else {
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
	public List<Repay> getRepayByLoan(String loanId) {

		return repayDao.getRepayLoan(loanId);
	}

	@Override
	public void update(Repay repay) {
		repayDao.update(repay);

	}

	@Override
	public Repay getRepayById(String id) {
		return repayDao.getRepayById(id);
	}
	
	@Override
	public List<Repay> getRepayList(Date date) {
		return repayDao.getRepayList(date);
	}	
	
	@Override
	public List<RepayMail> getInterval(int days) {
		
		return repayDao.getInterval(days);
	}

	@Override
	public List<RepayMail> getRepayInterval(String userId, int days) {
		return repayDao.getRepayInterval(userId, days);
	}

	@Override
	public List<RepayMail> getRepayFinishInterval(int days) {
		
		return repayDao.getRepayFinishInterval(days);
	}

}