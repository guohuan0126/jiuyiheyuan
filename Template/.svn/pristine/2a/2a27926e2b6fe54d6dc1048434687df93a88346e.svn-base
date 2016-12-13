package com.duanrong.dataAnalysis.business.redPackage.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.dataAnalysis.business.redPackage.dao.RedPackageDao;
import com.duanrong.dataAnalysis.business.redPackage.modle.RedPackage;
@Repository
public class RedPackageDaoImpl extends BaseDaoImpl<RedPackage> implements RedPackageDao{


	public  RedPackageDaoImpl() {
		this.setMapperNameSpace("com.duanrong.dataAnalysis.business.redPackage.mapper.redPackageMapper"); // 设置命名空间
	}

	@Override
	public List<RedPackage> getRedPackageList() {
		return this.getSqlSession().selectList("getRedPackageList");
	}

	@Override
	public int getRedCount(String id) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		return this.getSqlSession().selectOne("getRedCount", params);
	}

	@Override
	public int getByUserCount(String id) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		return this.getSqlSession().selectOne("getByUserCount", params);
	}

	@Override
	public int getUsedUserCount(String id) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		return this.getSqlSession().selectOne("getUsedUserCount", params);
	}

	@Override
	public int getRedPackageSendedCount(String id) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		return this.getSqlSession().selectOne("getRedPackageSendedCount", params);
	}

	@Override
	public int getUnCount(String id) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		return this.getSqlSession().selectOne("getUnCount", params);
	}

	@Override
	public int getUnUsedCount(String id) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		return this.getSqlSession().selectOne("getUnUsedCount", params);
	}

	@Override
	public int getUsedCount(String id) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		return this.getSqlSession().selectOne("getUsedCount", params);
	}

	@Override
	public double getPayMoney(String id) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		return this.getSqlSession().selectOne("getPayMoney", params);
	}

	@Override
	public double getAllPayMoney(String id) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		return this.getSqlSession().selectOne("getAllPayMoney", params);
	}

	
	
	
	
	

}
