package com.duanrong.drpay.business.user.dao;

import java.util.List;

import base.dao.BaseDao;

import com.duanrong.drpay.business.user.model.AuthenticationResult;
import com.duanrong.drpay.business.user.model.TrusteeshipUserInfo;
import com.duanrong.drpay.business.user.model.User;

/**
 * 
 * @author 尹逊志
 * @date 2014-8-29下午3:14:43
 */
public interface UserDao extends BaseDao<User> {	

	/**
	 * 根据手机号查询用户
	 * 
	 * @param mobileNumber
	 *            手机号
	 * @return
	 */
	public User getUserByMobileNumber(String mobileNumber);

	/**
	 * 用户登录验证
	 * 
	 * @param user
	 * @return
	 */
	public User verifyLogin(User user);

	/**
	 * 为用户添加角色
	 * 
	 * @param role
	 */
	public void addRole(String userId, String roleId);

	/**
	 * 获得用户角色
	 * 
	 * @param role
	 */
	public List<String> getRoles(String userId);


	/**
	 * 校验身份证号是否存在
	 * 
	 * @param userId
	 *            用户ID
	 * @param idCard
	 *            身份证号
	 */
	public boolean verifyIdCard(String userId, String idCard);
	

	/**
	 * 修改注册手机号
	 * @param user
	 */
	public void updateMobileNumber(User user);
	
	/**
	 * 修改邀请人手机号
	 * @param oldNumber
	 * @param newNumber
	 */
	public void updateReferrerMobileNumber(String oldNumber, String newNumber);

	/**
	 * 修改红包手机号
	 * @param oldNumber
	 * @param newNumber
	 */
	public void updateDetailMobileNumber(String oldNumber, String newNumber);

	/**
	 * 修改分享手机号
	 * @param oldNumber
	 * @param newNumber
	 */
	public void updateShareMobileNumber(String oldNumber, String newNumber);

	/**
	 * 判断用户是否拥有指定角色
	 * @param userId
	 * @param roleId
	 * @return
	 */
	boolean hasRoleByUserId(String userId, String roleId);

	void insertTrusteeshipUserInfo(String userId, String idCardNo,
			String bankCardNo, String requestNo);

	TrusteeshipUserInfo getTrusteeshipUserInfo(String userId, String requestNo);

	/**
	 * 根据userId去鉴权结果表中查询鉴权结果为result的数据
	 * @param userId
	 * @param result
	 * @return
	 */
	public AuthenticationResult getAuthenticationResultByStatus(String userId,
			String result);
}
