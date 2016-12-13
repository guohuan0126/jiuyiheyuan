package com.duanrong.business.demand.dao;

import base.dao.BaseDao;

import com.duanrong.business.demand.model.Demandtreasure;

/**
 * @Description: 短信
 * @Author: 林志明
 * @CreateDate: Nov 24, 2014
 */
public interface DemandtreasureDao extends BaseDao<Demandtreasure> {
	/**
	 * 查询某阶段下活期宝发送的利息
	 * @param start
	 * @param end
	 * @return
	 */
	double readDemandInterest(String start, String end);
	
}
