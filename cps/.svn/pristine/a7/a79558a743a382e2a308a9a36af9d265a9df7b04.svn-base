package com.duanrong.cps.business.touzhija.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.platform.model.PlatformUserRelation;
import com.duanrong.cps.business.repay.model.Repay;
import com.duanrong.cps.business.touzhija.dao.TouZhiJiaDao;

@Repository
public class TouZhiJiaDaoImpl extends BaseDaoImpl<Loan> implements TouZhiJiaDao {

	public TouZhiJiaDaoImpl(){
		this.setMapperNameSpace("com.duanrong.cps.business.thirdPlatform.mapper.thirdPlatformMapper");
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

}
