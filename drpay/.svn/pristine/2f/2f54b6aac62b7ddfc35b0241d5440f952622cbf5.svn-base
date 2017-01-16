package com.duanrong.drpay.business.repay.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import util.ArithUtil;
import util.DateUtil;
import base.exception.TradeException;
import base.exception.UserAccountException;

import com.duanrong.drpay.business.account.model.UserAccount;
import com.duanrong.drpay.business.account.service.UserAccountService;
import com.duanrong.drpay.business.invest.model.InvestSubLoan;
import com.duanrong.drpay.business.invest.service.InvestService;
import com.duanrong.drpay.business.loan.LoanConstants;
import com.duanrong.drpay.business.loan.model.Loan;
import com.duanrong.drpay.business.repay.dao.RepayInvestSubLoanDao;
import com.duanrong.drpay.business.repay.dao.RepaySubLoanDao;
import com.duanrong.drpay.business.repay.model.RepayInvestSubLoan;
import com.duanrong.drpay.business.repay.model.RepaySubLoan;
import com.duanrong.drpay.business.repay.service.RepaySubLoanService;
import com.duanrong.drpay.config.IdUtil;
import com.duanrong.drpay.config.ToType;
import com.duanrong.util.InterestUtil;

/**
 * @author JD
 *
 */
@Service
public class RepaySubLoanServiceImpl implements RepaySubLoanService {
	
	@Resource
	RepaySubLoanDao repaySubLoanDao;
	
	@Resource
	RepayInvestSubLoanDao repayInvestSubLoanDao;
	
	@Resource
	UserAccountService userAccountService;
	
	@Resource
	InvestService investService;

	@Override
	public RepaySubLoan get(String repaySubLoanId) {
		return repaySubLoanDao.get(repaySubLoanId);
	}
	
	@Override
	public void update(RepaySubLoan repaySubLoan) {
		repaySubLoanDao.update(repaySubLoan);
	}

	/**
	 * 通过子标的还款记录
	 * 查询自标的投资记录
	 * 生成子标的还款明细
	 */
	@Override
	public RepaySubLoan createRepayInvestSubLoan(RepaySubLoan repaySubLoan,Loan loan) throws UserAccountException, TradeException{
		//查询自标的投资记录
		List<InvestSubLoan> investSubLoans = investService.getInvestSubLoans(repaySubLoan.getSubloanId());
		if(CollectionUtils.isEmpty(investSubLoans)){
			throw new TradeException("未查询到子标投资记录");
		}
		Double repayCorpus = 0.0;
		Double repayInterest = 0.0;
		//删除原有记录
		this.repayInvestSubLoanDao.deleteRepayInvestSubLoans(repaySubLoan.getRepaySubLoanId());
		for (InvestSubLoan investSubLoan : investSubLoans) {
			Double paidInterest = 0D;
			Double paidCorpus	= 0D;	
			// 计息方式（按天或按月）
			Integer deadline =loan.getDeadline();
			Integer period = repaySubLoan.getPeriod();
			String operationType = loan.getOperationType();
			String repayType = loan.getRepayType();
			Integer day = loan.getDay();
			if (StringUtils.equals(operationType, "天")) {
				Integer dayInterval = DateUtil.getIntervalDaysNoABS(
						repaySubLoan.getStartTime(), repaySubLoan.getEndTime());
				if (LoanConstants.RepayType.DQHBFX.equals(repayType)) {
					dayInterval = day;
				}
				paidInterest =ArithUtil.round( getInterest(investSubLoan,loan, period, dayInterval),2);
			} else {
				paidInterest = ArithUtil.round(getInterest(investSubLoan, loan,period, deadline),2);
			}
			// 获得该笔投资的本金
			paidCorpus = ArithUtil.round(getCorpus(investSubLoan,loan, period, deadline, repaySubLoan.getIsBeforeRepay()==1),2);
			
			RepayInvestSubLoan repayInvestSubLoan = new RepayInvestSubLoan();
			
			repayInvestSubLoan.setRepayInvestSubLoanId(IdUtil.generateId(ToType.PLMX));
			repayInvestSubLoan.setRepaySubLoanId(repaySubLoan.getRepaySubLoanId());
			repayInvestSubLoan.setSubLoanId(investSubLoan.getSubloanId());
			repayInvestSubLoan.setCorpus(paidCorpus);
			repayInvestSubLoan.setInterest(paidInterest);
			repayInvestSubLoan.setUserId(investSubLoan.getUserId());
			repayInvestSubLoan.setInvestId(investSubLoan.getInvestId());
			//判断投资人账户是否存在 user_account
			UserAccount userAccount = userAccountService.getUserAccount(investSubLoan.getUserId());
			if(userAccount==null){
				repayInvestSubLoan.setStatus(-1);
			}else{
				repayCorpus += paidCorpus;
				repayInterest +=paidInterest;
			}
			repayInvestSubLoanDao.insert(repayInvestSubLoan);
		}
		repaySubLoan.setCorpus(ArithUtil.round(repayCorpus,2));
		repaySubLoan.setInterest(ArithUtil.round(repayInterest,2));
		repaySubLoan.setHandleStatus("sended");
		repaySubLoan.setOperationTime(new Date());
		repaySubLoanDao.update(repaySubLoan);
		return repaySubLoan;
	}

	@Override
	public List<RepayInvestSubLoan> getRepayInvestSubLoans(String repaySubloanId) {
		return repayInvestSubLoanDao.getRepayInvestSubLoans(repaySubloanId);
	}

	@Override
	public List<RepaySubLoan> getRepaySubLoans(String repayId) {
		return repaySubLoanDao.getRepaySubLoans(repayId);
	}

	/**
	 * 获得利息
	 * 
	 * @param invest
	 * @param period
	 * @param deadline
	 * @return
	 */
	public double getInterest(InvestSubLoan investSubLoan,Loan loan, int period, int deadline) {
		Double money = investSubLoan.getMoney();
		Double rate = loan.getRate();

		// 按月付息到期还本
		if (StringUtils.equals(loan.getRepayType(),
				LoanConstants.RepayType.RFCL)) {
			if ("天".equals(loan.getOperationType())) {
				return getRFCLInterestByPeriodDay(money, rate, deadline);
			} else {
				return getRFCLInterestByPeriod(loan.getType(),
						investSubLoan.getInvestTime(), loan.getGiveMoneyTime(),
						money, rate, period, deadline);
			}
		}

		// 一次性到期还本付息
		if (loan.getRepayType().equals(LoanConstants.RepayType.DQHBFX)) {
			if ("天".equals(loan.getOperationType())) {
				return getDQHBFXInterestByPeriodDay(money, rate, deadline);
			} else {
				return getDQHBFXInterestByPeriod(money,
						rate, loan.getDeadline());
			}
		}

		if (loan.getRepayType().equals(LoanConstants.RepayType.CPM)) {
			return InterestUtil.interest(money, rate, deadline, period);
		}
		return 0;
	}
	
	public double getRFCLInterestByPeriodDay(double money, double rate,
			int period) {
		double interest = ArithUtil.round(money * rate / 365 * period, 2);
		return interest;
	}
	private double getRFCLInterestByPeriod(String loanType, Date investTime,
			Date giveMoneyTime, double money, double rate, int period,
			int deadline) {
		return ArithUtil.round(money * rate / 12, 2);

	}
	public double getDQHBFXInterestByPeriodDay(double money, double rate,
			int dayTotal) {
		double interest = ArithUtil.round(money * rate / 365 * dayTotal, 2);
		return interest;
	}
	public double getDQHBFXInterestByPeriod(double money, double rate, int month) {
		double interest = ArithUtil.round(money * rate / 12 * month, 2);
		return interest;
	}
	
	/**
	 * 获得本金
	 * 
	 * @param invest
	 * @param period
	 * @param deadline
	 * @return
	 */
	public double getCorpus(InvestSubLoan investSubLoan,Loan loan, int period, int deadline,
			boolean isBeforeRepay) {
		Double money = investSubLoan.getMoney();

		if (loan.getRepayType()
				.equals(LoanConstants.RepayType.RFCL)) {
			return period == deadline ? money : 0;
		} else if (loan.getRepayType()
				.equals(LoanConstants.RepayType.DQHBFX)) {
			return money;
		} else if (loan.getRepayType()
				.equals(LoanConstants.RepayType.CPM)) {
			if (isBeforeRepay) {
				return InterestUtil.corpus(money, loan.getRate(), deadline,
						period)
						+ InterestUtil.surplusCorpus(money, loan.getRate(),
								deadline, period);
			}
			return InterestUtil.corpus(money, loan.getRate(), deadline,
					period);
		}

		return 0;
	}

	@Override
	public Map<String, Double> getCorpusAndInterestByInvestId(String investId) {
		return repayInvestSubLoanDao.getCorpusAndInterestByInvestId(investId);
	}
	
	@Override
	public List<RepaySubLoan> getRepaySubLoansBySubloanId(String subloanId) {
		return repaySubLoanDao.getRepaySubLoansBySubloanId(subloanId);
	}
	
	/**
	 * 查询子标还款计划数量
	 * @param repaySubloan
	 * @return
	 */
	@Override
	public int getSumRepayAgriculture(String loanId){
		return repaySubLoanDao.getSumRepayAgriculture(loanId);
	}
	
	/**
	 * 更新子标还款计划 repayId
	 * @param repaySubloan
	 */
	@Override
	public int getSumRepayVehicle(String loanId){
		return repaySubLoanDao.getSumRepayVehicle(loanId);
	}


	@Override
	public RepayInvestSubLoan getRepayInvestSubLoan(String repayInvestSubLoanId) {
		return repayInvestSubLoanDao.getRepayInvestSubLoan(repayInvestSubLoanId);
	}

	@Override
	public void updateRepayInvestSubLoan(RepayInvestSubLoan repayInvestSubLoan) {
		repayInvestSubLoanDao.update(repayInvestSubLoan);
	}

	@Override
	public void updateRepaySubLoan(RepaySubLoan repaySubLoan) {
		repaySubLoanDao.update(repaySubLoan);		
	}

	@Override
	public void insertRepayInvestSubLoan(RepayInvestSubLoan repayInvestSubLoan) {
		repayInvestSubLoanDao.insert(repayInvestSubLoan);
	}
}
