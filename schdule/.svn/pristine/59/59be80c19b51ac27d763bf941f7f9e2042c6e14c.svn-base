package com.duanrong.business.user.dao;


import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.user.model.AuthInfo;





/**
 * 
 * @author 尹逊志
 * @date 2014-8-29上午11:14:48
 */
public interface AuthInfoDao extends BaseDao<AuthInfo> {
	/**
	 * 
	 * 
	 * @param mobileNumber
	 *            手机号
	 * @param authCode
	 *            验证码
	 * @param type
	 *            验证码类型
	 * @return
	 */
	public List<AuthInfo> getAuthInfo(String mobileNumber, String authCode,
			String type);
	PageInfo<AuthInfo> pageInfo(int pageNo, int pageSize, Map map);
	public List<AuthInfo> getAuthNum(Map map);
	public void updateauth(AuthInfo authInfo);
}
