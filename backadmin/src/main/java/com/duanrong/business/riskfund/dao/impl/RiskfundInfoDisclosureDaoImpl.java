package com.duanrong.business.riskfund.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import base.dao.impl.BaseDaoImpl;
import com.duanrong.business.riskfund.dao.RiskfundInfoDisclosureDao;
import com.duanrong.business.riskfund.model.RiskfundInfoDisclosure;


@Repository
public class RiskfundInfoDisclosureDaoImpl extends BaseDaoImpl<RiskfundInfoDisclosure> implements
		RiskfundInfoDisclosureDao {
	public RiskfundInfoDisclosureDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.riskfund.mapper.RiskfundInfoDisclosureMapper");
	}

	@Override
	public List<RiskfundInfoDisclosure> findList(
			Map<String, Object> params) {
		List<RiskfundInfoDisclosure> disclosurelist = this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".findList",params);
		return disclosurelist;
	}

	@Override
	public int deleteByPrimaryKey(String id) {
	 return this.getSqlSession().delete(this.getMapperNameSpace() + ".deleteByPrimaryKey",id);
		
	}



}
