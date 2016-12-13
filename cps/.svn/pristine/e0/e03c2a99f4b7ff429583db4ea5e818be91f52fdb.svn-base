package com.duanrong.cps.business.platform.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.cps.business.platform.dao.DemandBillDao;
import com.duanrong.cps.business.platform.model.DemandBill;

@Repository
public class DemandBillDaoImpl extends BaseDaoImpl<DemandBill> implements DemandBillDao{

	public DemandBillDaoImpl(){
		this.setMapperNameSpace("com.duanrong.cps.business.thirdPlatform.mapper.platformDemandMapper");
	}

	/**
	 * 查询用户活期流水记录
	 */
	@Override
	public PageInfo<DemandBill> queryDemandBill(int pageNo,
			int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<DemandBill> list = getSqlSession().selectList(
				getMapperNameSpace() + ".queryDemandBill",params);
		PageInfo<DemandBill> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	
}
