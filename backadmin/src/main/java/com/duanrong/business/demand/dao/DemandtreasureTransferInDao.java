package com.duanrong.business.demand.dao;



import java.math.BigDecimal;
import java.util.List;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.model.DemandtreasureTransferIn;

/**
 * @Description: 活期宝转入
 * @Author: 赵磊
 * @CreateDate: 2015-07-17
 */

public interface DemandtreasureTransferInDao extends BaseDao<DemandtreasureTransferIn> {
	public DemandtreasureTransferIn readTotal();
	PageInfo<DemandtreasureTransferIn> readPageInfo(int pageNo, int pageSize);
	public List<DemandtreasureTransferIn> readTran(DemandtreasureTransferIn tran);
	public List<DemandtreasureTransferIn> readStatus();
	public double readUserDemandInSumMoney(String userId);
	/**
	 * 得到该用户累计的转入金额TransferIn
	 * @param userId
	 * @return
	 */
	public double readTransferInAllMoney(String userId);
	
	public List<DemandtreasureTransferIn> readTransferInRecordTop(String userId, int top) ;
	/**
	 * 转入查询，带分页
	 * @param parseInt
	 * @param parseInt2
	 * @param transferIn
	 * @return
	 */
	public PageInfo readListForTranferIn(int parseInt, int parseInt2, DemandtreasureTransferIn transferIn);
	/**
	 * 总转入金额查询
	 * @param transferIn
	 * @return
	 */
	public BigDecimal readSumMoneyTransferIn(DemandtreasureTransferIn transferIn);
	public int readSumPeopleTransferIn(DemandtreasureTransferIn transferIn);
	public BigDecimal readSumMoneyTransferInFail(
			DemandtreasureTransferIn transferIn);
	public int readSumPeopleTransferInFail(DemandtreasureTransferIn transferIn);
}
