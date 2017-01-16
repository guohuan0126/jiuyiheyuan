package com.duanrong.drpay.business.demand.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.drpay.business.demand.dao.DemandTreasureLoanDao;
import com.duanrong.drpay.business.demand.model.DemandTreasureLoan;
import com.duanrong.drpay.business.demand.service.DemandTreasureLoanService;

import base.pagehelper.PageInfo;


@Service
public class DemandTreasureLoanServiceImpl implements DemandTreasureLoanService{

	@Resource
	DemandTreasureLoanDao demandTreasureLoanDao;

	@Override
	public PageInfo<DemandTreasureLoan> findPageInfo(int pageNo, int pageSize,
			DemandTreasureLoan demandTreasureLoan) {
		PageInfo<DemandTreasureLoan> pageInfo = demandTreasureLoanDao.findPageInfo(pageNo, pageSize, demandTreasureLoan);
		if (pageInfo != null) {		
			//周期计算 
			List<DemandTreasureLoan> results = pageInfo.getResults();
			if (results != null && !results.isEmpty()) {
				for (DemandTreasureLoan treasureLoan : results) {
					if("月".equals(treasureLoan.getOperationType())){
						treasureLoan.setTerm(treasureLoan.getMonth()+"个月");
					}else if("天".equals(treasureLoan.getOperationType())){
						treasureLoan.setTerm(treasureLoan.getDay()+"天");
					}
				}
			}
		}
		return pageInfo ;
	}
	
	@Override
	public List<DemandTreasureLoan> findAll() {
		return demandTreasureLoanDao.findAll();
	}

	@Override
	public void insert(DemandTreasureLoan demandTreasureLoan) {
		demandTreasureLoanDao.insert(demandTreasureLoan);
	}

	@Override
	public void update(DemandTreasureLoan demandTreasureLoan) {
		demandTreasureLoanDao.update(demandTreasureLoan);
	}

	@Override
	public DemandTreasureLoan getSumTotalMoney(){
		DemandTreasureLoan demandTreasureLoan = new DemandTreasureLoan();
		demandTreasureLoan.setRepayMentMoney(demandTreasureLoanDao.getSumTotalMoneyByStatus("repay"));
		demandTreasureLoan.setAlreadyMoney(demandTreasureLoanDao.getSumTotalMoneyByStatus("finish"));
		return demandTreasureLoan;
		
	}
}
