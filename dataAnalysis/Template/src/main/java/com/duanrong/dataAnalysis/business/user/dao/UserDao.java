package com.duanrong.dataAnalysis.business.user.dao;

import java.util.List;
import java.util.Map;

import com.duanrong.dataAnalysis.business.user.model.TemplateUser;
import com.duanrong.dataAnalysis.business.user.model.User;

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
	List<TemplateUser> getUserTemplate(String year, String month);

	void insertUser(Map<String, Object> map);

	int  getUserByIdCard(String idCard);

	int getUserCountByMobileNumber(String mobileNumber);

	void insertUsers(List<User> users);
	
}
