package com.duanrong.drpay.business.payment.dao.impl;


import base.dao.impl.BaseDaoImpl;
import com.duanrong.drpay.business.payment.dao.WithdrawCashDao;
import com.duanrong.drpay.business.payment.model.WithdrawCash;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class WithdrawCashDaoImpl extends BaseDaoImpl<WithdrawCash> implements
		WithdrawCashDao {

	public WithdrawCashDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.payment.mapper.WithdrawCashMapper");
	}

	@Override
	public Integer getHolidayDate(Date date, String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("date", date);
		param.put("type", type);
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getHolidayDate", param);
	}

	@Override
	public WithdrawCash getWithLock(String id) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getWithLock", id);

	}
}
