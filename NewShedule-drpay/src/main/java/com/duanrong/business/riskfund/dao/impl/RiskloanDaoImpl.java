package com.duanrong.business.riskfund.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.repay.model.Repay;
import com.duanrong.business.riskfund.dao.RiskloanDao;
import com.duanrong.business.riskfund.model.Riskloan;

@Repository
public class RiskloanDaoImpl extends BaseDaoImpl<Riskloan> implements
		RiskloanDao {
	public RiskloanDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.riskfund.mapper.RiskloanMapper");
	}

	@Override
	public List<Riskloan> findList(int id) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".findList", id);
	}
}
