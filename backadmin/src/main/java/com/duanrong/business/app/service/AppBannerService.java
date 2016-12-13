package com.duanrong.business.app.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.duanrong.business.app.model.AppBanner;
import com.duanrong.business.app.model.AppFeedback;

import base.pagehelper.PageInfo;

/**
 * 手机APPBannerService
 * @author Qiu Feihu
 * @version 2015年6月5日10:57:44
 */
public interface AppBannerService {
   
	public AppBanner read(String id);
	
	public void delete(String id);
	
	public void add(AppBanner appBanner);
	
	public void update(AppBanner appBanner);
	
	public PageInfo<AppBanner> readPageLite(int pageNo, int pageSize, AppBanner appBanner);
	
	public String uploadFile(CommonsMultipartFile[] files,HttpServletRequest request);
	
}
