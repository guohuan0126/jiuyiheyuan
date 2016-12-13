package com.duanrong.dataAnalysis.business.user.service;

import java.util.List;

import com.duanrong.dataAnalysis.business.user.model.ResData;
import com.duanrong.dataAnalysis.business.user.model.UserData;

public interface UserDataService {

	UserData getUserData();

	UserData getUserDataByT();

 	/*UserData getUserDataByS(String sTime, String eTime);*/
	
	List<ResData> getUserCountByW();

	List<ResData> getUserRByW();

	List<ResData> getUserIByW();
	//用户注册（一个月）
	List<ResData> getUserCountByM();
	//用户开户（一个月）
	List<ResData> getUserRByM();
	//用户投资（一个月）
	List<ResData> getUserIByM();
	//注册 阶段
	List<ResData> getUserCountByD(String sTime,String eTime);
	//投资 阶段
	List<ResData> getUserIByD(String sTime,String eTime);
	//开户阶段
	List<ResData> getUserRByD(String sTime,String eTime);

	double getRegisterUserCount(String start, String end, String userSource);

	double getRealNameUserCount(String start, String end, String userSource);

	double getFristInvestCount(String start, String end, String userSource);

	double getFristInvestMoney(String start, String end, String userSource);

	double getInvestMoneyAgain(String start, String end, String userSource);

	double getInvestUserMoneyAgain(String start, String end, String userSource);



	
	
}
