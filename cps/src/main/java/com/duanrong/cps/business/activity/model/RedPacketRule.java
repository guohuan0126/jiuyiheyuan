package com.duanrong.cps.business.activity.model;

/**
 * 活动类型
 * @author Qiu Feihu
 * @time 2015年6月18日16:13:33
 */
public class RedPacketRule {
  
	private Integer id;      //编号
	private String name;     //活动名称
	private String type;     //活动类型
	private String sendType; //发送类型
	private String getRule;  //活动规则
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getGetRule() {
		return getRule;
	}
	public void setGetRule(String getRule) {
		this.getRule = getRule;
	}
	
}
