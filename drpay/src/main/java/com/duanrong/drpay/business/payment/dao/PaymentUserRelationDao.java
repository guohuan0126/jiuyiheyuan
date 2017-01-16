package com.duanrong.drpay.business.payment.dao;

import base.dao.BaseDao;

import com.duanrong.drpay.business.payment.model.PaymentUserRelation;

public interface PaymentUserRelationDao extends BaseDao<PaymentUserRelation>{

	public PaymentUserRelation getUserToken(PaymentUserRelation paymentUserRelation);
}
