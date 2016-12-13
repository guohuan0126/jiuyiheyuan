package com.duanrong.business.demand.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.business.demand.dao.AvailableMoneyRecordDao;
import com.duanrong.business.demand.model.AvailableMoneyRecord;
import com.duanrong.business.demand.service.AvailableMoneyRecordService;

@Service
public class AvailableMoneyRecordServiceImpl implements
		AvailableMoneyRecordService {

	@Resource
	AvailableMoneyRecordDao availableMoneyRecordDao;


	@Override
	public void update(AvailableMoneyRecord availableMoneyRecord) {
		availableMoneyRecordDao.update(availableMoneyRecord);
	}

	
	@Override
	public AvailableMoneyRecord getEndLine() {
		return availableMoneyRecordDao.getEndLine();
	}
}
