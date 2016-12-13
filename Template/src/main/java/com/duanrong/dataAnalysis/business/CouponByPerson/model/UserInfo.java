package com.duanrong.dataAnalysis.business.CouponByPerson.model;

public class UserInfo {
/**
 * 根据用户id 显示的红包详细信息
 */
	
	
	private int redCount;
	private int usedCount;
	private double investMoney;
	private double sendedMoney;
	private double userMoney;
	private double allMoney;
	public double getAllMoney() {
		return allMoney;
	}
	public void setAllMoney(double allMoney) {
		this.allMoney = allMoney;
	}
	public int getRedCount() {
		return redCount;
	}
	public void setRedCount(int redCount) {
		this.redCount = redCount;
	}
	public int getUsedCount() {
		return usedCount;
	}
	public void setUsedCount(int usedCount) {
		this.usedCount = usedCount;
	}
	public double getInvestMoney() {
		return investMoney;
	}
	public void setInvestMoney(double investMoney) {
		this.investMoney = investMoney;
	}
	public double getSendedMoney() {
		return sendedMoney;
	}
	public void setSendedMoney(double sendedMoney) {
		this.sendedMoney = sendedMoney;
	}
	public double getUserMoney() {
		return userMoney;
	}
	public void setUserMoney(double userMoney) {
		this.userMoney = userMoney;
	}
	
	
	
}
