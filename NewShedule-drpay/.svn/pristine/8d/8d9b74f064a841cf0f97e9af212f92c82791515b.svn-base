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
import com.duanrong.business.demand.model.DemandtreasureLoan;
import com.duanrong.util.jedis.DRJedisCacheUtil;

@Repository
public class DemandTreasureLoanDaoImpl extends BaseDaoImpl<DemandtreasureLoan> implements DemandTreasureLoanDao{

	public DemandTreasureLoanDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.demand.mapper.DemandtreasureLoanMapper");
	}
	public PageInfo<DemandtreasureLoan> pageInfo(int pageNo, int pageSize,DemandtreasureLoan d) {
		PageHelper.startPage(pageNo, pageSize);
		List<DemandtreasureLoan> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo",d);
		PageInfo<DemandtreasureLoan> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	@Override
	public void updateLoan(DemandtreasureLoan d) {
		getSqlSession().update(getMapperNameSpace() + ".updateLoan", d);		
	}
	@Override
	public List<DemandtreasureLoan> exportList(DemandtreasureLoan d) {
		List<DemandtreasureLoan> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo",d);
		return list;
	}

	@Override
	public List<DemandtreasureLoan> getDemandTreasureLoanByStatus(String sort) {	
		Map<String, Object> map = new HashMap<>();
		map.put("sort", sort);
		return getSqlSession().selectList(getMapperNameSpace() + ".getDemandTreasureLoanByStatus", map);
	}
	@Override
	public void updateBatchValidMoney(List<DemandtreasureLoan> ds) {
		getSqlSession().update(getMapperNameSpace() + ".batchUpdateValidMoney", ds);
	}

	@Override
	public int updateOpenAmount(List list) {
		return getSqlSession().update(getMapperNameSpace() + ".batchUpdateOpenAmount", list);
	}
	@Override
	public void finishLoan() {		
		getSqlSession().update(getMapperNameSpace() + ".finishLoan");
		DRJedisCacheUtil.del(RedisCacheKey.NOTICES_LOAN);
	}
}
