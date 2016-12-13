
package com.duanrong.dataAnalysis.business.currnetData.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.dataAnalysis.business.cashData.model.ResCashData;
import com.duanrong.dataAnalysis.business.currnetData.dao.CurrnetDataDao;
import com.duanrong.dataAnalysis.business.currnetData.model.CurrnetData;
import com.duanrong.dataAnalysis.business.currnetData.model.ResCurrnetData;
@Repository
public class CurrnetDataDaoImpl extends BaseDaoImpl<CurrnetData> implements CurrnetDataDao {

	public CurrnetDataDaoImpl(){
		this.setMapperNameSpace("com.duanrong.dataAnalysis.business.currnetData.mapper.currnetDataMapper");
	}
	
	
	@Override
	public CurrnetData getCurrnetDataT() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("getCurrnetDataT");
	}


	@Override
	public List<ResCurrnetData> getInData(String beginTime, String endTime) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		List<ResCurrnetData> list=this.getSqlSession().selectList("getInData", map);
		return list;
	}


	@Override
	public List<ResCurrnetData> getOutData(String beginTime, String endTime) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		List<ResCurrnetData> list=this.getSqlSession().selectList("getOutData", map);
		return list;
	}

}
