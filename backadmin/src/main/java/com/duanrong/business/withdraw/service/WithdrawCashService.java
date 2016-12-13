package com.duanrong.business.withdraw.service;

import java.math.BigDecimal;
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
	public double readWithdrawMoneyPerDay(Map<String, Object> params);

	public WithdrawCash read(String id);

	/**
	 * 根据条件进行组合查询
	 * 
	 * @param withdrawCash
	 * @return
	 */
	public List<WithdrawCash> readByCondition(WithdrawCash withdrawCash);

	/**
	 * 查询提现总额
	 * 
	 * @param withdrawCash
	 * @return
	 */
	public Double readTotalWithdrawCash(WithdrawCash withdrawCash);

	/**
	 * 查询用户的提现记录
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageData<WithdrawCash> readPaging(int pageIndex, int pageSize,
			WithdrawCash cash);

	/**
	 * 查询用户的提现次数
	 * 
	 * @param withcash
	 * @return
	 */
	public long readWithdrawCount(WithdrawCash withcash);
	/**
	 * 分页
	 * @param withcash
	 * @return
	 */
	 public PageInfo<WithdrawCash> readPageInfo(int pageNo, int pageSize,
			 WithdrawCash withcash);
	 /**
		 * 查询提现费用总额
		 * 
		 * @param recharge
		 * @return
		 */
		public Double readTotalFee(WithdrawCash withcash);
		public List<WithdrawCash> readCashNum(Map map);

		/**
		 * 总提现金额[去除固定借款人]
		 * @param withdrawCash
		 * @return
		 */
		public BigDecimal readExcludeFixedFee(WithdrawCash withdrawCash);
		
		public void update(WithdrawCash withcash);

		public int readExcludeFixedFeePeopleCount(WithdrawCash withdrawCash);
}