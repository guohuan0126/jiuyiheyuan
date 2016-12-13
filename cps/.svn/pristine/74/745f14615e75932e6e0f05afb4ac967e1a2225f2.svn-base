package com.duanrong.cps.business.bjs.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.duanrong.cps.business.bjs.model.BjsProductType;
import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.repay.model.Repay;
import com.duanrong.cps.business.user.model.UserBill;

public interface BjsService {
	
	/**
	 * 查询产品信息
	 * @param date
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Loan> queryLoan(Date date, Integer start, Integer limit);
	
	/**
	 * 查询产品总数量
	 * @param params
	 * @return
	 */
	public int queryLoanAccount(Date date);
	
	/**
	 * 记录请求日志信息
	 * @param params
	 */
	public void logInsertBjsRequestLog(Map<String,Object> params);
	
	/**
	 * 查询用户信息
	 */
	public Map<String,Object> queryBjsUserInfo(String BjsUserId);
	
	/**
	 * 查询我们的用户与第三方用户关联关系
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getPlatFormUserRelation(String userId) ;
	
	/**
	 * 查询用户的总收益 
	 * @param userId
	 * @return
	 */
	public Double getUserTotalInterest(String userId);
	
	/**
	 * 用户交易流水查询接口
	 * @return
	 */
	public List<UserBill> queryCapitalFlow(Date date, Integer start,
			Integer limit, String userId);
	
	/**
	 * 用户交易流水个数查询
	 * @return
	 */
	public int queryCapitalFlowAccount(String userId, Date date);
	
	/**
	 * 投资查询接口
	 * @return
	 */
	public List<Invest>queryInvestByUserId(Date date, Integer start,
			Integer limit, String userId);
	
	/**
	 * 查询用户投资的总记录数
	 * @return
	 */
	public int queryInvestAccount(String userId, Date date);
	
	/**
	 * 收益相关查询接口
	 * @return
	 */
	public List<Invest>queryInterest(Date date, Integer start,
			Integer limit, String userId);
	
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
	public int queryInterestAccount(String userId, Date date);

}
