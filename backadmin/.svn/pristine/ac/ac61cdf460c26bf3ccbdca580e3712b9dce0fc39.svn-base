package com.duanrong.business.ruralfinance.dao;

import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.business.ruralfinance.model.AgricultureDebitRecords;




public interface AgricultureDebitRecordsDao<T> {
	/**
	 * 保存数据借款人信息
	 * @param params
	 * @return
	 */
	public int saveAgricultureDebitRecords(T obj,String type);

	/**
	 * 通用条件查询
	 * @param map
	 * @return
	 */
	public AgricultureDebitRecords findByCondition(Map map);
	
	/**
	 * 保存excel
	 * @param obj
	 * @param type
	 * @return
	 */
	public int saveExcle(Object obj, String type);
	/**
	 * 查询中金扣款记录
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */

	PageInfo<AgricultureDebitRecords> readAgricultureDebitRecords(int pageNo,
			int pageSize, Map<String, Object> params);
	}
