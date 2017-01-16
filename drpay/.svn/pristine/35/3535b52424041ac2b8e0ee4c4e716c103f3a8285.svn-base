package com.duanrong.drpay.business.autoinvest.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.drpay.business.autoinvest.dao.AutoInvestDao;
import com.duanrong.drpay.business.autoinvest.model.AutoInvest;
import com.duanrong.drpay.business.autoinvest.service.AutoInvestService;

@Service
public class AutoInvestServiceImpl implements AutoInvestService {

	@Resource
	AutoInvestDao autoInvestDao;

	@Override
	public void settingAutoInvest(AutoInvest autoInvest) {
		AutoInvest tempAI = autoInvestDao.get(autoInvest.getUserId());
		autoInvest.setLastAutoInvestTime(new Date());
		if (tempAI != null) {
			autoInvestDao.update(autoInvest);			
		} else {
			autoInvestDao.insert(autoInvest);
		}
	}

	@Override
	public AutoInvest query(String id) {
		return autoInvestDao.get(id);
	}

	@Override
	public void update(AutoInvest autoInvest) {
		autoInvestDao.update(autoInvest);
	}

	@Override
	public AutoInvest get(String id) {
		return autoInvestDao.get(id);
	}

}