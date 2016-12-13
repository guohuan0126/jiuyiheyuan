package com.duanrong.business.autoinvest.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import util.Log;
import base.pagehelper.PageInfo;
import com.duanrong.business.autoinvest.dao.AutoInvestDao;
import com.duanrong.business.autoinvest.model.AutoInvest;
import com.duanrong.business.autoinvest.service.AutoInvestService;
import com.duanrong.business.invest.InvestConstants;
import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.loan.dao.LoanDao;

@Service
public class AutoInvestServiceImpl implements AutoInvestService {

	@Resource
	AutoInvestDao autoInvestDao;
	
	@Resource
	LoanDao loanDao;
	
	@Resource
	InvestDao investDao;
	
	@Resource
	Log log;
	
	@Resource
	InvestService invesrService;
	
	
	@Override
	public void insert(AutoInvest autoInvest) {
		autoInvestDao.insert(autoInvest);
	}

	@Override
	public void settingAutoInvest(AutoInvest autoInvest) {

		AutoInvest tempAI = autoInvestDao.get(autoInvest.getUserId());

		if (tempAI != null) {

			// FIXME：是否有问题？
			autoInvest.setLastAutoInvestTime(new Date());
			autoInvest.setStatus(InvestConstants.AutoInvest.Status.OFF);
			autoInvestDao.update(autoInvest);			
		} else {
			autoInvest.setLastAutoInvestTime(new Date());
			autoInvestDao.insert(autoInvest);
		}

	}

	@Override
	public AutoInvest get(String id) {
		return autoInvestDao.get(id);
	}

	@Override
	public void update(AutoInvest autoInvest) {
		autoInvest.setInvestMoney(autoInvest.getMaxMoney());
		autoInvestDao.update(autoInvest);
	}

	@Override
	public Long getQueueNumber(String userId) {
		return autoInvestDao.getQueueNumber(userId);
	}

	@Override
	public Long getAutoInvestConut() {
		return autoInvestDao.getAutoInvestConut();
	}

	@Override
	public PageInfo<AutoInvest> findPageInfo(int pageNo, int pageSize,
			AutoInvest autoInvest) {
		return autoInvestDao.pageLite(pageNo, pageSize, autoInvest);
	}
	
}