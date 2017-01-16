package com.duanrong.business.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.business.user.dao.UserVisitInfoDao;
import com.duanrong.business.user.model.UserVisitInfo;
import com.duanrong.business.user.service.UserVisitInfoService;

@Service
public class UserVisitInfoServiceImpl implements UserVisitInfoService {

	@Resource
	UserVisitInfoDao userVisitInfoDao;

	@Override
	public List<UserVisitInfo> findAllRemarks(UserVisitInfo userVisitInfo) {
		return userVisitInfoDao.find(userVisitInfo);
	}

	@Override
	public void delete(String id) {
		UserVisitInfo t=new UserVisitInfo();
		t.setId(id);
		 userVisitInfoDao.update(t);
	}
}
