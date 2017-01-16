package com.duanrong.drpay.trusteeship.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.AESUtil;
import util.DateStyle;
import util.DateUtil;
import util.Log;
import util.MD5Encry;
import util.OssUtil;
import util.SmsHttpClient;
import base.error.ErrorCode;
import base.exception.BankCardException;
import base.exception.DataAlreadExistException;
import base.exception.ParameterException;
import base.exception.PlatformAccountException;
import base.exception.TradeException;
import base.exception.UserAccountException;
import base.exception.UserInfoException;

import com.duanrong.drpay.business.account.BankName;
import com.duanrong.drpay.business.account.PlatformAccountEnum;
import com.duanrong.drpay.business.account.model.BankCard;
import com.duanrong.drpay.business.account.model.CgtUserAccount;
import com.duanrong.drpay.business.account.model.Freeze;
import com.duanrong.drpay.business.account.model.UnbindCardInfo;
import com.duanrong.drpay.business.account.model.UserAccount;
import com.duanrong.drpay.business.account.service.BankCardService;
import com.duanrong.drpay.business.account.service.FreezeService;
import com.duanrong.drpay.business.account.service.PlatformAccountService;
import com.duanrong.drpay.business.account.service.UserAccountBusinessService;
import com.duanrong.drpay.business.account.service.UserAccountService;
import com.duanrong.drpay.business.autoinvest.model.AutoInvest;
import com.duanrong.drpay.business.autoinvest.service.AutoInvestService;
import com.duanrong.drpay.business.invest.InvestConstants;
import com.duanrong.drpay.business.payment.BankMapUtil;
import com.duanrong.drpay.business.user.model.AuthenticationResult;
import com.duanrong.drpay.business.user.model.RedPacket;
import com.duanrong.drpay.business.user.model.User;
import com.duanrong.drpay.business.user.service.AuthInfoService;
import com.duanrong.drpay.business.user.service.RedPacketService;
import com.duanrong.drpay.business.user.service.UserService;
import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.config.IdUtil;
import com.duanrong.drpay.config.ToType;
import com.duanrong.drpay.jsonservice.handler.RegexInput;
import com.duanrong.drpay.jsonservice.handler.TerminalEnum;
import com.duanrong.drpay.jsonservice.param.AutoInvestParamter;
import com.duanrong.drpay.jsonservice.param.UserAccountParameter;
import com.duanrong.drpay.trusteeship.constants.TrusteeshipServer;
import com.duanrong.drpay.trusteeship.helper.model.AuthType;
import com.duanrong.drpay.trusteeship.helper.model.Generator;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorPlatformJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorUserAccountJSON;
import com.duanrong.drpay.trusteeship.helper.model.NotifyURL;
import com.duanrong.drpay.trusteeship.helper.model.UserRole;
import com.duanrong.drpay.trusteeship.helper.service.TrusteeshipService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipAccountService;

@Service
public class TrusteeshipAccountServiceImpl implements TrusteeshipAccountService {

	@Resource
	TrusteeshipService trusteeshipService;

	@Resource
	UserService userService;

	@Resource
	BankCardService bankCardService;

	@Resource
	PlatformAccountService platformAccountService;

	@Resource
	Log log;

	@Resource
	UserAccountService userAccountService;

	@Resource
	UserAccountBusinessService userAccountBusinessService;

	@Resource
	AutoInvestService autoInvestService;

	@Resource
	RedPacketService redPacketService;

	@Resource
	AuthInfoService authInfoService;

	@Resource
	SmsHttpClient smsHttpClient;

	@Resource
	FreezeService freezeService;

	private static final String filePath = "applyunbindbankcard/"
			+ new SimpleDateFormat("yyyyMM").format(new Date());

	@Override
	public Generator createAccount(UserAccountParameter params,
			TerminalEnum terminalType) throws UserInfoException,
			UserAccountException, ParameterException, BankCardException {
		// 校验用户是否注册
		User user = userService.get(params.getUserId());
		if (user == null) {
			log.errLog("绑卡开户", "userId:" + params.getUserId() + "没有注册");
			throw new UserInfoException(ErrorCode.UserNoRegist);
		}
		// 身份证号已存在， 您填写的身份证号已存在
		if (userService.getIdCard(params.getUserId(), params.getIdCardNo())) {
			log.errLog("绑卡开户", "userId:" + params.getUserId() + "的身份证号："
					+ params.getIdCardNo() + "已被使用");
			throw new UserAccountException(ErrorCode.IdCardNoAlreadyExist);
		}
		// 判断投资或者借款角色
		/*
		 * if (userService.hasRoleByUserId(params.getUserId(), "INVESTOR") ||
		 * userService.hasRoleByUserId(params.getUserId(), "LOANER")) {
		 * log.errLog("绑卡开户", "userId:" + params.getUserId() +
		 * "已开户，拥有投资或者借款权限"); throw new
		 * UserAccountException(ErrorCode.UserAccountOpened); }
		 */
		// 判断用户是否开户
		UserAccount account = userAccountService.getUserAccount(params
				.getUserId());
		if (account != null && account.getStatus() == 1) {
			log.errLog("绑卡开户", "userId:" + params.getUserId() + "已开户");
			throw new UserAccountException(ErrorCode.UserAccountOpened);
		}
		Map<String, Object> cardInfoMap = BankMapUtil.findBankInfo(params
				.getBankcardNo());
		if (cardInfoMap.get("status").equals("error")) {
			log.errLog("绑卡开户", "userId:" + params.getUserId() + "输入的银行卡号:"
					+ params.getBankcardNo() + "不正确");
			throw new ParameterException(ErrorCode.BankCardValid);
		} else if (cardInfoMap.get("cardType").equals("贷记卡")) {
			log.errLog("绑卡开户", "userId:" + params.getUserId() + "输入的是信用卡号:"
					+ params.getBankcardNo());
			throw new BankCardException(ErrorCode.CreditCardNoBind);
		}
		String cardName = (String) cardInfoMap.get("name");
		String bankCode = BankName.getBankCode(cardName);// 银行简称
		if (!params.getBankCode().equals(bankCode)) {
			log.errLog("绑卡开户", "userId:" + params.getUserId()
					+ "输入的银行卡号与所选银行不一致");
			throw new BankCardException(ErrorCode.CardNoNotIdenticalWithBankNo);
		}

		// 查询出存管通支持的所有银行卡编码
		List<String> bankList = bankCardService.getBankCardUsableByCgt();
		boolean flag = false;
		for (String string : bankList) {
			if (string.equals(bankCode)) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			log.errLog("绑卡开户", "userId:" + params.getUserId() + "，存管通不支持该行"
					+ bankCode + "绑卡");
			throw new BankCardException(ErrorCode.BankCardNotSupported);
		}
		String requestNo = IdUtil.generateId(ToType.OPAC);

		// user实名
		user.setRealname(params.getRealName());
		user.setIdCard(params.getIdCardNo());
		// 插入银行卡列表（绑卡）
		BankCard bankCard = new BankCard();
		bankCard.setId(IdUtil.randomUUID());
		bankCard.setPaymentNo(requestNo);
		bankCard.setBank(bankCode);
		bankCard.setName(cardName);
		bankCard.setCardNo(AESUtil.encode(params.getBankcardNo()));
		bankCard.setBankMobile(AESUtil.encode(params.getMobile()));
		bankCard.setUserId(params.getUserId());
		bankCard.setTime(new Date());
		bankCard.setStatus("VERIFYING");
		bankCard.setBankCardType("储蓄卡");
		bankCard.setPaymentId("xmbank");
		// 封装存管通reqData
		GeneratorUserAccountJSON json = new GeneratorUserAccountJSON();
		json.setRequestNo(requestNo);
		json.setCallbackUrl(NotifyURL.OPAC);
		json.setSource(terminalType);
		json.setPlatformUserNo(params.getUserId());
		json.setRealName(params.getRealName());
		json.setIdCardType(params.getIdCardType());
		json.setUserRole(params.getUserRole());
		json.setIdCardNo(params.getIdCardNo());
		json.setMobile(params.getMobile());
		json.setBankcardNo(params.getBankcardNo());
		if (UserRole.INVESTOR == params.getUserRole()) {
			json.setAuthList(AuthType.TENDER, AuthType.CREDIT_ASSIGNMENT);
		} else if (UserRole.BORROWERS == params.getUserRole() || UserRole.INTERMEDIATOR == params.getUserRole()) {
			json.setAuthList(AuthType.REPAYMENT, AuthType.RECHARGE);
		}
		bankCardService.quickBindingCard(bankCard);
		userService.update(user);
		return trusteeshipService.create(json,
				TrusteeshipServer.PERSONAL_REGISTER,
				BusinessEnum.create_account);
	}

	@Override
	public void createAccountCallback(GeneratorUserAccountJSON respData) {
		try {
			if (respData == null) {
				log.errLog("个人绑卡开户服务器通知处理", "响应数据为空");
			}
			if (respData.getCode().equals("0")) {
				/* AUDIT 审核中;PASSED 审核通过;BACK 审核回退;REFUSED 审核拒绝 */
				if (respData.getAuditStatus().equals("PASSED")) {
					// 扣取鉴权手续费
					try {
						platformAccountService.transferOut(
								PlatformAccountEnum.PLATFORM_SYS, 1,
								BusinessEnum.create_account, "鉴权手续费",
								respData.getRequestNo());
					} catch (PlatformAccountException e) {
						log.errLog("绑卡开户",
								"userId:" + respData.getPlatformUserNo()
										+ "，平台余额不足，鉴权手续费扣取失败");
					}
					UserAccount userAccount = userAccountService
							.getUserAccount(respData.getPlatformUserNo());
					// 开户
					if (userAccount == null) {
						String authorization = null;
						if (respData.getUserRole().name().equals("INVESTOR")) {
							authorization = "autoInvest";
						} else if (respData.getUserRole().name()
								.equals("BORROWERS") || respData.getUserRole().name()
								.equals("INTERMEDIATOR")) {
							authorization = "autoRepay";
						}
						// 本地开户
						userAccountService.createUserAccount(
								respData.getPlatformUserNo(), "", authorization);
					}
					// 绑卡
					BankCard bankCard = bankCardService.getByPaymentNo(respData
							.getRequestNo());
					if (bankCard != null) {
						bankCard.setStatus("VERIFIED");
						bankCardService.update(bankCard);
					} else {

						log.errLog("个人绑卡开户服务器通知处理",
								"userId:" + respData.getPlatformUserNo()
										+ "的绑卡记录为空", 1);
					}
					// 增加用户角色
					if (respData.getUserRole().name().equals("INVESTOR")) {
						if (!userService.hasRoleByUserId(
								respData.getPlatformUserNo(), "INVESTOR")) {
							userService.addRole(respData.getPlatformUserNo(),
									"INVESTOR");
						}
					} else if (respData.getUserRole().name()
							.equals("BORROWERS")) {
						if (!userService.hasRoleByUserId(
								respData.getPlatformUserNo(), "LOANER")) {
							userService.addRole(respData.getPlatformUserNo(),
									"LOANER");
						}
					} else if (respData.getUserRole().name()
							.equals("INTERMEDIATOR")) {
						if (!userService.hasRoleByUserId(
								respData.getPlatformUserNo(), "INTERMEDIATOR")) {
							userService.addRole(respData.getPlatformUserNo(),
									"INTERMEDIATOR");
						}
					}
				} else {
					log.errLog("个人绑卡开户服务器通知处理",
							"userId:" + respData.getPlatformUserNo() + "审核状态为："
									+ respData.getAuditStatus());
				}
			} else {
				log.errLog("个人绑卡开户服务器通知处理", respData.toJSON());
			}
		} catch (Exception e) {
			log.errLog("个人绑卡开户服务器通知处理",
					"userId:" + respData.getPlatformUserNo() + ",异常：" + e);
		}
	}

	@Override
	public void activateAccountCallback(GeneratorUserAccountJSON respData) {
		try {
			if (respData == null) {
				log.errLog("用户激活", "响应数据为空");
			}
			if (respData.getCode().equals("0")) {
				/* AUDIT 审核中;PASSED 审核通过;BACK 审核回退;REFUSED 审核拒绝 */
				if (respData.getAuditStatus().equals("PASSED")) {
					UserAccount userAccount = userAccountService
							.getUserAccount(respData.getPlatformUserNo());
					// 开户
					if (userAccount == null) {
						throw new UserAccountException("用户激活,未查到激活用户，userId"
								+ respData.getPlatformUserNo());
					}
					userAccount.setStatus(1);
					userAccount.setTime(new Date());
					userAccountService.updateUserAccount(userAccount);
					try {
						if ("FULL_CHECKED".equals(respData.getAccessType())) {
							//四要素鉴权通过绑卡记录卡号手机号
							BankCard bankCard = new BankCard();
							bankCard.setId(IdUtil.randomUUID());
							bankCard.setPaymentNo(respData.getRequestNo());
							bankCard.setBank(respData.getBankcode());
							bankCard.setName(BankName.getBankName(respData.getBankcode()));
							bankCard.setCardNo(AESUtil.encode(respData.getBankcardNo()));
							bankCard.setUserId(respData.getPlatformUserNo());
							bankCard.setTime(new Date());
							bankCard.setStatus("VERIFIED");
							bankCard.setBankCardType("储蓄卡");
							bankCard.setPaymentId("xmbank");
							bankCard.setBankMobile(AESUtil.encode(respData.getMobile()));
							bankCardService.quickBindingCard(bankCard);
						} else if ("PART_CHECKED".equals(respData.getAccessType())) {
							//三要素鉴权通过绑卡记录卡号不记录手机号
							BankCard bankCard = new BankCard();
							bankCard.setId(IdUtil.randomUUID());
							bankCard.setPaymentNo(respData.getRequestNo());
							bankCard.setBank(respData.getBankcode());
							bankCard.setName(BankName.getBankName(respData.getBankcode()));
							bankCard.setCardNo(AESUtil.encode(respData.getBankcardNo()));
							bankCard.setUserId(respData.getPlatformUserNo());
							bankCard.setTime(new Date());
							bankCard.setStatus("VERIFIED");
							bankCard.setBankCardType("储蓄卡");
							bankCard.setPaymentId("xmbank");
							bankCardService.quickBindingCard(bankCard);
						} else {
							// 鉴权未通过进行解绑
							// 封装解绑（直连）reqData
							GeneratorJSON json = new GeneratorJSON();
							json.setRequestNo(IdUtil.generateId(ToType.TUBC));
							json.setPlatformUserNo(respData.getPlatformUserNo());
							Generator data = trusteeshipService.execute(json,
									TrusteeshipServer.UNBIND_BANKCARD_DIRECT,
									GeneratorJSON.class, BusinessEnum.unbindcard);
							if (data != null&& data.getRespData() != null&& "0".equals(data.getRespData().getCode())) {
								BankCard card = bankCardService.getBankCardVerifedByUserId(respData.getPlatformUserNo());
								if (card != null) {
									card.setDeleteBankCard("delete");
									card.setCancelStatus("解绑完成");
									card.setCancelBandDingTime(DateUtil.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM_SS));
									bankCardService.updateAllValidCard(card);
								} else {
									log.errLog("解绑卡", "userId:" + respData.getPlatformUserNo() + "不存在绑定的银行卡");
								}
							} else {
								log.errLog("解绑卡", "userId：" + data.getPlatformNo() + "解绑银行卡失败，" + data, 1);
							}
						}
					} catch (Exception e) {
						log.errLog("激活回调绑卡失败", "userId:"+respData.getPlatformUserNo()+",e:"+e);
					}
				} else {
					log.errLog("用户激活", "userId:" + respData.getPlatformUserNo()
							+ "审核状态为：" + respData.getAuditStatus());
				}
			} else {
				log.errLog("用户激活", respData.toJSON());
			}
		} catch (Exception e) {
			log.errLog("用户激活", "userId:" + respData.getPlatformUserNo()
					+ ",异常：" + e);
		}
	}

	@Override
	public CgtUserAccount queryCgtUserInfo(String userId)
			throws UserInfoException, UserAccountException {
		// 判断本地用户信息是否存在
		User user = userService.get(userId);
		if (user == null) {
			log.errLog("查询存管通用户信息", "userId:" + userId + "没有注册");
			throw new UserInfoException(ErrorCode.UserNoRegist);
		}
		UserAccount account = userAccountService.getUserAccount(userId);
		/*
		 * if (account == null) { log.errLog("查询存管通用户信息", "userId:" + userId +
		 * "未开户"); throw new
		 * UserAccountException(ErrorCode.UserAccountNoOpened); }
		 */
		// 查询存管通用户信息
		GeneratorJSON json = new GeneratorJSON();
		json.setPlatformUserNo(userId);
		Generator generator = trusteeshipService.execute(json,
				TrusteeshipServer.QUERY_USER_INFORMATION,
				GeneratorUserAccountJSON.class);
		GeneratorUserAccountJSON data = (GeneratorUserAccountJSON) generator
				.getRespData();
		CgtUserAccount cgtUserAccount = new CgtUserAccount();
		if (account == null && data == null) {
			log.errLog("查询存管通用户信息", "userId:" + userId + "未开户");
			throw new UserAccountException(ErrorCode.UserAccountNoOpened);
		}
		if (account != null) {
			cgtUserAccount.setAutoInvest(account.getAutoInvest());
			cgtUserAccount.setAutoRepay(account.getAutoRepay());
			cgtUserAccount.setAvailableBalance(account.getAvailableBalance());
			cgtUserAccount.setBalance(account.getBalance());
			cgtUserAccount.setFreezeAmount(account.getFreezeAmount());
			cgtUserAccount.setId(account.getId());
			cgtUserAccount.setPassword(account.getPassword());
			cgtUserAccount.setStatus(account.getStatus());
			cgtUserAccount.setTime(account.getTime());
			cgtUserAccount.setUserId(account.getUserId());
		}
		if (data != null) {
			cgtUserAccount.setCgtAccessType(data.getAccessType());
			cgtUserAccount.setCgtActiveStatus(data.getActiveStatus());
			cgtUserAccount.setCgtAuditStatus(data.getAuditStatus());
			cgtUserAccount.setCgtAuthlist(data.getAuthList());
			cgtUserAccount.setCgtAvailableAmount(Double.parseDouble(data
					.getAvailableAmount()));
			cgtUserAccount.setCgtBalance(Double.parseDouble(data.getBalance()));
			cgtUserAccount.setCgtBankcardNo(data.getBankcardNo());
			cgtUserAccount.setCgtBankcode(data.getBankcode());
			cgtUserAccount.setCgtFreezeAmount(Double.parseDouble(data
					.getFreezeAmount()));
			cgtUserAccount.setCgtIdCardNo(data.getIdCardNo());
			cgtUserAccount.setCgtIdCardType(data.getIdCardType());
			cgtUserAccount.setCgtIsImportUserActivate(data
					.getIsImportUserActivate());
			cgtUserAccount.setCgtMobile(data.getMobile());
			cgtUserAccount.setCgtName(data.getName());
			cgtUserAccount.setCgtUserId(data.getPlatformUserNo());
			cgtUserAccount.setCgtUserRole(data.getUserRole());
			cgtUserAccount.setCgtUserType(data.getUserType());
		}
		return cgtUserAccount;
	}

	@Override
	public UserAccount queryLocalUserInfo(String userId)
			throws UserInfoException, UserAccountException {

		// 查询本地用户信息与本地用户账户信息
		User user = userService.get(userId);
		if (user == null) {
			log.errLog("查询本地用户信息", "userId:" + userId + "没有注册");
			throw new UserInfoException(ErrorCode.UserNoRegist);
		}
		UserAccount account = userAccountService.getUserAccount(userId);
		if (account == null) {
			log.errLog("查询本地用户信息", "userId:" + userId + "未开户");
			throw new UserAccountException(ErrorCode.UserAccountNoOpened);
		}
		return account;
	}

	@Override
	public Generator checkPassword(String userId, String templateType,
			TerminalEnum terminalType) throws UserInfoException,
			UserAccountException {
		User user = userService.get(userId);
		if (user == null) {
			log.errLog("存管通校验密码", "userId:" + userId + "没有注册");
			throw new UserInfoException(ErrorCode.UserNoRegist);
		}
		UserAccount account = userAccountService.getUserAccount(userId);
		if (account == null) {
			log.errLog("存管通校验密码", "userId:" + userId + "未开户");
			throw new UserAccountException(ErrorCode.UserAccountNoOpened);
		}
		GeneratorJSON json = new GeneratorJSON();
		json.setSource(terminalType);
		json.setPlatformUserNo(userId);
		json.setRequestNo(IdUtil.generateId(ToType.CPWD));
		json.setBizTypeDescription(templateType);
		json.setCallbackUrl(NotifyURL.CPWD);
		return trusteeshipService
				.create(json, TrusteeshipServer.CHECK_PASSWORD);
	}

	@Override
	public void checkPasswordCallback(GeneratorJSON respData) {
		try {
			if (respData == null) {
				log.errLog("存管通校验密码服务器通知处理", "响应数据为空");
			}
			if ("0".equals(respData.getCode())) {
				// 无本地数据处理
			} else {
				log.errLog("存管通校验密码服务器通知处理", respData.toJSON());
			}
		} catch (Exception e) {
			log.errLog("存管通校验密码服务器通知处理",
					"userId:" + respData.getPlatformUserNo() + "," + e);
		}
	};

	@Override
	public Generator bindCard(String userId, String bankcardNo,
			String bankCode, String mobile, TerminalEnum terminalType)
			throws UserInfoException, UserAccountException, ParameterException,
			BankCardException {
		// 校验用户是否注册
		UserAccount account = userAccountService.getUserAccount(userId);
		if (account == null) {
			log.errLog("绑定银行卡", "userId:" + userId + "未开户");
			throw new UserAccountException(ErrorCode.UserAccountNoOpened);
		}
		if (account != null && !(account.getStatus() == 1)) {
			log.errLog("绑定银行卡", "userId:" + userId + "账户状态不正常，status："
					+ account.getStatus());
			throw new UserAccountException(ErrorCode.UserAccountStatusError);
		}

		Map<String, Object> cardInfoMap = BankMapUtil.findBankInfo(bankcardNo);
		if (cardInfoMap.get("status").equals("error")) {
			log.errLog("绑定银行卡", "userId:" + userId + "输入的银行卡号:" + bankcardNo
					+ "不正确");
			throw new ParameterException(ErrorCode.BankCardValid);
		} else if (cardInfoMap.get("cardType").equals("贷记卡")) {
			log.errLog("绑定银行卡", "userId:" + userId + "输入的是信用卡号:" + bankcardNo);
			throw new BankCardException(ErrorCode.CreditCardNoBind);
		}

		List<String> bankList = bankCardService.getBankCardUsableByCgt();
		boolean flag = false;
		for (String string : bankList) {
			if (string.equals(bankCode)) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			log.errLog("绑定银行卡", "userId:" + userId + "，存管通不支持该行" + bankCode
					+ "绑卡");
			throw new BankCardException(ErrorCode.BankCardNotSupported);
		}

		String cardName = (String) cardInfoMap.get("name");
		String bankCardCode = BankName.getBankCode(cardName);// 银行简称
		if (!bankCode.equals(bankCardCode)) {
			log.errLog("绑定银行卡", "userId:" + userId + "输入的银行卡号与所选银行不一致");
			throw new BankCardException(ErrorCode.CardNoNotIdenticalWithBankNo);
		}
		String requestNo = IdUtil.generateId(ToType.BYBC);
		// 绑卡接口调用之前，必须确定用户没有有效银行卡。
		BankCard verifedCard = bankCardService
				.getBankCardVerifedByUserId(userId);
		if (verifedCard != null) {
			log.errLog("绑定银行卡", "userId:" + userId + "存在已绑定的有效银行卡");
			throw new BankCardException(ErrorCode.BindCardExist);
		}
		// 插入银行卡列表（绑卡）
		BankCard bankCard = new BankCard();
		bankCard.setId(IdUtil.randomUUID());
		bankCard.setPaymentNo(requestNo);
		bankCard.setBank(BankName.getBankCode(cardName));
		bankCard.setName(cardName);
		bankCard.setCardNo(AESUtil.encode(bankcardNo));
		bankCard.setUserId(userId);
		bankCard.setTime(new Date());
		bankCard.setStatus("VERIFYING");
		bankCard.setBankCardType("储蓄卡");
		bankCard.setPaymentId("xmbank");
		// 封装存管通reqData
		GeneratorUserAccountJSON json = new GeneratorUserAccountJSON();
		json.setBankcardNo(bankcardNo);
		if (StringUtils.isNoneBlank(mobile)) {
			bankCard.setBankMobile(AESUtil.encode(mobile));
			json.setMobile(mobile);
		}
		json.setSource(terminalType);
		json.setPlatformUserNo(userId);
		json.setRequestNo(requestNo);
		json.setCallbackUrl(NotifyURL.BYBC);
		bankCardService.quickBindingCard(bankCard);
		return trusteeshipService
				.create(json, TrusteeshipServer.PERSONAL_BIND_BANKCARD,
						BusinessEnum.bindcard);
	}

	@Override
	public void bindCardCallback(GeneratorUserAccountJSON respData) {
		try {
			if (respData == null) {
				log.errLog("绑卡服务器通知处理", "响应数据为空");
			}
			if (respData.getCode().equals("0")) {
				// 扣取鉴权手续费
				try {
					platformAccountService.transferOut(
							PlatformAccountEnum.PLATFORM_SYS, 1,
							BusinessEnum.bindcard, "鉴权手续费",
							respData.getRequestNo());
				} catch (PlatformAccountException e) {
					log.errLog("绑定银行卡",
							"userId:" + respData.getPlatformUserNo()
									+ "，平台余额不足，鉴权手续费扣取失败");
				}
				BankCard bankCard = bankCardService.getByPaymentNo(respData
						.getRequestNo());
				if (bankCard != null) {
					bankCard.setBankMobile(AESUtil.encode(respData.getMobile()));
					bankCard.setStatus("VERIFIED");
					bankCardService.update(bankCard);
				} else {
					log.errLog("绑卡服务器通知处理",
							"userId:" + respData.getPlatformUserNo()
									+ "的绑卡记录为空", 1);
				}
			} else {
				log.errLog("绑卡服务器通知处理",
						"userId:" + respData.getPlatformUserNo() + ","
								+ respData);
			}
		} catch (Exception e) {
			log.errLog("绑卡服务器通知处理", "userId:" + respData.getPlatformUserNo()
					+ "," + e);
		}
	};

	@Override
	public Generator resetPassword(String userId, TerminalEnum terminalType)
			throws UserInfoException, UserAccountException {
		// 已经在aop中校验了user和useraccount是否存在和有效
		GeneratorJSON json = new GeneratorJSON();
		json.setSource(terminalType);
		json.setRequestNo(IdUtil.generateId(ToType.RPWD));
		json.setPlatformUserNo(userId);
		json.setCallbackUrl(NotifyURL.RPWD);
		return trusteeshipService.create(json,
				TrusteeshipServer.RESET_PASSWORD, BusinessEnum.reset_password);
	}

	@Override
	public void resetPasswordCallback(GeneratorJSON respData) {
		try {
			if (respData == null) {
				log.errLog("修改密码服务器通知处理", "响应数据为空");
			}
			if ("0".equals(respData.getCode())) {
				// 无本地数据处理
			} else {
				log.errLog("修改密码服务器通知处理", respData.toJSON());
			}
		} catch (Exception e) {
			log.errLog("修改密码服务器通知处理", "userId:" + respData.getPlatformUserNo()
					+ "," + e);
		}
	};

	@Override
	public String unBindCard(String userId) throws UserInfoException,
			UserAccountException {
		// 获取解绑中的银行卡信息
		BankCard card = bankCardService.getUnbindingCard(userId);
		if (card == null) {
			log.errLog("解绑卡", "userId:" + userId + "不存在解绑中的银行卡");
			throw new UserAccountException(ErrorCode.UnbindingCardNotFound);
		}
		String cancalBandTime = card.getCancelBandDingTime();
		// 封装解绑（直连）reqData
		GeneratorJSON json = new GeneratorJSON();
		json.setRequestNo(IdUtil.generateId(ToType.TUBC));
		json.setPlatformUserNo(userId);
		Generator data = trusteeshipService.execute(json,
				TrusteeshipServer.UNBIND_BANKCARD_DIRECT, GeneratorJSON.class,
				BusinessEnum.unbindcard);
		if (data != null && data.getRespData() != null
				&& "0".equals(data.getRespData().getCode())) {
			card.setDeleteBankCard("delete");
			card.setCancelStatus("解绑完成");
			card.setCancelBandDingTime(DateUtil.DateToString(new Date(),
					DateStyle.YYYY_MM_DD_HH_MM_SS));
			bankCardService.updateAllValidCard(card);
			// TODO 解绑成功，发送短信（已增加）
			try {
				User user = userService.get(userId);
				String cardNo = AESUtil.decode(card.getCardNo());
				String last4CardNo = cardNo.substring(cardNo.length() - 4);
				smsHttpClient.sendSms(
						user.getMobileNumber(),
						user.getRealname() + ","
								+ DateUtil.getDate(cancalBandTime) + ","
								+ last4CardNo, "unbindcard_success");
			} catch (Exception e) {
				log.errLog("解绑卡", "userId：" + data.getPlatformNo()
						+ "解绑银行卡发送短信失败，" + e);
			}
			return "解绑成功";
		} else {
			log.errLog("解绑卡", "userId：" + data.getPlatformNo() + "解绑银行卡失败，"
					+ data);
			throw new UserAccountException(ErrorCode.UnbindCardFail);
		}
	}

	@Override
	public Generator modifyMobile(String userId, TerminalEnum terminalType)
			throws UserInfoException, UserAccountException {

		GeneratorUserAccountJSON json = new GeneratorUserAccountJSON();
		json.setSource(terminalType);
		json.setRequestNo(IdUtil.generateId(ToType.MDPN));
		json.setPlatformUserNo(userId);
		// json.setMobile(mobile);
		json.setCallbackUrl(NotifyURL.MDPN);
		return trusteeshipService.create(json, TrusteeshipServer.MODIFY_MOBILE,
				BusinessEnum.modify_mobile);
	}

	@Override
	public void modifyMobileCallback(GeneratorUserAccountJSON respData) {
		try {
			if (respData == null) {
				log.errLog("修改手机号服务器通知处理", "响应数据为空");
			}
			if ("0".equals(respData.getCode())) {
				bankCardService.updateBankMobile(respData.getMobile(),
						respData.getPlatformUserNo());
			} else {
				log.errLog("修改手机号服务器通知处理", respData.toJSON());
			}
		} catch (Exception e) {
			log.errLog("修改手机号服务器通知处理", "userId:" + respData.getPlatformUserNo()
					+ "," + e);
		}
	}

	@Override
	@Transactional
	public Generator freezeMoney(String loginId, String expiredTime,
			String description, String userId, double money) {
		GeneratorJSON json = new GeneratorJSON();
		json.setRequestNo(IdUtil.generateId(ToType.CAAT));
		json.setPlatformUserNo(userId);
		json.setAmount(money);
		Generator generator = trusteeshipService.execute(json,
				TrusteeshipServer.FREEZE, GeneratorPlatformJSON.class,
				BusinessEnum.freeze);
		String code = generator.getRespData().getCode();
		if (code.equals("0")) {
			Freeze freeze = new Freeze();
			freeze.setId(json.getRequestNo());
			freeze.setFreezeTime(new Date());
			freeze.setFreezeUserId(loginId);
			try {
				freeze.setDescription(URLDecoder.decode(description, "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			freeze.setExpiredTime(DateUtil.StringToDate(expiredTime));
			freeze.setMoney(money);
			freeze.setUserId(userId);
			freeze.setStatus(0);
			try {
				userAccountService.freeze(userId, money, BusinessEnum.freeze,
						"管理员干预冻结", "冻结:" + userId + "金额:" + money,
						freeze.getId());
			} catch (UserAccountException e) {
				log.errLog("存管冻结金额失败", "userId : " + userId + ", 冻结金额： + "
						+ money + "， 失败" + ExceptionUtils.getMessage(e));
			}
			freezeService.save(freeze);
		}
		return generator;
}

	@Override
	public Generator unfreezeMoney(String loginId,String requestNo) throws TradeException{
		Freeze freeze = freezeService.get(requestNo);
		if (freeze == null) {
			log.errLog("资金解冻", "requestNo:"+requestNo+"未找到资金冻结记录");
			throw new TradeException(ErrorCode.MoneyFreezeNotFind);
		}
		GeneratorJSON json = new GeneratorJSON();
		json.setRequestNo(IdUtil.generateId(ToType.UNFR));
		json.setOriginalFreezeRequestNo(requestNo);
		json.setAmount(freeze.getMoney());
		Generator execute = trusteeshipService.execute(json, TrusteeshipServer.UNFREEZE,GeneratorPlatformJSON.class, BusinessEnum.unfreeze);
		if (execute.getRespData().getCode().equals("0")) {
			freeze.setStatus(3);
			freeze.setUnfreezeUserId(loginId);
			freeze.setUnFreezeTime(new Date());
			try {
				userAccountService.unfreeze(freeze.getUserId(), freeze.getMoney(), BusinessEnum.unfreeze, "管理员干预解冻",  "解冻:" + freeze.getUserId() + "金额:" + freeze.getMoney(), requestNo);
				freezeService.update(freeze);
			} catch (UserAccountException e) {
				e.printStackTrace();
				log.errLog("本地解冻异常", "解冻userId："+freeze.getUserId()+"解冻金额："+freeze.getMoney()+"异常信息："+e);
			}
		}
		return execute;
	}

	@Override
	public void settingAutoInvest(AutoInvestParamter paramter)
			throws ParameterException {
		if (paramter.getMinDeadline() > paramter.getMaxDeadline()
				|| paramter.getMinRate() > paramter.getMaxRate()
				|| paramter.getMinMoney() > paramter.getMaxMoney()) {
			throw new ParameterException(ErrorCode.AutoInvestRateError);
		}
		if (paramter.getMinMoney() % 100 != 0
				|| paramter.getMaxMoney() % 100 != 0) {
			throw new ParameterException(ErrorCode.AutoInvestMoneyError);
		}
		if (paramter.getMinMoney() < 100) {
			throw new ParameterException(ErrorCode.AutoInvestMinMoneyError);
		}
		if (paramter.getMaxMoney() > 1000000) {
			throw new ParameterException(ErrorCode.AutoInvestMaxMoneyError);
		}
		AutoInvest autoInvest = new AutoInvest();
		autoInvest.setUserId(paramter.getUserId());
		autoInvest.setMinDeadline(paramter.getMinDeadline());
		autoInvest.setMinMoney(paramter.getMinMoney());
		autoInvest.setMinRate(paramter.getMinRate());
		autoInvest.setMaxDeadline(paramter.getMaxDeadline());
		autoInvest.setMaxMoney(paramter.getMaxMoney());
		autoInvest.setMaxRate(paramter.getMaxRate());
		autoInvest.setInvestMoney(paramter.getMaxMoney());
		String loanType = paramter.getLoanType();
		loanType = loanType.replace("车押宝", "车贷");
		loanType = loanType.replace("房押宝", "房贷");
		loanType = loanType.replace("企业宝", "企业贷");
		autoInvest.setLoanType(loanType);
		autoInvest.setStatus(InvestConstants.AutoInvest.Status.ON);
		autoInvestService.settingAutoInvest(autoInvest);
	}

	@Override
	public void cancelAutoInvest(String userId) {
		AutoInvest autoInvest = autoInvestService.query(userId);
		autoInvest.setStatus(InvestConstants.AutoInvest.Status.OFF);
		autoInvestService.update(autoInvest);
	}

	@Override
	public AutoInvest getAutoInvest(String userId) {
		return autoInvestService.get(userId);
	}

	@Override
	public Generator activateUserAccount(String userId, String bankCode, String bankCardNo,
			TerminalEnum terminalType) throws UserAccountException, BankCardException {
		// 验证本地数据是否需要激活
		UserAccount account = userAccountService.getUserAccount(userId);
		if(account==null){
			log.errLog("激活", "userId:"+userId+"未开户");
			throw new UserAccountException(ErrorCode.UserAccountNoOpened);
		}
		if (account.getStatus()!=0) {
			log.errLog("激活", "userId:"+userId+"已激活，无需此操作");
			throw new UserAccountException(ErrorCode.UserAccountStatusError);
		}
		
		Map<String, Object> cardInfoMap = BankMapUtil.findBankInfo(bankCardNo);
		if (cardInfoMap.get("status").equals("error")) {
			log.errLog("激活", "userId:" + userId + "输入的银行卡号:" + bankCardNo
					+ "不正确");
			throw new BankCardException(ErrorCode.BankCardValid);
		} else if (cardInfoMap.get("cardType").equals("贷记卡")) {
			log.errLog("激活", "userId:" + userId + "输入的是信用卡号:" + bankCardNo);
			throw new BankCardException(ErrorCode.CreditCardNoBind);
		}

		List<String> bankList = bankCardService.getBankCardUsableByCgt();
		boolean flag = false;
		for (String string : bankList) {
			if (string.equals(bankCode)) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			log.errLog("激活", "userId:" + userId + "，存管通不支持该行" + bankCode
					+ "绑卡");
			throw new BankCardException(ErrorCode.BankCardNotSupported);
		}

		String cardName = (String) cardInfoMap.get("name");
		String bankCardCode = BankName.getBankCode(cardName);// 银行简称
		if (!bankCode.equals(bankCardCode)) {
			log.errLog("激活", "userId:" + userId + "输入的银行卡号与所选银行不一致");
			throw new BankCardException(ErrorCode.CardNoNotIdenticalWithBankNo);
		}
		
		//查询是否有有效银行卡
		BankCard bankCard = bankCardService.getBankCardVerifedByUserId(userId);
		//查询鉴权结果
		AuthenticationResult authenticationResult = userService.getAuthenticationResultByStatus(userId, "鉴权未通过");
		boolean ChangeCardflag = false;
		if (bankCard == null || bankCard.getCardNo() == null ) {
			ChangeCardflag = true;
		} 
		if (bankCard != null && bankCard.getCardNo() != null) {
			if (authenticationResult != null) {
				ChangeCardflag = true;
			}
		}
		//调用换卡直连接口
		if (ChangeCardflag) {
			GeneratorUserAccountJSON json = new GeneratorUserAccountJSON();
			json.setRequestNo(IdUtil.generateId(ToType.CUBC));
			json.setPlatformUserNo(userId);
			json.setBankcardNo(bankCardNo);
			Generator data = trusteeshipService.execute(json,
					TrusteeshipServer.CHANGE_USER_BANKCARD, GeneratorJSON.class,
					BusinessEnum.change_user_bankcard);
			if (data != null && data.getRespData() != null
					&& "0".equals(data.getRespData().getCode())) {
				log.infoLog("未激活换卡", "userId:" + userId + "未激活换卡成功");
			} else {
				log.errLog("未激活换卡", "userId：" + userId + "未激活换卡失败，"
						+ data);
				throw new UserAccountException(ErrorCode.RequestFailTryAgain);
			}
		}
		GeneratorJSON json = new GeneratorJSON();
		json.setSource(terminalType);
		json.setRequestNo(IdUtil.generateId(ToType.ACTI));
		json.setPlatformUserNo(userId);
		json.setCallbackUrl(NotifyURL.ACTI);
		return trusteeshipService.create(json,
				TrusteeshipServer.ACTIVATE_STOCKED_USER,
				BusinessEnum.activate_account);
	}

	@Override
	@Transactional
	public void resetMobile(String userId, String newMobile, String oldMobile,
			String password, String idCardNo, String authCode)
			throws DataAlreadExistException, UserInfoException {
		User user = userService.get(userId);
		if (user == null) {
			log.errLog("修改本地手机号", "userId:"+userId+"未注册");
			throw new UserInfoException(ErrorCode.UserNoRegist);
		}
		// 不需要暴露type类型，默认是修改登陆手机号的验证码
		if (!authInfoService
				.operateAuthCode(newMobile, authCode, "resetMobile")) {
			log.errLog("修改本地手机号", "userId:"+userId+"验证码错误");
			throw new UserInfoException(ErrorCode.AuthCodeFail);
		}
		// 校验密码单独拿出来，user不返回password
		if (!StringUtils.equals(MD5Encry.Encry(password), user.getPassword())) {
			log.errLog("修改本地手机号", "userId:"+userId+"登录密码错误");
			throw new UserInfoException(ErrorCode.LoginPasswordError);
		}
		if (!StringUtils.equals(oldMobile, user.getMobileNumber())) {
			log.errLog("修改本地手机号", "userId:"+userId+"原手机号错误");
			throw new UserInfoException(ErrorCode.OldMobileError);
		}
		if (!StringUtils.equals(idCardNo, user.getIdCard())) {
			log.errLog("修改本地手机号", "userId:"+userId+"身份证号错误");
			throw new UserInfoException(ErrorCode.IdCardNoError);
		}
		// 校验格式
		if (!(RegexInput.checkMobilePhone(newMobile))) {
			log.errLog("修改本地手机号", "userId:"+userId+"手机号格式不正确");
			throw new UserInfoException(ErrorCode.MobileFormatError);
		}
		if (userService.getUserByMobileNumber(newMobile) != null) {
			log.errLog("修改本地手机号", "userId:"+userId+"手机号已存在");
			throw new DataAlreadExistException(ErrorCode.MobileExist);
		}
		RedPacket redPacket = new RedPacket();
		redPacket.setMobileNumber(newMobile);
		List<RedPacket> list = redPacketService
				.operateRedPacketDetails(redPacket);
		if (!list.isEmpty() && list.size() > 0) {
			log.errLog("修改手机号", "userId" + userId + "，修改的手机号在红包表中存在"
					+ newMobile);
			throw new DataAlreadExistException(ErrorCode.MobileExist);
		}
		redPacketService.updateDetailMobileNumber(user.getMobileNumber(),
				newMobile);
		redPacketService.updateShareMobileNumber(user.getMobileNumber(),
				newMobile);
		userService.updateReferrerMobileNumber(user.getMobileNumber(),
				newMobile);
		user.setMobileNumber(newMobile);
		userService.updateUserMobileNumber(user);
	}

	@Override
	public void unBindCardApply(String userId, String imgData)
			throws UserInfoException {
		BankCard card = bankCardService.getBankCardVerifedByUserId(userId);
		if (card == null) {
			log.errLog("解绑卡申请（前端，上传证件照片）", "userId:" + userId + "不存在绑定的银行卡");
			throw new UserInfoException(ErrorCode.BankCardNoFound);
		}
		List<UnbindCardInfo> list = bankCardService
				.getUnbindCardInfo(userId, 0);
		if (list != null && list.size() > 0) {
			log.errLog("解绑卡申请（前端，上传证件照片）", "userId:" + userId + "已存在待审核的证件照片");
			throw new UserInfoException(ErrorCode.UnbindingCardExist);
		}
		try {
			byte[] imgStream = Base64.decodeBase64(imgData);
			InputStream in = new ByteArrayInputStream(imgStream);
			String key = userId + "_" + IdUtil.randomUUID() + ".jpg";
			String imgPath = filePath + "/" + key;
			String result = OssUtil.putObject(imgPath, in);
			log.infoLog("解绑卡申请（前端，上传证件照片）", "图片上传结果：" + result);
			UnbindCardInfo unbindCard = new UnbindCardInfo();
			unbindCard.setUserId(userId);
			unbindCard.setStatus(0);
			unbindCard.setImgPath(imgPath);
			bankCardService.insertUnbindCardInfo(unbindCard);
			BankCard bankCard = new BankCard();
			bankCard.setUserId(userId);
			bankCard.setCancelStatus("解绑中");
			bankCard.setCancelBandDingTime(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			bankCardService.updateAllValidCard(bankCard);
			try {
				User user = userService.get(userId);
				String cardNo = AESUtil.decode(card.getCardNo());
				String last4CardNo = cardNo.substring(cardNo.length() - 4);
				smsHttpClient.sendSms(user.getMobileNumber(),
				user.getRealname()+","+DateUtil.getDate(new Date())+","+last4CardNo,
				"unbindcard_apply");
			} catch (Exception e) {
				log.errLog("解绑卡申请（前端，上传证件照片）", "userId：" + userId + "发送短信失败，"
						+ e);
			}
		} catch (Exception e) {
			log.errLog("解绑卡申请（前端，上传证件照片）", "userId:" + userId + "," + e);
			throw new UserInfoException(ErrorCode.UnbindCardFail);
		}
	}

	@Override
	public Generator queryUserInformation(String userId, String type, int handle) throws UserInfoException {
		// 查询存管通用户信息
		GeneratorJSON json = new GeneratorJSON();
		json.setPlatformUserNo(userId);
		Generator generator = trusteeshipService.execute(json,
				TrusteeshipServer.QUERY_USER_INFORMATION,
				GeneratorUserAccountJSON.class);
		if (handle == 1) {
			handleLocalBusiness(generator, type);
		}
		return generator;
	}

	private void handleLocalBusiness(Generator generator, String type) throws UserInfoException {
		if (generator == null) {
			log.errLog("本地业务处理", "存管通响应数据为空");
			throw new UserInfoException(ErrorCode.CgtRespDataIsNull);
		}
		GeneratorUserAccountJSON data = (GeneratorUserAccountJSON) generator
				.getRespData();
		if (BusinessEnum.create_account.equals(type)) {
			this.createAccountCallback(data);
		} 
	}

	@Override
	public Map<String, Object> userInfoForActive(String userId) throws UserInfoException {
		Map<String, Object> data = new HashMap<>();
		User user = userService.get(userId);
		//bankCode、bankcardNo、idCardNo、realname
		if (user != null) {
			data.put("realname", user.getRealname());
			data.put("idCardNo", user.getIdCard());
		} else {
			log.errLog("查询用户信息", "userId:"+ userId +"未开户");
			throw new UserInfoException(ErrorCode.UserNoRegist);
		}
		//查询鉴权结果
		AuthenticationResult authenticationResult = userService.getAuthenticationResultByStatus(userId, "鉴权未通过");
		BankCard card = bankCardService.getBankCardVerifedByUserId(userId);  
		if (card != null ) {
			if (authenticationResult == null) {
				data.put("bankCode", card.getBank());
				data.put("bankcardNo", AESUtil.decode(card.getCardNo()));
			} else {
				data.put("bankCode", null);
				data.put("bankcardNo", null);
			}
		} else {
			data.put("bankCode", null);
			data.put("bankcardNo", null);
		}
		return data;
	}

}