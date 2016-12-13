package com.duanrong.business.demand.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.dao.DemandtreasureTransferInDao;
import com.duanrong.business.demand.model.DateRate;
import com.duanrong.business.demand.model.DemandtreasureTransferIn;

@Repository
public class DemandtreasureTransferInDaoImpl extends BaseDaoImpl<DemandtreasureTransferIn> implements DemandtreasureTransferInDao {

	public DemandtreasureTransferInDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.demand.mapper.DemandtreasureTransferInMapper");
	}

	@Override
	public DemandtreasureTransferIn readTotal() {
		return getSqlSession().selectOne(getMapperNameSpace() + ".gettotal");
	}

	@Override
	public PageInfo<DemandtreasureTransferIn> readPageInfo(int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<DemandtreasureTransferIn> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo");
		PageInfo<DemandtreasureTransferIn> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public List<DemandtreasureTransferIn> readTran(DemandtreasureTransferIn tran) {
		return getSqlSession().selectList(
				getMapperNameSpace() + ".findTran",tran);
	}

	@Override
	public List<DemandtreasureTransferIn> readStatus() {
		return getSqlSession().selectList(
				getMapperNameSpace() + ".findStatus");
	}
	@Override
	public double readUserDemandInSumMoney(String userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		Double money = this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInMoneySumByStatus",params);
		return money==null?0:money;
	}

	@Override
	public double readTransferInAllMoney(String userId) {
		Double money = getSqlSession().selectOne(getMapperNameSpace() + ".getInSumMoney",userId);

		money =( money==null?0:money);

		return money;
	}

	@Override
	public List<DemandtreasureTransferIn> readTransferInRecordTop(
			String userId, int top) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("limit", top);
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".GetTransferInTop",params);
	}

	/**
	 * 转入查询，带分页
	 */
	@Override
	public PageInfo readListForTranferIn(int pageNo, int pageSize, DemandtreasureTransferIn transferIn) {
		PageHelper.startPage(pageNo, pageSize);
		List<DemandtreasureTransferIn> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getListForTranferIn",transferIn);
		PageInfo<DemandtreasureTransferIn> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	/**
	 * 总转入金额查询
	 * @param transferIn
	 * @return
	 */
	public BigDecimal readSumMoneyTransferIn(DemandtreasureTransferIn transferIn) {
		return getSqlSession().selectOne(getMapperNameSpace()+".getSumMoneyTransferIn",transferIn);
	}

	@Override
	public int readSumPeopleTransferIn(DemandtreasureTransferIn transferIn) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getMapperNameSpace()+".getSumPeopleTransferIn",transferIn);
	}

	@Override
	public BigDecimal readSumMoneyTransferInFail(
			DemandtreasureTransferIn transferIn) {
		return getSqlSession().selectOne(getMapperNameSpace()+".getSumMoneyTransferInFail",transferIn);
	}

	@Override
	public int readSumPeopleTransferInFail(DemandtreasureTransferIn transferIn) {
		return getSqlSession().selectOne(getMapperNameSpace()+".getSumPeopleTransferInFail",transferIn);
	}
}