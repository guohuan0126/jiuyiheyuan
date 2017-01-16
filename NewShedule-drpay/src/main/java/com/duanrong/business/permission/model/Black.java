package com.duanrong.business.permission.model;

import base.model.BaseModel;

/**
 * @author xiao
 * @date 2015年1月29日 下午1:15:57
 */
public class Black extends BaseModel{

	private String id;
	private String content;
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "BlackList [id=" + id + ", content=" + content + ", type="
				+ type + "]";
	}

	private static final long serialVersionUID = -5339830976712847846L;
}
