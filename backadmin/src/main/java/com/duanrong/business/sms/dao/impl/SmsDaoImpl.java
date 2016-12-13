package com.duanrong.business.sms.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.sms.dao.SmsDao;
import com.duanrong.business.sms.model.Sms;

@Repository
public class SmsDaoImpl extends BaseDaoImpl<Sms> implements SmsDao {

	public SmsDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.sms.mapper.SmsMapper");
	}
	@Override
	public PageInfo<Sms> pageInfo(int pageNo, int pageSize, Map map) {
		PageHelper.startPage(pageNo, pageSize);
		List<Sms> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo", map);
		PageInfo<Sms> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	@Override
	public List<Sms> getSmsNum(Map map) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getSmsNum", map);
	}
}
