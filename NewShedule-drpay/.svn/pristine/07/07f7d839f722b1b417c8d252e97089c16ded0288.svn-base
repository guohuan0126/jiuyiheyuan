package com.duanrong.business.demand.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.business.demand.dao.DemandtreasureGuOutDetailDao;
import com.duanrong.business.demand.model.DemandtreasureGuOutDetail;
import com.duanrong.business.demand.model.DemandtreasureTransferIn;
import com.duanrong.business.demand.service.DemandtreasureGuOutDetailService;




@Service
public class DemandtreasureGuOutDetailServiceImpl implements DemandtreasureGuOutDetailService {
	@Resource
	DemandtreasureGuOutDetailDao demandtreasureGuOutDetailDao;
	
	public List<DemandtreasureGuOutDetail> find(DemandtreasureGuOutDetail detail) {
		return demandtreasureGuOutDetailDao.find(detail);
	}

	@Override
	public PageInfo<DemandtreasureGuOutDetail> pageInfo(int pageNo, int pageSize,DemandtreasureGuOutDetail d) {
		return demandtreasureGuOutDetailDao.pageInfo(pageNo,pageSize,d);
	}
}
