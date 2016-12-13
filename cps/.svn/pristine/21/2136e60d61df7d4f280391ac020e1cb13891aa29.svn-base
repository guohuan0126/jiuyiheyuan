package com.duanrong.cps.business.loan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duanrong.cps.business.loan.dao.LoanDataDao;
import com.duanrong.cps.business.loan.model.LoanData;
import com.duanrong.cps.business.loan.service.LoanDataService;
@Service
public class LoanDataServiceImpl implements LoanDataService {

	@Autowired
	private LoanDataDao loanDataDao;
	@Override
	public LoanData getLoanData() {
		return loanDataDao.getLoanData();
	}

}
