package com.duanrong.business.loan.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import util.Log;
import base.pagehelper.PageInfo;

import com.duanrong.business.loan.dao.LoanVipDao;
import com.duanrong.business.loan.model.LoanVip;
import com.duanrong.business.loan.service.LoanVipService;
import com.duanrong.business.system.service.OperaRecordService;

@Service("loanVipService")
public class LoanVipServiceImpl implements LoanVipService {
	@Resource
	LoanVipDao loanVipDao;

	@Resource
	Log log;

	@Resource
	OperaRecordService operaRecordService;

	@Override
	public PageInfo<LoanVip> findPageInfo(int pageNo, int pageSize) {
		return loanVipDao.pageInfo(pageNo, pageSize);
	}

	@Override
	public void insert(LoanVip loanVip) {
		loanVipDao.insert(loanVip);
	}

	@Override
	public LoanVip get(String userId) {
		return loanVipDao.get(userId);
	}

	@Override
	public void update(LoanVip loanVip) {
		loanVipDao.update(loanVip);
	}

}
