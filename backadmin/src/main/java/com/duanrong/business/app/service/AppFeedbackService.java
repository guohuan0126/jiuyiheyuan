package com.duanrong.business.app.service;

import java.util.List;

import base.pagehelper.PageInfo;

import com.duanrong.business.app.model.AppFeedback;

/**
 * 手机APP反馈问题Service接口
 * @author Qiu Feihu
 * @version 2015年6月4日16:41:50
 */
public interface AppFeedbackService {
    

	
	/**
	 * 分页
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param loan
	 * @return
	 */
	public PageInfo<AppFeedback> readPageLite(int pageNo, int pageSize, AppFeedback app);
	
	/**
	 * 普通条件查询
	 * @param app
	 * @return
	 */
	public List<AppFeedback> read(AppFeedback app);
	/**
	 * 更新处理状态
	 * @param id
	 * @param handleType
	 */
	public void updateChangeHandleType(String id, String handleType);
}