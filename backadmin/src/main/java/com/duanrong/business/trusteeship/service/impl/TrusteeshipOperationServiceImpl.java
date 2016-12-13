package com.duanrong.business.trusteeship.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.stereotype.Service;

import util.Log;
import util.MyCollectionUtils;
import util.XMLUtil;
import base.pagehelper.PageInfo;

import com.duanrong.business.trusteeship.dao.TrusteeshipOperationDao;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipQuery;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.util.Dom4jUtil;
import com.duanrong.yeepay.xml.GeneratorXML;

@Service
public class TrusteeshipOperationServiceImpl implements
		TrusteeshipOperationService {

	@Resource
	TrusteeshipOperationDao trusteeshipOperationDao;
	@Resource
	Log log;

	@Override
	public TrusteeshipOperation insert(TrusteeshipOperation to) {
		if (StringUtils.isBlank(to.getType())
				|| StringUtils.isBlank(to.getMarkId())
				|| StringUtils.isBlank(to.getOperator())
				|| StringUtils.isBlank(to.getTrusteeship())) {
			return null;
		}
		
		String createAccount = TrusteeshipYeepayConstants.OperationType.CREATE_ACCOUNT;
		String bindingYeepayBankcard = TrusteeshipYeepayConstants.OperationType.BINDING_YEEPAY_BANKCARD;
		String AutoInvesAUTHORIZEA = TrusteeshipYeepayConstants.OperationType.AUTHORIZEAUTOINVEST;
		String thansaction = TrusteeshipYeepayConstants.OperationType.TO_CP_TRANSACTION;
		// 开户和绑卡特殊处理，因为开户和绑卡的markid和operateid都是用户名，如果用户第二次发起相同的操作，那么之前的TO记录会被删除，只记录最后一次的记录。因此更改operateid和status，使记录不重复
		if (StringUtils.equals(to.getType(), bindingYeepayBankcard)) {
			dealToSameRecord(to, bindingYeepayBankcard);
		} else if (StringUtils.equals(to.getType(), createAccount)) {
			dealToSameRecord(to, createAccount);
		} else if (StringUtils.equals(to.getType(), AutoInvesAUTHORIZEA)) {
			dealToSameRecord(to, AutoInvesAUTHORIZEA);
		}else if (StringUtils.equals(to.getType(), thansaction)) {
			dealToSameRecord(to, thansaction);
		} else {
			this.delete4Save(to.getType(), to.getMarkId(), to.getOperator(),
					to.getTrusteeship());
		}

		trusteeshipOperationDao.insert(to);
		return to;
	}

	public void dealToSameRecord(TrusteeshipOperation to,
			String bindingYeepayBankcard) {
		List<TrusteeshipOperation> tos = trusteeshipOperationDao.get(
				bindingYeepayBankcard, to.getMarkId(), to.getOperator(),
				"yeepay");

		if (MyCollectionUtils.isNotBlank(tos)) {
			Map<String, Object> params = new HashMap<>();
			params.put("operator", "recode：" + to.getId());
			params.put("status", "recode：" + to.getId());
			params.put("tos", tos);
			trusteeshipOperationDao.updateBatch(params);
		}
	}

	public TrusteeshipOperation read(String trusteeshipOperationId) {
		return trusteeshipOperationDao.get(trusteeshipOperationId);
	}

	public TrusteeshipOperation read(String type, String markId,
			String operator, String trusteeship) {

		List<TrusteeshipOperation> tos = trusteeshipOperationDao.get(type,
				markId, operator, trusteeship);

		if (tos.size() > 1) {
			return null;
		}
		if (tos.size() == 0) {
			return null;
		}

		TrusteeshipOperation to = tos.get(0);
		return to;
	}

	public void update(TrusteeshipOperation to) {
		trusteeshipOperationDao.update(to);
	}

	public void delete4Save(String type, String markId, String operator,
			String trusteeship) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("markId", markId);
		params.put("operator", operator);
		params.put("trusteeship", trusteeship);
		trusteeshipOperationDao.delete4Save(params);
	}

	@Override
	public PageInfo<TrusteeshipOperation> readPageInfo(int pageNo,
			int pageSize, Map map) {
		return trusteeshipOperationDao.pageInfo(pageNo, pageSize, map);
	}
	public TrusteeshipQuery query(String number, String type) {
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);

		/*********************** XML拼接 ***********************/
		// 参数拼接
		GeneratorXML xml = new GeneratorXML();
		xml.setPlatformNo(TrusteeshipYeepayConstants.Config.MER_CODE);
		xml.setRequestNo(number);
		xml.setMode(type);

		String content = null;
		try {
			content = XMLUtil.getXML(xml);
		} catch (Exception e) {
			log.errLog("提现轮询XML拼接异常", e);
			return null;
		}

		/*********************** 生成签名 ***********************/
		String sign = com.duanrong.yeepaysign.CFCASignUtil.sign(content);
	
		postMethod.setParameter("req", content);
		postMethod.setParameter("sign", sign);
		postMethod.setParameter("service", "QUERY");
		TrusteeshipQuery query=new TrusteeshipQuery();
		/*********************** 执行POST ***********************/
		try {
			int statusCode = client.executeMethod(postMethod);
			if (HttpStatus.SC_OK != statusCode) {
				log.errLog("单笔业务查询异常", postMethod.getStatusLine()
						.toString());
			}
			// 易宝响应结果
			byte[] responseBody = postMethod.getResponseBody();
			String respInfo = new String(responseBody, "UTF-8");
			log.infoLog("单笔业务返回数据", number+":"+respInfo);
			// 响应信息
			Document respXML = DocumentHelper.parseText(respInfo);
			Map<String, String> resultMap = Dom4jUtil.xmltoMap(respInfo);
			String code = resultMap.get("code");
			String description = resultMap.get("description");
			query.setDescription(description);
		
			String records = resultMap.get("records");
			if ("1".equals(code) && StringUtils.isNotBlank(records)) {
				if(type.equals("RECHARGE_RECORD")){
					String status = respXML.selectSingleNode(
							"/response/records/record/status").getStringValue();
					String amount = respXML.selectSingleNode(
							"/response/records/record/amount").getStringValue();
					String userNo = respXML.selectSingleNode(
							"/response/records/record/userNo").getStringValue();
					String createTime = respXML.selectSingleNode(
							"/response/records/record/createTime").getStringValue();
					String payProduct = respXML.selectSingleNode(
							"/response/records/record/payProduct").getStringValue();
					query.setRamount(Double.parseDouble(amount));
					query.setRcreateTime(DateUtil.StringToDate(createTime));
					query.setRpayProduct(payProduct);
					query.setRstatus(status);
					query.setRuserNo(userNo);
					query.setType(type);
				}
				if(type.equals("WITHDRAW_RECORD")){
					String status = respXML.selectSingleNode(
							"/response/records/record/status").getStringValue();
					String amount = respXML.selectSingleNode(
							"/response/records/record/amount").getStringValue();
					String userNo = respXML.selectSingleNode(
							"/response/records/record/userNo").getStringValue();
					String createTime = respXML.selectSingleNode(
							"/response/records/record/createTime").getStringValue();
					String remitStatus = respXML.selectSingleNode(
							"/response/records/record/remitStatus").getStringValue();
					query.setWamount(Double.parseDouble(amount));
					query.setWcreateTime(DateUtil.StringToDate(createTime));
					query.setWremitStatus(remitStatus);
					query.setWstatus(status);
					query.setWuserNo(userNo);
					query.setType(type);
				}
				if(type.equals("CP_TRANSACTION")){
					String requestNo = respXML.selectSingleNode(
							"/response/records/record/requestNo").getStringValue();
					String bizType = respXML.selectSingleNode(
							"/response/records/record/bizType").getStringValue();
					String amount = respXML.selectSingleNode(
							"/response/records/record/amount").getStringValue();
					String status = respXML.selectSingleNode(
							"/response/records/record/status").getStringValue();
					String subStatus = respXML.selectSingleNode(
							"/response/records/record/subStatus").getStringValue();
					query.setTamount(Double.parseDouble(amount));
					query.setTbizType(bizType);
					query.setTrequestNo(requestNo);
					query.setTstatus(status);
					query.setTsubStatus(subStatus);
					query.setType(type);
				}
				if(type.equals("FREEZERE_RECORD")){
					String requestNo = respXML.selectSingleNode(
							"/response/records/record/requestNo").getStringValue();
					String platformUserNo = respXML.selectSingleNode(
							"/response/records/record/platformUserNo").getStringValue();
					String amount = respXML.selectSingleNode(
							"/response/records/record/amount").getStringValue();
					String expired = respXML.selectSingleNode(
							"/response/records/record/expired").getStringValue();
					String createTime = respXML.selectSingleNode(
							"/response/records/record/createTime").getStringValue();
					String status = respXML.selectSingleNode(
							"/response/records/record/status").getStringValue();
					query.setFamount(Double.parseDouble(amount));
					query.setFplatformUserNo(platformUserNo);
					query.setFrequestNo(requestNo);
					query.setFstatus(status);
					query.setFcreateTime(DateUtil.StringToDate(createTime));
					query.setFexpired(DateUtil.StringToDate(expired));
					query.setType(type);
				}
				
			}
			return query;
		} catch (Exception e) {
			log.errLog("单笔业务", e);
		} finally {
			postMethod.releaseConnection();
		}
		return query;
}
}