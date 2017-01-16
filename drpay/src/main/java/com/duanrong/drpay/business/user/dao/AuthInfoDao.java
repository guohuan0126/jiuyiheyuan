package com.duanrong.drpay.business.user.dao;

import java.util.List;

import base.dao.BaseDao;

import com.duanrong.drpay.business.user.model.AuthInfo;

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

	/**
	 * 统计天或月发送验证码的数量
	 * 
	 * @param mobileNumber
	 *            手机号
	 * @param scope
	 *            today | month
	 * @return
	 */
	public Long getAuthInfoDateScope(String mobileNumber, String scope);
}
