package com.duanrong.business.flow.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.flow.dao.ItemFlowDao;
import com.duanrong.business.flow.model.ItemFlow;

@Repository
public class ItemFlowDaoImpl extends BaseDaoImpl<ItemFlow> implements ItemFlowDao {

public ItemFlowDaoImpl(){
		
		super.setMapperNameSpace("com.duanrong.business.flow.mapper.ItemFlowMapper");
		
	}
	
	@Override
	public int oprate(ItemFlow itemFlow) {
		
		return this.getSqlSession().update(
				getMapperNameSpace()+".oprate", itemFlow);
	}

	@Override
	public List<ItemFlow> getItemFlowByItemID(Map<String, Object> map) {
		
		return this.getSqlSession().selectList(
				getMapperNameSpace()+".getItemFlowByItemIdAndItemType", map);
	}

	@Override
	public int insertItem(ItemFlow itemFlow) {
		
		return this.getSqlSession().insert(
				getMapperNameSpace()+".insertItem", itemFlow);
	}

	@Override
	public List<ItemFlow> getItemFlowByItemIDAndUser(Map<String, Object> map) {
		
		return this.getSqlSession().selectList(
				getMapperNameSpace()+".getItemFlowByItemIdAndUser", map);
	}

	

}
