package com.duanrong.business.demand.dao;

import java.util.List;
import java.util.Map;

import com.duanrong.business.demand.model.DemandTreasureInvest;
import com.duanrong.business.demand.model.DemandTreasureOpration;

import base.dao.BaseDao;

public interface DemandtreasureInvestDao extends BaseDao<DemandTreasureInvest> {

	public void insertBatchOpration(List<DemandTreasureOpration> oprations);
	
	public void insertBatch(List<DemandTreasureInvest> invests);
	public List <DemandTreasureInvest> findAllOfUser(Map map);
	
	public int updateMoney(List list);
	
	//查询当天到期的资产
	public List<DemandTreasureInvest> getInvestByLoan();
	
	
	//查询当天到期的资产
	public List<DemandTreasureInvest> getInvestMoneyByLoan();
	
	//删除金额
	public void deleteByMoney(Map<String, Object> map);

	public void insertOpration(DemandTreasureOpration opration);
}
