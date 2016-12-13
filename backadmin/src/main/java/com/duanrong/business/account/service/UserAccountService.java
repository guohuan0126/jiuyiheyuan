package com.duanrong.business.account.service;

import java.util.List;
import java.util.Map;

import base.exception.InsufficientBalanceException;
import base.exception.InsufficientFreezeAmountException;
import base.exception.NoOpenAccountException;
import base.exception.QueryTimeTooLongException;
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
	 */
	UserAccount readUserAccount(String userId);

	PageInfo<UserAccount> readUserAccounts(int pageNo, int pageSize,Map params);

	/**
	 * 用户资金流水查询, 用户id为空，默认全部查询， 查询时间段默认查询当天，前后不能超过一个月
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param userBill
	 * @return
	 * @throws QueryTimeTooLongException
	 */
	PageInfo<UserBill> readPageLite(int pageNo, int pageSize, UserBill userBill)
			throws QueryTimeTooLongException;

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

	List<UserBill> readUserBills(String userId);

	/**
	 * 同步账户
	 * @param userId
	 * @param seqNum
	 * @throws Exception 
	 */
	void transfer(String userId, int seqNum) throws Exception;
}
