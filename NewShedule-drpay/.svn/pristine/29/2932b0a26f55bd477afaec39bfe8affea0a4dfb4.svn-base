package com.duanrong.business.payment.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.business.payment.dao.PaymentAdvancefundDao;
import com.duanrong.business.payment.model.PaymentAdvancefund;
import com.duanrong.business.payment.service.PaymentAdvancefundService;

@Service
public class PaymentAdvancefundServiceImpl implements PaymentAdvancefundService {

	@Resource
	PaymentAdvancefundDao paymentAdvancefundDao;
	
	/**
	 * 获取垫付资金信息
	 */
	@Override
	public PaymentAdvancefund query() {
		return paymentAdvancefundDao.query();
	}

	@Override
	public void update(PaymentAdvancefund advancefund) {
		paymentAdvancefundDao.update(advancefund);
	}

}
