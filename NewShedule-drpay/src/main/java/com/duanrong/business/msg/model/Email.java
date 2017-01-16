package com.duanrong.business.msg.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : wangjing
 * @CreateTime : 2014-12-23 上午11:02:42
 * @Description : jdp2p com.esoft.jdp2p.loan.babyModel House.java
 * 
 */
public class Email extends BaseModel {
	private static final long serialVersionUID = -6524264191558392682L;
	private String id;
    private String content;
    private String email;
    private String status;
    private Date time;
    private String title;
    private String type;
    private String userId;
    private String createUserID;
    private Date sendTime;
    private String sendUserID;
    private String loanId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCreateUserID() {
		return createUserID;
	}
	public void setCreateUserID(String createUserID) {
		this.createUserID = createUserID;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getSendUserID() {
		return sendUserID;
	}
	public void setSendUserID(String sendUserID) {
		this.sendUserID = sendUserID;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

}
