package com.duanrong.dataAnalysis.business.UserInfo.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.dataAnalysis.business.UserInfo.dao.UserInfoDao;
import com.duanrong.dataAnalysis.business.UserInfo.model.ComeBack;
import com.duanrong.dataAnalysis.business.UserInfo.model.UserInfo;
import com.duanrong.dataAnalysis.business.UserInfo.model.UserSourceData;
@Repository
public class UserInfoDaoImpl extends BaseDaoImpl<UserInfo> implements UserInfoDao  {
	
	 
	public  UserInfoDaoImpl() {
		this.setMapperNameSpace("com.duanrong.dataAnalysis.business.UserInfo.mapper.UserInfoMapper"); // 设置命名空间
	}
	//活期宝
	@Override
	public double getLiveMoney(String id) {
		Map<String, Object> params=new HashMap<>();
		params.put("id", id);
		return this.getSqlSession().selectOne("getLiveMoney", params);
	}
	//在投金额
	@Override
	public double getInvestIngMoney(String id) {
		Map<String, Object> params=new HashMap<>();
		params.put("id", id);
		return this.getSqlSession().selectOne("getInvestIngMoney", params);
	}
	//冻结金额
	@Override
	public double getNotUsedMoeny(String id) {
		Map<String, Object> params=new HashMap<>();
		params.put("id", id);
		return this.getSqlSession().selectOne("getNotUsedMoeny", params);
	}
	@Override
	public List<ComeBack> getComeBack(String id) {
		// TODO Auto-generated method stub
		Map<String, Object> params=new HashMap<>();
		params.put("id", id);
		return this.getSqlSession().selectList("getComeBack", params);
	}
	@Override
	public String getInvestTime(String id) {
		Map<String, Object> params=new HashMap<>();
		params.put("id", id);
		return this.getSqlSession().selectOne("getInvestTime", params);
	}
	@Override
	public UserSourceData getUserSourceData(String userSource,String registerTimeBegin,String registerTimeEnd) {
		Map<String, Object> params=new HashMap<>();
		params.put("userSource", userSource);
		params.put("registerTimeBegin", registerTimeBegin);
		params.put("registerTimeEnd", registerTimeEnd);
		return this.getSqlSession().selectOne("getUserSourceData", params);
	}
	@Override
	public double getUserAllMoney(String userSource,String registerTimeBegin,String registerTimeEnd) {
		Map<String, Object> params=new HashMap<>();
		params.put("userSource", userSource);
		params.put("registerTimeBegin", registerTimeBegin);
		params.put("registerTimeEnd", registerTimeEnd);
		return this.getSqlSession().selectOne("getUserAllMoney", params);
	}
	@Override
	public double getUserAllMoneyById(String id) {
		Map<String, Object> params=new HashMap<>();
		params.put("id", id);
		return this.getSqlSession().selectOne("getUserAllMoneyById", params);
	}
	@Override
	public double getUserUsedMoney(String userSource,String registerTimeBegin,String registerTimeEnd) {
		Map<String, Object> params=new HashMap<>();
		params.put("userSource", userSource);
		params.put("registerTimeBegin", registerTimeBegin);
		params.put("registerTimeEnd", registerTimeEnd);
		return this.getSqlSession().selectOne("getUserUsedMoney", params);
	}
	
	
	
}
