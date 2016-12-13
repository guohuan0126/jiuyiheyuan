package com.duanrong.business.demand.service;

import java.util.List;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.business.demand.model.DemandtreasureLoan;

public interface DemandTreasureLoanService {
	public PageInfo<DemandtreasureLoan> pageInfo(int pageNo, int pageSize,DemandtreasureLoan d);
	public void update(DemandtreasureLoan demandtreasureLoan);
	public void insert(DemandtreasureLoan demandtreasureLoan);
	public DemandtreasureLoan get(String id);
	public void updateLoan(DemandtreasureLoan d);
	public List<DemandtreasureLoan> exportList(DemandtreasureLoan d);
	
	/**
	 * 查询可投资产信息
	 * @param order
	 * @return
	 */
	public List<DemandtreasureLoan> getDemandTreasureLoanByStatus(String order);
	

	public int updateOpenAmount(List list);
	
	public void finishLoan();
}