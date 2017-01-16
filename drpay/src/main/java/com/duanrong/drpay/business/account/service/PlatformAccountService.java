package com.duanrong.drpay.business.account.service;

import base.exception.ParameterException;
import base.exception.PlatformAccountException;
import base.pagehelper.PageInfo;

import com.duanrong.drpay.business.account.PlatformAccountEnum;
import com.duanrong.drpay.business.account.model.PlatformAccount;
import com.duanrong.drpay.business.account.model.PlatformBill;
import com.duanrong.drpay.config.BusinessEnum;

/**
 * 
 * @author xiao
 * @datetime 2016年10月27日 上午9:15:20
 */
public interface PlatformAccountService {

	/**
	 * 查询平台账户（加锁 慎用）
	 * 
	 * @return
	 */
	PlatformAccount getPlatformAccountWithLock(PlatformAccountEnum platformAccountType);
	
	/**
	 * 查询平台账户
	 * 
	 * @return
	 */
	PlatformAccount getPlatformAccount(PlatformAccountEnum platformAccountType);
	
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
	void transferIn(PlatformAccountEnum platformAccountType, double money, BusinessEnum businessType, String typeInfo,
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
	 * @throws PlatformAccountException 
	 * 			  可用余额不足
	 */
	void transferOut(PlatformAccountEnum platformAccountType, double money, BusinessEnum businessType, String typeInfo,
			String requestNo) throws PlatformAccountException;

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
	 * @throws PlatformAccountException 
	 */
	void freeze(PlatformAccountEnum platformAccountType, double money, BusinessEnum businessType, String typeInfo,
			String requestNo) throws PlatformAccountException;

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
	 * @throws PlatformAccountException 
	 */
	void unfreeze(PlatformAccountEnum platformAccountType, double money, BusinessEnum businessType, String typeInfo,
			String requestNo) throws PlatformAccountException;


	/**
	 * 平台账户流水分页，默认查询当天流水，按时间短查询，时间前后差不能超过一个月
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param platformBill
	 * @return
	 * @throws PlatformAccountException 
	 */
	PageInfo<PlatformBill> pageLite(int pageNo, int pageSize,
			PlatformBill platformBill) throws ParameterException;

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
	 * @throws PlatformAccountException 
	 */
	void tofreeze(PlatformAccountEnum platformAccountType, double money, BusinessEnum businessType, String typeInfo,
			String requestNo) throws PlatformAccountException;
	
	

	/**
	 * 计算手续费
	 * @param money
	 * @param businessType
	 * @param paymentId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	double getFee(BusinessEnum businessType)throws Exception;
	
	double getFee(BusinessEnum businessType,double money, String paymentId, String type)throws Exception;

}
