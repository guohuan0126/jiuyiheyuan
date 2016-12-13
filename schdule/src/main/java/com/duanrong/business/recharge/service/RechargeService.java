package com.duanrong.business.recharge.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import base.model.PageData;
import base.pagehelper.PageInfo;

import com.duanrong.business.recharge.model.Recharge;

/**
 * @Description: 充值
 * @Author: 林志明
 * @CreateDate: Sep 11, 2014
 */
public interface RechargeService {

	/**
	 * 获取每天的充值金额
	 * 
	 * @param params
	 * @return
	 */
	public double getRechargeMoneyPerDay(Map<String, Object> params);

	/**
	 * 更新充值记录
	 */
	public void update(Recharge recharge);

	/**
	 * 充值成功操作
	 * 
	 * @param rechargeId
	 */
	/*@Transactional(rollbackFor = Exception.class)
	public void rechargeSuccess(String rechargeId);*/
	
	
	/**
	 * 充值成功操作
	 * 
	 * @param rechargeId 充值ID
	 * @param type 充值类型
	 */
	@Transactional(rollbackFor = Exception.class)
	public void rechargeSuccess(String rechargeId,String type);

	public Recharge get(String id);

	/**
	 * 保存充值记录
	 */
	public void insert(Recharge recharge);

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
	 * 分页查询
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页显示的记录数
	 * @param recharge
	 *            条件
	 */
	public PageData<Recharge> findPaging(int pageNo, int pageSize,
			Recharge recharge);

	/**
	 * 获取用户充值次数
	 * 
	 * @param recharge
	 * @return
	 */
	public Long getRechargeCount(Recharge recharge);
	/**
	 * 分页
	 * @param recharge
	 * @return
	 */
	 public PageInfo<Recharge> getAllRecharge(int pageNo, int pageSize,
			 Recharge recharge);

	 /**
		 * 查询费用总额
		 * 
		 * @param recharge
		 * @return
		 */
		public Double getTotalFee(Recharge recharge);
		public List<Recharge> getRechargeNum(Map map);
		public void save(Recharge recharge);

}