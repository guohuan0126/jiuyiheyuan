package com.duanrong.business.demand.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.dao.DateRateDao;
import com.duanrong.business.demand.model.DateRate;

@Repository
public class DateRateDaoImpl extends BaseDaoImpl<DateRate> implements DateRateDao {

	public DateRateDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.demand.mapper.DateRateMapper");
	}
	@Override
	public PageInfo<DateRate> readPageInfo(int pageNo, int pageSize,Map map) {
		PageHelper.startPage(pageNo, pageSize);
		List<DateRate> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo",map);
		PageInfo<DateRate> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	
	@Override
	public double readRate(Date date) {
		return getSqlSession().selectOne(getMapperNameSpace() + ".getRate", date);
	}
	@Override
	public List<DateRate> readRateList(Date date) {
		List<DateRate> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getRateList",date);
		return list;
	}
}
