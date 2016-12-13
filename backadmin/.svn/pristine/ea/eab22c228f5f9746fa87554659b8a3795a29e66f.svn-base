package com.duanrong.business.trusteeship.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.trusteeship.model.TrusteeshipOperation;

public interface TrusteeshipOperationDao extends BaseDao<TrusteeshipOperation> {

	/**
	 * 保存时对已存在的数据进行删除
	 * 
	 * @param map
	 */
	public void delete4Save(Map<String, Object> params);

	public List<TrusteeshipOperation> get(String type, String markId,
			String operator, String trusteeship);

	/**
	 * 批量更新
	 * 
	 * @param tos
	 */
	public void updateBatch(Map<String, Object> params);
	PageInfo<TrusteeshipOperation> pageInfo(int pageNo, int pageSize, Map map);
}
