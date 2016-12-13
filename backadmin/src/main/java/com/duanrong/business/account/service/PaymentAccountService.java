package com.duanrong.business.account.service;

import java.util.List;

import base.exception.InsufficientBalance;
import base.exception.InsufficientFreeze;
import base.exception.InsufficientFreezeAmountException;
import base.exception.OutOfDateException;
import base.exception.QueryTimeTooLongException;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.PaymentEnum;
import com.duanrong.business.account.model.PaymentAccount;
import com.duanrong.business.account.model.PaymentBill;
import com.duanrong.business.account.model.PaymentChannel;

/**
 * 第三方账户操作
 * 
 * @author xiao
 * @datetime 2016年10月26日 下午8:09:37
 */
public interface PaymentAccountService {

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
	void transferIn(PaymentEnum paymentId, double money,
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
	 * @throws InsufficientBalance
	 */
	void transferOut(PaymentEnum paymentId, double money, BusinessEnum businessType, String typeInfo,
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
	void freeze(PaymentEnum paymentId, double money, BusinessEnum businessType, String typeInfo,
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
	void unfreeze(PaymentEnum paymentId, double money, BusinessEnum businessType, String typeInfo,
			String requestNo) throws InsufficientFreeze;

	
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
			String requestNo) throws InsufficientFreeze;
	
	
	/**
	 * 第三方账户信息
	 * 
	 * @return
	 */
	List<PaymentAccount> readPaymentAccount();
	
	PaymentChannel readChannelByCode(String code);
	PaymentAccount readPaymentAccountByPaymentId(String pamenyId);
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
	PageInfo<PaymentBill> readPageLite(int pageNo, int pageSize,
			PaymentBill paymentBill) throws QueryTimeTooLongException;
}