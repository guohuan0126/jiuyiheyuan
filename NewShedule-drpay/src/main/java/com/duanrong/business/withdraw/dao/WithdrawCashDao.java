package com.duanrong.business.withdraw.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import base.dao.BaseDao;

import com.duanrong.business.withdraw.model.WithdrawCash;

/**
 * @Description: 提现
 * @Author: 林志明
 * @CreateDate: Sep 11, 2014
 */
public interface WithdrawCashDao extends BaseDao<WithdrawCash> {

	/**
	 * 查询（加锁 慎用）
	 * @param id
	 * @return
	 */
	WithdrawCash getWithLock(String id);
	
	/**
	 * 获取每天的提现金额
	 * 
	 * @param params
	 * @return
	 */
	public double getWithdrawMoneyPerDay(Map<String, Object> params);

	/**
	 * 根据条件进行组合查询
	 * 
	 * @param withdrawCash
	 * @return
	 */
	public List<WithdrawCash> getByCondition(WithdrawCash withdrawCash);

	/**
	 * 查询提现总额
	 * 
	 * @param withdrawCash
	 * @return
	 */
	public Double getTotalWithdrawCash(WithdrawCash withdrawCash);
	
	/**
	 * 查询用户的提现次数
	 * @param withcash
	 * @return
	 */
	public long GetWithdrawCount(WithdrawCash withcash);
	
	/**
	 * 查询提现费用总额
	 * 
	 * @param withcash
	 * @return
	 */
	public Double getTotalFee(WithdrawCash withcash);
	public List<WithdrawCash> getCashNum(Map map);

	Integer getHolidayDate(Date date,String type);

	/**
	 * 根据date与status获取提现记录
	 * @param map
	 * @return
	 */
	List<WithdrawCash> getWithdrawCashByDateAndStatus(Map<String, Object> map);
}
