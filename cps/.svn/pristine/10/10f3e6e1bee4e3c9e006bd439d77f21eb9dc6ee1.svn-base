package com.duanrong.cps.business.platform.service;

import java.util.List;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.cps.business.bsy.model.InvestByBsy;
import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.platform.model.DemandBill;
import com.duanrong.cps.business.platform.model.PlatformUserRelation;

public interface PlatformService {
	
	/**
	 * 记录请求日志信息
	 * @param params
	 */
	public void logInsertRequestLog(Map<String,Object> params);
	
	/**
	 * 查询第三方平台用户信息
	 */
	PageInfo<PlatformUserRelation> getPlatformUserInfo(int parseInt, int parseInt2, Map<String, Object> params);
	
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
	 * 查询用户活期流水记录
	 */
	public PageInfo<DemandBill> queryDemandBill(int pageNo,int pageSize, Map<String, Object> params) ;
	
	/**
	 * 查询推送到第三方平台的标的
	 */
	public PageInfo<Loan> getLoanHistory(int pageNo,int pageSize, Map<String, Object> params) ;
	
	/**
	 * 根据user_other_info表查询投资用户的用户来源
	 * @param param
	 * @return
	 */
	public List<Invest> getUserInfoInvest(Map<String, Object> param);
	
	/**
	 * 查询注册来源为第三方平台，但是没有绑定关系的用户
	 */
	public List<Invest> getNoRelationThirdUser(Map<String, Object> param);
	
	/**
	 * 查询没有绑定关系的第三方用户的投资信息
	 */
	public List<Invest> queryInvestUserOtherInfo(Map<String, Object> param);
}
