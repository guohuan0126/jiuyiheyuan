package com.duanrong.drpay.trusteeship.helper.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import util.FastJsonUtil;

import com.duanrong.drpay.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.drpay.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.config.IdUtil;
import com.duanrong.drpay.trusteeship.constants.TrusteeshipConstants;
import com.duanrong.drpay.trusteeship.constants.TrusteeshipServer;
import com.duanrong.drpay.trusteeship.helper.httpclient.TrusteeshipHttpClient;
import com.duanrong.drpay.trusteeship.helper.model.Generator;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;
import com.duanrong.drpay.trusteeship.helper.model.UserDevice;
import com.duanrong.drpay.trusteeship.helper.service.TrusteeshipService;
import com.duanrong.drpay.trusteeship.helper.sign.Sign;

/**
 * TrusteeshipService 抽象类
 * 
 * @author xiao
 * @datetime 2016年12月12日 上午9:32:12
 */

public abstract class AbstractTrusteeshipService implements TrusteeshipService {

	@Resource
	TrusteeshipOperationService trusteeshipOperationService;

	@Override
	public Generator create(GeneratorJSON json, TrusteeshipServer server) {
		String jsonstr = json.toJSON();
		return new Generator(TrusteeshipConstants.REQUEST_URL
				+ TrusteeshipConstants.GATEWAY, server, Sign.sign(jsonstr),
				jsonstr);
	}

	@Override
	public Generator create(GeneratorJSON json, TrusteeshipServer server,
			BusinessEnum businessType) {
		String jsonstr = json.toJSON();
		String sign = Sign.sign(jsonstr);
		/****************** 请求前保存to记录 *****************/
		TrusteeshipOperation to = new TrusteeshipOperation();
		to.setId(IdUtil.randomUUID());
		to.setMarkId(json.getRequestNo());
		to.setOperator(json.getRequestNo());
		to.setType(businessType.name());
		to.setRequestUrl(TrusteeshipConstants.REQUEST_URL
				+ TrusteeshipConstants.GATEWAY + server.name());
		to.setRequestTime(new Date());
		to.setRequestData("sign=" + sign + ", req=" + jsonstr);
		to.setTrusteeship("xmbank");
		to.setStatus(com.duanrong.drpay.business.trusteeship.TrusteeshipConstants.SENDED);
		to.setUserId(json.getPlatformUserNo());
		trusteeshipOperationService.insert(to);
		/****************************************************/
		return new Generator(TrusteeshipConstants.REQUEST_URL
				+ TrusteeshipConstants.GATEWAY, server, sign, jsonstr);
	}

	@Override
	public Generator execute(GeneratorJSON json, TrusteeshipServer server,
			Class<? extends GeneratorJSON> GeneratorJSONClass) {
		String reqData = json.toJSON();
		String sign = Sign.sign(reqData);
		return new Generator(TrusteeshipConstants.REQUEST_URL
				+ TrusteeshipConstants.SERVICE, server, UserDevice.PC, sign,
				reqData, (GeneratorJSON) FastJsonUtil.jsonToObj(
						TrusteeshipHttpClient.send(
								TrusteeshipConstants.REQUEST_URL
										+ TrusteeshipConstants.SERVICE, server,
								reqData, sign), GeneratorJSONClass));
	}

	@Override
	public Generator execute(GeneratorJSON json, TrusteeshipServer server,
			Class<? extends GeneratorJSON> GeneratorJSONClass,
			BusinessEnum businessType) {
		String reqData = json.toJSON();
		String sign = Sign.sign(reqData);

		/***************** 请求前保存to记录 *****************/
		TrusteeshipOperation to = new TrusteeshipOperation();
		to.setId(IdUtil.randomUUID());
		to.setMarkId(json.getRequestNo());
		to.setOperator(json.getRequestNo());
		to.setType(businessType.name());
		to.setRequestUrl(TrusteeshipConstants.REQUEST_URL
				+ TrusteeshipConstants.GATEWAY + server.name());
		to.setRequestTime(new Date());
		to.setRequestData("sign=" + sign + ", req=" + reqData);
		to.setTrusteeship("xmbank");
		to.setStatus(com.duanrong.drpay.business.trusteeship.TrusteeshipConstants.SENDED);
		to.setUserId(json.getPlatformUserNo());
		trusteeshipOperationService.insert(to);
		/******************************************************/

		/*************** 发送直连请求 ***************/
		String respData = TrusteeshipHttpClient
				.send(TrusteeshipConstants.REQUEST_URL
						+ TrusteeshipConstants.SERVICE, server, reqData, sign);
		GeneratorJSON respJson = (GeneratorJSON) FastJsonUtil.jsonToObj(
				respData, GeneratorJSONClass);
		/*************** 请求后更新 to 记录 ******************/
		to.setResponseData(respData);
		to.setResponseTime(new Date());
		if (respJson != null && respJson.getCode().equals("0"))
			to.setStatus(com.duanrong.drpay.business.trusteeship.TrusteeshipConstants.PASSED);
		else
			to.setStatus(com.duanrong.drpay.business.trusteeship.TrusteeshipConstants.REFUSED);
		trusteeshipOperationService.update(to);
		/******************************************************/

		return new Generator(TrusteeshipConstants.REQUEST_URL
				+ TrusteeshipConstants.SERVICE, server, UserDevice.PC, sign,
				reqData, respJson);
	}
}
