package com.duanrong.business.node.service;

import java.util.List;

import com.duanrong.business.node.model.CategoryTerm;
import com.duanrong.business.node.model.Node;

import base.pagehelper.PageInfo;


/**
 * @Description: 文章列表
 * @Author: 林志明
 * @CreateDate: Nov 18, 2014
 */
public interface NodeService {
	
	/**
	 * 术语分页
	 * @param pageNo
	 * @param pageSize
	 * @param categoryTerm
	 * @return
	 */
	public PageInfo<CategoryTerm> getPageInfoForCategoryTerm(
			int pageNo, int pageSize, CategoryTerm categoryTerm);
	
	/**
	 * 术语列表
	 * @return
	 */
	public List<CategoryTerm> getListForCategoryTerm();
	
	/**
	 * 文章分页
	 * @param pageNo
	 * @param pageSize
	 * @param node
	 * @return
	 */
	public PageInfo<Node> getPageInfoForNode(
			int pageNo, int pageSize, Node node);
	
	
	/**
	 * 查询单篇文章
	 * @param id
	 * @return
	 */
	public Node get(String id);
	
	/**
	 * 查询文章术语
	 * @return
	 */
	public List<String> getTermNameByNodeId(String nodeId);
	
	/**
	 * 更新文章
	 * @param id
	 * @return
	 */
	public String update(Node node);
	
	/**
	 * 创建文章
	 * @param node
	 */
	public String insert(Node node);
	
	/**
	 * 添加术语
	 * @param categoryTerm
	 * @return
	 */
	public String insertCategoryTerm(CategoryTerm categoryTerm);
	
	/**
	 * 更新术语
	 * @param categoryTerm
	 * @return
	 */
	public String updateCategoryTerm(CategoryTerm categoryTerm);
	
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
	public CategoryTerm getCategoryTermByName(String name);
}
