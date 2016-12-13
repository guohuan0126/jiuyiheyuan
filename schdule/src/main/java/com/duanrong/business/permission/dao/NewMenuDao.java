package com.duanrong.business.permission.dao;

import java.util.List;
import base.dao.BaseDao;

import com.duanrong.business.permission.model.NewMenu;

/**
 * @author xiao
 * @date 2015年1月29日 上午10:15:06
 */
public interface NewMenuDao extends BaseDao<NewMenu> {
	
	/**
	 * 根据用户编号查询菜单
	 * @param userId
	 * @return
	 */
	public List<NewMenu> getMenuByUserId(String userId);
	
	/**
	 * 添加菜单
	 * @param newMenu
	 * @return
	 */
	public int insertNewMenu(NewMenu newMenu);

	/**
	 * 修改菜单
	 * @param newMenu
	 * @return
	 */
	public int updateNewMenu(NewMenu newMenu);
		
	/**
	 * 获取不受权限控制的资源
	 * @return
	 */
	public List<NewMenu> getNewMenuNoPermission();
	
	
	/**
	 * 删除资源（逻辑）
	 * @return
	 */
	public int deleteNewMenuById(int id);
	
	
}
