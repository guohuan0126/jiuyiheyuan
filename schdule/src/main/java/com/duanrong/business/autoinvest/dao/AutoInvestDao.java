package com.duanrong.business.autoinvest.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;

import com.duanrong.business.autoinvest.model.AutoInvest;

/**
 * @Description: 自动投标
 * @Author: 林志明
 * @CreateDate: Nov 27, 2014
 */
public interface AutoInvestDao extends BaseDao<AutoInvest> {
	/**
	 * 获取自动投标用户排队号
	 * 
	 * @param userId
	 * @return
	 */
	public Long getQueueNumber(String userId);

	/**
	 * 查询开启自动投标的总数量
	 */
	public Long getAutoInvestConut();
	
	
	/**
	 * 查询符合此次投标的自动投标列表
	 */
	public List<AutoInvest> getConsistAutoInvest(Map<String, Object> map);

}