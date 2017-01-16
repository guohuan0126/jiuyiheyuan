package com.duanrong.drpay.business.demand.dao;

import com.duanrong.drpay.business.demand.model.DemandTreasureLoan;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

/**
 * @Description: 活期宝投资项目
 * @Author: 
 * @CreateDate:
 */
public interface DemandTreasureLoanDao extends BaseDao<DemandTreasureLoan> {

	PageInfo<DemandTreasureLoan> findPageInfo(int pageNo, int pageSize,
											  DemandTreasureLoan demandTreasureLoan);

	Double getSumTotalMoneyByStatus(String loanStatus);
}
