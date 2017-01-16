package com.duanrong.drpay.business.repay.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;

import com.duanrong.drpay.business.repay.model.RepayInvestSubLoan;

/**
 * @Description: 还款
 * @Author:
 * @CreateDate: Sep 15, 2014
 */
public interface RepayInvestSubLoanDao extends BaseDao<RepayInvestSubLoan> {

	List<RepayInvestSubLoan> getRepayInvestSubLoans(String repaySubloanId);

	void deleteRepayInvestSubLoans(String repaySubloanId);

	Map<String, Double> getCorpusAndInterestByInvestId(String investId);

	RepayInvestSubLoan getRepayInvestSubLoan(String repayInvestSubLoanId);
	
}
