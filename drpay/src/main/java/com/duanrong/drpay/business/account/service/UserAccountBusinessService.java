package com.duanrong.drpay.business.account.service;

import base.exception.BankCardException;
import base.exception.ErrorCodeException;


/**
 * 账户相关业务
 * 
 * @author xiao
 * 
 */
public interface UserAccountBusinessService {

	/**
	 * 实名认证（开户本地处理）
	 * 
	 * @param userId
	 *            用户id
	 * @param realName
	 *            真实姓名
	 * @param idCard
	 *            身份证号
	 * @param password
	 *            交易密码
	 * @param bankCard
	 *            银行卡号
	 * @param mobile
	 *            预留手机号
	 */
	void openAccount(String userId, String realName, String idCard,
			String password, String bankCard, String mobile);

	/**
	 * 绑卡（本地处理）
	 * 
	 * @param userId
	 *            用户id
	 * @param cardNo
	 *            卡号
	 * @param mobile
	 *            预留手机号
	 * @param cardName
	 *            银行卡名称
	 * @throws BankCardException
	 *            银行卡已绑定
	 */
	void bindingCard(String userId, String cardNo, String cardName,
			String mobile, String paymentId,String status, String paymentNo)
			throws BankCardException;

	/**
	 * 解绑（本地处理）
	 * 
	 * @param userId
	 */
	void unBindingCard(String userId);

	/**
	 * 设置交易密码
	 * 
	 * @param userId
	 * @param password
	 * @throws AuthInfoOutOfDateException
	 * @throws NoMatchingObjectsException
	 */
	void rsetPassword(String userId, String password, String mobileNumber,
			String authCode, String type) throws ErrorCodeException;

	/**
	 * 验证交易密码
	 * 
	 * @param userId
	 * @param password
	 * @return
	 */
	boolean isPassword(String userId, String password);

	/************************* 暂时不做 *****************************/

	/**
	 * 修改手机号
	 * 
	 * @param type
	 * @param paramType
	 * @param idCard
	 * @param authCode
	 * @param password
	 * @throws AuthInfoOutOfDateException
	 * @throws NoMatchingObjectsException
	 */
	void rsetMobile(String userId, String mobile, String oldMobile,
			String password, String authCode, String idCard, String paramType,
			String type) throws ErrorCodeException;

	/************************* 暂时不做 *****************************/
	/**
	 * 冻结账户
	 * 
	 * @param userId
	 */
	void freezeAccount(String userId);

	/**
	 * 解冻账户
	 * 
	 * @param userId
	 */
	void unfreezeAccount(String userId);

	/**
	 * 自动投标投标授权
	 * 
	 * @param userId
	 * @throws ErrorCodeException 
	 */
	void authInvest(String userId, String password,
			String loanType, Integer maxDeadline, Double maxMoney,
			Double maxRate, Integer minDeadline, Double minMoney, Double minRate) throws ErrorCodeException;
	/**
	 * 关闭自动投标
	 * 
	 * @param userId
	 */
	void cancelAutoInvest(String userId);
}