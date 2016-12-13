package com.duanrong.cps.business.user.service.impl;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.duanrong.cps.business.user.dao.UserDao;
import com.duanrong.cps.business.user.model.User;
import com.duanrong.cps.business.user.service.UserService;
import com.duanrong.cps.util.HashCrypt;
import com.duanrong.cps.util.MyStringUtils;

/**
 * 
 * @author 
 * @date 2014-8-29下午5:45:05
 */
@Service
public class UserServiceImpl implements UserService {

	@Resource
	 private UserDao userDao;
	private HttpServletResponse response;
	
	@Override
	public User getUserByMobileNumber(String mobileNumber) {
		// TODO Auto-generated method stub
		return userDao.getUserByMobileNumber(mobileNumber);
	}
	
	@Override
	public User getUserById(String id) {
		
		return userDao.getUserById(id);
	}
	
	@Override
	public User getUserByEmail(String email){	
		
		return userDao.getUserByEmail(email);
	}
	
	@Override
	public String login(String username, String password) {
		User u = null;
		if(username.contains("@")){
			u= getUserByEmail(username);
		}else if(username.length() == 11 && MyStringUtils.isNumeric(username)){
			u = getUserByMobileNumber(username);
		}else{
			u = getUserById(username);
		}
		if(u == null){
			//user is not exits
			return "-1";
		}else{
			//根据用户状态判断用户的状态是否为禁用（0）或不禁用（1）
			if(u.getStatus().equals(0)){
				return "-2";// user is fribdenn
			}
			String pass = u.getPassword();
			if(!pass.equals(HashCrypt.getDigestHash(password))){
				//从user类中查找userid的值
				return "-3";//password is error
			}
			else
			{	
				int count = userDao.getCountUserAndRole(u.getUserId(), "data_analysis");
				if(count >0)
				{
					return "1";//success
				}
				else
				{
					return "-4";//user has not permission;
				}
			}
	 
		}
	
	}

	@Override
	public int getCountUserAndRole(String userId, String roleId) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//根据用户名密码查到user对象
	public User getUser(String username, String password) {
		User user = null;
		if(username.contains("@")){
			user= getUserByEmail(username);
		}else if(username.length() == 11 && MyStringUtils.isNumeric(username)){
			user = getUserByMobileNumber(username);
		}else{
			user = getUserById(username);
		}
			
		return user;
	}

	@Override
	public User getUserByMail(String email) {
		return userDao.getUserByMail(email);
	}

	@Override
	public User getUserByIdCard(String idCard) {
		return userDao.getUserByCard(idCard);
	}
	
}

	

