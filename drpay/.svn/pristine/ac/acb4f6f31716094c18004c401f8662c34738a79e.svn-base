package com.duanrong.drpay.business.invest.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.drpay.business.invest.dao.InvestDao;
import com.duanrong.drpay.business.invest.model.Invest;
import com.duanrong.drpay.business.invest.model.InvestRedpacket;
import com.duanrong.drpay.business.invest.model.InvestSubLoan;

@Repository
public class InvestDaoImpl extends BaseDaoImpl<Invest> implements InvestDao {

	public InvestDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.invest.mapper.InvestMapper");
	}

	/**
	 * 根据项目ID查询有效募集金额（排除流标）
	 * @param loanId
	 * @return
	 */
	@Override
	public Double getInvestSumMoneyByLoan(String loanId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInvestSumMoneyByLoan", loanId);
	}

	/**
	 * 根据项目ID、用户ID查询有效投资金额总和（排除流标）
	 * 
	 * @param userId  用户ID
	 * @param loanId  项目ID
	 * @return
	 */
	@Override
	public Double getInvestSumMoneyByUser(String userId, String loanId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("loanId", loanId);
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInvestSumMoneyByUser", params);
	}
	
	/**
	 * 根据条件查询用户投资的项目
	 * 
	 * @return
	 */
	@Override
	public List<Invest> getInvestLoan(Invest invest) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getInvestLoan", invest);
	}
	
	@Override
	public void insertInvestRedpacket(InvestRedpacket investRedpacket) {
		this.getSqlSession().insert(
				getMapperNameSpace() + ".insertInvestRedpacket",
				investRedpacket);
	}
	
	@Override
	public int getInvestSeccessByLoanId(String loanId) {
		return getSqlSession().selectOne(
				getMapperNameSpace() + ".getInvestSeccessByLoanId", loanId);
	}

	@Override
	public long getInvestSuccessNotNewRecordByUserId(String userId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInvestSuccessNotNewRecordByUserId", userId);
	}


	@Override
	public Long getInvestSuccessRecordByUserId(String userId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInvestSuccessRecordByUserId", userId);
	}


	@Override
	public void insertBatchInvestSubloan(List<InvestSubLoan> investSubLoans) {
		this.getSqlSession().insert(
				getMapperNameSpace() + ".batchInsertInvestSubLoan", investSubLoans);
	}

	@Override
	public double getInvestSubloanMoneyByInvestId(String investId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInvestSubloanMoneyByInvestId", investId);
	}

	@Override
	public List<InvestSubLoan> getInvestSubloanByInvestId(String investId) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getInvestSubloanByInvestId", investId);
	}

	@Override
	public List<InvestSubLoan> getInvestSubLoans(String subloanId) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getInvestSubLoans", subloanId);
	}

	@Override
	public InvestSubLoan getInvestSubLoan(String investSubloanId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInvestSubloan", investSubloanId);
	}

	@Override
	public void insertInvestSubloan(InvestSubLoan investSubLoan) {
		this.getSqlSession().insert(
				getMapperNameSpace() + ".insertInvestSubLoan", investSubLoan);
	}

	@Override
	public void updateInvestSubloan(InvestSubLoan investSubLoan) {
		this.getSqlSession().update(
				getMapperNameSpace() + ".updateInvestSubloan", investSubLoan);
	}

	@Override
	public void updateInvestSubloanByInvestId(String investId) {
		this.getSqlSession().update(
				getMapperNameSpace() + ".updateInvestSubloanByInvestId", investId);
	}

}
