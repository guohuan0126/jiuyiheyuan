package com.duanrong.cps.business.activity.model;

import java.util.Date;

import base.model.BaseModel;


public class ActivateCoupon extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1239138989062568721L;

	/**
	 * 主键
	 */
	private String id;
	/**
	 * 优惠券码
	 */
	private String redPacketCode;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 使用状态(0,未使用 1,使用)
	 */
	private String useStatus;
	/**
	 * 激活用户的ID
	 */
	private String userId;
	/**
	 * 发行状态
	 */
	private String releaseStatus;
	/**
	 * 操作人ID
	 */
	private String createId;
	/**
	 * 对应的红包活动名称,同red_packet_rule表ID
	 */
	private String ruleId;
	/**
	 * 优惠券类型1.现金,2加息
	 */
	private String type;
	/**
	 * 具体红包数值(金额,利息)
	 */
	private String value;
	/**
	 * 优惠券有效期截止时间
	 */
	private Date endTime;
	/**
	 * 优惠券激活时间
	 */
	private Date activeTime;
	
	/**
	 * 父编号
	 * @return
	 */
	private String pid;
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRedPacketCode() {
		return redPacketCode;
	}
	public void setRedPacketCode(String redPacketCode) {
		this.redPacketCode = redPacketCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getReleaseStatus() {
		return releaseStatus;
	}
	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}
	
	
}
