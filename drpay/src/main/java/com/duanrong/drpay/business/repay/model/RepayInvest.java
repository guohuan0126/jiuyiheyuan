package com.duanrong.drpay.business.repay.model;

import java.util.Date;

public class RepayInvest {
		
	private Integer id;
	private String investId;
	private String repayId;
	private Double corpus;
	private Double interest;
	private double rewardMoney;  //奖励
	private int status;
	private Date repayTime;
	/**
	 * 项目放款之前的利息补贴
	 */
	private Double investAllowanceInterest;
	private String investUserId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInvestId() {
		return investId;
	}
	public void setInvestId(String investId) {
		this.investId = investId;
	}
	public String getRepayId() {
		return repayId;
	}
	public void setRepayId(String repayId) {
		this.repayId = repayId;
	}
	public Double getCorpus() {
		return corpus;
	}
	public void setCorpus(Double corpus) {
		this.corpus = corpus;
	}
	public Double getInterest() {
		return interest;
	}
	public void setInterest(Double interest) {
		this.interest = interest;
	}
	public double getRewardMoney() {
		return rewardMoney;
	}
	public void setRewardMoney(double rewardMoney) {
		this.rewardMoney = rewardMoney;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getRepayTime() {
		return repayTime;
	}
	public void setRepayTime(Date repayTime) {
		this.repayTime = repayTime;
	}
	public Double getInvestAllowanceInterest() {
		return investAllowanceInterest;
	}
	public void setInvestAllowanceInterest(Double investAllowanceInterest) {
		this.investAllowanceInterest = investAllowanceInterest;
	}
	public String getInvestUserId() {
		return investUserId;
	}
	public void setInvestUserId(String investUserId) {
		this.investUserId = investUserId;
	}
	@Override
	public String toString() {
		return "RepayInvest [id=" + id + ", investId=" + investId
				+ ", repayId=" + repayId + ", corpus=" + corpus + ", interest="
				+ interest + ", rewardMoney=" + rewardMoney + ", status="
				+ status + ", repayTime=" + repayTime
				+ ", investAllowanceInterest=" + investAllowanceInterest
				+ ", investUserId=" + investUserId + "]";
	}
	
	
}
