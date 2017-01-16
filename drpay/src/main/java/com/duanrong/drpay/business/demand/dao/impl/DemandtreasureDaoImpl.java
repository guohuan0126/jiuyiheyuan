package com.duanrong.drpay.business.demand.dao.impl;

import org.springframework.stereotype.Repository;

import com.duanrong.drpay.business.demand.dao.DemandtreasureDao;
import com.duanrong.drpay.business.demand.model.Demandtreasure;

import base.dao.impl.BaseDaoImpl;

@Repository
public class DemandtreasureDaoImpl extends BaseDaoImpl<Demandtreasure> implements DemandtreasureDao {

	public DemandtreasureDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.demand.mapper.DemandtreasureMapper");
	}

	@Override
	public Demandtreasure getWithLock() {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getWithLock");
	}
}
