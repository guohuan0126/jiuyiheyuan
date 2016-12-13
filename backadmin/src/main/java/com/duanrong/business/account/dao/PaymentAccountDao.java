package com.duanrong.business.account.dao;

import com.duanrong.business.account.model.PaymentAccount;
import com.duanrong.business.account.model.PaymentBill;
import com.duanrong.business.account.model.PaymentChannel;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

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

	public PaymentChannel getChannelByCode(String code);

	public PageInfo<PaymentBill> pageLite(int pageNo, int pageSize,
			PaymentBill paymentBill);

}
