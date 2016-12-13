package com.duanrong.business.activity.model;

import java.util.Date;

import base.model.BaseModel;


public class ActivateCouponRecord extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1239138989062568721L;

	private String id;
	private String createId;
	private Date createTime;
	private String remark;
	private String flag;
	private String num;
	
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	
}
