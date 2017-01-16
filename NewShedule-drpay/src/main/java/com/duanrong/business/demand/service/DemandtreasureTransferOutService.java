package com.duanrong.business.demand.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import base.exception.InsufficientBalance;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.model.DemandtreasureGuOut;
import com.duanrong.business.demand.model.DemandtreasureTransferOut;

/**
 * @Description: 活期宝转出
 * @Author: 赵磊
 * @CreateDate: 2015-07-17
 */

public interface DemandtreasureTransferOutService {
	public void insert(DemandtreasureTransferOut demandOut);

	public void update(DemandtreasureTransferOut demandOut);
	public DemandtreasureTransferOut gettotal();
	PageInfo<DemandtreasureTransferOut> pageInfo(int pageNo, int pageSize,Map map);
	List<DemandtreasureTransferOut> findAll();
	

	public List<DemandtreasureTransferOut> gettotalUser();
	
	/**
	 * 查询出所有成功转让即转出的活期宝
	 */
	public List  <DemandtreasureTransferOut> findAllSuccess();
	
}