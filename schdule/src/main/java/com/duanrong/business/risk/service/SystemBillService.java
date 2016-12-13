package com.duanrong.business.risk.service;

import java.util.Date;

import base.exception.InsufficientBalance;

/**
 * @Description: 系统账户
 * @Author: 林志明
 * @CreateDate: Nov 22, 2014
 */
public interface SystemBillService {

	/**
	 * 获取账户余额
	 * 
	 * @return
	 */
	public double getBalance();

	/**
	 * 转出
	 * 
	 * @param money
	 *            金额
	 * @param operatorType
	 *            操作类型
	 * @param operatorDetail
	 *            操作详情
	 * 
	 * @throws InsufficientBalance
	 *             余额不足
	 */
	public void transferOut(double money, String reason, String detail)
			throws InsufficientBalance;

	/**
	 * 转入.
	 * 
	 * @param money
	 *            金额
	 * @param operatorType
	 *            操作类型
	 * @param operatorDetail
	 *            操作详情
	 */
	public void transferInto(double money, String reason, String detail);

	/**
	 * 转入.
	 * 
	 * @param money
	 *            金额
	 * @param operatorType
	 *            操作类型
	 * @param operatorDetail
	 *            操作详情
	 */
	public void transferInto(double money, String reason, String detail,
			Date date);

}
