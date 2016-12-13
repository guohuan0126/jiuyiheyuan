package com.duanrong.dataAnalysis.business.user.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.duanrong.dataAnalysis.business.user.dao.UserDao;
import com.duanrong.dataAnalysis.business.user.model.TemplateUser;
import com.duanrong.dataAnalysis.business.user.model.User;

import base.dao.impl.BaseDaoImpl;
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
	private final String GETLOGIN="getlogin" ;
	
	 
	 public UserDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.dataAnalysis.user.mapper.UserMapper"); // 设置命名空间
	}
	@Override
	public User getUserByMobileNumber(String mobileNumber) {
		return this.getSqlSession().selectOne("getUserByMobileNumber", mobileNumber);
	}

	public User getLogin(String username, String password){	
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		return (User) this.getSqlSession().selectOne(GETLOGIN, user);
	}


	@Override
	public User getUserById(String mobileNumber){		
		return this.getSqlSession().selectOne("getUserById", mobileNumber);
	}

	@Override
	public User getUserByEmail(String email){
		return this.getSqlSession().selectOne("getUserByEmail", email);
	}
	//根据用户id 查询出角色id

	@Override
	public List<String> getRoleIdByUserId(String userId) {
		return this.getSqlSession().selectList("getRoleByUserId",userId);
	}

	@Override
	public int getCountUserAndRole(String userId, String roleId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("roleId", roleId);

		return this.getSqlSession().selectOne("getCountUserAndRole", params);
	}
	
	@Override
	public List<TemplateUser> getUserTemplate(String year, String month) {
		Map<String, Object> params = new HashMap<>();
		params.put("year", year);
		params.put("month", month);
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("getUserTemplate", params);
	}
	@Override
	public void insertUser(Map<String, Object> map) {
		
		this.getSqlSession().insert("insertUser", map);
	}
	@Override
	public int  getUserByIdCard(String idCard) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("getUserByIdCard", idCard);
	}
	@Override
	public int getUserCountByMobileNumber(String mobileNumber) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("getUserCountByMobileNumber", mobileNumber);
	}
	@Override
	public void insertUsers(List<User> users) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert("insertUsers", users);
	}
	
}
