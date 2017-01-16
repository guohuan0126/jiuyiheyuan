package com.duanrong.drpay.jsonservice.param;

public class AutoInvestParamter extends Parameter{

	private static final long serialVersionUID = 7498212165872218130L;
	
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
	private Double minMoney;
	/**
	 * 借款的最大融资金额
	 */
	private Double maxMoney;
	/**
	 * 贷款类型
	 */
	private String loanType;


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

	public Double getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(Double minMoney) {
		this.minMoney = minMoney;
	}

	public Double getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(Double maxMoney) {
		this.maxMoney = maxMoney;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	@Override
	public String toString() {
		return "AutoInvestParamter [minRate=" + minRate
				+ ", maxRate=" + maxRate + ", minDeadline=" + minDeadline
				+ ", maxDeadline=" + maxDeadline + ", minMoney=" + minMoney
				+ ", maxMoney=" + maxMoney + ", loanType=" + loanType
				+ "]";
	}

}
