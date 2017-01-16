package com.duanrong.drpay.business.account.dao.impl;

import org.springframework.stereotype.Repository;

import com.duanrong.drpay.business.account.dao.FreezeDao;
import com.duanrong.drpay.business.account.model.Freeze;

import base.dao.impl.BaseDaoImpl;




@Repository
public class FreezeDaoImpl extends BaseDaoImpl<Freeze> implements FreezeDao {

	public FreezeDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.account.mapper.FreezeMapper");
	}

}
