package com.duanrong.business.user.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * 账户统计信息
 * 
 * @author Lin Zhiming
 * @version Mar 2, 2015 10:26:00 AM
 */
public class AccountCount extends BaseModel {
	private static final long serialVersionUID = 6578241716900897848L;

	private String id;
	/**
	 * 本地账户总余额
	 */
	private Double sumBalance;

	/**
	 * 本地账户总的可用余额
	 */
	private Double sumAvailableAmount;
	/**
	 * 本地账户总的冻结金额
	 */
	private Double sumFreezeAmount;
	/**
	 * 易宝账户总余额
	 */
	private Double sumEbaoBalance;
	/**
	 * 易宝账户总的可用余额
	 */
	private Double sumEbaoAvailableAmount;
	/**
	 * 易宝账户总的冻结金额
	 */
	private Double sumEbaoFreezeAmount;
	/**
	 * 检查用户的数量 易宝开户的数量
	 */
	private Long allInvestorsNums;
	/**
	 * 注册用户总数
	 */
	private Long allUserNums;
	/**
	 * 每日新增用户数量
	 */
	private Long userNumsPerDay;
	/**
	 * 每日提现金额
	 */
	private Double sumWithdrawMoneyPerDay;
	/**
	 * 每日充值金额
	 */
	private Double sumRechargeMoneyPerDay;

	/**
	 * 检查时间
	 */
	private Date time;
	/**
	 * 开户用户数量
	 */
	private Long accountUserNums;
	/**
	 * 投资数量
	 */
	private Long allinvestNums;

	
	public Long getAllinvestNums() {
		return allinvestNums;
	}

	public void setAllinvestNums(Long allinvestNums) {
		this.allinvestNums = allinvestNums;
	}

	public Long getAccountUserNums() {
		return accountUserNums;
	}

	public void setAccountUserNums(Long accountUserNums) {
		this.accountUserNums = accountUserNums;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getSumBalance() {
		return sumBalance;
	}

	public void setSumBalance(Double sumBalance) {
		this.sumBalance = sumBalance;
	}

	public Double getSumAvailableAmount() {
		return sumAvailableAmount;
	}

	public void setSumAvailableAmount(Double sumAvailableAmount) {
		this.sumAvailableAmount = sumAvailableAmount;
	}

	public Double getSumFreezeAmount() {
		return sumFreezeAmount;
	}

	public void setSumFreezeAmount(Double sumFreezeAmount) {
		this.sumFreezeAmount = sumFreezeAmount;
	}

	public Double getSumEbaoBalance() {
		return sumEbaoBalance;
	}

	public void setSumEbaoBalance(Double sumEbaoBalance) {
		this.sumEbaoBalance = sumEbaoBalance;
	}

	public Double getSumEbaoAvailableAmount() {
		return sumEbaoAvailableAmount;
	}

	public void setSumEbaoAvailableAmount(Double sumEbaoAvailableAmount) {
		this.sumEbaoAvailableAmount = sumEbaoAvailableAmount;
	}

	public Double getSumEbaoFreezeAmount() {
		return sumEbaoFreezeAmount;
	}

	public void setSumEbaoFreezeAmount(Double sumEbaoFreezeAmount) {
		this.sumEbaoFreezeAmount = sumEbaoFreezeAmount;
	}

	public Long getAllInvestorsNums() {
		return allInvestorsNums;
	}

	public void setAllInvestorsNums(Long allInvestorsNums) {
		this.allInvestorsNums = allInvestorsNums;
	}

	public Long getAllUserNums() {
		return allUserNums;
	}

	public void setAllUserNums(Long allUserNums) {
		this.allUserNums = allUserNums;
	}

	public Long getUserNumsPerDay() {
		return userNumsPerDay;
	}

	public void setUserNumsPerDay(Long userNumsPerDay) {
		this.userNumsPerDay = userNumsPerDay;
	}

	public Double getSumWithdrawMoneyPerDay() {
		return sumWithdrawMoneyPerDay;
	}

	public void setSumWithdrawMoneyPerDay(Double sumWithdrawMoneyPerDay) {
		this.sumWithdrawMoneyPerDay = sumWithdrawMoneyPerDay;
	}

	public Double getSumRechargeMoneyPerDay() {
		return sumRechargeMoneyPerDay;
	}

	public void setSumRechargeMoneyPerDay(Double sumRechargeMoneyPerDay) {
		this.sumRechargeMoneyPerDay = sumRechargeMoneyPerDay;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "AccountCount [id=" + id + ", sumBalance=" + sumBalance
				+ ", sumAvailableAmount=" + sumAvailableAmount
				+ ", sumFreezeAmount=" + sumFreezeAmount + ", sumEbaoBalance="
				+ sumEbaoBalance + ", sumEbaoAvailableAmount="
				+ sumEbaoAvailableAmount + ", sumEbaoFreezeAmount="
				+ sumEbaoFreezeAmount + ", allInvestorsNums="
				+ allInvestorsNums + ", allUserNums=" + allUserNums
				+ ", userNumsPerDay=" + userNumsPerDay
				+ ", sumWithdrawMoneyPerDay=" + sumWithdrawMoneyPerDay
				+ ", sumRechargeMoneyPerDay=" + sumRechargeMoneyPerDay
				+ ", time=" + time + "]";
	}

}
