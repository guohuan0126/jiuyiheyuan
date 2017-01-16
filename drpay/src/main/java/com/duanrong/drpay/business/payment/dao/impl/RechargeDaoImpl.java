package com.duanrong.drpay.business.payment.dao.impl;


import base.dao.impl.BaseDaoImpl;
import com.duanrong.drpay.business.payment.dao.RechargeDao;
import com.duanrong.drpay.business.payment.model.Recharge;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RechargeDaoImpl extends BaseDaoImpl<Recharge> implements
		RechargeDao {

	public RechargeDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.payment.mapper.RechargeMapper");
	}

	@Override
	public List<Recharge> getByCondition(Recharge recharge) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getByCondition", recharge);
	}

	@Override
	public Double getTotalRecharge(Recharge recharge) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getTotalRecharge", recharge);
	}

	@Override
	public List<Recharge> findQuickRechargeForToday(String userId) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".findQuickRechargeForToday",
				userId);
	}

	@Override
	public List<Recharge> findQuickRechargeForMonth(String userId) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".findQuickRechargeForMonth",
				userId);
	}

	@Override
	public Recharge getWithLock(String id) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getWithLock", id);
	}

}
