package com.duanrong.drpay.business.trusteeship.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.drpay.business.trusteeship.dao.TrusteeshipOperationDao;
import com.duanrong.drpay.business.trusteeship.model.TrusteeshipNotify;
import com.duanrong.drpay.business.trusteeship.model.TrusteeshipOperation;

@Repository
public class TrusteeshipOperationDaoImpl extends
		BaseDaoImpl<TrusteeshipOperation> implements TrusteeshipOperationDao {

	public TrusteeshipOperationDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.trusteeship.mapper.TrusteeshipOperationMapper");
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
	public void insertNotify(TrusteeshipNotify notify) {
		this.getSqlSession().insert(this.getMapperNameSpace() + ".insertNotify",
				notify);
	}

	

}