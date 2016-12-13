package com.duanrong.business.repaytracking.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.business.repaytracking.dao.RepayTrackingDao;
import com.duanrong.business.repaytracking.model.RepayTracking;
import com.duanrong.business.repaytracking.service.RepayTrackingService;

@Service
public class RepayTrackingServiceImpl implements RepayTrackingService {

	@Resource
	RepayTrackingDao repayTrackingDao;

	@Override
	public PageInfo<RepayTracking> getByUserId(int pageNo, int pageSize,
			String userId) {
		return repayTrackingDao.getByUserId(pageNo, pageSize, userId);
	}

}
