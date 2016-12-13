package com.duanrong.business.ruralfinance.service;

import java.util.List;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.business.ruralfinance.model.AgricultureForkLoans;


public interface AgricultureForkLoansService {
	
	
	/**
	 * 批量拆分子标
	 * 
	 */
	public void insertBatch(List<AgricultureForkLoans> list);
	
	/**
	 * 已经放款但未打包的借款项目 ，按照相同项目周期查询
	 * @param pageNo
	 * @param pageSize
	 * @param Ruralfinance_data
	 * @return
	 */
	public PageInfo<AgricultureForkLoans> readPackagingLoan(int pageNo, int pageSize, Map<String, Object> params);
	/**
	 * 批量打包
	 * 
	 */
	public void updateBatch(Map<String, Object> params);
	

	/**
	 * 修改打包项目信息
	 * @param params
	 */
	public int updateForkLoanstatus(Map<String, Object> params);
	
	/**
	 * 根据子标id查询父标的借款人信息及子标信息
	 * @param id
	 * @return
	 */
	public AgricultureForkLoans readByForkId(String id);
	
	/**
	 * 批量修改子标打包状态
	 * 
	 */
	public void updatePackStatus(Map<String, Object> params);
	
	public String BatchForkLoans(String[] ids);
}
