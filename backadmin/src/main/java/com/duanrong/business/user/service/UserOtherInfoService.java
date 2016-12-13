package com.duanrong.business.user.service;

import com.duanrong.business.user.model.UserOtherInfo;

/**
 * @Description: 用户地址、邮编、来源、用户IP等服务
 * @Author: 林志明
 * @CreateDate: Sep 12, 2014
 */
public interface UserOtherInfoService {
	public void insertOrUpdate(UserOtherInfo userOtherInfo);

	public UserOtherInfo read(String id);

	/**
	 * 更新但不包含user
	 * 
	 * @param userOtherInfo
	 */
	public void insertOrUpdateNoUser(UserOtherInfo userOtherInfo);
	/**
	 * add by wangjing
	 */
	public void insertUpdate (UserOtherInfo userOtherInfo,String weixin,String qq,String remark,String visitType, String admin);
	
	public void update(UserOtherInfo userOtherInfo);

}