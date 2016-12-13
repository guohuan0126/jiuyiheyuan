package com.duanrong.business.paymentInstitution.dao;

import base.dao.BaseDao;

import com.duanrong.business.paymentInstitution.model.PaymentDefrayPay;

public interface PaymentDefrayPayDao extends BaseDao<PaymentDefrayPay> {

	PaymentDefrayPay queryPaymentDefrayPayByMarkId(String markId);

}