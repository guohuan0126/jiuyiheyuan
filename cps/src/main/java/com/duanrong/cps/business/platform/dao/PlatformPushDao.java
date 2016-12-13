package com.duanrong.cps.business.platform.dao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import base.pagehelper.PageInfo;

import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.netloaneye.model.PushLoan;
import com.duanrong.cps.business.platform.model.PushInvest;
import com.duanrong.cps.business.platform.model.PushUserAccount;

public interface PlatformPushDao {

	/**
	 * 查询可以推送到相应第三方平台的项目信息
	 */
	public PageInfo<Loan> queryWaitPushLoanList(int pageNo, int pageSize, JSONObject json);
	
	/**
	 * 得到要推送标的信息
	 */
	public List<Loan> getLoanByLoanId(Map<String,Object>params);
	
	/**
	 * 推送标的插入数据库
	 */
	public int insertPushLoan(PushLoan pushLoan);
	
	/**
	 * 查询向第三方平台推送过的标的信息
	 * 
	 */
	public PageInfo<Loan> getLoanHistory(int pageNo, int pageSize,Map<String, Object> params) ;
	
	/**
	 * 推送的投资记录查入数据库
	 */
	public int insertPushInvest(PushInvest pushInvest);
	
	/**
	 * 推送用户资产记录插入数据库
	 */
	public int insertPushUserAccount(PushUserAccount userAccount);
	
	/**
	 * 查询push_loan表
	 */
	public List<PushLoan> getPushLoanInfo(Map<String,Object>params);
	
	/**
	 * 修改push_loan表
	 */
	public int updatePushLoan(PushLoan pushLoan);
	
	/**
	 * 查询push_investors表
	 */
	public List<PushInvest>getPushInvestorsInfo(Map<String,Object>params);
	
	/**
	 * 查询推送到第三方平台的投资记录
	 */
	public PageInfo<Invest>getInvestHistory(int pageNo, int pageSize,
			Map<String, Object> params);
}
