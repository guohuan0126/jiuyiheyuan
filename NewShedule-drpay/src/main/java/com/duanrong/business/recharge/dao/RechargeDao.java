package com.duanrong.business.recharge.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import base.dao.BaseDao;

import com.duanrong.business.recharge.model.Recharge;

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
	 * 查询（加锁， 慎用）
	 * @param id
	 * @return
	 */
	Recharge getWithLock(String id);

	/**
	 * 查询recharge中在priDate到nextDate之间的状态为status的记录
	 * @param preDate
	 * @param nextDate
	 * @param status
	 * @return
	 */
	public List<Recharge> getRechargeByDateAndStatus(Map<String, Object> map);
}
