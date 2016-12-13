package com.duanrong.business.payment.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.payment.dao.PaymentWithdrawRecordDao;
import com.duanrong.business.payment.model.PaymentWithdrawRecord;

@Repository
public class PaymentWithdrawRecordDaoImpl extends BaseDaoImpl<PaymentWithdrawRecord> implements PaymentWithdrawRecordDao {

	public PaymentWithdrawRecordDaoImpl(){
		this.setMapperNameSpace("com.duanrong.business.payment.mapper.PaymentWithdrawRecordMapper");
	}
	
	@Override
	public void updateBatch(Map<String, Object> params) {
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateBatch",params);
	}

	@Override
	public List<PaymentWithdrawRecord> get(String markId, String operator,
			String paymentId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("markId", markId);
		params.put("operator", operator);
		params.put("paymentId", paymentId);
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getByCondition", params);
	}

	@Override
	public List<PaymentWithdrawRecord> getUnCallbackOperations(
			PaymentWithdrawRecord paymentWithdrawRecord) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getUnCallbackOperations",
				paymentWithdrawRecord);
	}
	
	@Override
	public List<PaymentWithdrawRecord> getYeeapyOperations(
			Date date) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getYeeapyOperations",
				date);
	}

	@Override
	public List<PaymentWithdrawRecord> getDefraypayOperations(Date date) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getDefraypayOperations",
				date);
	}

	
}
