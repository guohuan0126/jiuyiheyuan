package com.duanrong.cps.business.user.model;

/**
 * 用户数据model
 * @author bj
 *
 */
public class UserData {

	//用户总数量
	private int allUserCount;
	//用户开户总数量
	private int allUserRegisterCount;
	//用户总投资数
	private int allStateCount;
	//今日注册用户
	private int newAllUserCount;
	//今日开户用户
	private int newAllUserRegisterCount;
	//今日投资总数
	private int newInvestCount;
	//昨日投资用户数量
	private int lastInvestCount;
	//昨日 开户
	private int lastAllUserRegisterCount;
	//昨日新增用户数量
	private int userCount;
	
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public int getAllUserCount() {
		return allUserCount;
	}
	public void setAllUserCount(int allUserCount) {
		this.allUserCount = allUserCount;
	}
	public int getAllUserRegisterCount() {
		return allUserRegisterCount;
	}
	public void setAllUserRegisterCount(int allUserRegisterCount) {
		this.allUserRegisterCount = allUserRegisterCount;
	}
	public int getAllStateCount() {
		return allStateCount;
	}
	public void setAllStateCount(int allStateCount) {
		this.allStateCount = allStateCount;
	}
	public int getNewAllUserCount() {
		return newAllUserCount;
	}
	public void setNewAllUserCount(int newAllUserCount) {
		this.newAllUserCount = newAllUserCount;
	}
	public int getNewAllUserRegisterCount() {
		return newAllUserRegisterCount;
	}
	public void setNewAllUserRegisterCount(int newAllUserRegisterCount) {
		this.newAllUserRegisterCount = newAllUserRegisterCount;
	}
	public int getNewInvestCount() {
		return newInvestCount;
	}
	public void setNewInvestCount(int newInvestCount) {
		this.newInvestCount = newInvestCount;
	}
	
	public int getLastInvestCount() {
		return lastInvestCount;
	}
	public void setLastInvestCount(int lastInvestCount) {
		this.lastInvestCount = lastInvestCount;
	}
	public int getLastAllUserRegisterCount() {
		return lastAllUserRegisterCount;
	}
	public void setLastAllUserRegisterCount(int lastAllUserRegisterCount) {
		this.lastAllUserRegisterCount = lastAllUserRegisterCount;
	}
	
	
	
}
