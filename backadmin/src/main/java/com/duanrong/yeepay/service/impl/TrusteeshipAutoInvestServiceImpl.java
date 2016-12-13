package com.duanrong.yeepay.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import util.Log;
import util.MyStringUtils;
import base.exception.ExceedMoneyNeedRaised;
import base.exception.InsufficientBalance;

import com.duanrong.business.autoinvest.dao.AutoInvestDao;
import com.duanrong.business.autoinvest.model.AutoInvest;
import com.duanrong.business.followInvestAward.service.FollowInvestAwardService;
import com.duanrong.business.invest.InvestConstants;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.loan.LoanConstants;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.service.LoanService;
import com.duanrong.business.sms.SmsConstants;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.Dom4jUtil;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.client.DRHTTPSClient;
import com.duanrong.util.jedis.DRJedisMQ;
import com.duanrong.util.json.FastJsonUtil;
import com.duanrong.yeepay.service.TrusteeshipAutoInvestService;
import com.duanrong.yeepaysign.CFCASignUtil;

/**
 * 账户查询
 * 
 * @author Lin Zhiming
 * @version Mar 2, 2015 10:53:28 AM
 */
@Service
public class TrusteeshipAutoInvestServiceImpl implements
		TrusteeshipAutoInvestService {

	@Resource
	AutoInvestDao autoInvestDao;

	@Resource
	InvestService investService;

	@Resource
	LoanService loanService;

	@Resource
	TrusteeshipOperationService trusteeshipOperationService;

	@Resource
	FollowInvestAwardService followInvestAwardService;

	@Resource
	UserMoneyService userMoneyService;

	@Resource
	SmsService smsService;

	@Resource
	Log log;


	@Override
	public void autoInvestV2(AutoInvest autoInvest, Loan loan,
			double investMoney) throws HttpException, IOException,
			InsufficientBalance, ExceedMoneyNeedRaised {
		// 1.只有筹款中和贷前公告可以投资
		if (LoanConstants.LoanStatus.RAISING.equals(loan.getStatus())
				|| LoanConstants.LoanStatus.DQGS.equals(loan.getStatus())) {
			// 2.创建一笔投资并验证投资参数
			Invest invest = new Invest();
			try {
				invest.setInvestUserID(autoInvest.getUserId());
				invest.setIsAutoInvest(autoInvest.isAutoInvest());
				invest.setLoanId(loan.getId());
				invest.setMoney(investMoney);
				investService.create(invest);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

			// 计算抽成百分比
			double totalMoney = loan.getTotalmoney();
			double guranteeFee = loan.getLoanGuranteeFee();
			invest.setManagementExpense(0);
			if (guranteeFee > 0) {
				double fee = ArithUtil.round(investMoney
						* (guranteeFee / totalMoney), 2, BigDecimal.ROUND_DOWN);
				invest.setManagementExpense(fee <= 0 ? 0 : fee);
			}
			// 3.插入投资记录，状态为等待确认
			investService.syncInvest(invest, "create");

			// 4.拼接请求字符串
			/*********************** XML拼接 ***********************/
			StringBuffer xml = new StringBuffer();
			xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
			xml.append("<request platformNo=\""
					+ TrusteeshipYeepayConstants.Config.MER_CODE + "\">");
			xml.append("<requestNo>" + invest.getId() + "</requestNo>");
			xml.append("<platformUserNo>" + invest.getInvestUserID()
					+ "</platformUserNo>");
			xml.append("<userType>MEMBER</userType>");
			xml.append("<bizType>TENDER</bizType>");
			xml.append("<details>");
			xml.append("<detail>");
			xml.append("<targetUserType>MEMBER</targetUserType>");// 收款人类型（个人会员）
			xml.append("<targetPlatformUserNo>"
					+ loan.getBorrowMoneyUserID().trim()
					+ "</targetPlatformUserNo>");// 收款人ID
			xml.append(MyStringUtils.append("<amount>", ArithUtil.round(
					invest.getMoney() - invest.getManagementExpense(), 2),
					"</amount>"));// 转入金额
			xml.append("<bizType>TENDER</bizType>");
			xml.append("</detail>");
			xml.append("<detail>");
			xml.append("<targetUserType>MERCHANT</targetUserType>");// 收款人类型（个人会员）
			xml.append("<targetPlatformUserNo>"
					+ TrusteeshipYeepayConstants.Config.MER_CODE
					+ "</targetPlatformUserNo>");// 平台ID
			xml.append(MyStringUtils.append("<amount>",
					ArithUtil.round(invest.getManagementExpense(), 2),
					"</amount>"));// 转入金额
			xml.append("<bizType>COMMISSION</bizType>");
			xml.append("</detail>");
			xml.append("</details>");
			xml.append("<extend>");
			xml.append("<property name=\"tenderOrderNo\" value=\"")
					.append(loan.getId()).append("\" />");
			xml.append("<property name=\"tenderName\" value=\"")
					.append(loan.getName()).append("\" />");
			xml.append("<property name=\"tenderAmount\" value=\"")
					.append(loan.getTotalmoney()).append("\" />");
			xml.append("<property name=\"tenderDescription\" value=\""
					+ loan.getName() + loan.getRepayType() + "\" />");
			xml.append("<property name=\"borrowerPlatformUserNo\" value=\"")
					.append(loan.getBorrowMoneyUserID().trim()).append("\" />");
			xml.append("</extend>");
			xml.append("<notifyUrl>"
					+ TrusteeshipYeepayConstants.ResponseS2SUrl.PRE_RESPONSE_URL
					+ TrusteeshipYeepayConstants.OperationType.AUTOINVEST
					+ "</notifyUrl>");
			xml.append("</request>");
			String content = xml.toString();

			// 创建交易记录对象
			TrusteeshipOperation to = new TrusteeshipOperation();
			to.setId(IdGenerator.randomUUID());
			to.setMarkId(invest.getId());
			to.setOperator(invest.getId());
			to.setRequestUrl(TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
			to.setRequestTime(new Date());
			to.setStatus(TrusteeshipYeepayConstants.Status.SENDED);
			to.setType(TrusteeshipYeepayConstants.OperationType.AUTOINVEST);
			to.setUserId(autoInvest.getUserId());
			to.setTrusteeship("yeepay");
			try {
				// 获取签名加密
				String sign = CFCASignUtil.sign(content);
				to.setRequestData("sign=" + sign + ",req=" + content); // 交易xml
				trusteeshipOperationService.insert(to);
				log.infoLog("自动投标XML", content);
				log.infoLog("自动投标sign", sign);
				// 5.创建直连请求，请求易宝接口
				List<NameValuePair> params = new ArrayList<>();
				params.add(new BasicNameValuePair("req", content));
				params.add(new BasicNameValuePair("sign", sign));
				params.add(new BasicNameValuePair("service", "AUTO_TRANSACTION"));
				String responseBody = DRHTTPSClient
						.sendHTTPRequestPostToString(
								TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl,
								new BasicHeader[0], params);
				log.infoLog(autoInvest.getUserId() + "自动投标", responseBody);
				to.setResponseData(responseBody);
				to.setResponseTime(new Date());
				if(responseBody != null && responseBody.trim().length() > 0){
					@SuppressWarnings("unchecked")
					Map<String, String> respMap = Dom4jUtil.xmltoMap(responseBody);
					String code = respMap.get("code");			
					// 6.判断参数
					if ("1".equals(code)) {
						try {
							// 成功后本地处理
							// 更新自动投标队列
							autoInvest.setLastAutoInvestTime(new Date());
							autoInvestDao.update(autoInvest);
							// 更新 invest 表状态
							if (InvestConstants.InvestStatus.WAIT_AFFIRM
									.equals(invest.getStatus())) {
								invest.setStatus(InvestConstants.InvestStatus.BID_SUCCESS);
								investService.update(invest);
								
								
								try{
									Map<String, Object> map = new HashMap<>();
									
									map.put("investId", invest.getId());
									map.put("loanId", invest.getLoanId());
									map.put("pushTime", new Date());			
									DRJedisMQ.product("pushinvest", FastJsonUtil.objToJson(map));
									
									/**
									 * 十一随即返现活动
									 */
									Date investTime = invest.getTime();
									SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");								
									Date startDate = format.parse("2016-09-26 23:59:59");
									Date endDate = format.parse("2016-10-03 23:59:59");
									
									if (investTime != null && startDate.getTime() < investTime.getTime()
											&& investTime.getTime() < endDate.getTime()) {
										DRJedisMQ
										.product("national_invest", invest.getId());
									}
								}catch(Exception e){
									log.errLog("pushInvest", e);		
								}	
								to.setStatus(TrusteeshipYeepayConstants.Status.PASSED);
								trusteeshipOperationService.update(to);
								if (loan.getOperationType().equals("月")) {
									// 7.发送短信和站内信
									smsService.sendSms(invest.getInvestUserID(),"自动投标系统已为您投中"+ investMoney+ "元，"
									+ loan.getName()+ "，"+ loan.getDeadline()+ "个月，年化利率"+ ArithUtil.round(loan.getRate() * 100,2) + "%。",
									SmsConstants.INVEST);
								} else {
									smsService.sendSms(invest.getInvestUserID(),"自动投标系统已为您投中"+ investMoney+ "元，"
									+ loan.getName()+ "，"+ loan.getDay()+ "天，年化利率"+ ArithUtil.round(loan.getRate() * 100,2) + "%",
									SmsConstants.INVEST);
								}
							}
						} catch (Exception e) {
							to.setStatus(TrusteeshipYeepayConstants.Status.SENDED);
						}
					}
				}else{
					to.setStatus(TrusteeshipYeepayConstants.Status.SENDED);
				}
			} catch (Exception e) {
				log.errLog("自动投标", e);
				// 异常捕获，失败处理
				/*if (invest.getStatus().equals(
						InvestConstants.InvestStatus.WAIT_AFFIRM)) {
					// 改项目状态，项目如果是等待复核的状态，改为募集中
					investService.syncInvest(invest, "S2SFail");
					to.setStatus(TrusteeshipYeepayConstants.Status.REFUSED);					
				}*/
			}
			trusteeshipOperationService.update(to);
		}
	}

	@Override
	public void S2SCallback(HttpServletRequest request,
			HttpServletResponse response) {
		String notifyXML = request.getParameter("notify");

		/********** 参数解析和对象获取 **********/
		Map<String, String> resultMap = Dom4jUtil.xmltoMap(notifyXML);
		String code = resultMap.get("code");
		String requestNo = resultMap.get("requestNo");
		TrusteeshipOperation to = trusteeshipOperationService.read("auto_invest",
				requestNo, requestNo, "yeepay");
		if(to == null) to = new TrusteeshipOperation();
		to.setResponseData(notifyXML);
		to.setResponseTime(new Date());
		Invest invest = investService.read(requestNo);
		// 6.判断参数
		if ("1".equals(code)) {
			// 成功后本地处理
			// 更新自动投标队列
			AutoInvest autoInvest = autoInvestDao.get(invest.getInvestUserID());
			autoInvest.setLastAutoInvestTime(new Date());
			autoInvestDao.update(autoInvest);
			// 更新 invest 表状态
			if (InvestConstants.InvestStatus.WAIT_AFFIRM.equals(invest
					.getStatus())) {
				invest.setStatus(InvestConstants.InvestStatus.BID_SUCCESS);
				investService.update(invest);
				to.setStatus(TrusteeshipYeepayConstants.Status.PASSED);
				trusteeshipOperationService.update(to);
				Loan loan = invest.getLoan();
				try {
					if (loan.getOperationType().equals("月")) {
						// 7.发送短信和站内信
						smsService.sendSms(
								invest.getInvestUserID(),
								"自动投标系统已为您投中"
										+ invest.getMoney()
										+ "元，"
										+ loan.getName()
										+ "，"
										+ loan.getDeadline()
										+ "个月，年化利率"
										+ ArithUtil.round(loan.getRate() * 100,
												2) + "%。", SmsConstants.INVEST);
					} else {
						smsService.sendSms(
								invest.getInvestUserID(),
								"自动投标系统已为您投中"
										+ invest.getMoney()
										+ "元，"
										+ loan.getName()
										+ "，"
										+ loan.getDay()
										+ "天，年化利率"
										+ ArithUtil.round(loan.getRate() * 100,
												2) + "%", SmsConstants.INVEST);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			// 失败后的本地处理
			if (invest.getStatus().equals(
					InvestConstants.InvestStatus.WAIT_AFFIRM)) {
				// 改项目状态，项目如果是等待复核的状态，改为募集中
				try {
					investService.syncInvest(invest, "S2SFail");
				} catch (ExceedMoneyNeedRaised e) {
					e.printStackTrace();
				} catch (InsufficientBalance e) {
					e.printStackTrace();
				}
				to.setStatus(TrusteeshipYeepayConstants.Status.REFUSED);
				trusteeshipOperationService.update(to);
			}
		}
	}

}