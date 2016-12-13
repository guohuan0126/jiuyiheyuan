package com.duanrong.dataAnalysis.business.loan.dao;

import java.math.BigDecimal;

import com.duanrong.dataAnalysis.business.loan.model.LoanData;

public interface LoanDataDao {
 
	public LoanData getLoanData();

	public BigDecimal getDemandTransferin();

	public BigDecimal getDemandTreasureLoanMoney();

	public BigDecimal getDemandTreasureSubMoney();
}
