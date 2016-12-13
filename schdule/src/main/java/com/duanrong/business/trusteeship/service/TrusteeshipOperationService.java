package com.duanrong.business.trusteeship.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import base.pagehelper.PageInfo;

import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipQuery;

//detele interface.
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
	@Transactional(rollbackFor = Exception.class)
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
	 * 保存时对已存在的数据进行删除
	 * 
	 * @param type
	 * @param markId
	 * @param operator
	 * @param trusteeship
	 */
	public void delete4Save(String type, String markId, String operator,
			String trusteeship);

	public PageInfo<TrusteeshipOperation> findPageInfo(int pageNo,
			int pageSize, Map map);

	public TrusteeshipQuery query(String number, String type);

	/**
	 * 获取minute分钟之前发出的且到目前为止尚未收到回调的所有请求
	 * 
	 * @param minute
	 *            请求距离目前的时间间隔（分钟）
	 * @return
	 */
	public List<TrusteeshipOperation> getUnCallbackOperations(int minute);
	
	public List<TrusteeshipOperation> getTransferOperations(int minute);
}