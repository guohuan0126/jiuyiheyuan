package com.duanrong.business.autoinvest.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.business.autoinvest.dao.AutoInvestHistoryDao;
import com.duanrong.business.autoinvest.model.AutoInvestHistory;
import com.duanrong.business.autoinvest.service.AutoInvestHistoryService;

@Service
public class AutoInvestHistoryServiceImpl implements AutoInvestHistoryService {

	@Resource
	AutoInvestHistoryDao autoInvestHistoryDao;
	
	@Override
	public void insert(AutoInvestHistory autoInvestHistory) {
		autoInvestHistoryDao.insert(autoInvestHistory);
	}

}
