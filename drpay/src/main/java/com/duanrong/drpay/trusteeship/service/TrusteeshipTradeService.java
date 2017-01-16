package com.duanrong.drpay.trusteeship.service;

import base.exception.AccountException;
import base.exception.LoanException;
import base.exception.TradeException;

import com.duanrong.drpay.trusteeship.helper.model.GeneratorAsynDetailJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;

/**
 * 交易业务 单笔业务查询
 * 
 * @author xiao
 * @datetime 2016年12月7日 上午9:15:16
 */
public interface TrusteeshipTradeService {

	/**
	 * 项目放款
	 * 
	 * @param loanId
	 *            项目编号
	 * @throws TradeException
	 */
	void giveMoneyToBorrower(String loanId) throws TradeException;

	/**
	 * 批量放款异步通知
	 * 
	 * @param loanId
	 *            项目编号
	 * @throws TradeException
	 */
	void giveMoneyToBorrowerCallback(GeneratorAsynDetailJSON generatorAsynDetailJSON);
	
	/**
	 * 放款异步通知补偿
	 * @param investSubloanId
	 * @throws AccountException 
	 * @throws TradeException 
	 */
	void giveMoneyToBorrowerCompensate(String investSubloanId) throws TradeException, AccountException;
	
	/**
	 * 流标
	 * 
	 * @param loanId
	 *            项目id
	 * @param investId
	 *            投资id
	 * @param type
	 *            流标类型 loan/invest
	 * @throws LoanException
	 * @throws AccountException
	 * @throws TradeException
	 * @throws Exception
	 */
	void bidders(String loanId, String investId, String type)
			throws LoanException, TradeException, AccountException, Exception;

	/**
	 * 预处理取消
	 * @param preTransaction
	 * @param money
	 * @return
	 */
	GeneratorJSON transactionCancle(String preTransaction, double money);
}
