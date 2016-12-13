package com.duanrong.business.followInvestAward.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.followInvestAward.dao.FollowInvestDao;
import com.duanrong.business.followInvestAward.model.FollowInvestActivity;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-9-1 上午11:29:38
 * @Description : drsoa Maven Webapp com.duanrong.business.invest.dao.impl
 *              InvestDaoImpl.java
 * 
 */
@Repository
public class FollowInvestDaoImpl extends BaseDaoImpl<FollowInvestActivity> implements FollowInvestDao {

	public FollowInvestDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.followInvestAward.model.FollowInvestActivity");
	}

	@Override
	public Date getFollowRechargeTime(String userId, Date endTime) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("endTime", endTime);
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getFollowRechargeTime", map);
	}

	@Override
	public Double getRecharges(String userId, Date registerTime,
			Date addRegisterTime) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("registerTime", registerTime);
		map.put("addRegisterTime", addRegisterTime);
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getRecharges", map);
	}

	@Override
	public Double getWithdrawCashs(String userId, Date registerTime,
			Date addRegisterTime) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("registerTime", registerTime);
		map.put("addRegisterTime", addRegisterTime);
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getWithdrawCashs", map);
	}

	@Override
	public Double getReturnPondByY(String userID) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getReturnPondByY", userID);
	}

	@Override
	public Double getPaidInterest(String userId, Date registerTime,
			Date addRegisterTime) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("registerTime", registerTime);
		map.put("addRegisterTime", addRegisterTime);
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getPaidInterest", map);
	}

	@Override
	public Double getPlatformTransferByUsername(String userId,
			Date registerTime, Date addRegisterTime) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("registerTime", registerTime);
		map.put("addRegisterTime", addRegisterTime);
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getPlatformTransferByUsername", map);
	}
}
