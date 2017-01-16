package com.duanrong.drpay.business.loan.model;

import java.util.Date;
import java.util.List;
import com.duanrong.drpay.business.invest.model.Invest;
import com.duanrong.drpay.business.user.model.User;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-8-28 上午11:16:46
 * @Description : drsoa com.duanrong.business.loan.model Loan.java
 * 
 */
public class Loan  {

	// id
	private String id;
	
	//子标id
	private String subloanId;
	
	// 项目名称
	private String name;
	// 借款人(用户表id 外键)
	private User borrowMoneyUser;
	// 借款人(用户表id 外键)
	private String borrowMoneyUserID;
	
	// 借款合同存储路径
	private String contract;
	// 借款总金额
	private Double totalmoney;
	// 投资起点金额
	private Double investOriginMoney;
	// 单位递增金额
	private Double increaseMoney;
	// 借款利率(不是百分比形式)
	private Double rate;

	// 项目类型
	private String loanType;
	// 借款管理费
	private Double loanGuranteeFee;
	// 计算方式
	private String operationType;
	// 还款方式
	private String repayType;
	// 是否提前还款
	private String beforeRepay;
	// 借款期限(月)
	private Integer deadline;
	// 借款期限(天)
	private Integer day;
	// 预计开始执行时间
	private Date expectTime;
	// 项目状态
	private String status;
	// 是否为测试项目
	private String textItem;
	// 提交时间
	private Date commitTime;
	// 放款操作时间 + 1
	private Date giveMoneyTime;
	// 项目发起人
	private User sponsorUser;
	// 借款合同名称
	private String contractName;

	private String type;
	//到期时间
	private Date finishTime;
	// 最大投资金额
	private Double maxInvestMoney;
	// 预计执行之间格式化
	private String expectTimeFormat;

	private List<Invest> invests;

	private Integer symbol;

	// 剩余金额，前台用
	private Double remainAmount;
	
	// 新手专享
	private String newbieEnjoy;

	/**
	 * 计息时间规则
	 */
	private String interestRule;

	/**
	 * 放款操作时间
	 */
	private Date giveMoneyOperationTime;

	/**
	 * 项目放款之前的利息补贴
	 */
	private Double loanAllowanceInterest;

	/**
	 * 机构专享
	 */
	private String organizationExclusive;
	
	/**
	 * 是否可投
	 */
	private int whetherInvested;
	
	private String loanSpacialType;
	
	private int isBeforeRepay;
	
	private int stock;

	/**
	 * 是否是散标或者理财包
	 */
	private String standardOrProject;
	
	//项目是否已在存管注册
	private int drpayStatus;

	
	
	public String getSubloanId() {
		return subloanId;
	}

	public void setSubloanId(String subloanId) {
		this.subloanId = subloanId;
	}

	public int getDrpayStatus() {
		return drpayStatus;
	}

	public void setDrpayStatus(int drpayStatus) {
		this.drpayStatus = drpayStatus;
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

	public User getBorrowMoneyUser() {
		return borrowMoneyUser;
	}

	public void setBorrowMoneyUser(User borrowMoneyUser) {
		this.borrowMoneyUser = borrowMoneyUser;
	}

	public String getBorrowMoneyUserID() {
		return borrowMoneyUserID;
	}

	public void setBorrowMoneyUserID(String borrowMoneyUserID) {
		this.borrowMoneyUserID = borrowMoneyUserID;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public Double getTotalmoney() {
		return totalmoney;
	}

	public void setTotalmoney(Double totalmoney) {
		this.totalmoney = totalmoney;
	}

	public Double getInvestOriginMoney() {
		return investOriginMoney;
	}

	public void setInvestOriginMoney(Double investOriginMoney) {
		this.investOriginMoney = investOriginMoney;
	}

	public Double getIncreaseMoney() {
		return increaseMoney;
	}

	public void setIncreaseMoney(Double increaseMoney) {
		this.increaseMoney = increaseMoney;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public Double getLoanGuranteeFee() {
		return loanGuranteeFee;
	}

	public void setLoanGuranteeFee(Double loanGuranteeFee) {
		this.loanGuranteeFee = loanGuranteeFee;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
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

	public Date getExpectTime() {
		return expectTime;
	}

	public void setExpectTime(Date expectTime) {
		this.expectTime = expectTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTextItem() {
		return textItem;
	}

	public void setTextItem(String textItem) {
		this.textItem = textItem;
	}

	public Date getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	public Date getGiveMoneyTime() {
		return giveMoneyTime;
	}

	public void setGiveMoneyTime(Date giveMoneyTime) {
		this.giveMoneyTime = giveMoneyTime;
	}

	public User getSponsorUser() {
		return sponsorUser;
	}

	public void setSponsorUser(User sponsorUser) {
		this.sponsorUser = sponsorUser;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getMaxInvestMoney() {
		return maxInvestMoney;
	}

	public void setMaxInvestMoney(Double maxInvestMoney) {
		this.maxInvestMoney = maxInvestMoney;
	}

	public String getExpectTimeFormat() {
		return expectTimeFormat;
	}

	public void setExpectTimeFormat(String expectTimeFormat) {
		this.expectTimeFormat = expectTimeFormat;
	}

	public List<Invest> getInvests() {
		return invests;
	}

	public void setInvests(List<Invest> invests) {
		this.invests = invests;
	}

	public Integer getSymbol() {
		return symbol;
	}

	public void setSymbol(Integer symbol) {
		this.symbol = symbol;
	}

	public Double getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(Double remainAmount) {
		this.remainAmount = remainAmount;
	}

	public String getNewbieEnjoy() {
		return newbieEnjoy;
	}

	public void setNewbieEnjoy(String newbieEnjoy) {
		this.newbieEnjoy = newbieEnjoy;
	}

	public String getInterestRule() {
		return interestRule;
	}

	public void setInterestRule(String interestRule) {
		this.interestRule = interestRule;
	}

	public Date getGiveMoneyOperationTime() {
		return giveMoneyOperationTime;
	}

	public void setGiveMoneyOperationTime(Date giveMoneyOperationTime) {
		this.giveMoneyOperationTime = giveMoneyOperationTime;
	}

	public Double getLoanAllowanceInterest() {
		return loanAllowanceInterest;
	}

	public void setLoanAllowanceInterest(Double loanAllowanceInterest) {
		this.loanAllowanceInterest = loanAllowanceInterest;
	}

	public String getOrganizationExclusive() {
		return organizationExclusive;
	}

	public void setOrganizationExclusive(String organizationExclusive) {
		this.organizationExclusive = organizationExclusive;
	}

	public int getWhetherInvested() {
		return whetherInvested;
	}

	public void setWhetherInvested(int whetherInvested) {
		this.whetherInvested = whetherInvested;
	}

	public String getLoanSpacialType() {
		return loanSpacialType;
	}

	public void setLoanSpacialType(String loanSpacialType) {
		this.loanSpacialType = loanSpacialType;
	}

	public int getIsBeforeRepay() {
		return isBeforeRepay;
	}

	public void setIsBeforeRepay(int isBeforeRepay) {
		this.isBeforeRepay = isBeforeRepay;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	
	public String getStandardOrProject() {
		return standardOrProject;
	}

	public void setStandardOrProject(String standardOrProject) {
		this.standardOrProject = standardOrProject;
	}

	@Override
	public String toString() {
		return "Loan [id=" + id + ", name=" + name + ", borrowMoneyUser="
				+ borrowMoneyUser + ", borrowMoneyUserID=" + borrowMoneyUserID
				+ ", contract=" + contract + ", totalmoney=" + totalmoney
				+ ", investOriginMoney=" + investOriginMoney
				+ ", increaseMoney=" + increaseMoney + ", rate=" + rate
				+ ", loanType=" + loanType + ", loanGuranteeFee="
				+ loanGuranteeFee + ", operationType=" + operationType
				+ ", repayType=" + repayType + ", beforeRepay=" + beforeRepay
				+ ", deadline=" + deadline + ", day=" + day + ", expectTime="
				+ expectTime + ", status=" + status + ", textItem=" + textItem
				+ ", commitTime=" + commitTime + ", giveMoneyTime="
				+ giveMoneyTime + ", sponsorUser=" + sponsorUser
				+ ", contractName=" + contractName + ", type=" + type
				+ ", finishTime=" + finishTime + ", maxInvestMoney="
				+ maxInvestMoney + ", expectTimeFormat=" + expectTimeFormat
				+ ", invests=" + invests + ", symbol=" + symbol
				+ ", remainAmount=" + remainAmount + ", newbieEnjoy="
				+ newbieEnjoy + ", interestRule=" + interestRule
				+ ", giveMoneyOperationTime=" + giveMoneyOperationTime
				+ ", loanAllowanceInterest=" + loanAllowanceInterest
				+ ", organizationExclusive=" + organizationExclusive
				+ ", whetherInvested=" + whetherInvested + ", loanSpacialType="
				+ loanSpacialType + ", isBeforeRepay=" + isBeforeRepay
				+ ", stock=" + stock + ", standardOrProject="
				+ standardOrProject + "]";
	}

	

}