package com.duanrong.business.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.duanrong.business.user.dao.RoleDao;
import com.duanrong.business.user.model.Role;

import base.dao.impl.BaseDaoImpl;

/**
 * 
 * @author 尹逊志
 * @date 2014-9-1上午10:39:19
 */
@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {
	public RoleDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.user.mapper.RoleMapper"); // 设置命名空间
	}

	@Override
	public List<Role> getRolesByUserId(String userId) {
		List<Role> roleList = this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getRolesByUserId", userId);
		return roleList;
	}

	@Override
	public void disableRole(String id) {
		this.getSqlSession().update(getMapperNameSpace() + ".disableRole", id);
	}

}
