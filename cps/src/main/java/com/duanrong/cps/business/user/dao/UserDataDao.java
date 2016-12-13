package com.duanrong.cps.business.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import base.dao.BaseDao;

import com.duanrong.cps.business.user.model.ResData;
import com.duanrong.cps.business.user.model.UserData;

public interface UserDataDao extends BaseDao<UserData> {

	UserData getUserData();

	UserData getUserDataByT();

	/*UserData getUserDataByS(@Param("sTime")String sTime, @Param("eTime")String eTime);*/
	
	 List<ResData > getUserCountByW();

	List<ResData> getUserRByW();

	List<ResData> getUserIByW();
	//用户注册
	List<ResData> getUserCountByM();
	//用户开户
	List<ResData> getUserRByM();
	//用户投资
	List<ResData> getUserIByM();

	List<ResData> getUserCountByD(String sTime, String eTime);

	List<ResData> getUserIByD(String sTime, String eTime);

	List<ResData> getUserRByD(String sTime, String eTime);

	double getRegisterUserCount(String start, String end, String userSource);

	double getRealNameUserCount(String start, String end, String userSource);

	double getFristInvestCount(String start, String end, String userSource);

	double getFristInvestMoney(String start, String end, String userSource);

	double getInvestMoneyAgain(String start, String end, String userSource);

	double getInvestUserMoneyAgain(String start, String end, String userSource);


	
}
