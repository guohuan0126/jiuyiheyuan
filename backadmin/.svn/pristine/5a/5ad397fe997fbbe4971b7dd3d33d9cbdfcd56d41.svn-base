package com.duanrong.business.app.service;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import base.pagehelper.PageInfo;

import com.duanrong.business.app.model.AppBanner;
import com.duanrong.business.app.model.AppStartDiagram;
/**
 * 手机AppStartDiagramService
 * @author zhang yingwei
 * @version 2016年6月27日10:57:44
 */
public interface AppStartDiagramService {
	
	
    public void add(AppStartDiagram appStartDiagram);
    
    //根据id删除app启动图
    public void delete(String id);
    //根据id查询app启动图
    public AppStartDiagram get(String id);
	public void update(AppStartDiagram appStartDiagram);
	
	//翻页查询启动图列表
	public PageInfo<AppStartDiagram> pageLite(int pageNo, int pageSize,
			AppStartDiagram appStartDiagram);
	
	//上传app八张启动图
	public String uploadFile(CommonsMultipartFile[] files,HttpServletRequest request);
	
	//获取图片限制大小
	public String getimgsize();
}
