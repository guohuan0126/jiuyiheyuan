package com.duanrong.drpay.business.demand.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.drpay.business.demand.dao.AvailableMoneyRecordDao;
import com.duanrong.drpay.business.demand.model.AvailableMoneyRecord;
import com.duanrong.drpay.business.demand.service.AvailableMoneyRecordService;

import util.Log;
import base.pagehelper.PageInfo;

@Service
public class AvailableMoneyRecordServiceImpl implements AvailableMoneyRecordService {

	@Resource
	AvailableMoneyRecordDao availableMoneyRecordDao;
	
	@Resource
	Log log;

	@Override
	public PageInfo<AvailableMoneyRecord> findPageInfo(int pageNo, int pageSize) {
		return availableMoneyRecordDao.pageInfo(pageNo, pageSize);
	}

	@Override
	public void insert(AvailableMoneyRecord availableMoneyRecord) {
		availableMoneyRecordDao.insert(availableMoneyRecord);
	}

	@Override
	public void del(AvailableMoneyRecord availableMoneyRecord) {
		availableMoneyRecordDao.del(availableMoneyRecord);
	}
	
	@Override
	public void update(AvailableMoneyRecord availableMoneyRecord) {
		availableMoneyRecordDao.update(availableMoneyRecord);		
	}
	
	@Override
	public AvailableMoneyRecord getEndLine() {
		return availableMoneyRecordDao.getEndLine();
	}
}
