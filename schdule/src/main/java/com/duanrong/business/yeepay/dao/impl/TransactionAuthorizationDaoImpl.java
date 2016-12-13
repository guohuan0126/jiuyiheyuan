package com.duanrong.business.yeepay.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.yeepay.dao.TransactionAuthorizationDao;
import com.duanrong.business.yeepay.model.TransactionAuthorization;



@Repository
public class TransactionAuthorizationDaoImpl extends
		BaseDaoImpl<TransactionAuthorization> implements
		TransactionAuthorizationDao {

	public TransactionAuthorizationDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.yeepay.mapper.TransactionAuthorizationMapper");
	}

	@Override
	public PageInfo<TransactionAuthorization> pageInfo(int pageNo,
			int pageSize,Map map) {
		PageHelper.startPage(pageNo, pageSize);
		List<TransactionAuthorization> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo",map);
		PageInfo<TransactionAuthorization> pageInfo = new PageInfo<>(list);
		return pageInfo;
	
	}
}
