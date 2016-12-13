package com.duanrong.business.user.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * 账户平衡实体类设计
 * 
 * @author Lin Zhiming
 * @version Mar 2, 2015 10:43:57 AM
 */
public class AccountChecking extends BaseModel {
	private static final long serialVersionUID = -5962047410291386474L;
	/**
	 * 主键ID
	 */
	private String id;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 本地账户余额
	 */
	private Double balance;

	/**
	 * 本地账户可用余额
	 */
	private Double availableAmount;
	/**
	 * 本地冻结金额
	 */
	private Double freezeAmount;
	/**
	 * 易宝账户余额
	 */
	private String ebaoBalance;
	/**
	 * 易宝账户可用余额
	 */
	private String ebaoAvailableAmount;
	/**
	 * 易宝账户冻结金额
	 */
	private String ebaoFreezeAmount;

	/**
	 * 更新时间
	 */
	private Date time;

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

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(Double availableAmount) {
		this.availableAmount = availableAmount;
	}

	public Double getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(Double freezeAmount) {
		this.freezeAmount = freezeAmount;
	}

	public String getEbaoBalance() {
		return ebaoBalance;
	}

	public void setEbaoBalance(String ebaoBalance) {
		this.ebaoBalance = ebaoBalance;
	}

	public String getEbaoAvailableAmount() {
		return ebaoAvailableAmount;
	}

	public void setEbaoAvailableAmount(String ebaoAvailableAmount) {
		this.ebaoAvailableAmount = ebaoAvailableAmount;
	}

	public String getEbaoFreezeAmount() {
		return ebaoFreezeAmount;
	}

	public void setEbaoFreezeAmount(String ebaoFreezeAmount) {
		this.ebaoFreezeAmount = ebaoFreezeAmount;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "AccountChecking [id=" + id + ", userId=" + userId
				+ ", balance=" + balance + ", availableAmount="
				+ availableAmount + ", freezeAmount=" + freezeAmount
				+ ", ebaoBalance=" + ebaoBalance + ", ebaoAvailableAmount="
				+ ebaoAvailableAmount + ", ebaoFreezeAmount="
				+ ebaoFreezeAmount + ", time=" + time + "]";
	}

}
