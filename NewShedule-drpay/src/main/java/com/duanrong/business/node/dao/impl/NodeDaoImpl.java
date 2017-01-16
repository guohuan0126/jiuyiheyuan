package com.duanrong.business.node.dao.impl;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.node.dao.NodeDao;
import com.duanrong.business.node.model.CategoryTerm;
import com.duanrong.business.node.model.Node;

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
		
		return getSqlSession().delete(
				getMapperNameSpace()+".updateBody", map);
	}

	@Override
	public int insertBody(Map<String, Object> map) {
		
		return getSqlSession().insert(
				getMapperNameSpace()+".insertBody", map);
	}

	@Override
	public int insertCategoryTerm(CategoryTerm categoryTerm) {
		
		return getSqlSession().insert(
				getMapperNameSpace()+".insertCategoryTerm", categoryTerm);
	}

	@Override
	public int updateCategoryTerm(CategoryTerm categoryTerm) {
		
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
		return getSqlSession().insert(
				getMapperNameSpace()+".insertNodeTerm", map);
	}

	@Override
	public int deleteNodeTerm(String nodeId) {		
		return getSqlSession().delete(
				getMapperNameSpace()+".deleteNodeTerm", nodeId);
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
	
}
