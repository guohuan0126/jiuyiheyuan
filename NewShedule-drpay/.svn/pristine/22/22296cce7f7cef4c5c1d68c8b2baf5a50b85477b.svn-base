package com.duanrong.business.invest.model;

import java.util.Date;

import base.model.BaseModel;

import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.user.model.User;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-9-1 上午11:17:27
 * @Description : drsoa Maven Webapp com.duanrong.business.invest.model
 *              Invest.java
 * 
 */
public class Invest extends BaseModel {
	private static final long serialVersionUID = -9122071688964736291L;

	
	// 主键id
	private String id;
	// 投资人
	private User user;
	// 投资人ID
	private String investUserID;
	// 投资的项目
	private Loan loan;
	// 组合条件查询使用
	private String loanId;
	// 投资时间
	private Date time;
	// 是否自动投标
	private Boolean isAutoInvest;
	// 投资状态
	private String status;
	// 注意，此处存储的不是百分比利率
	private Double rate;
	// 利率百分比形式
	private Double ratePercent;
	// 投资类型(本金保障计划之类)
	private String type;
	// 投资金额
	private Double money;
	// 投资金额
	private Double sumMoney;
	// 已还本金
	private double paidMoney;
	// 已还利息
	private double paidInterest;
	// 预计收益
	private Double interest;
	// 回池金额(跟投奖励相关字段)
	private Double returnPondMoney;

	//是否发送加息券
	private int redpacketForVisitor;
	// 可享受跟投奖励金额
	private Double transferMoney;

	// 备用字段
	private Double minInvestMoney;
	// 该笔投资是否需要回池(跟投奖励相关字段)
	private String returnPond;

	private int redpacketId;
	
	//双十一活动闯关关数
	private int flag;
	//双十一活动投资人ID
	private String userId;
	
	/** excel导出用 **/
	// 项目名称
	private String loanName;
	private int duration;
	private Date startDate;
	private Date endDate;

	private String email;
	// 项目类型
	private String loanType;

	// 投资用户身份证
	private String userIdCard;

	//归属地
	private String userProvince;		
	private String userCity;
	
	// 投资用户地址
	private String userHomeAddress;

	// 投资用户手机号
	private String userMobileNumber;

	// 投资用户历史投资次数
	private int investTotal;

	// 用户姓名
	private String investUserName;

	// 用户来源
	private String userSource;

	private String[] conditions;
	private int num;
	private int remarkNum;
	
	//借款管理费
	private double managementExpense;

	
	public int getRedpacketForVisitor() {
		return redpacketForVisitor;
	}

	public void setRedpacketForVisitor(int redpacketForVisitor) {
		this.redpacketForVisitor = redpacketForVisitor;
	}

	public int getRedpacketId() {
		return redpacketId;
	}

	public void setRedpacketId(int redpacketId) {
		this.redpacketId = redpacketId;
	}

	public String getUserProvince() {
		return userProvince;
	}

	public void setUserProvince(String userProvince) {
		this.userProvince = userProvince;
	}

	public String getUserCity() {
		return userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public int getRemarkNum() {
		return remarkNum;
	}

	public void setRemarkNum(int remarkNum) {
		this.remarkNum = remarkNum;
	}

	// 每笔投资利息补贴
	private double investAllowanceInterest;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the loan
	 */
	public Loan getLoan() {
		return loan;
	}

	/**
	 * @param loan
	 *            the loan to set
	 */
	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @return the isAutoInvest
	 */
	public Boolean getIsAutoInvest() {
		return isAutoInvest;
	}

	/**
	 * @param isAutoInvest
	 *            the isAutoInvest to set
	 */
	public void setIsAutoInvest(Boolean isAutoInvest) {
		this.isAutoInvest = isAutoInvest;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the rate
	 */
	public Double getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            the rate to set
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}

	/**
	 * @return the ratePercent
	 */
	public Double getRatePercent() {
		if (this.ratePercent == null && this.getRate() != null) {
			return this.getRate() * 100;
		}
		return ratePercent;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param ratePercent
	 *            the ratePercent to set
	 */
	public void setRatePercent(Double ratePercent) {
		this.ratePercent = ratePercent;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the money
	 */
	public Double getMoney() {
		return money;
	}

	/**
	 * @param money
	 *            the money to set
	 */
	public void setMoney(Double money) {
		this.money = money;
	}

	/**
	 * @return the paidMoney
	 */
	public double getPaidMoney() {
		return paidMoney;
	}

	/**
	 * @param paidMoney
	 *            the paidMoney to set
	 */
	public void setPaidMoney(double paidMoney) {
		this.paidMoney = paidMoney;
	}

	/**
	 * @return the paidInterest
	 */
	public double getPaidInterest() {
		return paidInterest;
	}

	/**
	 * @param paidInterest
	 *            the paidInterest to set
	 */
	public void setPaidInterest(double paidInterest) {
		this.paidInterest = paidInterest;
	}

	/**
	 * @return the interest
	 */
	public Double getInterest() {
		return interest;
	}

	/**
	 * @param interest
	 *            the interest to set
	 */
	public void setInterest(Double interest) {
		this.interest = interest;
	}

	/**
	 * @return the returnPondMoney
	 */
	public Double getReturnPondMoney() {
		return returnPondMoney;
	}

	/**
	 * @param returnPondMoney
	 *            the returnPondMoney to set
	 */
	public void setReturnPondMoney(Double returnPondMoney) {
		this.returnPondMoney = returnPondMoney;
	}

	/**
	 * @return the minInvestMoney
	 */
	public Double getMinInvestMoney() {
		return minInvestMoney;
	}

	/**
	 * @param minInvestMoney
	 *            the minInvestMoney to set
	 */
	public void setMinInvestMoney(Double minInvestMoney) {
		this.minInvestMoney = minInvestMoney;
	}

	/**
	 * @return the returnPond
	 */
	public String getReturnPond() {
		return returnPond;
	}

	/**
	 * @param returnPond
	 *            the returnPond to set
	 */
	public void setReturnPond(String returnPond) {
		this.returnPond = returnPond;
	}

	/**
	 * @return the investUserID
	 */
	public String getInvestUserID() {
		return investUserID;
	}

	/**
	 * @param investUserID
	 *            the investUserID to set
	 */
	public void setInvestUserID(String investUserID) {
		this.investUserID = investUserID;
	}

	/**
	 * @return the loanId
	 */
	public String getLoanId() {
		return loanId;
	}

	/**
	 * @param loanId
	 *            the loanId to set
	 */
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getUserSource() {
		return userSource;
	}

	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}

	public String[] getConditions() {
		return conditions;
	}

	public void setConditions(String[] conditions) {
		this.conditions = conditions;
	}

	public Double getTransferMoney() {
		return transferMoney;
	}

	public void setTransferMoney(Double transferMoney) {
		this.transferMoney = transferMoney;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */

	public String getLoanName() {
		return loanName;
	}

	@Override
	public String toString() {
		return "Invest [id=" + id + ", investUserID=" + investUserID
				+ ", loanId=" + loanId + ", time=" + time + ", isAutoInvest="
				+ isAutoInvest + ", status=" + status + ", rate=" + rate
				+ ", ratePercent=" + ratePercent + ", type=" + type
				+ ", money=" + money + ", paidMoney=" + paidMoney
				+ ", paidInterest=" + paidInterest + ", interest=" + interest
				+ ", returnPondMoney=" + returnPondMoney + ", minInvestMoney="
				+ minInvestMoney + ", returnPond=" + returnPond + ", loanName="
				+ loanName + "]";
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getInvestUserName() {
		return investUserName;
	}

	public void setInvestUserName(String investUserName) {
		this.investUserName = investUserName;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getUserIdCard() {
		return userIdCard;
	}

	public void setUserIdCard(String userIdCard) {
		this.userIdCard = userIdCard;
	}

	public String getUserHomeAddress() {
		return userHomeAddress;
	}

	public void setUserHomeAddress(String userHomeAddress) {
		this.userHomeAddress = userHomeAddress;
	}

	public String getUserMobileNumber() {
		return userMobileNumber;
	}

	public void setUserMobileNumber(String userMobileNumber) {
		this.userMobileNumber = userMobileNumber;
	}

	public int getInvestTotal() {
		return investTotal;
	}

	public void setInvestTotal(int investTotal) {
		this.investTotal = investTotal;
	}

	public Double getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(Double sumMoney) {
		this.sumMoney = sumMoney;
	}

	public double getInvestAllowanceInterest() {
		return investAllowanceInterest;
	}

	public void setInvestAllowanceInterest(double investAllowanceInterest) {
		this.investAllowanceInterest = investAllowanceInterest;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getManagementExpense() {
		return managementExpense;
	}

	public void setManagementExpense(double managementExpense) {
		this.managementExpense = managementExpense;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}



}
