package com.duanrong.cps.business.loan.dao.impl;


import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageInfo;

import com.duanrong.cps.business.loan.dao.LoanDao;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.netloaneye.model.NetLoanModel;

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
		this.setMapperNameSpace("com.duanrong.cps.business.loan.mapper.LoanMapper");
	}

	
	/**
	 * 查询可以推送网贷天眼的项目列表
	 * @param loan
	 * @return
	 */
	public List<Loan> getWaitPushLoanList(Loan loan){
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".getWaitPushLoanList", loan);
	}
	
	/**
	 * 查询推送记录
	 */
	public List<Loan> getPushRecords(Loan loan){
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".getPushRecords",loan);
	}
	
	public Loan getP2pEyeLoanByLoanId(String id){
		return this.getSqlSession().selectOne(getMapperNameSpace()+".getP2pEyeLoanByLoanId",id);
	}
	
	public NetLoanModel getNetLoanCount(Loan loan){
		return this.getSqlSession().selectOne(getMapperNameSpace()+".getNetLoanCount");
	}
	
	public NetLoanModel getNetLoanSum(Loan loan){
		return this.getSqlSession().selectOne(getMapperNameSpace()+".getNetLoanSum");
	}

	@Override
	public int updateLoan(Loan loan) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(
				this.getMapperNameSpace() + ".update", loan);
	}

	@Override
	public List<Loan> getBsyWaitPushLoanList(Loan loan) {
		 	return this.getSqlSession().selectList(this.getMapperNameSpace()+".getBsyWaitPushLoanList", loan);
	}


	@Override
	public List<Loan> getBsyLoanByLoanId(List idList) {
		return this.getSqlSession().selectList(getMapperNameSpace()+".getBsyLoanByLoanId",idList);
	}

	/**
	 * 查询推送到比搜益的项目列表
	 * @param pageNo
	 * @param loan
	 * @return
	 */
	@Override
	public List<Loan> getBsyLoanHistory(Map<String,Object>paramMap) {
		
		return this.getSqlSession().selectList(getMapperNameSpace()+".pageLiteBsyLoanHistory",paramMap);
	}


	@Override
	public int getBorrowCount(String date) {
		return this.getSqlSession().selectOne(getMapperNameSpace()+".getBorrowCount",date);
	}


	@Override
	public Double getInvestAllMoney(String date) {
		return this.getSqlSession().selectOne(getMapperNameSpace()+".getinvestAllMoney",date);
	}


	@Override
	public Double getAllWaitBackMoney(String date) {
		return this.getSqlSession().selectOne(getMapperNameSpace()+".getAllWaitBackMoney",date);
	}


	@Override
	public Loan getLoanInfo(String loanId) {
		return this.getSqlSession().selectOne(getMapperNameSpace()+".getLoanInfo",loanId);
	}
	
}
