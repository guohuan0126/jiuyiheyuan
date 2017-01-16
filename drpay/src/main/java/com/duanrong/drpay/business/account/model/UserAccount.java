package com.duanrong.drpay.business.account.model;

import java.util.Date;

public class UserAccount {
	// ID自增
	private Integer id;
	// 用户ID
	private String userId;
	// 余额
	private Double balance;
	// 可用余额
	private Double availableBalance;
	// 冻结金额
	private Double freezeAmount;
	// 交易密码
	private String password;
	// 自动投标标识
	private Integer autoInvest;
	// 自动还款标识
	private Integer autoRepay;
	// 自动投标标识
	private Integer autoRecharge;
	// 自动还款标识
	private Integer autoWithdraw;
	
	// 最后更新时间
	private Date time;

	//状态，0：未激活  1：已激活
	private Integer status;
	
	public Integer getAutoRecharge() {
		return autoRecharge;
	}

	public void setAutoRecharge(Integer autoRecharge) {
		this.autoRecharge = autoRecharge;
	}

	public Integer getAutoWithdraw() {
		return autoWithdraw;
	}

	public void setAutoWithdraw(Integer autoWithdraw) {
		this.autoWithdraw = autoWithdraw;
	}

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

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public Double getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(Double freezeAmount) {
		this.freezeAmount = freezeAmount;
	}

	public String getPassword() {
		return "";
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAutoInvest() {
		return autoInvest;
	}

	public void setAutoInvest(Integer autoInvest) {
		this.autoInvest = autoInvest;
	}

	public Integer getAutoRepay() {
		return autoRepay;
	}

	public void setAutoRepay(Integer autoRepay) {
		this.autoRepay = autoRepay;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", userId=" + userId + ", balance="
				+ balance + ", availableBalance=" + availableBalance
				+ ", freezeAmount=" + freezeAmount + ", password=" + password
				+ ", autoInvest=" + autoInvest + ", autoRepay=" + autoRepay
				+ ", autoRecharge=" + autoRecharge + ", autoWithdraw="
				+ autoWithdraw + ", time=" + time + ", status=" + status + "]";
	}
	
}
