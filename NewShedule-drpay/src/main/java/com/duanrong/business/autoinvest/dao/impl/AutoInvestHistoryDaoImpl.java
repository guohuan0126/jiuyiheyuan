package com.duanrong.business.autoinvest.dao.impl;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.autoinvest.dao.AutoInvestHistoryDao;
import com.duanrong.business.autoinvest.model.AutoInvestHistory;

@Repository
public class AutoInvestHistoryDaoImpl extends BaseDaoImpl<AutoInvestHistory>
		implements AutoInvestHistoryDao {

	public AutoInvestHistoryDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.autoinvest.mapper.AutoInvestHistory");
	}
}
