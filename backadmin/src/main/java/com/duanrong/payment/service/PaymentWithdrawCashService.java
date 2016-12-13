package com.duanrong.payment.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.duanrong.business.paymentInstitution.model.PaymentRechargeRecord;


/**
 * @Description: 提现
 * @Author: 林志明
 * @CreateDate: Sep 11, 2014
 */
public interface PaymentWithdrawCashService extends OperationService{

	public String confirmWithdrawCash(String orderId,String drepayType);

	public Map<String,String> tradeQuery(String parameter);

	double getJDbalance();
	
	public String verifyWithdrawCash();

	String transferCancle(String requestNo) throws Exception;

	String transferConfirm(String requestNo) throws Exception;

	public Map<String, String> tradeFuiouQuery(String orderId);

	public String  BaoFooS2SCallback(HttpServletRequest request,
			HttpServletResponse response, String operationType);

	public double getBaoFoobalance();
	//宝付单笔查询
	public String queryPaymentDefrayPay(String orderId);


}