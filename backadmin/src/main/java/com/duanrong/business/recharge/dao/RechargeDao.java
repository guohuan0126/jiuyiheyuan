package com.duanrong.business.recharge.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import base.dao.BaseDao;

import com.duanrong.business.recharge.model.Recharge;
import com.duanrong.business.sms.model.Sms;

/**
 * @Description: 充值
 * @Author: 林志明
 * @CreateDate: Sep 11, 2014
 */
public interface RechargeDao extends BaseDao<Recharge> {

	/**
	 * 根据条件进行组合查询
	 * 
	 * @return
	 */
	public List<Recharge> getByCondition(Recharge recharge);

	/**
	 * 查询（加锁 慎用）
	 * 
	 * @return
	 */
	Recharge getWithLock(String id);

	
	
	/**
	 * 查询充值总额
	 * 
	 * @param recharge
	 * @return
	 */
	public Double getTotalRecharge(Recharge recharge);

	/**
	 * 查询费用总额
	 * 
	 * @param recharge
	 * @return
	 */
	public Double getTotalFee(Recharge recharge);

	/**
	 * 获取每天的充值金额
	 * 
	 * @param params
	 * @return
	 */
	public double getRechargeMoneyPerDay(Map<String, Object> params);
	public List<Recharge> getRechargeNum(Map map);
    public void save(Recharge recharge);

    /**
     * 【去除固定借款人】总充值金额
     * @param recharge
     * @return
     */
	public BigDecimal getExcludeFixedBorrowerFee(Recharge recharge);

	public int getRechargeSuccessPeople(Recharge recharge);

	public BigDecimal getExcludeFixedBorrowerFeeFail(Recharge recharge);

	public int getRechargeFailPeople(Recharge recharge);
}
