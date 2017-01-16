package com.duanrong.drpay.business.loan.dao;

import java.util.List;

import base.dao.BaseDao;

import com.duanrong.drpay.business.invest.model.Invest;
import com.duanrong.drpay.business.loan.model.Loan;
import com.duanrong.drpay.business.loan.model.SubLoan;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-8-28 下午12:45:16
 * @Description : drsoa com.duanrong.business.loan.dao LoanDao.java
 * 
 */
public interface LoanDao extends BaseDao<Loan> {

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午5:29:29
	 * @param userID
	 * @return
	 */
	List<Loan> getLoansByGroupCondition(Loan loan);


	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author 孙铮
	 * @time 2014-9-1 下午12:01:22
	 * @param invest
	 * @return
	 */
	List<Invest> getInvestsByGroupCondition(Invest invest);
	
	/**
	 * 根据ID给项目加锁
	 * @param id
	 * @return
	 */
	Loan getWithLock(String id);

	/**
	 * 查询理财计划车贷子标
	 * @param loanId
	 * @return
	 */
	List<SubLoan> getVehicle(String loanId);
	
	/**
	 * 查询理财计划农贷子标
	 * @param loanId
	 * @return
	 */
	List<SubLoan> getAgricultureForkLoans(String loanId);
	
	/**
	 * 查询理财计划车贷子标
	 * @param loanId
	 * @return
	 */
	List<SubLoan> getVehicleByGroupCondition(String loanId, int drpayStatus);
	
	/**
	 * 查询理财计划农贷子标
	 * @param loanId
	 * @return
	 */
	List<SubLoan> getAgricultureByGroupCondition(String loanId, int drpayStaus);


	/**
	 * 查询理财计划车贷子标
	 * @param loanId
	 * @return
	 */
	void updateBatchVehicle(List<SubLoan> subloans);
	
	/**
	 * 查询理财计划农贷子标
	 * @param loanId
	 * @return
	 */
	void updateBatchAgricultureForkLoans(List<SubLoan> subloans);
	
	/**
	 * 查询理财计划车贷子标
	 * @param loanId
	 * @return
	 */
	void updateVehicle(SubLoan subloan);
	
	/**
	 * 查询理财计划农贷子标
	 * @param loanId
	 * @return
	 */
	void updateAgricultureForkLoans(SubLoan subloan);
}
