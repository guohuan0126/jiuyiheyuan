package com.duanrong.business.repay.dao;

import java.util.List;

import base.dao.BaseDao;

import com.duanrong.business.repay.model.RepayInvest;

public interface RepayInvestDao extends BaseDao<RepayInvest> {
	/**
	 * 根据条件进行组合查询
	 * 
	 * @param repay
	 * @return
	 */
	public RepayInvest getByCondition(RepayInvest repayInvest);
	
	/**
	 * 根据repayId查询
	 * @param repayId
	 * @return
	 */
	public List<RepayInvest> getByRepayId(String repayId);
	
	/**
	 * 批量更新
	 * @param repayInvest
	 */
	public void updateBatch(RepayInvest repayInvest);
}
