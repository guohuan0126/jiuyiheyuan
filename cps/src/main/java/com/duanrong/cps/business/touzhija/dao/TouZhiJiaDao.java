package com.duanrong.cps.business.touzhija.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.platform.model.PlatformUserRelation;
import com.duanrong.cps.business.repay.model.Repay;

public interface TouZhiJiaDao {

	/**
	 * 查询产品信息
	 * @param startTime
	 * @param endTime
	 * @param loans
	 * @return
	 */
	public List<Loan> queryLoan(Map<String,Object>params);
	
	/**
	 *  查询还款表
	 * @param params
	 * @return
	 */
	public List<Repay>queryRepayInfo(Map<String,Object>params);
	
	/**
	 * 查询用户有关联表
	 * @param params
	 * @return
	 */
	public List<PlatformUserRelation> queryPlatformInfo(Map<String,Object>params);
	
	/**
	 * 查询投资记录
	 * @param params
	 * @return
	 */
	public List<Invest>queryInvest(Map<String,Object>params);
	
	/**
	 * 查询用户的帐户余额
	 */
	public Map<String,Object> queryUserEnableAccount(String userId);
	
	
	/**
	 * 关联查询投资和还款表
	 * @param params
	 * @return
	 */
	public List<Invest>queryInvestRepay(Map<String,Object>params);
	
	
}
