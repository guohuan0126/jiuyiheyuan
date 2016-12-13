package com.duanrong.business.riskfund.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * @Description: 保障金
 * @Author: wangjing
 * @CreateDate: Nov 22, 2014
 */
public class Riskfund extends BaseModel {

	private static final long serialVersionUID = -4429256346479377354L;

	private int id;
	private Date time;
	private String type;
	private Double totalmoney;
	private Double duemoney;
	private Double overmoney;
	private Double spotmoney;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(Double totalmoney) {
		this.totalmoney = totalmoney;
	}
	public Double getDuemoney() {
		return duemoney;
	}
	public void setDuemoney(Double duemoney) {
		this.duemoney = duemoney;
	}
	public Double getOvermoney() {
		return overmoney;
	}
	public void setOvermoney(Double overmoney) {
		this.overmoney = overmoney;
	}
	public Double getSpotmoney() {
		return spotmoney;
	}
	public void setSpotmoney(Double spotmoney) {
		this.spotmoney = spotmoney;
	}
	

}