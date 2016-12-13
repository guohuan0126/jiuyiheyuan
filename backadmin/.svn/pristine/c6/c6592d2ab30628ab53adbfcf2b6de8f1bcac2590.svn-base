package com.duanrong.business.permission.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.permission.dao.NewMenuDao;
import com.duanrong.business.permission.model.NewMenu;

/**
 * @author xiao
 * @date 2015年1月29日 上午10:19:55
 */
@Repository
public class NewMenuDaoImpl extends BaseDaoImpl<NewMenu> implements NewMenuDao {

	public NewMenuDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.permission.mapper.NewMenuMapper");
	}

	@Override
	public List<NewMenu> getMenuByUserId(String userId) {
		return getSqlSession().selectList(
				getMapperNameSpace() + ".getNewMenuByUserId", userId);
	}

	@Override
	public int insertNewMenu(NewMenu newMenu) {
		return getSqlSession().insert(getMapperNameSpace() + ".insertNewMenu",
				newMenu);
	}

	@Override
	public int updateNewMenu(NewMenu newMenu) {
		return getSqlSession().update(getMapperNameSpace() + ".updateNewMenu",
				newMenu);
	}
	
	@Override
	public List<NewMenu> getNewMenuNoPermission() {		
		return getSqlSession().selectList(
				getMapperNameSpace() + ".getNewMenuNoPermission");
	}

	@Override
	public int deleteNewMenuById(int id) {		
		return getSqlSession().update(
				getMapperNameSpace() + ".deleteNewMenuById", id);
	}

	

}
