package com.duanrong.business.app.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.app.dao.APPDao;
import com.duanrong.business.app.model.APP;
import com.duanrong.business.repay.model.Repay;

@Repository
public class APPDaoImpl extends BaseDaoImpl<APP> implements APPDao {

	public APPDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.app.mapper.APP");
	}

	@Override
	public PageInfo<APP> findPageInfo(int pageNo, int pageSize, Map map) {
		PageHelper.startPage(pageNo, pageSize);
		List<APP> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo", map);
		PageInfo<APP> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

}
