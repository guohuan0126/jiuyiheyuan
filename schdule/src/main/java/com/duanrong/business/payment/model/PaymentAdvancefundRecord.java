package com.duanrong.business.payment.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * @Description 垫付资金记录
 * @author JD
 * @CreateDate 2016年4月26日15:11:13
 */
public class PaymentAdvancefundRecord extends BaseModel{

	private static final long serialVersionUID = -7006613429906673336L;
	
	private String id;
	//充值/提出金额
	private double money;
	//类型 充值、提出
	private String type;
	//创建者
	private String creater;
	//创建时间
	private Date createTime;
	//第三方ID
	private String paymentId;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	@Override
	public String toString() {
		return "PaymentAdvancefundRecord [id=" + id + ", money=" + money
				+ ", type=" + type + ", creater=" + creater + ", createTime="
				+ createTime + ", paymentId=" + paymentId + "]";
	}
	
}
