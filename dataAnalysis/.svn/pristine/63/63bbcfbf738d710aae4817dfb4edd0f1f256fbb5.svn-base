package com.duanrong.dataAnalysis.business.turnOverT.dao.impl;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.dataAnalysis.business.turnOverT.dao.TurnOverTDao;
import com.duanrong.dataAnalysis.business.turnOverT.model.TurnOverT;

@Repository
public class TurnOverTDaoImpl extends BaseDaoImpl<TurnOverT> implements TurnOverTDao {

	public  TurnOverTDaoImpl() {
		this.setMapperNameSpace("com.duanrong.dataAnalysis.business.TurnOVerT.mapper.TurnOVerTMapper"); // 设置命名空间
	}

	@Override
	public TurnOverT getTurnOverMoney() {

		return this.getSqlSession().selectOne("getTurnOverMoney");
	}

	
	
	
}
