package com.duanrong.drpay.business.payment.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.drpay.business.payment.dao.PaymentBankChannelDao;
import com.duanrong.drpay.business.payment.model.PaymentChannel;
import com.duanrong.drpay.business.payment.service.PaymentBankChannelService;
@Service
public class PaymentBankChannelServiceImpl implements PaymentBankChannelService{

	@Resource
	PaymentBankChannelDao paymentBankChannelDao;
	
	@Override
	public List<PaymentChannel> getValidChannels() {
		return paymentBankChannelDao.getValidChannels();
	}
	
}
