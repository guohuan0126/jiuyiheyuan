package com.duanrong.business.token.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * @Description: 客户端请求服务端的token
 * @Author: 林志明
 * @CreateDate: Nov 5, 2014
 */
public class AccessToken extends BaseModel {
	private static final long serialVersionUID = 821769080138389837L;

	// 生成的token，客户端与服务端交互的凭证
	private String id;
	// 用户ID
	private String userId;
	// token的状态，默认为valid
	private String status;
	private Date createTime;
	private Date deadline;
	// token类型（userToken，generalToken）
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
