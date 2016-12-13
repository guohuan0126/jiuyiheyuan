package com.duanrong.dataAnalysis.business.turnOverT.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sun.security.util.BigInt;

import com.duanrong.dataAnalysis.business.turnOverT.dao.TurnOverTDao;
import com.duanrong.dataAnalysis.business.turnOverT.model.TurnOverT;
import com.duanrong.dataAnalysis.business.turnOverT.service.TurnOverTService;

@Service
public class TurnOverTServiceImpl implements TurnOverTService {

	
	@Resource
	private TurnOverTDao turnOverTDao;
	@Override
	public TurnOverT getTurnOverMoney() {
		
		TurnOverT turnOverT=turnOverTDao.getTurnOverMoney();
				//新注册用户总成交额
				int NAllMoney=turnOverT.getnUserMoneyCar()+turnOverT.getnUserMoneyH()+turnOverT.getnUserMoneyCompany()+turnOverT.getnUserMoneyB();
				turnOverT.setnAllMoney(NAllMoney);
				//历史注册用户总成交额
				long AllMoney=turnOverT.getaUserMoneyCar()+turnOverT.getaUserMoneyH()+turnOverT.getaUserMoneyCompany()+turnOverT.getaUserMoneyB();
				turnOverT.setAllMoney(AllMoney);
				//7天用户总成交额 SAllMoney]
				int SAllMoney=turnOverT.getsUserMoneyCar()+turnOverT.getsUserMoneyH()+turnOverT.getsUserMoneyCompany()+turnOverT.getsUserMoneyB();
				turnOverT.setsAllMoney(SAllMoney);
				//30天用户总成交额MAllMoney
				int MAllMoney=turnOverT.getmUserMoneyCar()+turnOverT.getmUserMoneyH()+turnOverT.getmUserMoneyCompany()+turnOverT.getmUserMoneyB();
				turnOverT.setmAllMoney(MAllMoney);
				//当日所有项目量
				int allMoneyLoan=turnOverT.getMoneyCar()+turnOverT.getMoneyHouse()+turnOverT.getMoneyCompany()+turnOverT.getMoneyNp();
				turnOverT.setAllMoneyLoan(allMoneyLoan);
				return turnOverT;
	}

	public static void main(String[] args) {
		long money1=1433087938l;
		long money2=458246300l;
		long money3=309000000l;
		long money4=68383000l;
		long allmoney=money1+money2+money3+money4;
		System.out.println(allmoney);
		System.out.println(allmoney/10000);
		
	}
	
}



