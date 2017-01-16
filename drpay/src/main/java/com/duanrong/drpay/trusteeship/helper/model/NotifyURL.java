package com.duanrong.drpay.trusteeship.helper.model;

import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.trusteeship.service.TrusteeshipAccountService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipFundTransferService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipInvestService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipPaymentService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipRepayService;

/**
 * @author xiao
 * @version 1.0.0
 * @datetime 2016-12-8 11:38
 */
public enum NotifyURL {

	/**
	 * 投标
	 */
	INVE(TrusteeshipInvestService.class, "investCallback",
			GeneratorPreTransactionJSON.class, BusinessEnum.invest),

	/**
	 * 开户 1、回调接口类 2、回调方法 名 3、回调方法参数类型 ResponseGeneratorJSON 的子类
	 */
	OPAC(TrusteeshipAccountService.class, "createAccountCallback",
			GeneratorUserAccountJSON.class, BusinessEnum.create_account),

	/**
	 * 开户 1、回调接口类 2、回调方法 名 3、回调方法参数类型 ResponseGeneratorJSON 的子类
	 */
	ACTI(TrusteeshipAccountService.class, "activateAccountCallback",
			GeneratorUserAccountJSON.class, BusinessEnum.activate_account),
	/**
	 * 绑定银行卡
	 */
	BYBC(TrusteeshipAccountService.class, "bindCardCallback",
			GeneratorUserAccountJSON.class, BusinessEnum.bindcard),
	/**
	 * 解绑银行卡
	 */
	TUBC(TrusteeshipAccountService.class, "unBindCardCallback",
			GeneratorJSON.class, BusinessEnum.unbindcard),
	/**
	 * 修改密码
	 */
	RPWD(TrusteeshipAccountService.class, "resetPasswordCallback",
			GeneratorJSON.class, BusinessEnum.reset_password),
	/**
	 * 修改预留手机号
	 */
	MDPN(TrusteeshipAccountService.class, "modifyMobileCallback",
			GeneratorUserAccountJSON.class, BusinessEnum.modify_mobile),
	/**
	 * 校验密码
	 */
	CPWD(TrusteeshipAccountService.class, "checkPasswordCallback",
			GeneratorJSON.class),
	/**
	 * 充值
	 */
	RECH(TrusteeshipPaymentService.class, "rechargeCallback",
			GeneratorRechargeJSON.class, BusinessEnum.recharge),
	/**
	 * 提现
	 */
	WICA(TrusteeshipPaymentService.class, "withdrawCallback",
			GeneratorWithdrawJSON.class, BusinessEnum.withdraw_cash),
	/**
	 * 还款
	 */
	REPAY(TrusteeshipRepayService.class, "repayCallback",
			GeneratorPreTransactionJSON.class, BusinessEnum.repay),

	/**
	 * 通用转账
	 */										
	TSCA(TrusteeshipFundTransferService.class, "transferCallback",
			GeneratorJSON.class, BusinessEnum.transfer);

	/**
	 * callback 类
	 */
	private Class<?> service;

	/**
     * 
     */
	private Class<? extends GeneratorJSON> parameterType;

	/**
	 * callback 回调方法名
	 */
	private String notifyMethod;

	/**
	 * 业务枚举
	 */
	private BusinessEnum businessType;

	/**
	 * 构建 notifyURL 回调方法默认 callback 方法参数类型 默认 ResponseGeneratorJSON
	 * 
	 * @param service
	 */
	NotifyURL(Class<?> service) {
		this.service = service;
		this.notifyMethod = "callback";
		this.parameterType = GeneratorJSON.class;
	}

	/**
	 * 构建 notifyURL 回调方法默认 callback 方法参数类型 默认 ResponseGeneratorJSON
	 * 
	 * @param service
	 */
	NotifyURL(Class<?> service, BusinessEnum businessType) {
		this.service = service;
		this.notifyMethod = "callback";
		this.parameterType = GeneratorJSON.class;
		this.businessType = businessType;
	}

	/**
	 * 构建 notifyURL 方法参数类型 默认 ResponseGeneratorJSON
	 * 
	 * @param service
	 * @param notifyMethod
	 */
	NotifyURL(Class<?> service, String notifyMethod) {
		this.service = service;
		this.notifyMethod = notifyMethod;
		this.parameterType = GeneratorJSON.class;
	}

	/**
	 * 构建 notifyURL 方法参数类型 默认 ResponseGeneratorJSON
	 * 
	 * @param service
	 * @param notifyMethod
	 */
	NotifyURL(Class<?> service, String notifyMethod, BusinessEnum businessType) {
		this.service = service;
		this.notifyMethod = notifyMethod;
		this.parameterType = GeneratorJSON.class;
		this.businessType = businessType;
	}

	/**
	 * 构建 notifyURL
	 * 
	 * @param service
	 * @param notifyMethod
	 * @param paramterType
	 */
	NotifyURL(Class<?> service, String notifyMethod,
			Class<? extends GeneratorJSON> parameterType) {
		this.service = service;
		this.notifyMethod = notifyMethod;
		this.parameterType = parameterType;
	}

	/**
	 * 构建 notifyURL
	 * 
	 * @param service
	 * @param notifyMethod
	 * @param paramterType
	 */
	NotifyURL(Class<?> service, String notifyMethod,
			Class<? extends GeneratorJSON> parameterType,
			BusinessEnum businessType) {
		this.service = service;
		this.notifyMethod = notifyMethod;
		this.parameterType = parameterType;
		this.businessType = businessType;
	}

	public Class<?> getService() {
		return this.service;
	}

	public String getNotifyMethod() {
		return this.notifyMethod;
	}

	public Class<? extends GeneratorJSON> getParameterType() {
		return this.parameterType;
	}

	public BusinessEnum getBusinessType() {
		return this.businessType;
	}
}