package com.duanrong.drpay.business.account.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.Log;

import com.duanrong.drpay.business.account.dao.BankCardDao;
import com.duanrong.drpay.business.account.model.BankCard;
import com.duanrong.drpay.business.account.model.UnbindCardInfo;
import com.duanrong.drpay.business.account.service.BankCardService;
import com.duanrong.drpay.business.payment.model.PaymentBankInfo;


/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-9-11 下午2:54:51
 * @Description : drsoa Maven Webapp com.duanrong.business.bankcard.service.impl
 *              BankCardServiceImpl.java
 * 
 */
@Service
public class BankCardServiceImpl implements BankCardService {
	@Resource
	BankCardDao bankCardDao;
	
	@Resource
	Log log;

	DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
	
	@Override
	public List<BankCard> getBankCardsByGroupCondition(BankCard bankCard) {
		List<BankCard> bcs = bankCardDao.getBankCardsByGroupCondition(bankCard);
		return bcs;
	}

	@Override
	public void insert(BankCard bankCard) {
		bankCardDao.insert(bankCard);
	}

	@Override
	public void update(BankCard bankCard) {
		bankCardDao.update(bankCard);
	}

	@Override
	public List<BankCard> getValidBankCardByUserId(String userId,
			boolean containsVerifying) {
		return bankCardDao.getValidBankCardByUserId(userId, containsVerifying);
	}

	@Override
	public List<BankCard> getVerifyingBankCardByUserId(String userId) {
		return bankCardDao.getVerifyingBankCardByUserId(userId);
	}

	@Override
	public List<BankCard> getCancelTheTieCard() {
		return bankCardDao.getCancelTheTieCard();
	}
	@Override
	public BankCard getUnbindingCard(String userId) {
		return bankCardDao.getUnbindingCard(userId);
	}

	/**
	 * 重新绑定银行卡
	 * 会将用户原来绑定的银行卡覆盖掉
	 * 参数：bankCard
	 * 
	 */
	@Override
	public void quickBindingCard(BankCard bankCard) {
		//参数校验
		if (null == bankCard
				|| StringUtils.isBlank(bankCard.getUserId())
				|| StringUtils.isBlank(bankCard.getCardNo())
				|| StringUtils.isBlank(bankCard.getBank())) {
			log.errLog("绑卡失败","参数校验失败 ：" + bankCard.toString() );
			return;
		}
		try{
			// 通过用户ID找到该用的银行卡,状态为:审核中、审核通过
			List<BankCard> list = bankCardDao.getValidBankCardByUserId(
					bankCard.getUserId(), true);
			if(list!=null){
				for(BankCard card : list){
					//将状态改为失败
					card.setDeleteBankCard("delete");
					String cancelBandDingTime = format.format(new Date());
					card.setCancelBandDingTime(cancelBandDingTime);
					card.setCancelStatus("被新的绑卡覆盖");
					//正常情况下是一条数据,没有采用批量更新
					bankCardDao.update(card);
				}
			}
			bankCardDao.insert(bankCard);
		}catch (Exception e) {
			log.errLog("绑卡失败",bankCard.toString() +"Exception:" + e);
		}
		
	}

	@Override
	public BankCard get(String id) {
		return bankCardDao.get(id);
	}

	@Override
	public BankCard getByPaymentNo(String paymentNo) {
		return bankCardDao.getByPaymentNo(paymentNo);
	}

	@Override
	public void updateBankMobile(String mobile, String userId) {
		bankCardDao.updateBankMobile(mobile, userId);
		
	}

	@Override
	public String getMobile(String userId, String bankNo) {
		return bankCardDao.getmobile(userId,bankNo);
	}

	@Override
	public List<PaymentBankInfo> findBankInfoByName(String bankName) {
		return bankCardDao.findBankInfoByName(bankName);
	}

	@Override
	public BankCard getBankCardVerifyingByUserId(String userId) {
		return bankCardDao.getBankCardVerifyingByUserId(userId);
	}

	@Override
	public BankCard getBankCardVerifedByUserId(String userId) {
		return bankCardDao.getBankCardVerifedByUserId(userId);
	}

	@Override
	public List<String> getBankCardUsableByBaoFoo() {
		return bankCardDao.getBankCardUsableByBaoFoo();
	}
	
	@Override
	public List<String> getBankCardUsableByCgt() {
		return bankCardDao.getBankCardUsableByCgt();
	}

	@Override
	public boolean booleanbankSupport(String PaymentId,String bankCode) {
		boolean flag=false;
		//根据渠道查出所有支持提现的银行卡
		List<String> bankList=bankCardDao.getBankCardUsableByPaymentId(PaymentId);
		for (String string : bankList) {
			if(string.equals(bankCode)){
				flag=true;	
				break;
			}
		}
		return flag;
	}

	@Override
	public void updateAllValidCard(BankCard bankCard) {
		bankCardDao.updateAllValidCard(bankCard);
	}

	@Override
	public PaymentBankInfo getBankInfoByCode(String bank) {
		return bankCardDao.getBankInfoByCode(bank);
	}

	@Override
	public void insertUnbindCardInfo(UnbindCardInfo unbindCardInfo) {
		bankCardDao.insertUnbindCardInfo(unbindCardInfo);
	}

	@Override
	public List<UnbindCardInfo> getUnbindCardInfo(String userId, int status) {
		return bankCardDao.getUnbindCardInfo(userId, status);
	}
}
