package com.duanrong.dataAnalysis.business.currnetData.dao;

import java.util.List;

import com.duanrong.dataAnalysis.business.currnetData.model.CurrnetData;
import com.duanrong.dataAnalysis.business.currnetData.model.ResCurrnetData;

public interface CurrnetDataDao {

	CurrnetData getCurrnetDataT();

	List<ResCurrnetData> getInData(String beginTime, String endTime);

	List<ResCurrnetData> getOutData(String beginTime, String endTime);

}
