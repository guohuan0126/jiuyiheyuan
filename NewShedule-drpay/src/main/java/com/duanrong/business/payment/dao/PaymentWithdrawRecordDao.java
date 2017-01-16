package com.duanrong.business.payment.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import base.dao.BaseDao;

import com.duanrong.business.payment.model.PaymentWithdrawRecord;

public interface PaymentWithdrawRecordDao  extends BaseDao<PaymentWithdrawRecord>{

	void updateBatch(Map<String, Object> params);

	List<PaymentWithdrawRecord> get(String markId, String operator,
			String paymentId);

	List<PaymentWithdrawRecord> getUnCallbackOperations(
			PaymentWithdrawRecord paymentWithdrawRecord);
	
	List<PaymentWithdrawRecord> getYeeapyOperations(
			Date date);

	List<PaymentWithdrawRecord> getDefraypayOperations(Date date);

}
