package com.duanrong.drpay.business.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.drpay.business.user.dao.AuthInfoDao;
import com.duanrong.drpay.business.user.model.AuthInfo;

/**
 * 
 * @author 尹逊志
 * @date 2014-8-29下午12:18:41
 */
@Repository
public class AuthInfoDaoImpl extends BaseDaoImpl<AuthInfo> implements
		AuthInfoDao {
	public AuthInfoDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.user.mapper.AuthInfoMapper"); // 设置命名空间
	}

	public List<AuthInfo> getAuthInfo(String mobileNumber, String authCode,
			String type) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobileNumber", mobileNumber);
		params.put("authCode", authCode);
		params.put("type", type);
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getAuthInfo", params);
	}

	@Override
	public Long getAuthInfoDateScope(String mobileNumber, String scope) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobileNumber", mobileNumber);
		params.put("scope", scope);
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getAuthInfoDateScope", params);
	}

}
