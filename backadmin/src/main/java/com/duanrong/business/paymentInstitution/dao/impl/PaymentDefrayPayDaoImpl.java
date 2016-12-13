package com.duanrong.business.paymentInstitution.dao.impl;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.paymentInstitution.dao.PaymentDefrayPayDao;
import com.duanrong.business.paymentInstitution.model.PaymentDefrayPay;

@Repository
public class PaymentDefrayPayDaoImpl extends BaseDaoImpl<PaymentDefrayPay>  implements PaymentDefrayPayDao {

	public PaymentDefrayPayDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.paymentInstitution.mapper.PaymentDefrayPayMapper");
	}

	@Override
	public PaymentDefrayPay queryPaymentDefrayPayByMarkId(String markId) {
		
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".queryPaymentDefrayPayByMarkId", markId);
	}
}
