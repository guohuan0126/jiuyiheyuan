package com.duanrong.business.platformtransfer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.business.platformtransfer.dao.PlatformTransferDao;
import com.duanrong.business.platformtransfer.model.PlatformTransfer;
import com.duanrong.business.platformtransfer.service.PlatformTransferService;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2015-3-3 上午10:04:15
 * @Description : NewAdmin com.duanrong.business.platformtransfer.service.impl
 *              PlatformTransferServiceImpl.java
 * 
 */
@Service
public class PlatformTransferServiceImpl implements PlatformTransferService {

	@Autowired
	PlatformTransferDao platformTransferDao;

	
	@Override
	public PageInfo<PlatformTransfer> findPageInfo(int pageNo, int pageSize,
			Map map) {
		return platformTransferDao.pageInfo(pageNo, pageSize, map);
	}


	@Override
	public void insertBatch(List<PlatformTransfer> list) {
		platformTransferDao.insertBatch(list);
	}


	@Override
	public List<PlatformTransfer> getByLoanId(String loanId, String repayId, String type) {
		
		return platformTransferDao.getByLoanId(loanId, repayId, type);
	}


	@Override
	public List<PlatformTransfer> getList(PlatformTransfer platformTransfer) {
		return platformTransferDao.getPlatformTransferGroupCondition(platformTransfer);
	}


	@Override
	public double getMoneyByGroup(PlatformTransfer platformTransfer) {
		
		return platformTransferDao.getMoneyByGroup(platformTransfer);
	}


}
