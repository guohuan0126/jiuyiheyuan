package com.duanrong.business.account.dao;

import base.dao.BaseDao;

import com.duanrong.business.account.model.PaymentAccount;
import com.duanrong.business.account.model.PaymentBill;

/**
 * 支付账户
 * @author xiao
 * @datetime 2016年11月23日 下午3:43:40
 */
public interface PaymentAccountDao extends BaseDao<PaymentAccount> {

	/**
	 * 支付账户查询（加锁）
	 * @param pamenyId
	 * @return
	 */
	public PaymentAccount getWithLock(String pamenyId);

	/**
	 * 插入支付流水
	 * @param paymentBill
	 */
	public void insert(PaymentBill paymentBill);

}
