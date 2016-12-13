package com.duanrong.business.sms.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.Log;
import base.pagehelper.PageInfo;

import com.duanrong.business.sms.SmsConstants;
import com.duanrong.business.sms.dao.SmsDao;
import com.duanrong.business.sms.model.Sms;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.User;
import com.duanrong.sms.SMSSend;
import com.duanrong.util.IdGenerator;

@Service
public class SmsServiceImpl implements SmsService {

	@Resource
	UserDao userDao;

	@Resource
	SmsDao smsDao;

	@Resource
	Log log;

	@Override
	public void sendSms(String userId, String content, String type) {
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(content)) {
			if (StringUtils.isBlank(type)) {
				type = SmsConstants.WITHOUT;
			}
			User user = userDao.get(userId);
			String mobileNumber = user.getMobileNumber();
			if (StringUtils.isNoneBlank(mobileNumber)) {
				try {
					SMSSend.batchSend(mobileNumber, content);					
					Sms sms = new Sms();
					sms.setId(IdGenerator.randomUUID());
					sms.setUserId(userId);
					sms.setMobileNumber(mobileNumber);
					sms.setContent(content);
					sms.setTime(new Date());
					sms.setType(type);
					smsDao.insert(sms);

				} catch (Exception e) {
					log.errLog("调用短信发送工具类失败",e);	
				}

			}
		}
	}
	
	@Override
	public void sendSms1(String userId, String content, String type) {
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(content)) {
			if (StringUtils.isBlank(type)) {
				type = SmsConstants.WITHOUT;
			}
			User user = userDao.get(userId);
			String mobileNumber = user.getMobileNumber();
			if (StringUtils.isNoneBlank(mobileNumber)) {		
				try {
					SMSSend.batchSend(mobileNumber, content);					
					Sms sms = new Sms();
					sms.setId(IdGenerator.randomUUID());
					sms.setUserId(userId);
					sms.setMobileNumber(mobileNumber);
					sms.setContent(content);
					sms.setTime(new Date());
					sms.setType(type);
					smsDao.insert(sms);

				} catch (Exception e) {
					log.errLog("调用短信发送工具类失败",e);	
				}

			}
		}
	}

	@Override
	public PageInfo<Sms> readPageInfo(int pageNo, int pageSize, Map map) {
		return smsDao.pageInfo(pageNo, pageSize, map);
	}

	@Override
	public void sendSms(String userId, String content, String type, Date date) {
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(content)) {
			if (StringUtils.isBlank(type)) {
				type = SmsConstants.WITHOUT;
			}
			User user = userDao.get(userId);
			String mobileNumber = user.getMobileNumber();
			if (StringUtils.isNoneBlank(mobileNumber)) {
				try {
					SMSSend.batchSend(mobileNumber, content);					
					Sms sms = new Sms();
					sms.setId(IdGenerator.randomUUID());
					sms.setUserId(userId);
					sms.setMobileNumber(mobileNumber);
					sms.setContent(content);
					sms.setTime(new Date());
					sms.setType(type);
					smsDao.insert(sms);

				} catch (Exception e) {
					log.errLog("调用短信发送工具类失败",e);	
				}
			}
		}
	}

	@Override
	public List<Sms> readSmsNum(Map map) {
		return smsDao.getSmsNum(map);
	}

	
	
}
