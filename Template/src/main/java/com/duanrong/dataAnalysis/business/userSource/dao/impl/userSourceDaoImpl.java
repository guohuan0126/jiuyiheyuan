package com.duanrong.dataAnalysis.business.userSource.dao.impl;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.dataAnalysis.business.userSource.dao.userSourceDao;
import com.duanrong.dataAnalysis.business.userSource.model.UserSource;

@Repository
public class userSourceDaoImpl extends BaseDaoImpl<UserSource> implements userSourceDao {

	public  userSourceDaoImpl() {
		this.setMapperNameSpace("com.duanrong.dataAnalysis.business.userSource.mapper.userSourceMapper"); // 设置命名空间
	}

	@Override
	public UserSource getUserCount() {
		
		return this.getSqlSession().selectOne("getUserCount");
	}

	
}
