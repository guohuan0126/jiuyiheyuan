package com.duanrong.drpay.trusteeship.helper.model;

public class GeneratorUserAccountJSON extends GeneratorJSON {

	// 用户姓名
	private String realName;
	// 证件类型（枚举）
	private IdCardType idCardType;
	// 用户角色（枚举）
	private UserRole userRole;
	// 证件号码
	private String idCardNo;
	// 银行预留手机号
	private String mobile;
	// 银行卡号
	private String bankcardNo;
	private String bankcode;
	//修改类型，MODIFYMOBILE 表示预留手机号修改，适用于快捷用户更新预留手机号， CHECKTYPEUPDATE 表示升级为快捷卡
	private String modifyType = "MODIFYMOBILE";
	
	// 鉴权验证类型
	private String checkType = "LIMIT";

	// 自动授权
	private String authList;

	private String accessType;

	// 审核状态
	private String auditStatus;

	// 见【用户类型】
	private UserType userType;
	// 见【用户状态】
	private String activeStatus;

	// 账户余额
	private String balance;
	// 可用余额
	private String availableAmount;
	// 冻结金额
	private String freezeAmount;

	// 迁移导入会员状态，true 表示已激活，false 表示未激活，正常注册成功会员则默认状态为true
	private String isImportUserActivate;
	// 开户名称，个人返回姓名，企业返回企业名称
	private String name;
	
	
	public String getModifyType() {
		return modifyType;
	}

	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
	}

	public IdCardType getIdCardType() {
		return idCardType;
	}

	public void setIdCardType(IdCardType idCardType) {
		this.idCardType = idCardType;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankcardNo() {
		return bankcardNo;
	}

	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getAuthList() {
		return this.authList;
	}

	public void setAuthList(AuthType... authList) {
		StringBuffer strs = new StringBuffer();
		if(authList != null && authList.length > 0){
			for(AuthType auth : authList){
				strs.append(auth + ",");
			}
		}
		if(strs.lastIndexOf(",") == strs.length() - 1) strs.deleteCharAt(strs.length() - 1);
		this.authList = strs.toString();
	}
	
	public void setAuthList(String authList) {
		this.authList = authList;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getBankcode() {
		return bankcode;
	}

	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(String availableAmount) {
		this.availableAmount = availableAmount;
	}

	public String getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(String freezeAmount) {
		this.freezeAmount = freezeAmount;
	}

	public String getIsImportUserActivate() {
		return isImportUserActivate;
	}

	public void setIsImportUserActivate(String isImportUserActivate) {
		this.isImportUserActivate = isImportUserActivate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
