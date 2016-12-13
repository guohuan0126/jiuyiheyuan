package com.duanrong.business.demand.dao;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.model.AvailableMoneyRecord;

/**
 * @Description: 短信
 * @Author: 林志明
 * @CreateDate: Nov 24, 2014
 */
public interface AvailableMoneyRecordDao extends BaseDao<AvailableMoneyRecord> {
	PageInfo<AvailableMoneyRecord> readPageInfo(int pageNo, int pageSize);
	void del(AvailableMoneyRecord availableMoneyRecord);
	public AvailableMoneyRecord readRecord();
	/**
	 * 获取活期宝可用资产
	 * @return
	 */
	double readUserUsedMoney();
	/**
	 * 更新活期宝可用资产
	 * @param money
	 */
	void updateDemandTreasure(double money);
	double readAvailableMoneyRecord(String date);
}
