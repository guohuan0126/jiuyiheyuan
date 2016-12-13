package com.duanrong.dataAnalysis.business.ActiveData.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.dataAnalysis.business.ActiveData.dao.ActiveDataDao;
import com.duanrong.dataAnalysis.business.ActiveData.model.ActiveData;
import com.duanrong.dataAnalysis.business.ActiveData.model.ActiveR;
import com.duanrong.dataAnalysis.business.ActiveData.model.UserR;


@Repository
public class ActiveDataDaoImpl extends BaseDaoImpl<ActiveData> implements ActiveDataDao {

	public  ActiveDataDaoImpl() {
		this.setMapperNameSpace("com.duanrong.dataAnalysis.business.ActiveData.mapper.ActiveDataMapper"); // 设置命名空间
	}

	public List<ActiveData> getActiveDataById(String activeId, String userSource) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("activeId", activeId);
		params.put("userSource", userSource);
		return this.getSqlSession().selectList("getActiveDataById", params);
	}

	public ActiveR getData(String activeId) {
		Map<String , Object> params=new HashMap<>();
		params.put("activeId", activeId);
		return this.getSqlSession().selectOne("getData", params);
	}

	@Override
	public List<UserR> getUserR(String activeId,String userSource) {
		Map<String , Object> params=new HashMap<>();
		params.put("activeId", activeId);
		params.put("userSource", userSource);
		return this.getSqlSession().selectList("getUserR",params);
	}

	
	public double getActiveDataService(String userId) {
		Map<String , Object> params=new HashMap<>();
		params.put("userId", userId);
		return this.getSqlSession().selectOne("getActiveDataService",params);
	}

	@Override
	public int getSendCount(String activeId, double money) {
		Map<String , Object> params=new HashMap<>();
		params.put("activeId", activeId);
		params.put("money", money);
		return this.getSqlSession().selectOne("getSendCount",params);
	}

	@Override
	public int getUsedNum(String activeId, double money) {
		Map<String , Object> params=new HashMap<>();
		params.put("activeId", activeId);
		params.put("money", money);
		return this.getSqlSession().selectOne("getUsedNum",params);
	}

	@Override
	public int getUnusedCount(String activeId, double money) {
		Map<String , Object> params=new HashMap<>();
		params.put("activeId", activeId);
		params.put("money", money);
		return this.getSqlSession().selectOne("getUnusedCount",params);
	}

	@Override
	public int getExpiredCount(String activeId, double money) {
		Map<String , Object> params=new HashMap<>();
		params.put("activeId", activeId);
		params.put("money", money);
		return this.getSqlSession().selectOne("getExpiredCount",params);
	}

	@Override
	public double getUserInvestMoney(String activeId, double money) {
		Map<String , Object> params=new HashMap<>();
		params.put("activeId", activeId);
		params.put("money", money);
		return this.getSqlSession().selectOne("getUserInvestMoney",params);
	}
	
	
}
