package com.duanrong.newadmin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import util.Log;
import util.MyStringUtils;

import com.duanrong.business.demand.service.DemandtreasureTransferOutService;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.yeepay.service.TrusteeshipAutoInvestService;
import com.duanrong.yeepay.service.TrusteeshipGiveMoneyToBorrowerService;
import com.duanrong.yeepay.service.TrusteeshipTransactionAuthorizationService;
import com.duanrong.yeepaysign.CFCASignUtil;

/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : SunZ
 * @CreateTime : 2015-4-2 下午2:16:27
 * @Description : NewAdmin com.duanrong.newadmin.controller
 *              YeepayNotifyController.java
 * 
 */
@Controller
@RequestMapping(value = "/trusteeship_return_s2s")
public class S2SController extends BaseController {

	@Resource
	Log log;
	@Resource
	DemandtreasureTransferOutService demandtreasureTransferOutService;

	@Resource
	TrusteeshipTransactionAuthorizationService trusteeshipTransactionAuthorizationService;
	
	@Resource
	TrusteeshipGiveMoneyToBorrowerService trusteeshipGiveMoneyToBorrowerService;
	
	@Resource
	TrusteeshipAutoInvestService trusteeshipAutoInvestService;
	/**
	 * 
	 * @description 记录
	 * @author 孙铮
	 * @time 2015-4-2 下午2:30:24
	 * @param operationType
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/{operationType}")
	public void callback(@PathVariable String operationType,
			HttpServletRequest request, HttpServletResponse response) {
		// 响应的参数 为XML格式
		String notifyXML = request.getParameter("notify");
		log.infoLog(operationType + " → S2S XML", notifyXML);
		// 签名
		String sign = request.getParameter("sign");

		boolean flag = CFCASignUtil.isVerifySign(notifyXML, sign);

		if (MyStringUtils.isNotAnyBlank(notifyXML, sign)) {

			log.infoLog(operationType + " → S2S sign", sign);

			if (flag) {

				dealS2S(operationType, request, response);

				/********** 通知易宝响应成功 **********/
				try {
					response.getWriter().write("SUCCESS");
				} catch (Exception e) {
					log.errLog("通知易宝响应成功异常", e);
				}
			} else {
				log.errLog("易宝签名", "易宝签名不正确");
			}
		} else {
			log.errLog("易宝返回参数为空", "易宝返回参数为空");
		}
	}

	public void dealS2S(String operationType, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 自动投标授权
			if (StringUtils.equals(
					TrusteeshipYeepayConstants.OperationType.AUTOINVEST,
					operationType)) {
				//trusteeshipAutoInvestService.S2SCallback(request, response);
				return;
			} else if (StringUtils.equals(
					TrusteeshipYeepayConstants.OperationType.PLATFORM_TRANSFER,
					operationType)) {
				return;
			}
			else if (StringUtils.equals(
					TrusteeshipYeepayConstants.OperationType.THANAUTH,
					operationType)) {
				return;
			}
			else if (StringUtils.equals(
					TrusteeshipYeepayConstants.OperationType.WHDEBITNOCARD,
					operationType)) {
				return;
			}else if (StringUtils.equals(
								TrusteeshipYeepayConstants.OperationType.TO_CP_TRANSACTION,
								operationType)) {
				demandtreasureTransferOutService.S2SCallback(request,
									response);
							return;
			}else if(StringUtils.equals(
					"geth",operationType)){							
				trusteeshipTransactionAuthorizationService.S2SCallback(request, response);
			}else if(StringUtils.equals(
					"complete_transaction_invest",operationType)){
				trusteeshipGiveMoneyToBorrowerService.S2SCallback(request, response);
			}
		} catch (Exception ex) {
			log.errLog(MyStringUtils.append(operationType, " → S2S"), ex);
		}
	}
}
