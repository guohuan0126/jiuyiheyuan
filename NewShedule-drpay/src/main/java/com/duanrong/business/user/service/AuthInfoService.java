package com.duanrong.business.user.service;

import java.util.List;
import java.util.Map;

import base.exception.AuthInfoOutOfDateException;
import base.exception.NoMatchingObjectsException;
import base.pagehelper.PageInfo;

import com.duanrong.business.user.model.AuthInfo;
import com.duanrong.business.user.model.Role;

/**
 * 
 * @author 尹逊志
 * @date 2014-8-28下午6:28:11
 */
public interface AuthInfoService {
	/**
	 * 生成验证码
	 * 
	 * @param source
	 * @param target
	 * @param deadline
	 * @param authType
	 * @return
	 */
	public AuthInfo createAuthInfo(String mobileNumber, String type);

	/**
	 * 校验手机号和验证码是否匹配
	 * 
	 * @param mobileNumber
	 *            手机号
	 * @param authCode
	 *            验证码
	 * @param type
	 *            验证码类型
	 * @return
	 */
	public String verifyAuthInfo(String mobileNumber, String authCode,
			String type) throws NoMatchingObjectsException,
			AuthInfoOutOfDateException;

	/**
	 * HTTP请求校验
	 * 
	 * @param mobileNumber
	 * @param authCode
	 * @param type
	 * @return
	 */
	public Boolean verifyAuthCode(String mobileNumber, String authCode,
			String type);

	/**
	 * 发送手机验证码
	 * 
	 * @param mobileNumber
	 *            手机号
	 * @param type
	 *            验证码类型
	 * @return
	 */
	boolean sendAuthInfo(String mobileNumber, String type);
	/**
	 * 分页
	 * @param map
	 * @return
	 */
	 public PageInfo<AuthInfo> findPageInfo(int pageNo, int pageSize,
			 Map map);
	 public List<AuthInfo> getAuthNum(Map map);
	 public AuthInfo getAuthInfoById(String id);
	 public void updateauth(AuthInfo authInfo);


}
