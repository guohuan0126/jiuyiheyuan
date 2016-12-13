package com.duanrong.cps.business.xicaiwang.model;

import java.util.Date;

public class XiCaiInvestInfo {
	
	//投资ID
	private String id;
	//p2p网站产品唯一ID
	private String pid;
	//用户名
	private String username;
	//来源
	private String display;
	//投资时间
	private String datetime;
	//投资金额
	private double money;
	//返佣金额
	private double commision;
	//投资期限
	private int life_cycle;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public double getCommision() {
		return commision;
	}
	public void setCommision(double commision) {
		this.commision = commision;
	}
	public int getLife_cycle() {
		return life_cycle;
	}
	public void setLife_cycle(int life_cycle) {
		this.life_cycle = life_cycle;
	}
	
	

}
