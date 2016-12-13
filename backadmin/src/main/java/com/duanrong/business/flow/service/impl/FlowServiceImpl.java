package com.duanrong.business.flow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import util.MyStringUtils;
import base.pagehelper.PageInfo;

import com.duanrong.business.flow.dao.FlowDao;
import com.duanrong.business.flow.dao.FlowNodeDao;
import com.duanrong.business.flow.model.Flow;
import com.duanrong.business.flow.model.FlowNode;
import com.duanrong.business.flow.service.FlowService;
import com.duanrong.business.user.model.Role;

/**
 * @author xiao
 * @date 2015年2月3日 下午6:19:26
 */

@Service
public class FlowServiceImpl implements FlowService {

	@Resource
	FlowDao flowDao;
	
	@Resource
	FlowNodeDao flowNodeDao;
	
	
	//添加工作流步骤
	private void addFlowNode(Flow flow){
		
		Map<String, Object> map = new HashMap<>();
		List<FlowNode> flowNodes = flow.getNodes();
		for(FlowNode node : flowNodes){		
			node.setFlowId(flow.getFlowId());
			flowNodeDao.insert(node);
			List<Role> roles = node.getRoles();
			for(Role role : roles){
				map.put("roleId", role.getId());
				map.put("nodeId", node.getNodeId());
				flowNodeDao.insertFlowRole(map);
				map.clear();
			}
		}
		
	}
	
	
	public void addFlow(Flow flow) {			
		flowDao.insert(flow);
		addFlowNode(flow);
	}

	
	public PageInfo<Flow> readAllFlow(int pageNo, int pageSize, Flow flow) {
		
		return flowDao.pageLite(pageNo, pageSize, flow);
	}


	
	public void updateFlow(Flow flow) {
		if(!MyStringUtils.isNotAnyBlank(flowDao.get(flow.getFlowId()))){
			flowDao.update(flow);
			List<FlowNode> flowNodes = flowNodeDao.getFlowNodeByFlowId(flow.getFlowId());
			for(FlowNode flowNode : flowNodes){
				flowNodeDao.deleteFlowRoleByNodeId(flowNode.getNodeId());			
				flowNodeDao.delete(flow.getFlowId());
			}
			addFlowNode(flow);		
		}
	}


	public Flow readFlowByFlowId(int flowId) {
		
		Flow flow = flowDao.get(flowId);
		List<FlowNode> nodes = flowNodeDao.selectFlowNodeByFlowId(flowId);
		List<FlowNode> n = new ArrayList<>();
		for(FlowNode node : nodes){
			List<Role> roles = flowNodeDao.selectRoleByNodeId(node.getNodeId());
			node.setRoles(roles);
			n.add(node);
		}
		flow.setNodes(n);
		return flow;
	}


	@Override
	public boolean deleteFlowById(int flowId) {
		
		return flowDao.deleteFlow(flowId) > 0;
	}
	
	

}
