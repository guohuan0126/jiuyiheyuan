package com.duanrong.business.user.model;



import base.model.BaseModel;

/**
 * @Description:
 * @Author: wangjing
 * @CreateDate: Sep 12, 2014
 */
public class UserAccount extends BaseModel {
	private static long serialVersionUID = -1276762496000406129L;
	private String code;
	private String description;
	private double balance;
	private double availableAmount;
	private double freezeAmount;
	private String cardNo;
	private String cardStatus;
	private String mobile;
	private String realname;
	private String paySwift;
	private double money;
	private double newMoney;
	private User user;
	private String payMnetId;
	
	

	
	public String getPayMnetId() {
		return payMnetId;
	}


	public void setPayMnetId(String payMnetId) {
		this.payMnetId = payMnetId;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public double getNewMoney() {
		return newMoney;
	}


	public void setNewMoney(double newMoney) {
		this.newMoney = newMoney;
	}


	public double getMoney() {
		return money;
	}


	public void setMoney(double money) {
		this.money = money;
	}


	public String getPaySwift() {
		return paySwift;
	}


	public void setPaySwift(String paySwift) {
		this.paySwift = paySwift;
	}


	public String getRealname() {
		return realname;
	}


	public void setRealname(String realname) {
		this.realname = realname;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}




	private String userid;
	
	public String getMemberType() {
		return memberType;
	}


	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}


	public String getActiveStatus() {
		return activeStatus;
	}


	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}




	private String memberType;
	private String activeStatus;
	public double getBbalance() {
		return bbalance;
	}


	public void setBbalance(double bbalance) {
		this.bbalance = bbalance;
	}


	public double getBfreezeAmount() {
		return bfreezeAmount;
	}


	public void setBfreezeAmount(double bfreezeAmount) {
		this.bfreezeAmount = bfreezeAmount;
	}


	public double getBavailableAmount() {
		return bavailableAmount;
	}


	public void setBavailableAmount(double bavailableAmount) {
		this.bavailableAmount = bavailableAmount;
	}




	private String bank;
	private double bbalance;
	private double bfreezeAmount;
	private double bavailableAmount;
	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}


	public double getAvailableAmount() {
		return availableAmount;
	}


	public void setAvailableAmount(double availableAmount) {
		this.availableAmount = availableAmount;
	}


	public double getFreezeAmount() {
		return freezeAmount;
	}


	public void setFreezeAmount(double freezeAmount) {
		this.freezeAmount = freezeAmount;
	}


	public String getCardNo() {
		return cardNo;
	}


	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}


	public String getCardStatus() {
		return cardStatus;
	}


	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}


	public String getBank() {
		return bank;
	}


	public void setBank(String bank) {
		this.bank = bank;
	}




	public String getAutoTender() {
		return autoTender;
	}


	public void setAutoTender(String autoTender) {
		this.autoTender = autoTender;
	}




	private String autoTender;
	

	@Override
	public String toString() {
		return "UserAccount [code=" + code + ", description=" + description + ", balance="
				+ balance + ", availableAmount=" + availableAmount + ", freezeAmount=" + freezeAmount
				+ ", cardNo=" + cardNo + ", cardStatus=" + cardStatus
				+ ", bank=" + bank + ", autoTender=" + autoTender+",bbalance="+bbalance+",bavailableAmount="+bavailableAmount
				+",bfreezeAmount="+bfreezeAmount+",memberType="+memberType+",activeStatus="+activeStatus+",mobile="+mobile+",userid="+userid
				+ "]";
	}
}
