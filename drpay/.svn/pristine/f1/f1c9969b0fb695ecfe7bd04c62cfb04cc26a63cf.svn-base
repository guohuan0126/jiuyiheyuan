package com.duanrong.drpay.business.invest.dao;

import java.util.List;

import base.dao.BaseDao;

import com.duanrong.drpay.business.invest.model.Invest;
import com.duanrong.drpay.business.invest.model.InvestRedpacket;
import com.duanrong.drpay.business.invest.model.InvestSubLoan;

public interface InvestDao extends BaseDao<Invest> {

	/**
	 * 查询项目募集金额
	 * @param loanId
	 * @return
	 */
	public Double getInvestSumMoneyByLoan(String loanId);

	public Double getInvestSumMoneyByUser(String userId, String loanId);

	public List<Invest> getInvestLoan(Invest invest);
	

	/**
	 * 创建补息奖励记录
	 * @param investRedpacket
	 */
	void insertInvestRedpacket(InvestRedpacket investRedpacket);

	/**
	 * 根据项目id查询投资成功的的数量
	 * @param loanId
	 * @return
	 */
	int getInvestSeccessByLoanId(String loanId);

	long getInvestSuccessNotNewRecordByUserId(String userId);
	
	/**
	 * 批量插入理财计划子标投资记录
	 * @param investSubLoans
	 */
	void insertBatchInvestSubloan(List<InvestSubLoan> investSubLoans);
	
	/**
	 * 插入理财计划子标投资记录
	 * @param investSubLoans
	 */
	void insertInvestSubloan(InvestSubLoan investSubLoan);
	
	/**
	 * 批量插入理财计划子标投资记录
	 * @param investSubLoans
	 */
	double getInvestSubloanMoneyByInvestId(String investId);
	
	/**
	 * 批量插入理财计划子标投资记录
	 * @param investSubLoans
	 */
	List<InvestSubLoan> getInvestSubloanByInvestId(String investId);

	List<InvestSubLoan> getInvestSubLoans(String subloanId);
	
	InvestSubLoan getInvestSubLoan(String investSubloanId);
	
	/**
	 * 更新 InvestSubLoan
	 * @param investSubLoan
	 */
	void updateInvestSubloan(InvestSubLoan investSubLoan);

	public Long getInvestSuccessRecordByUserId(String userId);

	public void updateInvestSubloanByInvestId(String investId);
}