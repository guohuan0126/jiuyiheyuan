package com.duanrong.business.bankcard.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.bankcard.dao.BankCardDao;
import com.duanrong.business.bankcard.model.BankCard;
import com.duanrong.business.sms.model.Sms;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-9-11 下午2:57:59
 * @Description : drsoa Maven Webapp com.duanrong.business.bankcard.dao
 *              BankCardDaoImpl.java
 * 
 */
@Repository
public class BankCardDaoImpl extends BaseDaoImpl<BankCard> implements
		BankCardDao {

	public BankCardDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.bankcard.mapper.BankCardMapper");
	}

	/**
	 * 
	 * @description 组合查询银行卡
	 * @author 孙铮
	 * @time 2014-9-11 下午2:53:01
	 * @param loan
	 * @return
	 */
	public List<BankCard> getBankCardsByGroupCondition(BankCard bankCard) {
		List<BankCard> bankcards = this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getBankCardsByGroupCondition",
				bankCard);
		return bankcards;
	}

	@Override
	public List<BankCard> getValidBankCardByUserId(String userId,
			boolean containsVerifying) {

		Map<String, Object> params = new HashMap<>();

		params.put("userId", userId);

		if (containsVerifying) {
			params.put("valid", "all");
		} else {
			params.put("valid", "verified");
		}

		return this
				.getSqlSession()
				.selectList(
						this.getMapperNameSpace() + ".getValidBankCardByUserId",
						params);
	}

	@Override
	public void updateAllValid4Cancel(BankCard bankCard) {
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".updateAllValid4Cancel", bankCard);
	}

	
	@Override
	public PageInfo<BankCard> pageInfo(int pageNo, int pageSize, Map map) {
		PageHelper.startPage(pageNo, pageSize);
		List<BankCard> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo", map);
		PageInfo<BankCard> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
}
