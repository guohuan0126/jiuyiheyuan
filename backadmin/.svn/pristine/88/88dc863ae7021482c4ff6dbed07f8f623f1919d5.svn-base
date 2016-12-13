package com.duanrong.business.flow.service;

import java.util.List;
import base.pagehelper.PageInfo;
import com.duanrong.business.flow.model.ItemFlow;
import com.duanrong.business.award.model.ItemType;


public interface ItemFlowService {

	
	/**
	 * 操作流程,
	 * 首先判断当前审批是否有效，包括操作用户有效，前置步骤是否完成。
	 * @param itemFlow
	 */
	public boolean oprate(ItemFlow itemFlow, String userId);
	

	/**
	 * 设置项目流程关联
	 * @param itemFlows
	 * @return 
	 */
	public boolean issueItem(int flowId, int itemId,ItemType type);
	
	/**
	 * 分页
	 * @param itemFlow
	 * @return
	 */
	 public PageInfo<ItemFlow> getAllItemFlow(int pageNo, int pageSize,
			ItemFlow itemFlow);
	 
	 /**
	  * 根据项目id获得项目的当前正在进行的审批步骤
	  * @param itemId
	  * @param itemType
	  * @return
	  */
	 public ItemFlow GetCurrentItemFlow(int itemId, ItemType type, String userId);
	 
	 /***
	  * 获得某个项目的所有flownitem。
	  * 可用作项目审批进度效果
	  * @param itemId
	  * @param type
	  * @return
	  */
	 public List<ItemFlow> getItemFlowByItemID(int itemId, ItemType type);
	 
	 /***
	  * 获得某个项目的所有flownitem。
	  * 可用作项目审批进度效果
	  * @param itemId
	  * @param type
	  * @return
	  */
	 public List<ItemFlow> getItemFlowByItemID(int itemId, ItemType type, String userId);
	 
}
