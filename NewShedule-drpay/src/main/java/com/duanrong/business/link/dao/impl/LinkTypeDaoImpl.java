package com.duanrong.business.link.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.link.dao.LinkTypeDao;
import com.duanrong.business.link.model.LinkType;


@Repository
public class LinkTypeDaoImpl extends BaseDaoImpl<LinkType> implements
		LinkTypeDao {
	
	public LinkTypeDaoImpl(){
		this.setMapperNameSpace("com.duanrong.business.link.mapper.LinkMapper");
	}

	@Override
	public PageInfo<LinkType> pageLiteForType(int pageNo, int pageSize,
			LinkType entity) {
		PageHelper.startPage(pageNo, pageSize);
		List<LinkType> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageLiteForType", entity);
		PageInfo<LinkType> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public List<LinkType> getTypeList() {	
		
		return getSqlSession().selectList(
				getMapperNameSpace()+".getTypeList");
	}

	
	
}
