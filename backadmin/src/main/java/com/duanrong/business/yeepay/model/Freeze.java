package com.duanrong.business.yeepay.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * @Description: 冻结
 * @Author: wangjing
 * @CreateDate: Aug 29, 2014
 */
public class Freeze extends BaseModel {
	private static final long serialVersionUID = -8189198520914248838L;
	private String id;
	private String userId;
	private Double money;
	private String requestNo;
	private Date expiredTime;
	private Date freezeTime;
	private Date unFreezeTime;
	private String description;
	private String freezeUserId;
	private String unfreezeUserId;
	private double balance;
	private double freezeMoney;
	private double yeepaybalance;
	private double yeepayfreezeMoney;
	
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getFreezeMoney() {
		return freezeMoney;
	}
	public void setFreezeMoney(double freezeMoney) {
		this.freezeMoney = freezeMoney;
	}
	public double getYeepaybalance() {
		return yeepaybalance;
	}
	public void setYeepaybalance(double yeepaybalance) {
		this.yeepaybalance = yeepaybalance;
	}
	public double getYeepayfreezeMoney() {
		return yeepayfreezeMoney;
	}
	public void setYeepayfreezeMoney(double yeepayfreezeMoney) {
		this.yeepayfreezeMoney = yeepayfreezeMoney;
	}
	/**
	 * 0是冻结，1是非冻结(初始状态)，2是冻结后未成功，3是冻结后已解冻
	 */
	private Integer status;
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
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public Date getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}
	public Date getFreezeTime() {
		return freezeTime;
	}
	public void setFreezeTime(Date freezeTime) {
		this.freezeTime = freezeTime;
	}
	public Date getUnFreezeTime() {
		return unFreezeTime;
	}
	public void setUnFreezeTime(Date unFreezeTime) {
		this.unFreezeTime = unFreezeTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFreezeUserId() {
		return freezeUserId;
	}
	public void setFreezeUserId(String freezeUserId) {
		this.freezeUserId = freezeUserId;
	}
	public String getUnfreezeUserId() {
		return unfreezeUserId;
	}
	public void setUnfreezeUserId(String unfreezeUserId) {
		this.unfreezeUserId = unfreezeUserId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
