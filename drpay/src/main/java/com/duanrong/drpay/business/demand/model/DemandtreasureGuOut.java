package com.duanrong.drpay.business.demand.model;

import java.util.Date;

import com.duanrong.drpay.business.user.model.User;

/**
 * 活期宝转入记录表
 * 记录用户转入的操作
 * @author JD
 *
 */
public class DemandtreasureGuOut {

	private String id;
	private String userId;
	//转入资金
	private double money;
	//发起时间
	private Date sendedTime;
	//到账时间
	private Date confirmTime;
	private Date freezeTime;
	//转出途径
	private String transferWay;
	//状态
	private String status;
	private String type;
	private String requestXml;
	private String responseXml;
	private String confirmreqXml;
	private String confirmrespXml;
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public Date getSendedTime() {
		return sendedTime;
	}
	public void setSendedTime(Date sendedTime) {
		this.sendedTime = sendedTime;
	}
	public Date getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	public Date getFreezeTime() {
		return freezeTime;
	}
	public void setFreezeTime(Date freezeTime) {
		this.freezeTime = freezeTime;
	}
	public String getTransferWay() {
		return transferWay;
	}
	public void setTransferWay(String transferWay) {
		this.transferWay = transferWay;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRequestXml() {
		return requestXml;
	}
	public void setRequestXml(String requestXml) {
		this.requestXml = requestXml;
	}
	public String getResponseXml() {
		return responseXml;
	}
	public void setResponseXml(String responseXml) {
		this.responseXml = responseXml;
	}
	public String getConfirmreqXml() {
		return confirmreqXml;
	}
	public void setConfirmreqXml(String confirmreqXml) {
		this.confirmreqXml = confirmreqXml;
	}
	public String getConfirmrespXml() {
		return confirmrespXml;
	}
	public void setConfirmrespXml(String confirmrespXml) {
		this.confirmrespXml = confirmrespXml;
	}

}