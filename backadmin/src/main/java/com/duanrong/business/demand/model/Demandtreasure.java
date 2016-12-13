package com.duanrong.business.demand.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * @Description: 短信
 * @Author: 林志明
 * @CreateDate: Nov 24, 2014
 */
public class Demandtreasure extends BaseModel {

	private static final long serialVersionUID = 4028643806162628481L;

	private String id;
	private String name;
	private Double availableMoney;
	private Double sumMoney;
	private Double investMaxmoney;
	private Date createTime;
	private Date updateTime;
	private String loanInfo;
	//项目可投开始时间
	private Integer startTime;
	//项目可投结束时间
	private Integer endTime;
	//每天提现次数
	private Integer outNumber;

	
	
	public Integer getStartTime() {
		return startTime;
	}
	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}
	public Integer getEndTime() {
		return endTime;
	}
	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}
	public Integer getOutNumber() {
		return outNumber;
	}
	public void setOutNumber(Integer outNumber) {
		this.outNumber = outNumber;
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
		this.availableMoney = availableMoney;
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
	public void setInvestMaxmoney(double investMaxmoney) {
		this.investMaxmoney = investMaxmoney;
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

}