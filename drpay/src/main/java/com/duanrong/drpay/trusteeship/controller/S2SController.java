package com.duanrong.drpay.trusteeship.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import util.FastJsonUtil;
import util.Log;
import util.SpringBeanUtil;

import com.duanrong.drpay.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.drpay.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.drpay.config.ConfigConstant;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;
import com.duanrong.drpay.trusteeship.helper.model.NotifyURL;
import com.duanrong.drpay.trusteeship.helper.model.UserDevice;

/**
 * 服务器通知
 * 
 * @author xiao
 * @datetime 2016年12月7日 下午3:45:43
 */
@Controller
@RequestMapping(value = "/depository", method = RequestMethod.POST)
public class S2SController {

	@Resource
	TrusteeshipOperationService trusteeshipOperationService;

	/**
	 * 回调
	 */
	private final static String CALLBACK = "CALLBACK";

	/**
	 * 服务器通知
	 */
	private final static String NOTIFY = "NOTIFY";

	@Resource
	Log log;

	org.apache.commons.logging.Log logger = LogFactory.getLog(S2SController.class);
	
	/**
	 * 存管通回调
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/service/{type}.do")
	public void service(HttpServletRequest request,
			HttpServletResponse response, @PathVariable NotifyURL type) {
		String responseType = request.getParameter("responseType");
		logger.debug("S2SController->Service:serviceName:"+request.getParameter("serviceName")+
				",responseType:"+responseType+",respData:"+request.getParameter("respData"));
		try {
			response.getWriter().write("SUCCESS");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (responseType.equals(CALLBACK)){
			this.callback(request, response, type);
		}
		if (responseType.equals(NOTIFY)){
			this.notify(request.getParameter("sign"),
					request.getParameter("respData"), type);
		}
	}

	/**
	 * callback 处理
	 * 
	 * @param request
	 * @param response
	 * @param type
	 */
	@Async
	private void callback(HttpServletRequest request,
			HttpServletResponse response, NotifyURL type) {
		String userDevice = request.getParameter("userDevice");
		String callbackUrl = ConfigConstant.CALLBACK_PC;
		if (UserDevice.MOBILE.name().equals(userDevice))
			callbackUrl = ConfigConstant.CALLBACK_M;
		else
			callbackUrl = ConfigConstant.CALLBACK_PC;
		Class<? extends GeneratorJSON> parameterType = type.getParameterType();
		String respData = request.getParameter("respData");
		GeneratorJSON json = (GeneratorJSON) FastJsonUtil.jsonToObj(respData,
				parameterType);
		if (json != null && json.getCode().equals("0")) {
			callbackUrl +=type.name() + "?source="+request.getParameter("source");
			if (!StringUtils.isBlank(json.getPlatformUserNo())){
				callbackUrl += "&userId="
						+ json.getPlatformUserNo();
				String serviceName = request.getParameter("serviceName");
				if ("ACTIVATE_STOCKED_USER".equals(serviceName)) {
					callbackUrl += "&accessType="
							+ json.getAccessType();
				}
			}
			try {
				response.sendRedirect(callbackUrl);
			} catch (IOException e) {
				log.errLog("callback错误", "callbackurl：" + callbackUrl
						+ ", error" + ExceptionUtils.getMessage(e));
			}
		} else {
			log.errLog("callback错误",
					"业务处理错误, 不处理callback, respData: " + json.toString());
		}
	}

	/**
	 * notify 处理
	 * 
	 * @param data
	 * @param type
	 */
	@Async
	private void notify(String sign, String data, NotifyURL type) {
		Class<?> clazz = type.getService();
		Class<? extends GeneratorJSON> parameterType = type.getParameterType();
		// 反射 实例 由 spring获取, 否则无法使用依赖注入
		Object obj = SpringBeanUtil.getBeanByType(clazz);
		GeneratorJSON json = (GeneratorJSON) FastJsonUtil.jsonToObj(data,
				parameterType);

		logger.debug("服务器通知返回：" + type.getService() + "\t " + type.getNotifyMethod() + "\t" + type.getParameterType());
		if (type.getBusinessType() != null) {
			/******************** 记录s2s to 记录 **********************/
			TrusteeshipOperation to = trusteeshipOperationService.get(type
					.getBusinessType().name(), json.getRequestNo(), json
					.getRequestNo(), "xmbank");
			if (to != null) {
				to.setResponseTime(new Date());
				to.setResponseData(to.getResponseData() == null ? "sign="
						+ sign + ", resp=" + data : to.getResponseData()
						+ "\n sign=" + sign + ", resp=" + data);
				if ("0".equals(json.getCode()))
					to.setStatus(com.duanrong.drpay.business.trusteeship.TrusteeshipConstants.PASSED);
				else
					to.setStatus(com.duanrong.drpay.business.trusteeship.TrusteeshipConstants.REFUSED);
				trusteeshipOperationService.update(to);
			}
			/*************************************************************/
		}
		try {
			logger.debug("服务器通知返回：反射回调方法");
			Method method = clazz.getMethod(type.getNotifyMethod(),
					parameterType);
			logger.debug("服务器通知返回：method: " + method.toString());
			method.invoke(obj, json);
		} catch (Exception e) {
			log.errLog("存管服务器通知错误", "notify：" + type.name()
					+ ", notifyMethod: " + type.getService() + ", paramter: "
					+ type.getParameterType() + ", data : " + data
					+ ", error: " + ExceptionUtils.getMessage(e));
			e.printStackTrace();
		}
	}
}