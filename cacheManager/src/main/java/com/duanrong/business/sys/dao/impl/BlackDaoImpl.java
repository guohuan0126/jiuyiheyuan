package com.duanrong.business.sys.dao.impl;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.sys.dao.BlackDao;
import com.duanrong.business.sys.model.Black;

@Repository
public class BlackDaoImpl extends BaseDaoImpl<Black> implements BlackDao {

	public BlackDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.sys.mapper.BlackMapper");
	}

}
