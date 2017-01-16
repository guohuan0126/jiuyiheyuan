package com.duanrong.business.payment.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.duanrong.business.payment.model.PaymentWithdrawRecord;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;


public interface PaymentWithdrawRecordService {

	void update(PaymentWithdrawRecord record);
	
	PaymentWithdrawRecord query(String markId, String operator,String paymentId);

	List<PaymentWithdrawRecord> getUnCallbackOperations(int i);

	@Transactional
	void transferLocal(TrusteeshipOperation to, PaymentWithdrawRecord record);

	List<PaymentWithdrawRecord> getYeeapyOperations(int minute);
	
	@Transactional
	void transferFrozeLocal(TrusteeshipOperation to, PaymentWithdrawRecord record);

	List<PaymentWithdrawRecord> getDefraypayOperations(int i);
	
	public Map<String,String> tradeQuery(String parameter);
}