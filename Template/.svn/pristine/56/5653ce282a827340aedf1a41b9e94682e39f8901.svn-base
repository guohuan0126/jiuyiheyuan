package com.duanrong.dataAnalysis.business.userDataToday.dao.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;

import sun.security.util.BigInt;
import base.dao.impl.BaseDaoImpl;

import com.duanrong.dataAnalysis.business.userDataToday.dao.userDataTDao;
import com.duanrong.dataAnalysis.business.userDataToday.model.userDataT;
@Repository
public class userDataTDaoImpl extends BaseDaoImpl<userDataT> implements userDataTDao {

	
	public  userDataTDaoImpl() {
		this.setMapperNameSpace("com.duanrong.dataAnalysis.business.userDataToday.mapper.userDataTodayMapper"); // 设置命名空间
	}


	//查询用户数据
	@Override
	public userDataT getUserDataT() {
		
		return this.getSqlSession().selectOne("getUserDataT");
	}

	//今日人均投资
	@Override
	public Integer getAllMoneyT() {
		
		return this.getSqlSession().selectOne("getTMoney");
	}


	@Override
	//历史人均投资
	public BigDecimal getAllMoneyA() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("getAllMoney");
	}


	@Override
	//7天注册人均投资
	public Integer getAllMoneyS() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("getMoneyS");
	}


	@Override
	//30天人均投资
	public Integer getAllMoneyM() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("getMoneyM");
	}
	
}
