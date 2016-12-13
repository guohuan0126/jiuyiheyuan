package com.duanrong.business.repaytracking.service;

import base.pagehelper.PageInfo;

import com.duanrong.business.repaytracking.model.RepayTracking;

/**
 * 还款追踪
 * 
 * @author Lin Zhiming
 */
public interface RepayTrackingService {

	/**
	 * 根据用户ID分页查询
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页显示的记录数
	 * @param userId
	 *            用户ID
	 */
	public PageInfo<RepayTracking> getByUserId(int pageNo, int pageSize,
			String userId);
}
