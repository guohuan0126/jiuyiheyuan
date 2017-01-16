package com.duanrong.drpay.trusteeship.service;

import java.util.List;
import java.util.Map;

import base.exception.BankCardException;
import base.exception.ParameterException;
import base.exception.PaymentAccountException;
import base.exception.TradeException;
import base.exception.UserAccountException;

import com.duanrong.drpay.business.account.model.BankCard;
import com.duanrong.drpay.jsonservice.handler.TerminalEnum;
import com.duanrong.drpay.trusteeship.helper.model.Generator;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorRechargeJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorWithdrawJSON;

/**
 * 充值提现业务
 * @author xiao
 * @datetime 2016年12月7日 上午9:16:08
 */
public interface TrusteeshipPaymentService {

	/**
	 * 充值
	 * @param parameter
	 * @return
	 * @throws ParameterException
	 * @throws BankCardException 
	 */
	Generator recharge(String userId, Double money, String rechargeWay, String source, TerminalEnum terminalType) throws ParameterException, BankCardException;

	/**
	 * 回退充值
	 * @param parameter
	 * @return
	 * @throws TradeException 
	 * @throws UserAccountException 
	 */
	GeneratorJSON rechargeWithRollback(String requestNo) throws TradeException, UserAccountException;
	
	/**
	 * 充值回调
	 * @param respData
	 */
	void rechargeCallback(GeneratorRechargeJSON respData);
	
	/**
	 * 提现
	 * @param userId
	 * @param money
	 * @return
	 * @throws UserAccountException
	 */
	Generator withdraw(String userId, Double money, TerminalEnum terminalType)throws UserAccountException;
	
	/**
	 * 提现回调
	 * @param respData
	 * @throws UserAccountException
	 */
	void withdrawCallback(GeneratorWithdrawJSON respData);

	/**
	 * 根据userId返回匹配最佳充值通道信息
	 * @param userId
	 * @param source
	 * @return
	 */
	Map<String, Object> findChannelByMoney(String userId, String source);

	/**
	 * 根据userId差尊用户有效的银行卡
	 * @param userId
	 * @return
	 */
	List<BankCard> getValidBankCardByUserId(String userId);

	/**
	 * 获取到账时间
	 * @param userId
	 * @param money
	 * @return
	 * @throws PaymentAccountException
	 */
	Map<String, String> getArrivalDate(String userId, Double money)throws PaymentAccountException;
}
