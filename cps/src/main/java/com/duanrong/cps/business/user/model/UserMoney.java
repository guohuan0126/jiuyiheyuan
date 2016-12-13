package com.duanrong.cps.business.user.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * 用户账户金额信息
 * 
 * @author 尹逊志
 * @date 2014-8-29下午1:26:07
 */
public class UserMoney extends BaseModel {
	private static final long serialVersionUID = 8790967852732366525L;
	
	private String id;
	private Long seqNum;
	private User user;
	private String userId;
	private Date time;
	private String type;
	private String typeInfo;
	private Double money;
	private String detail;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public Long getSeqNum() {
		return seqNum;
	}

	public User getUser() {
		return user;
	}

	public Date getTime() {
		return time;
	}

	public String getType() {
		return type;
	}

	public String getTypeInfo() {
		return typeInfo;
	}

	public Double getMoney() {
		return money;
	}

	public String getDetail() {
		return detail;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSeqNum(Long seqNum) {
		this.seqNum = seqNum;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTypeInfo(String typeInfo) {
		this.typeInfo = typeInfo;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "UserMoney [id=" + id + ", seqNum=" + seqNum + "," + ", userId="
				+ userId + ", time=" + time + ", type=" + type + ", typeInfo="
				+ typeInfo + ", money=" + money + ", detail=" + detail + "]";
	}
}