package com.duanrong.business.demand.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.model.DateRate;

/**
 * @Description: 每日利率
 * @Author: wangjing
 * @CreateDate: Nov 24, 2014
 */
public interface DateRateDao extends BaseDao<DateRate> {
	PageInfo<DateRate> readPageInfo(int pageNo, int pageSize,Map map);
			
	/**
	 * 获取计息利息
	 * @return
	 */
	public double readRate(Date date);
	public List<DateRate> readRateList(Date date);
	
}
