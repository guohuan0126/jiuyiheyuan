package com.duanrong.drpay.business.user.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: 林志明
 * @CreateDate: Sep 12, 2014
 */
public class User {

	private String userId;
	private String username;
	private Integer age;
	private String email;
	private Date registerTime;
	private String status;
	private String password;
	private String realname;
	private String idCard;
	private String mobileNumber;
	private String postAddress;
	private String postCode;
	private String userSource;
	private double investMoneyTotal;
	// 用户类型：user普通用户，enterprise企业用户，（guarantee担保企业，暂未启用）
	private String userType;
	private String interior; //公司员工
	// 企业用户相关信息
	private String enterpriseName;
	private String bankLicense;// 开户银行
	private String orgNo;// 组织机构代码证
	private String businessLicense;// 营业执照编号
	private String taxNo;// 税务登记号
	private String legal;// 法人姓名
	private String legalIdcard;// 法人身份证
	private String contact;// 联系人	
	private String userIp;//用户IP
	// 手机号归属地
	private String phoneNoAttribution;
	private String phoneNoCity;
	// 推荐人
	private String referrer;
	private Integer isTransfer; //用户是否已迁移 0为未迁移，1为已迁移
	
	private List<Role> roles = new ArrayList<Role>();

	
	public Integer getIsTransfer() {
		return isTransfer;
	}

	public void setIsTransfer(Integer isTransfer) {
		this.isTransfer = isTransfer;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPostAddress() {
		return postAddress;
	}

	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getUserSource() {
		return userSource;
	}

	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getInterior() {
		return interior;
	}

	public void setInterior(String interior) {
		this.interior = interior;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getBankLicense() {
		return bankLicense;
	}

	public void setBankLicense(String bankLicense) {
		this.bankLicense = bankLicense;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getLegal() {
		return legal;
	}

	public void setLegal(String legal) {
		this.legal = legal;
	}

	public String getLegalIdcard() {
		return legalIdcard;
	}

	public void setLegalIdcard(String legalIdcard) {
		this.legalIdcard = legalIdcard;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getPhoneNoAttribution() {
		return phoneNoAttribution;
	}

	public void setPhoneNoAttribution(String phoneNoAttribution) {
		this.phoneNoAttribution = phoneNoAttribution;
	}

	public String getPhoneNoCity() {
		return phoneNoCity;
	}

	public void setPhoneNoCity(String phoneNoCity) {
		this.phoneNoCity = phoneNoCity;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public double getInvestMoneyTotal() {
		return investMoneyTotal;
	}

	public void setInvestMoneyTotal(double investMoneyTotal) {
		this.investMoneyTotal = investMoneyTotal;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", age="
				+ age + ", email=" + email + ", registerTime=" + registerTime
				+ ", status=" + status + ", password=" + password
				+ ", realname=" + realname + ", idCard=" + idCard
				+ ", mobileNumber=" + mobileNumber + ", postAddress="
				+ postAddress + ", postCode=" + postCode + ", userSource="
				+ userSource + ", userType=" + userType + ", interior="
				+ interior + ", enterpriseName=" + enterpriseName
				+ ", bankLicense=" + bankLicense + ", orgNo=" + orgNo
				+ ", businessLicense=" + businessLicense + ", taxNo=" + taxNo
				+ ", legal=" + legal + ", legalIdcard=" + legalIdcard
				+ ", contact=" + contact + ", userIp=" + userIp
				+ ", phoneNoAttribution=" + phoneNoAttribution
				+ ", phoneNoCity=" + phoneNoCity + ", referrer=" + referrer
				+ ", isTransfer=" + isTransfer + ", roles=" + roles + "]";
	}
	
	
}
