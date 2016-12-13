package com.duanrong.yeepay.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
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

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.paymentInstitution.model.CompanyYeepayTransferUserYeepay;
import com.duanrong.business.paymentInstitution.model.PaymentRechargeRecord;
import com.duanrong.business.paymentInstitution.service.PaymentCompanyService;
import com.duanrong.business.platformtransfer.dao.PlatformTransferDao;
import com.duanrong.business.platformtransfer.model.PlatformTransfer;
import com.duanrong.business.recharge.service.RechargeService;
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
import com.duanrong.util.MapUtil;
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
	UserAccountService userAccountService;

	@Autowired
	InformationService informationService;

	@Resource
	PaymentCompanyService paymentCompanyService;

	@Resource
	RechargeService rechargeService;

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
			User user = userService.read(userId);
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
		platformTransfer.setOrderId(Id);
		platformTransfer.setLoanId(loanId);
		platformTransfer.setRepayId(repayId);
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
		TrusteeshipOperation to = trusteeshipOperationService.read(
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
			 */
			if (platformTransfer != null
					&& platformTransfer.getStatus().equals("等待平台划款")) {
				// 往UserBill中插入值并计算余额
				userAccountService.ptTransferIn(platformTransfer.getUsername(),
						platformTransfer.getActualMoney(), BusinessEnum.reward,
						"平台奖励", platformTransfer.getRemarks(),
						platformTransfer.getOrderId());
			}
			// 向用户发送消息
			informationService.sendInformation(platformTransfer.getUsername(),
					"平台奖励已发放", platformTransfer.getRemarks());

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
		xml.append("<notifyUrl>"
				+ TrusteeshipYeepayConstants.ResponseS2SUrl.PRE_RESPONSE_URL
				+ "/trans.do</notifyUrl>");
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
			String des = resultMap.get("description");
			if (code.equals("1")) {
				return "seccess";
			} else {
				return "fail";
			}
		} else {
			return "reqError";
		}
	}

	@Override
	public boolean supplementOrder(PaymentRechargeRecord record) {
		log.infoLog("第三方支付", "易宝平台转账开始，" + record.toString());
		/*********************** XML拼接 ***********************/
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		// 商户编号:商户在易宝唯一标识
		xml.append("<request platformNo=\""
				+ TrusteeshipYeepayConstants.Config.MER_CODE + "\">");
		// 请求流水号:接入方必须保证参数内的 requestNo 全局唯一，并且需要保存，留待后续业务使用
		xml.append("<requestNo>" + record.getMarkId() + "</requestNo>");
		// 商户平台会员标识:会员在商户平台唯一标识
		xml.append("<platformUserNo>"
				+ TrusteeshipYeepayConstants.Config.MER_CODE
				+ "</platformUserNo>");
		xml.append("<userType>MERCHANT</userType>");
		xml.append("<bizType>TRANSFER</bizType>");
		xml.append("<details>");
		xml.append("<detail>");
		xml.append("<targetUserType>MEMBER</targetUserType>");
		xml.append("<targetPlatformUserNo>" + record.getUserId()
				+ "</targetPlatformUserNo>");
		xml.append("<amount>" + record.getMoney() + "</amount>");
		xml.append("<bizType>TRANSFER</bizType>");
		xml.append("</detail>");
		xml.append("</details>");
		// 服务器通知 URL
		xml.append("<notifyUrl>"
				+ new StringBuffer(
						TrusteeshipYeepayConstants.ResponseS2SUrl.PRE_RESPONSE_URL)
						.append(TrusteeshipYeepayConstants.OperationType.DIRECT_TRANSACTION)
						.append(".do") + "</notifyUrl>");
		xml.append("</request>");

		String content = xml.toString();

		/*********************** 生成签名 ***********************/
		String sign = CFCASignUtil.sign(content);

		// 包装参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("req", content);
		params.put("sign", sign);

		log.infoLog("直接转账XML", content);
		log.infoLog("直接转账sign", sign);

		/*********************** 发送请求 ***********************/
		HttpClient client = new HttpClient();
		/* 创建一个post方法 */
		PostMethod postMethod = new PostMethod(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
		postMethod.setParameter("req", content.toString());
		postMethod.setParameter("sign", sign);
		postMethod.setParameter("service", "DIRECT_TRANSACTION");
		boolean flag = false;
		try {
			int statusCode = client.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				log.infoLog("TrusteeshipDirectTransactionService",
						"Method failed: " + postMethod.getStatusLine());
			}

			/*********************** 解析 ***********************/
			byte[] responseBody = postMethod.getResponseBody();
			@SuppressWarnings("unchecked")
			Map<String, String> respMap = Dom4jUtil.xmltoMap(new String(
					responseBody, "UTF-8"));
			String code = respMap.get("code");

			/*********************** 保存TO ***********************/

			String requestNo = record.getMarkId();
			Date date = new Date();
			TrusteeshipOperation to = new TrusteeshipOperation();
			to.setId(IdGenerator.randomUUID());
			to.setMarkId(requestNo);
			to.setOperator(requestNo);
			to.setRequestData(MapUtil.mapToString(params));
			to.setType("direct_transaction");
			to.setTrusteeship("JDpay");
			to.setRequestTime(date);
			to.setResponseData(new String(responseBody, "UTF-8"));
			log.infoLog("第三方支付", "易宝平台转账修改本地开始，TO表：" + to.getId());
			CompanyYeepayTransferUserYeepay ctu = new CompanyYeepayTransferUserYeepay();
			ctu.setId(IdGenerator.randomUUID());
			ctu.setRequetsNo(requestNo);
			ctu.setPaymentNo(requestNo);
			ctu.setMoney(record.getMoney());
			ctu.setUserId(record.getUserId());
			ctu.setPayMentId(record.getPayMentId());
			ctu.setCreateTime(date);
			log.infoLog("第三方支付", "易宝平台转账修改本地开始，ctu表：" + ctu.getId());
			if ("1".equals(code)) {
				ctu.setStatus("平台划款成功");
				to.setStatus(TrusteeshipYeepayConstants.Status.PASSED);
				paymentCompanyService.insertYeepayTransferUser(ctu);
				trusteeshipOperationService.insert(to);
				rechargeService.rechargeSuccess(requestNo);
				flag = true;
			} else {
				ctu.setStatus("平台划款失败");
				ctu.setMsg(respMap.get("description"));
				to.setStatus(TrusteeshipYeepayConstants.Status.REFUSED);
				paymentCompanyService.insertYeepayTransferUser(ctu);
				trusteeshipOperationService.insert(to);
				log.infoLog("第三方支付", "平台划款失败,code: " + code
						+ " , responseParam: "
						+ new String(responseBody, "UTF-8"));
			}
		} catch (Exception e) {
			log.errLog("第三方支付", "平台划款失败,record:" + record.toString()
					+ "Exception:" + e);
		} finally {
			postMethod.releaseConnection();
		}
		return flag;
	}

}
