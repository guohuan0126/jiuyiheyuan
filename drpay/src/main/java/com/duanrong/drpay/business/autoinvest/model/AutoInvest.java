package com.duanrong.drpay.business.autoinvest.model;

import java.util.Arrays;
import java.util.Date;

/**
 * @Description: 自动投标
 * @Author: 林志明
 * @CreateDate: Nov 27, 2014
 */
public class AutoInvest {
	
	private String userId;
	/**
	 * 每次投标金额
	 */
	private Double investMoney;
	/**
	 * 借款的最小利率
	 */
	private Double minRate;

	/**
	 * 借款的最大利率
	 */
	private Double maxRate;
	/**
	 * 借款的最短时间
	 */
	private Integer minDeadline;
	/**
	 * 借款的最长时间
	 */
	private Integer maxDeadline;

	/**
	 * 借款的最小融资金额
	 */
	private Double maxMoney;
	/**
	 * 借款的最大融资金额
	 */
	private Double minMoney;
	/**
	 * 贷款类型
	 */
	private String loanType;

	/**
	 * 账户保留余额
	 */
	private Double remainMoney;
	/**
	 * 上次自动投标时间
	 */
	private Date lastAutoInvestTime;

	private Integer seqNum;

	/**
	 * 状态（开启 关闭 ）
	 */
	private String status;

	private String[] loanTypes;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getInvestMoney() {
		return investMoney;
	}

	public void setInvestMoney(Double investMoney) {
		this.investMoney = investMoney;
	}

	public Double getMinRate() {
		return minRate;
	}

	public void setMinRate(Double minRate) {
		this.minRate = minRate;
	}

	public Double getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(Double maxRate) {
		this.maxRate = maxRate;
	}

	public Integer getMinDeadline() {
		return minDeadline;
	}

	public void setMinDeadline(Integer minDeadline) {
		this.minDeadline = minDeadline;
	}

	public Integer getMaxDeadline() {
		return maxDeadline;
	}

	public void setMaxDeadline(Integer maxDeadline) {
		this.maxDeadline = maxDeadline;
	}

	public Double getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(Double maxMoney) {
		this.maxMoney = maxMoney;
	}

	public Double getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(Double minMoney) {
		this.minMoney = minMoney;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public Double getRemainMoney() {
		return remainMoney;
	}

	public void setRemainMoney(Double remainMoney) {
		this.remainMoney = remainMoney;
	}

	public Date getLastAutoInvestTime() {
		return lastAutoInvestTime;
	}

	public void setLastAutoInvestTime(Date lastAutoInvestTime) {
		this.lastAutoInvestTime = lastAutoInvestTime;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String[] getLoanTypes() {
		return loanTypes;
	}

	public void setLoanTypes(String[] loanTypes) {
		this.loanTypes = loanTypes;
	}

	@Override
	public String toString() {
		return "AutoInvest [userId=" + userId + ", investMoney=" + investMoney
				+ ", minRate=" + minRate + ", maxRate=" + maxRate
				+ ", minDeadline=" + minDeadline + ", maxDeadline="
				+ maxDeadline + ", maxMoney=" + maxMoney + ", minMoney="
				+ minMoney + ", loanType=" + loanType + ", remainMoney="
				+ remainMoney + ", lastAutoInvestTime=" + lastAutoInvestTime
				+ ", seqNum=" + seqNum + ", status=" + status + ", loanTypes="
				+ Arrays.toString(loanTypes) + "]";
	}

}
