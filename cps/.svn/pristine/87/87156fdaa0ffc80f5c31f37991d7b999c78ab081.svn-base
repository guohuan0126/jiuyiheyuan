/**
 * 
 */
package com.duanrong.cps.business.netloaneye.service;



import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.netloaneye.model.NetLoanModel;
import com.duanrong.cps.business.netloaneye.model.PushLoan;

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
	PageInfo<Loan> getPushRecords(String pageNo, Loan loan);

	/**
	 * 向天眼推送项目
	 * @param id
	 * @return
	 */
	int pushNetLoan(String id,String userid);

	NetLoanModel getNetLoanCount(Loan loan);

	NetLoanModel getNetLoanSum(Loan loan);
	
	 //根据loanid查询push_loan
    public PushLoan selectByLoanId(String loanId);

	int pushNetLoanStatus(Loan loan);

}
