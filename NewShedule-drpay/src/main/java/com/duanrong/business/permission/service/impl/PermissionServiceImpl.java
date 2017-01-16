package com.duanrong.business.permission.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import util.Log;
import util.MyStringUtils;
import base.pagehelper.PageInfo;

import com.duanrong.business.permission.dao.PermissionDao;
import com.duanrong.business.permission.model.Black;
import com.duanrong.business.permission.model.NewMenu;
import com.duanrong.business.permission.model.Permission;
import com.duanrong.business.permission.service.NewMenuService;
import com.duanrong.business.permission.service.PermissionService;
import com.duanrong.business.user.model.Role;

/**
 * @author xiao
 * @date 2015年1月29日 下午1:41:02
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	@Resource
	PermissionDao permissionDao;

	@Resource
	NewMenuService newMenuService;
	
	@Resource
	Log log;

	@Override
	public List<Permission> getPermisstionByUserId(String userId) {
		return permissionDao.getPermissionByUserId(userId);
	}

	@Override
	public PageInfo<Permission> pageLite(int pageNo, int pageSize,
			Permission permission) {
		return permissionDao.pageLite(pageNo, pageSize, permission);
	}

	@Override
	public void insertPermission(Permission permission) throws Exception {
		if (null == getPermissionById(permission.getId())) {
			permissionDao.insert(permission);
		} else {
			throw new Exception(
					"PermissionService.class insertPermission : 权限编号已存在！");
		}
	}

	@Override
	public void updatePermission(Permission permission) throws Exception {
		if (null != getPermissionById(permission.getId())) {
			permissionDao.update(permission);
		} else {
			throw new Exception(
					"PermissionService.class updatePermission : 权限不存在！");
		}
	}

	@Override
	public Permission getPermissionById(String id) {
		return permissionDao.get(id);
	}

	@Override
	public List<Permission> getPermisstionByRoleId(String roleId) {
		return permissionDao.getPermissionByRoleId(roleId);
	}

	@Override
	public int deleteRolePermission(String roleId) {
		return permissionDao.deleteRolePermission(roleId);
	}

	@Override
	public boolean grantRolePermission(Role role, List<Permission> permissions) {
		Map<String, Object> map = new HashMap<>();
		int status = 0;
		if (MyStringUtils.isNotBlank(role.getId())) {
			status = deleteRolePermission(role.getId());
			log.infoLog("PermissionService.class deleteRolePermission",
					MyStringUtils.append("ID 为：", role.getId(),
							" 的角色权限已全部清除, 共清除 ", status, " 项权限"));
			status = 0;
			for (Permission permission : permissions) {
				if (MyStringUtils.isNotBlank(permission.getId())) {
					map.put("roleId", role.getId());
					map.put("permissionId", permission.getId());
					status += permissionDao.grantRolePermission(map);
					map.clear();
				}
			}
			log.infoLog("PermissionService.class grantRolePermission",
					MyStringUtils.append("已为 ID 为：", role.getId(),
							" 的角色重新授权, 共授予 ", status, " 项权限"));
			return true;
		}

		return false;
	}

	public int deleteMenuPermission(int menuId) {
		return permissionDao.deleteMenuPermission(menuId);
	}

	public boolean grantMenuPermission(NewMenu newMenu,
			List<Permission> permissions) {
		Map<String, Object> map = new HashMap<>();
		int status = 0;
		if (newMenu.getId() != 0) {
			status = deleteMenuPermission(newMenu.getId());
			log.infoLog("PermissionService.class deleteMenuPermission",
					MyStringUtils.append("ID 为：", newMenu.getId(),
							" 的菜单权限已全部清除, 共清除 ", status, " 项权限"));
			status = 0;
			for (Permission permission : permissions) {
				if (MyStringUtils.isNotBlank(permission.getId())) {
					map.put("newMenuId", newMenu.getId());
					map.put("permissionId", permission.getId());
					status += permissionDao.grantMenuPermission(map);
					map.clear();
				}
			}
			log.infoLog("PermissionService.class grantMenuPermission",
					MyStringUtils.append("已为 ID 为：", newMenu.getId(),
							" 的菜单重新授权, 共授予 ", status, " 项权限"));
			return true;
		}

		return false;
	}

	public int deleteUserRole(String userId) {
		return permissionDao.deleteUserRole(userId);
	}

	public boolean grantUserRole(String userId, List<Role> roles) {
		Map<String, Object> map = new HashMap<>();
		int status = 0;
		if (MyStringUtils.isNotBlank(userId)) {
			status = deleteUserRole(userId);
			log.infoLog("PermissionService.class deleteUserRole", MyStringUtils
					.append("ID 为：", userId, " 的用户角色已全部清除, 共清除 ", status,
							" 项权限"));
			status = 0;
			for (Role role : roles) {
				if (MyStringUtils.isNotBlank(role.getId())) {
					map.put("userId", userId);
					map.put("roleId", role.getId());
					status += permissionDao.grantUserRole(map);
					map.clear();
				}
			}
			log.infoLog("PermissionService.class grantMenuPermission",
					MyStringUtils.append("已为 ID 为：", userId,
							" 的用户重新授与角色, 共授予 ", status, " 项角色"));
			return true;
		}

		return false;
	}

	@Override
	public List<Permission> getPermissionByMenuId(int newMenuId) {
		
		return permissionDao.getPermissionByMenuId(newMenuId);
	}
	
	
	@Override
	public List<String> hasActivePermission(String userId) {				
		return permissionDao.getActiveByUserIdAndId(userId);
	}

	@Override
	public List<String> getIpList() {
		String ip = permissionDao.getAllIP();
		List<String> list = new ArrayList<>();
		if(MyStringUtils.isNotAnyBlank(ip)){
			String[] ips = ip.split(",");			
			if(ips.length > 0){
				for(int i = 0; i<ips.length; i++){
					list.add(ips[i]);
				}
			}
		}
		return list;
	}

	@Override
	public boolean addIPs(List<String> ips) {	
		List<String> ip = getIpList();
		StringBuffer sb = new StringBuffer();
		if(!ips.isEmpty() && !ip.isEmpty()){
			for(String s : ip){
				sb.append(s + ",");
			}
			for(String s : ips){
				sb.append(s + ",");
			}
			String ipStr = sb.toString();
			if(",".equals(ipStr.substring(ipStr.length() - 1))){
				ipStr = ipStr.substring(0, ipStr.length() - 1);
			}
			return permissionDao.updateIPs(ipStr) > 0;
		}
		return false;
	}

	@Override
	public boolean updateIPs(List<String> ips) {		
		StringBuffer sb = new StringBuffer();
		if(!ips.isEmpty()){
			for(String s : ips){
				sb.append(s + ",");
			}			
			String ipStr = sb.toString();
			if(",".equals(ipStr.substring(ipStr.length() - 1))){
				ipStr = ipStr.substring(0, ipStr.length() - 1);
			}
			return permissionDao.updateIPs(ipStr) > 0;
		}
		return false;
	}

	@Override
	public boolean deleteIPs(List<String> ips) {				
		StringBuffer sb = new StringBuffer();
		if(!ips.isEmpty()){
			for(String s : ips){
				sb.append(s + ",");
			}			
			String ipStr = sb.toString();
			if(",".equals(ipStr.substring(ipStr.length() - 1))){
				ipStr = ipStr.substring(0, ipStr.length() - 1);
			}
			return permissionDao.updateIPs(ipStr) > 0;
		}
		return false;
	}

	@Override
	public String getSuper(String name) {
		return permissionDao.getSuper(name);
	}

	@Override
	public PageInfo<Black> blackPageInfo(int pageNo, int pageSize,Map<String, Object> types) {
		return permissionDao.blackPageInfo(pageNo, pageSize,types);
	}

	@Override
	public void addblack(List<Black> list) {
		permissionDao.addblack(list);
		
	}

	@Override
	public boolean delete(int id) {
		return permissionDao.delete(id) > 0;
	}
  
	@Override
	public boolean deleteIPMob(String content){
		return permissionDao.delete(content)> 0;
		
	}
	@Override
	public List<Black> findBlackByType(String type) {
		return this.permissionDao.findBlackByType(type);
	}

}
