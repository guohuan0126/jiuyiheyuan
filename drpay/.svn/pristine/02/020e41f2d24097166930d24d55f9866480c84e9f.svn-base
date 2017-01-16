package com.duanrong.drpay.business.repay.service;

import java.util.List;
import java.util.Map;

import base.exception.TradeException;
import base.exception.UserAccountException;

import com.duanrong.drpay.business.loan.model.Loan;
import com.duanrong.drpay.business.repay.model.RepayInvestSubLoan;
import com.duanrong.drpay.business.repay.model.RepaySubLoan;

public interface RepaySubLoanService {
	
	RepaySubLoan get(String repaySubLoanId);
	
	void update(RepaySubLoan repaySubLoan);

	RepaySubLoan createRepayInvestSubLoan(RepaySubLoan repaySubLoan,Loan loan)throws UserAccountException,TradeException;

	/**
	 * 根据还款ID 查询子标的还款记录
	 * @param repayId
	 * @return
	 */
	List<RepaySubLoan> getRepaySubLoans(String repayId);
	
	/**
	 * 查询子标还款计划
	 * @param subloanId
	 * @return
	 */
	List<RepaySubLoan> getRepaySubLoansBySubloanId(String subloanId);
	
	List<RepayInvestSubLoan> getRepayInvestSubLoans(String repaySubloanId);

	Map<String, Double> getCorpusAndInterestByInvestId(String investId);
	
	/**
	 * 查询子标还款计划数量
	 * @param repaySubloan
	 * @return
	 */
	int getSumRepayAgriculture(String loanId);
	
	/**
	 * 查询子标还款计划数量
	 * @param repaySubloan
	 * @return
	 */
	int getSumRepayVehicle(String loanId);
	
	RepayInvestSubLoan getRepayInvestSubLoan(String repayInvestSubLoanId);

	void updateRepayInvestSubLoan(RepayInvestSubLoan repayInvestSubLoan);

	void updateRepaySubLoan(RepaySubLoan repaySubLoan);

	void insertRepayInvestSubLoan(RepayInvestSubLoan repayInvestSubLoan);

}
