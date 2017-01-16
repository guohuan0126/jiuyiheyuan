package com.duanrong.business.payment.dao.impl;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.payment.dao.PaymentYeepayTransferUserDao;
import com.duanrong.business.payment.model.PaymentYeepayTransferUser;

@Repository
public class PaymentYeepayTransferUserDaoImpl extends
		BaseDaoImpl<PaymentYeepayTransferUser> implements PaymentYeepayTransferUserDao {

	public PaymentYeepayTransferUserDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.payment.mapper.PaymentYeepayTransferUserMapper");
	}

	@Override
	public PaymentYeepayTransferUser getByRequestNo(String paymentNo) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getByRequestNo",paymentNo);
	}

}