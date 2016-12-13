package com.duanrong.business.withdraw.service;


import java.util.List;

import base.pagehelper.PageInfo;

import com.duanrong.business.withdraw.model.PaymentWithdrawRecord;
import com.duanrong.business.withdraw.model.WithdrawCash;


public interface PaymentWithdrawRecordService {

	void update(PaymentWithdrawRecord record);
	
	PaymentWithdrawRecord read(String markId, String operator,String paymentId);
	
	PaymentWithdrawRecord read(String id);
	
	String transferCancle(String requestNo) throws Exception;

	String transferConfirm(String requestNo) throws Exception;
	
	PageInfo<PaymentWithdrawRecord> readPageLite(int pageNo, int pageSize, PaymentWithdrawRecord paymentWithdrawRecord);
	
	double readWithdrawMoneyPerDay(PaymentWithdrawRecord record);
	
	int readWithdrawCountPerDay(PaymentWithdrawRecord record);
	
	List<PaymentWithdrawRecord> readWithdrawRecords(PaymentWithdrawRecord paymentWithdrawRecord);

	List<PaymentWithdrawRecord> readRepeatRecords();

	PaymentWithdrawRecord getPaymentWithdrawRecord(String transNo);
}