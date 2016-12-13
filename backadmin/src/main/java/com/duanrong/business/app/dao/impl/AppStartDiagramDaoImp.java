package com.duanrong.business.app.dao.impl;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.app.dao.AppStartDiagramDao;
import com.duanrong.business.app.model.AppBanner;
import com.duanrong.business.app.model.AppStartDiagram;

@Repository
public class AppStartDiagramDaoImp  extends BaseDaoImpl<AppStartDiagram>  implements AppStartDiagramDao {
   
	public AppStartDiagramDaoImp(){
		this.setMapperNameSpace("com.duanrong.business.app.mapper.AppStartDiagram");
	}
	
	public String getimgsize(){
		
		
		return getSqlSession().selectOne(
				getMapperNameSpace()+".selectimgsize");
	}
	
}
