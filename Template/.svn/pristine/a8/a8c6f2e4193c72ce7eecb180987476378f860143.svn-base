package com.duanrong.dataAnalysis.business.CouponByPerson.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.dataAnalysis.business.CouponByPerson.dao.CouponByPersonDao;
import com.duanrong.dataAnalysis.business.CouponByPerson.model.CouponByPerson;
import com.duanrong.dataAnalysis.business.CouponByPerson.model.UserInfo;
@Repository
public class CouponByPersonDaoImpl extends BaseDaoImpl< CouponByPerson> implements CouponByPersonDao {

	public  CouponByPersonDaoImpl() {
		this.setMapperNameSpace("com.duanrong.dataAnalysis.business.CouponByPerson.mapper.CouponByPersonMapper"); // 设置命名空间
	}
	
	
	
	@Override
	public List<CouponByPerson> getRedPackageByUserId(String id, String type,
			String status, String activeId) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		params.put("type", type);
		params.put("status", status);
		params.put("activeId", activeId);
		return this.getSqlSession().selectList("getRedPackageByUserId", params);
	}



	@Override
	public UserInfo getRedPackageInfo(String id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return this.getSqlSession().selectOne("getRedPackageInfo",params);
	}



	@Override
	public double getUserMoney(String id) {
		Map<String, Object> params=new HashMap<>();
		params.put("id", id);
		return this.getSqlSession().selectOne("getUserMoney", params);
	}



	@Override
	public double getInvestMoney(String userId) {
		Map<String, Object> params=new HashMap<>();
		params.put("userId", userId);
		return this.getSqlSession().selectOne("getInvestMoney", params);
	}

}
