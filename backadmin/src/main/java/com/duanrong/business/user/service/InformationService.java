package com.duanrong.business.user.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import base.model.PageData;
import base.pagehelper.PageInfo;

import com.duanrong.business.user.model.Information;

/**
 * @Description: 用户站内信服务
 * @Author: 林志明
 * @CreateDate: Sep 4, 2014
 */
public interface InformationService {

	/**
	 * 发送站内信
	 * 
	 * @param userId
	 *            用户ID
	 * @param title
	 *            标题
	 * @param content
	 *            站内信内容
	 * @return
	 */
	@Async
	public void sendInformation(String userId, String title, String content);

	/**
	 * 根据用户ID获得用户所有站内信
	 * 
	 * @param userId
	 * @return
	 */
	public List<Information> readInformationByUserId(String userId);

	/**
	 * 获得未读站内信的数量
	 * 
	 * @param userId
	 * @return
	 */
	public Long readNotReadByUserId(String userId);

	/**
	 * 设置站内信已读
	 * 
	 * @param userId
	 */
	public void updateRead(String userId);

	/**
	 * 分页查询
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页显示的记录数
	 */
	public PageData<Information> readPaging(int pageNo, int pageSize,
			Information information);

	/**
	 * 分页
	 * 
	 * @param map
	 * @return
	 */
	public PageInfo<Information> readPageInfo(int pageNo, int pageSize, Map map);

	public void sendInformation(String investUserID, String title,
			String sendContent, Date date);
}