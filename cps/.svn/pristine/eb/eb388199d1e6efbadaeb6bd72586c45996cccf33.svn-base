package com.duanrong.cps.business.bsy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.cps.business.bsy.model.BsyChangeInvestStatus;
import com.duanrong.cps.business.bsy.model.BsyInvest;
import com.duanrong.cps.business.bsy.model.BsyPushInterest;
import com.duanrong.cps.business.bsy.model.BsyPushLoad;
import com.duanrong.cps.business.bsy.model.BsyPushRepayMent;
import com.duanrong.cps.business.bsy.model.BsyPushedInvest;
import com.duanrong.cps.business.bsy.model.InvestByBsy;
import com.duanrong.cps.business.bsy.model.PlatformUserRelation;


public interface BsyService {

	/**
	 * 向比搜益推送项目
	 * @param id
	 * @return
	 */
	Map insertBsyPushLoan(String id,String userid);
	
	//查询出变化的项目
	public List<Map> getChangeBsyLoan();
	
	//更改已发标的状态
	public void updateByPrimaryKeySelective(BsyPushLoad bsyPushLoad);

	//查询未推送的投资记录
	List<BsyInvest> getPushInvest();

	void insertBsyPushInvest(HashMap<String,Object> params);

	List<BsyChangeInvestStatus> getpushChangeInvestStatus();
	//推送 交易状态更改的交易记录
	void updatePushInvestStatus(HashMap<String, Object> params);
	//查询所有bsy 推送过的交易记录
	PageInfo<BsyPushedInvest> getInvestStatus(int pageNo , int pageSize ,
			Map<String, Object> params);
	//查询比搜益用户的投资记录
	PageInfo<InvestByBsy> getInvestRecordsByBsy(int pageNo, int pageSize,
			Map<String, Object> params);
	// 查询比搜益起息状态推送
	PageInfo<BsyPushInterest> getbsyPushInterest(int pageNo, int pageSize,
			Map<String, Object> params);
	//查询比搜益回款推送记录
	PageInfo<BsyPushRepayMent> getbsyPushRepayMent(int parseInt, int parseInt2,
			Map<String, Object> params);
	//查询比搜益用户明细
	PageInfo<PlatformUserRelation> getBsyUserInfo(int parseInt, int parseInt2,
			Map<String, Object> params);
	//查询八金社投资记录
	public PageInfo<InvestByBsy> getInvestRecordsByBjs(int pageNo, int pageSize,
			Map<String, Object> params);
}
