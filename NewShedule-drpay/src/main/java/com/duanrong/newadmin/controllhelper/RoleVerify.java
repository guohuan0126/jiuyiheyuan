package com.duanrong.newadmin.controllhelper;

import java.util.ArrayList;
import java.util.List;

import com.duanrong.business.user.model.Role;


/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-9-13 下午3:53:00
 * @Description : drmob com.duanrong.mweb.controllhelper RoleVerify.java
 * 
 */
public class RoleVerify {
	/**
	 * 
	 * @description 验证用户是否包含指定权限
	 * @author 孙铮
	 * @time 2014-9-13 下午4:04:00
	 * @param roles 该用户的权限集合
	 * @param RoleName 要验证的权限
	 * @return
	 */
	public static boolean getUserIsContainRoleByRoleName(List<Role> roles,
			String RoleID) {
		boolean flag = false;
		List<String> roleNames = new ArrayList<String>();
		if (roles != null && roles.size() > 0) {
			for (Role role : roles) {
				roleNames.add(role.getId());
			}
		}
		if(RoleID != null && roleNames.contains(RoleID)){
			flag = true;
		}
		return flag;
	}
}
