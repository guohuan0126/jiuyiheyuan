package com.duanrong.drpay.business.account.model;

import java.util.Date;

public class PlatformAccount {
	// ID
	private Integer id;
	
	//账户类型
	private String name;
	
	// 余额(10, 2)
	private Double balance;
	// 可用余额(10, 2)
	private Double availableBalance;
	// 冻结金额(10, 2)
	private Double freezeAmount;
	// 最后更新时间
	private Date time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "PlatformAccount [id=" + id + ", name=" + name + ", balance="
				+ balance + ", availableBalance=" + availableBalance
				+ ", freezeAmount=" + freezeAmount + ", time=" + time + "]";
	}

}
