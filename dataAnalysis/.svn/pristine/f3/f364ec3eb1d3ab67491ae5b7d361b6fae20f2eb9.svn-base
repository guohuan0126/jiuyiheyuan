package com.duanrong.dataAnalysis.business.cashData.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.dataAnalysis.business.cashData.dao.cashDataDao;
import com.duanrong.dataAnalysis.business.cashData.model.ResCashData;
import com.duanrong.dataAnalysis.business.cashData.model.cashData;
import com.duanrong.dataAnalysis.business.cashData.model.payAndCashData;
import com.duanrong.dataAnalysis.business.cashData.service.cashDataService;
import com.duanrong.dataAnalysis.business.user.model.ResData;
@Service
public class cashDataServiceImpl implements cashDataService{

	@Resource
	private cashDataDao cashDataDao;
	@Override
	public cashData getCashDataT() {
		return cashDataDao.getCashDataT();
	}
	@Override
	public List<ResCashData> getPayData(String beginTime, String endTime) {
		
		List<ResCashData> datasList=cashDataDao.getPayData(beginTime,endTime);
		for(ResCashData dl: datasList){
			dl.setTimer(dl.getTimer().split(" ")[0]);
			dl.setType("充值金额");
		}
		return datasList;
	}
	@Override
	public List<ResCashData> getDrawData(String beginTime, String endTime) {
		List<ResCashData> datasList=cashDataDao.getDrawData(beginTime,endTime);
		for(ResCashData dl: datasList){
			dl.setTimer(dl.getTimer().split(" ")[0]);
			dl.setType("提现金额");
		}
		return datasList;
	}
	@Override
	public payAndCashData getDataByMail() {
		
		return cashDataDao.getDataByMail();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*//最近一周充值金额
	public List<ResCashData> getPayDataByW() {
		Calendar   c   =   Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH,-7);//关键是这个7天前....
		SimpleDateFormat   sdf   =   new   SimpleDateFormat( "yyyy-MM-dd ");
		String beginTime=sdf.format(c.getTime());
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd ");
		String endTime=format.format(date);
		
		List<ResCashData> datasList=cashDataDao.getPayData(beginTime,endTime);
		for(ResCashData dl: datasList){
			dl.setTimer(dl.getTimer().split(" ")[0]);
			dl.setType("充值金额");
		}
		return datasList;
	}
	//最近一周提现金额
	@Override
	public List<ResCashData> getDrawDataByW() {
		Calendar   c   =   Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH,-7);//关键是这个7天前....
		SimpleDateFormat   sdf   =   new   SimpleDateFormat( "yyyy-MM-dd ");
		String beginTime=sdf.format(c.getTime());
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd ");
		String endTime=format.format(date);
		
		List<ResCashData> datasList=cashDataDao.getDrawData(beginTime,endTime);
		for(ResCashData dl: datasList){
			dl.setTimer(dl.getTimer().split(" ")[0]);
			dl.setType("提现金额");
		}
		return datasList;
	}
	//最近一月充值金额
	@Override
	public List<ResCashData> getPayDataByM() {
		Calendar   c   =   Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH,-30);//关键是这个7天前....
		SimpleDateFormat   sdf   =   new   SimpleDateFormat( "yyyy-MM-dd ");
		String beginTime=sdf.format(c.getTime());
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd ");
		String endTime=format.format(date);
		
		List<ResCashData> datasList=cashDataDao.getPayData(beginTime,endTime);
		for(ResCashData dl: datasList){
			dl.setTimer(dl.getTimer().split(" ")[0]);
			dl.setType("充值金额");
		}
		return datasList;
	}
	//最近一月提现金额
	@Override
	public List<ResCashData> getDrawDataByM() {
		Calendar   c   =   Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH,-30);//关键是这个7天前....
		SimpleDateFormat   sdf   =   new   SimpleDateFormat( "yyyy-MM-dd ");
		String beginTime=sdf.format(c.getTime());
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd ");
		String endTime=format.format(date);
		
		List<ResCashData> datasList=cashDataDao.getDrawData(beginTime,endTime);
		for(ResCashData dl: datasList){
			dl.setTimer(dl.getTimer().split(" ")[0]);
			dl.setType("提现金额");
		}
		return datasList;
	}
	*/

	

	
}
