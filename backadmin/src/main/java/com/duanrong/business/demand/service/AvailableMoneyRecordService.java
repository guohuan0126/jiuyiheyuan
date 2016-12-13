package com.duanrong.business.demand.service;

import javax.servlet.http.HttpServletRequest;

import base.pagehelper.PageInfo;

import com.duanrong.business.demand.model.AvailableMoneyRecord;


/**
 * @Description: 短信处理
 * @Author: 林志明
 * @CreateDate: Nov 24, 2014
 */
public interface AvailableMoneyRecordService {
	
	public PageInfo<AvailableMoneyRecord> readPageInfo(int pageNo, int pageSize);
	
	public void insert(AvailableMoneyRecord availableMoneyRecord);
	
	public void del(AvailableMoneyRecord availableMoneyRecord);
	
	public AvailableMoneyRecord readRecord();
	
	public AvailableMoneyRecord read(String id);
	
	public void update(AvailableMoneyRecord availableMoneyRecord);
	
	/**
	 * 添加活期宝产品
	 * @param availableMoneyRecord
	 * @return
	 */
	public boolean saveRecord(AvailableMoneyRecord availableMoneyRecord,HttpServletRequest request);
	/**
	 * 获取到活期宝可用资产
	 * @return
	 */
	public double readUserUsedMoney();
	/**
	 * 更新活期宝可用资产
	 * @param money
	 */
	public void updateDemandTreasure(double money);


	public double readAvailableMoneyRecord(String date);
}