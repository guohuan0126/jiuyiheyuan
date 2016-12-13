package com.duanrong.business.ruralfinance.model;

import java.util.Date;
/**
 * 农贷中金扣款记录
 * @author YM
 *
 */
public class AgricultureDebitRecords {
   //uuid 主键
	private String id;
	//机构名称
	private String organizationName;
	//交易时间
	private Date transactionTime;
	//批次号
	private String  batchNumber;
	//明细号
	private String  detailsNumber;
	//银行ID
	private String  bankID;
	//金额
	private double money;
	//账户类型
	private String  customerType;
	//账户号码
	private String  bankcard;
	//账户名称
	private String  name;
	//分支行名称
	private String  branchName;
	//分支行城市
	private String  branchCity;
	//分支行省份
	private String branchProvince;
	//备注信息
	private String remark;
	//协议用户编号
	private String  protocolUserid;
	//协议号
	private String  protocolNum;
	//手机号码
	private String  mobileNumber;	 
	//电子邮件
	private String  email;
	//证件类型
	private String idType;
	//证件号码
	private String idCard;
	//银行代收的时间
	private Date  bankCollectionTime;
	//交易状态
	private String  transactionStatus;
	//银行响应代码
	private String  bankResponseCode;
	//银行响应消息
	private String bankResponseInfo;		
	//0扣款失败，1扣款成功
	private Integer  status;
	//是否查询更新过标识
	private Integer flag;
	
	private String uploadExcelId;
	//明细号有问题
	private int mxh;
	//交易状态有问题
	private int jyStatus;
	//卡介质类型
	private String cardType;
	//结算标识
	private String balanceFlag;
	
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
	 * @return the balanceFlag
	 */
	public String getBalanceFlag() {
		return balanceFlag;
	}
	/**
	 * @param balanceFlag the balanceFlag to set
	 */
	public void setBalanceFlag(String balanceFlag) {
		this.balanceFlag = balanceFlag;
	}
	/**
	 * @return the mxh
	 */
	public int getMxh() {
		return mxh;
	}
	/**
	 * @param mxh the mxh to set
	 */
	public void setMxh(int mxh) {
		this.mxh = mxh;
	}
	/**
	 * @return the jyStatus
	 */
	public int getJyStatus() {
		return jyStatus;
	}
	/**
	 * @param jyStatus the jyStatus to set
	 */
	public void setJyStatus(int jyStatus) {
		this.jyStatus = jyStatus;
	}
	/**
	 * @return the uploadExcelId
	 */
	public String getUploadExcelId() {
		return uploadExcelId;
	}
	/**
	 * @param uploadExcelId the uploadExcelId to set
	 */
	public void setUploadExcelId(String uploadExcelId) {
		this.uploadExcelId = uploadExcelId;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the organizationName
	 */
	public String getOrganizationName() {
		return organizationName;
	}
	/**
	 * @param organizationName the organizationName to set
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	/**
	 * @return the transactionTime
	 */
	public Date getTransactionTime() {
		return transactionTime;
	}
	/**
	 * @param transactionTime the transactionTime to set
	 */
	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}
	/**
	 * @return the batchNumber
	 */
	public String getBatchNumber() {
		return batchNumber;
	}
	/**
	 * @param batchNumber the batchNumber to set
	 */
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
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
	/**
	 * @return the bankID
	 */
	public String getBankID() {
		return bankID;
	}
	/**
	 * @param bankID the bankID to set
	 */
	public void setBankID(String bankID) {
		this.bankID = bankID;
	}
	/**
	 * @return the money
	 */
	public double getMoney() {
		return money;
	}
	/**
	 * @param money the money to set
	 */
	public void setMoney(double money) {
		this.money = money;
	}
	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}
	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	/**
	 * @return the bankcard
	 */
	public String getBankcard() {
		return bankcard;
	}
	/**
	 * @param bankcard the bankcard to set
	 */
	public void setBankcard(String bankcard) {
		this.bankcard = bankcard;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}
	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	/**
	 * @return the branchCity
	 */
	public String getBranchCity() {
		return branchCity;
	}
	/**
	 * @param branchCity the branchCity to set
	 */
	public void setBranchCity(String branchCity) {
		this.branchCity = branchCity;
	}
	/**
	 * @return the branchProvince
	 */
	public String getBranchProvince() {
		return branchProvince;
	}
	/**
	 * @param branchProvince the branchProvince to set
	 */
	public void setBranchProvince(String branchProvince) {
		this.branchProvince = branchProvince;
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
	 * @return the protocolUserid
	 */
	public String getProtocolUserid() {
		return protocolUserid;
	}
	/**
	 * @param protocolUserid the protocolUserid to set
	 */
	public void setProtocolUserid(String protocolUserid) {
		this.protocolUserid = protocolUserid;
	}
	/**
	 * @return the protocolNum
	 */
	public String getProtocolNum() {
		return protocolNum;
	}
	/**
	 * @param protocolNum the protocolNum to set
	 */
	public void setProtocolNum(String protocolNum) {
		this.protocolNum = protocolNum;
	}
	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}
	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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
	 * @return the idType
	 */
	public String getIdType() {
		return idType;
	}
	/**
	 * @param idType the idType to set
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}
	/**
	 * @return the idCard
	 */
	public String getIdCard() {
		return idCard;
	}
	/**
	 * @param idCard the idCard to set
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	/**
	 * @return the bankCollectionTime
	 */
	public Date getBankCollectionTime() {
		return bankCollectionTime;
	}
	/**
	 * @param bankCollectionTime the bankCollectionTime to set
	 */
	public void setBankCollectionTime(Date bankCollectionTime) {
		this.bankCollectionTime = bankCollectionTime;
	}
	/**
	 * @return the transactionStatus
	 */
	public String getTransactionStatus() {
		return transactionStatus;
	}
	/**
	 * @param transactionStatus the transactionStatus to set
	 */
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	/**
	 * @return the bankResponseCode
	 */
	public String getBankResponseCode() {
		return bankResponseCode;
	}
	/**
	 * @param bankResponseCode the bankResponseCode to set
	 */
	public void setBankResponseCode(String bankResponseCode) {
		this.bankResponseCode = bankResponseCode;
	}
	/**
	 * @return the bankResponseInfo
	 */
	public String getBankResponseInfo() {
		return bankResponseInfo;
	}
	/**
	 * @param bankResponseInfo the bankResponseInfo to set
	 */
	public void setBankResponseInfo(String bankResponseInfo) {
		this.bankResponseInfo = bankResponseInfo;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the flag
	 */
	public Integer getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AgricultureDebitRecords [id=" + id + ", organizationName="
				+ organizationName + ", transactionTime=" + transactionTime
				+ ", batchNumber=" + batchNumber + ", detailsNumber="
				+ detailsNumber + ", bankID=" + bankID + ", money=" + money
				+ ", customerType=" + customerType + ", bankcard=" + bankcard
				+ ", name=" + name + ", branchName=" + branchName
				+ ", branchCity=" + branchCity + ", branchProvince="
				+ branchProvince + ", remark=" + remark + ", protocolUserid="
				+ protocolUserid + ", protocolNum=" + protocolNum
				+ ", mobileNumber=" + mobileNumber + ", email=" + email
				+ ", idType=" + idType + ", idCard=" + idCard
				+ ", bankCollectionTime=" + bankCollectionTime
				+ ", transactionStatus=" + transactionStatus
				+ ", bankResponseCode=" + bankResponseCode
				+ ", bankResponseInfo=" + bankResponseInfo + ", status="
				+ status + ", flag=" + flag + "]";
	}
	
	
	
	

}
