package com.duanrong.drpay.business.account.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.AESUtil;
import util.Log;
import base.error.ErrorCode;
import base.exception.BankCardException;
import base.exception.ErrorCodeException;
import base.exception.UserAccountException;

import com.duanrong.drpay.business.account.BankName;
import com.duanrong.drpay.business.account.dao.BankCardDao;
import com.duanrong.drpay.business.account.dao.UserAccountDao;
import com.duanrong.drpay.business.account.model.BankCard;
import com.duanrong.drpay.business.account.model.UserAccount;
import com.duanrong.drpay.business.account.service.UserAccountBusinessService;
import com.duanrong.drpay.business.autoinvest.model.AutoInvest;
import com.duanrong.drpay.business.autoinvest.service.AutoInvestService;
import com.duanrong.drpay.business.user.dao.UserDao;
import com.duanrong.drpay.business.user.model.User;
import com.duanrong.drpay.business.user.service.AuthInfoService;
import com.duanrong.drpay.business.user.service.UserService;
import com.duanrong.drpay.config.IdUtil;

@Service
public class UserAccountBusinessServiceImpl implements
		UserAccountBusinessService {

	@Resource
	UserDao userDao;

	@Resource
	BankCardDao bankCardDao;

	@Resource
	Log log;

	@Resource
	UserService userService;

	@Resource
	AuthInfoService authInfoService;

	@Resource
	UserAccountDao userAccountDao;

	@Resource
	AutoInvestService autoInvestService;

	@Override
	public void openAccount(String userId, String realName, String idCard,
			String password, String bankCard, String mobile) {

	}

	@Override
	public void bindingCard(String userId, String cardNo, String cardName,
			String mobile, String paymentId,String status,String paymentNo) throws BankCardException {
		BankCard bankCard = new BankCard();
		bankCard.setUserId(userId);
		List<BankCard> selectList = (List<BankCard>) bankCardDao
				.getValidBankCardByUserId(userId, false);
		if (selectList.size() > 0)
			throw new BankCardException(ErrorCode.BindCardExist);
		BankCard bank = new BankCard();
		bank.setId(IdUtil.randomUUID());
		bank.setCardNo(AESUtil.encode(cardNo));
		bank.setUserId(userId);
		bank.setName(cardName);
		bank.setBank(BankName.getBankCode(cardName));
		bank.setStatus(status);
		bank.setBankCardType("储蓄卡");
		bank.setPaymentId(paymentId);
		bank.setPaymentNo(paymentNo);
		bank.setTime(new Date());
		bank.setBankMobile(AESUtil.encode(mobile));
		bankCardDao.insert(bank);
	}

	@Override
	public void unBindingCard(String userId) {

	}

	@Override
	public boolean isPassword(String userId, String password) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(password))
			return false;
		try {
			UserAccount userAccount = userAccountDao.get(userId);
			if (userAccount == null || !password.equals(userAccount.getPassword()))
				return false;
			return true;
		} catch (UserAccountException e) {
			return false;
		}	
	}

	@Override
	public void rsetPassword(String userId, String password,
			String mobileNumber, String authCode, String type)
			throws ErrorCodeException {
		if (authInfoService.operateAuthCode(mobileNumber, authCode,
				type)) {
			UserAccount userAccount = userAccountDao.get(userId);
			userAccount.setPassword(password);
			userAccountDao.update(userAccount);
		} else {
			log.errLog("UserAccountBusinessService->rsetMobile",
					"修改交易密码验证验证码失败" + "UserId:" + userId + "----");
		}
	}

	@Override
	public void freezeAccount(String userId) {
		userAccountDao.freezeAccount(userId);
	}

	@Override
	public void unfreezeAccount(String userId) {
		userAccountDao.unfreezeAccount(userId);
	}

	@Override
	public void authInvest(String userId, String password,
			String loanType, Integer maxDeadline, Double maxMoney,
			Double maxRate, Integer minDeadline, Double minMoney, Double minRate) {
		if (isPassword(userId, password)) {
			AutoInvest autoInvest = new AutoInvest();
			autoInvest.setUserId(userId);
			autoInvest.setLoanType(loanType);
			autoInvest.setMaxDeadline(maxDeadline);
			autoInvest.setMaxMoney(maxMoney);
			autoInvest.setMaxRate(maxRate);
			autoInvest.setMinDeadline(minDeadline);
			autoInvest.setMinMoney(minMoney);
			autoInvest.setMinRate(minRate);
			autoInvest.setStatus("on");
			autoInvest.setLastAutoInvestTime(new Date());
			Integer seqNum = 1;
			AutoInvest ai = autoInvestService.query(userId);
			if (ai != null)
				seqNum += ai.getSeqNum();
			autoInvest.setSeqNum(seqNum);
			autoInvestService.update(autoInvest);
			UserAccount userAccount = new UserAccount();
			userAccount.setUserId(userId);
			userAccount.setAutoInvest(1);
			userAccount.setTime(new Date());
			userAccountDao.update(userAccount);
		}else{
			log.errLog("UserAccountBusinessService->authInvest", "修改自动投标校验交易密码失败"
					+ "UserId:" + userId + "----");
		}
	}

	@Override
	public void cancelAutoInvest(String userId) {
		AutoInvest autoInvest = new AutoInvest();
		autoInvest.setUserId(userId);
		autoInvest.setStatus("off");
		autoInvest.setLastAutoInvestTime(new Date());
		autoInvestService.update(autoInvest);
		UserAccount userAccount = new UserAccount();
		userAccount.setUserId(userId);
		userAccount.setAutoInvest(0);
		userAccount.setTime(new Date());
		userAccountDao.update(userAccount);
	}

	@Override
	public void rsetMobile(String userId, String mobile, String oldMobile,
			String password, String authCode, String idCard, String paramType,
			String type) throws ErrorCodeException{
		boolean result = false;
		if ("0".equals(paramType)) {
			result = authInfoService.operateAuthCode(oldMobile, authCode, type);
		} else if ("1".equals(paramType)) {
			result = authInfoService.operateAuthCode(mobile, authCode, type);
		}
		if (result) {
			User user = userService.get(userId);
			// 修改红包券表中手机号
			if (user == null || StringUtils.isBlank(user.getUserId())
					|| StringUtils.isBlank(user.getMobileNumber())
					|| StringUtils.isBlank(mobile)) {
				log.errLog("UserAccountBusinessService->rsetMobile", "修改手机号失败"
						+ "UserId:" + userId + "----");
			} else {
				log.infoLog("重置手机号UserAccountBusinessService->rsetMobile",
						"old:" + user.getMobileNumber() + ",new:" + mobile);
				if (!isPassword(userId, password)) {
					log.errLog("UserAccountBusinessService->rsetMobile",
							"修改手机号验证交易密码错误！");
					return;
				}
				if (!(("1".equals(paramType)) && (userService.getIdCard(
						userId, idCard)))) {
					log.errLog("UserAccountBusinessService->rsetMobile",
							"修改手机号验证身份证号错误！");
					return;
				}
				userService.updateDetailMobileNumber(user.getMobileNumber(),
						mobile);
				userService.updateShareMobileNumber(user.getMobileNumber(),
						mobile);
				userService.updateReferrerMobileNumber(user.getMobileNumber(),
						mobile);
				user.setMobileNumber(mobile);
				userService.updateUserMobileNumber(user);
			}
		} else {
			log.errLog("UserAccountBusinessService->rsetMobile", "修改手机号校验验证码失败"
					+ "UserId:" + userId + "----");
		}
	}

}
