package com.duanrong.business.yeepay.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;
import com.duanrong.business.yeepay.dao.WithholdDao;
import com.duanrong.business.yeepay.model.Withhold;


@Repository
public class WithholdDaoImpl extends BaseDaoImpl<Withhold> implements WithholdDao {

	public WithholdDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.yeepay.mapper.WithholdMapper");
	}
	
	@Override
	public PageInfo<Withhold> pageInfo(int pageNo, int pageSize,Withhold withhold) {
		PageHelper.startPage(pageNo, pageSize);
		List<Withhold> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo",withhold);
		PageInfo<Withhold> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
}
