package com.duanrong.business.demand.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.model.DemandtreasureLoan;



/**
 * @Description: 活期宝投资项目
 * @Author: 
 * @CreateDate:
 */
public interface DemandTreasureLoanDao extends BaseDao<DemandtreasureLoan> {
	public PageInfo<DemandtreasureLoan> pageInfo(int pageNo, int pageSize,DemandtreasureLoan d);
	public void updateLoan(DemandtreasureLoan d);
	public List<DemandtreasureLoan> exportList(DemandtreasureLoan d);
	
	/**
	 * 查询可分配资产的项目
	 * @param order
	 * @return
	 */
	public List<DemandtreasureLoan> getDemandTreasureLoanByStatus(String sort);

	/**
	 * 更新资产的可投金额
	 * @param d
	 */
	public void updateBatchValidMoney(List<DemandtreasureLoan> ds);

	public int updateOpenAmount(List list);

	//跟新今日到期项目为完成
	public void finishLoan();
}
