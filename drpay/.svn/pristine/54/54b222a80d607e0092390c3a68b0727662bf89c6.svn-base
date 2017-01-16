package com.duanrong.drpay.business.repay.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.drpay.business.repay.dao.RepaySubLoanDao;
import com.duanrong.drpay.business.repay.model.RepaySubLoan;

@Repository
public class RepaySubLoanDaoImpl extends BaseDaoImpl<RepaySubLoan> implements RepaySubLoanDao {

	public RepaySubLoanDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.repay.mapper.RepaySubLoanMapper");
	}

	@Override
	public List<RepaySubLoan> getRepaySubLoans(String repayId) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getRepaySubLoans", repayId);
	}

	@Override
	public List<RepaySubLoan> getRepaySubLoansBySubloanId(String subloanId) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getRepaySubLoansBySubloanId", subloanId);
	}

	@Override
	public RepaySubLoan getSumMoneyRepayVehicle(RepaySubLoan repaySubloan) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getSumMoneyRepayVehicle", repaySubloan);
	}

	@Override
	public RepaySubLoan getSumMoneyRepayAgriculture(RepaySubLoan repaySubloan) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getSumMoneyRepayAgriculture", repaySubloan);
	}

	@Override
	public void updateRepaySubloanByAgriculture(RepaySubLoan repaySubloan) {
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".updateRepaySubloanByAgriculture", repaySubloan);
	}

	@Override
	public void updateRepaySubloanByVehicle(RepaySubLoan repaySubloan) {
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".updateRepaySubloanByVehicle", repaySubloan);
		
	}

	@Override
	public int getSumRepayVehicle(String loanId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getSumRepayVehicle", loanId);
	}

	@Override
	public int getSumRepayAgriculture(String loanId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getSumRepayAgriculture", loanId);
	}

}
