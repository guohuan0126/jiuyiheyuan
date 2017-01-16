package com.duanrong.drpay.trusteeship.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import util.AESUtil;
import util.ArithUtil;
import util.DateUtil;
import util.Log;
import util.SmsHttpClient;
import base.error.ErrorCode;
import base.exception.BankCardException;
import base.exception.ParameterException;
import base.exception.PaymentAccountException;
import base.exception.TradeException;
import base.exception.UserAccountException;

import com.duanrong.drpay.business.account.PlatformAccountEnum;
import com.duanrong.drpay.business.account.model.BankCard;
import com.duanrong.drpay.business.account.service.BankCardService;
import com.duanrong.drpay.business.account.service.PlatformAccountService;
import com.duanrong.drpay.business.account.service.UserAccountService;
import com.duanrong.drpay.business.payment.model.PaymentBankInfo;
import com.duanrong.drpay.business.payment.model.PaymentChannel;
import com.duanrong.drpay.business.payment.model.Recharge;
import com.duanrong.drpay.business.payment.model.WithdrawCash;
import com.duanrong.drpay.business.payment.service.ChannelMatchingService;
import com.duanrong.drpay.business.payment.service.PaymentBankChannelService;
import com.duanrong.drpay.business.payment.service.RechargeService;
import com.duanrong.drpay.business.payment.service.WithdrawCashService;
import com.duanrong.drpay.business.user.UserConstants;
import com.duanrong.drpay.business.user.model.User;
import com.duanrong.drpay.business.user.service.UserService;
import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.config.ConfigConstant;
import com.duanrong.drpay.config.IdUtil;
import com.duanrong.drpay.config.ToType;
import com.duanrong.drpay.jsonservice.handler.TerminalEnum;
import com.duanrong.drpay.trusteeship.constants.TrusteeshipServer;
import com.duanrong.drpay.trusteeship.helper.model.ExpectPayCompany;
import com.duanrong.drpay.trusteeship.helper.model.Generator;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorRechargeJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorWithdrawJSON;
import com.duanrong.drpay.trusteeship.helper.model.NotifyURL;
import com.duanrong.drpay.trusteeship.helper.model.PlatformType;
import com.duanrong.drpay.trusteeship.helper.model.RechargeWay;
import com.duanrong.drpay.trusteeship.helper.model.WithdrawForm;
import com.duanrong.drpay.trusteeship.helper.model.WithdrawType;
import com.duanrong.drpay.trusteeship.helper.service.TrusteeshipService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipPaymentService;

@Service
public class TrusteeshipPaymentServiceImpl implements TrusteeshipPaymentService {

	@Resource
	Log log;

	@Resource
	RechargeService rechargeService;

	@Resource
	ChannelMatchingService channelMatchingService;

	@Resource
	TrusteeshipService trusteeshipService;

	@Resource
	BankCardService bankCardService;

	@Resource
	WithdrawCashService withdrawCashService;

	@Resource
	UserAccountService userAccountService;

	@Resource
	PlatformAccountService platformAccountService;

	@Resource
	SmsHttpClient smsHttpClient;
	
	@Resource
	UserService userService;
	
	@Resource
	PaymentBankChannelService paymentBankChannelService;
	
	@Override
	@Transactional
	public Generator recharge(String userId, Double money, String rechargeWay,
			String source, TerminalEnum terminalType) throws ParameterException, BankCardException {
		// FIXME 原最小为1，测试改为0.01
		if (money < 0.01) {
			log.errLog("充值", "userId:" + userId + "的充值金额小于0.01块");
			throw new ParameterException(ErrorCode.RechargeOutMoney);
		}
		String bankCode = null;
		Map<String, Object> channel = null;
		if (PlatformType.SYS_GENERATE_001.name().equals(userId) || PlatformType.SYS_GENERATE_002.name().equals(userId)) {
			if (!"gateway".equals(rechargeWay)) {
				log.errLog("充值", "平台账户充值方式只能为网银！");
				throw new ParameterException(ErrorCode.RechargeWayWrong);
			}
		} else {
			// 查询用户绑定的银行卡编码
			List<BankCard> bankCards = bankCardService.getValidBankCardByUserId(
					userId, false);
			if (bankCards == null || bankCards.size() <= 0 || bankCards.get(0) == null) {
				log.errLog("充值", "userId:" + userId + "无有效银行卡");
				throw new BankCardException(ErrorCode.BankCardNoFound);
			}
			if ("quick".equals(rechargeWay)) {
				bankCode = bankCards.get(0).getBank();
				// 根据充值金额获取支付公司
				channel = channelMatchingService
						.findChannelByMoney(userId, money, source);
				String status = (String) channel.get("status");
				if ("success".equals(status)) {
					String channelType = (String) channel.get("channelType");
					String cardNo = (String) channel.get("cardNo");
					if (StringUtils.isBlank(channelType) || StringUtils.isBlank(cardNo)) {
						log.errLog("充值", "userId:" + userId + "尚未绑定银行卡");
						throw new BankCardException(ErrorCode.BankCardNoFound);
					}
				} else if ("noSurplusMoney".equals(status)) {
					log.errLog("充值", "userId:" + userId + "充值额度已用完");
					throw new BankCardException(ErrorCode.RechargeNoAmount);
				} else if ("noChannel".equals(status)) {
					log.errLog("充值", "userId:" + userId + "未找到充值渠道");
					throw new BankCardException(ErrorCode.RechargeNotFindChannel);
				} else {
					log.errLog("充值", "userId:" + userId + "充值失败");
					throw new BankCardException(ErrorCode.RechargeFail);
				}
			} 
		}
		
		// 增加充值记录
		Recharge recharge = new Recharge();
		GeneratorRechargeJSON json = new GeneratorRechargeJSON();
		recharge.setActualMoney(money);
		recharge.setUserId(userId);
		recharge.setType(rechargeWay);
		recharge.setRechargeWay(source);
		if ("quick".equals(rechargeWay)) {
			recharge.setCardNo(AESUtil.encode(channel.get("cardNo").toString()));
			recharge.setPaymentId(channel.get("channelType").toString());
		} else {
			// TODO 网银设置充值渠道
			recharge.setPaymentId(ExpectPayCompany.UCFPAY.name());
		}
		String id = IdUtil.generateId(ToType.RECH);
		recharge.setId(id);
		// 等待付款
		recharge.setStatus(UserConstants.RechargeStatus.WAIT_PAY);
		recharge.setFee(0D);
		rechargeService.insert(recharge);
		// 封装请求存管通参数
		RechargeWay ctgRechargeWay = null;
		if ("gateway".equals(rechargeWay)) {
			ctgRechargeWay = RechargeWay.WEB;
		} else {
			ctgRechargeWay = RechargeWay.SWIFT;
		}
		json.setPlatformUserNo(userId);
		json.setRequestNo(id);
		json.setAmount(money);
		if ("quick".equals(rechargeWay)) {
			json.setExpectPayCompany(ExpectPayCompany.valueOf(channel.get(
					"channelType").toString()));
		} else {
			// TODO 网银设置充值渠道
			json.setExpectPayCompany(ExpectPayCompany.UCFPAY);
		}
		json.setRechargeWay(ctgRechargeWay);
		json.setSource(terminalType);
		if (RechargeWay.SWIFT.name().equals(ctgRechargeWay.name())) {
			json.setBankcode(bankCode);
		}
		json.setCallbackUrl(NotifyURL.RECH);
		return trusteeshipService.create(json, TrusteeshipServer.RECHARGE,
				BusinessEnum.recharge);
	}

	@Override
	public void rechargeCallback(GeneratorRechargeJSON respData) {
		try {
			if (respData == null) {
				log.errLog("充值服务器通知处理", "响应数据为空");
				throw new TradeException(ErrorCode.CgtRespDataIsNull);
			}
			if (respData.getCode().equals("0")) {
				// SUCCESS 表示支付成功， FAIL 表示支付失败，PENDDING 表示支付中
				if (respData.getStatus().equals("SUCCESS")) {
					// 充值手续费计算
					rechargeService
							.rechargeSuccess(respData.getRequestNo(), "");
				} else if(respData.getStatus().equals("PENDDING")){
					log.errLog("充值支付中", "userId:" + respData.getPlatformUserNo()+","+respData.toJSON());
				} else if(respData.getStatus().equals("FAIL")){
					Recharge recharge = new Recharge();
					recharge.setId(respData.getRequestNo());
					recharge.setStatus("fail");
					rechargeService.update(recharge);
					log.errLog("充值失败", "userId:" + respData.getPlatformUserNo()+","+respData.toJSON(), 1);
				}
			} else {
				log.errLog("充值服务器通知处理", "userId" + respData.getPlatformUserNo()+","+respData.toJSON());
			}
		} catch (Exception e) {
			log.errLog("充值服务器通知处理", e);
		}
	}

	@Override
	public Generator withdraw(String userId, Double money, TerminalEnum terminalType)
			throws UserAccountException {
		List<BankCard> bankCards = bankCardService.getValidBankCardByUserId(
				userId, false);
		if (CollectionUtils.isEmpty(bankCards)) {
			throw new UserAccountException(ErrorCode.BankCardNoFound);
		} else {
			WithdrawCash withdrawCash = new WithdrawCash();
			String id = IdUtil.generateId(ToType.WICA);
			withdrawCash.setUserId(userId);
			withdrawCash.setActualMoney(money);
			Double balance = userAccountService.getUserAccount(
					withdrawCash.getUserId()).getAvailableBalance();
			if (ArithUtil.round(withdrawCash.getActualMoney(), 2) > ArithUtil
					.round(balance, 2)) {
				log.errLog("提现错误", withdrawCash.getUserId()
						+ ":的提现金额大于账户余额,提现金额" + withdrawCash.getActualMoney()
						+ "余额：" + balance);
				throw new UserAccountException(ErrorCode.BalanceToLow);
			}
			withdrawCash.setCashFine(0D);
			withdrawCash.setAccount("借款账户");
			withdrawCash.setId(id);
			withdrawCash.setTime(new Date());
			withdrawCash.setFee(0D);
			withdrawCash.setBankCardId(bankCards.get(0).getId());
			withdrawCash.setWithdrawType("NORMAL");
			withdrawCash.setPaymentId("XMbank");
			withdrawCash.setStatus(UserConstants.WithdrawStatus.WAIT_VERIFY);
			withdrawCashService.insert(withdrawCash);

			GeneratorWithdrawJSON json = new GeneratorWithdrawJSON();
			json.setPlatformUserNo(userId);
			json.setRequestNo(id);
			json.setAmount(money);
			json.setCallbackUrl(NotifyURL.WICA);
			json.setWithdrawType(WithdrawType.NORMAL);
			json.setWithdrawForm(WithdrawForm.IMMEDIATE);
			json.setSource(terminalType);
			return trusteeshipService.create(json, TrusteeshipServer.WITHDRAW,
					BusinessEnum.withdraw_cash);
		}
	}

	@Override
	public void withdrawCallback(GeneratorWithdrawJSON respData){
		try {
			String code = respData.getCode();
			String requestNo = respData.getRequestNo();
			String status = respData.getStatus();
			if (StringUtils.isBlank(code) || StringUtils.isBlank(requestNo)
					|| StringUtils.isBlank(status)) {
				log.errLog("提现Callback", "响应参数为空");
			}
			WithdrawCash withdrawCash = withdrawCashService.getWithLock(requestNo);
			if (StringUtils.equals("0", code)) {
				User user = userService.get(respData.getPlatformUserNo());
				Double actualMoney = withdrawCash.getActualMoney();
				if (StringUtils.equals("CONFIRMING", status)) {// 待确认
					//当提现为提现预处理，确认提现，才返回此状态
				} else if (StringUtils.equals("ACCEPT", status)
						|| StringUtils.equals("REMITING", status)) {
					withdrawCash.setRemitTime(new Date());
					withdrawCashService.successWithdraw(withdrawCash);
					// TODO 提现申请成功短信(已添加)
					try {
						Date date = withdrawCashService
								.getArrivalDate(new Date());			
						smsHttpClient.sendSms(user.getMobileNumber(), actualMoney+","+DateUtil.getDate(date), "withdraw");
					} catch (Exception e) {
						log.errLog("提现申请成功", "userId:"+respData.getPlatformUserNo()+"短信发送失败，"+e);
					}
				} else if (StringUtils.equals("SUCCESS", status)) {
					withdrawCash.setCompletedTime(new Date());
					withdrawCashService.update(withdrawCash);
					// TODO 提现到账短信通知（已增加）
					try {
						BankCard card = bankCardService.getBankCardVerifedByUserId(respData.getPlatformUserNo());
						String cardNo = AESUtil.decode(card.getCardNo());
						String last4CardNo = cardNo.substring(cardNo.length() - 4);
						Date date = withdrawCash.getRemitTime();
						smsHttpClient.sendSms(user.getMobileNumber(), user.getRealname()+","+DateUtil.getDate(date)+","+actualMoney+","+last4CardNo, "withdraw_success");
					} catch (Exception e) {
						log.errLog("提现到账", "userId:"+respData.getPlatformUserNo()+"短信发送失败，"+e);
					}
				} else if (StringUtils.equals("ACCEPT_FAIL", status)
						|| StringUtils.equals("FAIL", status)) {
					withdrawCash.setStatus(UserConstants.WithdrawStatus.FAIL);
					withdrawCashService.update(withdrawCash);
					log.errLog("提现失败", "流水号："+requestNo+"，状态为："+status,1);
				}else if(StringUtils.equals("PAUSE", status)){//暂停
					log.errLog("提现暂停", "流水号："+requestNo,1);
				}
			} else {
				log.errLog(this.getClass().getName() + ".withdrawCallback()",
						respData.toJSON());
			}
		} catch (Exception e) {
			log.errLog(this.getClass().getName() + ".withdrawCallback()",
					respData.toJSON());
		}
	}

	@Override
	public Map<String, Object> findChannelByMoney(String userId, String source) {
		Map<String, Object> appData = this.channelMatchingService
				.findChannelByMoney(userId, 0.0, source);
		String reminder = "1、充值最低"
				+ appData.get("minMoney")
				+ "元起，实时到账，免收手续费；\n2、仅可使用储蓄卡进行充值，严禁信用卡充值、套现、洗钱等行为；"
				+ "\n3、同一张银行卡在网上进行购物支付、转账、还款缴费等场景限额共享；\n4、如果您超过1小时后仍未收到充值款项，请与我们的客服联系（400-062-1008）。";
		appData.put("reminder", reminder);
		System.out.println(appData.get("status") + "  "
				+ appData.get("status").equals("noChannel"));
		List<PaymentChannel> channels = paymentBankChannelService.getValidChannels();
		StringBuilder channelMsg = new StringBuilder();
		if (channels != null && channels.size() > 0) {
			for (PaymentChannel channel : channels) {
				channelMsg.append(channel.getName()+"、");
			}
		}
		String msg = channelMsg.substring(0, channelMsg.length()-1).toString();
		appData.put("describ", "厦门银行选择"+msg+"提供支付服务，您在支付过程可能会收到以上通道发送的验证短信，请放心使用");
		if (appData.get("status").equals("noChannel")) {
			appData.put("surplusMoneyShow", appData.get("bankName")
					+ "，暂不支持快捷，请使用网银或重新绑卡");
		} else if (appData.get("status").equals("noSurplusMoney")) {
			appData.put("surplusMoneyShow", appData.get("bankCardShow")
					+ "，当天快捷充值额度已用完");
		} else if (appData.get("status").equals("notBankCard")) {
			appData.put("surplusMoneyShow", "您未添加银行卡，请绑卡");
		}
		return appData;
	}

	@Override
	public List<BankCard> getValidBankCardByUserId(String userId) {
		List<BankCard> list = bankCardService.getValidBankCardByUserId(userId,
				false);
		if (!CollectionUtils.isEmpty(list)) {
			for (BankCard bankCard : list) {
				if (StringUtils.isNotBlank(bankCard.getCardNo())) {
					try {
						String cardNo = AESUtil.decode(bankCard.getCardNo());
						bankCard.setCardNo(cardNo.substring(0, 4) + "********"
								+ cardNo.substring(cardNo.length() - 4));
					} catch (Exception e) {
					}
				}
				if (StringUtils.isNotBlank(bankCard.getBankMobile())) {
					try {
						String mobile = AESUtil
								.decode(bankCard.getBankMobile());
						bankCard.setBankMobile(mobile.substring(0, 3) + "****"
								+ mobile.substring(mobile.length() - 4));
					} catch (Exception e) {
					}
				} else {
					User user = userService.get(userId);
					if (user != null) {
						String mobile = user.getMobileNumber();
						bankCard.setBankMobile(mobile.substring(0, 3) + "****"
								+ mobile.substring(mobile.length() - 4));
					}
				}
				PaymentBankInfo bankInfo = bankCardService
						.getBankInfoByCode(bankCard.getBank());
				if (bankInfo != null)
					bankCard.setBankImgUrl(ConfigConstant.OSS_SERVER
							+ bankInfo.getLogo());
			}
		}
		return list;
	}

	@Override
	public Map<String, String> getArrivalDate(String userId, Double money)
			throws PaymentAccountException {
		List<BankCard> bankCards = bankCardService.getValidBankCardByUserId(
				userId, false);
		if (CollectionUtils.isEmpty(bankCards)) {
			throw new PaymentAccountException(ErrorCode.BankCardNoFound);
		}
		Date date = withdrawCashService.getArrivalDate(new Date());
		Map<String, String> map = new HashMap<String, String>();
		map.put("describe",
				"您本次提现预计将于" + DateUtil.DateToString(date, "yyyy年M月d日") + "到账");
		return map;
	}

	@Override
	public GeneratorJSON rechargeWithRollback(String requestNo)
			throws TradeException, UserAccountException {
		WithdrawCash withdrawCash = withdrawCashService.get(requestNo);
		if (withdrawCash == null)
			throw new TradeException(ErrorCode.WithdrawCashNoFind);
		GeneratorRechargeJSON json = new GeneratorRechargeJSON();
		json.setPlatformUserNo(withdrawCash.getUserId());
		json.setRequestNo(IdUtil.generateId(ToType.RECH));
		json.setAmount(withdrawCash.getActualMoney());
		json.setRechargeWay(RechargeWay.BACKROLL_RECHARGE);
		json.setWithdrawRequestNo(requestNo);
		Generator generator = trusteeshipService.execute(json,
				TrusteeshipServer.BACKROLL_RECHARGE, GeneratorJSON.class);
		if (generator.getRespData() != null) {
			GeneratorJSON generatorJson = generator.getRespData();
			if (generatorJson.getCode().equals("0")) {
				withdrawCash.setStatus(UserConstants.WithdrawStatus.FAIL);
				withdrawCashService.update(withdrawCash);
				userAccountService.transferIn(withdrawCash.getUserId(),
						withdrawCash.getActualMoney(), BusinessEnum.refund,
						"提现失败", "提现Id: " + requestNo + "提现失败, 资金回退",
						generatorJson.getRequestNo());
				platformAccountService.transferIn(
						PlatformAccountEnum.PLATFORM_SYS,
						withdrawCash.getFee(), BusinessEnum.refund, requestNo
								+ "提现失败，手续费回退", generatorJson.getRequestNo());
			}
		}
		return generator.getRespData();
	}
}
