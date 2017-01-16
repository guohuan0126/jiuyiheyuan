package com.duanrong.business.autoinvest.model;

import java.util.Date;

import base.model.BaseModel;

import com.duanrong.business.user.model.User;
import com.duanrong.util.ArithUtil;

/**
 * @Description: 自动投标
 * @Author: 林志明
 * @CreateDate: Nov 27, 2014
 */
public class AutoInvest extends BaseModel {
	private static final long serialVersionUID = 4733078258397879141L;

	private String userId;
	private User user;
	private boolean isAutoInvest;
	/**
	 * 每次投标金额
	 */
	private Double investMoney;	
	
	/**
	 * 借款的最小利率
	 */
	private Double minRate;
	private Double minRatePercent;// 最小利率，整数。不存入数据库

	/**
	 * 借款的最大利率
	 */
	private Double maxRate;
	private Double maxRatePercent;// 最小利率，整数。不存入数据库
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
	private String[] conditions;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String[] getConditions() {
		return conditions;
	}

	public void setConditions(String[] conditions) {
		this.conditions = conditions;
	}

	public String[] getLoanTypes() {
		return loanTypes;
	}

	public void setLoanTypes(String[] loanTypes) {
		this.loanTypes = loanTypes;
	}

	
	
	public boolean isAutoInvest() {
		return isAutoInvest;
	}

	public void setAutoInvest(boolean isAutoInvest) {
		this.isAutoInvest = isAutoInvest;
	}

	public AutoInvest() {
	}

	/**
	 * @param userId
	 * @param user
	 * @param investMoney
	 * @param minRate
	 * @param maxRate
	 * @param minDeadline
	 * @param maxDeadline
	 * @param maxMoney
	 * @param minMoney
	 * @param lastAutoInvestTime
	 * @param seqNum
	 * @param status
	 */
	public AutoInvest(String userId, Double investMoney, Double minRate,
			Double maxRate, Integer minDeadline, Integer maxDeadline,
			Date lastAutoInvestTime, Integer seqNum, String status) {
		super();
		this.userId = userId;
		this.investMoney = investMoney;
		this.minRate = minRate;
		this.maxRate = maxRate;
		this.minDeadline = minDeadline;
		this.maxDeadline = maxDeadline;
		this.lastAutoInvestTime = lastAutoInvestTime;
		this.seqNum = seqNum;
		this.status = status;
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

	public String getUserId() {
		return userId;
	}

	/****************** 以下为利率的百分数形式，不包含% ******************/
	public Double getMinRatePercent() {
		if (this.minRatePercent == null && this.getMinRate() != null) {
			return ArithUtil.round(this.getMinRate() * 100, 2);
		}
		return minRatePercent;
	}

	public void setMinRatePercent(Double minRatePercent) {
		if (minRatePercent != null) {
			this.minRate = ArithUtil.div(minRatePercent, 100, 4);
		}
		this.minRatePercent = minRatePercent;
	}

	public Double getMaxRatePercent() {
		if (this.getMaxRate() != null) {
			return ArithUtil.round(this.getMaxRate() * 100, 2);
		}
		return maxRatePercent;
	}

	public void setMaxRatePercent(Double maxRatePercent) {
		if (maxRatePercent != null) {
			this.maxRate = ArithUtil.div(maxRatePercent, 100, 4);
		}
		this.maxRatePercent = maxRatePercent;
	}
	
	@Override
	public String toString() {
		return "AutoInvest [userId=" + userId + ", investMoney=" + investMoney
				+ ", minRate=" + minRate + ", minRatePercent=" + minRatePercent
				+ ", maxRate=" + maxRate + ", maxRatePercent=" + maxRatePercent
				+ ", minDeadline=" + minDeadline + ", maxDeadline="
				+ maxDeadline + ", maxMoney=" + maxMoney + ", minMoney="
				+ minMoney + ", loanType=" + loanType + ", remainMoney="
				+ remainMoney + ", lastAutoInvestTime=" + lastAutoInvestTime
				+ ", seqNum=" + seqNum + ", status=" + status + "]";
	}

}
