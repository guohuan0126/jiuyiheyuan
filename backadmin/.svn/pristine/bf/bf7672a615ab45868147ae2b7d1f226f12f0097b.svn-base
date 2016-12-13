package com.duanrong.yeepay.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import util.Log;

import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.newadmin.utility.Dom4jUtil;
import com.duanrong.util.client.DRHTTPSClient;
import com.duanrong.yeepay.service.TrusteeshipTransferService;
import com.duanrong.yeepaysign.CFCASignUtil;


/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : SunZ
 * @CreateTime : 2015-4-2 下午11:16:18
 * @Description : NewAdmin com.duanrong.yeepay.service.impl
 *              TrusteeshipGiveMoneyToBorrowerServiceImpl.java
 * 
 */
@Service
public class TrusteeshipTransferServiceImpl implements TrusteeshipTransferService {

	@Resource
	Log log;

	@Resource
	TrusteeshipOperationService trusteeshipOperationService;

	/**
	 * 转账
	 * @param loan
	 * @param invests
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> transfer(String requestNo, String type, TrusteeshipOperation to) throws Exception {	
		
		StringBuffer content = new StringBuffer();
		content.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		// platformNo:商户编号
		content.append("<request platformNo=\""
				+ TrusteeshipYeepayConstants.Config.MER_CODE + "\">");
		content.append("<requestNo>" + requestNo + "</requestNo>");
		content.append("<mode>" + type + "</mode>");
		content.append("<notifyUrl>"
				+ TrusteeshipYeepayConstants.ResponseS2SUrl.PRE_RESPONSE_URL
				+ "complete_transaction_invest" + "</notifyUrl>");
		content.append("</request>");		
		String xml = content.toString();
		String sign = CFCASignUtil.sign(xml);
		to.setRequestData(to.getRequestData() + "\nsign=" + sign + ",req="
				+ xml);
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("req", xml));
		params.add(new BasicNameValuePair("sign", sign));
		params.add(new BasicNameValuePair("service", "COMPLETE_TRANSACTION"));
		String responseBody = DRHTTPSClient.sendHTTPRequestPostToString(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl,
				new BasicHeader[0], params);
		
		log.infoLog("取消转账", "requestId: " + requestNo + responseBody);
		@SuppressWarnings("unchecked")
		Map<String, String> respMap = Dom4jUtil.xmltoMap(responseBody);
		to.setResponseData(to.getResponseData() + responseBody);
		to.setRequestTime(new Date());
		return respMap;
		
	}
	
	
	
}
