package com.duanrong.dataAnalysis.business.user.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.dataAnalysis.business.user.dao.UserDataDao;
import com.duanrong.dataAnalysis.business.user.model.ResData;
import com.duanrong.dataAnalysis.business.user.model.UserData;
import com.duanrong.dataAnalysis.business.user.service.UserDataService;
@Service
public class UserDataServiceImpl  implements UserDataService{

	@Resource
	UserDataDao userDataDao;
	
	@Override
	public UserData getUserData() {
		
		return userDataDao.getUserData();
	}
	@Override
	public UserData getUserDataByT() {
		
		return userDataDao.getUserDataByT();
	}
	/*@Override
	public UserData getUserDataByS(String sTime, String eTime) {
		return userDataDao.getUserDataByS(sTime,eTime);
	}*/

	@Override
	public List<ResData> getUserCountByW() {
		List<ResData> datasList=userDataDao.getUserCountByW();
		for(ResData dl: datasList){
			dl.setTimer(dl.getTimer().split(" ")[0]);
			dl.setType("用户注册");
		}
		return datasList;
	}

	@Override
	public List<ResData> getUserRByW() {
		List<ResData> dataList=userDataDao.getUserRByW();
		for(ResData dl: dataList){
			dl.setTimer(dl.getTimer().split(" ")[0]);
			dl.setType("用户开户");
		}
		return  dataList;
	}
	@Override
	public List<ResData> getUserIByW() {
		List<ResData> dataList=userDataDao.getUserIByW();
		for(ResData dl: dataList){
			dl.setTimer(dl.getTimer().split(" ")[0]);
			dl.setType("用户投资");
		}
		return  dataList;
	}
	@Override
	//注册
	public List<ResData> getUserCountByM() {
		List<ResData> dataList=userDataDao.getUserCountByM();	
		for(ResData dl: dataList){
			dl.setTimer(dl.getTimer().split(" ")[0]);
			dl.setType("用户注册");
		}
		return dataList;
	}
	//开户
	@Override
	public List<ResData> getUserRByM() {
		List<ResData> dataList=userDataDao.getUserRByM();
		for(ResData dl: dataList){
			String timer=dl.getTimer().split(" ")[0];
			dl.setTimer(timer);
			if (timer.equals("2015-12-04")) {
				dl.setCount(0);
			}
			dl.setType("用户开户");
		}
		return dataList;
	}
	// 投资
	@Override
	public List<ResData> getUserIByM() {
		List<ResData> dataList=userDataDao.getUserIByM();
		for(ResData dl: dataList){
			dl.setTimer(dl.getTimer().split(" ")[0]);
			dl.setType("用户投资");
		}
		return dataList;
	}
	@Override
	public List<ResData> getUserCountByD(String sTime,String eTime) {
		List<ResData> dataList=userDataDao.getUserCountByD(sTime,eTime);
		for(ResData dl: dataList){
			dl.setTimer(dl.getTimer().split(" ")[0]);
			dl.setType("用户注册");
		}
		return dataList;
	}
	@Override
	public List<ResData> getUserIByD(String sTime,String eTime) {
		List<ResData> dataList=userDataDao.getUserIByD(sTime,eTime);
		for(ResData dl: dataList){
			dl.setTimer(dl.getTimer().split(" ")[0]);
			dl.setType("用户投资");
		}
		return dataList;
	}
	@Override
	public List<ResData> getUserRByD(String sTime,String eTime) {
		List<ResData> dataList=userDataDao.getUserRByD(sTime,eTime);
		for(ResData dl: dataList){
			String timer=dl.getTimer().split(" ")[0];
			dl.setTimer(timer);
			dl.setType("用户开户");
			if (timer.equals("2015-12-04")) {
				dl.setCount(0);
			}
		}
		return dataList;
	}
	@Override
	public double getRegisterUserCount(String start, String end,
			String userSource) {
	
		return userDataDao.getRegisterUserCount(start, end, userSource);
	}
	@Override
	public double getRealNameUserCount(String start, String end,
			String userSource) {
		// TODO Auto-generated method stub
		return userDataDao.getRealNameUserCount(start, end, userSource);
	}
	@Override
	public double getFristInvestCount(String start, String end,
			String userSource) {
		// TODO Auto-generated method stub
		return userDataDao.getFristInvestCount(start, end, userSource);
	}
	@Override
	public double getFristInvestMoney(String start, String end,
			String userSource) {
		// TODO Auto-generated method stub
		return userDataDao.getFristInvestMoney(start, end, userSource);
	}
	@Override
	public double getInvestMoneyAgain(String start, String end,
			String userSource) {
		// TODO Auto-generated method stub
		return userDataDao.getInvestMoneyAgain(start, end, userSource);
	}
	@Override
	public double getInvestUserMoneyAgain(String start, String end,
			String userSource) {
		// TODO Auto-generated method stub
		return userDataDao.getInvestUserMoneyAgain(start, end, userSource);
	}
	

	


	
}
