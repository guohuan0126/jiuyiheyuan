package com.duanrong.business.demand.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.dao.DemandtreasureTransferOutDao;
import com.duanrong.business.demand.model.DemandtreasureTransferOut;

@Repository
public class DemandtreasureTransferOutDaoImpl extends BaseDaoImpl<DemandtreasureTransferOut> implements DemandtreasureTransferOutDao {

	public DemandtreasureTransferOutDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.demand.mapper.DemandtreasureTransferOutMapper");
	}

	@Override
	public Double getUserDemandInSum(String userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		return this
				.getSqlSession()
				.selectOne(
						this.getMapperNameSpace() + ".getUserDemandInSum",
						params);
	}

	@Override
	public DemandtreasureTransferOut gettotal() {
		return getSqlSession().selectOne(getMapperNameSpace() + ".gettotal");
	}

	@Override
	public PageInfo<DemandtreasureTransferOut> pageInfo(int pageNo, int pageSize,Map map) {
		PageHelper.startPage(pageNo, pageSize);
		List<DemandtreasureTransferOut> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo",map);
		PageInfo<DemandtreasureTransferOut> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public List<DemandtreasureTransferOut> gettotalUser() {
		return getSqlSession().selectList(getMapperNameSpace() + ".gettotalUser");
	}
	public DemandtreasureTransferOut queryOutSumMoney(String userId,
			String status) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("status", status);	
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getOutSumMoney",params);
	}

	@Override
	public double getTransferOutAllMoney(String userId,String status) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("status", status);	
		Double money = this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getOutSumMoney2",params);
		
		return money==null?0:money;
	}

	/**
	 * 转出查询，带条件 分页
	 */
	@Override
	public PageInfo getListForTranferOut(int pageNo, int pageSize, DemandtreasureTransferOut transferOut) {
		PageHelper.startPage(pageNo, pageSize);
		List<DemandtreasureTransferOut> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getListForTranferOut",transferOut);
		PageInfo<DemandtreasureTransferOut> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	/**
	 * 总转出金额
	 * @param transferOut
	 * @return
	 */
	public BigDecimal getSumMoneyTransferOut(DemandtreasureTransferOut transferOut) {
		return getSqlSession().selectOne(getMapperNameSpace()+".getSumMoneyTransferOut",transferOut);
	}

	@Override
	public int getSumPeopleTransferOut(DemandtreasureTransferOut transferOut) {
		return getSqlSession().selectOne(getMapperNameSpace()+".getSumPeopleTransferOut",transferOut);
	}

	@Override
	public BigDecimal getSumMoneyFailTransferOut(
			DemandtreasureTransferOut transferOut) {
		return getSqlSession().selectOne(getMapperNameSpace()+".getSumMoneyFailTransferOut",transferOut);
	}

	@Override
	public int getSumPeopleFailTransferOut(DemandtreasureTransferOut transferOut) {
		return getSqlSession().selectOne(getMapperNameSpace()+".getSumPeopleFailTransferOut",transferOut);
	}
}
