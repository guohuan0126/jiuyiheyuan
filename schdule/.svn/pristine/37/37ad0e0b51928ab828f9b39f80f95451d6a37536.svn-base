package com.duanrong.business.demand.dao;



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
	public DemandtreasureTransferIn gettotal();
	PageInfo<DemandtreasureTransferIn> pageInfo(int pageNo, int pageSize);
	public List<DemandtreasureTransferIn> findTran(DemandtreasureTransferIn tran);
	public List<DemandtreasureTransferIn> findStatus();
	public double getuserDemandInSumMoney(String userId);
	/**
	 * 得到该用户累计的转入金额TransferIn
	 * @param userId
	 * @return
	 */
	public double getTransferInAllMoney(String userId);
	
	public List<DemandtreasureTransferIn> findTransferInRecordTop(String userId, int top) ;

	//查询为匹配的转入记录
	public List<DemandtreasureTransferIn> getTransferInByFork();
	
	
	//查询为匹配的转入记录
	public List<DemandtreasureTransferIn> getTransferInitByFork();
	//查询用户的活期再投
	public Double getDemandMoney(String userId);
	//查询昨日活期有转出的用户id
	public List<String> getTurnOutUserId();
}
