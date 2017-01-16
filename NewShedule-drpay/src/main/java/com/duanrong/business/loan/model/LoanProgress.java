package com.duanrong.business.loan.model;

/**
 * @Description: 借款项目进度
 * @Author: 林志明
 * @CreateDate: Dec 24, 2014
 */
public class LoanProgress {
	// 项目状态
	private Boolean underway;
	// 总金额
	private Double allMoney;
	// 筹款中的总金额
	private Double sumMoney;
	// 已投资的总金额
	private Double investMoney;

	public Boolean getUnderway() {
		return underway;
	}

	public void setUnderway(Boolean underway) {
		this.underway = underway;
	}

	public Double getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(Double allMoney) {
		this.allMoney = allMoney;
	}

	public Double getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(Double sumMoney) {
		this.sumMoney = sumMoney;
	}

	public Double getInvestMoney() {
		return investMoney;
	}

	public void setInvestMoney(Double investMoney) {
		this.investMoney = investMoney;
	}

	@Override
	public String toString() {
		return "LoanProgress [underway=" + underway + ", allMoney=" + allMoney
				+ ", sumMoney=" + sumMoney + ", investMoney=" + investMoney
				+ "]";
	}

}
