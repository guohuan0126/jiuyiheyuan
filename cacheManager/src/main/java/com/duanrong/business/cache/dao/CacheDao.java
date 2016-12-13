package com.duanrong.business.cache.dao;

import com.duanrong.business.cache.model.CacheKey;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;


public interface CacheDao extends BaseDao<CacheKey> {

	/**
	 * 缓存key列表
	 * @param PageNo
	 * @param pageSize
	 * @param sysCacheKey
	 * @return
	 */
	PageInfo<CacheKey> pageInfo(int pageNo, int pageSize, CacheKey cacheKey);
	
}
