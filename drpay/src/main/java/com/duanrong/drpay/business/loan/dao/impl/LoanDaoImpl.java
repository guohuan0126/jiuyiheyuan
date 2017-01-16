package com.duanrong.drpay.business.loan.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.drpay.business.invest.model.Invest;
import com.duanrong.drpay.business.loan.dao.LoanDao;
import com.duanrong.drpay.business.loan.model.Loan;
import com.duanrong.drpay.business.loan.model.SubLoan;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-8-28 下午12:46:30
 * @Description : drsoa com.duanrong.business.loan.dao.impl LoanDaoImpl.java
 * 
 */
@Repository
public class LoanDaoImpl extends BaseDaoImpl<Loan> implements LoanDao {

	public LoanDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.loan.mapper.LoanMapper");
	}

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午5:29:29
	 * @param userID
	 * @return
	 */
	@Override
	public List<Loan> getLoansByGroupCondition(Loan loan) {
		List<Loan> loans = this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getLoansByGroupCondition", loan);
		return loans;
	}

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author 孙铮
	 * @time 2014-9-1 下午12:01:22
	 * @param invest
	 * @return
	 */
	@Override
	public List<Invest> getInvestsByGroupCondition(Invest invest) {
		List<Invest> invests = this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getInvestsByGroupCondition",
				invest);
		return invests;
	}

	
	@Override
	public Loan getWithLock(String id) {
			return this.getSqlSession().selectOne(
					this.getMapperNameSpace() + ".getWithLock", id);
	}

	@Override
	public List<SubLoan> getVehicle(String loanId) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getVehicle", loanId);
	}

	@Override
	public List<SubLoan> getAgricultureForkLoans(String loanId) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getAgriculture", loanId);
	}

	@Override
	public void updateBatchVehicle(List<SubLoan> subloans) {
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".updateBatchVehicle", subloans);
	}

	@Override
	public void updateBatchAgricultureForkLoans(List<SubLoan> subloans) {
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".updateBatchAgricultureForkLoans", subloans);
	}

	@Override
	public List<SubLoan> getVehicleByGroupCondition(String loanId, int drpayStatus) {
		SubLoan subloan = new SubLoan();
		subloan.setLoanId(loanId);
		subloan.setDrpayStatus(drpayStatus);
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getVehicleByGroupCondition", subloan);

	}

	@Override
	public List<SubLoan> getAgricultureByGroupCondition(String loanId, int drpayStatus) {
		SubLoan subloan = new SubLoan();
		subloan.setLoanId(loanId);
		subloan.setDrpayStatus(drpayStatus);
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getAgricultureByGroupCondition", subloan);
	}

	@Override
	public void updateVehicle(SubLoan subloan) {
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".updateVehicle", subloan);
	}

	@Override
	public void updateAgricultureForkLoans(SubLoan subloan) {
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".updateAgricultureForkLoans", subloan);
	}

}
