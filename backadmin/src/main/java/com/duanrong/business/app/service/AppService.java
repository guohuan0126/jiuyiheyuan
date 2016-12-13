package com.duanrong.business.app.service;

import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.business.app.model.APP;
import com.duanrong.business.app.model.AppFeedback;
import com.duanrong.business.sms.model.Sms;

/**
 * APP相关服务
 * 
 * @author Lin Zhiming
 * @version Jan 14, 2015 1:56:37 PM
 */
public interface AppService {
	/**
	 * 保存反馈信息
	 * 
	 * @param appFeedback
	 */
	public void saveAppFeedback(AppFeedback appFeedback);

	/**
	 * 根据OS的名称查询最新版本号
	 * 
	 * @param osName
	 * @return
	 */
	//public APP getApp(String osName);

	public void saveApp(APP app);

	public void updateApp(APP app);
	public PageInfo<APP> readPageInfo(int pageNo, int pageSize, Map map);
	public APP readAppById(String id);

}
