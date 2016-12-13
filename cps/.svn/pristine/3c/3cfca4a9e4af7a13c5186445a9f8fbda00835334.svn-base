package com.duanrong.cps.business.platform.service.impl;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.cps.business.aiyouqian.model.AiyouqianPushLoan;
import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.netloaneye.model.PushLoan;
import com.duanrong.cps.business.platform.dao.PlatformPushDao;
import com.duanrong.cps.business.platform.service.PlatformPushService;

@Service
public class PlatformPushServiceImpl implements PlatformPushService{

	@Autowired
	private PlatformPushDao platformPushDao;
	
	/**
	 * 查询可以推送到相应第三方平台的项目信息
	 */
	@Override
	public PageInfo<Loan> queryWaitPushLoanList(int pageNo, int pageSize,
			JSONObject json) {
		
		return platformPushDao.queryWaitPushLoanList(pageNo, pageSize,  json);
	}


	/**
	 * 查询向第三方平台推送过的标的信息
	 * 
	 */
	@Override
	public PageInfo<Loan> getLoanHistory(int pageNo, int pageSize,
			Map<String, Object> params) {
		return platformPushDao.getLoanHistory(pageNo, pageSize, params);
	}

	/**
	 * 查询推送到第三方平台的投资记录
	 */
	@Override
	public PageInfo<Invest> getInvestHistory(int pageNo, int pageSize,
			Map<String, Object> params) {
		
		return platformPushDao.getInvestHistory(pageNo, pageSize, params);
	}

	
	

}
