package com.duanrong.cps.business.invest.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import base.exception.ExceedMaxAcceptableRate;
import base.exception.ExceedMoneyNeedRaised;
import base.exception.InsufficientBalance;
import base.exception.InvestMoneyException;
import base.exception.InvestorsAndFinanciersIDException;
import base.pagehelper.PageInfo;

import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.user.model.User;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-9-1 上午11:23:16
 * @Description : drsoa Maven Webapp com.duanrong.business.invest.service
 *              InvestService.java
 * 
 */
public interface InvestService {

	
	/**
	 * 投资记录（只显示天眼用户的投资记录）
	 * @param pageNo
	 * @param pageSize
	 * @param map
	 * @return
	 */
	public PageInfo<Invest> getInvestRecordsNetLoanEye(int pageNo, int pageSize, Map<String, Object> map);

	public BigDecimal getTotalMoneyForNetLoanEye(Map<String, Object> map);

	public int getTotalNumForNetLoanEye(Map<String, Object> map);

	public List<Invest> getExportInvestInfo(Map<String, Object> map);

	public Object getInvestDateLastOne(String loanId);

	public Object getInvestDateFirstOne(String loanId);

	/**
	 * 根据LOANID查询项目所有投资人
	 * @param loanId
	 * @return
	 */
	public Object getTotalCount(String loanId);

	/**
	 * 根据loanId查询投资情况
	 * @param loanId
	 * @return
	 */
	public Object getInvestRecords(String loanId);

	/**
	 * 根据项目ID查询已筹款总额
	 * @param id
	 * @return
	 */
	public Double getTotalMoney4AlreadyFundraising(String id);
	
}
