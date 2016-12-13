package com.duanrong.business.payMentChannel.dao;

import java.util.List;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.business.account.model.PaymentChannel;
import com.duanrong.business.payMentChannel.model.PayMentChannel;
import com.duanrong.business.payMentChannel.model.PaymentBankChannel;
import com.duanrong.business.payMentChannel.model.PaymentBankInfo;

public interface PayMentChannelDao {

	PageInfo<PayMentChannel> readPayMentChannel(int pageNo, int pageSize,
			Map<String, Object> params);

	void insertPaymentChannel(PayMentChannel paymentChannel);

	void updatePaymentChannel(Map<String, Object> params);

	PayMentChannel readPayMentChannelById(String id);

	PageInfo<PaymentBankInfo> readPaymentBankInfo(int pageNo, int pageSize,
			Map<String, Object> params);

	void insertPayMentBankInfo(PaymentBankInfo paymentBankInfo);

	PaymentBankInfo readPaymentBankInfoById(String id);

	void updatePaymentBankInfo(Map<String, Object> params);

	PageInfo<PaymentBankChannel> readPaymentBankChannel(int pageNo,
			int pageSize, Map<String, Object> params);

	List<PaymentBankInfo> readBankList();

	List<PayMentChannel> readChannelList();

	void insertPaymentBankChannel(PaymentBankChannel paymentBankChannel);

	PaymentBankChannel readPaymentBankChannelById(String id);

	void updatePayMentBankChannel(Map<String, Object> params);

	PaymentBankChannel readPaymentBankChannelByBankIdAndChannelId(String bankName,
			String channelName);

	PayMentChannel getChannelByCode(String payMentId);

}
