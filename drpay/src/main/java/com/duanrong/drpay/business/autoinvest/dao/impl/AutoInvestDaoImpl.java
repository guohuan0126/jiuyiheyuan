package com.duanrong.drpay.business.autoinvest.dao.impl;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.drpay.business.autoinvest.dao.AutoInvestDao;
import com.duanrong.drpay.business.autoinvest.model.AutoInvest;

@Repository
public class AutoInvestDaoImpl extends BaseDaoImpl<AutoInvest> implements
		AutoInvestDao {

	public AutoInvestDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.autoinvest.mapper.AutoInvest");
	}

}
