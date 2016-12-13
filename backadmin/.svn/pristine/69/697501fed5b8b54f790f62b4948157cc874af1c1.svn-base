package com.duanrong.business.ruralfinance.dao;

import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.business.ruralfinance.model.AgricultureTimelyloansPrepayment;

import java.util.List;




public interface AgricultureTimelyloansPrepaymentDao<T> {
	/**
	 * 保存数据借款人信息
	 * @param params
	 * @return
	 */
	public int saveAgricultureTimelyloansPrepayment(T obj,String type);

	/**
	 * 通用条件查询
	 * @param map
	 * @return
	 */
	public AgricultureTimelyloansPrepayment findByCondition(Map map);
	
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

	PageInfo<AgricultureTimelyloansPrepayment> readAgricultureTimelyPrepayment(int pageNo,
			int pageSize, Map<String, Object> params);
	/**
	 * 查询该uploadid上传数据
	 * @param params
	 * @return
	 */
   public List<AgricultureTimelyloansPrepayment> readTimelyPrepaymentbyupload(Map<String, Object> params);
   
	/**
	 * 修改项目匹配状态
	 * 
	 */
	public int updateTimelyloanPrepaymentStatus(String id);
}