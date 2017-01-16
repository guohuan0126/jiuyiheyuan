package com.duanrong.drpay.business.account.model;

import java.util.Date;

public class PaymentBill {
	// ID自增
	private Integer id;
	// 渠道
	private String channel;
	// 流水号
	private String requestNo;
	// 当前余额
	private Double balance;
	// 变动金额
	private Double money;
	// 冻结金额
	private Double freezeAmount;
	// 类型
	private String type;
	// 描述
	private String typeInfo;
	// 业务类型
	private String businessType;
	// 时间
	private Date time;

	// 开始时间和结束时间，查询条件用，不做序列化
	private Date beginTime;
	private Date endTime;

	public Double getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(Double freezeAmount) {
		this.freezeAmount = freezeAmount;
	}


	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeInfo() {
		return typeInfo;
	}

	public void setTypeInfo(String typeInfo) {
		this.typeInfo = typeInfo;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	@Override
	public String toString() {
		return "PaymentBill [id=" + id + ", channel=" + channel
				+ ", requestNo=" + requestNo + ", balance=" + balance
				+ ", money=" + money + ", freezeAmount=" + freezeAmount
				+ ", type=" + type + ", typeInfo=" + typeInfo
				+ ", business_type=" + businessType + ", time=" + time
				+ ", beginTime=" + beginTime + ", endTime=" + endTime + "]";
	}

}
