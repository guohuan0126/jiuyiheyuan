package com.duanrong.business.account.model;

import java.util.Date;

/**
 * @Description: 易宝开户成功记录
 * @Author: 林志明
 * @CreateDate: Aug 29, 2014
 */
public class TrusteeshipAccount {
	
	private String id;
	private String userId;
	/**
	 * 托管方
	 */
	private String trusteeship;
	/**
	 * 用户在托管方的编号
	 */
	private String accountId;
	/**
	 * 在托管方的开户时间
	 */
	private Date createTime;
	private String status;

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

	public String getTrusteeship() {
		return trusteeship;
	}

	public void setTrusteeship(String trusteeship) {
		this.trusteeship = trusteeship;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
