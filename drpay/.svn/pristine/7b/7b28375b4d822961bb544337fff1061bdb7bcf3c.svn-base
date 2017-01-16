package com.duanrong.drpay.business.loan.model;

import java.util.Date;
import java.util.List;

import com.duanrong.drpay.business.invest.model.InvestSubLoan;

/**
 * 理财计划子标model
 * 
 * @author xiao
 * @datetime 2016年12月30日 下午8:28:06
 */
public class SubLoan {

	private String id;
	/**
	 * 理财计划id
	 */
	private String loanId;

	/**
	 * 子标id
	 */
	private String subloanId;

	/**
	 * 子标名称
	 */
	private String subloanName;

	// 子标金额
	private double money;

	/**
	 * 子标状态
	 */
	private String subloanStatus;

	/**
	 * 剩余可分配金额
	 */
	private double allocationMoney;

	// 项目是否已在存管注册
	private int drpayStatus;
	
	// 借款利率(不是百分比形式)
	private Double rate;
	
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

	// 放款操作时间 + 1
	private Date giveMoneyTime;
	
	// 借款人(用户表id 外键)
	private String borrowMoneyUserID;
	
	private List<InvestSubLoan> investSubloans;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getSubloanId() {
		return subloanId;
	}

	public void setSubloanId(String subloanId) {
		this.subloanId = subloanId;
	}

	public String getSubloanName() {
		return subloanName;
	}

	public void setSubloanName(String subloanName) {
		this.subloanName = subloanName;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getSubloanStatus() {
		return subloanStatus;
	}

	public void setSubloanStatus(String subloanStatus) {
		this.subloanStatus = subloanStatus;
	}

	public double getAllocationMoney() {
		return allocationMoney;
	}

	public void setAllocationMoney(double allocationMoney) {
		this.allocationMoney = allocationMoney;
	}

	public int getDrpayStatus() {
		return drpayStatus;
	}

	public void setDrpayStatus(int drpayStatus) {
		this.drpayStatus = drpayStatus;
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
	
	public Date getGiveMoneyTime() {
		return giveMoneyTime;
	}

	public void setGiveMoneyTime(Date giveMoneyTime) {
		this.giveMoneyTime = giveMoneyTime;
	}
	
	public String getBorrowMoneyUserID() {
		return borrowMoneyUserID;
	}

	public void setBorrowMoneyUserID(String borrowMoneyUserID) {
		this.borrowMoneyUserID = borrowMoneyUserID;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	
	
	public List<InvestSubLoan> getInvestSubloans() {
		return investSubloans;
	}

	public void setInvestSubloans(List<InvestSubLoan> investSubloans) {
		this.investSubloans = investSubloans;
	}

	@Override
	public String toString() {
		return "SubLoan [id=" + id + ", loanId=" + loanId + ", subloanId="
				+ subloanId + ", subloanName=" + subloanName + ", money="
				+ money + ", subloanStatus=" + subloanStatus
				+ ", allocationMoney=" + allocationMoney + ", drpayStatus="
				+ drpayStatus + ", operationType=" + operationType
				+ ", repayType=" + repayType + ", beforeRepay=" + beforeRepay
				+ ", deadline=" + deadline + ", day=" + day + "]";
	}

	
	
	

}
