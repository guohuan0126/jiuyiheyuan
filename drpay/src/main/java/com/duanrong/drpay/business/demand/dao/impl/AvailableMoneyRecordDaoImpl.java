package com.duanrong.drpay.business.demand.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.duanrong.drpay.business.demand.dao.AvailableMoneyRecordDao;
import com.duanrong.drpay.business.demand.model.AvailableMoneyRecord;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

@Repository
public class AvailableMoneyRecordDaoImpl extends BaseDaoImpl<AvailableMoneyRecord> implements AvailableMoneyRecordDao {

	public AvailableMoneyRecordDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.demand.mapper.AvailableMoneyRecordMapper");
	}
	@Override
	public PageInfo<AvailableMoneyRecord> pageInfo(int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<AvailableMoneyRecord> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo");
		PageInfo<AvailableMoneyRecord> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	@Override
	public void del(AvailableMoneyRecord availableMoneyRecord) {
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".del", availableMoneyRecord);
	}
	@Override
	public AvailableMoneyRecord getEndLine() {
		
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getEndLine");
	}
}