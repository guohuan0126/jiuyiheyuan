package com.duanrong.business.riskfund.dao.impl;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.riskfund.dao.RiskfunddetailDao;
import com.duanrong.business.riskfund.model.Riskfunddetail;

@Repository
public class RiskfunddetailDaoImpl extends BaseDaoImpl<Riskfunddetail> implements
		RiskfunddetailDao {
	public RiskfunddetailDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.riskfund.mapper.RiskfunddetailMapper");
	}
}
