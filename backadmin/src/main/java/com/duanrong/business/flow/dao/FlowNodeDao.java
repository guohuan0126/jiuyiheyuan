package com.duanrong.business.flow.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;

import com.duanrong.business.flow.model.FlowNode;
import com.duanrong.business.user.model.Role;

/**
 * @author xiao
 * @date 2015年2月3日 下午6:52:38
 */
public interface FlowNodeDao extends BaseDao<FlowNode> {

	/**
	 * 流程绑定角色
	 * @param map
	 */
	public void insertFlowRole(Map<String, Object> map);
	
	/**
	 * 流程绑定角色
	 * @param map
	 */
	public List<FlowNode> getFlowNodeByFlowId(int flowId);	
	
	/**
	 * 删除流程绑定角色
	 * @param nodeId
	 */
	public void deleteFlowRoleByNodeId(int nodeId);
	
	/**
	 * 查询流程节点
	 * @param flowId
	 * @return 
	 */
	public List<FlowNode> selectFlowNodeByFlowId(int flowId);
	
	/**
	 * 查询流程节点角色
	 * @param flowId
	 * @return 
	 */
	public List<Role> selectRoleByNodeId(int nodeId);
}
