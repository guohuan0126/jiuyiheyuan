package com.duanrong.drpay.business.invest.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import base.exception.AccountException;
import base.exception.TradeException;
import base.exception.UserAccountException;

import com.duanrong.drpay.business.invest.model.Invest;
import com.duanrong.drpay.business.invest.model.InvestSubLoan;

public interface InvestService {

	/**
	 * 校验Invest
	 * @param invest
	 * @throws TradeException,AccountException 
	 */
	public void verifyInvest(Invest invest) throws TradeException, UserAccountException;

	/**
	 * 创建Invest
	 * @param invest
	 * @throws TradeException,AccountException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public void createInvest(Invest invest) throws TradeException, UserAccountException;
	/**
	 * 投资流标
	 * @param invest
	 * @throws TradeException
	 * @throws AccountException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void failInvest(Invest invest) throws TradeException, AccountException;

	/**
	 * 根据ID查询投资记录
	 * @param id
	 * @return
	 */
	public Invest get(String id);

	/**
	 * 更新投资记录
	 * @param invest
	 */
	public void update(Invest invest);

	/**
	 * 插入投资记录
	 * @param invest
	 */
	public void insert(Invest invest);
	/**
	 * 查询项目投资成功的数量
	 * @param loanId
	 * @return
	 */
	int getInvestSeccessByLoanId(String loanId);
	
	
	List<Invest> getInvestLoan(Invest invest);
	

	double getInvestSumMoneyByLoan(String loanId);

	
	double getInvestSumMoneyByUser(String userId, String loanId);
	
	long getInvestSuccessNotNewRecordByUserId(String userId);

	/**
	 * 根据用户ID查询状态不为流标的记录数
	 * 
	 * @param userId 用户ID
	 * 
	 * @return
	 */
	public Long getInvestSuccessRecordByUserId(String userId);
	
	
	/**
	 * 批量插入理财计划子标投资记录
	 * @param investSubLoans
	 */
	void insertBatchInvestSubloan(List<InvestSubLoan> investSubLoans);

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


	public List<InvestSubLoan> getInvestSubLoans(String subloanId);
	
	InvestSubLoan getInvestSubloan(String investSubloanId);

	/**
	 * 插入理财计划子标投资记录
	 * @param investSubLoans
	 */
	void insertInvestSubloan(InvestSubLoan investSubLoan);
	
	/**
	 * 更新 InvestSubLoan
	 * @param investSubLoan
	 */
	void updateInvestSubloan(InvestSubLoan investSubLoan);

}

