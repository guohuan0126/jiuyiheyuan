package com.duanrong.cps.business.bjs.model;

public class UserAccount {
	
	private String  p2pUserId; //userID
	private Double  sumIncome; // 	累计总收益
	private Double  totalBalance; // 	账户总金额
	private Double availableBalance; //用户可用余额
	private String remark; // 	备注
	private String channel; //渠道，八金社值为（bjs）,非bjs的导流交易，此字段传NULL即可
	public String getP2pUserId() {
		return p2pUserId;
	}
	public void setP2pUserId(String p2pUserId) {
		this.p2pUserId = p2pUserId;
	}
	public Double getSumIncome() {
		return sumIncome;
	}
	public void setSumIncome(Double sumIncome) {
		this.sumIncome = sumIncome;
	}
	public Double getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(Double totalBalance) {
		this.totalBalance = totalBalance;
	}
	public Double getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	

}
