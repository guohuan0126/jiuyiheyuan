package com.duanrong.business.permission.service;

import java.util.List;

import base.pagehelper.PageInfo;

import com.duanrong.business.permission.model.NewMenu;


/**
 * @author xiao
 * @param <NewMenu>
 * @date 2015年1月29日 上午10:20:59
 */


public interface NewMenuService {

	/**
	 * 获取用户权限菜单
	 * @param UserId
	 * @return
	 */
	public List<NewMenu> getNewMenuByUserId(String userId);
	
	/**
	 * 获取不收权限控制的菜单
	 * @return
	 */
	public List<NewMenu> getNewMenuNoPermission();
	/**
	 * 分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param newMenu
	 * @return
	 */
	public PageInfo<NewMenu> getAllNewMenu(int pageNo, int pageSize, NewMenu newMenu);
	
	/**
	 * 根据id获取菜单
	 * @param id
	 * @return
	 */
	public NewMenu getNewMenuById(int id);
	
	/**
	 * 添加菜单
	 * @param newMenu
	 * @return
	 */
	public boolean addNewMenu(NewMenu newMenu);

	/**
	 * 修改菜单
	 * @param newMenu
	 * @return
	 */
	public boolean alertNewMenu(NewMenu newMenu);
		
	/**
	 * 删除资源
	 * @param id
	 * @return
	 */
	public boolean deleteNewMenuById(int id);
}
