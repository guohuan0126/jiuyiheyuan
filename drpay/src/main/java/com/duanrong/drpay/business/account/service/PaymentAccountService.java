package com.duanrong.drpay.business.account.service;
import java.util.List;

import base.exception.ParameterException;
import base.exception.PaymentAccountException;
import base.pagehelper.PageInfo;

import com.duanrong.drpay.business.account.PaymentEnum;
import com.duanrong.drpay.business.account.model.PaymentAccount;
import com.duanrong.drpay.business.account.model.PaymentBill;
import com.duanrong.drpay.config.BusinessEnum;

/**
 * 第三方账户操作
 * 
 * @author xiao
 * @datetime 2016年10月26日 下午8:09:37
 */
public interface PaymentAccountService {

	/**
	 * 第三方账户信息
	 * 
	 * @return
	 */
	List<PaymentAccount> getPaymentAccount();
	
	
	/**
	 * 第三方账户信息
	 * 
	 * @return
	 */
	PaymentAccount getPaymentAccount(PaymentEnum payment);
	
	/**
	 * 第三方账户信息(加锁 慎用)
	 * 
	 * @return
	 */
	PaymentAccount queryPaymentAccountWithLock(PaymentEnum payment);
	
	
	/**
	 * 第三方入账
	 * 
	 * @param pamenyId
	 *            支付机构
	 * @param money
	 *            入账金额
	 * @param businessType
	 *            业务类型
	 * @param typeInfo
	 *            描述
	 * @param requestNo
	 *            流水号
	 */
	void transferIn(PaymentEnum pamenyId, double money,
			BusinessEnum businessType, String typeInfo, String requestNo);

	/**
	 * 第三方出账
	 * 
	 * @param pamenyId
	 *            支付机构
	 * @param money
	 *            出账金额
	 * @param businessType
	 *            业务类型
	 * @param typeInfo
	 *            描述
	 * @param requestNo
	 *            流水号
	 * @throws PaymentAccountException 
	 */
	void transferOut(PaymentEnum pamenyId, double money, BusinessEnum businessType, String typeInfo,
			String requestNo) throws PaymentAccountException;

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
	void freeze(PaymentEnum paymentId, double money, BusinessEnum businessType, String typeInfo,
			String requestNo) throws PaymentAccountException;

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
	void unfreeze(PaymentEnum paymentId, double money, BusinessEnum businessType, String typeInfo,
			String requestNo) throws PaymentAccountException;

	
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
	void tofreeze(PaymentEnum paymentId, double money, BusinessEnum businessType, String typeInfo,
			String requestNo) throws PaymentAccountException;

	/**
	 * 第三方账户流水
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param paymentBill
	 * @return
	 * @throws QueryTimeTooLongException 
	 * @throws OutOfDateException
	 */
	PageInfo<PaymentBill> pageLite(int pageNo, int pageSize,
			PaymentBill paymentBill) throws ParameterException;
}
