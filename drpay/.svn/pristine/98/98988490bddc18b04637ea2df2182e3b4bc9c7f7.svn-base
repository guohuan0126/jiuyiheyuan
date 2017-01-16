package com.duanrong.drpay.business.demand.service;

import java.util.List;

import com.duanrong.drpay.business.demand.model.Demandtreasure;


/**
 * @Description: 短信处理
 * @Author: 林志明
 * @CreateDate: Nov 24, 2014
 */
public interface DemandtreasureService {
	
	public List<Demandtreasure> queryAll();
	public void insert(Demandtreasure demandtreasure);
	public void update(Demandtreasure demandtreasure);
		
	/**
	 * 获取活期宝账户信息
	 * @param userId
	 * @return
	 */
	public double[] getTreasuerAccount(String userId);
	
	/**
	 * 活期宝产品信息
	 * @return
	 */
	public Demandtreasure queryDemandtreasure();
	
}