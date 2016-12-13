package com.duanrong.business.demand.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.business.demand.dao.DemandTreasureLoanDao;
import com.duanrong.business.demand.model.DemandtreasureLoan;
import com.duanrong.business.demand.service.DemandTreasureLoanService;

@Service
public class DemandTreasureLoanServiceImpl implements DemandTreasureLoanService{
	@Resource
	DemandTreasureLoanDao demandTreasureLoanDao;
	
	@Override
	public PageInfo<DemandtreasureLoan> pageInfo(int pageNo, int pageSize,
			DemandtreasureLoan d) {
		return demandTreasureLoanDao.pageInfo(pageNo, pageSize, d);
	}

	@Override
	public void update(DemandtreasureLoan demandtreasureLoan) {
		demandTreasureLoanDao.update(demandtreasureLoan);
	}

	@Override
	public void insert(DemandtreasureLoan demandtreasureLoan) {
		demandTreasureLoanDao.insert(demandtreasureLoan);
	}

	@Override
	public DemandtreasureLoan get(String id) {
		return demandTreasureLoanDao.get(id);
	}

	@Override
	public void updateLoan(DemandtreasureLoan d) {
		demandTreasureLoanDao.updateLoan(d);
	}

	@Override
	public List<DemandtreasureLoan> exportList(DemandtreasureLoan d) {
		// TODO Auto-generated method stub
	return demandTreasureLoanDao.exportList(d);
		
	}
	@Override
	public List<DemandtreasureLoan> getDemandTreasureLoanByStatus(String order) {
		
		return demandTreasureLoanDao.getDemandTreasureLoanByStatus(order);
	}

	@Override
	public int updateOpenAmount(List list) {
		return demandTreasureLoanDao.updateOpenAmount(list);
	}

	@Override
	public void finishLoan() {
		demandTreasureLoanDao.finishLoan();
	}

}
