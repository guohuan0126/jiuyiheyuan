package com.duanrong.cps.business.bjs.dao;

import java.util.List;
import java.util.Map;

import com.duanrong.cps.business.bjs.model.BjsProductType;
import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.repay.model.Repay;
import com.duanrong.cps.business.user.model.UserBill;

public interface BjsDao {
	
	/**
	 *查询产品信息
	 * @param parms
	 * @return
	 */
	public List<Loan> queryLoan(Map<String,Object>parms);
	
	public int queryLoanAccount(Map<String,Object>params);
	
	public void logInsertBjsRequestLog(Map<String, Object> params);
	
	public Map<String,Object> queryBjsUserInfo(String params);
	
	public Map<String,Object>getPlatFormUserRelation(String userId);
	
	/**
	 * 查询用户的总收益
	 * @param userId
	 * @return
	 */
	public Double getUserTotalInterest(String userId);
	
	/**
	 * 用户交易流水查询接口
	 * @param param
	 * @return
	 */
	public  List<UserBill> queryCapitalFlow(Map<String,Object>param);
	
	/**
	 * 用户交易流水个数查询
	 * @param param
	 * @return
	 */
	public int queryCapitalFlowAccount(Map<String,Object>param);
	
	/**
	 * 投资查询接口
	 * @param param
	 * @return
	 */
	public List<Invest>queryInvestByUserId(Map<String,Object>param);
	
	/**
	 * 查询用户投资的总记录数
	 * @param param
	 * @return
	 */
	public int queryInvestAccount(Map<String,Object>param);
	
	/**
	 * 收益相关查询接口
	 * @param param
	 * @return
	 */
	public List<Invest>queryInterest(Map<String,Object>param);
	
	/**
	 * 查询还款表
	 * @param param
	 * @return
	 */
	public List<Repay>queryRepay(String loanId);
	
	/**
	 * 查询收益的总记录数
	 * @param params
	 * @return
	 */
	public int queryInterestAccount(Map<String,Object>params);
}
