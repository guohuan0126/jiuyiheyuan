package com.duanrong.business.invest.model;

import java.util.Date;

import base.model.BaseModel;

public class PassThrough extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4294025283286069808L;
	
	//主键ID
	private String id;
	//创建时间
	private Date createTime;
	//用户ID
	private String userId;
	//推荐人ID
	private String recommendedId;
	//标识是否帮助闯关
	private String whetherHelp;
	//是否发放奖励(记录发放奖励的关卡。如果未过关默认为0)
	private String whetherReward;
	//是否为活动新注册用户（0为不是，1为是）
	private String whetherNew;
	//活动期间的投资额(包含帮助人的投资额)
	private int investTotalMoney;
	//标注活动第几期
	private int whichStage;
	//当用户为新推广注册用户并达到要求时候发奖（0为未发奖，1为已发）
	private int newUserReward;
	//被推荐人的手机号
	private String mobileNumber;
    //获得奖励总额
	private Double rewardMoney;
	
	private Double investMoney;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRecommendedId() {
		return recommendedId;
	}
	public void setRecommendedId(String recommendedId) {
		this.recommendedId = recommendedId;
	}
	public String getWhetherHelp() {
		return whetherHelp;
	}
	public void setWhetherHelp(String whetherHelp) {
		this.whetherHelp = whetherHelp;
	}
	public String getWhetherReward() {
		return whetherReward;
	}
	public void setWhetherReward(String whetherReward) {
		this.whetherReward = whetherReward;
	}
	public String getWhetherNew() {
		return whetherNew;
	}
	public void setWhetherNew(String whetherNew) {
		this.whetherNew = whetherNew;
	}
	public int getInvestTotalMoney() {
		return investTotalMoney;
	}
	public void setInvestTotalMoney(int investTotalMoney) {
		this.investTotalMoney = investTotalMoney;
	}
	public int getWhichStage() {
		return whichStage;
	}
	public void setWhichStage(int whichStage) {
		this.whichStage = whichStage;
	}
	public int getNewUserReward() {
		return newUserReward;
	}
	public void setNewUserReward(int newUserReward) {
		this.newUserReward = newUserReward;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Double getRewardMoney() {
		return rewardMoney;
	}
	public void setRewardMoney(Double rewardMoney) {
		this.rewardMoney = rewardMoney;
	}
	@Override
	public String toString() {
		return "PassThrough [id=" + id + ", createTime=" + createTime
				+ ", userId=" + userId + ", recommendedId=" + recommendedId
				+ ", whetherHelp=" + whetherHelp + ", whetherReward="
				+ whetherReward + ", whetherNew=" + whetherNew
				+ ", investTotalMoney=" + investTotalMoney + ", whichStage="
				+ whichStage + ", newUserReward=" + newUserReward
				+ ", mobileNumber=" + mobileNumber + ", rewardMoney="
				+ rewardMoney + "]";
	}
	public Double getInvestMoney() {
		return investMoney;
	}
	public void setInvestMoney(Double investMoney) {
		this.investMoney = investMoney;
	}
	
	
}