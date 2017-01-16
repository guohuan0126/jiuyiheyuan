package com.duanrong.drpay.business.account.dao;

import base.dao.BaseDao;
import base.exception.UserAccountException;
import base.pagehelper.PageInfo;

import com.duanrong.drpay.business.account.model.UserAccount;
import com.duanrong.drpay.business.account.model.UserBill;

/**
 * 
 * @author xiao
 * @datetime 2016年11月25日 上午9:12:41
 */
public interface UserAccountDao extends BaseDao<UserAccount> {

	/**
	 * 账户查询（加锁）
	 * @param userId
	 * @return
	 * @throws UserAccountException 
	 */
	UserAccount get(String userId) throws UserAccountException;
	
	/**
	 * 账户查询（加锁）
	 * @param userId
	 * @return
	 * @throws UserAccountException 
	 */
	UserAccount getWithLock(String userId) throws UserAccountException;
	
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
	 * 冻结账户
	 * @param userId
	 */
	void freezeAccount(String userId);

	/**
	 * 解冻账户
	 * @param userId
	 */
	public void unfreezeAccount(String userId);
	
	/**
	 * 流水分页
	 * @param pageNo
	 * @param pageSize
	 * @param userBill
	 * @return
	 */
	PageInfo<UserBill> pageLite(int pageNo, int pageSize, UserBill userBill);
	
	/**
	 * 根据流水查询账户信息
	 * @param userId
	 * @return
	 */
	public UserAccount getUserMoney(String userId);
	

}