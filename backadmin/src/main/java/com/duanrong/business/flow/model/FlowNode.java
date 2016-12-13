package com.duanrong.business.flow.model;

import java.util.ArrayList;
import java.util.List;

import base.model.BaseModel;

import com.duanrong.business.user.model.Role;

/**
 * @author xiao
 * @date 2015年2月3日 下午2:04:48
 */
public class FlowNode extends BaseModel {


	private static final long serialVersionUID = -222277576835626563L;
	
	private int nodeId;
	private int flowId;
	private String nodeName;
	private int nodeOrder;
	private int status;
	private String description;
	private List<Role> roles = new ArrayList<Role>();
	private String roleId;
	private String roleName;
	
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public int getFlowId() {
		return flowId;
	}
	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public int getNodeOrder() {
		return nodeOrder;
	}
	public void setNodeOrder(int nodeOrder) {
		this.nodeOrder = nodeOrder;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	} 
	
	

}
