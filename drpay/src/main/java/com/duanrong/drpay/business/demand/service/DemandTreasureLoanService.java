package com.duanrong.drpay.business.demand.service;

import java.util.List;

import com.duanrong.drpay.business.demand.model.DemandTreasureLoan;

import base.pagehelper.PageInfo;


public interface DemandTreasureLoanService {
	
	public PageInfo<DemandTreasureLoan> findPageInfo(int pageNo, int pageSize, DemandTreasureLoan demandTreasureLoan);
	public DemandTreasureLoan getSumTotalMoney();
	public List<DemandTreasureLoan> findAll();
	public void insert(DemandTreasureLoan demandTreasureLoan);
	public void update(DemandTreasureLoan demandTreasureLoan);
	
}