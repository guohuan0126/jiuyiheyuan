package com.duanrong.drpay.business.platformtransfer.model;

import java.util.Date;

public class PlatformTransfer {

	private String id;
	private String username;
	private Date time;
	private Double actualMoney;
	private Date successTime;
	private String status;
	private String remarks;
    private String longId;  
    private String type;
    private String loanId;
    private String repayId;
    private String orderId;
	private String billType;
    
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
	public String getLongId() {
		return longId;
	}
	public void setLongId(String longId) {
		this.longId = longId;
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
	public String getRepayId() {
		return repayId;
	}
	public void setRepayId(String repayId) {
		this.repayId = repayId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBillType() {
		return billType;
	}
	
	public void setBillType(String billType) {
		this.billType = billType;
	}
	
	@Override
	public String toString() {
		return "PlatformTransfer [id=" + id + ", username=" + username
				+ ", time=" + time + ", actualMoney=" + actualMoney
				+ ", successTime=" + successTime + ", status=" + status
				+ ", remarks=" + remarks + ", longId=" + longId + ", type="
				+ type + ", loanId=" + loanId + ", repayId=" + repayId
				+ ", orderId=" + orderId + "]";
	}
    
	
}
