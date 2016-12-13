package com.duanrong.dataAnalysis.business.loan.dao.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;

import com.duanrong.dataAnalysis.business.loan.dao.LoanDataDao;
import com.duanrong.dataAnalysis.business.loan.model.LoanData;

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
	@Override
	public BigDecimal getDemandTransferin() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("getDemandTransferin");
	}
	@Override
	public BigDecimal getDemandTreasureLoanMoney() {
		return this.getSqlSession().selectOne("getDemandTreasureLoanMoney");
	}
	@Override
	public BigDecimal getDemandTreasureSubMoney() {
		return this.getSqlSession().selectOne("getDemandTreasureSubMoney");
	}

}
