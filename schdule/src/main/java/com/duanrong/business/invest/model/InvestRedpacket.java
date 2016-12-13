package com.duanrong.business.invest.model;

import java.util.Date;

import base.model.BaseModel;

public class InvestRedpacket extends BaseModel {

	private static final long serialVersionUID = 1L;

	private String id;
	private double investAllowanceInterest;
	private double rewardMoney;
	private String investId;
	private String userId;
	private int sendAllowanceStatus;
	private Date sendAllowanceTime;
	private String sendAllowanceResult;
	private int sendRedpacketStatus;
	private Date sendRedpacketTime;
	private String allowanceOrder;
	private String repackedOrder;
	private String sendRedpacketResult;
	private String loanId;
	private int repackedId;
	private Date createTime;
	private String loanName;
	private Date giveMoneyTime;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getInvestAllowanceInterest() {
		return investAllowanceInterest;
	}
	public void setInvestAllowanceInterest(double investAllowanceInterest) {
		this.investAllowanceInterest = investAllowanceInterest;
	}
	public double getRewardMoney() {
		return rewardMoney;
	}
	public void setRewardMoney(double rewardMoney) {
		this.rewardMoney = rewardMoney;
	}
	public String getInvestId() {
		return investId;
	}
	public void setInvestId(String investId) {
		this.investId = investId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getSendAllowanceStatus() {
		return sendAllowanceStatus;
	}
	public void setSendAllowanceStatus(int sendAllowanceStatus) {
		this.sendAllowanceStatus = sendAllowanceStatus;
	}
	public Date getSendAllowanceTime() {
		return sendAllowanceTime;
	}
	public void setSendAllowanceTime(Date sendAllowanceTime) {
		this.sendAllowanceTime = sendAllowanceTime;
	}
	public String getSendAllowanceResult() {
		return sendAllowanceResult;
	}
	public void setSendAllowanceResult(String sendAllowanceResult) {
		this.sendAllowanceResult = sendAllowanceResult;
	}
	public int getSendRedpacketStatus() {
		return sendRedpacketStatus;
	}
	public void setSendRedpacketStatus(int sendRedpacketStatus) {
		this.sendRedpacketStatus = sendRedpacketStatus;
	}
	public Date getSendRedpacketTime() {
		return sendRedpacketTime;
	}
	public void setSendRedpacketTime(Date sendRedpacketTime) {
		this.sendRedpacketTime = sendRedpacketTime;
	}
	public String getAllowanceOrder() {
		return allowanceOrder;
	}
	public void setAllowanceOrder(String allowanceOrder) {
		this.allowanceOrder = allowanceOrder;
	}
	public String getRepackedOrder() {
		return repackedOrder;
	}
	public void setRepackedOrder(String repackedOrder) {
		this.repackedOrder = repackedOrder;
	}
	public String getSendRedpacketResult() {
		return sendRedpacketResult;
	}
	public void setSendRedpacketResult(String sendRedpacketResult) {
		this.sendRedpacketResult = sendRedpacketResult;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public int getRepackedId() {
		return repackedId;
	}
	public void setRepackedId(int repackedId) {
		this.repackedId = repackedId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	
	
	public Date getGiveMoneyTime() {
		return giveMoneyTime;
	}
	public void setGiveMoneyTime(Date giveMoneyTime) {
		this.giveMoneyTime = giveMoneyTime;
	}
	@Override
	public String toString() {
		return "InvestRedpacket [id=" + id + ", investAllowanceInterest="
				+ investAllowanceInterest + ", rewardMoney=" + rewardMoney
				+ ", investId=" + investId + ", userId=" + userId
				+ ", sendAllowanceStatus=" + sendAllowanceStatus
				+ ", sendAllowanceTime=" + sendAllowanceTime
				+ ", sendAllowanceResult=" + sendAllowanceResult
				+ ", sendRedpacketStatus=" + sendRedpacketStatus
				+ ", sendRedpacketTime=" + sendRedpacketTime
				+ ", allowanceOrder=" + allowanceOrder + ", repackedOrder="
				+ repackedOrder + ", sendRedpacketResult="
				+ sendRedpacketResult + ", loanId=" + loanId + ", repackedId="
				+ repackedId + ", createTime=" + createTime + ", loanName="
				+ loanName + "]";
	}
	
	
}
