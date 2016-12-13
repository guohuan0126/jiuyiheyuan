package com.duanrong.business.payment.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.payment.dao.PaymentRechargeRecordDao;
import com.duanrong.business.payment.model.PaymentRechargeRecord;

@Repository
public class PaymentRechargeRecordDaoImpl extends BaseDaoImpl<PaymentRechargeRecord> implements PaymentRechargeRecordDao {

	public PaymentRechargeRecordDaoImpl(){
		this.setMapperNameSpace("com.duanrong.business.payment.mapper.PaymentRechargeRecordMapper");
	}
	
	@Override
	public void updateBatch(Map<String, Object> params) {
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateBatch",params);
	}

	@Override
	public List<PaymentRechargeRecord> get(String markId, String operator,
			String paymentId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("markId", markId);
		params.put("operator", operator);
		params.put("paymentId", paymentId);
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getByCondition", params);
	}

	@Override
	public List<PaymentRechargeRecord> getUnCallbackOperations(
			PaymentRechargeRecord paymentRechargeRecord) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getUnCallbackOperations",
				paymentRechargeRecord);
	}

	
}
