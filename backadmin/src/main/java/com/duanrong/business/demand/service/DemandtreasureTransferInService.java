package com.duanrong.business.demand.service;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import base.exception.InsufficientBalance;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.model.DemandtreasureTransferIn;


/**
 * @Description: 活期宝转入确认
 * @Author: wangjing
 * @CreateDate: 2015-07-17
 */

public interface DemandtreasureTransferInService {
	public List<DemandtreasureTransferIn> readAll(DemandtreasureTransferIn demandtreasureTransferIn);
	public String transferInConfirm(DemandtreasureTransferIn demandIn, String flag) throws InsufficientBalance, IOException;
	public DemandtreasureTransferIn readTotal();
	PageInfo<DemandtreasureTransferIn> readPageInfo(int pageNo, int pageSize);
	public List<DemandtreasureTransferIn> readTran(DemandtreasureTransferIn demandtreasureTransferIn);
	public DemandtreasureTransferIn read(String id);
	public List<DemandtreasureTransferIn> readStatus();
	
	/**
	 * 查询某人转入记录中金额最高的top笔记录
	 * @param userId
	 * @param top
	 * @return
	 */
	public List<DemandtreasureTransferIn> readTransferInRecordTop(String userId,int top);
	
	/**
	 * 转入查询，带条件 分页
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