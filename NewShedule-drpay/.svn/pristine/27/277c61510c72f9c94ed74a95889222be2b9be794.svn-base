package com.duanrong.business.demand.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import util.DateUtilPlus;
import util.Log;
import util.MyStringUtils;

import base.pagehelper.PageInfo;

import com.duanrong.business.demand.DemandtreasureConstants;
import com.duanrong.business.demand.dao.DemandtreasureGuOutDao;
import com.duanrong.business.demand.dao.DemandtreasureGuOutDetailDao;
import com.duanrong.business.demand.dao.DemandtreasureTransferOutDao;
import com.duanrong.business.demand.model.DemandtreasureGuOut;
import com.duanrong.business.demand.model.DemandtreasureGuOutDetail;
import com.duanrong.business.demand.model.DemandtreasureTransferOut;
import com.duanrong.business.demand.service.DemandtreasureTransferOutService;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.service.InformationService;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.IdUtil;
import com.duanrong.util.MapUtil;
import com.duanrong.util.ToType;

@Service
public class DemandtreasureTransferOutServiceImpl implements DemandtreasureTransferOutService{

	
	@Resource
	DemandtreasureTransferOutDao demandtreasureTransferOutDao;
	@Resource
	TrusteeshipOperationService trusteeshipOperationService;
	@Resource
	DemandtreasureGuOutDao demandtreasureGuOutDao;
	@Resource
	DemandtreasureGuOutDetailDao demandtreasureGuOutDetailDao;	
	@Resource
	Log log;
	@Resource
	InformationService informationService;
	
	@Override
	public void insert(DemandtreasureTransferOut demandOut) {
		demandtreasureTransferOutDao.insert(demandOut);
	}

	@Override
	public void update(DemandtreasureTransferOut demandOut) {
		demandtreasureTransferOutDao.update(demandOut);
		
	}

	@Override
	public DemandtreasureTransferOut gettotal() {
		 return demandtreasureTransferOutDao.gettotal();
	}

	public PageInfo<DemandtreasureTransferOut> pageInfo(int pageNo, int pageSize,Map map) {
		return demandtreasureTransferOutDao.pageInfo(pageNo, pageSize, map);
	}

	@Override
	public List<DemandtreasureTransferOut> findAll() {
		return demandtreasureTransferOutDao.findAll();
	}

	@Override
	public List<DemandtreasureTransferOut> gettotalUser() {
		return demandtreasureTransferOutDao.gettotalUser();
	}

	@Override
	public List<DemandtreasureTransferOut> findAllSuccess() {
		return demandtreasureTransferOutDao.findAllSuccess();
	}
	
	
}
