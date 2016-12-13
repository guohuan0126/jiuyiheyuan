package com.duanrong.thirdPartyInterface.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import com.duanrong.util.ArithUtil;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-11-11 下午4:09:39
 * @Description : template com.duanrong.wdzj.model WDZJ.java
 * 
 */
public class WDZJLoan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 项目主键(唯一)
	 */
	private String projectId;
	/**
	 * 借款标题
	 */
	private String title;
	/**
	 * 借款金额
	 */
	private double amount;
	/**
	 * 进度 百分比 例如：55.5%。 100%表示满标。
	 */
	private String schedule;
	/**
	 * 利率 百分比 例如：24.5%
	 */
	private String interestRate;
	/**
	 * 借款期限
	 */
	private int deadline;
	/**
	 * 期限单位 仅限 ‘天’ 或 ’月’
	 */
	private String deadlineUnit;
	/**
	 * 奖励 奖励统一返回比例,而不是奖励金额。例如：奖励50元，借款金额1000元； 奖励=50/1000=5% 即返回’5’）
	 * 奖励比例统一去掉’%’，比如奖励比例1.2%则返回’1.2’即可
	 * 
	 * 如果平台系统无奖励字段，则统一返回0
	 */
	private double reward;
	/**
	 * 抵押标 ，质押标，信用标，流转标，净值标，秒标等。(对于不参与计算平均利率的秒标（天标）、活动标（体验标），就传“秒标”或是“活动标”)
	 * 借款类型可根据平台的情况修改，不限于上述类型。若一个标有多个类型，则在每个类型中间加半角分号“;”（如实地认证+担保，就传“实地认证;担保”）
	 */
	private String type;
	/**
	 * 还款方式 1：到期还本息(到期还本付息，一次性还本付息，按日计息到期还本,一次性付款、秒还) 2：每月等额本息(按月分期，按月等额本息)
	 * 3：每季分期（按季分期，按季等额本息） 5：每月付息到期还本(先息后本) 6：等额本金(按月等额本金) 7：每季付息到期还本（按季付息到期还本）
	 * 8：每月付息分期还本
	 */
	private int repaymentType;
	/**
	 * 投资人数据
	 */
	private List<WDZJInvest> subscribes;
	/**
	 * 发标人ID 如平台出于保护隐私目的不传实际ID的，可将ID进行加密或者哈希处理，处理后的ID与原ID能一一对应即可。但不能将ID加*隐藏部分字符，
	 * 否则会导致多个借款人使用同一个ID，导致借款集中度高。
	 */
	private String userName;
	/**
	 * 标的详细页面地址链接
	 */
	private String loanUrl;
	/**
	 * 标的成功时间。（满标的时间） 注意：是标被投满的时间（此标最后一个投标人投标的时间），而不是发标时间。 格式为标准时间格式：’2014-07-23
	 * 12:23:22’ 注意：getNowProjects方法取的是正在投标的信息，这个方法调用时可以没有这个字段。
	 * 但getProjectsByDate调用时必须有该字段。
	 */
	private String successTime;

	/**
	 * @return the projectId
	 */
	public String getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId
	 *            the projectId to set
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the schedule
	 */
	public String getSchedule() {
		return schedule;
	}

	/**
	 * @param schedule
	 *            the schedule to set
	 */
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	/**
	 * @return the interestRate
	 */
	public String getInterestRate() {
		return interestRate;
	}

	/**
	 * @param interestRate
	 *            the interestRate to set
	 */
	public void setInterestRate(String interestRate) {
		if (interestRate != null) {
			Double rate = Double.parseDouble(interestRate) * 100;
			rate = ArithUtil.round(rate, 2);
			interestRate = rate + "%";
		}
		this.interestRate = interestRate;
	}

	/**
	 * @return the deadline
	 */
	public int getDeadline() {
		return deadline;
	}

	/**
	 * @param deadline
	 *            the deadline to set
	 */
	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}

	/**
	 * @return the deadlineUnit
	 */
	public String getDeadlineUnit() {
		return deadlineUnit;
	}

	/**
	 * @param deadlineUnit
	 *            the deadlineUnit to set
	 */
	public void setDeadlineUnit(String deadlineUnit) {
		this.deadlineUnit = deadlineUnit;
	}

	/**
	 * @return the reward
	 */
	public double getReward() {
		return reward;
	}

	/**
	 * @param reward
	 *            the reward to set
	 */
	public void setReward(double reward) {
		reward = reward * 100;
		reward = ArithUtil.round(reward, 2);
		this.reward = reward;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the repaymentType
	 */
	public int getRepaymentType() {
		return repaymentType;
	}

	/**
	 * @param repaymentType
	 *            the repaymentType to set
	 */
	public void setRepaymentType(int repaymentType) {
		this.repaymentType = repaymentType;
	}

	/**
	 * @return the subscribes
	 */
	public List<WDZJInvest> getSubscribes() {
		return subscribes;
	}

	/**
	 * @param subscribes
	 *            the subscribes to set
	 */
	public void setSubscribes(List<WDZJInvest> subscribes) {
		this.subscribes = subscribes;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		String code = RandomStringUtils.random(6, false, true);
		userName = DigestUtils.md5Hex(this.projectId + code + userName);
		this.userName = userName;
	}

	/**
	 * @return the loanUrl
	 */
	public String getLoanUrl() {
		return loanUrl;
	}

	/**
	 * @param loanUrl
	 *            the loanUrl to set
	 */
	public void setLoanUrl(String loanUrl) {
		loanUrl = "www.duanrong.com/loan/" + this.getProjectId();
		this.loanUrl = loanUrl;
	}

	/**
	 * @return the successTime
	 */
	public String getSuccessTime() {
		return successTime;
	}

	/**
	 * @param successTime
	 *            the successTime to set
	 */
	public void setSuccessTime(String successTime) {
		this.successTime = successTime;
	}
}
