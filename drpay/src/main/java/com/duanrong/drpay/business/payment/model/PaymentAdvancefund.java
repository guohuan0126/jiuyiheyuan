package com.duanrong.drpay.business.payment.model;

/**
 * @Description 垫付资金信息
 * @author JD
 * @CreateDate 2016年4月26日15:11:13
 */
public class PaymentAdvancefund{

	private String id;
	//垫付剩余资金
	private double money;
	//告警阀值
	private double warnMoney;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public double getWarnMoney() {
		return warnMoney;
	}
	public void setWarnMoney(double warnMoney) {
		this.warnMoney = warnMoney;
	}
	@Override
	public String toString() {
		return "PaymentAdvancefund [id=" + id + ", money=" + money
				+ ", warnMoney=" + warnMoney + "]";
	}
	
	
}
