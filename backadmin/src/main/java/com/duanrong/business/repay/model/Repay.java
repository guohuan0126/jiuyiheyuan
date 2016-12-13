package com.duanrong.business.repay.model;

import java.util.Date;

import base.model.BaseModel;

import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.user.model.User;

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

	private Integer isBeforeRepay;
	
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
   private String  accountingDepartment;
	
	private String companyno;
	//新手标
	private String newbieEnjoy;
	
	public String getNewbieEnjoy() {
		return newbieEnjoy;
	}

	public void setNewbieEnjoy(String newbieEnjoy) {
		this.newbieEnjoy = newbieEnjoy;
	}

	public String getCompanyno() {
		return companyno;
	}

	public void setCompanyno(String companyno) {
		this.companyno = companyno;
	}

	public String  getAccountingDepartment() {
		return accountingDepartment;
	}

	public void setAccountingDepartment(String accountingDepartment) {
		this.accountingDepartment = accountingDepartment;
	}

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

	
	
	public Integer getIsBeforeRepay() {
		return isBeforeRepay;
	}

	public void setIsBeforeRepay(Integer isBeforeRepay) {
		this.isBeforeRepay = isBeforeRepay;
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
	// 预计开始执行时间
	private Date commitTime;
	// 计算方式
	private String operationType;
	// 借款期限(月)
	private Integer deadline;
	// 借款期限(天)
	private Integer day;
	// 借款利率(不是百分比形式)
	private Double rate;
	//借款期限月、日
	private String loanTerm;
	//统计利息和
	private Double interestAnd;
	//统计本金和
	private Double principalAnd;

	
	/**
	 * @return the interestAnd
	 */
	public Double getInterestAnd() {
		return interestAnd;
	}

	/**
	 * @param interestAnd the interestAnd to set
	 */
	public void setInterestAnd(Double interestAnd) {
		this.interestAnd = interestAnd;
	}

	/**
	 * @return the principalAnd
	 */
	public Double getPrincipalAnd() {
		return principalAnd;
	}

	/**
	 * @param principalAnd the principalAnd to set
	 */
	public void setPrincipalAnd(Double principalAnd) {
		this.principalAnd = principalAnd;
	}

	/**
	 * @return the loanTerm
	 */
	public String getLoanTerm() {
		return loanTerm;
	}

	/**
	 * @param loanTerm the loanTerm to set
	 */
	public void setLoanTerm(String loanTerm) {
		this.loanTerm = loanTerm;
	}

	/**
	 * 车牌号
	 */
	private String licensePlateNumber;
	

	/**
	 * @return the commitTime
	 */
	public Date getCommitTime() {
		return commitTime;
	}

	/**
	 * @param commitTime the commitTime to set
	 */
	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	/**
	 * @return the operationType
	 */
	public String getOperationType() {
		return operationType;
	}

	/**
	 * @param operationType the operationType to set
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	/**
	 * @return the deadline
	 */
	public Integer getDeadline() {
		return deadline;
	}

	/**
	 * @param deadline the deadline to set
	 */
	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}

	/**
	 * @return the day
	 */
	public Integer getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(Integer day) {
		this.day = day;
	}

	/**
	 * @return the rate
	 */
	public Double getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}

	/**
	 * @return the licensePlateNumber
	 */
	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}

	/**
	 * @param licensePlateNumber the licensePlateNumber to set
	 */
	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}

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