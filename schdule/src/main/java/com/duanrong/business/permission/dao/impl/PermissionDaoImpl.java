package com.duanrong.business.permission.dao.impl;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.permission.dao.PermissionDao;
import com.duanrong.business.permission.model.Black;
import com.duanrong.business.permission.model.Permission;

/**
 * @author xiao
 * @date 2015年1月29日 下午1:24:15
 */

@Repository
public class PermissionDaoImpl extends BaseDaoImpl<Permission> implements
		PermissionDao {

	public PermissionDaoImpl(){
		
		this.setMapperNameSpace("com.duanrong.business.permission.mapper.PermissionMapper");
	}
	
	
	public List<Permission> getPermissionByUserId(String userId) {
		
		return getSqlSession().selectList(getMapperNameSpace()+".getPermissionByUserId", userId);
	}

	
	public List<Permission> getPermissionByRoleId(String roleId) {
		
		return getSqlSession().selectList(getMapperNameSpace()+".getPermissionByRoleId", roleId);
	}


	public int deleteRolePermission(String roleId) {
		
		return getSqlSession().delete(getMapperNameSpace()+".deletePermissionByRoleId", roleId);
	}


	
	public int grantRolePermission(Map<String, Object> map) {
		
		return getSqlSession().insert(getMapperNameSpace()+".grantRolePermission", map);
	}


	
	public int deleteMenuPermission(int menuId) {
		return getSqlSession().delete(getMapperNameSpace()+".deletePermissionByMenuId", menuId);
	}


	public int grantMenuPermission(Map<String, Object> map) {
		
		return getSqlSession().insert(getMapperNameSpace()+".grantMenuPermission", map);
	}


	public int deleteUserRole(String userId) {
		return getSqlSession().delete(getMapperNameSpace()+".deleteRoleByUserId", userId);
	}


	
	public int grantUserRole(Map<String, Object> map) {
		
		return getSqlSession().insert(getMapperNameSpace()+".grantUserRole", map);
	}


	@Override
	public List<Permission> getPermissionByMenuId(int newMenuId) {
		
		return getSqlSession().selectList(getMapperNameSpace()+".getPermissionByMenuId", newMenuId);
	}


	@Override
	public List<Permission> getPermissionByUserIdAndUrl(String userId) {
		
		return getSqlSession().selectList(getMapperNameSpace()+".getActiveByUserIdAndId", userId);
	}

	@Override
	public List<String> getActiveByUserIdAndId(String userId) {
		return getSqlSession().selectList(getMapperNameSpace()+".getActiveByUserIdAndId", userId);
	}


	@Override
	public String getAllIP() {		
		return getSqlSession().selectOne(
				getMapperNameSpace() + ".getAllIp");
	}


	@Override
	public int updateIPs(String ips) {		
		return getSqlSession().insert(
				getMapperNameSpace()+".updateIps", ips);
	}


	@Override
	public String getSuper(String name) {		
		return getSqlSession().selectOne(
				getMapperNameSpace() + ".getSuper", name);
	}


	@Override
	public PageInfo<Black> blackPageInfo(int pageNo, int pageSize,Map<String, Object> types) {
		PageHelper.startPage(pageNo, pageSize);
		List<Black> list = getSqlSession().selectList(
				getMapperNameSpace() + ".blackPageInfo",types);
		PageInfo<Black> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	public List<Black> findBlackByType(String type){
		List<Black> list = getSqlSession().selectList(
				getMapperNameSpace() + ".findBlackByType",type);
		return list;
	}


	public void addblack(List<Black> list) {
		 getSqlSession().insert(
				getMapperNameSpace()+".addblack", list);
	}
  
	public int delete(int id) {
		return this.getSqlSession().update(getMapperNameSpace()+".delete", id);
	}

	@Override
	public int delete(String content) {
	
		return this.getSqlSession().update(getMapperNameSpace()+".deleteIpMob", content);
	}

}
