package com.duanrong.dataAnalysis.business.CapitalData.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.dataAnalysis.business.CapitalData.dao.CapitalDataDao;
import com.duanrong.dataAnalysis.business.CapitalData.model.CapitalData;
import com.duanrong.dataAnalysis.business.CapitalData.model.CapitalDataVO;
@Repository
public class CapitalDataDaoImpl extends BaseDaoImpl<CapitalData> implements CapitalDataDao {

	public CapitalDataDaoImpl(){
		this.setMapperNameSpace("com.duanrong.dataAnalysis.business.CapitalData.mapper.CapitalDataMapper");
	}
	@Override
	public CapitalData getCapitalData() {
		//查询资金数据
		return this.getSqlSession().selectOne("getCapitalData");
	}
	@Override
	public List<CapitalDataVO> getCount(Map<String, String> map,int argType) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("getCount");
	}
	
	
	@Override
	public List<CapitalDataVO> getCountByWeek(Map<String, String> map,
			int argType) {
		return this.getSqlSession().selectList("getCountByWeek");
	}
	@Override
	public List<CapitalDataVO> getCountByMonth(Map<String, String> map,
			int argType) {
		return this.getSqlSession().selectList("getCountByMonth");
	}
	@Override
	public List<CapitalDataVO> getCountByPhase(Map<String, String> map,
			int argType) {
		return this.getSqlSession().selectList("getCountByPhase",map);
	}

}
