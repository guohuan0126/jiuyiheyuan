package com.duanrong.business.payment.service;

import com.duanrong.business.payment.model.PaymentYeepayTransferUser;


public interface PaymentYeepayTransferUserService {

	public void insert(PaymentYeepayTransferUser ctu);

	public PaymentYeepayTransferUser getByRequestNo(String paymentNo);
}