package com.duanrong.dataAnalysis.business.currnetData.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.dataAnalysis.business.cashData.model.ResCashData;
import com.duanrong.dataAnalysis.business.currnetData.dao.CurrnetDataDao;
import com.duanrong.dataAnalysis.business.currnetData.model.CurrnetData;
import com.duanrong.dataAnalysis.business.currnetData.model.ResCurrnetData;
import com.duanrong.dataAnalysis.business.currnetData.service.CurrnetDataService;
@Service
public class CurrnetDataServiceImpl implements CurrnetDataService {

	@Resource
	private CurrnetDataDao currnetDataDao; 
	@Override
	public CurrnetData getCurrnetDataT() {
		// TODO Auto-generated method stub
		return currnetDataDao.getCurrnetDataT();
	}
	
	@Override
	public List<ResCurrnetData> getInData(String beginTime, String endTime) {
		
		List<ResCurrnetData> datasList=currnetDataDao.getInData(beginTime,endTime);
		for(ResCurrnetData dl: datasList){
			dl.setTimer(dl.getTimer().split(" ")[0]);
			dl.setType("转入金额");
		}
		return datasList;
	}
	@Override
	public List<ResCurrnetData> getOutData(String beginTime, String endTime) {
		List<ResCurrnetData> datasList=currnetDataDao.getOutData(beginTime,endTime);
		for(ResCurrnetData dl: datasList){
			dl.setTimer(dl.getTimer().split(" ")[0]);
			dl.setType("转出金额");
		}
		return datasList;
	}

	
}
