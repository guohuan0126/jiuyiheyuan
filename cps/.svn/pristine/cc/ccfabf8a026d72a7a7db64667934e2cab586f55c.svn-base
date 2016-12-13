package com.duanrong.cps.business.bsy.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;










import base.pagehelper.PageInfo;

import com.duanrong.cps.business.bsy.model.BsyInterest;
import com.duanrong.cps.business.bsy.model.BsyInvest;
import com.duanrong.cps.business.bsy.model.BsyChangeInvestStatus;


import com.duanrong.cps.business.bsy.model.BsyPushInterest;
import com.duanrong.cps.business.bsy.model.BsyPushLoad;
import com.duanrong.cps.business.bsy.model.BsyPushRepayMent;
import com.duanrong.cps.business.bsy.model.BsyPushedInvest;
import com.duanrong.cps.business.bsy.model.BsyRepayMoney;
import com.duanrong.cps.business.bsy.model.InvestByBsy;
import com.duanrong.cps.business.bsy.model.PlatformUserRelation;



public interface BsyDao {


	public int insertBsyPushLoad(List<BsyPushLoad> list);
	//查询车详情
	public Map getVehicle(String loanId);
	//查询房的详情
	public Map getHouse(String loanId);
	//查询企业详情
	public Map getEnterprise(String loanId);
	//查询供应宝详情
	public Map getSupplychain(String loanId);
	//查询出变化的项目
	public List<Map> getChangeBsyLoan();
	//更改已发标的状态
	public void updateByPrimaryKeySelective(BsyPushLoad bsyPushLoad);

	/**
	 * 得到比搜益用户所投标的有起息日期的记录
	 * */
	public List<BsyInvest> getBsyInterest();
	
	/**
	 * 插入比搜益起息状态表
	 * */
	public void insertInterest(BsyInterest bsyInterest);
	

	/**
	 * 查询要推送的回款状态记录
	 * */
	public List<BsyRepayMoney> getBsyRepayMoney();
	
	/**
	 * 插入推送的回款状态记录
	 * */
	public void insertRepayMoney(BsyRepayMoney bsyRepayMoneyList);
	

	//查询出为推送的投资记录
	public List<BsyInvest> getPushInvest();
	//插入已推送的投资记录
	public void insertBsyPushInvest(HashMap<String,Object> params);
	//查询已推送过的投资记录状态
	public List<BsyChangeInvestStatus> getpushChangeInvestStatus();
	//更新 交易状态更改的交易记录
	public void updatePushInvestStatus(HashMap<String, Object> params);
	public PageInfo<BsyPushedInvest> getInvestStatus(int pageNo, int pageSize,
			Map<String, Object> params);
	//查询比搜益用户的投资记录
	public PageInfo<InvestByBsy> getInvestRecordsByBsy(int pageNo,
			int pageSize, Map<String, Object> params);
	//查询推送的起息状态记录
	public PageInfo<BsyPushInterest> getbsyPushInterest(int pageNo,
			int pageSize, Map<String, Object> params);
	//查询推送的回款信息
	public PageInfo<BsyPushRepayMent> getbsyPushRepayMent(int pageNo,
			int pageSize, Map<String, Object> params);
	//比搜益用户明细
	public PageInfo<PlatformUserRelation> getBsyUserInfo(int pageNo,
			int pageSize, Map<String, Object> params);
	//查询八金社投资记录
	public PageInfo<InvestByBsy> getInvestRecordsByBjs(int pageNo, int pageSize,
			Map<String, Object> params);
}
