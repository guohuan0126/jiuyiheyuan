package com.duanrong.drpay.trusteeship.service;

import java.util.Map;

import base.exception.BankCardException;
import base.exception.DataAlreadExistException;
import base.exception.ParameterException;
import base.exception.TradeException;
import base.exception.UserAccountException;
import base.exception.UserInfoException;

import com.duanrong.drpay.business.account.model.CgtUserAccount;
import com.duanrong.drpay.business.account.model.UserAccount;
import com.duanrong.drpay.business.autoinvest.model.AutoInvest;
import com.duanrong.drpay.jsonservice.handler.TerminalEnum;
import com.duanrong.drpay.jsonservice.param.AutoInvestParamter;
import com.duanrong.drpay.jsonservice.param.UserAccountParameter;
import com.duanrong.drpay.trusteeship.helper.model.Generator;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorUserAccountJSON;

/**
 * 账户相关业务 开户 绑卡 自动授权.....
 * 
 * @author xiao
 * @datetime 2016年12月7日 上午9:13:25
 */
public interface TrusteeshipAccountService {

	/**
	 * 个人用户开户预处理，参数封装
	 * 
	 * @param params
	 * @return
	 * @throws UserInfoException
	 * @throws UserAccountException
	 * @throws ParameterException
	 * @throws BankCardException
	 */
	Generator createAccount(UserAccountParameter params, TerminalEnum terminalType)
			throws UserInfoException, UserAccountException, ParameterException,
			BankCardException;

	/**
	 * 开户回调服务器通知处理
	 * 
	 * @param respData
	 * @throws BankCardException
	 * @throws UserAccountException
	 */
	void createAccountCallback(GeneratorUserAccountJSON respData);

	/**
	 * 查询存管通用户信息
	 * 
	 * @param userId
	 * @return
	 * @throws UserInfoException
	 * @throws UserAccountException
	 */
	CgtUserAccount queryCgtUserInfo(String userId) throws UserInfoException,
			UserAccountException;

	/**
	 * 查询本地用户信息
	 * 
	 * @param userId
	 * @return
	 * @throws UserInfoException
	 * @throws UserAccountException
	 */
	UserAccount queryLocalUserInfo(String userId) throws UserInfoException,
			UserAccountException;

	/**
	 * 存管通校验交易密码
	 * 
	 * @param templateType
	 * @param userId
	 * @return
	 * @throws UserInfoException
	 * @throws UserAccountException
	 */
	Generator checkPassword(String userId, String templateType, TerminalEnum terminalType)
			throws UserInfoException, UserAccountException;

	/**
	 * 校验密码回调服务器通知处理
	 * 
	 * @param respData
	 */
	void checkPasswordCallback(GeneratorJSON respData);

	/**
	 * 个人绑卡预处理，参数封装
	 * 
	 * @param userId
	 * @param mobile
	 * @param bankcardNo
	 * @return
	 * @throws UserInfoException
	 * @throws UserAccountException
	 * @throws ParameterException
	 * @throws BankCardException
	 */
	Generator bindCard(String userId, String bankcardNo, String bankCode, String mobile, TerminalEnum terminalType)
			throws UserInfoException, UserAccountException, ParameterException,
			BankCardException;

	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @return
	 * @throws UserInfoException
	 * @throws UserAccountException
	 */
	Generator resetPassword(String userId, TerminalEnum terminalType) throws UserInfoException,
			UserAccountException;

	/**
	 * 修改密码回调服务器通知处理
	 * 
	 * @param respData
	 */
	void resetPasswordCallback(GeneratorJSON respData);

	/**
	 * 解绑卡（直连）
	 * 
	 * @param userId
	 * @return
	 * @throws UserInfoException
	 * @throws UserAccountException
	 */
	String unBindCard(String userId) throws UserInfoException,
			UserAccountException;

	/**
	 * 解绑卡回调服务器通知处理
	 * 
	 * @param respData
	 * @throws UserAccountException
	 * @throws UserInfoException
	 */
	/*
	 * void unBindCardCallback(GeneratorJSON respData) throws UserInfoException,
	 * UserAccountException;
	 */

	/**
	 * 绑卡回调服务器通知处理
	 * 
	 * @param respData
	 */
	void bindCardCallback(GeneratorUserAccountJSON respData);

	/**
	 * 修改银行卡预留手机号（不是登陆账户）
	 * 
	 * @param userId
	 * @param mobile
	 * @return
	 * @throws UserInfoException
	 * @throws UserAccountException
	 */
	Generator modifyMobile(String userId, TerminalEnum terminalType) throws UserInfoException,
			UserAccountException;

	/**
	 * 修改手机号回调服务器通知处理
	 * 
	 * @param respData
	 */
	void modifyMobileCallback(GeneratorUserAccountJSON respData);

	/**
	 * 冻结用户资金
	 * 
	 * @param userId
	 * @param money
	 * @return
	 */
	Generator freezeMoney(String loginId, String expiredTime, String description, String userId, double money);

	/**
	 * 解冻用户资金
	 * 
	 * @param userId
	 * @param money
	 * @return
	 * @throws TradeException 
	 */
	Generator unfreezeMoney(String loginId,String requestNo) throws TradeException;

	/**
	 * 设置自动投标
	 */
	void settingAutoInvest(AutoInvestParamter paramter)
			throws ParameterException;

	/**
	 * 取消自动投标
	 */
	void cancelAutoInvest(String userId);

	AutoInvest getAutoInvest(String userId);

	Generator activateUserAccount(String userId, String bankCode, String bankCardNo, TerminalEnum terminalType) throws UserAccountException, BankCardException;
	
	void activateAccountCallback(GeneratorUserAccountJSON respData);

	/**
	 * 修改本地手机号
	 * 
	 * @param userId
	 * @param newMobile
	 * @param oldMobile
	 * @param password
	 * @param idCardNo
	 * @param authCode
	 * @param type
	 * @throws DataAlreadExistException 
	 * @throws UserInfoException 
	 */
	void resetMobile(String userId, String newMobile, String oldMobile,
			String password, String idCardNo, String authCode) throws DataAlreadExistException, UserInfoException;

	
	/**
	 * 解绑卡申请（前端，上传证件照片）
	 * 
	 * @param userId
	 * @param request
	 * @return
	 * @throws UserInfoException 
	 */
	void unBindCardApply(String userId, String imgData) throws UserInfoException;

	/**
	 * 用户信息查询(cgt，调度)
	 * @param userId
	 * @param handle
	 * @return
	 * @throws UserInfoException 
	 */
	Generator queryUserInformation(String userId, String type, int handle) throws UserInfoException;

	/**
	 * 激活页面用户信息
	 * @param userId
	 * @throws UserInfoException 
	 */
	Map<String, Object> userInfoForActive(String userId) throws UserInfoException;

}
