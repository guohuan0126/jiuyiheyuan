package com.duanrong.drpay.business.account.service;

import base.exception.ParameterException;
import base.exception.UserAccountException;
import base.pagehelper.PageInfo;

import com.duanrong.drpay.business.account.model.UserAccount;
import com.duanrong.drpay.business.account.model.UserBill;
import com.duanrong.drpay.config.BusinessEnum;

/**
 * 账户相关接口
 * 账户相关接口手动开启提交事务
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
	 * @throws UserAccountException 
	 */
	UserAccount getUserAccount(String userId) throws UserAccountException;

	/**
	 * 账户查询（加锁 慎用）
	 * @param userId
	 * @return
	 * @throws UserAccountException
	 */
	UserAccount queryUserAccountWithLock(String userId) throws UserAccountException;
	/**
	 * 用户资金流水查询, 用户id为空，默认全部查询， 查询时间段默认查询当天，前后不能超过一个月
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param userBill
	 * @return
	 * @throws QueryTimeTooLongException
	 */
	PageInfo<UserBill> pageLite(int pageNo, int pageSize, UserBill userBill)
			throws ParameterException;

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
	 */
	void transferIn(String userId, double money, BusinessEnum businessType,
			String operatorInfo, String operatorDetail, String requestNo)
			throws UserAccountException;

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
	 */
	void ptTransferIn(String userId, double money, BusinessEnum businessType,
			String operatorInfo, String operatorDetail, String requestNo)
			throws UserAccountException;

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
	 */
	void transferOut(String userId, double money, BusinessEnum businessType,
			String operatorInfo, String operatorDetail, String requestNo)
			throws UserAccountException;

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
	 */
	void freeze(String userId, double money, BusinessEnum businessType,
			String operatorInfo, String operatorDetail, String requestNo)
			throws UserAccountException;

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
	 */
	void unfreeze(String userId, double money, BusinessEnum businessType,
			String operatorInfo, String operatorDetail, String requestNo)
			throws UserAccountException;

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
	 * @throws UserAccountException
	 *             未开户
	 */
	void tofreeze(String userId, double money, BusinessEnum businessType,
			String operatorInfo, String operatorDetail, String requestNo)
			throws UserAccountException;

	/**
	 * 开户
	 * 
	 * @param userId
	 *            用户id
	 * @param password
	 *            交易密码
	 * @throws UserAccountException
	 */
	void createUserAccount(String userId, String password, String authorization)
			throws UserAccountException;
	
	/**
	 * 查询用户最后一条流水
	 * 
	 * @param userId
	 * @return
	 */
	UserBill getLastUserBillByUserId(String userId);
	
	/**
	 * 根据流水查询账户信息
	 * @param userId
	 * @return
	 */
	UserAccount getUserMoney(String userId);
	
	
	void updateUserAccount(UserAccount userAccount);
}
