package com.duanrong.drpay.business.account.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.AESUtil;
import base.dao.impl.BaseDaoImpl;

import com.duanrong.drpay.business.account.dao.BankCardDao;
import com.duanrong.drpay.business.account.model.BankCard;
import com.duanrong.drpay.business.account.model.UnbindCardInfo;
import com.duanrong.drpay.business.payment.model.PaymentBankInfo;



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
		this.setMapperNameSpace("com.duanrong.drpay.business.account.mapper.BankCardMapper");
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
				.getSqlSession().selectList(this.getMapperNameSpace() + ".getValidBankCardByUserId",
						params);
	}

	@Override
	public void updateAllValidCard(BankCard bankCard) {
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".updateAllValidCard", bankCard);
	}

	@Override
	public List<BankCard> getVerifyingBankCardByUserId(String userId) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getVerifyingBankCardByUserId",
				userId);
	}
	
	@Override
	public List<BankCard> getCancelTheTieCard() {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getCancelTheTieCard");
	}
	@Override
	public BankCard getUnbindingCard(String userId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getUnbindingCard", userId);
	}

	@Override
	public BankCard getByPaymentNo(String paymentNo) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getByPaymentNo",paymentNo);
	}

	@Override
	public void updateBankMobile(String mobile, String userId) {
		Map<String, Object> map=new HashMap<>();
		map.put("mobile",AESUtil.encode(mobile));
		map.put("userId",userId);
		this.getSqlSession().update(this.getMapperNameSpace()+".updateBankMobile", map);
	}

	@Override
	public String getmobile(String userId, String bankNo) {
		Map<String, Object> map=new HashMap<>();
		map.put("userId",userId);
		map.put("bankNo",bankNo);
		return  this.getSqlSession().selectOne(this.getMapperNameSpace()+".getmobile", map);
	}

	/**
	 * 根据银行名称查询银行信息
	 * @param bankName
	 * @return
	 */
	@Override
	public List<PaymentBankInfo> findBankInfoByName(String bankName) {
		return this.getSqlSession().selectList(this.getMapperNameSpace() + 
				".findBankInfoByName", bankName);
	}

	@Override
	public BankCard getBankCardVerifyingByUserId(String userId) {
		return  this.getSqlSession().selectOne(this.getMapperNameSpace()+".getBankCardVerifyingByUserId",userId);
	}

	@Override
	public BankCard getBankCardVerifedByUserId(String userId) {
		return  this.getSqlSession().selectOne(this.getMapperNameSpace()+".getBankCardVerifedByUserId",userId);
	}

	@Override
	public List<String> getBankCardUsableByBaoFoo() {
		return  this.getSqlSession().selectList(this.getMapperNameSpace()+".getBankCardUsableByBaoFoo");
	}

	@Override
	public List<String> getBankCardUsableByPaymentId(String paymentId) {
		return  this.getSqlSession().selectList(this.getMapperNameSpace()+".getBankCardUsableByPaymentId",paymentId);
	}

	@Override
	public List<String> getBankCardUsableByCgt() {
		return  this.getSqlSession().selectList(this.getMapperNameSpace()+".getBankCardUsableByCgt");
	}

	@Override
	public PaymentBankInfo getBankInfoByCode(String bank) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getBankInfoByCode",bank);
	}

	@Override
	public void insertUnbindCardInfo(UnbindCardInfo unbindCardInfo) {
		this.getSqlSession().insert(this.getMapperNameSpace()+".insertUnbindCardInfo", unbindCardInfo);
	}

	@Override
	public List<UnbindCardInfo> getUnbindCardInfo(String userId, int status) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("status", status);
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".getUnbindCardInfo", params);
	}

}