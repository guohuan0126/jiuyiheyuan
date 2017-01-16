package com.duanrong.business.flow.service;

import base.pagehelper.PageInfo;

import com.duanrong.business.flow.model.Flow;

/**
 * @author xiao
 * @date 2015年2月3日 下午6:16:35
 */
public interface FlowService {
	
	/**
	 * 添加流程
	 * @param flow
	 */
	public void addFlow(Flow flow);

	/**
	 * 分页查询
	 * @param flow
	 * @return
	 */
	public PageInfo<Flow> getAllFlow(int pageNo, int pageSize, Flow flow);
	
	/**
	 * 更新流程
	 * @param flow
	 */
	public void updateFlow(Flow flow);
	
	/**
	 * 得到流程詳細信息
	 * @param flowId
	 * @return
	 */
	public Flow getFlowByFlowId(int flowId);
	
	/**
	 * 删除流程信息
	 * @param flowId
	 * @return
	 */
	public boolean deleteFlowById(int flowId);
}
