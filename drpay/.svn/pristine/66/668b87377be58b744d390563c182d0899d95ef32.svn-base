package com.duanrong.drpay.trusteeship.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.ArithUtil;
import util.Log;
import util.SmsHttpClient;
import base.error.ErrorCode;
import base.exception.DataAlreadExistException;
import base.exception.PlatformAccountException;
import base.exception.TradeException;
import base.exception.UserAccountException;
import base.exception.UserInfoException;

import com.duanrong.drpay.business.account.PlatformAccountEnum;
import com.duanrong.drpay.business.account.service.PlatformAccountService;
import com.duanrong.drpay.business.account.service.UserAccountService;
import com.duanrong.drpay.business.platformtransfer.model.PlatformTransfer;
import com.duanrong.drpay.business.platformtransfer.service.PlatformTransferService;
import com.duanrong.drpay.business.transaction.model.TransactionAuthorization;
import com.duanrong.drpay.business.transaction.service.GeneralTransactionService;
import com.duanrong.drpay.business.user.service.UserService;
import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.jsonservice.handler.TerminalEnum;
import com.duanrong.drpay.trusteeship.constants.TrusteeshipServer;
import com.duanrong.drpay.trusteeship.helper.model.BizType;
import com.duanrong.drpay.trusteeship.helper.model.Generator;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorDetailJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorPreTransactionJSON;
import com.duanrong.drpay.trusteeship.helper.model.NotifyURL;
import com.duanrong.drpay.trusteeship.helper.model.PlatformType;
import com.duanrong.drpay.trusteeship.helper.service.TrusteeshipService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipFundTransferService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipTradeService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipTransactionQueryService;
import com.duanrong.util.jedis.DRJedisDLock;

@Service
public class TrusteeshipFundTransferServiceImpl implements TrusteeshipFundTransferService {

	@Resource
	UserAccountService userAccountService;

	@Resource
	PlatformAccountService platformAccountService;

	@Resource
	UserService userService;

	@Resource
	TrusteeshipService trusteeshipService;

	@Resource
	PlatformTransferService platformTransferService;

	@Resource
	TrusteeshipTradeService trusteeshipTradeService;
	
	@Resource
	GeneralTransactionService generalTransactionService;

	@Resource
	TrusteeshipTransactionQueryService trusteeshipTransactionQueryService;
	
	@Resource
	SmsHttpClient smsHttpClient;

	@Resource
	Log log;

	private static final String REWARD_LOCK = "reward";
	
	//平台营销款预处理前缀
	private static final String PRET = "PRET";

	org.apache.commons.logging.Log logger = LogFactory.getLog(TrusteeshipFundTransferService.class);

	@Override
	public String sendRewardDirect(String userId, String requestNo, double rewardMoney, BusinessEnum businessType,
			String loanId, String info, String remarks)
			throws Exception {
		if (DRJedisDLock.getDLock(REWARD_LOCK + requestNo, requestNo)) {
			try {
				// 保存平台划款数据
				businessType = businessType == null ? BusinessEnum.reward : businessType;
				info = StringUtils.isBlank(info) ? "平台奖励" : info;
				PlatformTransfer platformTransfer = platformTransferService.prepare(requestNo, userId, rewardMoney,
						businessType.name(), loanId, remarks);
				if(platformTransfer.getStatus().equals("等待平台划款")){
					GeneratorJSON generatorJson = this.sendReward(platformTransfer, businessType, info);			
					if (generatorJson != null && generatorJson.getCode().equals("0")) {		
						platformTransfer.setStatus("平台划款处理中");
						platformTransferService.update(platformTransfer);
						if (sendRewardConfirm(platformTransfer, info)) {
							return "success";
						}else {
							log.errLog("平台奖励确认失败",
									"userId: " + userId + ", rewardType: " + businessType.toString() + ", 奖励名称: " + info + ", requestNo: " 
							+ requestNo + "发送确认失败, 请及时处理\n 平台确认返回" + generatorJson.getDescription(), 1);
						}
						return "sended";			
					} else {
						platformAccountService.unfreeze(PlatformAccountEnum.PLATFORM_MARKETING, rewardMoney, businessType,
								info, requestNo);
						platformTransfer.setStatus("平台划款失败");
						platformTransferService.update(platformTransfer);
						log.errLog("平台奖励发送失败", "userId: " + userId + ", rewardType: " + businessType.toString() + ", 奖励名称: "
								+ info + ", requestNo: " + requestNo + "发送预处理失败, 请及时处理\n 平台预处理返回code: " 
								+ generatorJson.getCode() + ",\t desc: " + generatorJson.getDescription(), 1);
					}
				}else if(platformTransfer.getStatus().equals("平台划款处理中")){
					//TODO 单笔业务查询
					Generator generator = trusteeshipTransactionQueryService.queryTransaction(PRET + requestNo, BusinessEnum.reward);
					GeneratorJSON json = generator.getRespData();
					if("0".equals(json.getCode()) && json.getDetails() != null){
						GeneratorDetailJSON detail = json.getDetails().get(0);
						if("INIT".equals(detail.getStatus())) return "sended";
						else if("FREEZED".equals(detail.getStatus())){
							if (sendRewardConfirm(platformTransfer, info)) {
								return "success";
							}else{
								//解冻平台预处理金额
								GeneratorJSON generatorJson = trusteeshipTradeService.transactionCancle(PRET + requestNo, rewardMoney);
								if(("0").equals(generatorJson.getCode())){
									//解冻金额，重新发起
									platformAccountService.unfreeze(PlatformAccountEnum.PLATFORM_MARKETING, rewardMoney, businessType,
											info, requestNo);
									platformTransfer.setStatus("平台划款失败");
									platformTransferService.update(platformTransfer);
									return "fail";
								}
								return "sended";
							}
						}
					}
					//解冻金额，重新发起
					platformAccountService.unfreeze(PlatformAccountEnum.PLATFORM_MARKETING, rewardMoney, businessType,
							info, requestNo);
					platformTransfer.setStatus("平台划款失败");
					platformTransferService.update(platformTransfer);
					return "fail";					
				}	
			} catch (Exception e) {
				log.errLog("平台奖励发送失败", "userId: " + userId + ", rewardType: " + businessType.toString() + ", 奖励名称: "
						+ info + ", requestNo: " + requestNo + "发送失败, 请及时处理\n" + ExceptionUtils.getMessage(e), 1);
				throw e;
			} finally {
				DRJedisDLock.releaseDLock(REWARD_LOCK + requestNo, requestNo);
			}
		}
		return "fail";
	}

	/**
	 * 平台奖励预处理
	 * 
	 * @return
	 * @throws PlatformAccountException
	 */
	private GeneratorJSON sendReward(PlatformTransfer platformTransfer, BusinessEnum businessType, String info)
			throws PlatformAccountException {	
		try {
			// 冻结本地营销账户
			platformAccountService.freeze(PlatformAccountEnum.PLATFORM_MARKETING, platformTransfer.getActualMoney(),
					businessType, info, platformTransfer.getOrderId());
		} catch (Exception e) {
			log.errLog("平台奖励发送失败",
					"userId: " + platformTransfer.getUsername() + ", rewardType: " + businessType.toString()
							+ ", 奖励名称: " + info + ", requestNo: " + platformTransfer.getOrderId() + "发送失败, 请及时处理\n"
							+ ExceptionUtils.getMessage(e),
					1);
			throw e;
		}
		GeneratorPreTransactionJSON json = new GeneratorPreTransactionJSON();
		json.setRequestNo(PRET + platformTransfer.getOrderId());// 预处理流水号固定是业务流水号+前缀PRET
		json.setPlatformUserNo(PlatformType.SYS_GENERATE_002.name());
		json.setBizType(BizType.MARKETING);
		json.setAmount(platformTransfer.getActualMoney());
		Generator generator = trusteeshipService.execute(json, TrusteeshipServer.PLATFORM_PRE_TRANSACTION,
				GeneratorJSON.class, BusinessEnum.reward);
		return generator.getRespData();
	}

	@Override
	public String sendRewardConfirm(String userId, double money, String requestNo, String info,
			BusinessEnum businessType)
			throws DataAlreadExistException, UserAccountException, PlatformAccountException, TradeException {
		PlatformTransfer platformTransfer = new PlatformTransfer();
		platformTransfer.setOrderId(requestNo);
		platformTransfer.setStatus("'等待平台划款'");
		List<PlatformTransfer> pransfers = platformTransferService.getPlatformTransfer(platformTransfer);
		if (pransfers.isEmpty()) {
			throw new TradeException(ErrorCode.RewardNoFind);
		}
		platformTransfer = pransfers.get(0);
		if (sendRewardConfirm(platformTransfer, info)) {
			return "success";
		}
		return "fail";
	}

	private boolean sendRewardConfirm(PlatformTransfer platformTransfer, String title)
			throws UserAccountException, PlatformAccountException {
		GeneratorPreTransactionJSON json = new GeneratorPreTransactionJSON();
		// 活期宝调用这个方法，传入transferOutId 流水号应该是从数据库中读取，保证幂等
		json.setRequestNo(platformTransfer.getOrderId());
		json.setPreTransactionNo(PRET + platformTransfer.getOrderId());
		List<GeneratorDetailJSON> details = new ArrayList<>(1);
		GeneratorDetailJSON generatorDetailJSON = new GeneratorDetailJSON();
		generatorDetailJSON.setAmount(platformTransfer.getActualMoney());
		generatorDetailJSON.setPlatformUserNo(platformTransfer.getUsername());
		details.add(generatorDetailJSON);
		json.setDetails(details);
		Generator generator = trusteeshipService.execute(json, TrusteeshipServer.CONFIRM_PLATFORM_MARKETING,
				GeneratorJSON.class, BusinessEnum.reward_confirm);
		if (generator != null && generator.getRespData() != null) {
			GeneratorJSON generatorJson = generator.getRespData();
			if (generatorJson.getCode().equals("0")) {
				double rewardMoney = ArithUtil.round(platformTransfer.getActualMoney(), 2);
				platformTransfer.setStatus("平台划款成功");
				platformTransferService.update(platformTransfer);
				// 转出营销款
				platformAccountService.tofreeze(PlatformAccountEnum.PLATFORM_MARKETING, rewardMoney,
						BusinessEnum.valueOf(platformTransfer.getType()), title, platformTransfer.getOrderId());
				// 发奖励
				try {
					userAccountService.ptTransferIn(platformTransfer.getUsername(), rewardMoney,
							BusinessEnum.valueOf(platformTransfer.getType()), title, platformTransfer.getRemarks(),
							platformTransfer.getOrderId());
					return true;
				} catch (Exception e) {
					log.errLog("平台奖励发送失败",
							"userId: " + platformTransfer.getUsername() + ", rewardType: " + platformTransfer.getType()
									+ ", 奖励名称: " + title + ", requestNo: " + platformTransfer.getOrderId()
									+ "发送失败, 请及时处理, 平台营销款确认失败\n" + ExceptionUtils.getMessage(e),
							1);
					throw e;
				}

			} else {
				log.errLog("平台奖励发送失败",
						"userId: " + platformTransfer.getUsername() + ", rewardType: " + platformTransfer.getType()
								+ ", 奖励名称: " + title + ", requestNo: " + platformTransfer.getOrderId()
								+ "发送失败, 请及时处理\n 平台营销款确认返回" + generatorJson.getDescription(),
						1);
			}
		} else {
			log.errLog("平台奖励发送失败",
					"userId: " + platformTransfer.getUsername() + ", rewardType: " + platformTransfer.getType()
							+ ", 奖励名称: " + title + ", requestNo: " + platformTransfer.getOrderId()
							+ "发送失败, 请及时处理\n 平台营销款确认返回null",
					1);
		}
		return false;
	}

	/**
	 * 验密扣费
	 * 
	 * @param requestNo
	 * @param userId
	 * @param money
	 * @param plafromAccountType
	 * @param descripion
	 * @param terminalType
	 * @return
	 */
	public Generator createDeduct(String requestNo, String userId, double money, PlatformType platformType,
			String descripion, TerminalEnum terminalType) {
		GeneratorPreTransactionJSON json = new GeneratorPreTransactionJSON();
		json.setRequestNo(requestNo);
		json.setPlatformUserNo(userId);
		json.setAmount(money);
		if (platformType == null)
			platformType = PlatformType.SYS_GENERATE_002;
		json.setTargetPlatformUserNo(platformType.name());
		json.setCustomDefine(descripion);
//		json.setExpired(DateUtil.addMinute(new Date(), 5));
		json.setCallbackUrl(NotifyURL.TSCA);
		return trusteeshipService.create(json, TrusteeshipServer.VERIFY_DEDUCT, BusinessEnum.transfer);
	}

	@Override
	public Generator createTransaction(String userId, double money, String descripion, TerminalEnum terminalType)
			throws UserInfoException, UserAccountException {
		/*
		 * User user = userService.get(targetUserId); if (user == null) throw
		 * new UserInfoException(ErrorCode.TargetUserNotFind); if
		 * (!userService.hasRoleByUserId(user.getUserId(), "INVESTOR")) {
		 * log.errLog("通用转账失败", "只能转账给普通用户, userId: " + user.getUserId()); throw
		 * new UserInfoException(ErrorCode.TargetUserNotInvestor); }
		 */
		// 开始转账
		TransactionAuthorization transaction = generalTransactionService.prepare(userId, money);
		// 冻结金额
		userAccountService.freeze(userId, money, BusinessEnum.transfer, "转账：冻结金额",
				"向用户" + PlatformAccountEnum.PLATFORM_MARKETING + "转账", transaction.getId());
		return this.createDeduct(transaction.getId(), userId, money, PlatformType.SYS_GENERATE_002, descripion,
				terminalType);

	}

	/*
	 * @Override public void transferCallback(GeneratorJSON generatorJSON) { if
	 * (null != generatorJSON && generatorJSON.getCode().equals("0")) { try {
	 * TransactionAuthorization tran = generalTransactionService
	 * .transferLocal(generatorJSON.getRequestNo()); // 冻结金额
	 * userAccountService.tofreeze(tran.getUserId(), tran.getAmount(),
	 * BusinessEnum.transfer, "转账成功", "向用户" +
	 * tran.getTransactionAuthorizationDetail() .getUserId() + "转账",
	 * generatorJSON.getRequestNo()); platformAccountService.transferIn(
	 * PlatformAccountEnum.PLATFORM_MARKETING, tran.getAmount(),
	 * BusinessEnum.transfer, "用户：" + tran.getUserId() + "向 用户 ： " +
	 * tran.getTransactionAuthorizationDetail() .getUserId() + "转账",
	 * generatorJSON.getRequestNo());
	 * 
	 * String result = this.sendRewardDirect(
	 * tran.getTransactionAuthorizationDetail().getUserId(), tran.getId() +
	 * tran.getTransactionAuthorizationDetail() .getId(), tran.getAmount(),
	 * BusinessEnum.transfer, "用户转账", "用户：" + tran.getUserId() + "转账， 原始流水号： " +
	 * tran.getId(), "用户：" + tran.getUserId() + " 向用户" +
	 * tran.getTransactionAuthorizationDetail() .getUserId() + "转账， 转账金额" +
	 * tran.getAmount() + " 原始流水号： " + tran.getId() + "， 奖励流水号: " + tran.getId()
	 * + tran.getTransactionAuthorizationDetail() .getId());
	 * 
	 * if(result.equals("success")){
	 * generalTransactionService.transferConfirm(tran.getId()); }else
	 * if(result.equals("sended")){ log.errLog("通用转账失败", "原始流水号： tranId：" +
	 * generatorJSON.getRequestNo() + "平台划款流水号： " + tran.getId() +
	 * tran.getTransactionAuthorizationDetail() .getId() +
	 * "平台划款确认失败, 请调用奖励确认接口重新确认", 1); }else if(result.equals("fail")){
	 * log.errLog("通用转账失败", "原始流水号： tranId：" + generatorJSON.getRequestNo() +
	 * "平台划款流水号： " + tran.getId() + tran.getTransactionAuthorizationDetail()
	 * .getId() + "平台划款失败, 请调用奖励接口重新划款", 1); } } catch (Exception e) {
	 * log.errLog("通用转账失败", "tranId：" + generatorJSON.getRequestNo() +
	 * "转账本地处理失败" + ExceptionUtils.getMessage(e), 1); } } else {
	 * log.errLog("通用转账失败", generatorJSON.getDescription() + "转账失败", 1); }
	 * 
	 * }
	 */

	@Override
	@Transactional
	public void transferCallback(GeneratorJSON generatorJSON) {
		logger.debug("服务器通知返回：tsca: " + generatorJSON.toString());
		if (null != generatorJSON && generatorJSON.getCode().equals("0")) {
			try {
				TransactionAuthorization tran = generalTransactionService.transferLocal(generatorJSON.getRequestNo());

				// 从冻结中转出
				userAccountService.tofreeze(tran.getUserId(), tran.getAmount(), BusinessEnum.transfer, "转账成功",
						"向用户" + PlatformAccountEnum.PLATFORM_MARKETING + "转账", generatorJSON.getRequestNo());
				platformAccountService.transferIn(PlatformAccountEnum.PLATFORM_MARKETING, tran.getAmount(),
						BusinessEnum.transfer, "用户：" + tran.getUserId() + "向 用户 ： "
								+ tran.getTransactionAuthorizationDetail().getUserId() + "转账",
						generatorJSON.getRequestNo());
			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("服务器通知返回：tsca: " + ExceptionUtils.getMessage(e));
				log.errLog("通用转账失败",
						"tranId：" + generatorJSON.getRequestNo() + "转账本地处理失败" + ExceptionUtils.getMessage(e), 1);
			}
		} else {
			log.errLog("通用转账失败", generatorJSON.getDescription() + "转账失败", 1);
		}

	}
}
