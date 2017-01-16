package com.duanrong.drpay.business.payment.dao;

import base.dao.BaseDao;
import com.duanrong.drpay.business.payment.model.Recharge;

import java.util.List;

/**
 * @Description: 充值
 * @Author: 林志明
 * @CreateDate: Sep 11, 2014
 */
public interface RechargeDao extends BaseDao<Recharge> {

	/**
	 * 根据条件进行组合查询
	 * 
	 * @return
	 */
	List<Recharge> getByCondition(Recharge recharge);

	/**
	 * 查询充值总额
	 * 
	 * @param recharge
	 * @return
	 */
	Double getTotalRecharge(Recharge recharge);
	
	
	/**
	 * 查询当天快捷充值总额度
	 * @param userId
	 * @return
	 */
	List<Recharge> findQuickRechargeForToday(String userId);
	
	/**
	 * 查询当天快捷充值总额度
	 * @param userId
	 * @return
	 */
	List<Recharge> findQuickRechargeForMonth(String userId);

	/**
	 * 查询（加锁， 慎用）
	 * @param id
	 * @return
	 */
	Recharge getWithLock(String id);
}
