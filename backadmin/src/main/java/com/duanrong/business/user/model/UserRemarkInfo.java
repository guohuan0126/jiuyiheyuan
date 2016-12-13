package com.duanrong.business.user.model;

public class UserRemarkInfo {

	
	private String userId;
	private String QQ;
	private String realName;
	private String userSource;
	private String  remark;
	private String admin;
	private String time;
	private String mobileNum;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getQQ() {
		return QQ;
	}
	public void setQQ(String qQ) {
		QQ = qQ;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getUserSource() {
		return userSource;
	}
	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
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
	
	
	
}
