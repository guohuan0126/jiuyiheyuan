package com.duanrong.business.payMentChannel.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import base.pagehelper.PageInfo;

import com.duanrong.business.payMentChannel.model.PayMentChannel;
import com.duanrong.business.payMentChannel.model.PaymentBankChannel;
import com.duanrong.business.payMentChannel.model.PaymentBankInfo;

public interface PayMentChannelService {

	PageInfo<PayMentChannel> readPayMentChannel(int pageNo, int pageSize,
			Map<String, Object> params);

	String uploadPayMentChannel(CommonsMultipartFile[] files,
			HttpServletRequest request);

	void insertPaymentChannel(PayMentChannel paymentChannel);

	void updatePaymentChannel(Map<String, Object> params);

	PayMentChannel readPayMentChannelById(String id);

	PageInfo<PaymentBankInfo> readPaymentBankInfo(int pageNo, int pageSize,
			Map<String, Object> params);

	String uploadPayMentBankInfo(CommonsMultipartFile[] files,
			HttpServletRequest request);

	void insertPayMentBankInfo(PaymentBankInfo paymentBankInfo);

	PaymentBankInfo readPaymentBankInfoById(String id);

	void updatePaymentBankInfo(Map<String, Object> params);

	PageInfo<PaymentBankChannel> readPaymentBankChannel(int pageNo, int pageSize, Map<String, Object> params);

	List<PaymentBankInfo> readBankList();

	List<PayMentChannel> readChannelList();

	void insertPaymentBankChannel(PaymentBankChannel paymentBankChannel);

	PaymentBankChannel readPaymentBankChannelById(String id);

	void updatePayMentBankChannel(Map<String, Object> params);

	PaymentBankChannel readPaymentBankChannelByBankIdAndChannelId(String bankName,
			String channelName);

}
