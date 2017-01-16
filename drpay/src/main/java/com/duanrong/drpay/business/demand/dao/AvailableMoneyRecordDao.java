package com.duanrong.drpay.business.demand.dao;

import com.duanrong.drpay.business.demand.model.AvailableMoneyRecord;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

/**
 * @Description: 短信
 * @Author: 林志明
 * @CreateDate: Nov 24, 2014
 */
public interface AvailableMoneyRecordDao extends BaseDao<AvailableMoneyRecord> {
	PageInfo<AvailableMoneyRecord> pageInfo(int pageNo, int pageSize);
	void del(AvailableMoneyRecord availableMoneyRecord);
	AvailableMoneyRecord getEndLine();
}
