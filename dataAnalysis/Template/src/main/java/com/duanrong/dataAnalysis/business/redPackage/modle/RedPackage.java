package com.duanrong.dataAnalysis.business.redPackage.modle;

public class RedPackage {
	
	private String id;
	private String name;
	private double redPackageMoney;
	private int count;
	//领取用户数
	private int byUserCount;
	//使用用户数
	private int usedUserCount;
	//红包发放数
	private int redPackageSendedCount;
	//已用总数
	private int usedCount;
	//过期总数
	private int unCount;
	//未用总数
	private int unUsedCount;
	//使用总数
	private int allUsedCount;
	//投入金额
	private double payMoney;
	//投资总额
	private double allPayMoney;
	//红包规则
	private String redPackageRule;
	
	public int getUsedCount() {
		return usedCount;
	}
	public void setUsedCount(int usedCount) {
		this.usedCount = usedCount;
	}
	
	public double getAllPayMoney() {
		return allPayMoney;
	}
	public void setAllPayMoney(double allPayMoney) {
		this.allPayMoney = allPayMoney;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getRedPackageMoney() {
		return redPackageMoney;
	}
	public void setRedPackageMoney(double redPackageMoney) {
		this.redPackageMoney = redPackageMoney;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getByUserCount() {
		return byUserCount;
	}
	public void setByUserCount(int byUserCount) {
		this.byUserCount = byUserCount;
	}
	public int getUsedUserCount() {
		return usedUserCount;
	}
	public void setUsedUserCount(int usedUserCount) {
		this.usedUserCount = usedUserCount;
	}
	public int getRedPackageSendedCount() {
		return redPackageSendedCount;
	}
	public void setRedPackageSendedCount(int redPackageSendedCount) {
		this.redPackageSendedCount = redPackageSendedCount;
	}
	public int getUnCount() {
		return unCount;
	}
	public void setUnCount(int unCount) {
		this.unCount = unCount;
	}
	public int getUnUsedCount() {
		return unUsedCount;
	}
	public void setUnUsedCount(int unUsedCount) {
		this.unUsedCount = unUsedCount;
	}
	public int getAllUsedCount() {
		return allUsedCount;
	}
	public void setAllUsedCount(int allUsedCount) {
		this.allUsedCount = allUsedCount;
	}
	public double getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(double payMoney) {
		this.payMoney = payMoney;
	}
	public String getRedPackageRule() {
		return redPackageRule;
	}
	public void setRedPackageRule(String redPackageRule) {
		this.redPackageRule = redPackageRule;
	}
	
	
	
}
