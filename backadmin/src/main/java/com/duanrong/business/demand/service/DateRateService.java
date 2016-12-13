package com.duanrong.business.demand.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.business.demand.model.DateRate;


/**
 * @Description: 每日利率
 * @Author: wangjing
 * @CreateDate: Nov 24, 2014
 */
public interface DateRateService {
	public PageInfo<DateRate> readPageInfo(int pageNo, int pageSize,Map map);
	public void addRate(String year,String month,double rate);
	public DateRate read(String id);
	public void update(DateRate dateRate);
	
	/**
	 * 获取计息利息
	 * @return
	 */
	public double readRate(Date date);
	public List<DateRate> readRateList(Date date);
	
}