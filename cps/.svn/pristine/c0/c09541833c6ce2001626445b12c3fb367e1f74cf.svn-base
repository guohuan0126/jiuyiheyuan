package com.duanrong.cps.business.user.service;
import javax.servlet.http.HttpServletResponse;

import com.duanrong.cps.business.user.model.User;

/**
 * 
 * @author  
 * @date 2014-8-29下午5:44:57
 */
public interface UserService {
		
	/**
	 * 根据手机号查询用户
	 * @param moileNUmber
	 * @return
	 */
	User getUser(String username,String password);
	User getUserByMobileNumber(String mobileNumber);
	//根据姓名密码判断登陆
	String login(String username ,String pass);
	
	User getUserByEmail(String email);
	
	User getUserById(String mobileNumber);
	
	int getCountUserAndRole(String userId,String roleId);
	
	/*List getRoleIdByUserId(String uId);*/
	
	public User getUserByMail(String email);
	
	/**
	 * 根据身份证查询用户
	 * @param idCard
	 * @return
	 */
	public User getUserByIdCard(String idCard);
	
}
