package com.duanrong.business.platformtransfer.model;

import java.io.Serializable;
import java.util.Date;

import com.duanrong.business.user.model.User;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2015-3-3 上午10:06:35
 * @Description : NewAdmin com.duanrong.business.platformtransfer.model
 *              PlatformTransfer.java
 * 
 */
public class PlatformTransfer implements Serializable {
	private static final long serialVersionUID = 4855641042056904362L;
	private String id;
	private String username;
	private Date time;
	private Double actualMoney;
	private Date successTime;
	private String status;
	private String remarks;
    private User user;
    private String longId;  
    private String type;
    private String loanId;
    private String repayId;
    private String orderId;
    
	public String getLongId() {
		return longId;
	}

	public void setLongId(String longId) {
		this.longId = longId;
	}	
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRepayId() {
		return repayId;
	}

	public void setRepayId(String repayId) {
		this.repayId = repayId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Double getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(Double actualMoney) {
		this.actualMoney = actualMoney;
	}

	public Date getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	
}
