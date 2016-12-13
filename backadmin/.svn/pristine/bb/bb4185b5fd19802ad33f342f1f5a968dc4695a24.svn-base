package com.duanrong.business.user.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.user.model.Information;

/**
 * @Description: 站内信DAO 
 * @Author:	林志明
 * @CreateDate:	Sep 4, 2014
 */
public interface InformationDao extends BaseDao<Information> {
	/**
	 * 获得未读站内信的数量
	 * @param userId
	 * @return
	 */
	public Long getNotReadByUserId(String userId);
	
	/**
	 * 设置站内信已读
	 * 
	 * @param userId
	 */
	public void updateRead(String userId);

	/**
	 * 根据用户ID获得用户所有站内信
	 * @param userId
	 * @return
	 */
	public List<Information> getInformationByUserId(String userId);
	
	PageInfo<Information> pageInfo(int pageNo, int pageSize, Map map);
	
	
}
