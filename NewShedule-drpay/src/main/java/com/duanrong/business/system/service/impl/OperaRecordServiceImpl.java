package com.duanrong.business.system.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.business.system.dao.OperaRecordDao;
import com.duanrong.business.system.model.OperaRecord;
import com.duanrong.business.system.service.OperaRecordService;

@Service
public class OperaRecordServiceImpl implements OperaRecordService {

	@Resource
	OperaRecordDao operaRecordDao;

	@Override
	@Async
	public void insertRecord(String title,String userid,String content) {
		OperaRecord operaRecord=new OperaRecord();
		operaRecord.setTitle(title);
		operaRecord.setUserid(userid);
		operaRecord.setContent(content);
		operaRecord.setTime(new Date());
		operaRecordDao.insert(operaRecord);
	}

	@Override
	public PageInfo<OperaRecord> findPageInfo(int pageNo, int pageSize, Map map) {
		return operaRecordDao.pageInfo(pageNo, pageSize, map);
	}
}
