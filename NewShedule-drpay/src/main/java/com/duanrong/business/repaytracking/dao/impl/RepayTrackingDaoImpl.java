package com.duanrong.business.repaytracking.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.repaytracking.dao.RepayTrackingDao;
import com.duanrong.business.repaytracking.model.RepayTracking;

@Repository
public class RepayTrackingDaoImpl extends BaseDaoImpl<RepayTracking> implements
		RepayTrackingDao {

	public RepayTrackingDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.repaytracking.mapper.RepayTrackingMapper");
	}

	@Override
	public PageInfo<RepayTracking> getByUserId(int pageNo, int pageSize,
			String userId) {
		PageHelper.startPage(pageNo, pageSize);
		List<RepayTracking> list = this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getByUserId", userId);
		PageInfo<RepayTracking> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

}
