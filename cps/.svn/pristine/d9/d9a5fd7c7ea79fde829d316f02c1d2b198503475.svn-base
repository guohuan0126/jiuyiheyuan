package com.duanrong.cps.business.aiyouqian.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.cps.business.aiyouqian.model.AiyouqianPushLoan;
import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.platform.service.PushService;

public interface AiyouqianService extends PushService{
	
	/**
	 * 返回要向爱有钱推送标的的信息
	 * @return
	 */
	public Map<String,Object> InsertPushLoanToAiyouqian(String id, String userid);
	
	/**
	 * 把推送的项目插入数据库
	 */
	public Integer insertToPushLoan(List<AiyouqianPushLoan>params,String userid);
	
	/**
	 *爱有钱刷新用户数据接口
	 */
	public JSONObject getAccInfo(String userId);
	
	/**
	 * 查询标的投资情况接口
	 */
	public JSONObject getLoanInvest(String loanId) ;
	
}
