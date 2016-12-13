package com.duanrong.cps.business.loan.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.cps.business.loan.dao.LoanDao;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.loan.service.LoanService;



/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : lxw
 * 
 */
@Service("loanService")
public class LoanServiceImpl implements LoanService {
	@Resource
	LoanDao loanDao;

	/**
	 * 查询可以推送网贷天眼的项目列表
	 * 
	 * @param pageNo
	 * @param loan
	 * @return
	 */
	public PageInfo<Loan> getwaitPushLoanList(String pageNo, Loan loan) {
		PageHelper.startPage(Integer.parseInt(pageNo), 10);
		List<Loan> lst = loanDao.getWaitPushLoanList(loan);
		return new PageInfo<>(lst);
	}



	@Override
	public PageInfo<Loan> getBsyWaitPushLoanList(String pageNo, Loan loan) {
		PageHelper.startPage(Integer.parseInt(pageNo), 10);
		List<Loan> lst = loanDao.getBsyWaitPushLoanList(loan);
		return new PageInfo<>(lst);
	}


	/**
	 * 查询推送到比搜益的项目列表
	 * @param pageNo
	 * @param loan
	 * @return
	 */
	@Override
	public PageInfo<Loan> getBsyLoanHistory(String pageNo, Map<String,Object>paramMap) {
		PageHelper.startPage(Integer.parseInt(pageNo), 10);
		List<Loan> lst = loanDao.getBsyLoanHistory(paramMap);
		return new PageInfo<>(lst);		
	}



	public Loan get(String id) {
		Loan loan = loanDao.get(id);
		return loan;
	}
}
