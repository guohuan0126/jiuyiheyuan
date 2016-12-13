package com.duanrong.business.demand.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.duanrong.business.demand.model.DemandTransferFork;
import com.duanrong.business.demand.model.DemandTreasureInvest;
import com.duanrong.business.demand.model.DemandTreasureOpration;
import com.duanrong.business.demand.model.DemandtreasureTransferOut;

public interface DemandTreasureInvestService {

	
	/**
	 * 资金匹配
	 * @param validLoans
	 * @param invests
	 * @param oprations
	 * @param type 类型/in（转入）, expired(续投)
	 */
	@Transactional
	public void transferInFork(Map<String, DemandTransferFork> forks, String id, String type, double money, String userId);
	
	public List<DemandTreasureInvest> findAllOfUser(Map map);
	
	
	public void updateOutInvest(List list, DemandtreasureTransferOut outObj,List<DemandTreasureOpration> oprations);
	
	//查询当天到期的项目
	public List<DemandTreasureInvest> getInvestByLoan();
	
	//查询当天到期的项目
	public List<DemandTreasureInvest> getInvestMoneyByLoan();
	
	
	@Transactional
	public void expired(double money, String userId);
	
}