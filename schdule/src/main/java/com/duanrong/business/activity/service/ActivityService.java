package com.duanrong.business.activity.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import base.pagehelper.PageInfo;

import com.duanrong.business.activity.model.Activity;
import com.duanrong.business.app.model.AppFeedback;

/**
 * 活动和专题Service
 * @author Qiu Feihu
 * @Time 2015年6月8日12:04:13
 *
 */
public interface ActivityService {
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public Activity get(Integer id);
    
	/**
	 * 添加
	 * @param activity
	 */
	public void add(Activity activity);
	
	/**
	 * 更新
	 * @param activity
	 */
	public void update(Activity activity);
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(Integer id);
	
	/**
	 * 分页
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param loan
	 * @return
	 */
	public PageInfo<Activity> pageLite(int pageNo, int pageSize, Activity activity);
	
	/**
	 * 上传
	 * @param files
	 * @param request
	 * @return
	 */
	public String uploadFile(CommonsMultipartFile[] files,HttpServletRequest request);
}
