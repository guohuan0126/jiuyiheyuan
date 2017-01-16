package com.duanrong.drpay.trusteeship.service;

import base.exception.AccountException;
import base.exception.TradeException;
import base.exception.UserAccountException;

import com.duanrong.drpay.jsonservice.handler.TerminalEnum;
import com.duanrong.drpay.trusteeship.helper.model.Generator;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;

/**
 * 投资业务 投标 自动投标 债权转让.....
 * 
 * @author xiao
 * @datetime 2016年12月7日 上午9:15:16
 */
public interface TrusteeshipInvestService {

	/**
	 * 创建投标记录
	 * 
	 * @param params
	 * @throws TradeException
	 * @throws UserAccountException
	 */
	Generator createInvest(String userId, String loanId, double money,
			int redpacketId, int isAutoInvest, TerminalEnum terminalType, String investSource)
			throws TradeException, AccountException;

	/**
	 * 创建投标记录
	 * 
	 * @param params
	 * @throws TradeException
	 * @throws UserAccountException
	 */
	Generator investAgain(String id, TerminalEnum terminalType)
			throws TradeException, AccountException;

	/**
	 * Callback
	 * 
	 * @param requestNo
	 *            请求流水号
	 * @throws SchedulerException 
	 * @throws TradeException 
	 * @throws AccountException 
	 * @throws TrusteeshipReturnException
	 */
	void investCallback(GeneratorJSON respData,
			String RequestNo) throws TradeException, AccountException;
	
	
	/**
	 * 投资订单补偿
	 * @param requestNo
	 * @throws TradeException 
	 * @throws AccountException 
	 */
	void investCompensate(String requestNo) throws TradeException, AccountException;
}
