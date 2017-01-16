package com.duanrong.drpay.business.payment.service;

import java.util.List;

import com.duanrong.drpay.business.payment.model.PaymentChannel;

/**
 * 支付渠道银行信息
 * @author drw
 * @datetime 2017年1月3日下午7:29:02
 */
public interface PaymentBankChannelService {

	/**
	 * 获取有效的渠道（PC或移动端可用）
	 * @return
	 */
	List<PaymentChannel> getValidChannels();
}
