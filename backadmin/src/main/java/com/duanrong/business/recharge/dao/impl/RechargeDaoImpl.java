package com.duanrong.business.recharge.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.recharge.dao.RechargeDao;
import com.duanrong.business.recharge.model.Recharge;

@Repository
public class RechargeDaoImpl extends BaseDaoImpl<Recharge> implements RechargeDao {

	public RechargeDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.recharge.mapper.RechargeMapper");
	}

	public List<Recharge> getByCondition(Recharge recharge) {
		return this.getSqlSession().selectList(this.getMapperNameSpace() + ".getByCondition", recharge);
	}

	public Double getTotalRecharge(Recharge recharge) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getTotalRecharge", recharge);
	}

	@Override
	public Double getTotalFee(Recharge recharge) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getTotalFee", recharge);
	}

	@Override
	public double getRechargeMoneyPerDay(Map<String, Object> params) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getRechargeMoneyPerDay", params);
	}

	@Override
	public List<Recharge> getRechargeNum(Map map) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getRechargeNum", map);
	}

	@Override
	public void save(Recharge recharge) {
		this.getSqlSession().insert(this.getMapperNameSpace() + ".save", recharge);
	}

	/**
     * 【去除固定借款人】总充值金额
     * @param recharge
     * @return
     */
	@Override
	public BigDecimal getExcludeFixedBorrowerFee(Recharge recharge) {
		return this.getSqlSession().selectOne(getMapperNameSpace()+".getExcludeFixedBorrowerFee", recharge);
	}

	@Override
	public int getRechargeSuccessPeople(Recharge recharge) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(getMapperNameSpace()+".getRechargeSuccessPeople", recharge);
	}

	@Override
	public BigDecimal getExcludeFixedBorrowerFeeFail(Recharge recharge) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(getMapperNameSpace()+".getExcludeFixedBorrowerFeeFail", recharge);
	}

	@Override
	public int getRechargeFailPeople(Recharge recharge) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(getMapperNameSpace()+".getRechargeFailPeople", recharge);
	}

	@Override
	public Recharge getWithLock(String id) {
		// TODO Auto-generated method stub
				return this.getSqlSession().selectOne(getMapperNameSpace()+".getWithLock", id);

	}

}