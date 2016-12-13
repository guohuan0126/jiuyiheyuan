package com.duanrong.yeepay.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.Log;
import util.XMLUtil;

import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.model.AccountChecking;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.util.Dom4jUtil;
import com.duanrong.yeepay.service.TrusteeshipQueryAccounantService;
import com.duanrong.yeepay.xml.GeneratorXML;
import com.duanrong.yeepaysign.CFCASignUtil;

/**
 * 账户查询
 * 
 * @author Lin Zhiming
 * @version Mar 2, 2015 10:53:28 AM
 */
@Service
public class TrusteeshipQueryAccounantServiceImpl implements
		TrusteeshipQueryAccounantService {
	@Resource
	TrusteeshipOperationService trusteeshipOperationService;

	@Resource
	Log log;

	@Resource
	UserMoneyService userMoneyService;

	@Override
	public AccountChecking queryAccount(String platformUserNo) throws Exception {
		AccountChecking accountChecking = new AccountChecking();

		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);

		/*********************** XML拼接 ***********************/
		GeneratorXML xml = new GeneratorXML();
		xml.setPlatformNo(TrusteeshipYeepayConstants.Config.MER_CODE);
		xml.setPlatformUserNo(platformUserNo);
		String content = null;
		try {
			content = XMLUtil.getXML(xml);
		} catch (Exception e) {
			log.errLog("账户查询XML拼接异常", e);
			return null;
		}

		/*********************** 生成签名 ***********************/
		String sign = CFCASignUtil.sign(content);
		log.infoLog("账户查询"+platformUserNo, sign+","+content);
		postMethod.setParameter("req", content);
		postMethod.setParameter("sign", sign);
		postMethod.setParameter("service", "ACCOUNT_INFO");

		/*********************** 执行POST ***********************/
		int statusCode = client.executeMethod(postMethod);
		if (HttpStatus.SC_OK != statusCode) {
			log.errLog("账户查询HTTP状态码异常", postMethod.getStatusLine().toString());
		}
		byte[] responseBody = postMethod.getResponseBody();
		String accountName = new String(responseBody, "UTF-8");
		log.infoLog("账户查询返回数据", accountName);

		Map<String, String> resultMap = Dom4jUtil.xmltoMap(accountName);

		String code = resultMap.get("code");
		String description = resultMap.get("description");
		String balance = resultMap.get("balance");
		String availableAmount = resultMap.get("availableAmount");
		String freezeAmount = resultMap.get("freezeAmount");
		String cardNo = resultMap.get("cardNo");
		String cardStatus = resultMap.get("cardStatus");
		String bank = resultMap.get("bank");

		if (StringUtils.equals("1", code)) {
			// 查询成功 处理查询记录
			accountChecking.setEbaoAvailableAmount(availableAmount);
			accountChecking.setEbaoBalance(balance);
			accountChecking.setEbaoFreezeAmount(freezeAmount);
		} else if (code.equals("101")) {
			log.infoLog("账户查询", "用户:" + platformUserNo + "以实名认证,但是未在易宝开户,code="+code);
		} else {
			accountChecking.setEbaoAvailableAmount("0.00");
			accountChecking.setEbaoBalance("0.00");
			accountChecking.setEbaoFreezeAmount("0.00");
			log.errLog("账户查询", "用户:" + platformUserNo + "存在不确定问题,code="+code);
		}
		return accountChecking;
	}
}
