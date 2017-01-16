package com.duanrong.drpay.business.demand.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.duanrong.drpay.business.demand.dao.DateRateDao;
import com.duanrong.drpay.business.demand.model.DateRate;

import base.dao.impl.BaseDaoImpl;

@Repository
public class DateRateDaoImpl extends BaseDaoImpl<DateRate> implements DateRateDao {

	public DateRateDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.demand.mapper.DateRateMapper");
	}
	
	@Override
	public List<DateRate> getRateDays(int days) {
		return getSqlSession().selectList(
				getMapperNameSpace() + ".getRateDays", days);
	}
}
