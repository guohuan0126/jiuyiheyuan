package com.duanrong.business.cache.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.business.cache.dao.CacheDao;
import com.duanrong.business.cache.model.CacheKey;
import com.duanrong.business.cache.service.CacheService;

import base.pagehelper.PageInfo;

@Service
public class CacheServiceImpl implements CacheService{

	@Resource
	CacheDao cacheDao;
	
	@Override
	public PageInfo<CacheKey> pageInfo(int pageNo, int pageSize,
			CacheKey cacheKey) {	
		return cacheDao.pageInfo(pageNo, pageSize, cacheKey);
	}

	@Override
	public void insert(CacheKey cacheKey) {
		cacheDao.insert(cacheKey);
	}

	@Override
	public void update(CacheKey cacheKey) {
		cacheDao.update(cacheKey);
		
	}

	@Override
	public void delete(String id) {
		cacheDao.delete(id);
		
	}

	@Override
	public CacheKey get(String id) {
	
		return cacheDao.get(id);
	}

}
