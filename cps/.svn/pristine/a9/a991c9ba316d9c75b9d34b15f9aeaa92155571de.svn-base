package com.duanrong.cps.business.user.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.duanrong.cps.business.user.dao.UserDao;
import com.duanrong.cps.business.user.model.User;

import base.dao.impl.BaseDaoImpl;
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
	private final String GETLOGIN="getlogin" ;
	
	 
	 public UserDaoImpl() {
		this.setMapperNameSpace("com.duanrong.cps.business.user.mapper.UserMapper"); // 设置命名空间
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
	public User getUserByMail(String email) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getUserByMail",
				email);
	}


	@Override
	public User getUserByCard(String idCard) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getUserByCard",
				idCard);
	}
	
	@Override
	public List<User>getTouzhijiaUser(){
		return this.getSqlSession().selectList("getTouzhijiaUser");
	}
	@Override
	public List<User> getUserInfo(String startdate, String enddate, int start,
			String pagesize) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("start", start);
		param.put("pagesize", Integer.valueOf(pagesize));
		param.put("startdate", startdate);
		param.put("enddate", enddate);
		return this.getSqlSession().selectList("getUserInfo",param);
	}
	@Override
	public int getTotalUser(String startdate, String enddate, int start,
			String pagesize) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("startdate", startdate);
		param.put("enddate", enddate);
		return this.getSqlSession().selectOne("getTotalUser",param);
	}
	
	@Override
	public String getUserSource(String userId) {
		return this.getSqlSession().selectOne("getUserSource",userId);
	}
	
}

