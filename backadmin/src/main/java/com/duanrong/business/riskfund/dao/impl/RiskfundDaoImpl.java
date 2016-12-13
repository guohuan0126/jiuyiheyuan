package com.duanrong.business.riskfund.dao.impl;

import org.springframework.stereotype.Repository;
import base.dao.impl.BaseDaoImpl;
import com.duanrong.business.riskfund.dao.RiskfundDao;
import com.duanrong.business.riskfund.model.Riskfund;

@Repository
public class RiskfundDaoImpl extends BaseDaoImpl<Riskfund> implements
		RiskfundDao {
	public RiskfundDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.riskfund.mapper.RiskfundMapper");
	}

	@Override
	public void updateRisk() {
		getSqlSession().update(
				getMapperNameSpace() + ".updateRisk");		
	}
}
