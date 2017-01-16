package com.duanrong.drpay.business.demand.service;

import com.duanrong.drpay.business.demand.model.AvailableMoneyRecord;

import base.pagehelper.PageInfo;


/**
 * @Description: 短信处理
 * @Author: 林志明
 * @CreateDate: Nov 24, 2014
 */
public interface AvailableMoneyRecordService {
	public PageInfo<AvailableMoneyRecord> findPageInfo(int pageNo, int pageSize);
	public void insert(AvailableMoneyRecord availableMoneyRecord);
	public void del(AvailableMoneyRecord availableMoneyRecord);
	public void update(AvailableMoneyRecord availableMoneyRecord);
	public AvailableMoneyRecord getEndLine();
}