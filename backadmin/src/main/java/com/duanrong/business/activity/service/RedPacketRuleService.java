package com.duanrong.business.activity.service;

import java.util.List;

import base.pagehelper.PageInfo;

import com.duanrong.business.activity.model.RedPacketDetail;
import com.duanrong.business.activity.model.RedPacketRule;

/**
 * 活动类型
 * @author Qiu Feihu
 * @time 2015年6月19日11:04:05 
 */
public interface RedPacketRuleService {
   
	public RedPacketRule read(Integer id);
	
	public void add(RedPacketRule rule);
	
	public void update(RedPacketRule rule);
	
	public void delete(Integer id);
	
	public PageInfo<RedPacketRule> readPageLite(int pageNo,int pageSize,RedPacketRule rule);
	
	public List<RedPacketRule> readAll();
}
