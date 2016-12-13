package com.duanrong.business.demand.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.demand.dao.DemandtreasureDao;
import com.duanrong.business.demand.model.Demandtreasure;

@Repository
public class DemandtreasureDaoImpl extends BaseDaoImpl<Demandtreasure> implements DemandtreasureDao {

	public DemandtreasureDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.demand.mapper.DemandtreasureMapper");
	}

	@Override
	public double readDemandInterest(String start, String end) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("start", start);
		params.put("end", end);
		return this.getSqlSession().selectOne("getDemandInterest", params);
	}
}
