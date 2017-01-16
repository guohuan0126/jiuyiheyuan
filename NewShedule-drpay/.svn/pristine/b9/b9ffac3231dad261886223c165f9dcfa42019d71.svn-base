package com.duanrong.business.node.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.node.model.CategoryTerm;
import com.duanrong.business.node.model.Node;

/**
 * @Description: 文章列表
 * @Author: 林志明
 * @CreateDate: Nov 18, 2014
 */
public interface NodeDao extends BaseDao<Node> {
	
	/**
	 * 术语分页
	 * @param categoryTerm
	 * @return
	 */
	public PageInfo<CategoryTerm> sqlLiteForCategoryTerm(int pageNo, int pageSize, CategoryTerm categoryTerm);
	
	/**
	 * 术语列表
	 * @return
	 */
	public List<CategoryTerm> getListCategoryTerm();
	
	/**
	 * 添加术语
	 * @param categoryTerm
	 * @return
	 */
	public int insertCategoryTerm(CategoryTerm categoryTerm);
	
	/**
	 * 更新术语
	 * @param categoryTerm
	 * @return
	 */
	public int updateCategoryTerm(CategoryTerm categoryTerm);
	
	/**
	 * 根据id查询术语
	 * @param id
	 * @return
	 */
	public CategoryTerm getCategoryTermById(String id);
	
	/**
	 * 根据name查询术语
	 * @param name
	 * @return
	 */
	public List<CategoryTerm> getCategoryTermByName(String name);
	
	/**
	 * 获取文章内容
	 * @param bodyId
	 * @return
	 */
	public String getBodyById(String bodyId);
	
	/**
	 * 获取文章术语
	 * @param bodyId
	 * @return
	 */
	public List<String> getTermNameByNodeId(String nodeId);
	
	/**
	 * 更新文章体
	 * @param bodyId
	 * @return
	 */
	public int updateBody(Map<String, Object> map);
	
	/**
	 * 添加文章体
	 * @param map
	 * @return
	 */
	public int insertBody(Map<String, Object> map);
	
	/**
	 * 添加文章术语
	 * @param map
	 * @return
	 */
	public int insertNodeTerm(Map<String, Object> map);
	
	/**
	 * 删除文章术语
	 * @param nodeId
	 * @return
	 */
	public int deleteNodeTerm(String nodeId);
	
	/**
	 * 查询术语
	 * @param categoryTerm
	 * @return
	 */
	public List<String> getCategoryTermByIdAndName(CategoryTerm categoryTerm);
	
	public List<CategoryTerm> getCategorysByNodeId(String id); 
}
