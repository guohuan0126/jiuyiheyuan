package com.duanrong.business.bankinfo.dao.impl;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import com.duanrong.business.bankinfo.dao.BankInfoDao;
import com.duanrong.business.bankinfo.model.BankInfo;
/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : YUMIN
 * @CreateTime : 2016-4-28 下午5:56:31
 * @Description : 银行卡信息管理
 * 
 */
@Repository
public class BankInfoImpl extends BaseDaoImpl<BankInfo> implements
		BankInfoDao {

	public BankInfoImpl() {
		this.setMapperNameSpace("com.duanrong.business.bankinfo.mapper.BankInfoMapper");
	}

	@Override
	public List<BankInfo> getBankInfo(BankInfo bankInfo) {
		List<BankInfo> bankInfos = this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getBankInfo",
				bankInfo);
		return bankInfos;
	}

	@Override
	public int updateBankStatue(Map<String, Object> params) {		
		return this.getSqlSession().update(	this.getMapperNameSpace() + ".updateBankstatus",
				params);
	}

	@Override
	public BankInfo readByid(String id) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".get",
				id);
	}

	
}
