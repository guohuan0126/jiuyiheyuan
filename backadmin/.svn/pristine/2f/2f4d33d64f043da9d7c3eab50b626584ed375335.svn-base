package com.duanrong.business.permission.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.permission.model.Black;
import com.duanrong.business.permission.model.Permission;

/**
 * @author xiao
 * @date 2015年1月29日 下午1:21:46
 */
public interface PermissionDao extends BaseDao<Permission> {

	/**
	 * 查询用户权限
	 * @param userId
	 * @return
	 */
	public List<Permission> getPermissionByUserId(String userId);
	
	/**
	 * 查询角色权限
	 * @param roleId
	 * @return
	 */
	public List<Permission> getPermissionByRoleId(String roleId);
	
	
	/**
	 * 清除角色权限
	 * @param roleId
	 * @return
	 */
	public int deleteRolePermission(String roleId);
	
	/**
	 * 角色授权
	 * @param map
	 * @return
	 */
	public int grantRolePermission(Map<String, Object> map);
	
	/**
	 * 清除菜单权限
	 * @param menuId
	 * @return
	 */
	public int deleteMenuPermission(int menuId);
	
	/**
	 * 菜单授权
	 * @param map
	 * @return
	 */
	public int grantMenuPermission(Map<String, Object> map);


	/**
	 * 清除用户角色信息
	 * @param userId
	 * @return
	 */
	public int deleteUserRole(String userId);

	/**
	 * 清除用户角色信息
	 * @param userId
	 * @return
	 */
	public int deleteUserRoleID(Map<String, String> map);
	/**
	 * 用户授角色
	 * @param map
	 * @return
	 */
	public int grantUserRole(Map<String, Object> map);
	
	/**
	 * 获取菜单权限
	 * @param menuId
	 * @return
	 */
	public List<Permission> getPermissionByMenuId(int newMenuId);
	
	/**
	 * 获取用户当前请求的权限
	 * @param map
	 * @return
	 */
	public List<Permission> getPermissionByUserIdAndUrl(String userId);
	
	/**
	 * 根据id获取当前用户拥有的操作
	 * @param userId
	 * @param menuId
	 * @return
	 */
	public List<String> getActiveByUserIdAndId(String userId);
	
	/**
	 * 获取所有ip白名单
	 * @return
	 */
	public String getAllIP();
	
	/**
	 * 追加ip
	 * @param 
	 * @return
	 */
	public int updateIPs(String ips);
	
	
	/**
	 * ip验证字段
	 * @param name
	 * @return
	 */
	public String getSuper(String name);
	PageInfo<Black> blackPageInfo(int pageNo, int pageSize,Map<String, Object> types);
	public void addblack(List<Black> list);
	public int delete(int id);
	public int delete(String content);
	
	/**
	 * 根据类型查询黑名单
	 * @param type
	 * @return
	 */
	public List<Black> findBlackByType(String type);

	

}
