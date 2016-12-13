package com.duanrong.business.account.dao;

import com.duanrong.business.account.model.PlatformAccount;
import com.duanrong.business.account.model.PlatformBill;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

public interface PlatformAccountDao extends BaseDao<PlatformAccount> {

	/**
	 * 查询平台账户信息
	 * @return
	 */
	public PlatformAccount get();
	
	/**
	 * 查询平台账户信息（加锁）
	 * @return
	 */
	public PlatformAccount getWithLock();

	public void insert(PlatformBill platformBill);

	public PageInfo<PlatformBill> pageLite(int pageNo, int pageSize,
			PlatformBill platformBill);

}
