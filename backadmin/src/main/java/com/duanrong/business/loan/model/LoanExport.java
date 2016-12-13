package com.duanrong.business.loan.model;

import java.util.Date;

/**
 * loan封装类,导出用
 * 
 * @author xiao
 * @date 上午11:55:57
 */
public class LoanExport {

	private String id;
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
	private String textItem;
	private String loanType;
	private String licensePlateNumber;
	private String pawnandgps;
	private Double calculateMoneyNeedRaised;
	private String brank;
	private String repayType;
	private Double singleMoney;
	
	private String borrowerIdCard;
	//机构名称
	private String organizationExclusiveExport;
	//核算单位
	private String accountingDepartmentExport;
	
	public String getOrganizationExclusiveExport() {
		return organizationExclusiveExport;
	}

	public void setOrganizationExclusiveExport(String organizationExclusiveExport) {
		this.organizationExclusiveExport = organizationExclusiveExport;
	}

	public String getAccountingDepartmentExport() {
		return accountingDepartmentExport;
	}

	public void setAccountingDepartmentExport(String accountingDepartmentExport) {
		this.accountingDepartmentExport = accountingDepartmentExport;
	}

	public String getBorrowerIdCard() {
		return borrowerIdCard;
	}

	public void setBorrowerIdCard(String borrowerIdCard) {
		this.borrowerIdCard = borrowerIdCard;
	}

	public Double getSingleMoney() {
		return singleMoney;
	}

	public void setSingleMoney(Double singleMoney) {
		this.singleMoney = singleMoney;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

	public String getBrank() {
		return brank;
	}

	public void setBrank(String brank) {
		this.brank = brank;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getCalculateMoneyNeedRaised() {
		return calculateMoneyNeedRaised;
	}

	public void setCalculateMoneyNeedRaised(Double calculateMoneyNeedRaised) {
		this.calculateMoneyNeedRaised = calculateMoneyNeedRaised;
	}

	public String getPawnandgps() {
		return pawnandgps;
	}

	public void setPawnandgps(String pawnandgps) {
		this.pawnandgps = pawnandgps;
	}

	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}

	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getTextItem() {
		return textItem;
	}

	public void setTextItem(String textItem) {
		this.textItem = textItem;
	}

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
