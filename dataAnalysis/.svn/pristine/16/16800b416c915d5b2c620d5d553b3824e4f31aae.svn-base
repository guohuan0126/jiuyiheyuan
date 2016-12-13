package com.duanrong.dataAnalysis.business.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.dataAnalysis.business.user.dao.UserDataDao;
import com.duanrong.dataAnalysis.business.user.model.ResData;
import com.duanrong.dataAnalysis.business.user.model.UserData;


@Repository
public class UserDataDaoImpl extends BaseDaoImpl<UserData> implements UserDataDao{
		
	public UserDataDaoImpl(){
		this.setMapperNameSpace("com.duanrong.dataAnalysis.business.user.mapper.UserDataMapper");
	}
	@Override
	public UserData getUserData() {
		
		return this.getSqlSession().selectOne("getUserData");
	}
	@Override
	public UserData getUserDataByT() {
		return this.getSqlSession().selectOne("getUserDataByT");
	}
	/*public UserData getUserDataByS(String sTime, String eTime) {
		Map<String, Object> params = new HashMap<>();
		params.put("sTime", sTime);
		params.put("eTime", eTime);
		return this.getSqlSession().selectOne("getUserDataByS", params);
	}*/
	@Override
	public List<ResData> getUserCountByW() {
		return this.getSqlSession().selectList("getUserCountByW");
	}
	@Override
	public List<ResData> getUserRByW() {
		return this.getSqlSession().selectList("getUserRByW");
	}
	@Override
	public List<ResData> getUserIByW() {
		return this.getSqlSession().selectList("getUserIByW");
	}
	//注册
	@Override
	public List<ResData> getUserCountByM() {
		
		
		return this.getSqlSession().selectList("getUserCountByM");
	}
	//开户
	@Override
	public List<ResData> getUserRByM() {
		return this.getSqlSession().selectList("getUserRByM");
	}
	@Override
	//投资
	public List<ResData> getUserIByM() {
		return this.getSqlSession().selectList("getUserIByM");
	}
	@Override
	public List<ResData> getUserCountByD(String sTime, String eTime) {
		Map<String, Object> params = new HashMap<>();
		params.put("sTime", sTime);
		params.put("eTime", eTime);
		return this.getSqlSession().selectList("getUserCountByD", params);
	}
	@Override
	public List<ResData> getUserIByD(String sTime, String eTime) {
		Map<String, Object> params = new HashMap<>();
		params.put("sTime", sTime);
		params.put("eTime", eTime);
		return this.getSqlSession().selectList("getUserIByD", params);
	}
	@Override
	public List<ResData> getUserRByD(String sTime, String eTime) {
		Map<String, Object> params = new HashMap<>();
		params.put("sTime", sTime);
		params.put("eTime", eTime);
		return this.getSqlSession().selectList("getUserRByD", params);
	}
	@Override
	public double getRegisterUserCount(String start, String end,
			String userSource) {
		Map<String, Object> params=new HashMap<>();
		params.put("start", start);
		if (end=="") {
			params.put("end", end);
		} else {
			params.put("end", end+" 23:59:59");
		}
		params.put("userSource", userSource);
		
		return this.getSqlSession().selectOne("getRegisterCount",params);
	}
	@Override
	public double getRealNameUserCount(String start, String end,
			String userSource) {
		Map<String, Object> params=new HashMap<>();
		params.put("start", start);
		if (end=="") {
			params.put("end", end);
		} else {
			params.put("end", end+" 23:59:59");
		}
		
		params.put("userSource", userSource);
		return this.getSqlSession().selectOne("getRealNameUserCount",params);
	}
	@Override
	public double getFristInvestCount(String start, String end,
			String userSource) {
		Map<String, Object> params=new HashMap<>();
		params.put("start", start);
		if (end=="") {
			params.put("end", end);
		} else {
			params.put("end", end+" 23:59:59");
		}
		
		params.put("userSource", userSource);
		return this.getSqlSession().selectOne("getFristInvestCount",params);
	}
	@Override
	public double getFristInvestMoney(String start, String end,
			String userSource) {
		Map<String, Object> params=new HashMap<>();
		params.put("start", start);
		if (end=="") {
			params.put("end", end);
		} else {
			params.put("end", end+" 23:59:59");
		}
		
		params.put("userSource", userSource);
		return this.getSqlSession().selectOne("getFristInvestMoney",params);
	}
	@Override
	public double getInvestMoneyAgain(String start, String end,
			String userSource) {
		Map<String, Object> params=new HashMap<>();
		params.put("start", start);
		if (end=="") {
			params.put("end", end);
		} else {
			params.put("end", end+" 23:59:59");
		}
		params.put("userSource", userSource);
		return this.getSqlSession().selectOne("getInvestMoneyAgain",params);
	}
	@Override
	public double getInvestUserMoneyAgain(String start, String end,
			String userSource) {
		Map<String, Object> params=new HashMap<>();
		params.put("start", start);
		if (end=="") {
			params.put("end", end);
		} else {
			params.put("end", end+" 23:59:59");
		}
		params.put("userSource", userSource);
		return this.getSqlSession().selectOne("getInvestUserMoneyAgain",params);
	}
	
	
	
	

	
}
