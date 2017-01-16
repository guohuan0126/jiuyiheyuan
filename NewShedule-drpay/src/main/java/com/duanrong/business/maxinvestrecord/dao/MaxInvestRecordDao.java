package com.duanrong.business.maxinvestrecord.dao;

import java.util.List;


import base.dao.BaseDao;

import com.duanrong.business.maxinvestrecord.model.MaxInvestRecord;

public interface MaxInvestRecordDao extends BaseDao<MaxInvestRecord>{
	//分次查询表数据
	public List<MaxInvestRecord> getRecord(int times);
	
	//根据用户id查询再投峰值记录
	public MaxInvestRecord getInvestRecord(String userId);
	
	//查询表的总条数
	public int getRecordCount();
}
