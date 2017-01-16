package com.duanrong.drpay.business.account.dao;
import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.drpay.business.account.PlatformAccountEnum;
import com.duanrong.drpay.business.account.model.PlatformAccount;
import com.duanrong.drpay.business.account.model.PlatformBill;

/**
 * 
 * @author xiao
 * @datetime 2016年11月25日 上午11:22:08
 */
public interface PlatformAccountDao extends BaseDao<PlatformAccount> {

	/**
	 * 查询平台账户
	 * @return
	 */
	PlatformAccount get(PlatformAccountEnum platformAccountType);
	
	/**
	 * 查询平台账户（加锁 慎用）
	 * @return
	 */
	PlatformAccount getWithLock(PlatformAccountEnum platformAccountType);
	
	/**
	 * 插入平台账户流水
	 * @param platformBill
	 */
	void insertBill(PlatformBill platformBill);

	/**
	 * 流水分页
	 * @param pageNo
	 * @param pageSize
	 * @param platformBill
	 * @return
	 */
	PageInfo<PlatformBill> pageLite(int pageNo, int pageSize,
			PlatformBill platformBill);

}
