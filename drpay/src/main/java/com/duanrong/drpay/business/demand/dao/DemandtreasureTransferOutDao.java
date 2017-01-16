package com.duanrong.drpay.business.demand.dao;

import java.util.List;

import com.duanrong.drpay.business.demand.model.DemandtreasureTransferOut;

import base.dao.BaseDao;

/**
 * @Description: 活期宝转入
 * @Author: 赵磊
 * @CreateDate: 2015-07-17
 */

public interface DemandtreasureTransferOutDao extends BaseDao<DemandtreasureTransferOut> {

	Double getOutMoneySumByStatus(String userId, String status);

	Integer getValidOutCount(String userId);

	Double getOutInterestSumByStatus(String userId, String status);

	public DemandtreasureTransferOut getOutSumMoney(String userId, String status);
	
	/**
	 * 用户当日转出总额
	 * @param userId
	 * @return
	 */
	public double getValidOutSumMoney(String userId);

	public DemandtreasureTransferOut getTotal();
	public List<DemandtreasureTransferOut> getTotalUser();
}