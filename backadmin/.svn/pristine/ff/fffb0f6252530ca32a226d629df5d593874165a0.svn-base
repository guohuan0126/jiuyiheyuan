package com.duanrong.business.ruralfinance.service;

import java.util.List;
import java.util.Map;

import com.duanrong.business.ruralfinance.model.AgricultureRepaymentPlan;


public interface AgricultureRepaymentPlanService {
		
	/**
	 * 批量生成还款计划
	 * 
	 */
	public void insertBatch(List<AgricultureRepaymentPlan> list);
	/**
	 * 修改还款计划信息
	 * @param params
	 * @return
	 */
	public int updateRepaymentplan(Map<String, Object> params);
	
	/**
	 * 批量修改还款计划的状态
	 * @param params
	 * @return
	 */
	public int updateRepaymentplanStatus(Map<String, Object> params);
	
	/**
	 * 批量修改中金扣款记录的标识
	 * @param flag
	 * @return
	 */
	public int updateRepaymentplanFlag(String uploadExcelId);
	/**
	 * 修改还款计划信息
	 * @param params
	 * @return
	 */
	public int updateTimlyRepaymentplan(AgricultureRepaymentPlan repaymentPlan);
	
	/**
	 * 手动修改还款计划信息
	 * @param params
	 * @return
	 */
	public int updateRepaymentplanById(Map<String, Object> params);

	
}
