package com.duanrong.business.flow.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;

import com.duanrong.business.flow.model.ItemFlow;

public interface ItemFlowDao extends BaseDao<ItemFlow> {
	
	
	/**
	 * 操作流程
	 * @param itemFlow
	 */
	public int oprate(ItemFlow itemFlow);
	
	
	/**
	 * 获取项目流程
	 * @param map
	 * @return
	 */
	public List<ItemFlow> getItemFlowByItemID(Map<String, Object> map);
	
	
	/**
	 * 获取项目流程
	 * @param map
	 * @return
	 */
	public List<ItemFlow> getItemFlowByItemIDAndUser(Map<String, Object> map);

	
	/**
	 * 添加流程
	 * @param itemFlow
	 */
	public int insertItem(ItemFlow itemFlow);
}
