package com.duanrong.business.demand.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * @Description: 短信
 * @Author: 林志明
 * @CreateDate: Nov 24, 2014
 */
public class AvailableMoneyRecord extends BaseModel {

	private static final long serialVersionUID = 4028643806162628481L;

	private String id;
	private double money;
	private Date begintime;
	private Date endtime;
	private int status;
	private Date expectTime; //预计执行时间
	
	private String operatorid;
	private String type;
	private double expiredMoney;
	private double realExpiredMoney;
	private double opendMoney;
	
	
	public Date getExpectTime() {
		return expectTime;
	}
	public void setExpectTime(Date expectTime) {
		this.expectTime = expectTime;
	}
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
	public Date getBegintime() {
		return begintime;
	}
	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	public String getOperatorid() {
		return operatorid;
	}
	public void setOperatorid(String operatorid) {
		this.operatorid = operatorid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getExpiredMoney() {
		return expiredMoney;
	}
	public void setExpiredMoney(double expiredMoney) {
		this.expiredMoney = expiredMoney;
	}
	public double getRealExpiredMoney() {
		return realExpiredMoney;
	}
	public void setRealExpiredMoney(double realExpiredMoney) {
		this.realExpiredMoney = realExpiredMoney;
	}
	public double getOpendMoney() {
		return opendMoney;
	}
	public void setOpendMoney(double opendMoney) {
		this.opendMoney = opendMoney;
	}
	@Override
	public String toString() {
		return "AvailableMoneyRecord [id=" + id + ", money=" + money
				+ ", begintime=" + begintime + ", endtime=" + endtime
				+ ", status=" + status + ", expectTime=" + expectTime
				+ ", operatorid=" + operatorid + ", type=" + type
				+ ", expiredMoney=" + expiredMoney + ", realExpiredMoney="
				+ realExpiredMoney + ", opendMoney=" + opendMoney + "]";
	}

	
}