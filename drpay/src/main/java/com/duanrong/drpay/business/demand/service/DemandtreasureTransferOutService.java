package com.duanrong.drpay.business.demand.service;

import com.duanrong.drpay.business.demand.model.DemandtreasureTransferOut;

import base.exception.TradeException;

/**
 * @Description: 活期宝转出
 * @Author: 赵磊
 * @CreateDate: 2015-07-17
 */

public interface DemandtreasureTransferOutService {

	public void insert(DemandtreasureTransferOut demandOut);

	public void update(DemandtreasureTransferOut demandOut);

	/**
	 * 获取用户正在转出的总金额
	 * 
	 * @param userId
	 * @return
	 */
	public Double getOutMoneySumByStatus(String userId, String status);

	/**
	 * 获取用户正在转出的总金额中的利息
	 * 
	 * @param userId
	 * @return
	 */
	public Double getOutInterestSumByStatus(String userId, String status);

	public Integer getValidOutCount(String userId);

	/**
	 * 用户当日转出
	 * 
	 * @param userId
	 * @return
	 */
	public double getValidOutSumMoney(String userId);

	
	/**
	 * 增强型账户校验，校验转出金额是否大于用户的活期宝账户资金
	 * @throws TradeException 
	 */
	public void enhanceValidateTransferOut() throws TradeException;
}