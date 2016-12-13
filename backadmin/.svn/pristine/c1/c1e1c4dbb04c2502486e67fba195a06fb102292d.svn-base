package com.duanrong.business.withdraw.dao;

import java.util.List;

import base.dao.BaseDao;

import com.duanrong.business.withdraw.model.PaymentWithdrawRecord;

public interface PaymentWithdrawRecordDao  extends BaseDao<PaymentWithdrawRecord>{

	List<PaymentWithdrawRecord> get(String markId, String operator,
			String paymentId);

	double getWithdrawMoneyPerDay(PaymentWithdrawRecord record);

	int readWithdrawCuntPerDay(PaymentWithdrawRecord record);
	
	List<PaymentWithdrawRecord> getWithdrawRecords(
			PaymentWithdrawRecord paymentWithdrawRecord);

	List<PaymentWithdrawRecord> getRepeatRecords();

	PaymentWithdrawRecord getPaymentWithdrawRecord(String transNo);
}
