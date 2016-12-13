package com.duanrong.business.flow.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.flow.dao.FlowNodeDao;
import com.duanrong.business.flow.model.FlowNode;
import com.duanrong.business.user.model.Role;

/**
 * @author xiao
 * @date 2015年2月3日 下午6:53:50
 */
@Repository
public class FlowNodeDaoImpl extends BaseDaoImpl<FlowNode> implements FlowNodeDao {

	
	public FlowNodeDaoImpl(){
		
		super.setMapperNameSpace("com.duanrong.business.flow.mapper.FlowNodeMapper");
		
	}
	
	@Override
	public void insertFlowRole(Map<String, Object> map) {
		
		this.getSqlSession().insert(getMapperNameSpace()+".insertFlowRole", map);		
	}

	@Override
	public void deleteFlowRoleByNodeId(int nodeId) {
		this.getSqlSession().delete(getMapperNameSpace()+".deleteFlowRole", nodeId);
		
	}

	@Override
	public List<FlowNode> getFlowNodeByFlowId(int flowId) {
		
		return this.getSqlSession().selectList(getMapperNameSpace()+".selectNodeIdByFlowId", flowId);
	}

	@Override
	public List<FlowNode> selectFlowNodeByFlowId(int flowId) {
		
		return this.getSqlSession().selectList(getMapperNameSpace()+".selectNodeByFlowId", flowId);
		
	}

	@Override
	public List<Role> selectRoleByNodeId(int nodeId) {
		
		return getSqlSession().selectList(getMapperNameSpace()+".selectRoleByNodeId", nodeId);
	}

	
}
