package com.duanrong.yeepay.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import util.Log;
import util.XMLUtil;

import com.duanrong.business.bankcard.service.BankCardService;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.model.AccountChecking;
import com.duanrong.util.Dom4jUtil;
import com.duanrong.util.client.DRHTTPClient;
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
	BankCardService bankCardService;

	@Override
	public AccountChecking queryAccount(String platformUserNo) throws Exception {
		AccountChecking accountChecking = new AccountChecking();
		
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
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("req", content));
		params.add(new BasicNameValuePair("sign", sign));
		params.add(new BasicNameValuePair("service", "ACCOUNT_INFO"));
		/*********************** 执行POST ***********************/
		String accountName = DRHTTPClient.sendHTTPRequestPostToString(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl, new BasicHeader[]{}, params);
		log.infoLog("账户查询返回数据", accountName);
		if(null != accountName && !"".equals(accountName.trim())){
			Map<String, String> resultMap = Dom4jUtil.xmltoMap(accountName);
			String code = resultMap.get("code");
			if (StringUtils.equals("1", code)) {		
				String balance = resultMap.get("balance");
				String cardNo = resultMap.get("cardNo");
				String availableAmount = resultMap.get("availableAmount");
				String freezeAmount = resultMap.get("freezeAmount");		
				// 查询成功 处理查询记录
				accountChecking.setEbaoAvailableAmount(availableAmount);
				accountChecking.setEbaoBalance(balance);
				accountChecking.setEbaoFreezeAmount(freezeAmount);
				log.infoLog(platformUserNo, cardNo);
			} else if (code.equals("101")) {
				log.infoLog("账户查询", "用户:" + platformUserNo + "以实名认证,但是未在易宝开户,code="+code);
			} else {
				accountChecking.setEbaoAvailableAmount("0.00");
				accountChecking.setEbaoBalance("0.00");
				accountChecking.setEbaoFreezeAmount("0.00");
				log.errLog("账户查询", "用户:" + platformUserNo + "存在不确定问题,code="+code);
			}
		}
		return accountChecking;
	}
}