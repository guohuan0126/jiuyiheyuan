package com.duanrong.business.demand.dao;

import base.dao.BaseDao;

import com.duanrong.business.demand.model.AvailableMoneyRecord;

/**
 * @Description: 短信
 * @Author: 林志明
 * @CreateDate: Nov 24, 2014
 */
public interface AvailableMoneyRecordDao extends BaseDao<AvailableMoneyRecord> {
	
	public AvailableMoneyRecord getEndLine();
}
