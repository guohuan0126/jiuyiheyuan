package com.duanrong.business.demand.dao.impl;

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

	@Override
	public List<DemandtreasureTransferOut> findAllSuccess() {
		
		 return getSqlSession().selectList(getMapperNameSpace() + ".findAllSuccess");
	}
}
