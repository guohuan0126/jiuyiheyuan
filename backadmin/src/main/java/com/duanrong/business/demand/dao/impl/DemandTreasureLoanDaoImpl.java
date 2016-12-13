package com.duanrong.business.demand.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.RedisCacheKey;
import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.dao.DemandTreasureLoanDao;
import com.duanrong.business.demand.model.DemandforkLoan;
import com.duanrong.business.demand.model.DemandtreasureLoan;
import com.duanrong.util.jedis.DRJedisCacheUtil;

@Repository
public class DemandTreasureLoanDaoImpl extends BaseDaoImpl<DemandtreasureLoan> implements DemandTreasureLoanDao{

	public DemandTreasureLoanDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.demand.mapper.DemandtreasureLoanMapper");
	}
	
	
	@Override
	public void insert(DemandtreasureLoan entity) {
		DRJedisCacheUtil.del(RedisCacheKey.LOANS_DEMAND);
		super.insert(entity);
	}

	@Override
	public void update(DemandtreasureLoan entity) {
		DRJedisCacheUtil.del(RedisCacheKey.LOANS_DEMAND);
		super.update(entity);
	}

	public PageInfo<DemandtreasureLoan> readPageInfo(int pageNo, int pageSize,DemandtreasureLoan d) {
		PageHelper.startPage(pageNo, pageSize);
		List<DemandtreasureLoan> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo",d);
		PageInfo<DemandtreasureLoan> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	@Override
	public void updateLoan(DemandtreasureLoan d) {
		DRJedisCacheUtil.del(RedisCacheKey.LOANS_DEMAND);
		getSqlSession().update(getMapperNameSpace() + ".updateLoan", d);		
	}
	@Override
	public List<DemandtreasureLoan> exportList(DemandtreasureLoan d) {
		List<DemandtreasureLoan> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo",d);
		return list;
	}

	@Override
	public int insertBatch(List<DemandtreasureLoan> list) {
		DRJedisCacheUtil.del(RedisCacheKey.LOANS_DEMAND);
		Map<String,Object>  params=new HashMap<String, Object>();
		params.put("list",list);		
		return this.getSqlSession().insert(this.getMapperNameSpace() +".batchDemandTreasureLoan",params);	
		
	}
	@Override
	public DemandtreasureLoan readByForkId(String id) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() +".getByForkId",id);
	}


	@Override
	public double readMoneyBydate(String date) {
		
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".readMoneyBydate",date);
	}
	@Override
	public double readAssignmentMoney() {
		
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".readAssignmentMoney");
	}
	@Override
	public void updateAssignmentMoneyById(Map<String,Object> params) {
		DRJedisCacheUtil.del(RedisCacheKey.LOANS_DEMAND);
		this.getSqlSession().update(this.getMapperNameSpace()+".updateAssignmentMoneyById", params);
		
	}
	@Override
	public List<DemandtreasureLoan> readDemandtreasureLoan(Map<String,Object> params) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".readDemandtreasureLoan",params);
	}
	@Override
	public List<DemandtreasureLoan> readDemandtreasureLoanIds() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".readDemandtreasureLoanIds");
	}
	@Override
	public void updateAssignmentStatusByIds(Map<String, Object> params) {
		DRJedisCacheUtil.del(RedisCacheKey.LOANS_DEMAND);
		this.getSqlSession().update(this.getMapperNameSpace()+".updateAssignmentStatusByIds", params);
	}
	@Override
	public void updateAssignmentStatusById(Map<String, Object> params) {
		DRJedisCacheUtil.del(RedisCacheKey.LOANS_DEMAND);
		this.getSqlSession().update(this.getMapperNameSpace()+".updateAssignmentStatusById", params);
		
	}
	@Override
	public List<DemandtreasureLoan> readByAvailableId(Map<String, Object> params) {
		return  this.getSqlSession().selectList(this.getMapperNameSpace()+".readByAvailableId",params);
	}
	@Override
	public List<DemandtreasureLoan> readByAvailableIdNew(
			Map<String, Object> params) {		
		return  this.getSqlSession().selectList(this.getMapperNameSpace()+".readByAvailableIdNew",params);
	}
	@Override
	public DemandtreasureLoan readDemandTreasureLoan() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".readDemandTreasureLoan");
	}
	@Override
	public List<DemandtreasureLoan> readTransferDemandtreasureLoan() {
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".transferDemandtreasureLoan");
	}
	@Override
	public DemandtreasureLoan readByLoaninfoId(String id) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() +".getLoaninfoId",id);
	}
	@Override
	public int updateLoanerinfoTime(Map<String, Object> params) {
		DRJedisCacheUtil.del(RedisCacheKey.LOANS_DEMAND);
		return this.getSqlSession().update(getMapperNameSpace() + ".updateLoanerinfoTime", params);
		
	}
	@Override
	public List<DemandtreasureLoan> readDemadfork(String id) {
		return this.getSqlSession().selectList(this.getMapperNameSpace() +".readDemadfork",id);
	}
	@Override
	public int updateDemandDisplay(Map<String, Object> params) {
		DRJedisCacheUtil.del(RedisCacheKey.LOANS_DEMAND);
		return this.getSqlSession().update(getMapperNameSpace() + ".updateDemandDisplay", params);
	}
	@Override
	public DemandforkLoan readforkCounts(Map<String, Object> params) {		
		return this.getSqlSession().selectOne(this.getMapperNameSpace() +".readforkCounts",params);
	}


	@Override
	public int updatedemandFinishTime(Map<String, Object> params) {
		//DRJedisCacheUtil.del(RedisCacheKey.LOANS_DEMAND);
		return this.getSqlSession().update(getMapperNameSpace() + ".updatedemandFinishTime", params);
	}	
}