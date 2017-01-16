package com.duanrong.drpay.business.trusteeship.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;

import com.duanrong.drpay.business.trusteeship.model.TrusteeshipNotify;
import com.duanrong.drpay.business.trusteeship.model.TrusteeshipOperation;

public interface TrusteeshipOperationDao extends BaseDao<TrusteeshipOperation> {


	public List<TrusteeshipOperation> get(String type, String markId,
										  String operator, String trusteeship);

	/**
	 * 批量更新
	 * 
	 * @param tos
	 */
	public void updateBatch(Map<String, Object> params);

	/**
	 * 插入存管通知数据
	 * @param trusteeshipNotify
	 */
	void insertNotify(TrusteeshipNotify notify);
}
