package com.duanrong.business.token.dao.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.duanrong.business.token.dao.AccessTokenDao;
import com.duanrong.business.token.model.AccessToken;

@Repository
public class AccessTokenDaoImpl extends SqlSessionDaoSupport implements AccessTokenDao  {

	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	@Override
	public AccessToken getTokenByID(Serializable id) {
		return this.getSqlSession().selectOne(
				"com.duanrong.business.token.mapper.AccessTokenMapper."
						+ "getTokenByID", id);
	}

	@Override
	public void insert(AccessToken accessToken) {
		this.getSqlSession().insert(
				"com.duanrong.business.token.mapper.AccessTokenMapper."
						+ "insert", accessToken);
	}
}
