package com.duanrong.cps.business.platform.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.cps.business.bsy.model.InvestByBsy;
import com.duanrong.cps.business.fengchelicai.model.FengchelicaiNotice;
import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.platform.dao.PlatformDao;
import com.duanrong.cps.business.platform.model.PlatformUserRelation;
import com.duanrong.cps.business.repay.model.Repay;

@Repository
public class PlatformDaoImpl extends BaseDaoImpl<Loan> implements PlatformDao{

	
	public PlatformDaoImpl(){
		this.setMapperNameSpace("com.duanrong.cps.business.thirdPlatform.mapper.thirdPlatformMapper");
	}
	
	/**
	 * 查询入第三方请求日志
	 */
	@Override
	public void logInsertRequestLog(Map<String, Object> params) {
		
		this.getSqlSession().selectList(this.getMapperNameSpace()+".insertRequestLog", params);
		
	}
	
	
	
	/**
	 * 查询产品信息
	 */
	@Override
	public List<Loan> queryLoan(Map<String,Object>params) {
		
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".queryLoanInfo", params);
	}

	/**
	 * 查询还款表
	 */
	@Override
	public List<Repay> queryRepayInfo(Map<String, Object> params) {
		
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".queryRepayInfo", params);
	}

	/**
	 * 查询用户有关联表
	 */
	@Override
	public List<PlatformUserRelation> queryPlatformInfo(
			Map<String, Object> params) {
		
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".queryPlatformInfo", params);
	}
	/**
	 * 查询投资记录
	 */
	@Override
	public List<Invest> queryInvest(Map<String, Object> params) {
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".queryInvest", params);
	}

	/**
	 * 查询用户的帐户余额
	 */
	@Override
	public Map<String,Object> queryUserEnableAccount(String userId) {
		Map<String,Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("enableMoney", 0);
		map.put("frozen", 0);
		this.getSqlSession().selectOne(this.getMapperNameSpace()+".queryUserEnableAccount", map);
		return map;
	}

	/**
	 * 关联查询投资和还款表
	 */
	@Override
	public List<Invest> queryInvestRepay(Map<String, Object> params) {
		
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".queryInvestRepay", params);
	}

	/**
	 * 查询第三方用户投资信息,分页查询
	 */
	@Override
	public PageInfo<PlatformUserRelation> getPlatformUserInfo(int pageNo,
			int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<PlatformUserRelation> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getPlatformUserInfo",params);
		PageInfo<PlatformUserRelation> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	/**
	 * 查询第三方用户投资信息,分页查询
	 */
	@Override
	public PageInfo<InvestByBsy> getInvestRecords(int pageNo, int pageSize,
			Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<InvestByBsy> list =new ArrayList<InvestByBsy>();
		 list = getSqlSession().selectList(
						getMapperNameSpace() + ".getPlatformInvestInfo",params);
		PageInfo<InvestByBsy> pageInfo = new PageInfo<>(list);
 		return pageInfo;
	}

	/**
	 * 根据user_other_info表中用户来源查询用户信息
	 */
	@Override
	public PageInfo<PlatformUserRelation> queryUserByUserSrouce(int pageNo,
			int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<PlatformUserRelation> list = getSqlSession().selectList(
				getMapperNameSpace() + ".queryUserByUserSrouce",params);
		PageInfo<PlatformUserRelation> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	/**
	 * 根据user_other_info表中用户来源查询投资记录
	 */
	@Override
	public PageInfo<InvestByBsy> queryUserInvestByUserSource(int pageNo,
			int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<InvestByBsy> list = getSqlSession().selectList(
				getMapperNameSpace() + ".queryUserInvestByUserSource",params);
		PageInfo<InvestByBsy> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public List<FengchelicaiNotice> getNotice(String page, String limit) {
		Map<String,Object> param = new HashMap<String,Object>();
		int start = (Integer.valueOf(page)-1)*(Integer.valueOf(limit));
		int pageSize = Integer.valueOf(limit);
		param.put("start", Integer.valueOf(start));
		param.put("pageSize", Integer.valueOf(pageSize));
		return getSqlSession().selectList(
				getMapperNameSpace() + ".getNotice",param);
	}

	@Override
	public double getAwardTotalMoney(String userId) {
		
		return getSqlSession().selectOne(
				getMapperNameSpace() + ".getAwardTotalMoney",userId);
	}

	@Override
	public double getRepayInterestTotalMoney(String userId, String status) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("sendStatus", status);
		return getSqlSession().selectOne(
				getMapperNameSpace() + ".getRepayInterestTotalMoney",params);
	}

	@Override
	public double getRecycledMoney(String userId, String status) {
		Map<String, Object> params = new HashMap<>();
		params.put("investUserID", userId);
		params.put("status", status);
		return getSqlSession().selectOne(
				getMapperNameSpace() + ".getRecycledMoney",params);
	}

	@Override
	public double getDemandAvlidTreasureMoney(String userId) {
		Double demand1 = getSqlSession().selectOne(
				getMapperNameSpace() + ".getDemandAvlidTreasureMoney",userId);
		
		Double demand2 = getSqlSession().selectOne(
				getMapperNameSpace() + ".getDemandAvlidTreasureMoney1",userId);
		return (demand1 - demand2);
		
	}

	@Override
	public double getDemandOutTreasureMoney1(String userId) {
		return getSqlSession().selectOne(
				getMapperNameSpace() + ".getDemandOutTreasureMoney1",userId);
	}

	@Override
	public double getInvestLoanDueInterest(String userId, String string) {
		return getSqlSession().selectOne(
				getMapperNameSpace() + ".getInvestLoanDueInterest",userId);
	}

	@Override
	public double getInvestsTotalInterest(String userId) {
		return getSqlSession().selectOne(
				getMapperNameSpace() + ".getInvestsTotalInterest",userId);
	}

	@Override
	public double getDemandInMoney(String userId) {
		return getSqlSession().selectOne(
				getMapperNameSpace() + ".getDemandInMoney",userId);
	}

	/**
	 * 根据user_other_info表查询投资用户的用户来源
	 */
	@Override
	public List<Invest> getUserInfoInvest(Map<String, Object> param) {
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".getUserInfoInvest", param);
	}

	/**
	 * 查询注册来源为第三方平台，但是没有绑定关系的用户
	 */
	@Override
	public List<Invest> getNoRelationThirdUser(Map<String, Object> param) {
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".getNoRelationThirdUser", param);
	}

	/**
	 * 查询没有绑定关系的第三方用户的投资信息
	 */
	@Override
	public List<Invest> queryInvestUserOtherInfo(Map<String, Object> param) {
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".queryInvestUserOtherInfo", param);
	}

	
	
}
