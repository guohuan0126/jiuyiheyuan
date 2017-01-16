package com.duanrong.business.app.dao;

import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.app.model.APP;

/**
 * @Description: 手机APP相关
 * @Author: 林志明
 * @CreateDate: Dec 31, 2014
 */
public interface APPDao extends BaseDao<APP> {

	PageInfo<APP> findPageInfo(int pageNo, int pageSize, Map map);
}