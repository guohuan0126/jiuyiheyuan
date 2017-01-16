package com.duanrong.business.sms.model;

import base.model.BaseModel;

/**
 * 用户消息模板
 * 
 * @author Lin Zhiming
 * @version Jun 1, 2015 2:33:43 PM
 */
public class UserMessageTemplate extends BaseModel {

	private String id;
	private String messageNode;
	private String messageWay;
	private String template;

	@Override
	public String toString() {
		return "UserMessageTemplate [id=" + id + ", messageNode=" + messageNode
				+ ", messageWay=" + messageWay + ", template=" + template + "]";
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessageNode() {
		return messageNode;
	}

	public void setMessageNode(String messageNode) {
		this.messageNode = messageNode;
	}

	public String getMessageWay() {
		return messageWay;
	}

	public void setMessageWay(String messageWay) {
		this.messageWay = messageWay;
	}

	private static final long serialVersionUID = 1946442400173160437L;

}