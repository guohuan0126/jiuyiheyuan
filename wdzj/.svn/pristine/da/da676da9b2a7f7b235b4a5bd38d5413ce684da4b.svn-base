package com.duanrong.business.push.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.duanrong.business.push.model.PushLoan;

/**
 * 项目
 * @author Today
 *
 */
public interface PushLoanDao {
	
	/**
	 * 项目状态更新
	 * @param id
	 * @return
	 */
	public int updateStatus(List<String> ids);

	/**
	 * 未结束的项目查询
	 * @return
	 */
	public List<PushLoan> getPushLoanStatusList();

}