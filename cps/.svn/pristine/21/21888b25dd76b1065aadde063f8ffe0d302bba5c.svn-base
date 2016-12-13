package com.duanrong.cps.business.repay.model;

import java.util.Date;

import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.user.model.User;

import base.model.BaseModel;



public class Repay extends BaseModel {

	private static final long serialVersionUID = -2587443311432951040L;

	private String id;
	private User user;
	private String userId;
	private Loan loan;
	private String loanId;
	/**
	 * 当前还款为第几期
	 */
	private Integer period;
	/**
	 * 还款时间
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

	// 本地还款需要补得利息
	private double repayAllowanceInterest;
	private String loanName;
	private String realname;
   
    //项目地点分公司
	private String itemAddress;
	//项目类型
	private String loanType;
	/**
	 * @return the itemAddress
	 */
	public String getItemAddress() {
		return itemAddress;
	}

	/**
	 * @param itemAddress the itemAddress to set
	 */
	public void setItemAddress(String itemAddress) {
		this.itemAddress = itemAddress;
	}

	/**
	 * @return the loanType
	 */
	public String getLoanType() {
		return loanType;
	}

	/**
	 * @param loanType the loanType to set
	 */
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	/**
	 * @return the borrowerName
	 */
	public String getBorrowerName() {
		return borrowerName;
	}

	/**
	 * @param borrowerName the borrowerName to set
	 */
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	/**
	 * @return the yaCarAndGPS
	 */
	public String getYaCarAndGPS() {
		return yaCarAndGPS;
	}

	/**
	 * @param yaCarAndGPS the yaCarAndGPS to set
	 */
	public void setYaCarAndGPS(String yaCarAndGPS) {
		this.yaCarAndGPS = yaCarAndGPS;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	//实际借款人
	private String borrowerName;
	//借款方式
	private String yaCarAndGPS;
	//新增/展期
	private String remark;

	//补息发送状态
	private int sendAllowancStatus;
	
	//红包奖励发送状态
	private int sendRedpacketStatus;
	
	//补息发送时间
	private Date sendAllowanceTime;
	
	//红包奖励发送时间
	private Date sendRedpacketTime;
	

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

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

	/**
	 * @return the repayAllowanceInterest
	 */
	public double getRepayAllowanceInterest() {
		return repayAllowanceInterest;
	}

	/**
	 * @param repayAllowanceInterest
	 *            the repayAllowanceInterest to set
	 */
	public void setRepayAllowanceInterest(double repayAllowanceInterest) {
		this.repayAllowanceInterest = repayAllowanceInterest;
	}

	
	
	public int getSendAllowancStatus() {
		return sendAllowancStatus;
	}

	public void setSendAllowancStatus(int sendAllowancStatus) {
		this.sendAllowancStatus = sendAllowancStatus;
	}

	public int getSendRedpacketStatus() {
		return sendRedpacketStatus;
	}

	public void setSendRedpacketStatus(int sendRedpacketStatus) {
		this.sendRedpacketStatus = sendRedpacketStatus;
	}

	public Date getSendAllowanceTime() {
		return sendAllowanceTime;
	}

	public void setSendAllowanceTime(Date sendAllowanceTime) {
		this.sendAllowanceTime = sendAllowanceTime;
	}

	public Date getSendRedpacketTime() {
		return sendRedpacketTime;
	}

	public void setSendRedpacketTime(Date sendRedpacketTime) {
		this.sendRedpacketTime = sendRedpacketTime;
	}

	@Override
	public String toString() {
		return "Repay [id=" + id + ", user=" + user + ", userId=" + userId
				+ ", loan=" + loan + ", loanId=" + loanId + ", period="
				+ period + ", time=" + time + ", repayDay=" + repayDay
				+ ", corpus=" + corpus + ", interest=" + interest
				+ ", defaultInterest=" + defaultInterest + ", status=" + status
				+ ", operationTime=" + operationTime + ", bringForwardRepay="
				+ bringForwardRepay + ", repayAllowanceInterest="
				+ repayAllowanceInterest + ", loanName=" + loanName
				+ ", realname=" + realname + ", sendAllowancStatus="
				+ sendAllowancStatus + ", sendRedpacketStatus="
				+ sendRedpacketStatus + ", sendAllowanceTime="
				+ sendAllowanceTime + ", sendRedpacketTime="
				+ sendRedpacketTime + "]";
	}

	

}