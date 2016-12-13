package com.duanrong.business.ruralfinance.model;

public class Template {


	private String id;
	private double money;
	private String bankName;
	private int accountType;
	private String userName;
	private String bankNum;
	private String branchname;
	private String province;
	private String city;
	private String balanceFlag;
	private int idType;
	private String idStatus;
	private String accountStatus;
	private String repayStatus;
	private String detailsNumber;
	//备注
	private String remark;
	//手机号
	private String mobileNum;
	//电子邮箱
	private String email;
	
	//协议用户编号
	private String protocolUserID;
	//卡介质类型
	private String cardType;
	private int earlyRepayment;
	
	
	public int getEarlyRepayment() {
		return earlyRepayment;
	}
	public void setEarlyRepayment(int earlyRepayment) {
		this.earlyRepayment = earlyRepayment;
	}
	/**
	 * @return the cardType
	 */
	public String getCardType() {
		return cardType;
	}
	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	/**
	 * @return the protocolUserID
	 */
	public String getProtocolUserID() {
		return protocolUserID;
	}
	/**
	 * @param protocolUserID the protocolUserID to set
	 */
	public void setProtocolUserID(String protocolUserID) {
		this.protocolUserID = protocolUserID;
	}
	/**
	 * @return the mobileNum
	 */
	public String getMobileNum() {
		return mobileNum;
	}
	/**
	 * @param mobileNum the mobileNum to set
	 */
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the repayStatus
	 */
	public String getRepayStatus() {
		return repayStatus;
	}
	/**
	 * @param repayStatus the repayStatus to set
	 */
	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}
	/**
	 * @return the detailsNumber
	 */
	public String getDetailsNumber() {
		return detailsNumber;
	}
	/**
	 * @param detailsNumber the detailsNumber to set
	 */
	public void setDetailsNumber(String detailsNumber) {
		this.detailsNumber = detailsNumber;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getIdStatus() {
		return idStatus;
	}
	public void setIdStatus(String idStatus) {
		this.idStatus = idStatus;
	}
	private String idCard;
	private double monthMoney;
	private double latePenalty;
	private String   num;
	
	public String  getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public double getMonthMoney() {
		return monthMoney;
	}
	public void setMonthMoney(double monthMoney) {
		this.monthMoney = monthMoney;
	}
	public double getLatePenalty() {
		return latePenalty;
	}
	public void setLatePenalty(double latePenalty) {
		this.latePenalty = latePenalty;
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
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public int getAccountType() {
		return accountType;
	}
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBankNum() {
		return bankNum;
	}
	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBalanceFlag() {
		return balanceFlag;
	}
	public void setBalanceFlag(String balanceFlag) {
		this.balanceFlag = balanceFlag;
	}
	public int getIdType() {
		return idType;
	}
	public void setIdType(int idType) {
		this.idType = idType;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
}
