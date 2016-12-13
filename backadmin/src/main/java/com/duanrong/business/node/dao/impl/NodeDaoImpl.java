package com.duanrong.business.node.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.RedisCacheKey;
import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.node.dao.NodeDao;
import com.duanrong.business.node.model.CategoryTerm;
import com.duanrong.business.node.model.Node;
import com.duanrong.util.jedis.DRJedisCacheUtil;

@Repository
public class NodeDaoImpl extends BaseDaoImpl<Node> implements NodeDao {
	public NodeDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.node.mapper.NodeMapper");
	}

	@Override
	public PageInfo<CategoryTerm> sqlLiteForCategoryTerm(int pageNo,
			int pageSize, CategoryTerm categoryTerm) {		
		PageHelper.startPage(pageNo, pageSize);
		List<CategoryTerm> list = getSqlSession().selectList(
				getMapperNameSpace()+".sqlLiteForCategoryTerm", categoryTerm);		
		PageInfo<CategoryTerm> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public List<CategoryTerm> getListCategoryTerm() {		
		return getSqlSession().selectList(
				getMapperNameSpace()+".getListCategoryTerm");
	}

	@Override
	public String getBodyById(String bodyId) {
		return getSqlSession().selectOne(
				getMapperNameSpace()+".getBodyById", bodyId);
	}

	@Override
	public List<String> getTermNameByNodeId(String nodeId) {
		
		return getSqlSession().selectList(
				getMapperNameSpace()+".getTermName", nodeId);
	}

	@Override
	public int updateBody(Map<String, Object> map) {
		DRJedisCacheUtil.hdel(RedisCacheKey.ARTICLE, map.get("nodeId").toString());
		return getSqlSession().delete(
				getMapperNameSpace()+".updateBody", map);
	}

	@Override
	public int insertBody(Map<String, Object> map) {
		DRJedisCacheUtil.hdel(RedisCacheKey.ARTICLE, map.get("nodeId").toString());
		return getSqlSession().insert(
				getMapperNameSpace()+".insertBody", map);
	}
	
	@Override
	public void update(Node node) {
		DRJedisCacheUtil.hdel(RedisCacheKey.ARTICLE, node.getId());
		super.update(node);
	}

	@Override
	public void insert(Node node) {
		DRJedisCacheUtil.hdel(RedisCacheKey.ARTICLE, node.getId());
		super.insert(node);
	}

	@Override
	public int insertCategoryTerm(CategoryTerm categoryTerm) {
		
		return getSqlSession().insert(
				getMapperNameSpace()+".insertCategoryTerm", categoryTerm);
	}

	@Override
	public int updateCategoryTerm(CategoryTerm categoryTerm) {
		if(categoryTerm != null){
			switch (categoryTerm.getId()){
			case "wangzhangonggao":
				DRJedisCacheUtil.del(RedisCacheKey.NOTICES_WEB);
				break;
			case "mediapublicity":
				DRJedisCacheUtil.del(RedisCacheKey.NOTICES_REDIA);
				break;
			case "projectNotice":
				DRJedisCacheUtil.del(RedisCacheKey.NOTICES_LOAN);
				break;
			default :
			}			
		}
		return getSqlSession().update(
				getMapperNameSpace()+".updateCategoryTerm", categoryTerm);
	}

	@Override
	public CategoryTerm getCategoryTermById(String id) {
		
		return getSqlSession().selectOne(
				getMapperNameSpace()+".getCategoryTermById", id);
	}

	@Override
	   
	public List<CategoryTerm> getCategoryTermByName(String name) {		
		return getSqlSession().selectList(
				getMapperNameSpace()+".getCategoryTermByName", name);
	}

	@Override
	public int insertNodeTerm(Map<String, Object> map) {	
		switch (map.get("termId").toString()){
		case "wangzhangonggao":
			DRJedisCacheUtil.del(RedisCacheKey.NOTICES_WEB);
			break;
		case "mediapublicity":
			DRJedisCacheUtil.del(RedisCacheKey.NOTICES_REDIA);
			break;
		case "projectNotice":
			DRJedisCacheUtil.del(RedisCacheKey.NOTICES_LOAN);
			break;
		default :
		}
		return getSqlSession().insert(
				getMapperNameSpace()+".insertNodeTerm", map);
	}

	@Override
	public int deleteNodeTerm(Node node) {	
		if(node.getTerm() == null || node.equals("")){
			DRJedisCacheUtil.del(RedisCacheKey.NOTICES_WEB);
			DRJedisCacheUtil.del(RedisCacheKey.NOTICES_REDIA);
			DRJedisCacheUtil.del(RedisCacheKey.NOTICES_LOAN);
		}
		return getSqlSession().delete(
				getMapperNameSpace()+".deleteNodeTerm", node.getId());
	}

	@Override
	public List<String> getCategoryTermByIdAndName(CategoryTerm categoryTerm) {
		
		return getSqlSession().selectList(
				getMapperNameSpace()+".getCategoryTermByIdAndName", categoryTerm);
	}

	@Override
	public List<CategoryTerm> getCategorysByNodeId(String id) {
		return getSqlSession().selectList(
				getMapperNameSpace()+".getCategorysByNodeId",id);
	}

	@Override
	public int getSortNum() {
		return getSqlSession().selectOne("getSortNum");
	}

	@Override
	public int updateEditSortNumById(String id, String sortNum) {
		List<CategoryTerm> terms = this.getCategorysByNodeId(id);
		if(!terms.isEmpty()){
			for(CategoryTerm term : terms ){
				switch (term.getId()){
				case "wangzhangonggao":
					DRJedisCacheUtil.del(RedisCacheKey.NOTICES_WEB);
					break;
				case "mediapublicity":
					DRJedisCacheUtil.del(RedisCacheKey.NOTICES_REDIA);
					break;
				case "projectNotice":
					DRJedisCacheUtil.del(RedisCacheKey.NOTICES_LOAN);
					break;
				default :
				}	
			}
			
			Map<String, String> params=new HashMap<String, String>();
			params.put("id", id);
			params.put("sortNum",sortNum);
			return getSqlSession().update("updateEditSortNumById", params);
		}
		return 0;	
	}

	@Override
	public List<CategoryTerm> getCategoryTerm() {
		return getSqlSession().selectList("getCategoryTerm");
	}

}
