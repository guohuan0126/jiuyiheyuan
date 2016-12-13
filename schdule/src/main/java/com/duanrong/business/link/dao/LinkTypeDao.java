package com.duanrong.business.link.dao;

import java.util.List;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.link.model.LinkType;

public interface LinkTypeDao extends BaseDao<LinkType>{
	
	
	/**
	 * 友情链接类型分页
	 */
	public PageInfo<LinkType> pageLiteForType(int pageNo, int pageSize, LinkType entity);
	
	/**
	 * 友情链接列表
	 */
	public List<LinkType> getTypeList();
}
