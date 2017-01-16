package com.duanrong.business.token.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.token.dao.AccessTokenDao;
import com.duanrong.business.token.model.AccessToken;

@Repository
public class AccessTokenDaoImpl extends BaseDaoImpl<AccessToken> implements
		AccessTokenDao {

	public AccessTokenDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.token.mapper.TokenMapper");
	}

	@Override
	public Long todayCount(String userId) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".todayCount", userId);
	}

	@Override
	public AccessToken validateToken(Map<String, Object> params) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".validateToken", params);
	}

	@Override
	public void saveTokenDetail(Map<String, Object> params) {
		this.getSqlSession().insert(this.getMapperNameSpace() + ".saveTokenDetail", params);
	}
}
