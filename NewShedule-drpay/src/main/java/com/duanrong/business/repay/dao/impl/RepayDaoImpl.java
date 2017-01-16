package com.duanrong.business.repay.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.duanrong.business.repay.model.RepayMail;

import org.springframework.stereotype.Repository;

import util.MyCollectionUtils;
import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.repay.dao.RepayDao;
import com.duanrong.business.repay.model.Repay;

@Repository
public class RepayDaoImpl extends BaseDaoImpl<Repay> implements RepayDao {

	public RepayDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.repay.mapper.RepayMapper");
	}


	@Override
	public void updateByLoanId(Repay repay) {
		this.getSqlSession().update(getMapperNameSpace() + ".updateByLoanId", repay);
		
	}
	@Override
	public Repay get(Serializable id) {
		Repay repay = super.get(id);
		if (repay != null) {
			Loan loan = repay.getLoan();
			loan.setId(repay.getLoanId());
			if (loan != null) {
				List<Invest> invests = loan.getInvests();
				if (MyCollectionUtils.isNotBlank(invests)) {
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
	public List<Repay> getRepayLoan(String loanId) {
		
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getRepayByLoan", loanId);
	}

	@Override
	public Repay getRepayById(String id) {
		
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getById", id);
	}

	@Override
	public List<Repay> getRepayList(Date date) {		
		return getSqlSession().selectList(
				getMapperNameSpace() + ".getRapayList", date);
	}

	@Override
	public List<RepayMail> getInterval(int days) {
	
		return getSqlSession().selectList(
				getMapperNameSpace() + ".getInterval",days);
	}

	@Override
	public List<RepayMail> getRepayInterval(String userId, int days) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("days", days);
		return getSqlSession().selectList(
				getMapperNameSpace() + ".getRepayInterval",map);
	}

	@Override
	public List<RepayMail> getRepayFinishInterval(int days) {
		Map<String, Object> map = new HashMap<>();
		map.put("days", days);
		return getSqlSession().selectList(
				getMapperNameSpace() + ".getRepayFinishInterval",map);
	}


	@Override
	public List<Repay> getRepayLoan() {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getRepayByLoan2");
	}


	@Override
	public List<Repay> getWaitRepay() {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getWaitRepay");
	}
	

}
