package com.duanrong.drpay.business.platformtransfer.service;

import java.util.List;

import base.exception.DataAlreadExistException;
import base.exception.TradeException;

import com.duanrong.drpay.business.platformtransfer.model.PlatformTransfer;

/**
 * 
 * @author xiao
 * @datetime 2016年11月16日 下午8:33:47
 */
public interface PlatformTransferService {

	/**
	 * 批量插入
	 * 
	 * @param list
	 */
	void insertBatch(List<PlatformTransfer> list);

	/**
	 * 插入
	 * 
	 * @param platformTransfer
	 */
	void insert(PlatformTransfer platformTransfer);

	/**
	 * 更新
	 * 
	 * @param platformTransfer
	 */
	void update(PlatformTransfer platformTransfer);

	/**
	 * 条件查询
	 * 
	 * @param platformTransfer
	 * @return
	 */
	List<PlatformTransfer> getPlatformTransfer(PlatformTransfer platformTransfer);

	/**
	 * 奖励预处理
	 * 
	 * @return
	 * @throws DataAlreadExistException
	 * @throws TradeException 
	 */
	PlatformTransfer prepare(String requestNo, String userId,
			double rewardMoney, String rewardType, String loanId,
			String remarks) throws DataAlreadExistException, TradeException;
}
