package com.duanrong.business.yeepay.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.duanrong.business.flow.model.FlowNode;

import base.model.BaseModel;

/**
 * @Description: 冻结
 * @Author: wangjing
 * @CreateDate: Aug 29, 2014
 */
public class Withhold extends BaseModel {
	private static final long serialVersionUID = -8189198520914248838L;
	private String id;
	private String userId;
	private String mobileNum;
	private String card;
	private String realname;
	private Date time;
	private int status;
	private List<WithholdBank> banks = new ArrayList<>();
	public List<WithholdBank> getBanks() {
		return banks;
	}
	public void setBanks(List<WithholdBank> banks) {
		this.banks = banks;
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
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
