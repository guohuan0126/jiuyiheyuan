package com.duanrong.business.maxinvestrecord.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.maxinvestrecord.dao.MaxInvestRecordDao;
import com.duanrong.business.maxinvestrecord.model.MaxInvestRecord;
@Repository
public class MaxInvestRecordDaoImpl extends BaseDaoImpl<MaxInvestRecord> implements MaxInvestRecordDao{
	
	
	public MaxInvestRecordDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.maxinvestrecord.mapper.MaxInvestRecordMapper");
	}

	@Override
	public MaxInvestRecord getInvestRecord(String userId) {
		return getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInvestRecord",
				userId);
	}
	@Override
	public List<MaxInvestRecord> getRecord(int times) {
		return this.getSqlSession().selectList(this.getMapperNameSpace() + ".getRecord",times);
	}

	@Override
	public int getRecordCount() {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getRecordCount");
	}

}