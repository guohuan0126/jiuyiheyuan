package com.duanrong.business.account.service;

import base.exception.InsufficientBalance;
import base.exception.InsufficientFreeze;
import base.exception.InsufficientFreezeAmountException;
import base.exception.OutOfDateException;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.model.PlatformAccount;
import com.duanrong.business.account.model.PlatformBill;

/**
 * 
 * @author xiao
 * @datetime 2016年10月27日 上午9:15:20
 */
public interface PlatformAccountService {

	/**
	 * 查询平台账户
	 * 
	 * @return
	 */
	PlatformAccount getPlatformAccount();

	/**
	 * 查询平台账户(加锁，慎用)
	 * 
	 * @return
	 */
	PlatformAccount queryPlatformAccountwithLock();

	
	/**
	 * 入账
	 * 
	 * @param money
	 *            入账金额
	 * @param businessType
	 *            业务类型
	 * @param typeInfo
	 *            描述
	 * @param requestNo
	 *            流水号
	 */
	void transferIn(double money, BusinessEnum businessType, String typeInfo,
			String requestNo);

	/**
	 * 出账
	 * 
	 * @param money
	 *            出账金额
	 * @param businessType
	 *            业务类型
	 * @param typeInfo
	 *            描述
	 * @param requestNo
	 *            流水号
	 * @throws InsufficientBalance 
	 * 			  可用余额不足
	 */
	void transferOut(double money, BusinessEnum businessType, String typeInfo,
			String requestNo) throws InsufficientBalance;

	/**
	 * 冻结
	 * 
	 * @param money
	 *            冻结金额
	 * @param businessType
	 *            业务类型
	 * @param typeInfo
	 *            描述
	 * @param requestNo
	 *            流水号
	 * @throws InsufficientFreezeAmountException 
	 */
	void freeze(double money, BusinessEnum businessType, String typeInfo,
			String requestNo) throws InsufficientFreezeAmountException;

	/**
	 * 解冻
	 * 
	 * @param money
	 *            解冻金额
	 * @param businessType
	 *            业务类型
	 * @param typeInfo
	 *            描述
	 * @param requestNo
	 *            流水号
	 * @throws InsufficientFreeze 
	 */
	void unfreeze(double money, BusinessEnum businessType, String typeInfo,
			String requestNo) throws InsufficientFreeze;

	
	/**
	 * 平台账户流水分页，默认查询当天流水，按时间短查询，时间前后差不能超过一个月
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param platformBill
	 * @return
	 * @throws OutOfDateException 
	 */
	PageInfo<PlatformBill> pageLite(int pageNo, int pageSize,
			PlatformBill platformBill) throws OutOfDateException;

	/**
	 * 从冻结中转出
	 * 
	 * @param money
	 *            转出金额
	 * @param businessType
	 *            业务类型
	 * @param typeInfo
	 *            描述
	 * @param requestNo
	 *            流水号
	 * @throws InsufficientFreeze 
	 */
	void tofreeze(double money, BusinessEnum businessType, String typeInfo,
			String requestNo) throws InsufficientFreeze;

}
