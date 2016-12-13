package com.duanrong.dataAnalysis.business.userDataToday.dao;

import java.math.BigDecimal;

import sun.security.util.BigInt;

import com.duanrong.dataAnalysis.business.userDataToday.model.userDataT;

public interface userDataTDao {

	userDataT getUserDataT();

	Integer getAllMoneyT();

	BigDecimal getAllMoneyA();

	Integer getAllMoneyS();

	Integer getAllMoneyM();
	
}
