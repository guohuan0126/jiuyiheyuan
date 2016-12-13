package com.duanrong.business.user.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import util.Log;
import base.pagehelper.PageInfo;

import com.duanrong.business.user.dao.RoleDao;
import com.duanrong.business.user.model.Role;
import com.duanrong.business.user.service.RoleService;

/**
 * 
 * @author 尹逊志
 * @date 2014-9-1上午10:41:22
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Resource
	RoleDao roleDao;

	@Resource
	Log log;

	public List<Role> readAllRoles() {
		return roleDao.findAll();
	}

	public Role readRoleById(String id) {
		return roleDao.get(id);
	}

	public List<Role> readRolesByUserId(String userId) {
		return roleDao.getRolesByUserId(userId);
	}

	public Map<String,String> readInvestorByUserId(String userId){
		
		return roleDao.getInvestorByUserId(userId);
	}
	
	
	
	public PageInfo<Role> readPaging(int pageNo, int pageSize, Role role) {

		return roleDao.pageLite(pageNo, pageSize, role);
	}

	public boolean addRole(Role role) {
		if (null == readRoleById(role.getId())) {
			roleDao.insert(role);
			return true;
		}
		return false;
	}

	public boolean alertRole(Role role) {
		if (null != readRoleById(role.getId())) {
			roleDao.update(role);
			return true;
		}
		return false;
	}

	public void disableRole(String id) {
		if (null != readRoleById(id)) {
			roleDao.disableRole(id);
		}
	}

}
