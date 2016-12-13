package com.duanrong.business.yeepay.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.yeepay.dao.FreezeDao;
import com.duanrong.business.yeepay.model.Freeze;


@Repository
public class FreezeDaoImpl extends BaseDaoImpl<Freeze> implements FreezeDao {

	public FreezeDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.yeepay.mapper.FreezeMapper");
	}
	public void save(Freeze freeze) {
		this.getSqlSession().insert(this.getMapperNameSpace() + ".save",
				freeze);
	}
	@Override
	public PageInfo<Freeze> pageInfo(int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<Freeze> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo");
		PageInfo<Freeze> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
}
