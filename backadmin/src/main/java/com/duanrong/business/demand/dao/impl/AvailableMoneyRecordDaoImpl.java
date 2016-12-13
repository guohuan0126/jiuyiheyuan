package com.duanrong.business.demand.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.dao.AvailableMoneyRecordDao;
import com.duanrong.business.demand.model.AvailableMoneyRecord;

@Repository
public class AvailableMoneyRecordDaoImpl extends BaseDaoImpl<AvailableMoneyRecord> implements AvailableMoneyRecordDao {

	public AvailableMoneyRecordDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.demand.mapper.AvailableMoneyRecordMapper");
	}
	@Override
	public PageInfo<AvailableMoneyRecord> readPageInfo(int pageNo, int pageSize) {
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
	public AvailableMoneyRecord readRecord() {
		return getSqlSession().selectOne(
				getMapperNameSpace() + ".getRecord");
	}
	@Override
	public double readUserUsedMoney() {
		
		return getSqlSession().selectOne("getUserUsedMoney");
	}
	@Override
	public void updateDemandTreasure(double money) {
		this.getSqlSession().update("updateDemandTreasure", money);
		
	}
	@Override
	public double readAvailableMoneyRecord(String date) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".readAvailableMoneyRecord", date);
	}
}