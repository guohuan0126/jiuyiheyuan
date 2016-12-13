package com.duanrong.business.cache.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.duanrong.business.cache.dao.CacheDao;
import com.duanrong.business.cache.model.CacheKey;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;


@Repository
public class CacheDaoImpl extends BaseDaoImpl<CacheKey> implements CacheDao{
	
	public CacheDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.cache.mapper.CacheMapper");
	}

	@Override
	public PageInfo<CacheKey> pageInfo(int pageNo, int pageSize,
			CacheKey cacheKey) {
		PageHelper.startPage(pageNo, pageSize);
		List<CacheKey> list = getSqlSession().selectList(
				this.getMapperNameSpace() + ".pageLite", cacheKey);
		PageInfo<CacheKey> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
}
