package com.duanrong.business.maxinvestrecord.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.business.maxinvestrecord.dao.MaxInvestRecordDao;
import com.duanrong.business.maxinvestrecord.model.MaxInvestRecord;
import com.duanrong.business.maxinvestrecord.service.MaxInvestRecordService;

@Service
public class MaxInvestRecordServiceImpl implements MaxInvestRecordService {
	@Resource
	MaxInvestRecordDao maxInvestRecordDao;
	@Override
	public MaxInvestRecord getInvestRecord(String userId) {
		return this.maxInvestRecordDao.getInvestRecord(userId);
	}

	@Override
	public void insert(MaxInvestRecord record) {
		this.maxInvestRecordDao.insert(record);
	}

	@Override
	public void update(MaxInvestRecord record) {
		this.maxInvestRecordDao.update(record);
	}

	@Override
	public List<MaxInvestRecord> getRecord(int times) {
		return maxInvestRecordDao.getRecord(times);
	}

	@Override
	public int getRecordCount() {
		return this.maxInvestRecordDao.getRecordCount();
	}
}
