package com.duanrong.drpay.business.payment.dao.impl;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.drpay.business.payment.dao.PaymentAdvancefundDao;
import com.duanrong.drpay.business.payment.model.PaymentAdvancefund;

@Repository
public class PaymentAdvancefundDaoImpl extends BaseDaoImpl<PaymentAdvancefund> implements PaymentAdvancefundDao {

	public PaymentAdvancefundDaoImpl(){
		this.setMapperNameSpace("com.duanrong.drpay.business.payment.mapper.PaymentAdvancefundMapper");
	}
	
	@Override
	public PaymentAdvancefund get() {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getPaymentAdvancefund");
	}

}
