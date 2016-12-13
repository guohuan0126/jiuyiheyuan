package com.duanrong.business.flow.model;

import java.util.Date;

import base.model.BaseModel;

import com.duanrong.business.user.model.User;

/**
 * @author xiao
 * @date 2015年2月3日 上午9:48:35
 */
public class ItemFlow extends BaseModel{

	
	private static final long serialVersionUID = 4561516723174077877L;
	private int id;
	private int itemId;
	private int flowId;
	private int nodeId;
	private String status;	
	private String UserId;
	private Date createTime;
	private Date operateTime;
	private String message;
	private String operate;	
	private String operateUser;
	private User user;
	private String userName;
	private FlowNode flowNode;
	private String itemType;
	
	
	public FlowNode getFlowNode() {
		return flowNode;
	}
	public void setFlowNode(FlowNode flowNode) {
		this.flowNode = flowNode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public int getFlowId() {
		return flowId;
	}
	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}
	public int getNodeId() {
		return nodeId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
		
	public int getItemId() {
		return itemId;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public String getOperateUser() {
		return operateUser;
	}
	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	
	
}
