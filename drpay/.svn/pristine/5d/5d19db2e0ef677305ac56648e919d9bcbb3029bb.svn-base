package com.duanrong.drpay.business.demand.service;

import org.springframework.transaction.annotation.Transactional;

import com.duanrong.drpay.business.demand.model.DemandtreasureTransferIn;

import base.exception.ErrorCodeException;
import base.pagehelper.PageInfo;

/**
 * @Description: 活期宝转入
 * @Author: 赵磊
 * @CreateDate: 2015-07-17
 */

public interface DemandtreasureTransferInService {

	/**
	 * 按条件分页查询转入
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param entity
	 * @return
	 */
	public PageInfo<DemandtreasureTransferIn> findPageInfo(int pageNo,
			int pageSize, DemandtreasureTransferIn entity);

	public void insert(DemandtreasureTransferIn demandIn);

	public void update(DemandtreasureTransferIn demandIn);

	@Transactional(rollbackFor = Exception.class)
	public void syncDemandIn(String userId,double money,String userSource)
			throws ErrorCodeException;

	public DemandtreasureTransferIn get(String id);

	public PageInfo<DemandtreasureTransferIn> pageLite(int pageNo,
			int pageSize, DemandtreasureTransferIn in);

}