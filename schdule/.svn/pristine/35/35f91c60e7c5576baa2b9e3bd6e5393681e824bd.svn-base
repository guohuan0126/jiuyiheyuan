package com.duanrong.business.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.sms.model.Sms;
import com.duanrong.business.user.dao.AuthInfoDao;
import com.duanrong.business.user.model.AuthInfo;

/**
 * 
 * @author 尹逊志
 * @date 2014-8-29下午12:18:41
 */
@Repository
public class AuthInfoDaoImpl extends BaseDaoImpl<AuthInfo> implements
		AuthInfoDao {
	public AuthInfoDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.user.mapper.AuthInfoMapper"); // 设置命名空间
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
	public PageInfo<AuthInfo> pageInfo(int pageNo, int pageSize, Map map) {
		PageHelper.startPage(pageNo, pageSize);
		List<AuthInfo> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo", map);
		PageInfo<AuthInfo> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public List<AuthInfo> getAuthNum(Map map) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getAuthNum", map);
	}
	public void updateauth(AuthInfo authInfo) {
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateauth", authInfo);
	}
}
