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

import com.duanrong.business.invest.model.PassThrough;
import com.duanrong.business.maxinvestrecord.model.MaxInvestRecord;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.Config;
import com.duanrong.business.user.model.Template;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.model.UserOtherInfo;
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
	public PageInfo<User> getUsersPhone(int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<User> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getNoPhoneAttrUser");
		PageInfo<User> pageInfo = new PageInfo<>(list);

		return pageInfo;
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
	public void updateMobileNumber(User user) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", user.getUserId());
		params.put("mobileNumber", user.getMobileNumber());
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateUserMobileNumber", params);
	}
	
	
	@Override
	public void updateReferrerMobileNumber(String oldNumber, String newNumber) {
		Map<String, Object> params = new HashMap<>();
		params.put("oldNumber", oldNumber);
		params.put("newNumber", newNumber);
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateReferrerMobileNumber", params);
	}

	@Override
	public List<User> getBigClientBirthday() {
		return getSqlSession().selectList(getMapperNameSpace() + ".getBigClientBirthday");
	}

	@Override
	public List<String> readUserMobileNumber(int day) {
		return getSqlSession().selectList(getMapperNameSpace() + ".readUserMobileNumber",day);
		
	}

	@Override
	public List<String> readUserMobukeNumbers() {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(getMapperNameSpace()+".readUserMobukeNumbers");
	}

	@Override
	public int readRedpacketByMob(String mobileNumber) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".readRedpacketByMob",mobileNumber);
	}
	@Override
	public List<User> getExpireRedpacket() {
		return getSqlSession().selectList(getMapperNameSpace() + ".getExpireRedpacket");
	}

	@Override
	public MaxInvestRecord getUserInfo(String uId) {
		return getSqlSession().selectOne(getMapperNameSpace() + ".getUserInfo",uId);
	}

	@Override
	public PassThrough getDoubleElevenPrize(String userId) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getDoubleElevenPrize",userId);
	}

	@Override
	public PassThrough getPassThroughByUserId(Map<String,Object>param){
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getPassThroughByUserId",param);
	}
	
	@Override
	public void insert4DoubleEleven(PassThrough passThrough) {
		this.getSqlSession().insert(
				this.getMapperNameSpace() + ".insert4DoubleEleven", passThrough);
	}

	@Override
	public List<PassThrough> getRecommendedPrize(String userId) {
		return this.getSqlSession().selectList(this.getMapperNameSpace() + ".getRecommendedPrize",userId);
	}

	@Override
	public void update4DoubleElevenReward(PassThrough passThrough) {
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".update4DoubleElevenReward", passThrough);
	}
	
	@Override
	public void update4DoubleEleven(PassThrough passThrough) {
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".update4DoubleEleven", passThrough);
	}

	@Override
	public List<String> readUserMobileNumberziran(int day) {
		return getSqlSession().selectList(this.getMapperNameSpace() + ".readUserMobileNumberziran",day);
	}

	@Override
	public List<String> readUserMobileNumbertuiguang(int day) {
		return getSqlSession().selectList(this.getMapperNameSpace() + ".readUserMobileNumbertuiguang",day);
	}
	
	@Override
	public void updatePassThrough(PassThrough passThrough){
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".updatePassThrough", passThrough);
	}

	@Override
	public UserOtherInfo getUserOtherInfoById(Map<String, Object> param) {
		
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".getUserOtherInfoById",param);
	}

	
}