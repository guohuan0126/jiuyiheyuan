package com.duanrong.business.payment.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import util.MyCollectionUtils;

import com.duanrong.business.payment.dao.PaymentRechargeRecordDao;
import com.duanrong.business.payment.model.PaymentRechargeRecord;
import com.duanrong.business.payment.service.PaymentRechargeRecordService;
import com.duanrong.business.trusteeship.model.TrusteeshipConstants;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;

@Service
public class PaymentRechargeRecordImpl implements PaymentRechargeRecordService {

	@Resource
	PaymentRechargeRecordDao paymentRechargeRecordDao;
	
	@Override
	public void insert(PaymentRechargeRecord record) {
		if (StringUtils.isBlank(record.getMarkId())
				|| StringUtils.isBlank(record.getOperator())
				|| StringUtils.isBlank(record.getPaymentId())) {
			return ;
		}

		dealSameRecord(record);
		paymentRechargeRecordDao.insert(record);
	}

	/**
	 * 如果markId和operator是唯一的，就将查询到的记录更新
	 */
	public void dealSameRecord(PaymentRechargeRecord record) {
		List<PaymentRechargeRecord> records = paymentRechargeRecordDao.get(
				record.getMarkId(), record.getOperator(), record.getPaymentId());

		if (MyCollectionUtils.isNotBlank(records)) {
			Map<String, Object> params = new HashMap<>();
			params.put("operator", "recode：" + record.getId());
			params.put("status", "recode：" + record.getId());
			params.put("records", records);
			paymentRechargeRecordDao.updateBatch(params);
		}
	}

	@Override
	public List<PaymentRechargeRecord> query(String markId, String operator,String paymentId) {
		return paymentRechargeRecordDao.get(markId, operator, paymentId);
	}

	@Override
	public void update(PaymentRechargeRecord record) {
		paymentRechargeRecordDao.update(record);
	}

	@Override
	public List<PaymentRechargeRecord> getUnCallbackOperations(int minute) {
		Date date = DateUtils.addMinutes(new Date(), -minute);
		PaymentRechargeRecord paymentRechargeRecord = new PaymentRechargeRecord();
		paymentRechargeRecord.setStatus(TrusteeshipConstants.Status.SENDED);
		paymentRechargeRecord.setRequestTime(date);
		return paymentRechargeRecordDao.getUnCallbackOperations(paymentRechargeRecord);
		
	}
}
