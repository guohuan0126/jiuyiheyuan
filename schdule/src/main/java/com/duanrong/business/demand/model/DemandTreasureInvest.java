package com.duanrong.business.demand.model;

import java.util.Date;

import base.model.BaseModel;

public class DemandTreasureInvest extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String demandTransferId;
	private String demandLoanId;
	private double money;
	private Date time;
	private String status;
	private String userId;
	private double totalMoney;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDemandTransferId() {
		return demandTransferId;
	}
	public void setDemandTransferId(String demandTransferId) {
		this.demandTransferId = demandTransferId;
	}
	public String getDemandLoanId() {
		return demandLoanId;
	}
	public void setDemandLoanId(String demandLoanId) {
		this.demandLoanId = demandLoanId;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
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
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
	public double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}
	@Override
	public String toString() {
		return "DemandTreasureInvest [id=" + id + ", demandTransferId="
				+ demandTransferId + ", demandLoanId=" + demandLoanId
				+ ", money=" + money + ", time=" + time + ", status=" + status
				+ "]";
	}
}
