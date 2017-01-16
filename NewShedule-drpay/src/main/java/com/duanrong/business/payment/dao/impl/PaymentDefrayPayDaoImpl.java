package com.duanrong.business.payment.dao.impl;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.payment.dao.PaymentDefrayPayDao;
import com.duanrong.business.payment.model.PaymentDefrayPay;

@Repository
public class PaymentDefrayPayDaoImpl extends BaseDaoImpl<PaymentDefrayPay>  implements PaymentDefrayPayDao {

	public PaymentDefrayPayDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.payment.mapper.PaymentDefrayPayMapper");
	}
}
