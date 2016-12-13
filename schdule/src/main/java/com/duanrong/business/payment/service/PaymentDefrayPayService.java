package com.duanrong.business.payment.service;

import base.pagehelper.PageInfo;

import com.duanrong.business.payment.model.PaymentDefrayPay;



public interface PaymentDefrayPayService {

	void insert(PaymentDefrayPay paymentDefrayPay);

	PageInfo<PaymentDefrayPay> pageLite(int pageNo, int pageSize,
			PaymentDefrayPay defrayPay);

	PaymentDefrayPay get(String id);
	
	void update(PaymentDefrayPay paymentDefrayPay);

}