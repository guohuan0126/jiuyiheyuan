package com.duanrong.business.user.service;

import java.util.List;

import base.pagehelper.PageInfo;

import com.duanrong.business.user.model.Role;

/**
 * 
 * @author 尹逊志
 * @date 2014-9-1上午10:41:41
 */
public interface RoleService {
	/**
	 * 获取所有的角色
	 * 
	 * @return
	 */
	public List<Role> getAllRoles();

	/**
	 * 根据角色Id获取角色信息
	 * 
	 * @param id
	 * @return
	 */
	public Role getRoleById(String id);

	/**
	 * 根据用户id获取该用户的角色列表
	 * 
	 * @param id
	 * @return
	 */
	public List<Role> getRolesByUserId(String userId);

	/**
	 * 条件查询
	 * 
	 * @param pagaNo
	 * @param pageSize
	 * @param role
	 * @return
	 */
	public PageInfo<Role> findPaging(int pageNo, int pageSize, Role role);

	/**
	 * 添加角色信息
	 * 
	 * @param role
	 * @return
	 */
	public boolean addRole(Role role);

	/**
	 * 修改角色信息
	 * 
	 * @param role
	 * @return
	 */
	public boolean alertRole(Role role);

	/**
	 * 根据id禁用角色
	 * 
	 * @param 角色ID
	 * @return
	 */
	public void disableRole(String id);
}
