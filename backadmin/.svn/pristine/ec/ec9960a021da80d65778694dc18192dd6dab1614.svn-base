package com.duanrong.business.repay.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.repay.model.Repay;

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
	
	/**
	 * 获得项目还款记录
	 * 
	 * @param loanId
	 *            项目ID
	 * @return
	 */
	public List<Repay> getRepayLoan(String loanId);
	
	/**
	 * 获得项目还款记录
	 * 
	 * @param loanId
	 *            项目ID
	 * @return
	 */
	public Repay getRepayById(String id);

	/**
	 * 获得下一期还款计划
	 * 
	 * @param loanId
	 *            项目ID
	 * @return
	 */
	public Repay getDownRepay(Repay repay);
	PageInfo<Repay> pageInfo(int pageNo, int pageSize, Map map);
	List<Repay> findRepay(Map map);

	/**
	 * 查询一周以内完成的还款记录
	 * @return
	 */
	public List<Repay> getRepayList(Date date);
	
	public void updateByLoanId(Repay repay);
	/**
	 * 还款计划表里的本金和和利息和
	 * @param map
	 * @return
	 */
	public Repay getTotalMoney(Map map);
	
	Repay getCurrentPeriodByLoanId(String loanId);
	
	void updateBefoRepay(Repay repay);
}

