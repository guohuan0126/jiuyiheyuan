package com.duanrong.business.payment.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.business.payment.dao.PaymentDefrayPayDao;
import com.duanrong.business.payment.model.PaymentDefrayPay;
import com.duanrong.business.payment.service.PaymentDefrayPayService;


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


}