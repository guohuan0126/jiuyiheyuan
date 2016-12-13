package com.duanrong.business.user.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import base.model.BaseModel;

/**
 * @Description:
 * @Author: 林志明
 * @CreateDate: Sep 12, 2014
 */
public class User extends BaseModel {
	private static final long serialVersionUID = -1276762496000406129L;

	private String userId;
	private String username;
	private Integer age;
	private String email;
	private Date registerTime;
	private String status;
	private String password;
	private String cashPassword;
	private String realname;
	private String idCard;
	private String mobileNumber;
	private String postAddress;
	private String homeAddress;
	private String postCode;
	private String userSource;
	private String nickname;
	private boolean autoInvest;
	private int num;
	private String phoneNoAttribution;
	private String phoneNoCity;
	
	private String inviteCode;
	
	
	
	public String getInviteCode() {
		return inviteCode;
	}


	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}


	public String getPhoneNoCity() {
		return phoneNoCity;
	}


	public void setPhoneNoCity(String phoneNoCity) {
		this.phoneNoCity = phoneNoCity;
	}

	private String qq;//and by wangjing
	
	
	
	public String getQq() {
		return qq;
	}


	public void setQq(String qq) {
		this.qq = qq;
	}


	public String getPhoneNoAttribution() {
		return phoneNoAttribution;
	}


	public void setPhoneNoAttribution(String phoneNoAttribution) {
		this.phoneNoAttribution = phoneNoAttribution;
	}


	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
	}

	// 用户类型：user普通用户，enterprise企业用户，（guarantee担保企业，暂未启用）
	private String userType;

	//用户投资次数
	private int investTotal;
	
	/************* 跟投奖励相关字段:跟投奖励结束后可以删除 **************/
	private Double investMoneyTotal;
	private Double investMoneyTotal1;

	// 企业用户相关信息
	private String enterpriseName;
	private String bankLicense;// 开户银行
	private String orgNo;// 组织机构代码证
	private String businessLicense;// 营业执照编号
	private String taxNo;// 税务登记号
	private String legal;// 法人姓名
	private String legalIdcard;// 法人身份证
	private String contact;// 联系人。

	// 推荐人
	private String referrer;
	private String authenname;
    private int investNum;
	
	// 用户账户平衡调用查询数据
	private Double balance;
	private Double frozenMoney;
	private UserOtherInfo userOtherInfo;
	private int remarkNum;//add by wangjing
	//当前投资金额
	private Double currMoney;
	private Double totalBlance;//总余额
	private Double totalMoney;//总投资金额
	private Double totalCurrMoney;//当前总投资金额
	private String oreferrer;
	private String userRemark;
	
	//第一次投资10w 的时间（大客户列表用）
	private Date investTime;

	public Date getInvestTime() {
		return investTime;
	}


	public void setInvestTime(Date investTime) {
		this.investTime = investTime;
	}


	public String getUserRemark() {
		return userRemark;
	}


	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}


	public String getOreferrer() {
		return oreferrer;
	}


	public void setOreferrer(String oreferrer) {
		this.oreferrer = oreferrer;
	}


	public Double getTotalBlance() {
		return totalBlance;
	}


	public void setTotalBlance(Double totalBlance) {
		this.totalBlance = totalBlance;
	}


	public Double getTotalMoney() {
		return totalMoney;
	}


	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}


	public Double getTotalCurrMoney() {
		return totalCurrMoney;
	}


	public void setTotalCurrMoney(Double totalCurrMoney) {
		this.totalCurrMoney = totalCurrMoney;
	}


	public Double getCurrMoney() {
		return currMoney;
	}


	public void setCurrMoney(Double currMoney) {
		this.currMoney = currMoney;
	}


	public int getRemarkNum() {
		return remarkNum;
	}


	public void setRemarkNum(int remarkNum) {
		this.remarkNum = remarkNum;
	}


	public UserOtherInfo getUserOtherInfo() {
		return userOtherInfo;
	}
	
	
	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public void setUserOtherInfo(UserOtherInfo userOtherInfo) {
		this.userOtherInfo = userOtherInfo;
	}

	public UserLoginLog getUserLoginLog() {
		return userLoginLog;
	}

	public void setUserLoginLog(UserLoginLog userLoginLog) {
		this.userLoginLog = userLoginLog;
	}

	private UserLoginLog userLoginLog;

	public String getEnterpriseName() {
		return enterpriseName;
	}
	
	public boolean isAutoInvest() {
		return autoInvest;
	}


	public void setAutoInvest(boolean autoInvest) {
		this.autoInvest = autoInvest;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	private List<Role> roles = new ArrayList<Role>();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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

	public String getCashPassword() {
		return cashPassword;
	}

	public void setCashPassword(String cashPassword) {
		this.cashPassword = cashPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public List<Role> getRoles() {
		return roles;
	}
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
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

	public Double getInvestMoneyTotal() {
		return investMoneyTotal;
	}

	public void setInvestMoneyTotal(Double investMoneyTotal) {
		this.investMoneyTotal = investMoneyTotal;
	}

	public Double getInvestMoneyTotal1() {
		return investMoneyTotal1;
	}

	public void setInvestMoneyTotal1(Double investMoneyTotal1) {
		this.investMoneyTotal1 = investMoneyTotal1;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getFrozenMoney() {
		return frozenMoney;
	}

	public void setFrozenMoney(Double frozenMoney) {
		this.frozenMoney = frozenMoney;
	}
	public int getInvestNum() {
		return investNum;
	}
	
	public int getInvestTotal() {
		return investTotal;
	}

	public void setInvestNum(int investNum) {
		this.investNum = investNum;
	}

	public String getAuthenname() {
		return authenname;
	}

	public void setAuthenname(String authenname) {
		this.authenname = authenname;
	}

	public void setInvestTotal(int investTotal) {
		this.investTotal = investTotal;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", age="
				+ age + ", email=" + email + ", registerTime=" + registerTime
				+ ", status=" + status + ", password=" + password
				+ ", cashPassword=" + cashPassword + ", realname=" + realname
				+ ", idCard=" + idCard + ", mobileNumber=" + mobileNumber
				+ ", postAddress=" + postAddress + ", postCode=" + postCode
				+ ", userSource=" + userSource + ", userType=" + userType
				+ ", investMoneyTotal=" + investMoneyTotal
				+ ", investMoneyTotal1=" + investMoneyTotal1
				+ ", enterpriseName=" + enterpriseName + ", bankLicense="
				+ bankLicense + ", orgNo=" + orgNo + ", businessLicense="
				+ businessLicense + ", taxNo=" + taxNo + ", legal=" + legal
				+ ", legalIdcard=" + legalIdcard + ", contact=" + contact
				+ ", referrer=" + referrer + ", balance=" + balance
				+ ", frozenMoney=" + frozenMoney + ", roles=" + roles + "]";
	}
}
