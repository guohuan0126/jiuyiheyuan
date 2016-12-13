package com.duanrong.business.risk.dao;

import base.dao.BaseDao;

import com.duanrong.business.risk.model.SystemBill;

/**
 * @Description: 系统账户
 * @Author: 林志明
 * @CreateDate: Nov 22, 2014
 */
public interface SystemBillDao extends BaseDao<SystemBill> {
	/**
	 * 获取最后的bill记录
	 * 
	 * @return
	 */
	public SystemBill getLastestBill();

	/**
	 * 根据类型获取总金额
	 * 
	 * @param type
	 * @return
	 */
	public Double getSumByType(String type);

}