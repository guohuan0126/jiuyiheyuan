package com.duanrong.cps.business.user.dao;

import java.util.List;

import com.duanrong.cps.business.user.model.User;

import base.dao.BaseDao;

/**
 * 
 * @author xiao
 *
 */
public interface UserDao extends BaseDao<User> {

	/**
	 * 根据手机号查询用户
	 * @param mobileNumber
	 * @return
	 */
	User getUserByMobileNumber(String mobileNumber);
	
	/**
	 * 根据手机号查询用户
	 * @param mobileNumber
	 * @return
	 */
	User getUserById(String mobileNumber);
	
	/**
	 * 根据电子邮件查询用户
	 * @param mobileNumber
	 * @return
	 */
	User getUserByEmail(String email);
	
	User getLogin(String username, String password);
	List<String> getRoleIdByUserId(String userId);

	int getCountUserAndRole(String userId,String roleId);
	
	
	/**
	 * 根据电邮查询用户
	 * 
	 * @param email
	 *            
	 * @return
	 */
	public User getUserByMail(String email);
	
	/**
	 * 根据身份证查询用户
	 * 
	 * @param email
	 *            
	 * @return
	 */
	public User getUserByCard(String idCard);
	
	/**
	 * 查询和投之家相联的老用户没有实名信息的用户
	 * @return
	 */
	public List<User>getTouzhijiaUser();

	public List<User> getUserInfo(String startdate, String enddate, int start, String pagesize);

	public int getTotalUser(String startdate, String enddate, int start,
			String pagesize);

	public String getUserSource(String userId);

}
