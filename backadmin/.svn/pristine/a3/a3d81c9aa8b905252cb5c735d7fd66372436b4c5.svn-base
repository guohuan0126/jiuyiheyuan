package com.duanrong.business.link.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import base.pagehelper.PageInfo;

import com.duanrong.business.link.model.Link;
import com.duanrong.business.link.model.LinkType;

/**
 * @Description: 友情链接、媒体报道等链接
 * @Author: 林志明
 * @CreateDate: Dec 1, 2014
 */
public interface LinkService {

	/**
	 * 根据类型获得所有链接
	 * 
	 * @param type
	 *            类型
	 * @return
	 */
	public List<Link> readLinksByType(String type);
	
	/**
	 * 友情链接分页
	 * 
	 * @param linkType
	 * @return
	 */
	public PageInfo<LinkType> readLikeTypePageLite(int pageNo, int pageSize,
			LinkType linkType);	
	
	
	/**
	 * 插入友情链接
	 * 
	 * @param linkType
	 * @return
	 */
	public void insert(LinkType linkType);	
	
	/**
	 * 查询友情链接类型
	 * 
	 * @param linkType
	 * @return
	 */
	public LinkType read(String id);	
	
	/**
	 * 友情链接类型列表
	 * 
	 * @param linkType
	 * @return
	 */
	public List<LinkType> readTypeList();	
	
	/**
	 * 编辑
	 * @param linkType
	 * @return
	 */
	public void update(LinkType linkType);	
	
	
	/**
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param link
	 * @return
	 */
	public PageInfo<Link> readPageLite(int pageNo, int pageSize,
			Link link);	
	
	
	/**
	 * 插入友情链接
	 * 
	 * @param linkType
	 * @return
	 */
	public String insertLink(Link link);	
	
	/**
	 * @param linkType
	 * @return
	 */
	public Link readLink(String id);	
	
	
	/**
	 * 编辑
	 * @param linkType
	 * @return
	 */
	public String updateLink(Link link);	
	/**
	 * 
	 * @description 上传图片
	 * @author wanjing
	 * @time 2015-3-11 下午6:20:12
	 * @param files
	 * @param request
	 */
	public String uploadArticleData(CommonsMultipartFile[] files,
			HttpServletRequest request);

}