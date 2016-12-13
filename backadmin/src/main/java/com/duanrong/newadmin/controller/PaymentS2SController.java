package com.duanrong.newadmin.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import util.Log;
import util.MyStringUtils;

import com.duanrong.payment.PaymentConstants;
import com.duanrong.payment.service.PaymentWithdrawCashService;

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
@RequestMapping(value = "/S2SCallback/v1/notify", method = RequestMethod.POST)
public class PaymentS2SController extends BaseController {

	@Resource
	PaymentWithdrawCashService paymentWithdrawCashService;
	
	@Resource
	Log log;
	/**
	 * 第三方返回
	 * @param operationType	类型
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/{operationType}")
	public void callback(@PathVariable String operationType,HttpServletRequest request, HttpServletResponse response) {
		log.infoLog("京东代付", "京东代付服务器返回通知");
		try {
			// 京东代付
			if (StringUtils.equals(PaymentConstants.JDConfig.DEFRAYPAY,operationType)) {
				paymentWithdrawCashService.S2SCallback(request, response,operationType);
				return;
			}else if(StringUtils.equals(PaymentConstants.BaofooConfig.DEFRAYPAY,operationType)){
				paymentWithdrawCashService.BaoFooS2SCallback(request, response,operationType);
				return;
			}
		} catch (Exception ex) {
			log.errLog(MyStringUtils.append("服务器通知"), "operationType:"+operationType+",Exception:"+ex);
		}
	}
}