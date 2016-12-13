package com.duanrong.business.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.business.app.dao.AppFeedbackDao;
import com.duanrong.business.app.model.AppFeedback;
import com.duanrong.business.app.service.AppFeedbackService;
import com.duanrong.business.loan.model.Loan;

@Service
public class AppFeedbackServiceImpl implements AppFeedbackService {
   
	
	@Resource
	AppFeedbackDao appFeedbackDao;
	
	@Override
	public PageInfo<AppFeedback> pageLite(int pageNo, int pageSize, AppFeedback app) {
		
		return appFeedbackDao.pageLite(pageNo, pageSize, app);
	}

	@Override
	public List<AppFeedback> find(AppFeedback app) {
		
		return appFeedbackDao.find(app);
	}

}