package com.duanrong.business.invest.model;

import java.util.Date;

/**
 * 理财计划匹配记录
 * @author xiao
 * @datetime 2016年12月30日 下午9:24:18
 */
public class InvestSubLoan {

	private int id;
	//子标投资id
	private String investSubloanId;
	
	//子标名称
	private String investSubloanName;
	
	//理财计划投资id
	private String investId;
	//子标id
	private String subloanId;
	//投资用户id
	private String userId;
	//子标匹配金额
	private double money;
	//子标应得利息
	private double interest;
	//子标已还本金
	private double paidMoney;
	//子标已换利息
	private double paidInterest;
	//子标匹配时间
	private Date investTime;
	//子标投资状态
	private String status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInvestSubloanId() {
		return investSubloanId;
	}
	public void setInvestSubloanId(String investSubloanId) {
		this.investSubloanId = investSubloanId;
	}
	public String getInvestSubloanName() {
		return investSubloanName;
	}
	public void setInvestSubloanName(String investSubloanName) {
		this.investSubloanName = investSubloanName;
	}
	public String getInvestId() {
		return investId;
	}
	public void setInvestId(String investId) {
		this.investId = investId;
	}
	public String getSubloanId() {
		return subloanId;
	}
	public void setSubloanId(String subloanId) {
		this.subloanId = subloanId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public double getPaidMoney() {
		return paidMoney;
	}
	public void setPaidMoney(double paidMoney) {
		this.paidMoney = paidMoney;
	}
	public double getPaidInterest() {
		return paidInterest;
	}
	public void setPaidInterest(double paidInterest) {
		this.paidInterest = paidInterest;
	}
	public Date getInvestTime() {
		return investTime;
	}
	public void setInvestTime(Date investTime) {
		this.investTime = investTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "InvestSubLoan [id=" + id + ", investSubloanId="
				+ investSubloanId + ", investId=" + investId + ", subloanId="
				+ subloanId + ", userId=" + userId + ", money=" + money
				+ ", interest=" + interest + ", paidMoney=" + paidMoney
				+ ", paidInterest=" + paidInterest + ", investTime="
				+ investTime + ", status=" + status + "]";
	}
	
}
