package com.duanrong.business.link.dao;

import java.util.List;

import base.dao.BaseDao;

import com.duanrong.business.link.model.Link;

/**
 * @Description: 友情链接、媒体报道等链接
 * @Author: 林志明
 * @CreateDate: Dec 1, 2014
 */
public interface LinkDao extends BaseDao<Link> {
	/**
	 * 根据类型获得所有链接
	 * 
	 * @param type
	 *            类型
	 * @return
	 */
	public List<Link> getLinksByType(String type);
	
	public Link get(String id);
	
	public void insert(Link link);
	
	public void update(Link link);
}
