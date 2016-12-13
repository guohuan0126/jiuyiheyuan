package com.duanrong.business.flow.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import util.Log;
import base.pagehelper.PageInfo;

import com.duanrong.business.award.ItemTypeUtil;
import com.duanrong.business.award.dao.AwardItemDao;
import com.duanrong.business.award.model.AwardItem;
import com.duanrong.business.award.model.ItemType;
import com.duanrong.business.flow.dao.ItemFlowDao;
import com.duanrong.business.flow.model.Flow;
import com.duanrong.business.flow.model.FlowNode;
import com.duanrong.business.flow.model.ItemFlow;
import com.duanrong.business.flow.service.FlowService;
import com.duanrong.business.flow.service.ItemFlowService;

@Service
public class ItemFlowServiceImpl implements ItemFlowService {

	@Resource
	ItemFlowDao itemFlowDao;

	@Resource
	AwardItemDao awardItemDao;
	
	@Resource
	FlowService flowService;

	@Resource
	Log log;

	@Override
	public boolean oprate(ItemFlow itemFlow, String userId) {
		
		ItemFlow item = GetCurrentItemFlow(
				itemFlow.getItemId(), ItemTypeUtil.getItemType(itemFlow.getItemType()), userId);
		ItemFlow currentItemFlow = itemFlowDao.get(itemFlow.getId());
		if(item != null && currentItemFlow != null && 
				item.getId() == currentItemFlow.getId()){	
				AwardItem awardItem = awardItemDao.get(itemFlow.getItemId());
				awardItem.setId(itemFlow.getItemId());
				if(itemFlow.getNodeId() == 45 && itemFlow.getStatus().equals("approve")){
					awardItem.setStatus("等待发送");					
				}
				awardItemDao.update(awardItem);
				return itemFlowDao.oprate(itemFlow) > 0;
																							
		}		
		return false;
	}

	@Override
	public boolean issueItem(int flowId, int itemId, ItemType type) {
		int flag = 0;
		Flow flow = flowService.getFlowByFlowId(flowId);
		for (FlowNode node : flow.getNodes()) {
			ItemFlow itemFlow = new ItemFlow();
			itemFlow.setFlowId(flow.getFlowId());
			itemFlow.setNodeId(node.getNodeId());
			itemFlow.setStatus(ItemType.UNDEAL.toString());
			itemFlow.setCreateTime(new Date());
			itemFlow.setItemType(type.toString());
			itemFlow.setItemId(itemId);
			flag += itemFlowDao.insertItem(itemFlow);
		}

		log.infoLog("AwardItemServiceImpl.class issueItem", "共插入： " + flag
				+ " 条项目流程");
		return flag > 0;
	}

	@Override
	public PageInfo<ItemFlow> getAllItemFlow(int pageNo, int pageSize,
			ItemFlow itemFlow) {

		return itemFlowDao.pageLite(pageNo, pageSize, itemFlow);
	}	
	
	@Override
	public ItemFlow GetCurrentItemFlow(int itemId, ItemType type, String userId) {
		List<ItemFlow> itemFlows = getItemFlowByItemID(itemId, type);
		ItemFlow itemFlow = null;
		if (!itemFlows.isEmpty()) {						
			for (int i = 0; i < itemFlows.size(); i++) {
				if (i != 0) {
					if (ItemType.APPROVE.toString().equals(
							itemFlows.get(i - 1).getStatus())
							&& ItemType.UNDEAL.toString().equals(
									itemFlows.get(i).getStatus())) {
						itemFlow = itemFlows.get(i);
						break;
					}else if(ItemType.UNAPPROVED.toString().equals(
							itemFlows.get(i - 1).getStatus())
							&& ItemType.UNDEAL.toString().equals(
									itemFlows.get(i).getStatus())){
						itemFlow = itemFlows.get(i-1);
						break;
					}
				} else {
					if (ItemType.UNDEAL.toString().equals(
							itemFlows.get(i).getStatus())) {
						itemFlow =  itemFlows.get(i);
						break;
					}
				}
			}
			if(itemFlow != null){
				if(getItemFlowByItemID(itemFlow.getId(), type, userId).size() == 0 ){
					return null;
				}
			}
		}
		return itemFlow;
	}

	@Override
	public List<ItemFlow> getItemFlowByItemID(int itemId, ItemType type) {
		Map<String, Object> map = new HashMap<>();
		map.put("itemId", itemId);
		map.put("itemType", type);
		return itemFlowDao.getItemFlowByItemID(map);
	}

	@Override
	public List<ItemFlow> getItemFlowByItemID(int id, ItemType type,
			String userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("itemType", type);
		map.put("userId", userId);
		return itemFlowDao.getItemFlowByItemIDAndUser(map);
	}

}
