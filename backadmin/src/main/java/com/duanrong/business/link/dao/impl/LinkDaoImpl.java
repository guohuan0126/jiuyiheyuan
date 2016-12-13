package com.duanrong.business.link.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import util.RedisCacheKey;
import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.link.dao.LinkDao;
import com.duanrong.business.link.model.Link;
import com.duanrong.util.jedis.DRJedisCacheUtil;

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
		switch(link.getType()){		
		case "pcbanner":
			DRJedisCacheUtil.del(RedisCacheKey.BANNER_PC);
			break;
		case "home":
			DRJedisCacheUtil.hdel(RedisCacheKey.NOTICES_REDIA, "0");
			break;
		case "friendly":
			DRJedisCacheUtil.del(RedisCacheKey.FRIENDLIES_PC);
			break;
		case "footerLinks":
			DRJedisCacheUtil.del(RedisCacheKey.FOOTER_LINK_PC);
			break;
		default:

		}		
		getSqlSession().insert(
				getMapperNameSpace() + ".insertLink", link);
	}
	
	@Override
	public void update(Link link){
		switch(link.getType()){		
		case "pcbanner":
			DRJedisCacheUtil.del(RedisCacheKey.BANNER_PC);
			break;
		case "home":
			DRJedisCacheUtil.hdel(RedisCacheKey.NOTICES_REDIA, "0");
			break;
		case "friendly":
			DRJedisCacheUtil.del(RedisCacheKey.FRIENDLIES_PC);
			break;
		case "footerLinks":
			DRJedisCacheUtil.del(RedisCacheKey.FOOTER_LINK_PC);
			break;
		default:

		}		
		getSqlSession().update(
				getMapperNameSpace() + ".updateLink", link);
	}

}
