package com.duanrong.business.account.dao;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.model.PlatformAccount;
import com.duanrong.business.account.model.PlatformBill;

/**
 * 平台账户
 * @author xiao
 * @datetime 2016年11月23日 下午1:59:45
 */
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

	/**
	 * 插入平台流水
	 * @param platformBill
	 */
	public void insert(PlatformBill platformBill);

	/**
	 * 分页
	 * @param pageNo
	 * @param pageSize
	 * @param platformBill
	 * @return
	 */
	public PageInfo<PlatformBill> pageLite(int pageNo, int pageSize,
			PlatformBill platformBill);

}
