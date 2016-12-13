package com.duanrong.cps.business.loan.dao.impl;

import org.springframework.stereotype.Repository;

import com.duanrong.cps.business.loan.dao.LoanDataDao;
import com.duanrong.cps.business.loan.model.LoanData;

import base.dao.impl.BaseDaoImpl;
@Repository
public class LoanDataDaoImpl extends BaseDaoImpl<LoanData> implements LoanDataDao {

	
	public LoanDataDaoImpl(){
		this.setMapperNameSpace("com.duanrong.dataAnalysis.business.Loan.mapper.LoanDataMapper");
	}
	@Override
	public LoanData getLoanData() {
		return this.getSqlSession().selectOne("getLoanData");
	}

}
