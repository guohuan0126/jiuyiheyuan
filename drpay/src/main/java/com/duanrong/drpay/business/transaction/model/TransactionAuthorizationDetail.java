package com.duanrong.drpay.business.transaction.model;


/**
 * 通用转账授权详细信息
 * 
 * @author Lin Zhiming
 * @version May 27, 2015 11:00:34 AM
 */
public class TransactionAuthorizationDetail {

	private Integer id;
	private String userId;
	private Double amount;
	private String status;
	private String transactionAuthorizationId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTransactionAuthorizationId() {
		return transactionAuthorizationId;
	}

	public void setTransactionAuthorizationId(String transactionAuthorizationId) {
		this.transactionAuthorizationId = transactionAuthorizationId;
	}

	@Override
	public String toString() {
		return "TransactionAuthorizationDetail [id=" + id + ", userId="
				+ userId + ", amount=" + amount + ", status=" + status
				+ ", transactionAuthorizationId=" + transactionAuthorizationId
				+ "]";
	}
	
}