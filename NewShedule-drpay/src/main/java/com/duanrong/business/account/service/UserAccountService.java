package com.duanrong.business.account.service;

import java.util.Map;

import base.exception.InsufficientBalanceException;
import base.exception.InsufficientFreezeAmountException;
import base.exception.NoOpenAccountException;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.model.UserAccount;
import com.duanrong.business.account.model.UserBill;

/**
 * 账户相关接口
 * 
 * @author xiao
 * 
 */
public interface UserAccountService {

	/**
	 * 账户查询
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 * @throws NoOpenAccountException 
	 */
	UserAccount getUserAccount(String userId) throws NoOpenAccountException;

	/**
	 * 账户查询（加锁 慎用）
	 * @param userId
	 * @return
	 * @throws NoOpenAccountException
	 */
	UserAccount queryUserAccountWithLock(String userId)
			throws NoOpenAccountException;
	/**
	 * 用户账户分页
	 * @param pageNo
	 * @param pageSize
	 * @param map
	 * @return
	 */
	PageInfo<UserAccount> getUserAccounts(int pageNo, int pageSize, Map<String, Object> map);
	/**
	 * 用户资金流水查询, 用户id为空，默认全部查询， 查询时间段默认查询当天，前后不能超过一个月
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param userBill
	 * @return
	 */
	PageInfo<UserBill> pageLite(int pageNo, int pageSize, UserBill userBill);

	/**
	 * 入账
	 * 
	 * @param userId
	 *            用户id
	 * @param money
	 *            入账金额
	 * @param businessType
	 *            业务类型
	 * @param operatorInfo
	 *            流水描述（用户查看）
	 * @param operatorDetail
	 *            流水详情
	 * @param requestNo
	 *            流水号
	 * @throws NoOpenAccountException
	 *             未开户
	 */
	void transferIn(String userId, double money, BusinessEnum businessType,
			String operatorInfo, String operatorDetail, String requestNo)
			throws NoOpenAccountException;

	/**
	 * 平台划款入账
	 * 
	 * @param userId
	 *            用户id
	 * @param money
	 *            入账金额
	 * @param businessType
	 *            业务类型
	 * @param operatorInfo
	 *            流水描述（用户查看）
	 * @param operatorDetail
	 *            流水详情
	 * @param requestNo
	 *            流水号
	 * @throws NoOpenAccountException
	 */
	void ptTransferIn(String userId, double money, BusinessEnum businessType,
			String operatorInfo, String operatorDetail, String requestNo)
			throws NoOpenAccountException;

	/**
	 * 出账
	 * 
	 * @param userId
	 *            用户id
	 * @param money
	 *            出账金额
	 * @param businessType
	 *            业务类型
	 * @param operatorInfo
	 *            流水描述（用户查看）
	 * @param operatorDetail
	 *            流水详情
	 * @param requestNo
	 *            流水号
	 * @throws NoOpenAccountException
	 *             未开户
	 * @throws InsufficientBalanceException
	 *             余额不足
	 */
	void transferOut(String userId, double money, BusinessEnum businessType,
			String operatorInfo, String operatorDetail, String requestNo)
			throws NoOpenAccountException, InsufficientBalanceException;

	/**
	 * 冻结资金
	 * 
	 * @param userId
	 *            用户id
	 * @param money
	 *            冻结金额
	 * @param businessType
	 *            业务类型
	 * @param operatorInfo
	 *            流水描述（用户查看）
	 * @param operatorDetail
	 *            流水详情
	 * @param requestNo
	 *            流水号
	 * @throws NoOpenAccountException
	 *             未开户
	 * @throws InsufficientBalanceException
	 *             余额不足
	 */
	void freeze(String userId, double money, BusinessEnum businessType,
			String operatorInfo, String operatorDetail, String requestNo)
			throws NoOpenAccountException, InsufficientBalanceException;

	/**
	 * 解冻资金
	 * 
	 * @param userId
	 *            用户id
	 * @param money
	 *            冻结金额
	 * @param businessType
	 *            业务类型
	 * @param operatorInfo
	 *            流水描述（用户查看）
	 * @param operatorDetail
	 *            流水详情
	 * @param requestNo
	 *            流水号
	 * @throws NoOpenAccountException
	 *             未开户
	 * @throws InsufficientFreezeAmountException
	 *             冻结金额不足
	 */
	void unfreeze(String userId, double money, BusinessEnum businessType,
			String operatorInfo, String operatorDetail, String requestNo)
			throws NoOpenAccountException, InsufficientFreezeAmountException;

	/**
	 * 从冻结中转出
	 * 
	 * @param userId
	 *            用户id
	 * @param money
	 *            转出金额
	 * @param businessType
	 *            业务类型
	 * @param operatorInfo
	 *            流水描述（用户查看）
	 * @param operatorDetail
	 *            流水详情
	 * @param requestNo
	 *            流水号
	 * @throws NoOpenAccountException
	 *             未开户
	 * @throws InsufficientFreezeAmountException
	 *             冻结金额不足
	 */
	void tofreeze(String userId, double money, BusinessEnum businessType,
			String operatorInfo, String operatorDetail, String requestNo)
			throws NoOpenAccountException, InsufficientFreezeAmountException;

	/**
	 * 开户
	 * 
	 * @param userId
	 *            用户id
	 * @param password
	 *            交易密码
	 */
	void createUserAccount(String userId, String password);
	
	/**
	 * 账户初始化
	 * @param userId
	 * @param balance
	 * @param freeze
	 */
	void initUserAccount(String userId, double balance, double freeze);

	
}
