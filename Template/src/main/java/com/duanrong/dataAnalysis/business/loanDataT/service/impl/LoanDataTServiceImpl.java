package com.duanrong.dataAnalysis.business.loanDataT.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.dataAnalysis.business.loanDataT.dao.LoanDataTDao;
import com.duanrong.dataAnalysis.business.loanDataT.model.LoanDataT;
import com.duanrong.dataAnalysis.business.loanDataT.service.LoanDataTService;
@Service
public class LoanDataTServiceImpl implements LoanDataTService{

	@Resource
	private LoanDataTDao loanDataTDao;
	@Override
	public LoanDataT getLoanDataT() {
		// TODO Auto-generated method stub
		return loanDataTDao.getLoanDataT();
	}

}
