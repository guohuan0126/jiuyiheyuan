package com.duanrong.dataAnalysis.business.user.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.duanrong.dataAnalysis.business.user.model.TemplateUser;
import com.duanrong.dataAnalysis.business.user.model.User;

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
	List<TemplateUser> getUserTemplate(String year, String month);
	void insertUser(Map<String, Object> map);
	int getUserByIdCard(String idCard);
	int getUserCountByMobileNumber(String mobileNumber);
	void insertUsers(List<User> users);
}
