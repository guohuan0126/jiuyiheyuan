package com.duanrong.drpay.trusteeship.service;

import base.exception.DataAlreadExistException;
import base.exception.PlatformAccountException;
import base.exception.TradeException;
import base.exception.UserAccountException;
import base.exception.UserInfoException;

import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.jsonservice.handler.TerminalEnum;
import com.duanrong.drpay.trusteeship.helper.model.Generator;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;
import com.duanrong.drpay.trusteeship.helper.model.PlatformType;

/**
 * 资金划转接口 包括营销奖励 包括验密扣费（用户给平台转账）
 * 
 * @author zjypp
 * 
 */
public interface TrusteeshipFundTransferService {

	/**
	 * 平台奖励,直接发送奖励（危险接口, 需谨慎使用）
	 * 
	 * @throws DataAlreadExistException
	 * @throws UserAccountException
	 * @throws PlatformAccountException
	 * @throws Exception 
	 */
	String sendRewardDirect(String userId, String requestNo,
			double rewardMoney, BusinessEnum businessType, String loanId,
			String title, String remarks) throws DataAlreadExistException,
			UserAccountException, PlatformAccountException, Exception;

	/**
	 * 平台奖励确认（危险接口, 需谨慎使用）
	 * 
	 * @throws DataAlreadExistException
	 * @throws UserAccountException
	 * @throws PlatformAccountException
	 * @throws TradeException
	 */
	@Deprecated
	String sendRewardConfirm(String userId, double money, String requestNo,
			String info, BusinessEnum businessType)
			throws DataAlreadExistException, UserAccountException,
			PlatformAccountException, TradeException;

	
	/**
	 * 通用转账
	 * 
	 * @param userId
	 * @param targetUserId
	 * @param money
	 * @param descripion
	 * @param terminalType
	 * @return
	 * @throws UserInfoException
	 * @throws UserAccountException
	 */
	Generator createDeduct(String requestNo, String userId,
			double money, PlatformType platformType,
			String descripion, TerminalEnum terminalType);
	
	/**
	 * 通用转账
	 * 
	 * @param userId
	 * @param targetUserId
	 * @param money
	 * @param descripion
	 * @param terminalType
	 * @return
	 * @throws UserInfoException
	 * @throws UserAccountException
	 */
	Generator createTransaction(String userId,
			double money, String descripion, TerminalEnum terminalType)
			throws UserInfoException, UserAccountException;
	
	/**
	 * 通用转账回调
	 */
	void transferCallback(GeneratorJSON generatorJSON);
}