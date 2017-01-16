package com.duanrong.drpay.trusteeship.service;

import base.exception.LoanException;
import base.exception.UserAccountException;
import base.exception.UserInfoException;

import com.duanrong.drpay.business.loan.model.Loan;
import com.duanrong.drpay.jsonservice.param.LoanParameter;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;

/**
 * 项目业务类 创建标的 变更标的 流标.....
 * 
 * @author xiao
 * @datetime 2016年12月7日 上午9:12:38
 */
public interface TrusteeshipLoanService {

	/**
	 * 创建项目(仅在存管通注册项目信息)
	 * 
	 * @param loan
	 * @throws UserInfoException
	 * @throws UserAccountException
	 * @throws LoanException 
	 */
	GeneratorJSON createLoan(String userId, LoanParameter loan)
			throws UserInfoException, UserAccountException, LoanException;


	/**
	 * 创建批量投标计划
	 * @param parameter
	 * @return
	 */
	GeneratorJSON createProject(LoanParameter parameter)throws UserAccountException;
	
	
	/**
	 * 变更标的状态（仅修改存管通项目状态）
	 * @param loanId
	 * @param status
	 * @return
	 * @throws LoanException
	 */
	GeneratorJSON modifyLoan(String loanId,  String status)	throws LoanException;
	
	/**
	 * 变更标的状态（仅修改存管通项目状态）
	 * @param loanId
	 * @param status
	 * @return
	 * @throws LoanException
	 */
	GeneratorJSON modifyLoan(Loan loan,  String status)	throws LoanException;
	
	
	/**
	 * 查询标的状态
	 * @param loanId
	 * @return
	 * @throws LoanException
	 */
	GeneratorJSON queryLoan(String loanId)	throws LoanException;

}