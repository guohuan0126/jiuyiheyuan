package com.duanrong.drpay.business.trusteeship.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.duanrong.drpay.business.trusteeship.TrusteeshipConstants;
import com.duanrong.drpay.business.trusteeship.dao.TrusteeshipOperationDao;
import com.duanrong.drpay.business.trusteeship.model.TrusteeshipNotify;
import com.duanrong.drpay.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.drpay.business.trusteeship.service.TrusteeshipOperationService;

@Service
public class TrusteeshipOperationServiceImpl implements
		TrusteeshipOperationService {

	@Resource
	TrusteeshipOperationDao trusteeshipOperationDao;

	@Override
	public TrusteeshipOperation insert(TrusteeshipOperation to) {
		if (StringUtils.isBlank(to.getType())
				|| StringUtils.isBlank(to.getMarkId())
				|| StringUtils.isBlank(to.getOperator())
				|| StringUtils.isBlank(to.getTrusteeship())) {
			return null;
		}
		dealToSameRecord(to);
		trusteeshipOperationDao.insert(to);
		return to;
	}

	/**
	 * 如果markId和operator是唯一的，就将查询到的记录更新
	 */
	private void dealToSameRecord(TrusteeshipOperation to) {
		List<TrusteeshipOperation> tos = trusteeshipOperationDao.get(
				to.getType(), to.getMarkId(), to.getOperator(), "yeepay");
		if (tos != null && !tos.isEmpty()) {
			Map<String, Object> params = new HashMap<>();
			params.put("operator", TrusteeshipConstants.RECODE + ":" + to.getId());
			params.put("status", TrusteeshipConstants.RECODE + ":" + to.getId());
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
		if (!tos.isEmpty() && tos.size() == 1) {
			return tos.get(0);
		}
		return null;
	}

	public void update(TrusteeshipOperation to) {
		trusteeshipOperationDao.update(to);
	}

	@Override
	public void insertNotify(TrusteeshipNotify notify) {
		trusteeshipOperationDao.insertNotify(notify);
	}
}
