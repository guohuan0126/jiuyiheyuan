package com.duanrong.cps.business.platform.model;

import java.util.Date;

public class PushUserAccount {


	private String id;
	private String userId;
	private Double tradeAmount; //在投金额
	private Double waitInterest; //预期总收益
	private Double amount; //总资产
	private Double balance; //闲置资金
	private Date createTime;
	private String investId;
	private String type; 
	
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
	public Double getTradeAmount() {
		return tradeAmount;
	}
	public void setTradeAmount(Double tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	public Double getWaitInterest() {
		return waitInterest;
	}
	public void setWaitInterest(Double waitInterest) {
		this.waitInterest = waitInterest;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getInvestId() {
		return investId;
	}
	public void setInvestId(String investId) {
		this.investId = investId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
