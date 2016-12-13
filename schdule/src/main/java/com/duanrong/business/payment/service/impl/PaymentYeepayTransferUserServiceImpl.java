package com.duanrong.business.payment.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.business.payment.dao.PaymentYeepayTransferUserDao;
import com.duanrong.business.payment.model.PaymentYeepayTransferUser;
import com.duanrong.business.payment.service.PaymentYeepayTransferUserService;

@Service
public class PaymentYeepayTransferUserServiceImpl implements PaymentYeepayTransferUserService {

	@Resource
	PaymentYeepayTransferUserDao paymentYeepayTransferUserDao;
	
	@Override
	public void insert(PaymentYeepayTransferUser ctu) {
		paymentYeepayTransferUserDao.insert(ctu);
	}

	@Override
	public PaymentYeepayTransferUser getByRequestNo(String paymentNo) {
		return paymentYeepayTransferUserDao.getByRequestNo(paymentNo);
	}


}
