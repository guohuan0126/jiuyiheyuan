package com.duanrong.business.demand.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.dao.DemandtreasureTransferInDao;
import com.duanrong.business.demand.model.DemandtreasureTransferIn;

@Repository
public class DemandtreasureTransferInDaoImpl extends BaseDaoImpl<DemandtreasureTransferIn> implements DemandtreasureTransferInDao {

	public DemandtreasureTransferInDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.demand.mapper.DemandtreasureTransferInMapper");
	}

	@Override
	public DemandtreasureTransferIn gettotal() {
		return getSqlSession().selectOne(getMapperNameSpace() + ".gettotal");
	}

	@Override
	public PageInfo<DemandtreasureTransferIn> pageInfo(int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<DemandtreasureTransferIn> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo");
		PageInfo<DemandtreasureTransferIn> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public List<DemandtreasureTransferIn> findTran(DemandtreasureTransferIn tran) {
		return getSqlSession().selectList(
				getMapperNameSpace() + ".findTran",tran);
	}

	@Override
	public List<DemandtreasureTransferIn> findStatus() {
		return getSqlSession().selectList(
				getMapperNameSpace() + ".findStatus");
	}
	@Override
	public double getuserDemandInSumMoney(String userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		Double money = this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInMoneySumByStatus",params);
		return money==null?0:money;
	}

	@Override
	public double getTransferInAllMoney(String userId) {
		Double money = getSqlSession().selectOne(getMapperNameSpace() + ".getInSumMoney",userId);

		money =( money==null?0:money);

		return money;
	}

	@Override
	public List<DemandtreasureTransferIn> findTransferInRecordTop(
			String userId, int top) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("limit", top);
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".GetTransferInTop",params);
	}

	@Override
	public List<DemandtreasureTransferIn> getTransferInByFork() {		
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getTransferInByFork");
	}

	@Override
	public List<DemandtreasureTransferIn> getTransferInitByFork() {
		
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".demandInit");
	}

	@Override
	public Double getDemandMoney(String userId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getDemandMoney",userId);
	}

	@Override
	public List<String> getTurnOutUserId() {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getTurnOutUserId");
	}
}