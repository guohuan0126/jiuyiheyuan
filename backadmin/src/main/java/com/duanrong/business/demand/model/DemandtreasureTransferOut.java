package com.duanrong.business.demand.model;

import java.util.Date;

import com.duanrong.business.user.model.User;

import base.model.BaseModel;

/**
 * 活期宝转入记录表
 * 记录用户转入的操作
 * @author JD
 *
 */
public class DemandtreasureTransferOut extends BaseModel {

	private static final long serialVersionUID = 4028643806162628481L;

	private String id;
	private String userId;
	private String userSource;
	private String userSourceIsNull;
	private Date  registerTime;
	//转入资金
	private double money;
	//发起时间
	private Date sendedTime;
	//到账时间
	private Date successTime;
	//转出途径
	private String transferWay;
	//状态
	private String status;
	private String fork;
	
	private String detail;
	private double summoney;
	private int total;
	private User user;
	//本金
	private double principal;
	//利息
	private double interest;
	
	///-----时间查询条件
	private String sendedTimeBeg;//发起时间查询开始
	private String sendedTimeEnd;//发起时间查询结束
	
	
	
	public String getUserSourceIsNull() {
		return userSourceIsNull;
	}
	public void setUserSourceIsNull(String userSourceIsNull) {
		this.userSourceIsNull = userSourceIsNull;
	}
	public String getUserSource() {
		return userSource;
	}
	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public String getFork() {
		return fork;
	}
	public void setFork(String fork) {
		this.fork = fork;
	}
	public String getSendedTimeBeg() {
		return sendedTimeBeg;
	}
	public void setSendedTimeBeg(String sendedTimeBeg) {
		this.sendedTimeBeg = sendedTimeBeg;
	}
	public String getSendedTimeEnd() {
		return sendedTimeEnd;
	}
	public void setSendedTimeEnd(String sendedTimeEnd) {
		this.sendedTimeEnd = sendedTimeEnd;
	}
	
	public double getPrincipal() {
		return principal;
	}
	public void setPrincipal(double principal) {
		this.principal = principal;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
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
	public Date getSuccessTime() {
		return successTime;
	}
	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}