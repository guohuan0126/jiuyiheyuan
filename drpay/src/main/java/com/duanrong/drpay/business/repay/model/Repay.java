package com.duanrong.drpay.business.repay.model;

import java.util.Date;

import com.duanrong.drpay.business.loan.model.Loan;
import com.duanrong.drpay.business.user.model.User;

public class Repay {
	
	private String id;
	private User user;
	private String userId;
	private Loan loan;
	private String loanId;
	
	/*
	 * 还款处理中的状态
	 * sended发起
	 * freeze借款人账户已经冻结（包括银行和本地）
	 * success完成
	 */
	private String handleStatus;
	private String operator;
	/**
	 * 计息日（放款日）
	 */
	private Date giveMoneyDate;

	/**
	 * 项目放款之前的利息补贴
	 */
	private Double repayAllowanceInterest;
	
	private int isBeforeRepay;

	public Double getRepayAllowanceInterest() {
		return repayAllowanceInterest;
	}

	public void setRepayAllowanceInterest(Double repayAllowanceInterest) {
		this.repayAllowanceInterest = repayAllowanceInterest;
	}

	public Date getGiveMoneyDate() {
		return giveMoneyDate;
	}

	public void setGiveMoneyDate(Date giveMoneyDate) {
		this.giveMoneyDate = giveMoneyDate;
	}

	/**
	 * 当前还款为第几期
	 */
	private Integer period;
	/**
	 * 本期还款初始时间
	 */
	private Date time;
	/**
	 * 还款日
	 */
	private Date repayDay;
	/**
	 * 本金
	 */
	private Double corpus;
	/**
	 * 利息
	 */
	private Double interest;

	/**
	 * 罚息（逾期利息+网站逾期罚息）
	 */
	private Double defaultInterest;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 还款操作时间
	 */
	private Date operationTime;

	// 是否可提现还款
	private String bringForwardRepay;

	// Constructors

	/** default constructor */
	public Repay() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
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

	public Double getDefaultInterest() {
		return defaultInterest;
	}

	public void setDefaultInterest(Double defaultInterest) {
		this.defaultInterest = defaultInterest;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public String getBringForwardRepay() {
		return bringForwardRepay;
	}

	public void setBringForwardRepay(String bringForwardRepay) {
		this.bringForwardRepay = bringForwardRepay;
	}

	public int getIsBeforeRepay() {
		return isBeforeRepay;
	}

	public void setIsBeforeRepay(int isBeforeRepay) {
		this.isBeforeRepay = isBeforeRepay;
	}


	public String getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return "Repay [id=" + id + ", user=" + user + ", userId=" + userId
				+ ", loan=" + loan + ", loanId=" + loanId + ", period="
				+ period + ", time=" + time + ", repayDay=" + repayDay
				+ ", corpus=" + corpus + ", interest=" + interest
				+ ", defaultInterest=" + defaultInterest + ", status=" + status
				+ ", operationTime=" + operationTime + ", bringForwardRepay="
				+ bringForwardRepay + "]";
	}

}