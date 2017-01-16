package com.duanrong.business.loan.service;


import base.pagehelper.PageInfo;

import com.duanrong.business.loan.model.LoanVip;

public interface LoanVipService {
	 public PageInfo<LoanVip> findPageInfo(int pageNo, int pageSize);
	 public void insert(LoanVip loanVip);
	 public LoanVip get(String userId);
	 public void update(LoanVip loanVip);
}
