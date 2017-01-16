package com.duanrong.drpay.trusteeship.service;

import org.quartz.SchedulerException;

import base.exception.AccountException;
import base.exception.TradeException;
import base.exception.UserAccountException;

import com.duanrong.drpay.jsonservice.handler.TerminalEnum;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorAsynDetailJSON;

/**
 * 还款业务
 * @author xiao
 * @datetime 2016年12月7日 上午9:15:16
 */
public interface TrusteeshipRepayService {

	/**
	 * 创建还款记录
	 * @param params
	 * @throws TradeException
	 * @throws UserAccountException
	 */
	public String createSendedRepay(String id,int beforeRepay, TerminalEnum terminalType) 
			throws UserAccountException, TradeException;
	
	public void repayCallback(GeneratorAsynDetailJSON generatorAsynDetail);

	public void handleRepay(String repayId) throws TradeException, AccountException, SchedulerException;
	
}
