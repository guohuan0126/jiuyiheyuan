package com.duanrong.yeepay.service.impl;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Service;

import util.Log;
import util.XMLUtil;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.yeepay.service.TrusteeshipQueryBusinessService;
import com.duanrong.yeepay.xml.GeneratorXML;
import com.duanrong.yeepaysign.CFCASignUtil;

/**
 * 账户查询
 * 
 * @author Lin Zhiming
 * @version Mar 2, 2015 10:53:28 AM
 */
@Service
public class TrusteeshipQueryBusinessServiceImpl implements
		TrusteeshipQueryBusinessService {

	@Resource
	Log log;

	@Override
	public String query(String requestNo) throws Exception {
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);

		/*********************** XML拼接 ***********************/
		GeneratorXML xml = new GeneratorXML();
		xml.setPlatformNo(TrusteeshipYeepayConstants.Config.MER_CODE);
		xml.setRequestNo(requestNo);
		xml.setMode("CP_TRANSACTION");
		String content = XMLUtil.getXML(xml);
		/*********************** 生成签名 ***********************/
		String sign = CFCASignUtil.sign(content);
		log.infoLog("投资轮询XML", content);
		log.infoLog("投资轮询sign", sign);
		postMethod.setParameter("req", content);
		postMethod.setParameter("sign", sign);
		postMethod.setParameter("service", "QUERY");

		/*********************** 执行POST ***********************/

		int statusCode = client.executeMethod(postMethod);
		if (HttpStatus.SC_OK != statusCode) {
			log.errLog("投资轮询HTTP状态码异常", postMethod.getStatusLine().toString());
		}
		// 易宝响应结果
		byte[] responseBody = postMethod.getResponseBody();
		String respInfo = new String(responseBody, "UTF-8");
		log.infoLog("投资轮询返回数据", respInfo);
		// 响应信息
		postMethod.releaseConnection();
		return respInfo;

	}
}
