package com.duanrong.business.yeepay.model;


import java.util.Date;
import java.util.List;

import base.model.BaseModel;

/**
 * 通用转账授权
 * 
 * @author Lin Zhiming
 * @version May 27, 2015 11:00:34 AM
 */
public class TransactionAuthorization extends BaseModel {

	private String id;
	private String userId;
	private Date expired;
	private Date commitTime;
	private String status;
	private List<TransactionAuthorizationDetail> transactionAuthorizationDetails;
	private Double amount;

	@Override
	public String toString() {
		return "TransactionAuthorization [id=" + id + ", userId=" + userId
				+ ", expired=" + expired + ", commitTime=" + commitTime
				+ ", status=" + status + ", transactionAuthorizationDetails="
				+ transactionAuthorizationDetails + ", amount=" + amount + "]";
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public List<TransactionAuthorizationDetail> getTransactionAuthorizationDetails() {
		return transactionAuthorizationDetails;
	}

	public void setTransactionAuthorizationDetails(
			List<TransactionAuthorizationDetail> transactionAuthorizationDetails) {
		this.transactionAuthorizationDetails = transactionAuthorizationDetails;
	}

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

	public Date getExpired() {
		return expired;
	}

	public void setExpired(Date expired) {
		this.expired = expired;
	}

	public Date getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private static final long serialVersionUID = -1893199816354394538L;
}