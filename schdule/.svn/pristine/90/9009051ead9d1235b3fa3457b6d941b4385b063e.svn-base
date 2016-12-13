package com.duanrong.business.payment.dao.impl;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.payment.dao.PaymentAdvancefundDao;
import com.duanrong.business.payment.model.PaymentAdvancefund;

@Repository
public class PaymentAdvancefundDaoImpl extends BaseDaoImpl<PaymentAdvancefund> implements PaymentAdvancefundDao {

	public PaymentAdvancefundDaoImpl(){
		this.setMapperNameSpace("com.duanrong.business.payment.mapper.PaymentAdvancefundMapper");
	}
	
	@Override
	public PaymentAdvancefund query() {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getPaymentAdvancefund");
	}

}
