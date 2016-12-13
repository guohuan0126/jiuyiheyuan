package com.duanrong.business.user.dao;

import java.util.Date;
import java.util.List;

import base.dao.BaseDao;

import com.duanrong.business.user.model.UserLoginLog;

/**
 * @Description: 登录日志
 * @Author: 林志明
 * @CreateDate: Nov 19, 2014
 */
public interface UserLoginLogDao extends BaseDao<UserLoginLog> {
	public UserLoginLog getByUserId(String userId);

	/**
	 * 获取上次用户登录时间
	 * 
	 * @param userId
	 * @return
	 */
	public List<Date> getLastLoginTime(String userId);

}
