package com.duanrong.business.account.service;

import com.duanrong.business.account.AuthorizationEnum;

/**
 * 账户相关业务
 * @author xiao
 *
 */
public interface UserAccountBusinessService {

	/**
	 * 实名认证（开户本地处理）
	 * 
	 * @param userId 用户id
	 * @param realName 真实姓名
	 * @param idCard 身份证号
	 * @param password 交易密码
	 * @param bankCard 银行卡号
	 * @param mobile 预留手机号
	 */
	void openAccount(String userId, String realName, String idCard,
			String password, String bankCard, String mobile);

	/**
	 * 绑卡（本地处理）
	 * @param userId 用户id
	 * @param cardNo 卡号
	 * @param mobile 预留手机号
	 */
	void bindingCard(String userId, String cardNo, String mobile);
	
	/**
	 * 解绑（本地处理）
	 * @param userId
	 */
	void unBindingCard(String userId);
	
	/**
	 * 设置交易密码
	 * @param userId
	 * @param password
	 */
	void rsetPassword(String userId, String password);
	
	/**
	 * 查询交易密码
	 * @param userId
	 * @param password
	 */
	void queryPassword(String userId);
	
	/*************************   暂时不做    *****************************/
	/**
	 * 冻结账户
	 * @param userId
	 */
	void freeze(String userId);
	
	/**
	 * 解冻账户
	 * @param userId
	 */
	void unfreeze(String userId);
	
	/**
	 * 自动投标投标授权
	 * @param userId
	 */
	void authInvest(String userId, AuthorizationEnum authorizationtype);
}