package com.duanrong.drpay.business.account.model;

import java.util.Date;

public class PaymentAccount {
	// ID自增
	private Integer id;
	// 京东、富友、宝付
	private String channel;
	// 余额
	private Double balance;
	// 可用余额
	private Double availableBalance;
	// 冻结金额
	private Double freezeAmount;
	// 最后更新时间
	private Date time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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
		return "PaymentAccount [id=" + id + ", channel=" + channel
				+ ", balance=" + balance + ", availableBalance="
				+ availableBalance + ", freezeAmount=" + freezeAmount
				+ ", time=" + time + "]";
	}

}
