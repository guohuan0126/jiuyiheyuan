package com.duanrong.business.demand.model;

import java.util.Date;

import base.model.BaseModel;

public class DemandTreasureOpration extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String demandTreasureInvestId;
	private String demandTransferId;
	private String demandLoanId;
	private double money;
	private Date time;
	private String type;
	private String remark;
	
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
	
	
	public String getDemandTreasureInvestId() {
		return demandTreasureInvestId;
	}
	public void setDemandTreasureInvestId(String demandTreasureInvestId) {
		this.demandTreasureInvestId = demandTreasureInvestId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "DemandTreasureOpration [id=" + id + ", demandTreasureInvestId="
				+ demandTreasureInvestId + ", demandTransferId="
				+ demandTransferId + ", demandLoanId=" + demandLoanId
				+ ", money=" + money + ", time=" + time + ", type=" + type
				+ ", remark=" + remark + "]";
	}
	
	
}
