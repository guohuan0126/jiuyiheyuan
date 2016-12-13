package com.duanrong.business.sms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import base.pagehelper.PageInfo;

import com.duanrong.business.sms.model.Sms;

/**
 * @Description: 短信处理
 * @Author: 林志明
 * @CreateDate: Nov 24, 2014
 */
public interface SmsService {
	/**
	 * 发送短信
	 * 
	 * @param userId
	 *            用户ID
	 * @param content
	 *            短信内容
	 * @param type
	 *            短信类型
	 * @return
	 */
	@Async
	public void sendSms(String userId, String content, String type);

	/**
	 * 发送短信
	 * 
	 * @param userId
	 *            用户ID
	 * @param content
	 *            短信内容
	 * @param type
	 *            短信类型
	 * @return
	 */
	@Async
	public void sendSms(String userId, String content, String type, Date date);
	
	@Async
	public void sendSms1(String userId, String content, String type);

	/**
	 * 分页
	 * 
	 * @param map
	 * @return
	 */
	public PageInfo<Sms> readPageInfo(int pageNo, int pageSize, Map map);
	public List<Sms> readSmsNum(Map map);

}