package com.duanrong.dataAnalysis.business.cashData.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.dataAnalysis.business.cashData.dao.cashDataDao;
import com.duanrong.dataAnalysis.business.cashData.model.ResCashData;
import com.duanrong.dataAnalysis.business.cashData.model.cashData;
import com.duanrong.dataAnalysis.business.cashData.model.payAndCashData;
import com.duanrong.dataAnalysis.business.user.model.ResData;

@Repository
public class cashDataDaoImpl extends BaseDaoImpl<cashData> implements cashDataDao{

	public cashDataDaoImpl(){
		this.setMapperNameSpace("com.duanrong.dataAnalysis.business.cashData.mapper.cashDataMapper");
	}

	@Override
	public cashData getCashDataT() {
		return this.getSqlSession().selectOne("getCashDataT");
	}

	@Override
	public List<ResCashData> getPayData(String beginTime, String endTime) {
		// TODO Auto-generated method stub
		Map<String, String> map=new HashMap<String, String>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		List<ResCashData> list=this.getSqlSession().selectList("getPayData", map);
		return list;
	}
	
	@Override
	public List<ResCashData> getDrawData(String beginTime, String endTime) {
		// TODO Auto-generated method stub
		Map<String, String> map=new HashMap<String, String>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return this.getSqlSession().selectList("getDrawData", map);
	}

	@Override
	public payAndCashData getDataByMail() {
		
		return this.getSqlSession().selectOne("getDataByMail");
	}
	
	
	

	
	
	
}
