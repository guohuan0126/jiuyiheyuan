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
	private String userSource;
	private Date registerTime;
	private String userSourceIsNull;
	private Date time;
	// 到账金额
	private Double actualMoney;
	private String status;
	private String bankCardId;
	public String getRegisterTimeStart() {
		return registerTimeStart;
	}

	public void setRegisterTimeStart(String registerTimeStart) {
		this.registerTimeStart = registerTimeStart;
	}
	
	public String getUserSourceIsNull() {
		return userSourceIsNull;
	}

	public void setUserSourceIsNull(String userSourceIsNull) {
		this.userSourceIsNull = userSourceIsNull;
	}

	public String getRegisterTimeEnd() {
		return registerTimeEnd;
	}

	public void setRegisterTimeEnd(String registerTimeEnd) {
		this.registerTimeEnd = registerTimeEnd;
	}

	private String registerTimeStart;
	private String registerTimeEnd;
	
	private BankCard bankCard;
	/**
	 * 是否为加急提现
	 */
	private String withdrawType;
	
	private String paymentId;

	private String userType;
    
	
	
    public String getUserSource() {
		return userSource;
	}

	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	
	public String getWithdrawType() {
		return withdrawType;
	}

	public void setWithdrawType(String withdrawType) {
		this.withdrawType = withdrawType;
	}

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

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
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
