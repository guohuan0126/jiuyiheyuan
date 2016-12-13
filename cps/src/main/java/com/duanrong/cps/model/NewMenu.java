package com.duanrong.cps.model;

import base.model.BaseModel;

/**
 * @author xiao
 * @date 2015年1月29日 上午10:01:11
 */
public class NewMenu extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8070184556982741436L;
	
	
	private Integer id; //菜单编号
	
	private String menuName; //菜单名称
	
	private Integer parentId; //父节点id
	
	private String url; //资源
	
	private String type; //菜单类型（保留字段）

	private Integer order; //排序字段
	
	private Integer status; //菜单状态
	
	private String systypeId;// 系统类型(权限控制用)
	
	/**
	 * 权限id
	 * @author dongjun add by 2016-3-4
	 */
	private String permissionId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getSystypeId() {
		return systypeId;
	}

	public void setSystypeId(String systypeId) {
		this.systypeId = systypeId;
	}

	
	

}
