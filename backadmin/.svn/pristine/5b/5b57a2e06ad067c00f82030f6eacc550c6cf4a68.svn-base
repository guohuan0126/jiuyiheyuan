package com.duanrong.business.account.dao;

import java.util.List;
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
	 * 更新用户流水
	 * @param userBill
	 */
	void updateUserBill(UserBill userBill);

	PageInfo<UserBill> pageLite(int pageNo, int pageSize, UserBill userBill);

	PageInfo<UserAccount> getUserAccounts(int pageNo, int pageSize,Map params);

	List<UserBill> getUserBills(String userId);
	
	/**
	 * 条件查询
	 * @param userBill
	 * @return
	 */
	List<UserBill> getUserBillsByWhere(UserBill userBill);
}
