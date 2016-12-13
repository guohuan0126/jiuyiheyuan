package com.duanrong.dataAnalysis.business.loan.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duanrong.dataAnalysis.business.loan.dao.LoanDataDao;
import com.duanrong.dataAnalysis.business.loan.model.LoanData;
import com.duanrong.dataAnalysis.business.loan.service.LoanDataService;
@Service
public class LoanDataServiceImpl implements LoanDataService {

	@Autowired
	private LoanDataDao loanDataDao;
	@Override
	public LoanData getLoanData() {
		return loanDataDao.getLoanData();
	}
	@Override
	public BigDecimal getDemandTransferin() {
		// TODO Auto-generated method stub
		return loanDataDao.getDemandTransferin();
	}
	@Override
	public BigDecimal getDemandTreasureLoanMoney() {
		// TODO Auto-generated method stub
		return loanDataDao.getDemandTreasureLoanMoney();
	}
	@Override
	public BigDecimal getDemandTreasureSubMoney() {
		// TODO Auto-generated method stub
		return loanDataDao.getDemandTreasureSubMoney();
	}

}
