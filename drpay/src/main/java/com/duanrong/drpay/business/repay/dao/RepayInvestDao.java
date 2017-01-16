package com.duanrong.drpay.business.repay.dao;

import java.util.List;

import base.dao.BaseDao;

import com.duanrong.drpay.business.repay.model.RepayInvest;

public interface RepayInvestDao extends BaseDao<RepayInvest> {
	/**
	 * 根据条件进行组合查询
	 * 
	 * @param repay
	 * @return
	 */
	public RepayInvest getByCondition(RepayInvest repayInvest);

	public void deleteRepayInvests(String repayId);
	
	
	int getRepayInvestCountByRepayIdAndStatus(String repayId);

	public List<RepayInvest> getRepayInvests(String repayId); 
}