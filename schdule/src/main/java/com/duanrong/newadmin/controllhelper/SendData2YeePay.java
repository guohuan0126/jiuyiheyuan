package com.duanrong.newadmin.controllhelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;



import com.duanrong.newadmin.utility.HtmlElementUtil;
import com.duanrong.newadmin.utility.LoadConstantProterties2;
import com.duanrong.newadmin.utility.MapUtil;
import com.duanrong.newadmin.utility.ToType;

/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved
 * Company : 久亿财富（北京）投资有限公司
 * @Author : 孙铮
 * @CreateTime : 2014-11-17 下午1:46:58 
 * @Description : drpc com.duanrong.pcweb.controllhelper SendData2YeePay.java
 *
 */
public class SendData2YeePay {

	/**
	 * 
	 * @description 给易宝发送数据
	 * @author 孙铮
	 * @time 2014-9-5 下午5:32:21
	 * @param xmlDate
	 *            服务端返回的xml数据
	 * @param to
	 * @param response
	 * @return true发送成功,false发送失败
	 * @throws IOException
	 * @throws InsufficientBalance_Exception 
	 * @throws ExceedMoneyNeedRaised_Exception 
	 * @throws ExceedMaxAcceptableRate_Exception 
	 */
	public static boolean sendOperation(String xmlDate,ToType type,
			HttpServletResponse response,String sign) throws IOException{
		if (type == null) {
			return false;
		}
		if (xmlDate == null) {
			return false;
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("req", xmlDate);
		params.put("sign", sign);
		String mapToString = MapUtil.mapToString(params);
		
		String requestURL = LoadConstantProterties2
				.getValueByDefaultPro("YEEPAY_URL")
				+ LoadConstantProterties2.getValueByDefaultPro(type.toString());
		
		Map<String, String> p = MapUtil.stringToHashMap(mapToString);
		String content = HtmlElementUtil.createAutoSubmitForm(p,
				requestURL, "utf-8");

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.getWriter().write(content);
		return true;
	}
	/**
	 * 统一的易宝callback回调地址
	 * 
	 * @return
	 */
	public static String unifiedYeepayCallback(ToType type) {
		String callback = LoadConstantProterties2
				.getValueByDefaultPro("ResponseWebUrl_PRE_RESPONSE_URL")
				+ LoadConstantProterties2
						.getValueByDefaultPro("UNIFIED_PREFIX");
		if (ToType.ENTE.toString().equals(type.toString())
				|| ToType.OPAC.toString().equals(type.toString())) {
			callback += "regSuccess";
		} else {
			callback += type.toString();
		}
		return callback;
	}
}
