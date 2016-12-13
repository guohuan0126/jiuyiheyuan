package com.duanrong.cps.business.loan.service;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.cps.business.loan.model.Loan;


/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : lxw
 * 
 */
public interface LoanService {

	
	/**
	 * 查询可以推送网贷天眼的项目列表
	 * @param pageNo
	 * @param loan
	 * @return
	 */
	public PageInfo<Loan> getwaitPushLoanList(String pageNo, Loan loan);
	
	/**
	 * 查询可以推送比搜益的项目列表
	 * @param pageNo
	 * @param loan
	 * @return
	 */
	public PageInfo<Loan> getBsyWaitPushLoanList(String pageNo, Loan loan);
	
	/**
	 * 查询推送到比搜益的项目列表
	 * @param pageNo
	 * @param loan
	 * @return
	 */
	public PageInfo<Loan> getBsyLoanHistory(String pageNo,Map<String,Object>paramMap);
	
	/**
	 * 
	 * @description 根据id查询单条借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午12:37:14
	 * @param id
	 * @return
	 */
	public Loan get(String id);
}
