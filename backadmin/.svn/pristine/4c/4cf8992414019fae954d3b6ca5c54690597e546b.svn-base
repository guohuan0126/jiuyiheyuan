package com.duanrong.business.risk.dao.impl;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.risk.dao.SystemBillDao;
import com.duanrong.business.risk.model.SystemBill;

@Repository
public class SystemBillDaoImpl extends BaseDaoImpl<SystemBill> implements
		SystemBillDao {

	public SystemBillDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.risk.mapper.SystemBillMapper");
	}

	@Override
	public SystemBill getLastestBill() {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getLastestBill");
	}

	@Override
	public Double getSumByType(String type) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getSumByType", type);
	}

}
