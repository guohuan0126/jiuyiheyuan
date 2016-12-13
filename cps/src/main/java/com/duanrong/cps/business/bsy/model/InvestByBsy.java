package com.duanrong.cps.business.bsy.model;

import java.util.Date;

public class InvestByBsy {
	
	//投资id
	private String investId;
	//预期收益
	private double  interest;
	//投资金额
	private double investMoney;
	//年化利率
	private double rate;
	//投资状态
	private String investStatus;
	//投资时间
	private Date investTime;
	//项目编号
	private String loanId;
	//项目名称
	private String loanName;
	//项目周期月份
	private String month;
	//项目周期天数
	private String days;
	//项目周期类型
	private String operationType;
	//用户编号
	private String userId;
	//用户姓名
	private String userName;
	//用户手机号
	private String mobileNumber;
	//周期
	private String loanTime;
	
	private Integer deadline;
	
	//投资来源
	private String userSource; 
	
	//红包id
	private String redId;
	//红包金额
	private Double redMoney; 
	//红包利率
	private Double redRate;
	//红包类型
	private String redType;
	
	private String redMoneyOrRate;
	//活动id
	private Integer activityId;
	//活动name
	private String activityName;
	
	//自动投标还是手动投标
	private String isAutoInvest;
	
	//用户来源
	private String infoUserSource;
	
	
	public String getRedId() {
		return redId;
	}
	public void setRedId(String redId) {
		this.redId = redId;
	}
	public Double getRedMoney() {
		return redMoney;
	}
	public void setRedMoney(Double redMoney) {
		this.redMoney = redMoney;
	}
	public Double getRedRate() {
		return redRate;
	}
	public void setRedRate(Double redRate) {
		this.redRate = redRate;
	}
	public String getRedType() {
		return redType;
	}
	public void setRedType(String redType) {
		this.redType = redType;
	}
	public String getLoanTime() {
		return loanTime;
	}
	public void setLoanTime(String loanTime) {
		this.loanTime = loanTime;
	}
	public double getInvestMoney() {
		return investMoney;
	}
	public void setInvestMoney(double investMoney) {
		this.investMoney = investMoney;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public String getInvestId() {
		return investId;
	}
	public void setInvestId(String investId) {
		this.investId = investId;
	}
	public String getInvestStatus() {
		return investStatus;
	}
	public void setInvestStatus(String investStatus) {
		this.investStatus = investStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Date getInvestTime() {
		return investTime;
	}
	public void setInvestTime(Date investTime) {
		this.investTime = investTime;
	}
	
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public Integer getDeadline() {
		return deadline;
	}
	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}
	public String getUserSource() {
		return userSource;
	}
	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}
	public String getRedMoneyOrRate() {
		return redMoneyOrRate;
	}
	public void setRedMoneyOrRate(String redMoneyOrRate) {
		this.redMoneyOrRate = redMoneyOrRate;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getIsAutoInvest() {
		return isAutoInvest;
	}
	public void setIsAutoInvest(String isAutoInvest) {
		this.isAutoInvest = isAutoInvest;
	}
	public String getInfoUserSource() {
		return infoUserSource;
	}
	public void setInfoUserSource(String infoUserSource) {
		this.infoUserSource = infoUserSource;
	}
	
}
