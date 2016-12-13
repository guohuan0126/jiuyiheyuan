package com.duanrong.business.autoinvest.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.autoinvest.dao.AutoInvestDao;
import com.duanrong.business.autoinvest.model.AutoInvest;

@Repository
public class AutoInvestDaoImpl extends BaseDaoImpl<AutoInvest> implements
		AutoInvestDao {

	public AutoInvestDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.autoinvest.mapper.AutoInvest");
	}

	@Override
	public Long getQueueNumber(String userId) {
		Long queueNumber = this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getQueueNumber", userId);
		return queueNumber == 0 ? 1 : queueNumber;
	}

	@Override
	public Long getAutoInvestConut() {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getAutoInvestConut");
	}

	@Override
	public List<AutoInvest> getConsistAutoInvest(Map<String, Object> map) {		
				
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getConsistAutoInvest", map);
	}

	
}
