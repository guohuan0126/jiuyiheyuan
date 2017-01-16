package com.duanrong.business.user.model;

import base.model.BaseModel;

/**
 * 角色实体类
 * 
 * @author 尹逊志
 * @date 2014-8-30下午2:52:29
 */
public class Role extends BaseModel {
	private static final long serialVersionUID = -7673535127091430758L;

	private String id;
	private String name;
	private String description;
	/*private String status;
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}*/

	public Role(String id) {
		this.id = id;
	}

	public Role() {
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", description="
				+ description + "]";
	}

}