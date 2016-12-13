package com.duanrong.thirdPartyInterface.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.dao.BaseDao;
import base.model.Invest;
import base.model.Loan;

import com.duanrong.thirdPartyInterface.service.WDTYService;

@Service
public class WDTYServiceImpl implements WDTYService {
	@Resource
	BaseDao<Loan> baseDaoLoan;

	@Resource
	BaseDao<Invest> baseDaoInvest;

	public List<Loan> findAll() {
		baseDaoLoan
				.setMapperNameSpace("com.duanrong.thirdPartyInterface.mapper.LoanMapper.");
		List<Loan> loanList = baseDaoLoan.findAll();
		return loanList;
	}

	public double getInvestedMoney(String loanId) {
		baseDaoInvest
				.setMapperNameSpace("com.duanrong.thirdPartyInterface.mapper.InvestMapper.");
		double investedMoney = baseDaoInvest.getInvestedMoney(loanId);
		return investedMoney;
	}

	@Override
	public List<Invest> findAllInvests(Map<Object, Object> map) {
		baseDaoInvest
				.setMapperNameSpace("com.duanrong.thirdPartyInterface.mapper.InvestMapper.");
		List<Invest> investList = baseDaoInvest.getWdtyInvestsByLoanId(map);
		return investList;
	}

	@Override
	public List<Loan> findAllLoans(Map<Object, Object> map) {
		baseDaoLoan
				.setMapperNameSpace("com.duanrong.thirdPartyInterface.mapper.LoanMapper.");
		List<Loan> loanList = baseDaoLoan.findAllLoans(map);
		return loanList;
	}

	@Override
	public Long getInvestNum(String loanId) {
		baseDaoInvest
				.setMapperNameSpace("com.duanrong.thirdPartyInterface.mapper.InvestMapper.");
		long investedNum = baseDaoInvest.getInvestedNum(loanId);
		return investedNum;
	}

	@Override
	public long getAllLoansSize() {
		baseDaoLoan
				.setMapperNameSpace("com.duanrong.thirdPartyInterface.mapper.LoanMapper.");
		return baseDaoLoan.findAllLoansSize();
	}

	@Override
	public long findLoansByTime(String time_from, String time_to) {
		baseDaoLoan
				.setMapperNameSpace("com.duanrong.thirdPartyInterface.mapper.LoanMapper.");
		return baseDaoLoan.findLoansByTime(time_from,time_to);
	}

}
