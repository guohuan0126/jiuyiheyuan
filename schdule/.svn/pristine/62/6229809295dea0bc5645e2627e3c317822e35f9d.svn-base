package com.duanrong.business.user.dao;


import java.util.List;

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
	
	/**
	 * 更新过期红包
	 */
	public void updateExpired();
	
	public void updateDetailMobileNumber(String oldNumber, String newNumber);
	
	public void updateShareMobileNumber(String oldNumber, String newNumber);


	public void batchInsert(List<RedPacket> list);
	
}
