package com.duanrong.business.yeepay.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import base.exception.InsufficientFreezeAmountException;
import base.exception.NoOpenAccountException;
import base.pagehelper.PageInfo;

import com.duanrong.business.yeepay.model.TransactionAuthorization;
import com.duanrong.business.yeepay.model.TransactionAuthorizationDetail;




/**
 * 通用转账
 * 
 * @author Lin Zhiming
 * @version May 27, 2015 11:00:34 AM
 */
public interface GeneralTransactionService {
	public PageInfo<TransactionAuthorization> readPageInfo(int pageNo, int pageSize,Map map);
	public List<TransactionAuthorizationDetail> readDetails(String id);
	public void authConfirm(String tranid, String flag) throws IOException, NoOpenAccountException, InsufficientFreezeAmountException;
	
	/**
	 * 添加新的通用转账数据
	 * @param model
	 */
	public void insertGeneralTransaction(TransactionAuthorization model);
	
	public void updateTA(TransactionAuthorization transactionAuthorization);

	public void updateTAD(
			TransactionAuthorizationDetail transactionAuthorizationDetail);
	
	public TransactionAuthorization readTA(String id);
}
