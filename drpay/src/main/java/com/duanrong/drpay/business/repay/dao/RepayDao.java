package com.duanrong.drpay.business.repay.dao;

import java.util.Date;
import java.util.List;

import base.dao.BaseDao;

import com.duanrong.drpay.business.repay.model.Repay;

/**
 * @Description: 还款
 * @Author: 林志明
 * @CreateDate: Sep 15, 2014
 */
public interface RepayDao extends BaseDao<Repay> {
	/**
	 * 根据条件进行组合查询
	 * 
	 * @param repay
	 * @return
	 */
	public List<Repay> getByCondition(Repay repay);

	/**
	 * 获得项目还款完成（结束）时间
	 * 
	 * @param loanId
	 *            项目ID
	 * @return
	 */
	public Date getCompleteDate(String loanId);

	public void updateOtherRepay(Repay repay);
}
