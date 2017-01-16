package com.duanrong.drpay.trusteeship.service;

import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.trusteeship.helper.model.Generator;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorPreTransactionJSON;

import base.exception.AccountException;
import base.exception.TradeException;

/**
 * 单笔业务查询
 * @author xiao
 * @date 2017年1月5日 下午7:37:52
 */
public interface TrusteeshipTransactionQueryService {

	
	/**
	 * 单笔业务查询
	 * 
	 * @param params
	 * @throws AccountException 
	 * @throws TradeException
	 */
	Generator queryTransaction(String requestNo, BusinessEnum type) throws TradeException, AccountException;

	Generator queryTransaction(String requestNo, BusinessEnum type, int handle) throws TradeException, AccountException;

	/**
	 * 查询平台账户信息
	 */
	GeneratorJSON queryPlatformInfo();
	
	/**
	 * 查询批量投标请求
	 * @param requestNo
	 * @return
	 */
	GeneratorPreTransactionJSON queryProjectOrder(String requestNo);

}
