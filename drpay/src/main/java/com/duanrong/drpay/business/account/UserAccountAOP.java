package com.duanrong.drpay.business.account;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;

import util.Log;
import base.error.ErrorCode;
import base.exception.ParameterException;
import base.exception.UserAccountException;
import base.exception.UserInfoException;

import com.duanrong.drpay.business.account.model.UserAccount;
import com.duanrong.drpay.business.account.service.UserAccountService;
import com.duanrong.drpay.business.user.model.User;
import com.duanrong.drpay.business.user.service.UserService;

/**
 * 账户检查, 账户操作之前检查用户是否被禁用，账户是否被锁 ...
 * 
 * @author xiao
 * @datetime 2016年11月25日 上午11:58:09
 */
public class UserAccountAOP {

	@Resource
	UserService userService;

	@Resource
	UserAccountService userAccountService;

	@Resource
	Log log;

	/**
	 * 账户检查
	 * 
	 * @throws ParameterException
	 * @throws UserInfoException
	 * @throws UserAccountException
	 */
	void checkAccount(JoinPoint pjp) throws ParameterException,
			UserInfoException, UserAccountException {
		Object[] objs = pjp.getArgs();
		if (objs == null || objs.length <= 0)
			throw new ParameterException(ErrorCode.ParametersError);
		if (objs[0] == null || !(objs[0] instanceof String))
			throw new ParameterException(ErrorCode.ParametersError);
		String userId = (String) objs[0];
		User user = userService.get(userId);
		if (user == null)
			throw new UserInfoException(ErrorCode.UserNoRegist);
		if (user.getStatus().equals("0"))
			throw new UserInfoException(ErrorCode.UserDisable);
		UserAccount userAccount = userAccountService.getUserAccount(userId);
		if (userAccount == null)
			throw new UserAccountException(ErrorCode.UserAccountNoOpened);
		if (userAccount.getStatus() != 1) 
			throw new UserAccountException(ErrorCode.UserAccountNoOpened); 
			
		// 账户平衡检查
		/*
		 * UserAccount usa = userAccountService.getUserMoney(userId); if
		 * (ArithUtil.round( userAccount.getAvailableBalance() -
		 * usa.getAvailableBalance(), 2) != 0){ log.infoLog("账户不平衡", "userId: "
		 * + userId + ",可用余额：" + userAccount.getAvailableBalance() + ", 冻结金额: "
		 * + userAccount.getFreezeAmount() + ", 流水统计可用金额： " +
		 * usa.getAvailableBalance() + "，冻结金额：" + usa.getFreezeAmount() +
		 * "请及时处理！！", 1); throw new
		 * UserAccountException(ErrorCode.UserAccountNoBalance); }
		 */

	}
}