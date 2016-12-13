package com.duanrong.business.demand.model;

import java.util.Date;

import base.model.BaseModel;

import com.duanrong.business.user.model.User;

/**
 * 活期宝转入记录表
 * 记录用户转入的操作
 * @author JD
 *
 */
public class DemandtreasureTransferIn extends BaseModel {

	private static final long serialVersionUID = 4028643806162628481L;

	private String id;
	private String userId;
	//转入资金
	private double money;
	//发起时间
	private Date sendedTime;
	//冻结时间
	private Date freezeTime;
	//直接接口确认时间
	private Date confirmTime;
	//转入途径
	private String transferWay;
	//状态
	private String status;
	//
	private String demandTreasureId;
	private double summoney;
	private int total;
	private User user;
	
	private int fork;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public double getSummoney() {
		return summoney;
	}
	public void setSummoney(double summoney) {
		this.summoney = summoney;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
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
	public Date getFreezeTime() {
		return freezeTime;
	}
	public void setFreezeTime(Date freezeTime) {
		this.freezeTime = freezeTime;
	}
	public Date getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
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
	public String getDemandTreasureId() {
		return demandTreasureId;
	}
	public void setDemandTreasureId(String demandTreasureId) {
		this.demandTreasureId = demandTreasureId;
	}
	public int getFork() {
		return fork;
	}
	public void setFork(int fork) {
		this.fork = fork;
	}	
}