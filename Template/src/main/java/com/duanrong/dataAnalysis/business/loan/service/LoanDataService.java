package com.duanrong.dataAnalysis.business.loan.service;

import java.math.BigDecimal;

import com.duanrong.dataAnalysis.business.loan.model.LoanData;

public interface LoanDataService {

	
	public LoanData getLoanData();

	public BigDecimal getDemandTransferin();

	public BigDecimal getDemandTreasureLoanMoney();

	public BigDecimal getDemandTreasureSubMoney();
}
