package com.duanrong.drpay.business.demand.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.duanrong.drpay.business.demand.dao.DemandtreasureTransferInDao;
import com.duanrong.drpay.business.demand.model.DemandtreasureTransferIn;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

@Repository
public class DemandtreasureTransferInDaoImpl extends BaseDaoImpl<DemandtreasureTransferIn> implements DemandtreasureTransferInDao {

	public DemandtreasureTransferInDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.demand.mapper.DemandtreasureTransferInMapper");
	}

	@Override
	public PageInfo<DemandtreasureTransferIn> findPageInfo(int pageNo, int pageSize,
			DemandtreasureTransferIn demandIn) {
		PageHelper.startPage(pageNo, pageSize);
		List<DemandtreasureTransferIn> list = getSqlSession().selectList(
				getMapperNameSpace() + ".findPageInfo",demandIn);
		PageInfo<DemandtreasureTransferIn> pageInfo = new PageInfo<>(list);
		return pageInfo;
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
	public int isDemandRiseMoney(DemandtreasureTransferIn demandIn) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".isDemandRiseMoney", demandIn);		
	}
}
