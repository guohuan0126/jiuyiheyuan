package com.duanrong.business.demand.service;


import java.io.IOException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import base.exception.ExceedMaxInvestMoney;
import base.exception.ExceedMoneyNeedRaised;
import base.exception.InsufficientBalance;
import base.exception.InvestMoneyException;
import base.exception.ObjectIsNullException;
import base.exception.OutOfDateException;
import base.exception.ParameterException;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.model.DemandtreasureTransferIn;


/**
 * @Description: 活期宝转入确认
 * @Author: wangjing
 * @CreateDate: 2015-07-17
 */

public interface DemandtreasureTransferInService {
	public List<DemandtreasureTransferIn> findAll(DemandtreasureTransferIn demandtreasureTransferIn);
	public String transferInConfirm(DemandtreasureTransferIn demandIn, String flag) throws InsufficientBalance, IOException;
	public DemandtreasureTransferIn gettotal();
	PageInfo<DemandtreasureTransferIn> pageInfo(int pageNo, int pageSize);
	public List<DemandtreasureTransferIn> findTran(DemandtreasureTransferIn demandtreasureTransferIn);
	public DemandtreasureTransferIn get(String id);
	public List<DemandtreasureTransferIn> findStatus();
	
	/**
	 * 查询某人转入记录中金额最高的top笔记录
	 * @param userId
	 * @param top
	 * @return
	 */
	public List<DemandtreasureTransferIn> findTransferInRecordTop(String userId,int top);
	
	@Transactional(rollbackFor = Exception.class)
	public void syncDemandIn(DemandtreasureTransferIn demandIn, String type)
			throws ObjectIsNullException, ParameterException,
			InvestMoneyException, InsufficientBalance, ExceedMoneyNeedRaised,
			ExceedMaxInvestMoney,OutOfDateException;
	
	
	public List<DemandtreasureTransferIn> getTransferInByFork();
	
	public void update(DemandtreasureTransferIn demandtreasureTransferIn);
	
	public List<DemandtreasureTransferIn> getTransferInitByFork();
	public Double getDemandMoney(String userId);
	//查询昨日活期有转出的用户id
	public List<String> getTurnOutUserId();
}