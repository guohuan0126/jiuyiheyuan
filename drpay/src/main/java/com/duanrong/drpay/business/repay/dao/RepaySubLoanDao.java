package com.duanrong.drpay.business.repay.dao;

import java.util.List;

import base.dao.BaseDao;

import com.duanrong.drpay.business.repay.model.RepaySubLoan;

/**
 * @Description: 还款
 * @Author:
 * @CreateDate: Sep 15, 2014
 */
public interface RepaySubLoanDao extends BaseDao<RepaySubLoan> {
	
	List<RepaySubLoan> getRepaySubLoans(String repayId);

	List<RepaySubLoan> getRepaySubLoansBySubloanId(String subloanId);
	
	/**
	 * 查询子标还款计划每期的本息
	 * @param repaySubloan
	 * @return
	 */
	RepaySubLoan getSumMoneyRepayVehicle(RepaySubLoan repaySubloan);
	
	/**
	 * 查询子标还款计划每期的本息
	 * @param repaySubloan
	 * @return
	 */
	RepaySubLoan getSumMoneyRepayAgriculture(RepaySubLoan repaySubloan);
	
	
	/**
	 * 查询子标还款计划数量
	 * @param repaySubloan
	 * @return
	 */
	int getSumRepayVehicle(String loanId);
	
	/**
	 * 查询子标还款计划数量
	 * @param repaySubloan
	 * @return
	 */
	int getSumRepayAgriculture(String loanId);
	
	/**
	 * 更新子标还款计划 repayId
	 * @param repaySubloan
	 */
	void updateRepaySubloanByAgriculture(RepaySubLoan repaySubloan);
	
	/**
	 * 更新子标还款计划 repayId
	 * @param repaySubloan
	 */
	void updateRepaySubloanByVehicle(RepaySubLoan repaySubloan);

}
