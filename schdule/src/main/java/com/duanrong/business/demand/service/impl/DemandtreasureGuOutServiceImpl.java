package com.duanrong.business.demand.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.business.demand.dao.DemandtreasureGuOutDao;
import com.duanrong.business.demand.model.DemandtreasureGuOut;
import com.duanrong.business.demand.service.DemandtreasureGuOutService;




@Service
public class DemandtreasureGuOutServiceImpl implements DemandtreasureGuOutService {
	@Resource
	DemandtreasureGuOutDao demandtreasureGuOutDao;
	
	public PageInfo<DemandtreasureGuOut> pageInfo(int pageNo, int pageSize) {
		return demandtreasureGuOutDao.pageInfo(pageNo, pageSize);
	}

	@Override
	public DemandtreasureGuOut get(String id) {
		return demandtreasureGuOutDao.get(id);
	}

	@Override
	public double findGuCount() {
		return demandtreasureGuOutDao.findGuCount()==null?0:demandtreasureGuOutDao.findGuCount();
	}

	@Override
	public void update(DemandtreasureGuOut gu) {
		demandtreasureGuOutDao.update(gu);
	}
}
