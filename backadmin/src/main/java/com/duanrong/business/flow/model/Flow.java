package com.duanrong.business.flow.model;

import java.util.ArrayList;
import java.util.List;

import base.model.BaseModel;

/**
 * @author xiao
 * @date 2015年2月3日 上午9:29:14
 */
public class Flow extends BaseModel{

	private static final long serialVersionUID = 3056764536795066155L;
	
	private int flowId;
	private String flowName;
	private String description;
	private int status;
	private List<FlowNode> nodes = new ArrayList<>();
	
	public int getFlowId() {
		return flowId;
	}
	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<FlowNode> getNodes() {
		return nodes;
	}
	public void setNodes(List<FlowNode> nodes) {
		this.nodes = nodes;
	} 
	
	
	
}
