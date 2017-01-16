package com.duanrong.business.demand.model;

public class DemandTransferFork {

	private double validMoney;
	private DemandTreasureInvest invest;
	private DemandTreasureOpration opration;
	public double getValidMoney() {
		return validMoney;
	}
	public void setValidMoney(double validMoney) {
		this.validMoney = validMoney;
	}
	public DemandTreasureInvest getInvest() {
		return invest;
	}
	public void setInvest(DemandTreasureInvest invest) {
		this.invest = invest;
	}
	public DemandTreasureOpration getOpration() {
		return opration;
	}
	public void setOpration(DemandTreasureOpration opration) {
		this.opration = opration;
	}

}