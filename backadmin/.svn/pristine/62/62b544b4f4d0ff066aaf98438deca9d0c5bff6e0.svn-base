package com.duanrong.business.yeepay.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.service.LoanService;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.dao.RedPacketDao;
import com.duanrong.business.user.model.RedPacket;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.business.yeepay.service.FailLoanService;
import com.duanrong.newadmin.constants.InvestConstants;
import com.duanrong.newadmin.constants.LoanConstants;
import com.duanrong.newadmin.utility.Dom4jUtil;
import com.duanrong.util.DateUtil;
import com.duanrong.util.client.DRHTTPSClient;
import com.duanrong.yeepaysign.CFCASignUtil;

@Service
public class FailLoanServiceImpl implements FailLoanService {

	@Resource
	InvestDao investDao;
	@Resource
	UserMoneyService userMoneyService;
	@Resource
	InvestService investService;
	@Resource
	LoanService loanService;
	@Resource
	TrusteeshipOperationService trusteeshipOperationService;
	@Resource
	RedPacketDao redPacketDao;
	
	@Resource
	UserAccountService userAccountService;
	
	@Resource
	Log log;

	@Override
	public String failLoan(Invest invest) throws InsufficientBalance {	
		return sendV2(invest);
		
	}


	private String sendV2(Invest invest) throws InsufficientBalance {
		
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
		if (invest.getIsAutoInvest() != null && invest.getIsAutoInvest()) {
			type = TrusteeshipYeepayConstants.OperationType.AUTOINVEST;
		}
		String sign = CFCASignUtil.sign(content.toString());
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
			responseBody = DRHTTPSClient.sendHTTPRequestPostToString(
					TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl,
					new BasicHeader[0], params);
		} catch (IOException e) {
			e.printStackTrace();
			return "fail";
		}

		log.infoLog("单项目易宝流标请求返回数据", responseBody);
		Map<String, String> respMap = Dom4jUtil.xmltoMap(responseBody);
		String code = respMap.get("code");
		to.setResponseData(to.getResponseData() + responseBody);
		if (code.equals("1")) {

			failLoan(invest, to);
		} else {
			to.setStatus(TrusteeshipYeepayConstants.Status.REFUSED);
			trusteeshipOperationService.update(to);
		}
		return "ok";
	}

	public String sendV2(String id){
		StringBuffer content = new StringBuffer();
		content.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		// platformNo:商户编号
		content.append("<request platformNo=\""
				+ TrusteeshipYeepayConstants.Config.MER_CODE + "\">");

		content.append("<requestNo>" + id + "</requestNo>");
		content.append("<mode>CANCEL</mode>");
		content.append("<notifyUrl>"
				+ TrusteeshipYeepayConstants.ResponseS2SUrl.PRE_RESPONSE_URL
				+ "complete_transaction_invest" + "</notifyUrl>");
		content.append("</request>");
		String xml = content.toString();
		/****************************************************/
		String sign = CFCASignUtil.sign(content.toString());
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("req", xml));
		params.add(new BasicNameValuePair("sign", sign));
		params.add(new BasicNameValuePair("service", "COMPLETE_TRANSACTION"));
		try {
			DRHTTPSClient.sendHTTPRequestPostToString(
					TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl,
					new BasicHeader[0], params);
		} catch (IOException e) {
			return "fail";
		}
		return "ok";
	}

	public void failLoan(Invest invest, TrusteeshipOperation to)
			throws InsufficientBalance {
		Loan loan = loanService.read(invest.getLoanId());
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
		String status = investDao.getLoanStatus(invest.getId());
		invest.setStatus(InvestConstants.InvestStatus.CANCEL);
		investService.update(invest);
		if (status.equals(LoanConstants.LoanStatus.RECHECK)) {
			loan.setStatus(LoanConstants.LoanStatus.RAISING);
			loanService.update(loan);
		}
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

	/**
	 * 本地流标
	 * @param invest
	 * @throws InsufficientBalance
	 */
	public void failLoan2(Invest invest)
			throws InsufficientBalance {
		Loan loan = loanService.read(invest.getLoanId());
		try {
			userAccountService.unfreeze(invest.getInvestUserID(),
					invest.getMoney(), BusinessEnum.bidders, "解冻：投资"
							+ loan.getName(), "借款ID："
							+ loan.getId(), invest.getId());
		} catch (NoOpenAccountException e) {
			log.errLog("流标失败", e);
			e.printStackTrace();
		} catch (InsufficientFreezeAmountException e) {
			log.errLog("流标失败", e);
			e.printStackTrace();
		}
		String status = investDao.getLoanStatus(invest.getId());
		invest.setStatus(InvestConstants.InvestStatus.CANCEL);
		investService.update(invest);
		if (status.equals(LoanConstants.LoanStatus.RECHECK)) {
			loan.setStatus(LoanConstants.LoanStatus.RAISING);
			loanService.update(loan);
		}
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
	}
}
