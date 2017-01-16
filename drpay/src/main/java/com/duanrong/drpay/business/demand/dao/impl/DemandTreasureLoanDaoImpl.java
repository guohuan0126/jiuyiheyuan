package com.duanrong.drpay.business.demand.dao.impl;

import org.springframework.stereotype.Repository;

import com.duanrong.drpay.business.demand.dao.DemandTreasureLoanDao;
import com.duanrong.drpay.business.demand.model.DemandTreasureLoan;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageInfo;

@Repository
public class DemandTreasureLoanDaoImpl extends BaseDaoImpl<DemandTreasureLoan> implements DemandTreasureLoanDao{

	public DemandTreasureLoanDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.demand.mapper.DemandTreasureLoanMapper");
	}

	@Override
	public PageInfo<DemandTreasureLoan> findPageInfo(int pageNo, int pageSize,
			DemandTreasureLoan entity) {
		return this.pageLite(pageNo, pageSize, entity);
	}
	
	@Override
	public Double getSumTotalMoneyByStatus(String loanStatus) {
		Double money = getSqlSession().selectOne(getMapperNameSpace() + ".getSumTotalMoneyByStatus", loanStatus);
		return money == null ? 0 : money; 
	}
	
}
