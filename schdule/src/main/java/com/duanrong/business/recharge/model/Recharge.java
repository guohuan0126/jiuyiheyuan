package com.duanrong.business.recharge.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * @Description: 充值
 * @Author: 林志明
 * @CreateDate: Sep 11, 2014
 */
public class Recharge extends BaseModel {

	private static final long serialVersionUID = -7518276027821870602L;

	private String id;
	private String userId;
	private Date time;
	// 到账金额
	private Double actualMoney;
	private Date successTime;
	private String status;
	// 是否为管理员充值(0,1)
	private String isRechargedByAdmin;
	private Double fee;
	// 充值方式
	private String rechargeWay;
	private String mnumber;
    
    private String realname;
    
    private String start;
    private String end;
    private int num;
    private String type;
    
    private String paymentId;
    
    
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getMnumber() {
		return mnumber;
	}

	public void setMnumber(String mnumber) {
		this.mnumber = mnumber;
	}

	
	

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Double getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(Double actualMoney) {
		this.actualMoney = actualMoney;
	}

	public Date getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsRechargedByAdmin() {
		return isRechargedByAdmin;
	}

	public void setIsRechargedByAdmin(String isRechargedByAdmin) {
		this.isRechargedByAdmin = isRechargedByAdmin;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getRechargeWay() {
		return rechargeWay;
	}

	public void setRechargeWay(String rechargeWay) {
		this.rechargeWay = rechargeWay;
	}

	@Override
	public String toString() {
		return "Recharge [id=" + id + ", userId=" + userId + ", time=" + time
				+ ", actualMoney=" + actualMoney + ", successTime="
				+ successTime + ", status=" + status + ", isRechargedByAdmin="
				+ isRechargedByAdmin + ", fee=" + fee + ", rechargeWay="
				+ rechargeWay +",mnumber="+mnumber+",start="+start+",end="+end+",realname="+realname+ "]";
	}

}