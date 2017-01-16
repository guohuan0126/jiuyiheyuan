package com.duanrong.drpay.business.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.drpay.business.user.PasswordEncode;
import com.duanrong.drpay.business.user.dao.UserDao;
import com.duanrong.drpay.business.user.model.AuthenticationResult;
import com.duanrong.drpay.business.user.model.TrusteeshipUserInfo;
import com.duanrong.drpay.business.user.model.User;

/**
 * 
 * @author 尹逊志
 * @date 2014-8-29下午3:16:28
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
	public UserDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.user.mapper.UserMapper");
	}

	@Override
	public User verifyLogin(User user) {
		user.setPassword(PasswordEncode.encode(user.getPassword()));
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".verifyLogin", user);
	}

	@Override
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
	public List<String> getRoles(String userId) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getRoles", userId);
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
	public void updateDetailMobileNumber(String oldNumber, String newNumber) {
		Map<String, Object> params = new HashMap<>();
		params.put("oldNumber", oldNumber);
		params.put("newNumber", newNumber);
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateDetailMobileNumber", params);
	}
	
	@Override
	public void updateShareMobileNumber(String oldNumber, String newNumber) {
		Map<String, Object> params = new HashMap<>();
		params.put("oldNumber", oldNumber);
		params.put("newNumber", newNumber);
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateShareMobileNumber", params);
	}

	@Override
	public boolean hasRoleByUserId(String userId, String roleId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("roleId", roleId);
		int num = this.getSqlSession().selectOne(this.getMapperNameSpace() + ".hasRoleByUserId", params);
		return num > 0 ? true : false;
	}

	@Override
	public void insertTrusteeshipUserInfo(String userId, String idCardNo,
			String bankCardNo, String requestNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("idCardNo", idCardNo);
		params.put("bankCardNo", bankCardNo);
		params.put("requestNo", requestNo);
		this.getSqlSession().insert(this.getMapperNameSpace() + ".insertTrusteeshipUserInfo", params);
	}

	@Override
	public TrusteeshipUserInfo getTrusteeshipUserInfo(
			String userId, String requestNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("requestNo", requestNo);
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getTrusteeshipUserInfo", params);
	}

	@Override
	public AuthenticationResult getAuthenticationResultByStatus(String userId,
			String result) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("result", result);
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getAuthenticationResultByStatus", params);
	}
	
}
