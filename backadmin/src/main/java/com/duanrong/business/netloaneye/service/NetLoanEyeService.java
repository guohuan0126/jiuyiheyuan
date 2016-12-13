/**
 * 
 */
package com.duanrong.business.netloaneye.service;

import com.duanrong.business.dictionary.model.Dictionary;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.netloaneye.model.NetLoanModel;
import com.duanrong.business.netloaneye.model.PushLoan;

import base.pagehelper.PageInfo;

/**
 * @author bj
 *
 */
public interface NetLoanEyeService {

	/**
	 * 查询推送记录
	 * @param pageNo
	 * @param loan
	 * @return
	 */
	PageInfo<Loan> readPushRecords(String pageNo, Loan loan);

	/**
	 * 向天眼推送项目
	 * @param id
	 * @return
	 */
	int pushNetLoan(String id,String userid);

	NetLoanModel readNetLoanCount(Loan loan);

	NetLoanModel readNetLoanSum(Loan loan);
	
	 //根据loanid查询push_loan
    public PushLoan readByLoanId(String loanId);

	int pushNetLoanStatus(Loan loan);

}
