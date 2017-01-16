package com.duanrong.drpay.jsonservice.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import base.error.ErrorCode;
import base.exception.ParameterException;

import com.duanrong.drpay.jsonservice.handler.View;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;
import com.duanrong.drpay.trusteeship.service.TrusteeshipTransactionQueryService;

@Controller
@RequestMapping(value = "/account", method = RequestMethod.POST)
public class PlatformAccountController extends BaseController {

	@Resource
	TrusteeshipTransactionQueryService trusteeshipTransactionQueryService;

	@RequestMapping("/queryPlatformInfo")
	@ResponseBody
	public View queryPlatformInfo()throws ParameterException {
		View view = getView();
		GeneratorJSON respData = trusteeshipTransactionQueryService.queryPlatformInfo();
		System.out.println(respData);
		view.setData(respData);
		view.setError(ErrorCode.SUCCESS);
		return view;
	}

}
