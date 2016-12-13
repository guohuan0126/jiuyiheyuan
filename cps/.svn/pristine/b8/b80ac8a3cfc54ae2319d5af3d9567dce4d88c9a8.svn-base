package com.duanrong.cps.business.platform.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import base.pagehelper.PageInfo;

import com.duanrong.cps.business.aiyouqian.model.AiyouqianPushLoan;
import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.netloaneye.model.PushLoan;

public interface PlatformPushService {
	
	
	/**
	 * 查询可以推送到相应第三方平台的项目信息
	 */
	public PageInfo<Loan> queryWaitPushLoanList(int pageNo, int pageSize, JSONObject json);

	/**
	 * 查询向第三方平台推送过的标的信息
	 * 
	 */
	public PageInfo<Loan> getLoanHistory(int pageNo, int pageSize,
			Map<String, Object> params);
	/**
	 * 查询推送到第三方平台的投资记录
	 */
	public PageInfo<Invest>getInvestHistory(int pageNo, int pageSize,
			Map<String, Object> params);
}
