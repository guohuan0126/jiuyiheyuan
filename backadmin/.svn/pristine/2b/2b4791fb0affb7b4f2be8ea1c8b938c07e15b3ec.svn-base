package com.duanrong.business.user.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.Config;
import com.duanrong.business.user.model.Template;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.model.UserByFuRen;
import com.duanrong.business.user.model.UserInfoConfirmation;
import com.duanrong.business.user.model.UserInvestCheck;
import com.duanrong.business.user.model.UserRemarkInfo;
import com.duanrong.business.user.model.newInvestUser;
import com.duanrong.util.HashCrypt;

/**
 * 
 * @author 尹逊志
 * @date 2014-8-29下午3:16:28
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
	public UserDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.user.mapper.UserMapper"); // 设置命名空间
	}

	public User verifyLogin(User user) {
		user.setPassword(HashCrypt.getDigestHash(user.getPassword()));
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".verifyLogin", user);
	}

	public void addRole(String userId, String roleId) {
		List<String> roles = getRoles(userId);
		if (roles != null) {
			for (String role : roles) {
				if (StringUtils.equals(roleId, role)) {
					return;
				}
			}
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("roleId", roleId);
		this.getSqlSession().insert(this.getMapperNameSpace() + ".addRole",
				param);
	}
	@Override
	public User getUserByMobileNumber(String mobileNumber) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getUserByMobileNumber",
				mobileNumber);
	}
	@Override
	public User getUserByMail(String email) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getUserByMail",
				email);
	}

	@Override
	public List<String> getRoles(String userId) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getRoles", userId);
	}

	@Override
	public Long getPerson() {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getPerson");
	}

	@Override
	public String getPerson2() {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getPerson2");
	}

	@Override
	public boolean verifyIdCard(String userId, String idCard) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("idCard", idCard);
		Long cardCount = this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".verifyIdCard", params);
		if (cardCount > 0) {
			return true;
		}

		return false;
	}

	@Override
	public void updatePersonCount4Config() {
		try {
			this.getSqlSession().update(
					this.getMapperNameSpace() + ".updatePersonCount4Config");
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	@Override
	public List<User> getUserByRealname(String realname) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getUserByRealname", realname);
	}

	@Override
	public Long getTheNumberOfRegisteredEveryDay(Map<String, Object> params) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace()+ ".getTheNumberOfRegisteredEveryDay", params);
	}

	@Override
	public PageInfo<User> pageInfo(int pageNo, int pageSize, Map map) {
		PageHelper.startPage(pageNo, pageSize);
		List<User> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo", map);
		PageInfo<User> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	@Override
	public List<User> getUsersPhone(Map map) {
		List<User> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo", map);
		return list;
	}
	
	@Override
	public int verifyUser(User user) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".verifyUser", user);
	}

	@Override
	public List<User> getUsers(User user) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".verifyLogin", user);
	}

	@Override
	public Template getTemplateById(String id) {		
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getTemplateById", id);
	}

	@Override
	public PageInfo<Config> getConfigForPageLite(int pageNo, int pageSize, Config config) {
		PageHelper.startPage(pageNo, pageSize);
		List<Config> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getConfigForPageLite", config);
		PageInfo<Config> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public Config getConfigById(String id) {		
		return getSqlSession().selectOne(
				getMapperNameSpace() + ".getConfigById", id);
	}

	@Override
	public int updateConfig(Config config) {
		
		return getSqlSession().update(getMapperNameSpace() + ".updateConfig", config);
	}

	@Override
	public int insertConfig(Config config) {
		
		return getSqlSession().insert(getMapperNameSpace() + ".insertConfig", config);
	}

	@Override
	public List<User> getUserNum(Map map) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getUserNum", map);
	}
	/**
	 * add by wangjing
	 * @param map
	 * @return
	 */
	@Override
	public List<User> findUsers(Map<String, Object> map) {
		return getSqlSession().selectList(
				this.getMapperNameSpace() + ".pageInfo", map);
	}

	@Override
	public List<User> getUserTotalMoney(Map map) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getUserTotalMoney", map);
	}
	@Override
	public Long getTheNumberOfAccountEveryDay(Map<String, Object> params) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace()+ ".getTheNumberOfAccountEveryDay", params);
	}

	@Override
	public PageInfo<User> findAdminUsers(int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<User> list = getSqlSession().selectList(
				getMapperNameSpace() + ".findAdminUsers");
		PageInfo<User> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public void updateUserPhoneArea(User user) {
		try {
			this.getSqlSession().update(
					this.getMapperNameSpace() + ".updateUserPhoneArea",user);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	@Override
	public List<User> exportList(Map map) {
		List<User> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo", map);
		return list;
	}
	
	
	@Override
	public User getUserByInviteCode(String inviteCode) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getUserByInviteCode", inviteCode);		
	}

	@Override
	public int getAvlidUserByMobileNumber(String mobileNumber) {
		Integer count = this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getAvlidUserByMobileNumber",
				mobileNumber);
		return count == null ? 0 : count;
	}

	@Override
	public int getCountByReffer(String referrer, Date firstInvestTime) {
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<>();
		param.put("referrer", referrer);
		param.put("firstInvestTime", firstInvestTime);
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getCountByReffer", param);
	}

	@Override
	public User getUserByCard(String idCard) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getUserByCard",
				idCard);
	}

	@Override
	public PageInfo<newInvestUser> getInvestUserInfoPageLite(int pageNo,
			int pageSize, Map<String, Object> params) {
			
		PageHelper.startPage(pageNo, pageSize);
		List<newInvestUser> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getInvestUserInfo1PageLite", params);
		PageInfo<newInvestUser> pageInfo = new PageInfo<>(list);
		return pageInfo;
		
		
	}

	@Override
	public PageInfo<UserInvestCheck> getUserInvestCheck(int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<UserInvestCheck> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getUserInvestCheckinPageLite");
		PageInfo<UserInvestCheck> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public int insertUserInfoCheck(Map<String, Object> params) {
		
		return this.getSqlSession().insert("insertUserInfoCheck", params);
	}

	@Override
	public int delUserinfo(String id) {
		
		return this.getSqlSession().update("delUserinfo", id);
	}

	@Override
	public List<UserInfoConfirmation> getUserInfoConfirmation(String userId,
			String start, String end) {
		Map<String, Object> params=new HashMap<>();
		params.put("userId", userId);
		params.put("start", start);
		params.put("end", end+" 23:59:59");
		return this.getSqlSession().selectList("getUserInfoConfirmation", params);
	}


	@Override
	public String getTemplate(String string) {
		 Map<String, Object> params=new HashMap<>();
		 params.put("id", string);
		return this.getSqlSession().selectOne("getTemplate", params);
	}

	@Override
	public int updateExportTime(String userId,String exportTime) {
		 Map<String, Object> params=new HashMap<>();
		 params.put("userId", userId);
		 params.put("exportTime", exportTime);
		return this.getSqlSession().update("updateExportTime", params);
	}

	@Override
	public List<String> getUserIds() {
		
		return this.getSqlSession().selectList("getUserIds");
	}

	@Override
	public PageInfo<UserInvestCheck> getUserInvestCheckByTime(int pageNo,
			int pageSize, String start, String end) {
		PageHelper.startPage(pageNo, pageSize);
		Map<String, Object>params=new HashMap<>();
		params.put("start", start);
		params.put("end", end+" 23:59:59");
		List<UserInvestCheck> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getUserInvestCheckinByTimePageLite",params);
		PageInfo<UserInvestCheck> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<newInvestUser> getInvestUserInfoRemarkPageLite(int pageNo,
			int pageSize, Map<String, Object> params) {
		
		PageHelper.startPage(pageNo, pageSize);
		List<newInvestUser> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getInvestUserInfo1RemarkPageLite", params);
		PageInfo<newInvestUser> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	
	@Override
	public PageInfo<newInvestUser> getInvestUserInfoNoRemarkPageLite(int pageNo,
			int pageSize, Map<String, Object> params) {
		
		PageHelper.startPage(pageNo, pageSize);
		List<newInvestUser> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getInvestUserInfo1NoRemarkPageLite", params);
		PageInfo<newInvestUser> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public int getPeopleCount(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("getPeopleCount", params);
	}

	@Override
	public List<UserRemarkInfo> getRemarkInfo(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("getRemarkInfo", params);
	}

	@Override
	public int getPeopleCountRemark(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("getPeopleCountRemark", params);
	}

	@Override
	public int getPeopleCountNoRemark(Map<String, Object> params) {
		return this.getSqlSession().selectOne("getPeopleCountNoRemark", params);
	}

	@Override
	public PageInfo<UserByFuRen> readInvestInfoByFuRen(int pageNo,
			int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<UserByFuRen> list = getSqlSession().selectList(
				getMapperNameSpace() + ".readInvestInfoByFuRen", params);
		PageInfo<UserByFuRen> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public List<UserByFuRen> readExportInvestByFuRen() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".readExportInvestByFuRen");
	}

	@Override
	public List<User> queryAdmin(User user) {
		System.out.println(user.getUserId());
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".queryAdmin", user);
	}

	@Override
	public List<newInvestUser> getInvestUserInfo(Map<String, Object> params) {
		List<newInvestUser> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getInvestUserInfo1PageLite", params);		
		return list;
	}

	@Override
	public List<newInvestUser> getInvestUserInfoRemarkPageLite(
			Map<String, Object> params) {		
		List<newInvestUser> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getInvestUserInfo1RemarkPageLite", params);		
		return list;
	}

	@Override
	public List<newInvestUser> getInvestUserInfoNoRemarkPageLite(
			Map<String, Object> params) {
		
		List<newInvestUser> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getInvestUserInfo1NoRemarkPageLite", params);		
		return list;
	}

	@Override
	public String getEncodeUserId(Map<String, Object> map1) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("getEncodeUserId", map1);
	}

	/** 注销用户
	 * 
	 */
	@Override
	public void cancelUser(User user) {
		
		this.getSqlSession().update("cancelUser", user);
		
	}
	
	
}
