package com.duanrong.business.withdraw.model;

import java.util.Date;

import base.model.BaseModel;

import com.duanrong.business.bankcard.model.BankCard;

/**
 * @Description: 提现
 * @Author: 林志明
 * @CreateDate: Sep 11, 2014
 */
public class WithdrawCash extends BaseModel {

	private static final long serialVersionUID = -2070862600766079854L;

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
	
	//提现方式（NORMAL正常、URGENT加急）
	private String withdrawType;
	
	/**
	 * 是投资账户还是借款账户提现
	 */
	private String account;
	private String realname;
	private String start;
	private String end;
	private String mnumber;
	private int num;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getMnumber() {
		return mnumber;
	}

	public void setMnumber(String mnumber) {
		this.mnumber = mnumber;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	
	
	public String getWithdrawType() {
		return withdrawType;
	}

	public void setWithdrawType(String withdrawType) {
		this.withdrawType = withdrawType;
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

	@Override
	public String toString() {
		return "WithdrawCash [id=" + id + ", userId=" + userId + ", time="
				+ time + ", actualMoney=" + actualMoney + ", status=" + status
				+ ", bankCardId=" + bankCardId + ", bankCard=" + bankCard
				+ ", fee=" + fee + ", cashFine=" + cashFine +",realname="+realname+",start="+start+",end="+end +",mnumber="+mnumber+", account="
				+ account + "]";
	}

}
