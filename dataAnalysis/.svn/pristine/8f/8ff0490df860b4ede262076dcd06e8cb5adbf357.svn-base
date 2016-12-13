package com.duanrong.dataAnalysis.business.userSource.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duanrong.dataAnalysis.business.userSource.dao.userSourceDao;
import com.duanrong.dataAnalysis.business.userSource.model.UserSource;
import com.duanrong.dataAnalysis.business.userSource.service.userSourceService;

@Service
public class userSourceServiceImpl implements userSourceService {

	@Autowired
	userSourceDao userSourceDao;
	@Override
	public UserSource getUserCount() {
		
		return userSourceDao.getUserCount();
	}

	
}
