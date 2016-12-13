package base.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-9-1 上午11:17:27
 * @Description : drsoa Maven Webapp com.duanrong.business.invest.model
 *              Invest.java
 * 
 */
public class Invest extends BaseModel implements Serializable {
	private static final long serialVersionUID = -9122071688964736291L;

	// 主键id
	private String id;
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
	// 已还本金
	private double paidMoney;
	// 已还利息
	private double paidInterest;
	// 预计收益
	private Double interest;
	// 回池金额(跟投奖励相关字段)
	private Double returnPondMoney;
	// 备用字段
	private Double minInvestMoney;
	// 该笔投资是否需要回池(跟投奖励相关字段)
	private String returnPond;

	// 用户来源
	private String userSource;

	private String[] conditions;

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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Invest [id=" + id + ", investUserID=" + investUserID
				+ ", loan=" + loan + ", loanId=" + loanId + ", time=" + time
				+ ", isAutoInvest=" + isAutoInvest + ", status=" + status
				+ ", rate=" + rate + ", ratePercent=" + ratePercent + ", type="
				+ type + ", money=" + money + ", paidMoney=" + paidMoney
				+ ", paidInterest=" + paidInterest + ", interest=" + interest
				+ ", returnPondMoney=" + returnPondMoney + ", minInvestMoney="
				+ minInvestMoney + ", returnPond=" + returnPond
				+ ", userSource=" + userSource + ", conditions="
				+ Arrays.toString(conditions) + "]";
	}

	
}
