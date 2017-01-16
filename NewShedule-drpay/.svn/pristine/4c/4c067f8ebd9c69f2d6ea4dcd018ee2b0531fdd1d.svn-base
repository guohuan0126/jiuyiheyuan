package com.duanrong.business.token.dao;

import java.util.Map;

import base.dao.BaseDao;

import com.duanrong.business.token.model.AccessToken;

/**
 * @Description: TOKEN数据服务 
 * @Author:	林志明
 * @CreateDate:	Nov 7, 2014
 */
public interface AccessTokenDao extends BaseDao<AccessToken> {

	/**
	 * 获取本日生成TOKEN的数量
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public Long todayCount(String userId);

	public AccessToken validateToken(Map<String, Object> params);

	public void saveTokenDetail(Map<String, Object> params);
}
