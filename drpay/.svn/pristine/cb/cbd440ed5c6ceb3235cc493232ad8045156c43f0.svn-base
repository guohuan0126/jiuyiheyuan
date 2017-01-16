package com.duanrong.drpay.business.payment.service;

import base.exception.UserAccountException;

import com.duanrong.drpay.business.payment.model.Recharge;

import java.util.List;

/**
 * @Description: 充值
 * @Author: 林志明
 * @CreateDate: Sep 11, 2014
 */
public interface RechargeService {
	
	/**
	 * 更新充值记录 
	 */
	public void update(Recharge recharge);

	/**
	 * 充值成功操作
	 * 
	 * @param rechargeId
	 * @throws UserAccountException 
	 */
	public void rechargeSuccess(String rechargeId) throws UserAccountException;

	
	/**
	 * 充值成功操作
	 * 
	 * @param rechargeId 充值ID
	 * @param type 充值类型
	 * @throws UserAccountException 
	 */
	public void rechargeSuccess(String rechargeId, String type) throws UserAccountException;
	
	/**
	 * 通过ID查找充值信息
	 * @param id
	 * @return
	 */
	public Recharge get(String id);

	/**
	 * 保存充值记录
	 */
	public void insert(Recharge recharge);

	/**
	 * 根据条件进行组合查询
	 * 
	 * @return
	 */
	public List<Recharge> getByCondition(Recharge recharge);


}
