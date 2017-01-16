package com.duanrong.drpay.business.demand.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.duanrong.drpay.business.demand.dao.DemandtreasureTransferOutDao;
import com.duanrong.drpay.business.demand.model.DemandtreasureTransferOut;

import base.dao.impl.BaseDaoImpl;

@Repository
public class DemandtreasureTransferOutDaoImpl extends
		BaseDaoImpl<DemandtreasureTransferOut> implements
		DemandtreasureTransferOutDao {

	public DemandtreasureTransferOutDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.demand.mapper.DemandtreasureTransferOutMapper");
	}

	@Override
	public Double getOutMoneySumByStatus(String userId, String status) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("status", status);
		Double money = this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getOutMoneySumByStatus", params);
		return money == null ? 0 : money;
	}

	@Override
	public Integer getValidOutCount(String userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		Integer count = this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getValidOutCount", params);
		return count == null ? 0 : count;
	}

	@Override
	public Double getOutInterestSumByStatus(String userId, String status) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("status", status);
		Double money = this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getOutInterestSumByStatus",
				params);
		return money == null ? 0 : money;
	}

	@Override
	public DemandtreasureTransferOut getOutSumMoney(String userId,
			String status) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("status", status);
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getOutSumMoney", params);
	}

	@Override
	public double getValidOutSumMoney(String userId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getValidOutSumMoney", userId);
	}

	@Override
	public DemandtreasureTransferOut getTotal() {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".gettotal");
	}

	@Override
	public List<DemandtreasureTransferOut> getTotalUser() {
		return getSqlSession().selectList(
				getMapperNameSpace() + ".gettotalUser");
	}
}