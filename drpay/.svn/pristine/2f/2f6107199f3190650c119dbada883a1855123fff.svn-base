package com.duanrong.drpay.business.account.model;

import com.duanrong.drpay.trusteeship.helper.model.IdCardType;
import com.duanrong.drpay.trusteeship.helper.model.UserRole;
import com.duanrong.drpay.trusteeship.helper.model.UserType;

/**
 * 存管账户
 * @author xiao
 * @datetime 2016年12月12日 下午8:35:46
 */
public class CgtUserAccount extends UserAccount{

	/**
	 * 存管通用户信息
	 */
	private String cgtUserId;
	private IdCardType cgtIdCardType;
	private UserRole cgtUserRole;
	private String cgtIdCardNo;
	private String cgtMobile;
	private String cgtBankcardNo;
	private String cgtBankcode;
	private String cgtAccessType;
	private String cgtAuditStatus;
	// 见【用户类型】
	private UserType cgtUserType;
	// 见【用户状态】
	private String cgtActiveStatus;
	// 账户余额
	private double cgtBalance;
	// 可用余额
	private double cgtAvailableAmount;
	// 冻结金额
	private double cgtFreezeAmount;
	// 用户授权列表，TENDER 表示自动投标授权，REPAYMENT 表示自动还款授权，CREDIT_ASSIGNMENT
	// 表示自动购买债权授权，WITHDRAW 表示自动提现授权，此处根据用户实际授权情况返回，两个或两个以上的授权值用“,”英文半角逗号分隔
	private String cgtAuthlist;
	// 迁移导入会员状态，true 表示已激活，false 表示未激活，正常注册成功会员则默认状态为true
	private String cgtIsImportUserActivate;
	// 开户名称，个人返回姓名，企业返回企业名称
	private String cgtName;
	
	public String getCgtUserId() {
		return cgtUserId;
	}

	public void setCgtUserId(String cgtUserId) {
		this.cgtUserId = cgtUserId;
	}

	public IdCardType getCgtIdCardType() {
		return cgtIdCardType;
	}

	public void setCgtIdCardType(IdCardType cgtIdCardType) {
		this.cgtIdCardType = cgtIdCardType;
	}

	public UserRole getCgtUserRole() {
		return cgtUserRole;
	}

	public void setCgtUserRole(UserRole cgtUserRole) {
		this.cgtUserRole = cgtUserRole;
	}

	public String getCgtIdCardNo() {
		return cgtIdCardNo;
	}

	public void setCgtIdCardNo(String cgtIdCardNo) {
		this.cgtIdCardNo = cgtIdCardNo;
	}

	public String getCgtMobile() {
		return cgtMobile;
	}

	public void setCgtMobile(String cgtMobile) {
		this.cgtMobile = cgtMobile;
	}

	public String getCgtBankcardNo() {
		return cgtBankcardNo;
	}

	public void setCgtBankcardNo(String cgtBankcardNo) {
		this.cgtBankcardNo = cgtBankcardNo;
	}

	public String getCgtBankcode() {
		return cgtBankcode;
	}

	public void setCgtBankcode(String cgtBankcode) {
		this.cgtBankcode = cgtBankcode;
	}

	public String getCgtAccessType() {
		return cgtAccessType;
	}

	public void setCgtAccessType(String cgtAccessType) {
		this.cgtAccessType = cgtAccessType;
	}

	public String getCgtAuditStatus() {
		return cgtAuditStatus;
	}

	public void setCgtAuditStatus(String cgtAuditStatus) {
		this.cgtAuditStatus = cgtAuditStatus;
	}

	public UserType getCgtUserType() {
		return cgtUserType;
	}

	public void setCgtUserType(UserType cgtUserType) {
		this.cgtUserType = cgtUserType;
	}

	public String getCgtActiveStatus() {
		return cgtActiveStatus;
	}

	public void setCgtActiveStatus(String cgtActiveStatus) {
		this.cgtActiveStatus = cgtActiveStatus;
	}

	public double getCgtBalance() {
		return cgtBalance;
	}

	public void setCgtBalance(double cgtBalance) {
		this.cgtBalance = cgtBalance;
	}

	public double getCgtAvailableAmount() {
		return cgtAvailableAmount;
	}

	public void setCgtAvailableAmount(double cgtAvailableAmount) {
		this.cgtAvailableAmount = cgtAvailableAmount;
	}

	public double getCgtFreezeAmount() {
		return cgtFreezeAmount;
	}

	public void setCgtFreezeAmount(double cgtFreezeAmount) {
		this.cgtFreezeAmount = cgtFreezeAmount;
	}

	public String getCgtAuthlist() {
		return cgtAuthlist;
	}

	public void setCgtAuthlist(String cgtAuthlist) {
		this.cgtAuthlist = cgtAuthlist;
	}

	public String getCgtIsImportUserActivate() {
		return cgtIsImportUserActivate;
	}

	public void setCgtIsImportUserActivate(String cgtIsImportUserActivate) {
		this.cgtIsImportUserActivate = cgtIsImportUserActivate;
	}

	public String getCgtName() {
		return cgtName;
	}

	public void setCgtName(String cgtName) {
		this.cgtName = cgtName;
	}

	@Override
	public String toString() {
		return "CgtUserAccount [cgtUserId=" + cgtUserId + ", cgtIdCardType="
				+ cgtIdCardType + ", cgtUserRole=" + cgtUserRole
				+ ", cgtIdCardNo=" + cgtIdCardNo + ", cgtMobile=" + cgtMobile
				+ ", cgtBankcardNo=" + cgtBankcardNo + ", cgtBankcode="
				+ cgtBankcode + ", cgtAccessType=" + cgtAccessType
				+ ", cgtAuditStatus=" + cgtAuditStatus + ", cgtUserType="
				+ cgtUserType + ", cgtActiveStatus=" + cgtActiveStatus
				+ ", cgtBalance=" + cgtBalance + ", cgtAvailableAmount="
				+ cgtAvailableAmount + ", cgtFreezeAmount=" + cgtFreezeAmount
				+ ", cgtAuthlist=" + cgtAuthlist + ", cgtIsImportUserActivate="
				+ cgtIsImportUserActivate + ", cgtName=" + cgtName + "]";
	}

	
	
}
