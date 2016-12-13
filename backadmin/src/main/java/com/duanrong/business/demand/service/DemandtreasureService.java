package com.duanrong.business.demand.service;

import java.util.Date;
import java.util.List;

import com.duanrong.business.demand.model.Demandtreasure;


/**
 * @Description: 短信处理
 * @Author: 林志明
 * @CreateDate: Nov 24, 2014
 */
public interface DemandtreasureService {
	public List<Demandtreasure> readAll();
	public void insert(Demandtreasure demandtreasure);
	public void update(Demandtreasure demandtreasure);
	
	/**
	 * 手动派息
	 * @param date
	 * @return
	 */
	public int insertDayDate(Date now);
	/**
	 * 查询某阶段下活期宝发送的利息
	 * @param start
	 * @param end
	 * @return
	 */
	public double readDemandInterest(String start, String end);
	
}