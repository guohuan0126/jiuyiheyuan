package com.duanrong.business.withdraw.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.withdraw.dao.WithdrawCashDao;
import com.duanrong.business.withdraw.model.WithdrawCash;

@Repository
public class WithdrawCashDaoImpl extends BaseDaoImpl<WithdrawCash> implements
		WithdrawCashDao {

	public WithdrawCashDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.withdraw.mapper.WithdrawCashMapper");
	}

	public List<WithdrawCash> getByCondition(WithdrawCash withdrawCash) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getByCondition", withdrawCash);
	}

	public Double getTotalWithdrawCash(WithdrawCash withdrawCash) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getTotalWithdrawCash",
				withdrawCash);
	}

	/**
	 * 查询用户的提现次数
	 * 
	 * @param withcash
	 * @return
	 */
	public long GetWithdrawCount(WithdrawCash withcash) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getCountWithdrawCash", withcash);
	}

	public Double getTotalFee(WithdrawCash withcash) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getTotalFee", withcash);
	}

	@Override
	public double getWithdrawMoneyPerDay(Map<String, Object> params) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getWithdrawMoneyPerDay", params);
	}

	@Override
	public List<WithdrawCash> getCashNum(Map map) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getCashNum", map);
	}

	/**
	 * 总提现金额[去除固定借款人]
	 */
	@Override
	public BigDecimal getExcludeFixedFee(WithdrawCash withdrawCash) {
		return this.getSqlSession().selectOne(
				getMapperNameSpace() + ".getExcludeFixedFee", withdrawCash);
	}

	@Override
	public Integer getHolidayDate(Date date, String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("date", date);
		param.put("type", type);
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getHolidayDate", param);
	}

	public int getExcludeFixedFeePeopleCount(WithdrawCash withdrawCash) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(
				getMapperNameSpace() + ".getExcludeFixedFeePeopleCount",
				withdrawCash);
	}

	@Override
	public WithdrawCash getWithLock(String id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(
				getMapperNameSpace() + ".getWithLock", id);
	}

}
