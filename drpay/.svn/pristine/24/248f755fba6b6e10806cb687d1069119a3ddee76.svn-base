package com.duanrong.drpay.business.payment.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.drpay.business.payment.dao.PaymentAdvancefundDao;
import com.duanrong.drpay.business.payment.model.PaymentAdvancefund;
import com.duanrong.drpay.business.payment.service.PaymentAdvancefundService;

@Service
public class PaymentAdvancefundServiceImpl implements PaymentAdvancefundService {

	@Resource
	PaymentAdvancefundDao paymentAdvancefundDao;
	
	/**
	 * 获取垫付资金信息
	 */
	@Override
	public PaymentAdvancefund query() {
		return paymentAdvancefundDao.get();
	}

	@Override
	public void update(PaymentAdvancefund advancefund) {
		paymentAdvancefundDao.update(advancefund);
	}

}
