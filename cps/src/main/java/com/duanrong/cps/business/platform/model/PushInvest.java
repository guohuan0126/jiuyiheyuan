package com.duanrong.cps.business.platform.model;

import java.util.Date;

public class PushInvest {

	private String id;
	private String userId;
	private String loanId;
	private Double amount;
	private Date investTime;
	private Date sendTime;
	private String investId;
	private Double interest;
	private String investChannel;
	private Date interestdate;
	private String type;
	private Double commission;   //佣金
	
	
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
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getInvestTime() {
		return investTime;
	}
	public void setInvestTime(Date investTime) {
		this.investTime = investTime;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getInvestId() {
		return investId;
	}
	public void setInvestId(String investId) {
		this.investId = investId;
	}
	public Double getInterest() {
		return interest;
	}
	public void setInterest(Double interest) {
		this.interest = interest;
	}
	public String getInvestChannel() {
		return investChannel;
	}
	public void setInvestChannel(String investChannel) {
		this.investChannel = investChannel;
	}
	public Date getInterestdate() {
		return interestdate;
	}
	public void setInterestdate(Date interestdate) {
		this.interestdate = interestdate;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getCommission() {
		return commission;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	
	
}
