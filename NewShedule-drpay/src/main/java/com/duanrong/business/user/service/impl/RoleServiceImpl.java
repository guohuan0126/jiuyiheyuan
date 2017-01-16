package com.duanrong.business.user.service.impl;

import java.util.List;

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

	public List<Role> getAllRoles() {
		return roleDao.findAll();
	}

	public Role getRoleById(String id) {
		return roleDao.get(id);
	}

	public List<Role> getRolesByUserId(String userId) {
		return roleDao.getRolesByUserId(userId);
	}

	public PageInfo<Role> findPaging(int pageNo, int pageSize, Role role) {

		return roleDao.pageLite(pageNo, pageSize, role);
	}

	public boolean addRole(Role role) {
		if (null == getRoleById(role.getId())) {
			roleDao.insert(role);
			return true;
		}
		return false;
	}

	public boolean alertRole(Role role) {
		if (null != getRoleById(role.getId())) {
			roleDao.update(role);
			return true;
		}
		return false;
	}

	public void disableRole(String id) {
		if (null != getRoleById(id)) {
			roleDao.disableRole(id);
		}
	}

}
