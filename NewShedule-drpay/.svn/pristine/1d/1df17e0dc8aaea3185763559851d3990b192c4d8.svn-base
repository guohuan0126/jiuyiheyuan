package com.duanrong.business.app.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.business.app.dao.APPDao;
import com.duanrong.business.app.dao.AppFeedbackDao;
import com.duanrong.business.app.model.APP;
import com.duanrong.business.app.model.AppFeedback;
import com.duanrong.business.app.service.AppService;
import com.duanrong.business.loan.model.Loan;

@Service
public class AppServiceImpl implements AppService {

	@Resource
	AppFeedbackDao appFeedbackDao;

	@Resource
	APPDao appDao;

	@Override
	public void saveAppFeedback(AppFeedback appFeedback) {
		appFeedbackDao.insert(appFeedback);
	}

	//@Override
	/*public APP getApp(String osName) {
		return appDao.get(osName);
	}*/

	@Override
	public void saveApp(APP app) {
		appDao.insert(app);
	}

	@Override
	public void updateApp(APP app) {
		appDao.update(app);
	}

	@Override
	public PageInfo<APP> findPageInfo(int pageNo, int pageSize, Map map) {
		return appDao.findPageInfo(pageNo,pageSize,map);
	}

	@Override
	public APP getAppById(String id) {
		return appDao.get(id);
	}


}
