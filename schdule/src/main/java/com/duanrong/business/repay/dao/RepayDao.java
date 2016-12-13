package com.duanrong.business.repay.dao;

import java.util.Date;
import java.util.List;

import base.dao.BaseDao;

import com.duanrong.business.repay.model.Repay;
import com.duanrong.business.repay.model.RepayMail;

/**
 * @Description: 还款
 * @Author: 林志明
 * @CreateDate: Sep 15, 2014
 */
public interface RepayDao extends BaseDao<Repay> {
	
	
	/**
	 * 获得项目还款记录
	 * 
	 * @param loanId
	 *            项目ID
	 * @return
	 */
	public List<Repay> getRepayLoan(String loanId);
	
	public void updateByLoanId(Repay repay);
	/**
	 * 获得项目还款记录
	 * 
	 * @param loanId
	 *            项目ID
	 * @return
	 */
	public List<Repay> getRepayLoan();
	
	/**
	 * 获得项目还款记录
	 * 
	 * @param id
	 *            项目ID
	 * @return
	 */
	public Repay getRepayById(String id);


	/**
	 * 查询一周以内完成的还款记录
	 * @return
	 */
	public List<Repay> getRepayList(Date date);
	
	
	/**
	 * 查询days天后需要还款的用户
	 * @param days
	 * @return
	 */
	public List<RepayMail> getInterval(int days);

	/**
	 * 查询days天后需要还款的用户
	 * @param days
	 * @param userId
	 * @return
	 */
	public List<RepayMail> getRepayInterval(String userId, int days);
	
	
	/**
	 * 查询days天前完成还款的信息
	 * @param days
	 * @return
	 */
	public List<RepayMail> getRepayFinishInterval(int days);
}

