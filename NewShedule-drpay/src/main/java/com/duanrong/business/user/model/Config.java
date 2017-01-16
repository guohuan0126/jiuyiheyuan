package com.duanrong.business.user.model;

import base.model.BaseModel;

public class Config extends BaseModel {

	private static final long serialVersionUID = 7225308022716894476L;
	
	private String id; 
	private String name;
	private String value;
	private String description;
	private String type;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	
}
