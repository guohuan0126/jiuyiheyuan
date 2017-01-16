package com.duanrong.drpay.business.user.service;

import com.duanrong.drpay.business.user.model.AuthenticationResult;
import com.duanrong.drpay.business.user.model.TrusteeshipUserInfo;
import com.duanrong.drpay.business.user.model.User;

/**
 * 
 * @author 尹逊志
 * @date 2014-8-29下午5:44:57
 */
public interface UserService {


	/**
	 * 根据手机号查询用户
	 * 
	 * @param mobileNumber
	 *            手机号
	 * @return
	 */
	public User getUserByMobileNumber(String mobileNumber);
	
	/**
	 * 根据userId去鉴权结果表中查询鉴权结果为result的数据
	 * @param userId
	 * @param result
	 * @return
	 */
	public AuthenticationResult getAuthenticationResultByStatus(String userId, String result);
	
	/**
	 * 根据id查询用户
	 * 
	 * @param id
	 * @return
	 */
	public User get(String id);

	public void update(User user);
	/**
	 * 根据id删除某一用户
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 根据用户id判断用户的某些属性是否存在 如用户名，邮箱，手机号，身份证号等
	 * 
	 * @param fieldNmae
	 * @param id
	 * @return
	 */
	public boolean isAlreadExist(String fieldName, String value);

	/**
	 * 为用户添加角色
	 * 
	 * @param role
	 */
	public void addRole(String userId, String role);
	
	/**
	 * 校验身份证号是否存在
	 * 
	 * @param userId
	 *            用户ID
	 * @param idCard
	 *            身份证号
	 */
	public boolean getIdCard(String userId, String idCard);

	
	/**
	 * 修改手机号
	 * @param user
	 */
	public boolean updateUserMobileNumber(User user);

	/**
	 * 修改推荐人手机号
	 * @param user
	 */
	public void updateReferrerMobileNumber(String oldNumber, String newNumber);

	/**
	 * 修改红包手机号
	 * 
	 * @param oldNmber
	 * @param newNumber
	 */
	public void updateDetailMobileNumber(String oldNumber, String newNumber);

	/**
	 * 修改分享表中的手机号
	 */
	public void updateShareMobileNumber(String oldNumber, String newNumber);

	/**
	 * 查询用户是否有权限
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	boolean hasRoleByUserId(String userId, String roleId);
	
	void insertTrusteeshipUserInfo(String userId, String idCardNo, String bankCardNo, String requestNo);
	
	TrusteeshipUserInfo getTrusteeshipUserInfo(String userId, String requestNo);
}