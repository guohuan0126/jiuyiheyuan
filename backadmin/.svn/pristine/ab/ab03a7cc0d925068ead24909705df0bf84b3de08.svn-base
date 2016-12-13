package com.duanrong.business.user.dao;


import java.util.Map;

import base.dao.BaseDao;

import com.duanrong.business.user.model.RedPacket;
import com.duanrong.business.user.model.Rule;

/**
 * @author xiao
 * @date 2015年1月22日 上午11:18:08
 */
public interface RedPacketDao extends BaseDao<RedPacket> {

	
	/**
	 * 获取活动规则
	 * @param id
	 * @return
	 */
	public Rule getRuleById(int id);
	
	
	public int getRedPacketByRuleId(String mobile);
	
	public Map<String,String> getRedPacketByMobile(String mobile);
	public void delRedPacketByMobile(String mobile);
	public void updatePacketByMobile(Map<String,String> mobilemap);
}
