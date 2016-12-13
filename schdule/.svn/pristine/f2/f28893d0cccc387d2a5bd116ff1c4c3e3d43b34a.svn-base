package com.duanrong.business.withdraw.service;

import java.util.List;
import java.util.Map;

import base.model.PageData;
import base.pagehelper.PageInfo;

import com.duanrong.business.withdraw.model.WithdrawCash;

/**
 * @Description: 提现
 * @Author: 林志明
 * @CreateDate: Sep 11, 2014
 */
public interface WithdrawCashService {

	/**
	 * 获取每天的提现金额
	 * 
	 * @param params
	 * @return
	 */
	public double getWithdrawMoneyPerDay(Map<String, Object> params);

	public WithdrawCash get(String id);
	
	/**
	 * 查询（加锁， 慎用）
	 * @param id
	 * @return
	 */
	public WithdrawCash queryWithLock(String id);

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
	 * 查询用户的提现记录
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageData<WithdrawCash> findPaging(int pageIndex, int pageSize,
			WithdrawCash cash);

	/**
	 * 查询用户的提现次数
	 * 
	 * @param withcash
	 * @return
	 */
	public long GetWithdrawCount(WithdrawCash withcash);
	/**
	 * 分页
	 * @param withcash
	 * @return
	 */
	 public PageInfo<WithdrawCash> findPageInfo(int pageNo, int pageSize,
			 WithdrawCash withcash);
	 /**
		 * 查询提现费用总额
		 * 
		 * @param recharge
		 * @return
		 */
		public Double getTotalFee(WithdrawCash withcash);
		public List<WithdrawCash> getCashNum(Map map);
		
		public void update(WithdrawCash withcash);
}
