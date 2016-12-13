package com.duanrong.cps.business.platform.dao;

import java.util.List;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.cps.business.bsy.model.InvestByBsy;
import com.duanrong.cps.business.fengchelicai.model.FengchelicaiNotice;
import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.platform.model.PlatformUserRelation;
import com.duanrong.cps.business.repay.model.Repay;

public interface PlatformDao {
	
	/**
	 * 查询入第三方请求日志
	 * @param params
	 */
	public void logInsertRequestLog(Map<String, Object> params);
	
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
	
	
	/**
	 * 查询第三方用户投资信息,分页查询
	 */
	public PageInfo<PlatformUserRelation> getPlatformUserInfo(int pageNo, int pageSize, Map<String, Object> params);
	
	/**
	 * 查询第三方用户投资信息,分页查询
	 */
	public PageInfo<InvestByBsy> getInvestRecords(int pageNo, int pageSize, Map<String, Object> params);
	
	/**
	 * 根据user_other_info表中用户来源查询用户信息
	 */
	public PageInfo<PlatformUserRelation> queryUserByUserSrouce(int pageNo, int pageSize,Map<String,Object>params);
	
	/**
	 * 根据user_other_info表中用户来源查询投资记录
	 */
	public PageInfo<InvestByBsy> queryUserInvestByUserSource(int pageNo, int pageSize, Map<String, Object> params);
	
	/**
	 * 根据user_other_info表查询投资用户的用户来源
	 * @param param
	 * @return
	 */
	public List<Invest>getUserInfoInvest(Map<String,Object>param);
	
	/**
	 * 查询注册来源为第三方平台，但是没有绑定关系的用户
	 * @param param
	 * @return
	 */
	public List<Invest>getNoRelationThirdUser(Map<String,Object>param);
	
	/**
	 * 查询没有绑定关系的第三方用户的投资信息
	 */
	public List<Invest>queryInvestUserOtherInfo(Map<String,Object>param);

	public List<FengchelicaiNotice> getNotice(String page, String limit);

	/**
	 * 获取用户所有奖励
	 * @param userId
	 * @return
	 */
	public double getAwardTotalMoney(String userId);

	public double getRepayInterestTotalMoney(String userId, String string);
 //3当前投资金额
	public double getRecycledMoney(String userId, String status);

	public double getDemandAvlidTreasureMoney(String userId);

	public double getDemandOutTreasureMoney1(String userId);

	public double getInvestLoanDueInterest(String userId, String string);

	public double getInvestsTotalInterest(String userId);

	public double getDemandInMoney(String userId);
	
	
}
