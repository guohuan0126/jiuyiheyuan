package com.duanrong.yeepay.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.Log;

import com.duanrong.business.platformtransfer.dao.PlatformTransferDao;
import com.duanrong.business.platformtransfer.model.PlatformTransfer;
import com.duanrong.business.trusteeship.model.TrusteeshipConstants;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.InformationService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.newadmin.utility.Dom4jUtil;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.IdUtil;
import com.duanrong.yeepay.service.TrusteeshipPlatformTransferService;
import com.duanrong.yeepaysign.CFCASignUtil;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2015-3-3 上午10:04:15
 * @Description : NewAdmin com.duanrong.business.platformtransfer.service.impl
 *              PlatformTransferServiceImpl.java
 * 
 */
@Service
public class TrusteeshipPlatformTransferServiceImpl implements
		TrusteeshipPlatformTransferService {

	private final Lock lock = new ReentrantLock();

	@Autowired
	UserService userService;

	@Autowired
	PlatformTransferDao platformTransferDao;

	@Autowired
	TrusteeshipOperationService trusteeshipOperationService;

	@Autowired
	InformationService informationService;

	@Resource
	Log log;

	@Override
	public String platformTransferTrusteeship(String Id, String userId,
			Double actualMoney, String remarks, String type, String loanId,
			String repayId) {
		lock.lock();
		String platformTransferId = null;
		PlatformTransfer platformTransfer = new PlatformTransfer();
		try {
			// 判断用户是否存在
			User user = userService.get(userId);
			if (user == null) {
				return "平台划款未找到该用户userId:" + userId;
			}
			if (actualMoney == null || actualMoney <= 0) {
				return "平台划款金额不正确userId:" + userId + "金额:" + actualMoney;
			}
			platformTransfer = createPlatformTransfer(Id, user.getUserId(),
					actualMoney, remarks, type, loanId, repayId);
			platformTransferId = platformTransfer.getId();
			String ptoResult = createPlatformTransferOrder(platformTransfer);
			if ("success".equals(ptoResult)) {
				platformTransfer.setStatus("平台划款成功");
				platformTransfer.setSuccessTime(new Date());
			} else {
				platformTransfer.setStatus(ptoResult);
			}
			platformTransferDao.update(platformTransfer);
			return ptoResult;
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog("平台划款异常:" + platformTransferId, e);
			platformTransfer.setStatus("平台划款异常");
			platformTransferDao.update(platformTransfer);
			return "平台划款异常!" + platformTransferId;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 
	 * @description 保存一条平台划款记录
	 * @author 孙铮
	 * @time 2015-3-3 上午10:32:39
	 * @param userId
	 *            用户id
	 * @param actualMoney
	 *            划款金额
	 * @param remarks
	 *            备注
	 * @return
	 */
	private PlatformTransfer createPlatformTransfer(String Id, String userId,
			Double actualMoney, String remarks, String type, String loanId,
			String repayId) throws Exception {
		PlatformTransfer platformTransfer = new PlatformTransfer();
		platformTransfer.setUsername(userId);
		platformTransfer.setActualMoney(actualMoney);
		platformTransfer.setRemarks(remarks);
		platformTransfer.setId(IdUtil.randomUUID());
		platformTransfer.setStatus("等待平台划款");
		platformTransfer.setTime(new Date());
		platformTransfer.setType(type);
		platformTransfer.setLoanId(loanId);
		platformTransfer.setRepayId(repayId);
		platformTransfer.setOrderId(Id);
		platformTransferDao.insert(platformTransfer);
		return platformTransfer;
	}

	/**
	 * 
	 * @description 创建平台划款订单并发送
	 * @author 孙铮
	 * @time 2015-3-3 上午10:34:57
	 * @param platformTransfer
	 * @throws IOException
	 * @throws HttpException
	 */
	private String createPlatformTransferOrder(PlatformTransfer platformTransfer)
			throws Exception {
		String id = platformTransfer.getOrderId();
		String username = platformTransfer.getUsername();
		Double actualMoney = platformTransfer.getActualMoney();

		/*********************** XML拼接 ***********************/
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		xml.append("<request platformNo=\""
				+ TrusteeshipYeepayConstants.Config.MER_CODE + "\">");
		xml.append("<requestNo>" + id + "</requestNo>");
		xml.append("<userType>MERCHANT</userType>");
		xml.append("<bizType>TRANSFER</bizType>");
		xml.append("<platformUserNo>"
				+ TrusteeshipYeepayConstants.Config.MER_CODE
				+ "</platformUserNo>");
		xml.append("<details>");
		xml.append("<detail>");
		xml.append("<targetUserType>MEMBER</targetUserType>");
		xml.append("<targetPlatformUserNo>" + username
				+ "</targetPlatformUserNo>");
		xml.append("<amount>" + actualMoney + "</amount>");
		xml.append("<bizType>TRANSFER</bizType>");
		xml.append("</detail>");
		xml.append("</details>");
		xml.append("<notifyUrl>"
				+ TrusteeshipYeepayConstants.ResponseS2SUrl.PRE_RESPONSE_URL
				+ TrusteeshipYeepayConstants.OperationType.PLATFORM_TRANSFER
				+ "</notifyUrl>");
		xml.append("</request>");

		/*********************** 生成签名 ***********************/
		String sign = CFCASignUtil.sign(xml.toString());
		if (sign == null) {
			log.infoLog("平台划款生成签名失败", "sign:" + sign + "平台划款id:" + id
					+ "用户名username:" + username);
			return "平台划款生成签名失败sign:" + sign + "平台划款id:" + id + "用户名username:"
					+ username;
		}

		/*********************** 创建post方法 ***********************/
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
		postMethod.setParameter("req", xml.toString());
		postMethod.setParameter("service", "DIRECT_TRANSACTION");
		postMethod.setParameter("sign", sign);

		/*********************** 保存TO ***********************/
		TrusteeshipOperation to = new TrusteeshipOperation();
		to.setId(IdGenerator.randomUUID());
		to.setMarkId(id);
		to.setOperator(id);
		to.setRequestUrl(TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl
				+ TrusteeshipYeepayConstants.OperationType.PLATFORM_TRANSFER);
		to.setRequestData(xml.toString());
		to.setType(TrusteeshipYeepayConstants.OperationType.PLATFORM_TRANSFER);
		to.setTrusteeship("yeepay");
		to.setRequestTime(new Date());
		to.setStatus(TrusteeshipYeepayConstants.Status.SENDED);
		// 需要设置userId，进行记录
		to.setUserId(username);
		trusteeshipOperationService.insert(to);

		/*********************** 执行发送 ***********************/
		int statusCode = client.executeMethod(postMethod);
		/*********************** 处理结果 ***********************/
		if (statusCode == HttpStatus.SC_OK) {
			return platformTransferPostCallback(postMethod, id, actualMoney,
					username, platformTransfer);
		} else {
			log.infoLog("平台划款返回状态吗错误", "statusCode:" + statusCode + "平台划款id:"
					+ id + "用户名username:" + username);
			return "平台划款返回状态吗错误statusCode:" + statusCode + "平台划款id:" + id
					+ "用户名username:" + username;
		}
	}

	@SuppressWarnings("unchecked")
	private String platformTransferPostCallback(PostMethod postMethod,
			String requestNo, Double money, String username,
			PlatformTransfer platformTransfer) throws Exception {
		byte[] responseBody = postMethod.getResponseBody();
		Map<String, String> resultMap = Dom4jUtil.xmltoMap(new String(
				responseBody, "UTF-8"));

		StringBuffer sb = new StringBuffer();
		String platformNo = resultMap.get("platformNo");
		sb.append(platformNo).append(",");
		String code = resultMap.get("code");
		sb.append(code).append(",");
		String description = resultMap.get("description");
		sb.append(description);

		// to表对应的平台划款记录
		TrusteeshipOperation to = trusteeshipOperationService.get(
				TrusteeshipYeepayConstants.OperationType.PLATFORM_TRANSFER,
				requestNo, requestNo, "yeepay");
		// 设置响应时间和参数
		to.setResponseTime(new Date());
		to.setResponseData(sb.toString());
		return platformTransferStatus(code, to, platformTransfer.getId());
	}

	private String platformTransferStatus(String code, TrusteeshipOperation to,
			String platformTransferId) throws Exception {
		// 本地平台划款记录
		String result = null;
		PlatformTransfer platformTransfer = platformTransferDao
				.get(platformTransferId);
		if ("1".equals(code)) {// 平台还款成功

			/**
			 * 修改UserBill表 补息和红包在外层处理
			 * 已放到外层
			 */
			// 设置用户操作状态,是否成功,成功修改操作状态
			to.setStatus(TrusteeshipConstants.Status.PASSED);
			result = "success";
		} else {// 平台划款失败
			platformTransfer.setStatus("平台划款失败:" + to.getResponseData());
			to.setStatus(TrusteeshipConstants.Status.REFUSED);
			result = "平台划款失败:" + to.getResponseData() + "易宝返回code" + code;
		}
		trusteeshipOperationService.update(to);
		return result;
	}

	@Override
	public String confirmTransferTrusteeship(String reqNo, String type)
			throws Exception {

		/*********************** XML拼接 ***********************/
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		xml.append("<request platformNo=\""
				+ TrusteeshipYeepayConstants.Config.MER_CODE + "\">");
		xml.append("<requestNo>" + reqNo + "</requestNo>");
		xml.append("<mode>" + type + "</mode>");
		xml.append("<notifyUrl>http://demoadmin.duanrong.net/trusteeship_return_s2s/trans</notifyUrl>");
		xml.append("</request>");
		/*********************** 生成签名 ***********************/
		String sign = CFCASignUtil.sign(xml.toString());

		/*********************** 创建post方法 ***********************/
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
		postMethod.setParameter("req", xml.toString());
		postMethod.setParameter("service", "COMPLETE_TRANSACTION");
		postMethod.setParameter("sign", sign);

		/*********************** 执行发送 ***********************/
		int statusCode = client.executeMethod(postMethod);
		/*********************** 处理结果 ***********************/
		if (statusCode == HttpStatus.SC_OK) {
			byte[] responseBody = postMethod.getResponseBody();
			Map<String, String> resultMap = Dom4jUtil.xmltoMap(new String(
					responseBody, "UTF-8"));
			String code = resultMap.get("code");
			if (code.equals("1")) {
				return "seccess";
			} else {
				return "fail";
			}
		} else {
			return "reqError";
		}
	}

}
