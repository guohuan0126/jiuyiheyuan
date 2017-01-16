package com.duanrong.drpay.business.transaction.service;

import base.exception.TradeException;

import com.duanrong.drpay.business.transaction.model.TransactionAuthorization;

/**
 * 通用转账
 * 
 * @author Lin Zhiming
 * @version May 27, 2015 11:00:34 AM
 */
public interface GeneralTransactionService {
	
	/**
	 * 转账预处理
	 * @return
	 */
	TransactionAuthorization prepare(String userId, double money);
	
	/**
	 * 添加新的通用转账数据
	 * @param model
	 */
	void insertGeneralTransaction(TransactionAuthorization model);
	
	/**
	 * 更新通用转账数据
	 * @param transactionAuthorization
	 */
	void updateGeneralTransaction(
			TransactionAuthorization transactionAuthorization);
	
	/**
	 * 验密扣费成功后本地处理
	 * @param tranid
	 * @param flag
	 * @throws TradeException 
	 */
	TransactionAuthorization transferLocal(String tranid) throws TradeException;
	
	/**
	 * 转账确认
	 * @param tranid
	 * @throws TradeException
	 */
	void transferConfirm(String tranid) throws TradeException;

}
