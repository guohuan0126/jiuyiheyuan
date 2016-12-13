package com.duanrong.business.payment.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;

import com.duanrong.business.payment.model.PaymentRechargeRecord;

public interface PaymentRechargeRecordDao  extends BaseDao<PaymentRechargeRecord>{

	void updateBatch(Map<String, Object> params);

	List<PaymentRechargeRecord> get(String markId, String operator,
			String paymentId);

	List<PaymentRechargeRecord> getUnCallbackOperations(
			PaymentRechargeRecord paymentRechargeRecord);

}
