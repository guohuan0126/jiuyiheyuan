package com.duanrong.drpay.business.account.dao;
import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.drpay.business.account.model.PaymentAccount;
import com.duanrong.drpay.business.account.model.PaymentBill;

/**
 * 
 * @author xiao
 * @datetime 2016年11月25日 上午9:41:06
 */
public interface PaymentAccountDao extends BaseDao<PaymentAccount> {

	/**
	 * 支付账户查询（加锁）
	 * @param pamenyId
	 * @return
	 */
	PaymentAccount getWithLock(String pamenyId);
	
	/**
	 * 插入支付流水
	 * @param paymentBill
	 */
	void insertBill(PaymentBill paymentBill);
	/**
	 * 支付账户流水分页
	 * @param pageNo
	 * @param pageSize
	 * @param paymentBill
	 * @return
	 */
	PageInfo<PaymentBill> pageLite(int pageNo, int pageSize,
			PaymentBill paymentBill);

}
