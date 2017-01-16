package com.duanrong.business.trusteeship.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.stereotype.Service;

import util.Log;
import util.MyCollectionUtils;
import util.XMLUtil;
import base.pagehelper.PageInfo;

import com.duanrong.business.trusteeship.dao.TrusteeshipOperationDao;
import com.duanrong.business.trusteeship.model.TrusteeshipConstants;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipQuery;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.util.Dom4jUtil;

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
		} else if (StringUtils.equals(to.getType(), thansaction)) {
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

	public TrusteeshipOperation get(String trusteeshipOperationId) {
		return trusteeshipOperationDao.get(trusteeshipOperationId);
	}

	public TrusteeshipOperation get(String type, String markId,
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
	public PageInfo<TrusteeshipOperation> findPageInfo(int pageNo,
			int pageSize, Map map) {
		return trusteeshipOperationDao.pageInfo(pageNo, pageSize, map);
	}

	public TrusteeshipQuery query(String number, String type) {
		
		return null;
	}
	
	@Override
	public List<TrusteeshipOperation> getUnCallbackOperations(int minute) {
		Date date = DateUtils.addMinutes(new Date(), -minute);
		TrusteeshipOperation trusteeshipOperation = new TrusteeshipOperation();
		trusteeshipOperation.setStatus(TrusteeshipConstants.Status.SENDED);
		trusteeshipOperation.setRequestTime(date);
		return trusteeshipOperationDao
				.getTO4automaticQuery(trusteeshipOperation);
	}
	
	@Override
	public List<TrusteeshipOperation> getTransferOperations(int minute) {
		Date date = DateUtils.addMinutes(new Date(), -minute);
		return trusteeshipOperationDao
				.getTOautomaticQuery(date);
	}
}