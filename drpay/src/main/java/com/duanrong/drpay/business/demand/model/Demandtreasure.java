package com.duanrong.drpay.business.demand.model;

import java.util.Date;

/**
 * @Description: 短信
 * @Author: 林志明
 * @CreateDate: Nov 24, 2014
 */
public class Demandtreasure {

	private String id;
	private String name;
	private Double availableMoney; //可投金额
	private Double sumMoney; //活期宝金额
	private Double investMaxmoney; //单用户最大可投金额
	private Double outMaxMoney; //当用户当日转出限额
	
	private double billMoney; //累计交易金额
	
	private double billInterest; //累计收益
	private double presentMoney; //本次开放金额
	
	private int userNum; //参与用户数量
	
	private Date createTime; 
	private Date updateTime; 
	private String loanInfo;
	
	//项目可投开始时间
	private int startTime;
	//项目可投结束时间
	private int endTime;
	//每天提现次数
	private int outNumber;	
	
	
	public Double getOutMaxMoney() {
		return outMaxMoney;
	}
	public void setOutMaxMoney(Double outMaxMoney) {
		this.outMaxMoney = outMaxMoney;
	}
	public int getUserNum() {
		return userNum;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public double getPresentMoney() {
		return presentMoney;
	}
	public void setPresentMoney(double presentMoney) {
		this.presentMoney = presentMoney;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getAvailableMoney() {
		return availableMoney;
	}
	public void setAvailableMoney(Double availableMoney) {
		this.availableMoney =  availableMoney;
	}
	public Double getSumMoney() {
		return sumMoney;
	}
	public void setSumMoney(Double sumMoney) {
		this.sumMoney = sumMoney;
	}
	public Double getInvestMaxmoney() {
		return investMaxmoney;
	}
	public void setInvestMaxmoney(Double investMaxmoney) {
		this.investMaxmoney = investMaxmoney;
	}
	public double getBillMoney() {
		return billMoney;
	}
	public void setBillMoney(double billMoney) {
		this.billMoney = billMoney;
	}
	public double getBillInterest() {
		return billInterest;
	}
	public void setBillInterest(double billInterest) {
		this.billInterest = billInterest;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getLoanInfo() {
		return loanInfo;
	}
	public void setLoanInfo(String loanInfo) {
		this.loanInfo = loanInfo;
	}
	
	
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	public int getOutNumber() {
		return outNumber;
	}
	public void setOutNumber(int outNumber) {
		this.outNumber = outNumber;
	}
	@Override
	public String toString() {
		return "Demandtreasure [id=" + id + ", name=" + name
				+ ", availableMoney=" + availableMoney + ", sumMoney="
				+ sumMoney + ", investMaxmoney=" + investMaxmoney
				+ ", billMoney=" + billMoney + ", billInterest=" + billInterest
				+ ", presentMoney=" + presentMoney + ", userNum=" + userNum
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", loanInfo=" + loanInfo + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", outNumber=" + outNumber + "]";
	}
	
	
}