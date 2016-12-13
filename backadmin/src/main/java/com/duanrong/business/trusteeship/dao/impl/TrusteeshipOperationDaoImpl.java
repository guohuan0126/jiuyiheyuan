package com.duanrong.business.trusteeship.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.sms.model.Sms;
import com.duanrong.business.trusteeship.dao.TrusteeshipOperationDao;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;

@Repository
public class TrusteeshipOperationDaoImpl extends
		BaseDaoImpl<TrusteeshipOperation> implements TrusteeshipOperationDao {

	public TrusteeshipOperationDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.trusteeship.mapper.TrusteeshipOperationMapper");
	}

	public void delete4Save(Map<String, Object> params) {
		this.getSqlSession().delete(this.getMapperNameSpace() + ".delete4Save",
				params);
	}

	public List<TrusteeshipOperation> get(String type, String markId,
			String operator, String trusteeship) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("markId", markId);
		params.put("operator", operator);
		params.put("trusteeship", trusteeship);
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getByCondition", params);
	}

	@Override
	public void updateBatch(Map<String, Object> params) {
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateBatch",
				params);
	}

	@Override
	public PageInfo<TrusteeshipOperation> pageInfo(int pageNo, int pageSize,
			Map map) {
		PageHelper.startPage(pageNo, pageSize);
		List<TrusteeshipOperation> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo", map);
		PageInfo<TrusteeshipOperation> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

}