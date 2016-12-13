package com.duanrong.business.user.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * @Description: 用户登录日志
 * @Author: 林志明
 * @CreateDate: Nov 19, 2014
 */
public class UserLoginLog extends BaseModel {
	private static final long serialVersionUID = -9177968034780751346L;
	
	public UserLoginLog(String userId, String loginIp, String isSuccess) {
		super();
		this.userId = userId;
		this.loginIp = loginIp;
		this.isSuccess = isSuccess;
	}

	public UserLoginLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String id;
	private String userId;
	private Date loginTime;
	private String loginIp;
	
	private String startTime;    //开始时间(查询用)
	private String endTime;      //结束时间(查询用)
	/**
	 * 登录是否成功，可以用来判断登录失败次数，进而结合其他数据对账号进行相关处理。
	 */
	private String isSuccess;
	/**
	 * 登录信息
	 */
	private String loginInfo;
	/**
	 * 登陆次数
	 */
	private int num;
	

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

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

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(String loginInfo) {
		this.loginInfo = loginInfo;
	}
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	@Override
	public String toString() {
		return "UserLoginLog [id=" + id + ", userId=" + userId + ", loginTime="
				+ loginTime + ", loginIp=" + loginIp + ", isSuccess="
				+ isSuccess + ", loginInfo=" + loginInfo + "]";
	}

	
	
}