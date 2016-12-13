package com.duanrong.business.link.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.link.dao.LinkDao;
import com.duanrong.business.link.model.Link;

@Repository
public class LinkDaoImpl extends BaseDaoImpl<Link> implements LinkDao {
	public LinkDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.link.mapper.LinkMapper");
	}

	@Override
	public List<Link> getLinksByType(String type) {
		return getSqlSession().selectList(
				getMapperNameSpace() + ".getLinksByType", type);
	}

	@Override
	public Link get(String id){
		return getSqlSession().selectOne(
				getMapperNameSpace() + ".getLink", id);
	}
	
	@Override
	public void insert(Link link){
		getSqlSession().insert(
				getMapperNameSpace() + ".insertLink", link);
	}
	
	@Override
	public void update(Link link){
		getSqlSession().insert(
				getMapperNameSpace() + ".updateLink", link);
	}

}
