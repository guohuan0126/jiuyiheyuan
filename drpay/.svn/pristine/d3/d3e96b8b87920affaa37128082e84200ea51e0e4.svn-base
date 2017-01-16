package com.duanrong.drpay.business.demand.dao;

import com.duanrong.drpay.business.demand.model.DemandtreasureTransferIn;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

/**
 * @Description: 活期宝转入
 * @Author: 赵磊
 * @CreateDate: 2015-07-17
 */

public interface DemandtreasureTransferInDao extends BaseDao<DemandtreasureTransferIn> {

	PageInfo<DemandtreasureTransferIn> findPageInfo
			(int pageNo, int pageSize, DemandtreasureTransferIn entity);

	double getuserDemandInSumMoney(String userId);

	int isDemandRiseMoney(DemandtreasureTransferIn demandIn);

}
