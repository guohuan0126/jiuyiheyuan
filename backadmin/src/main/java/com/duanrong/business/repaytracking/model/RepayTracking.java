package com.duanrong.business.repaytracking.model;

import java.util.Date;

import base.model.BaseModel;

public class RepayTracking extends BaseModel {
	private static final long serialVersionUID = -1817562346567401318L;

	/***************** Loan *******************/
	// 项目ID
	private String loanId;
	// 项目名称
	private String loanName;
	// 借款合同存储路径
	private String contract;
	// 借款总金额
	private Double loanAmount;
	// 借款利率(不是百分比形式)
	private Double rate;
	// 借款利率的百分比展示形式
	private Double ratePercent;
	// 项目类型
	private String loanType;
	// 计算方式
	private String operationType;
	// 是否提前还款
	private String beforeRepay;
	// 借款期限(月)
	private Integer deadline;
	// 借款期限(天)
	private Integer day;

	/***************** Repay *******************/
	// 当前还款为第几期
	private Integer period;
	// 还款日
	private Date repayDay;
	// 本金
	private Double corpus;
	// 利息
	private Double interest;

	/***************** Invest *******************/
	// 投资金额
	private Double investAmount;
	// 投资金额
	private Date investTime;

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

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getRatePercent() {
		return ratePercent;
	}

	public void setRatePercent(Double ratePercent) {
		this.ratePercent = ratePercent;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getBeforeRepay() {
		return beforeRepay;
	}

	public void setBeforeRepay(String beforeRepay) {
		this.beforeRepay = beforeRepay;
	}

	public Integer getDeadline() {
		return deadline;
	}

	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Date getRepayDay() {
		return repayDay;
	}

	public void setRepayDay(Date repayDay) {
		this.repayDay = repayDay;
	}

	public Double getCorpus() {
		return corpus;
	}

	public void setCorpus(Double corpus) {
		this.corpus = corpus;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(Double investAmount) {
		this.investAmount = investAmount;
	}

	public Date getInvestTime() {
		return investTime;
	}

	public void setInvestTime(Date investTime) {
		this.investTime = investTime;
	}

	@Override
	public String toString() {
		return "RepayTracking [loanId=" + loanId + ", loanName=" + loanName
				+ ", contract=" + contract + ", loanAmount=" + loanAmount
				+ ", rate=" + rate + ", ratePercent=" + ratePercent
				+ ", loanType=" + loanType + ", operationType=" + operationType
				+ ", beforeRepay=" + beforeRepay + ", deadline=" + deadline
				+ ", day=" + day + ", period=" + period + ", repayDay="
				+ repayDay + ", corpus=" + corpus + ", interest=" + interest
				+ ", investAmount=" + investAmount + ", investTime="
				+ investTime + "]";
	}

}