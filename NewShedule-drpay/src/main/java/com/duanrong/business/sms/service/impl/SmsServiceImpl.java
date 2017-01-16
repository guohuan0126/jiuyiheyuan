package com.duanrong.business.sms.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.Log;
import base.pagehelper.PageInfo;

import com.duanrong.business.sms.SmsConstants;
import com.duanrong.business.sms.dao.SmsDao;
import com.duanrong.business.sms.dao.UserMessageTemplateDao;
import com.duanrong.business.sms.model.Sms;
import com.duanrong.business.sms.model.UserMessageTemplate;
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

	@Resource
	UserMessageTemplateDao userMessageTemplateDao;
	
	@Async
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
					log.errLog("发送短信异常，mobile:"+mobileNumber, e);
				}

			}
		}
	}

	@Async
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
					SMSSend.batchSend1(mobileNumber, content);
					Sms sms = new Sms();
					sms.setId(IdGenerator.randomUUID());
					sms.setUserId(userId);
					sms.setMobileNumber(mobileNumber);
					sms.setContent(content);
					sms.setTime(new Date());
					sms.setType(type);
					smsDao.insert(sms);
				} catch (Exception e) {
					log.errLog("发送短信异常，mobile:"+mobileNumber, e);
				}

			}
		}
	}
	
	@Override
	public PageInfo<Sms> findPageInfo(int pageNo, int pageSize, Map map) {
		return smsDao.pageInfo(pageNo, pageSize, map);
	}

	@Override
	@Async
	@Transactional(rollbackFor = Exception.class)
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
					log.errLog("发送短信异常，mobile:"+mobileNumber, e);
				}
			}
		}
	}

	@Override
	public List<Sms> getSmsNum(Map map) {
		return smsDao.getSmsNum(map);
	}

	@Override
	public void sendSmsForMobile(String mobileNumber, String content,
			String type) {
		if (StringUtils.isNotBlank(mobileNumber)
				&& StringUtils.isNotBlank(content)) {
			if (StringUtils.isBlank(type)) {
				type = SmsConstants.WITHOUT;
			}
			try {			
				SMSSend.batchSend(mobileNumber, content);
				User u = userDao.getUserByMobileNumber(mobileNumber);
				Sms sms = new Sms();
				sms.setId(IdGenerator.randomUUID());
				String userId = mobileNumber;
				if(null != u && null != u.getUserId()){
					userId = u.getUserId();
				}
				sms.setUserId(u.getUserId());
				sms.setMobileNumber(userId);
				sms.setContent(content);
				sms.setTime(new Date());
				sms.setType(type);
				smsDao.insert(sms);
			} catch (Exception e) {
				log.errLog("发送短信异常，mobile:"+mobileNumber, e);
			}

		}

	}

	@Override
	public String operateTemplate(String type) {
		UserMessageTemplate userMessageTemplate = userMessageTemplateDao
				.get(type + "_sms");
		String template = userMessageTemplate.getTemplate();
		return template;
	}
}
