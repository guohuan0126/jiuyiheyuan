package com.duanrong.business.demand.service;

import java.io.IOException;
import java.math.BigDecimal;
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
	public DemandtreasureTransferOut readTotal();
	PageInfo<DemandtreasureTransferOut> readPageInfo(int pageNo, int pageSize,Map map);
	List<DemandtreasureTransferOut> readAll();
	List<String> createTransferOut(String account,List<DemandtreasureTransferOut> list, String callUrl,double summoney);

	public void S2SCallback(ServletRequest request,
			ServletResponse response);
	public List<String> createstarTransferOut(DemandtreasureGuOut out);
	public String transferOutConfirm(String id, String flag) throws InsufficientBalance, IOException;
	public List<DemandtreasureTransferOut> readTotalUser();

	/**
	 * 转出查询，带条件 分页
	 * @param parseInt
	 * @param parseInt2
	 * @param transferOut
	 * @return
	 */
	public PageInfo readListForTranferOut(int parseInt, int parseInt2, DemandtreasureTransferOut transferOut);

	/**
	 * 总转出金额
	 * @param transferOut
	 * @return
	 */
	public BigDecimal readSumMoneyTransferOut(DemandtreasureTransferOut transferOut);

	public int readSumPeopleTransferOut(DemandtreasureTransferOut transferOut);

	public BigDecimal readSumMoneyFailTransferOut(
			DemandtreasureTransferOut transferOut);

	public int readSumPeopleFailTransferOut(DemandtreasureTransferOut transferOut);
	
}