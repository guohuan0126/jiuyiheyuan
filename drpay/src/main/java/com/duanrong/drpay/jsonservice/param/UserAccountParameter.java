package com.duanrong.drpay.jsonservice.param;

import com.duanrong.drpay.trusteeship.helper.model.IdCardType;
import com.duanrong.drpay.trusteeship.helper.model.UserRole;

public class UserAccountParameter extends Parameter {

	private static final long serialVersionUID = 5026991328923523705L;
	//用户姓名
	private String realName;
	//证件类型（枚举）
	private IdCardType idCardType;
	//用户角色（枚举）
	private UserRole userRole;
	//证件号码
	private String idCardNo;
	//银行预留手机号
	private String mobile;
	//银行编码
	private String bankCode;
	//银行卡号
	private String bankcardNo;
	
	//鉴权验证类型
	private String checkType = "LIMIT";
	
	//自动授权
	private String authList;
	
	
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
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
		return authList;
	}
	
	public void setAuthList(String authList) {
		this.authList = authList;
	}
	@Override
	public String toString() {
		return "UserAccountParameter [realName=" + realName + ", idCardType="
				+ idCardType + ", userRole=" + userRole + ", idCardNo="
				+ idCardNo + ", mobile=" + mobile + ", bankCode=" + bankCode
				+ ", bankcardNo=" + bankcardNo + ", checkType=" + checkType
				+ ", authList=" + authList + "]";
	}
	
}
