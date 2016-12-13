package com.duanrong.business.paymentInstitution.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.business.paymentInstitution.dao.PaymentDefrayPayDao;
import com.duanrong.business.paymentInstitution.model.PaymentDefrayPay;
import com.duanrong.business.paymentInstitution.service.PaymentDefrayPayService;


@Service
public class PaymentDefrayPayServiceImpl implements PaymentDefrayPayService{

	@Resource
	PaymentDefrayPayDao paymentDefrayPayDao;
	
	@Override
	public void insert(PaymentDefrayPay paymentDefrayPay){
		paymentDefrayPayDao.insert(paymentDefrayPay);
	}

	@Override
	public PageInfo<PaymentDefrayPay> pageLite(int pageNo, int pageSize,
			PaymentDefrayPay defrayPay) {
		return paymentDefrayPayDao.pageLite(pageNo, pageSize, defrayPay);
	}

	@Override
	public PaymentDefrayPay get(String id) {
		return paymentDefrayPayDao.get(id);
	}

	@Override
	public void update(PaymentDefrayPay paymentDefrayPay) {
		paymentDefrayPayDao.update(paymentDefrayPay);		
	}

	@Override
	public PaymentDefrayPay queryPaymentDefrayPayByMarkId(String markId) {
		return paymentDefrayPayDao.queryPaymentDefrayPayByMarkId(markId);	
	}


}