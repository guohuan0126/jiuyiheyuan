package com.duanrong.business.user.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.user.dao.UserLoginLogDao;
import com.duanrong.business.user.model.UserLoginLog;

@Repository
public class UserLoginLogDaoImpl extends BaseDaoImpl<UserLoginLog> implements
		UserLoginLogDao {
	public UserLoginLogDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.user.mapper.UserLoginLogMapper"); // 设置命名空间
	}

	@Override
	public UserLoginLog getByUserId(String userId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getByUserId", userId);
	}

	@Override
	public List<Date> getLastLoginTime(String userId) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getLastLoginTime", userId);
	}

}
