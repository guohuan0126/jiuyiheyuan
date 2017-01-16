package com.duanrong.drpay.business.payment.model;

import com.duanrong.drpay.business.account.model.BankCard;

import java.util.Date;

/**
 * @Description: 提现
 * @Author: 林志明
 * @CreateDate: Sep 11, 2014
 */
public class WithdrawCash {

	private String id;
	private String userId;
	private Date time;
	// 到账金额
	private Double actualMoney;
	private String status;
	private String bankCardId;

	private BankCard bankCard;

	/**
	 * 手续费
	 */
	private Double fee;

	/**
	 * 提现罚金
	 */
	private Double cashFine;

	/**
	 * 是投资账户还是借款账户提现
	 */
	private String account;
	
	//提现方式（NORMAL正常、URGENT加急）
	private String withdrawType;
	
	private String paymentId;
	//出款时间
	private Date remitTime;
	//到账时间
	private Date completedTime;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Double getCashFine() {
		return cashFine;
	}

	public void setCashFine(Double cashFine) {
		this.cashFine = cashFine;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public BankCard getBankCard() {
		return bankCard;
	}

	public void setBankCard(BankCard bankCard) {
		this.bankCard = bankCard;
	}

	public String getWithdrawType() {
		return withdrawType;
	}

	public void setWithdrawType(String withdrawType) {
		this.withdrawType = withdrawType;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public Date getRemitTime() {
		return remitTime;
	}

	public void setRemitTime(Date remitTime) {
		this.remitTime = remitTime;
	}

	public Date getCompletedTime() {
		return completedTime;
	}

	public void setCompletedTime(Date completedTime) {
		this.completedTime = completedTime;
	}
	@Override
	public String toString() {
		return "WithdrawCash [id=" + id + ", userId=" + userId + ", time="
				+ time + ", actualMoney=" + actualMoney + ", status=" + status
				+ ", bankCardId=" + bankCardId + ", bankCard=" + bankCard
				+ ", fee=" + fee + ", cashFine=" + cashFine + ", account="
				+ account + ", withdrawType=" + withdrawType + "]";
	}
}