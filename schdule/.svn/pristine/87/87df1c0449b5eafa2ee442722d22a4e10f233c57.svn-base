package com.duanrong.business.demand.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.model.DemandtreasureTransferOut;

/**
 * @Description: 活期宝转入
 * @Author: 赵磊
 * @CreateDate: 2015-07-17
 */

public interface DemandtreasureTransferOutDao extends BaseDao<DemandtreasureTransferOut> {

	Double getUserDemandInSum(String userId);
	public DemandtreasureTransferOut gettotal();
	PageInfo<DemandtreasureTransferOut> pageInfo(int pageNo, int pageSize,Map map);
	public List<DemandtreasureTransferOut> gettotalUser();
	public DemandtreasureTransferOut queryOutSumMoney(String userId,
			String status);
	/**
	 * 得到该用户累计的转出金额TransferOut
	 * @param userId
	 * @return
	 */
	public double getTransferOutAllMoney(String userId,String status);
	/**
	 * 查询出所有成功转让即转出的活期宝
	 */
	public List  <DemandtreasureTransferOut> findAllSuccess();
}
