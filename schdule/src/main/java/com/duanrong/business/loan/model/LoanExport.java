package com.duanrong.business.loan.model;

import java.util.Date;

/**
 * loan封装类,导出用
 * @author xiao
 * @date 上午11:55:57
 */
public class LoanExport {
	
	private Date commitTime;
	private String loanName;
	private String detail;
	private double money;
	private String deal;
	private double rate;
	private String itemAddress;
	private String customerManagerName;
	private String realname;
	private Double loanGuranteeFee;
	private String remark;
	private String itemRate;
	private String borrowerName;
		
	
	public String getBorrowerName() {
		return borrowerName;
	}
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}
	
	public String getCustomerManagerName() {
		return customerManagerName;
	}
	public void setCustomerManagerName(String customerManagerName) {
		this.customerManagerName = customerManagerName;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public Double getLoanGuranteeFee() {
		return loanGuranteeFee;
	}
	public void setLoanGuranteeFee(Double loanGuranteeFee) {
		this.loanGuranteeFee = loanGuranteeFee;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getItemRate() {
		return itemRate;
	}
	public void setItemRate(String itemRate) {
		this.itemRate = itemRate;
	}
	public Date getCommitTime() {
		return commitTime;
	}
	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getDeal() {
		return deal;
	}
	public void setDeal(String deal) {
		this.deal = deal;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getItemAddress() {
		return itemAddress;
	}
	public void setItemAddress(String itemAddress) {
		this.itemAddress = itemAddress;
	}
	
	
}
