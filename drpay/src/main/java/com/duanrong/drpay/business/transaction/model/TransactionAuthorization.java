package com.duanrong.drpay.business.transaction.model;

import java.util.Date;

/**
 * 通用转账授权
 * 
 * @author Lin Zhiming
 * @version May 27, 2015 11:00:34 AM
 */
public class TransactionAuthorization {

	private String id;
	private String userId;
	private Date expired;
	private Date commitTime;
	private String status;
	private TransactionAuthorizationDetail transactionAuthorizationDetail;
	private Double amount;
	private String duserId;


	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public TransactionAuthorizationDetail getTransactionAuthorizationDetail() {
		return transactionAuthorizationDetail;
	}

	public void setTransactionAuthorizationDetail(
			TransactionAuthorizationDetail transactionAuthorizationDetail) {
		this.transactionAuthorizationDetail = transactionAuthorizationDetail;
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

	
	public String getDuserId() {
		return duserId;
	}

	
	public void setDuserId(String duserId) {
		this.duserId = duserId;
	}

	@Override
	public String toString() {
		return "TransactionAuthorization [id=" + id + ", userId=" + userId
				+ ", expired=" + expired + ", commitTime=" + commitTime
				+ ", status=" + status + ", transactionAuthorizationDetail="
				+ transactionAuthorizationDetail + ", amount=" + amount
				+ ", duserId=" + duserId + "]";
	}
	
}