package com.duanrong.dataAnalysis.business.userDataToday.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.security.util.BigInt;

import com.duanrong.dataAnalysis.business.userDataToday.dao.userDataTDao;
import com.duanrong.dataAnalysis.business.userDataToday.model.userDataT;
import com.duanrong.dataAnalysis.business.userDataToday.service.userDataTService;

@Service
public class userDataTServiceImpl implements userDataTService{

	@Autowired
	private userDataTDao userDataTDao;

	@Override
	public userDataT getUserDataT() {
		
		userDataT userDataT=userDataTDao.getUserDataT();
		//今日投资总数
		Integer moneyT=userDataTDao.getAllMoneyT();
		if (moneyT==null) {
			userDataT.setInvestMoneyByP(0);
			
		} else {
			int count=userDataT.getInvsetCount();
			if (count==0) {
				userDataT.setInvestMoneyByP(0);
			} else {
				moneyT=moneyT/count;
				userDataT.setInvestMoneyByP(moneyT);
			}
			
		}
		//历史
		BigDecimal moneyA=userDataTDao.getAllMoneyA();
		if (moneyA==null) {                                                     //投资资金  除于 投资人数（今日 历史 7 30 天）
			userDataT.setAllInvestMoneyByP(0);
		} else {
			int count=userDataT.getAllInvsetCount();
			if (count==0) {
				userDataT.setAllInvestMoneyByP(0);
			} else {
				 moneyA= moneyA.divide(new BigDecimal(count),2);
				/*moneyA=moneyA/count;*/
				userDataT.setAllInvestMoneyByP(moneyA.doubleValue());
			}
			
			

		}
		//7
		Integer moneyS=userDataTDao.getAllMoneyS();
		if (moneyS==null) {
			userDataT.setInvestMoneyByPS(0);
			
		} else {
			int count=userDataT.getInvsetCountS();
			if (count==0) {
				userDataT.setInvestMoneyByPS(0);
			} else {
				moneyS=moneyS/count;
				userDataT.setInvestMoneyByPS(moneyS);
			}
			

		}
		//30
		Integer moneyM=userDataTDao.getAllMoneyM();
		if (moneyM==null) {
			userDataT.setInvestMoneyByPM(0);
			
		} else {
			int count=userDataT.getInvsetCountM();
			if (count==0) {
				userDataT.setInvestMoneyByPM(0);
			} else {
				moneyM=moneyM/count;
				userDataT.setInvestMoneyByPM(moneyM);
			}
			

		}
		
		
		return userDataT;
	}

	/*@Override
	public Integer getAllMoneyT() {
		return userDataTDao.getAllMoneyT();
	}

	@Override
	public Integer getAllMoneyA() {
		// TODO Auto-generated method stub
		return userDataTDao.getAllMoneyA();
	}

	@Override
	public Integer getAllMoneyS() {
		// TODO Auto-generated method stub
		return userDataTDao.getAllMoneyS();
	}

	@Override
	public Integer getAllMoneyM() {
		// TODO Auto-generated method stub
		return userDataTDao.getAllMoneyM();
	}*/
	
	
}
