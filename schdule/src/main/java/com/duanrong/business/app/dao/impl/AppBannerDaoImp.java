package com.duanrong.business.app.dao.impl;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.app.dao.AppBannerDao;
import com.duanrong.business.app.model.APP;
import com.duanrong.business.app.model.AppBanner;

@Repository
public class AppBannerDaoImp  extends BaseDaoImpl<AppBanner>  implements AppBannerDao {
   
	public AppBannerDaoImp(){
		this.setMapperNameSpace("com.duanrong.business.app.mapper.AppBanner");
	}
}
