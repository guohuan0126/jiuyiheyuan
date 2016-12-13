package com.duanrong.business.user.model;

import java.util.Date;
import java.util.List;

public class UserInfoConfirmation {

	private String userName;
	private String user_addr;
	private Date investTime;
	private String loanName;
	private String loanId;
	private double investMoney;
	private String operationType;
	private String deadline;
	private String day;
	private String reteTime;
	private double rate;
	private double interest;
	
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public String getUser_addr() {
		return user_addr;
	}
	public void setUser_addr(String user_addr) {
		this.user_addr = user_addr;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getInvestTime() {
		return investTime;
	}
	public void setInvestTime(Date investTime) {
		this.investTime = investTime;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public double getInvestMoney() {
		return investMoney;
	}
	public void setInvestMoney(double investMoney) {
		this.investMoney = investMoney;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getReteTime() {
		return reteTime;
	}
	public void setReteTime(String reteTime) {
		this.reteTime = reteTime;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	
}
