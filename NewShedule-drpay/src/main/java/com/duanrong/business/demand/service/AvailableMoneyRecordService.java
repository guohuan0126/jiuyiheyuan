package com.duanrong.business.demand.service;


import com.duanrong.business.demand.model.AvailableMoneyRecord;


/**
 * @Description: 短信处理
 * @Author: 林志明
 * @CreateDate: Nov 24, 2014
 */
public interface AvailableMoneyRecordService {
		
	public void update(AvailableMoneyRecord availableMoneyRecord);
	
	public AvailableMoneyRecord getEndLine();
	
}