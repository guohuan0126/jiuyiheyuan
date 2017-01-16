package com.duanrong.business.demand.dao.impl;


import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.demand.dao.AvailableMoneyRecordDao;
import com.duanrong.business.demand.model.AvailableMoneyRecord;

@Repository
public class AvailableMoneyRecordDaoImpl extends BaseDaoImpl<AvailableMoneyRecord> implements AvailableMoneyRecordDao {

	public AvailableMoneyRecordDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.demand.mapper.AvailableMoneyRecordMapper");
	}
	
	
	@Override
	public AvailableMoneyRecord getEndLine() {
		
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getEndLine");
	}
}
