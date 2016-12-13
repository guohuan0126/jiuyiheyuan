package com.duanrong.business.user.service;

import base.pagehelper.PageInfo;

import com.duanrong.business.activity.model.Activity;
import com.duanrong.business.user.model.UserLoginLog;

/**
 * @Description: 登录日志
 * @Author: 林志明
 * @CreateDate: Nov 19, 2014
 */
public interface UserLoginLogService {
	public void insert(UserLoginLog userLoginLog);

	public UserLoginLog readByUserId(String userId);

	/**
	 * 获取上次用户登录时间
	 * 
	 * @param userId
	 * @return
	 */
	public String readLastLoginTime(String userId);
	
	/**
	 * 分页查询
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param loan
	 * @return
	 */
	public PageInfo<UserLoginLog> readPageLite(int pageNo, int pageSize, UserLoginLog loginLog);

}