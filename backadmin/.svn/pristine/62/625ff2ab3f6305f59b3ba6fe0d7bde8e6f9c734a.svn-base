package com.duanrong.business.user.dao;

import java.util.List;
import java.util.Map;

import com.duanrong.business.user.model.Role;

import base.dao.BaseDao;

/**
 * 
 * @author 尹逊志
 * @date 2014-9-1上午10:37:50
 */
public interface RoleDao extends BaseDao<Role> {
	/**
	 * 根据用户Id获取用户所拥有的角色
	 * 
	 * @param userId
	 * @return
	 */
	public List<Role> getRolesByUserId(String userId);

	/**
	 * 根据id禁用角色
	 * 
	 * @param id 角色ID
	 * @return
	 */
	public void disableRole(String id);
	
	/**
	 * 根据id查看是否开通易宝账户
	 * 
	 * @param id 角色ID
	 * @return
	 */
	public Map<String,String> getInvestorByUserId(String userId);

}
