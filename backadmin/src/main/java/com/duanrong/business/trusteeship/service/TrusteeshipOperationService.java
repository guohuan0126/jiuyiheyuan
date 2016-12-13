package com.duanrong.business.trusteeship.service;

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
	public TrusteeshipOperation read(String trusteeshipOperationId);

	/**
	 * 查询
	 * 
	 * @param type
	 * @param markId
	 * @param operator
	 * @param trusteeship
	 * @return
	 */
	public TrusteeshipOperation read(String type, String markId,
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
	public PageInfo<TrusteeshipOperation> readPageInfo(int pageNo, int pageSize, Map map);
	public TrusteeshipQuery query(String number, String type);

}