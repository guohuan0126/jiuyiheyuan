package com.duanrong.drpay.business.repay.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.drpay.business.invest.model.Invest;
import com.duanrong.drpay.business.loan.model.Loan;
import com.duanrong.drpay.business.repay.dao.RepayDao;
import com.duanrong.drpay.business.repay.model.Repay;

@Repository
public class RepayDaoImpl extends BaseDaoImpl<Repay> implements RepayDao {

	public RepayDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.repay.mapper.RepayMapper");
	}

	public List<Repay> getByCondition(Repay repay) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getByCondition", repay);
	}

	@Override
	public Repay get(Serializable id) {
		Repay repay = super.get(id);
		if (repay != null) {
			Loan loan = repay.getLoan();
			loan.setId(repay.getLoanId());
			if (loan != null) {
				List<Invest> invests = loan.getInvests();
				if (!invests.isEmpty()) {
					for (Invest invest : invests) {
						invest.setLoan(loan);
						invest.setRate(loan.getRate());
						invest.setLoanName(loan.getName());
					}
					loan.setInvests(invests);
					repay.setLoan(loan);
				}
			}
		}
		return repay;
	}

	@Override
	public Date getCompleteDate(String loanId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getCompleteDate", loanId);
	}

	@Override
	public void updateOtherRepay(Repay repay) {
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".updateOtherRepay", repay);
	}

}
