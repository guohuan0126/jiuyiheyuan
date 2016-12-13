package com.duanrong.business.app.model;

import java.util.Date;



/**
 * @Description: 手机APP启动图
 * @Author: 张英伟
 * @CreateDate: 2015年6月27日10:30:02
 */
public class AppStartDiagram {

	
	private String id;         //编号
	private String userId;         //用户编号
	private String picUrl;         //图片路径
	private Date createTime;            //创建时间
	private String status;         //使用状态 (0未使用1使用)
	private Date updateTime;            //更新时间
	private String picName;         //图片名称
	private String description;         //描述
	private String startTime;    //开始时间(查询用)
	private String endTime;      //结束时间(查询用)
	
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
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	
}
