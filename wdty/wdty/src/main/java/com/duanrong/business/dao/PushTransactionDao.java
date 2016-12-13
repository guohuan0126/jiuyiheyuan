package com.duanrong.business.dao;

import java.util.List;

import com.duanrong.business.model.PushTransaction;

/**
 * 天眼用户交易记录
 * @author Today
 *
 */
public interface PushTransactionDao {
	
	/**
	 * 保存天眼用户交易信息
	 * @param record
	 * @return
	 */
	public int insert(List<PushTransaction> recordList);
	
}