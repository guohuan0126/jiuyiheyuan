package com.duanrong.drpay.business.repay.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.drpay.business.repay.dao.RepayInvestSubLoanDao;
import com.duanrong.drpay.business.repay.model.RepayInvestSubLoan;

@Repository
public class RepayInvestSubLoanDaoImpl extends BaseDaoImpl<RepayInvestSubLoan> implements RepayInvestSubLoanDao {

	public RepayInvestSubLoanDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.repay.mapper.RepayInvestSubLoanMapper");
	}

	@Override
	public List<RepayInvestSubLoan> getRepayInvestSubLoans(String repaySubloanId) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getRepayInvestSubLoans", repaySubloanId);
	}

	@Override
	public void deleteRepayInvestSubLoans(String repaySubloanId) {
		this.getSqlSession().delete(
				this.getMapperNameSpace() + ".deleteRepayInvestSubLoans", repaySubloanId);
	}

	@Override
	public Map<String, Double> getCorpusAndInterestByInvestId(String investId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("investId", investId);
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getCorpusAndInterestByInvestId", params);
	}

	@Override
	public RepayInvestSubLoan getRepayInvestSubLoan(String repayInvestSubLoanId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getRepayInvestSubLoan", repayInvestSubLoanId);
	}

}
