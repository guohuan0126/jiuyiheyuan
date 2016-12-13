package com.duanrong.business.permission.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import util.Log;
import base.pagehelper.PageInfo;

import com.duanrong.business.permission.dao.NewMenuDao;
import com.duanrong.business.permission.model.NewMenu;
import com.duanrong.business.permission.service.NewMenuService;

/**
 * @author xiao
 * @date 2015年1月29日 上午10:23:39
 */

@Service
public class NewMenuServiceImpl implements NewMenuService {

	@Resource
	NewMenuDao newMenuDao;

	@Resource
	Log log;

	public List<NewMenu> readNewMenuByUserId(String userId) {
		List<NewMenu> newMenusNoPermission = newMenuDao.getNewMenuNoPermission();
		List<NewMenu> newMenus = newMenuDao.getMenuByUserId(userId);
		if(!newMenusNoPermission.isEmpty()){
			for(NewMenu newMenu : newMenusNoPermission){
				newMenus.add(newMenu);
			}
			newMenusNoPermission.clear();
		}
		return newMenus;
	}

	public NewMenu readNewMenuById(int id) {

		return newMenuDao.get(id);
	}

	public boolean addNewMenu(NewMenu newMenu) {

		if (readNewMenuById(newMenu.getId()) == null) {
			return newMenuDao.insertNewMenu(newMenu) > 0;
		}
		return false;
	}

	public boolean alertNewMenu(NewMenu newMenu) {

		if (readNewMenuById(newMenu.getId()) != null) {
			return newMenuDao.updateNewMenu(newMenu) > 0;	
		}
		return false;
	}

	@Override
	public PageInfo<NewMenu> readAllNewMenu(int pageNo, int pageSize,
			NewMenu newMenu) {		
		return newMenuDao.pageLite(pageNo, pageSize, newMenu);
	}

	@Override
	public boolean deleteNewMenuById(int id) {
		
		if (readNewMenuById(id) != null) {
			return newMenuDao.deleteNewMenuById(id) > 0;
		}
		return false;
	}

	@Override
	public List<NewMenu> readNewMenuNoPermission() {
		
		return newMenuDao.getNewMenuNoPermission();
	}

	
}
