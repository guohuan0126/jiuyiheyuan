package com.duanrong.business.withdraw.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.withdraw.dao.PaymentWithdrawRecordDao;
import com.duanrong.business.withdraw.model.PaymentWithdrawRecord;

@Repository
public class PaymentWithdrawRecordDaoImpl extends BaseDaoImpl<PaymentWithdrawRecord> implements PaymentWithdrawRecordDao {

	public PaymentWithdrawRecordDaoImpl(){
		this.setMapperNameSpace("com.duanrong.business.withdraw.mapper.PaymentWithdrawRecordMapper");
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
	public double getWithdrawMoneyPerDay(PaymentWithdrawRecord record) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getWithdrawMoneyPerDay", record);
	}

	@Override
	public int readWithdrawCuntPerDay(PaymentWithdrawRecord record) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getWithdrawCuntPerDay", record);
	}
	@Override
	public List<PaymentWithdrawRecord> getWithdrawRecords(
			PaymentWithdrawRecord paymentWithdrawRecord) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getWithdrawRecords", paymentWithdrawRecord);
	}


	@Override
	public List<PaymentWithdrawRecord> getRepeatRecords() {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getRepeatRecords");
	}


	@Override
	public PaymentWithdrawRecord getPaymentWithdrawRecord(String transNo) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getPaymentWithdrawRecord", transNo);
	}

}
