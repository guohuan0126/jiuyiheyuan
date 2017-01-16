package com.duanrong.business.permission.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import base.pagehelper.PageInfo;

import com.duanrong.business.permission.model.Black;
import com.duanrong.business.permission.model.NewMenu;
import com.duanrong.business.permission.model.Permission;
import com.duanrong.business.user.model.Role;

/**
 * @author xiao
 * @date 2015年1月29日 下午1:38:21
 */

public interface PermissionService {

	/**
	 * 得到用户权限
	 * 
	 * @param userId
	 * @return
	 */
	public List<Permission> getPermisstionByUserId(String userId);

	/**
	 * 得到角色权限
	 * 
	 * @param roleId
	 * @return
	 */
	public List<Permission> getPermisstionByRoleId(String roleId);

	/**
	 * 分页查询
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param permission
	 * @return
	 */
	public PageInfo<Permission> pageLite(int pageNo, int pageSize,
			Permission permission);

	/**
	 * 添加权限
	 * 
	 * @param permission
	 * @return
	 * @throws Exception
	 */
	public void insertPermission(Permission permission) throws Exception;

	/**
	 * 修改权限
	 * 
	 * @param permission
	 * @return
	 * @throws Exception
	 */
	public void updatePermission(Permission permission) throws Exception;

	/**
	 * 根据id得到权限
	 * 
	 * @param id
	 * @return
	 */
	public Permission getPermissionById(String id);

	/**
	 * 清除角色权限
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteRolePermission(String roleId);

	/**
	 * 角色授权
	 * 
	 * @param map
	 * @return
	 */
	public boolean grantRolePermission(Role role, List<Permission> permissions);

	/**
	 * 清除菜单权限
	 * 
	 * @param menuId
	 * @return
	 */
	public int deleteMenuPermission(int menuId);

	/**
	 * 菜单授权
	 * 
	 * @param map
	 * @return
	 */
	public boolean grantMenuPermission(NewMenu newMenu,
			List<Permission> permissions);

	/**
	 * 清除用户角色信息
	 * 
	 * @param userId
	 * @return
	 */
	public int deleteUserRole(String userId);

	/**
	 * 用户授角色
	 * 
	 * @param map
	 * @return
	 */
	public boolean grantUserRole(String userId, List<Role> roles);
	
	/**
	 * 获取菜单权限
	 * @param menuId
	 * @return
	 */
	public List<Permission> getPermissionByMenuId(int newMenuId);
			
	/**
	 * 用户是否拥有此操作权限
	 * @param url
	 * @return
	 */
	public List<String> hasActivePermission(String userId);
	
	/**
	 * 获取ip列表
	 * @param menuId
	 * @return
	 */
	public List<String> getIpList();
	
	/**
	 * 添加ip
	 * @param 
	 * @return
	 */
	public boolean addIPs(List<String> ips);
	
	/**
	 * 修改ip
	 * @param ips
	 * @return
	 */
	public boolean updateIPs(List<String> ips);
	
	/**
	 * 删除ip
	 * @param ips
	 * @return
	 */
	public boolean deleteIPs(List<String> ips);
	
	/**
	 * ip过滤字段
	 * @param name
	 * @return
	 */
	public String getSuper(String name);
	public PageInfo<Black> blackPageInfo(int pageNo, int pageSize,Map<String, Object> types);
	public void addblack(List<Black> list);
	public boolean delete(int id);
	public boolean deleteIPMob(String content);
	
	/**
	 * 根据类型查询黑名单
	 * @param type
	 * @return
	 */
	public List<Black> findBlackByType(String type);


	
}
