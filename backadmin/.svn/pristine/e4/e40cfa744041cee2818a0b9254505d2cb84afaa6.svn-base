package com.duanrong.business.app.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import util.RedisCacheKey;
import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.app.dao.AppBannerDao;
import com.duanrong.business.app.model.AppBanner;
import com.duanrong.util.jedis.DRJedisCacheUtil;

@Repository
public class AppBannerDaoImp  extends BaseDaoImpl<AppBanner>  implements AppBannerDao {
   
	public AppBannerDaoImp(){
		this.setMapperNameSpace("com.duanrong.business.app.mapper.AppBanner");
	}

	@Override
	public void insert(AppBanner entity) {
		DRJedisCacheUtil.del(RedisCacheKey.BANNER_M);
		super.insert(entity);
	}

	@Override
	public void update(AppBanner entity) {
		DRJedisCacheUtil.del(RedisCacheKey.BANNER_M);
		super.update(entity);
	}

	@Override
	public void delete(Serializable id) {
		DRJedisCacheUtil.del(RedisCacheKey.BANNER_M);
		super.delete(id);
	}
	
	
}
