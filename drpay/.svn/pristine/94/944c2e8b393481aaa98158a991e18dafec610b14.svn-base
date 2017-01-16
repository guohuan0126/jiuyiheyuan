package com.duanrong.drpay.business.trusteeship.service;

import com.duanrong.drpay.business.trusteeship.model.TrusteeshipNotify;
import com.duanrong.drpay.business.trusteeship.model.TrusteeshipOperation;

public interface TrusteeshipOperationService {

	/**
	 * 保存
	 * 
	 * @param to
	 * @return
	 */
	public TrusteeshipOperation insert(TrusteeshipOperation to);

	/**
	 * 根据id查询
	 * 
	 * @param trusteeshipOperationId
	 * @return
	 */
	public TrusteeshipOperation get(String trusteeshipOperationId);

	/**
	 * 查询
	 * 
	 * @param type
	 * @param markId
	 * @param operator
	 * @param trusteeship
	 * @return
	 */
	public TrusteeshipOperation get(String type, String markId,
									  String operator, String trusteeship);

	/**
	 * 更新
	 * 
	 * @param to
	 */
	public void update(TrusteeshipOperation to);

	/**
	 * 添加存管异步通知记录
	 * @param notify
	 */
	void insertNotify(TrusteeshipNotify notify);

}