package com.duanrong.business.account.dao;

import java.util.Map;

import base.dao.BaseDao;
import base.exception.NoOpenAccountException;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.model.UserAccount;
import com.duanrong.business.account.model.UserBill;

public interface UserAccountDao extends BaseDao<UserAccount> {

	/**
	 * 查询账户
	 * @param userId
	 * @return
	 * @throws NoOpenAccountException
	 */
	UserAccount get(String userId) throws NoOpenAccountException;
	
	/**
	 * 查询账户（加锁）
	 * @param userId
	 * @return
	 * @throws NoOpenAccountException 
	 */
	UserAccount getWithLock(String userId) throws NoOpenAccountException;

	/**
	 * 查询用户最后一条流水
	 * 
	 * @param userId
	 * @return
	 */
	UserBill getLastUserBillByUserId(String userId);

	/**
	 * 添加资金流水
	 * 
	 * @param userBill
	 */
	void insertUserBill(UserBill userBill);
	
	/**
	 * 流水分页
	 * @param pageNo
	 * @param pageSize
	 * @param userBill
	 * @return
	 */
	PageInfo<UserBill> pageLite(int pageNo, int pageSize, UserBill userBill);
	
	/**
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param map
	 * @return
	 */
	PageInfo<UserAccount> getUserAccounts(int pageNo, int pageSize,Map map);
}
