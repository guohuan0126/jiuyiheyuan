package com.duanrong.yeepay.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import util.Log;
import base.exception.InsufficientBalance;
import base.exception.InsufficientFreezeAmountException;
import base.exception.NoOpenAccountException;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.invest.InvestConstants;
import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.loan.LoanConstants;
import com.duanrong.business.loan.dao.LoanDao;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.trusteeship.model.TrusteeshipConstants;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.dao.RedPacketDao;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.RedPacket;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.newadmin.utility.Dom4jUtil;
import com.duanrong.util.DateUtil;
import com.duanrong.util.client.DRHTTPSClient;
import com.duanrong.yeepay.service.TrusteeshipCancelInvestService;
import com.duanrong.yeepaysign.CFCASignUtil;

/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : SunZ
 * @CreateTime : 2015-4-2 下午11:16:06
 * @Description : NewAdmin com.duanrong.yeepay.service
 *              TrusteeshipCancelInvestService.java
 * 
 */
@Service
public class TrusteeshipCancelInvestServiceImpl implements
		TrusteeshipCancelInvestService {

	@Resource
	Log log;

	@Resource
	LoanDao loanDao;

	@Resource
	InvestDao investDao;

	@Resource
	UserMoneyService userMoneyService;

	@Resource
	TrusteeshipOperationService trusteeshipOperationService;

	@Resource
	UserDao userDao;

	@Resource
	RedPacketDao redPacketDao;

	@Resource
	UserAccountService userAccountService;
	
	/**
	 * 
	 * @description 易宝流标
	 * @author 孙铮
	 * @time 2015-4-2 下午11:15:37
	 * @param userId
	 *            操作人
	 * @param loanId
	 *            借款项目id
	 * @return 流标结果
	 * @throws InsufficientBalance
	 */
	public String cancelInvest(String userId, String loanId)
			throws InsufficientBalance {
		if (StringUtils.isBlank(loanId)) {
			log.infoLog("流标失败", userId + "在执行流标时失败,借款项目id错误:" + loanId);
			return "流标失败:借款项目id错误";
		}
		Loan loan = loanDao.get(loanId);
		if (loan == null) {
			log.infoLog("流标失败", userId + "在执行流标时失败,借款项目不存在");
			return "流标失败:借款项目不存在";
		}
		if (LoanConstants.LoanStatus.COMPLETE.equals(loan.getStatus())
				|| LoanConstants.LoanStatus.REPAYING.equals(loan.getStatus())) {
			log.infoLog("流标失败", userId + "在执行流标时失败,借款项目状态不正确");
			return "流标失败:借款项目状态不正确";
		}
		loan.setStatus(LoanConstants.LoanStatus.CANCEL);

		Invest i = new Invest();
		i.setStatus(InvestConstants.InvestStatus.BID_SUCCESS);
		i.setLoanId(loanId);
		List<Invest> invests = investDao.getInvestLoan(i);
		boolean flag = true;

		for (Invest invest : invests) {
			flag = sendV2(invest, loan.getName());
		}

		loanDao.update(loan);
		return flag ? "流标成功" : "流标失败";
	}

	/*private boolean send(Invest invest, String loanName)
			throws InsufficientBalance {
		PostMethod postMethod = new PostMethod(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
		StringBuffer content = new StringBuffer();
		content.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		// 商户编号:商户在易宝唯一标识
		content.append("<request platformNo=\""
				+ TrusteeshipYeepayConstants.Config.MER_CODE + "\">");
		content.append("<platformUserNo>" + invest.getInvestUserID()
				+ "</platformUserNo>");
		content.append("<requestNo>" + invest.getId() + "</requestNo>");
		content.append("</request>");

		// 保存操作信息
		TrusteeshipOperation to = trusteeshipOperationService.read(
				TrusteeshipYeepayConstants.OperationType.INVEST,
				invest.getId(), invest.getId(), "yeepay");
		if (to == null) {
			to = trusteeshipOperationService.read(
					TrusteeshipYeepayConstants.OperationType.AUTOINVEST,
					invest.getId(), invest.getId(), "yeepay");
		}
		to.setStatus(TrusteeshipYeepayConstants.Status.SENDED);
		to.setRequestTime(new Date());
		to.setRequestData(content.toString());
		trusteeshipOperationService.update(to);

		postMethod.setParameter("req", content.toString());
		String sign = CFCASignUtil.sign(content.toString());
		postMethod.setParameter("sign", sign);
		postMethod.setParameter("service", "REVOCATION_TRANSFER");
		// 执行post方法
		HttpClient client = new HttpClient();
		try {
			int statusCode = client.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				log.infoLog("流标失败", "httpStatus错误:" + HttpStatus.SC_OK
						+ postMethod.getStatusLine());
			}
			// 获得返回的结果
			byte[] responseBody = postMethod.getResponseBody();
			// 响应信息
			@SuppressWarnings("unchecked")
			Map<String, String> resultMap = Dom4jUtil.xmltoMap(new String(
					responseBody, "UTF-8"));
			// 返回码
			String code = resultMap.get("code");
			// 描述
			to.setResponseData(new String(responseBody, "UTF-8"));
			to.setResponseTime(new Date());
			if (code.equals("1")) {// 如果取消投标成功，执行业务逻辑。
				userMoneyService.unfreezeMoney(invest.getInvestUserID(),
						invest.getMoney(), "解冻：投资" + loanName, "");
				// 更改投资状态
				invest.setStatus(InvestConstants.InvestStatus.CANCEL);
				investDao.update(invest);
				to.setStatus(TrusteeshipConstants.Status.PASSED);
				trusteeshipOperationService.update(to);
				return true;
			} else {
				to.setStatus(TrusteeshipConstants.Status.REFUSED);
				trusteeshipOperationService.update(to);
				return false;
			}
		} catch (Exception e) {
			log.infoLog(invest.getId() + "流标失败", e);
			e.printStackTrace();
			return false;
		} finally {
			postMethod.releaseConnection();
		}
	}
*/
	private boolean sendV2(Invest invest, String loanName)
			throws InsufficientBalance {
		StringBuffer content = new StringBuffer();
		content.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		// platformNo:商户编号
		content.append("<request platformNo=\""
				+ TrusteeshipYeepayConstants.Config.MER_CODE + "\">");

		content.append("<requestNo>" + invest.getId() + "</requestNo>");
		content.append("<mode>CANCEL</mode>");
		content.append("<notifyUrl>"
				+ TrusteeshipYeepayConstants.ResponseS2SUrl.PRE_RESPONSE_URL
				+ "complete_transaction_invest" + "</notifyUrl>");
		content.append("</request>");
		String xml = content.toString();
		/****************************************************/
		String type = TrusteeshipYeepayConstants.OperationType.INVEST;
		if (invest.getIsAutoInvest()) {
			type = TrusteeshipYeepayConstants.OperationType.AUTOINVEST;
		}
		String sign = CFCASignUtil.sign(xml);
		TrusteeshipOperation to = trusteeshipOperationService.read(type,
				invest.getId(), invest.getId(), "yeepay");
		to.setRequestData(to.getRequestData() + "\nsign=" + sign + ",req="
				+ xml);

		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("req", xml));
		params.add(new BasicNameValuePair("sign", sign));
		params.add(new BasicNameValuePair("service", "COMPLETE_TRANSACTION"));
		String responseBody = "";
		try {
			DRHTTPSClient.sendHTTPRequestPostToString(
					TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl,
					new BasicHeader[0], params);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		log.infoLog("单项目易宝流标请求返回数据", responseBody);
		Map<String, String> respMap = Dom4jUtil.xmltoMap(responseBody);
		String code = respMap.get("code");
		to.setResponseData(to.getResponseData() + responseBody);
		if (code.equals("1")) {
			try {
				cancelInvest(invest, to, loanName);
				return true;
			} catch (Exception e) {
				to.setStatus(TrusteeshipYeepayConstants.Status.REFUSED);
				trusteeshipOperationService.update(to);
				return false;
			}
		} else {
			to.setStatus(TrusteeshipYeepayConstants.Status.REFUSED);
			trusteeshipOperationService.update(to);
			return false;
		}
	}

	@Override
	public void cancelInvest(Invest invest, TrusteeshipOperation to,
			String loanName) throws InsufficientBalance {
		try {
			userAccountService.unfreeze(invest.getInvestUserID(),
					invest.getMoney(), BusinessEnum.bidders, "解冻：投资"
							+ invest.getLoan().getName(), "借款ID："
							+ invest.getLoan().getId(), invest.getId());
		} catch (NoOpenAccountException e) {
			log.errLog("流标失败", e);
			e.printStackTrace();
		} catch (InsufficientFreezeAmountException e) {
			log.errLog("流标失败", e);
			e.printStackTrace();
		}
		to.setStatus(TrusteeshipYeepayConstants.Status.PASSED);
		invest.setStatus(InvestConstants.InvestStatus.CANCEL);
		investDao.update(invest);
		if (invest.getRedpacketId() > 0) {
			RedPacket redpacket = redPacketDao.get(invest.getRedpacketId());
			if (redpacket != null && redpacket.getSendStatus().equals("used")) {
				if (DateUtil.calculateIntervalDays1(new Date(),
						redpacket.getDeadLine()) < 0) {
				} else {
					redpacket.setSendStatus("unused");
				}
				redPacketDao.update(redpacket);
			}
		}
		to.setStatus("cancel");
		trusteeshipOperationService.update(to);
	}
}
