package com.duanrong.business.demand.dao;

import java.math.BigDecimal;
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
	 * 转出查询，带条件 分页
	 * @param parseInt
	 * @param parseInt2
	 * @param transferOut
	 * @return
	 */
	PageInfo getListForTranferOut(int parseInt, int parseInt2, DemandtreasureTransferOut transferOut);
	/**
	 * 总转出金额
	 * @param transferOut
	 * @return
	 */
	BigDecimal getSumMoneyTransferOut(DemandtreasureTransferOut transferOut);
	int getSumPeopleTransferOut(DemandtreasureTransferOut transferOut);
	BigDecimal getSumMoneyFailTransferOut(DemandtreasureTransferOut transferOut);
	int getSumPeopleFailTransferOut(DemandtreasureTransferOut transferOut);
}
