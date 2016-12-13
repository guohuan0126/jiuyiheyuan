package com.duanrong.yeepay.schedule;

import static util.MyStringUtils.append;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import util.Log;
import util.MyStringUtils;
import util.XMLUtil;

import com.duanrong.business.autoinvest.model.AutoInvest;
import com.duanrong.business.autoinvest.service.AutoInvestService;
import com.duanrong.business.bankcard.BankName;
import com.duanrong.business.bankcard.model.BankCard;
import com.duanrong.business.bankcard.service.BankCardService;
import com.duanrong.business.demand.DemandtreasureConstants;
import com.duanrong.business.demand.model.DemandtreasureTransferIn;
import com.duanrong.business.demand.service.DemandtreasureTransferInService;
import com.duanrong.business.followInvestAward.service.FollowInvestAwardService;
import com.duanrong.business.invest.InvestConstants;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.recharge.service.RechargeService;
import com.duanrong.business.sms.SmsConstants;
import com.duanrong.business.sms.dao.SmsDao;
import com.duanrong.business.sms.model.Sms;
import com.duanrong.business.trusteeship.model.TrusteeshipAccount;
import com.duanrong.business.trusteeship.model.TrusteeshipConstants;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipAccountService;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.Role;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.RedPacketService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.business.withdraw.service.WithdrawCashService;
import com.duanrong.business.withdraw.service.impl.WithdrawCashBO;
import com.duanrong.sms.SMSSend;
import com.duanrong.util.Dom4jUtil;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.LoadConstantProterties;
import com.duanrong.util.ShortUrlGenerator;
import com.duanrong.yeepay.constants.YeepayConstants;
import com.duanrong.yeepay.xml.GeneratorXML;
import com.duanrong.yeepaysign.CFCASignUtil;

/**
 * <p>
 * 轮询
 * </p>
 * 
 * <pre>
 * 使用账户查询的业务有：开户，绑卡，取消绑卡，自动投标授权
 * </pre>
 * 
 * @author Lin Zhiming
 * @version Mar 4, 2015 3:51:10 PM
 */
@SuppressWarnings("unchecked")
@Component
public class TrusteeshipSchedule {

	@Resource
	Log log;

	@Resource
	TrusteeshipOperationService trusteeshipOperationService;

	@Resource
	BankCardService bankCardService;

	@Resource
	RechargeService rechargeService;

	@Resource
	WithdrawCashBO withdrawCashBO;

	@Resource
	WithdrawCashService withdrawCashService;

	@Resource
	InvestService investService;

	@Resource
	FollowInvestAwardService followInvestAwardService;

	@Resource
	TrusteeshipAccountService trusteeshipAccountService;

	@Resource
	UserService userService;

	@Resource
	AutoInvestService autoInvestService;

	@Resource
	RedPacketService redPacketService;

	@Resource
	DemandtreasureTransferInService demandtreasureTransferInService;

	@Resource
	UserDao userDao;

	@Resource
	SmsDao smsDao;

	@Scheduled(cron="0 0/5 * * * ?")
	public void handleSendedOperations() {
		// 查找请求表里面，等待返回的数据，且请求时间在十分钟以外。
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.infoLog("yeepay-轮询开始", sdf.format(new Date()));
		System.out.println("=========Spring Task=========");
		List<TrusteeshipOperation> tos = trusteeshipOperationService
				.getUnCallbackOperations(10);
		if (CollectionUtils.isEmpty(tos)) {
			log.infoLog("yeepay-轮询结束", "没有可以轮询的数据");
			return;
		}
		for (TrusteeshipOperation to : tos) {
			try {
				log.infoLog(
						"RefreshTrusteeshipOperation",
						append("markID:", to.getMarkId(), ", type:",
								to.getType()));

				if (to.getType().equals(
						TrusteeshipYeepayConstants.OperationType.INVEST) || to.getType().equals(
								TrusteeshipYeepayConstants.OperationType.AUTOINVEST)) {
					// 投标
					//investRecord(to);
				} else if (to.getType().equals(
						TrusteeshipYeepayConstants.OperationType.RECHARGE)) {
					// 充值
					rechargeRecord(to);
				} else if (to.getType().equals(
						TrusteeshipYeepayConstants.OperationType.REPAY)) {
					// 还款
				} else if (to.getType().equals(
						TrusteeshipYeepayConstants.OperationType.WITHDRAW_CASH)) {
					// 提现
					withdrawRecord(to);
				} else if (to
						.getType()
						.equals(TrusteeshipYeepayConstants.OperationType.GIVE_MOENY_TO_BORROWER)) {
					// 放款
					giveMoenyToBorrowerRecord(to);
				} else if (to
						.getType()
						.equals(TrusteeshipYeepayConstants.OperationType.CREATE_ACCOUNT)) {
					// 开户
					createAccountRecord(to);
				} else if (to
						.getType()
						.equals(TrusteeshipYeepayConstants.OperationType.BINDING_YEEPAY_BANKCARD)) {
					// 绑卡
					bindingCardRecord(to);
				} else if (to
						.getType()
						.equals(TrusteeshipYeepayConstants.OperationType.AUTHORIZEAUTOINVEST)) {
					// 自动投标授权
					authorizeAutoInvestRecord(to);
				} else if (to.getType().equals(
						TrusteeshipYeepayConstants.OperationType.TO_RCIN)) {
					// 活期宝转入
					demandInRecord(to);
				} else if (to.getType().equals(
						TrusteeshipYeepayConstants.OperationType.RESET_MOBILE)) {
					// 修改手机号
					restMobileNumber(to);
				}
			} catch (Exception e) {
				log.errLog("轮询异常", e);
			}
		}
		log.infoLog("yeepay-soa轮询结束", sdf.format(new Date()));
	}

	/**
	 * 修改手机号
	 * 
	 * @param to
	 */
	private void restMobileNumber(TrusteeshipOperation to) {
		String userId = to.getMarkId();
		try {

			String respInfo = getAccountInfo(userId, "修改手机号");
			if (StringUtils.isBlank(respInfo)) {
				log.errLog("开户轮询易宝返回信息为空", respInfo);
				return;
			}
			Map<String, String> resultMap = Dom4jUtil.xmltoMap(respInfo);

			String code = resultMap.get("code");
			// 激活状态
			String mobile = resultMap.get("bindMobileNo");
			if (StringUtils.equals("1", code) && !StringUtils.isBlank(mobile)) {
				User user = userService.get(userId);
				if (user == null ||StringUtils.isBlank(user.getUserId())
						|| StringUtils.isBlank(user.getMobileNumber())||StringUtils.isBlank(mobile)) {
					log.errLog("重置手机号调度", "修改本地用户失败" + "UserId:"
							+ userId + "----");
					to.setStatus(TrusteeshipConstants.Status.REFUSED);
				}else if(mobile.equals(user.getMobileNumber())){
					log.infoLog("重置手机号调度", "修改的手机号与本地一致" + "UserId:"
							+ userId + "----");
				}else {
					redPacketService.updateDetailMobileNumber(
							user.getMobileNumber(), mobile);
					redPacketService.updateShareMobileNumber(
							user.getMobileNumber(), mobile);
					userService.updateReferrerMobileNumber(
							user.getMobileNumber(), mobile);
					user.setMobileNumber(mobile);
					userService.updateUserMobileNumber(user);
					to.setStatus(TrusteeshipConstants.Status.PASSED);
				}
				to.setResponseData(respInfo);
				to.setResponseTime(new Date());
				trusteeshipOperationService.update(to);
			} else {
				dealFail(to, respInfo, 5);
			}
		} catch (Exception e) {
			log.errLog("修改手机号", e);
		}
	}

	/**
	 * 放款
	 */
	private void giveMoenyToBorrowerRecord(TrusteeshipOperation to) {
		to.setStatus(TrusteeshipYeepayConstants.Status.NO_RESPONSE);
		trusteeshipOperationService.update(to);
	}

	/**
	 * 自动投标授权
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private void authorizeAutoInvestRecord(TrusteeshipOperation to) {
		String userId = to.getMarkId();

		try {
			String respInfo = getAccountInfo(userId, "自动投标授权");
			if (StringUtils.isBlank(respInfo)) {
				log.errLog("自动投标授权轮询易宝返回信息为空", respInfo);
				return;
			}
			Map<String, String> resultMap = Dom4jUtil.xmltoMap(respInfo);

			String code = resultMap.get("code");
			// 开启状态
			String autoTender = resultMap.get("autoTender");

			if (StringUtils.equals("1", code)) {
				if (StringUtils.equals(autoTender,
						YeepayConstants.AuthorizeAutoInvestStatus.TRUE)) {
					AutoInvest autoInvest = autoInvestService.get(userId);
					if (autoInvest != null) {
						if (InvestConstants.AutoInvest.Status.OFF
								.equals(autoInvest.getStatus())) {
							autoInvest
									.setStatus(InvestConstants.AutoInvest.Status.ON);
							autoInvestService.update(autoInvest);
							to.setStatus(TrusteeshipConstants.Status.PASSED);
							to.setResponseData(respInfo);
							to.setResponseTime(new Date());
							trusteeshipOperationService.update(to);
						}
					} else {
						log.errLog("自动投标授权轮询", "AutoInvest对象不存在");
						to.setStatus(TrusteeshipConstants.Status.REFUSED);
						to.setResponseData(respInfo);
						to.setResponseTime(new Date());
						trusteeshipOperationService.update(to);
					}
				} else {
					// 用户到易宝，长时间未做操作，就要判断现在的时间是否大于（TO请求时间+5小时）
					dealFail(to, respInfo, 5);
				}
			} else {
				// 如果请求失败，就要判断现在的时间是否大于（TO请求时间+5小时），如果请求大于5小时，设为refused
				dealFail(to, respInfo, 5);
			}
		} catch (Exception e) {
			log.errLog("自动投标授权轮询", e);
		}
	}

	/**
	 * 处理请求失败
	 * 
	 * @param to
	 * @param respInfo
	 *            易宝返回内容
	 * @param hour
	 *            易宝请求后hour小时将请求状态改为refused
	 */
	private void dealFail(TrusteeshipOperation to, String respInfo, int hour) {
		Calendar calendarTO = Calendar.getInstance();
		calendarTO.setTime(to.getRequestTime());
		calendarTO.add(Calendar.HOUR, hour);
		Calendar calendarNOW = Calendar.getInstance();
		calendarNOW.setTime(new Date());
		if (calendarNOW.getTimeInMillis() > calendarTO.getTimeInMillis()) {
			to.setStatus(TrusteeshipConstants.Status.REFUSED);
			to.setResponseData(respInfo);
			to.setResponseTime(new Date());
			trusteeshipOperationService.update(to);
		}
	}

	/**
	 * 开户
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private void createAccountRecord(TrusteeshipOperation to) {
		String userId = to.getMarkId();

		try {

			String respInfo = getAccountInfo(userId, "开户");
			if (StringUtils.isBlank(respInfo)) {
				log.errLog("开户轮询易宝返回信息为空", respInfo);
				return;
			}
			Map<String, String> resultMap = Dom4jUtil.xmltoMap(respInfo);

			String code = resultMap.get("code");
			// 激活状态
			String activeStatus = resultMap.get("activeStatus");

			if (StringUtils.equals("1", code)) {
				if (StringUtils.equals(activeStatus,
						YeepayConstants.UserActiveStatus.ACTIVATED)) {
					User user = userService.get(userId);
					
					TrusteeshipAccount ta = trusteeshipAccountService.get(user.getUserId());

					if (ta == null) {
						ta = new TrusteeshipAccount();
						ta.setId(user.getUserId());
						ta.setUserId(user.getUserId());
						ta.setAccountId(resultMap.get("yeepay"));
						ta.setCreateTime(new Date());
						ta.setStatus(TrusteeshipYeepayConstants.Status.PASSED);
						ta.setTrusteeship("yeepay");
						trusteeshipAccountService.insert(ta);
					} else {
						ta.setAccountId(resultMap.get("yeepay"));
						ta.setCreateTime(new Date());
						ta.setStatus(TrusteeshipYeepayConstants.Status.PASSED);
						ta.setTrusteeship("yeepay");
						trusteeshipAccountService.update(ta);
					}

					// 普通用户添加投资权限，如果是企业用户，需要手动更改用户状态。
					if (StringUtils.equalsIgnoreCase(user.getUserType(), "user")
							|| StringUtils.isBlank(user.getUserType())) {
						Role role = new Role("INVESTOR");
						userService.addRole(user.getUserId(), role);
					}

					
					to.setStatus(TrusteeshipConstants.Status.PASSED);
					to.setResponseData(respInfo);
					to.setResponseTime(new Date());
					trusteeshipOperationService.update(to);
				} else {
					dealFail(to, respInfo, 5);
				}

			} else {
				dealFail(to, respInfo, 5);
			}
		} catch (Exception e) {
			log.errLog("开户账户查询", e);
		}
	}

	/**
	 * 投资
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private void investRecord(TrusteeshipOperation to) {
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);

		/*********************** XML拼接 ***********************/
		GeneratorXML xml = new GeneratorXML();
		xml.setPlatformNo(TrusteeshipYeepayConstants.Config.MER_CODE);
		xml.setRequestNo(to.getMarkId());
		xml.setMode("CP_TRANSACTION");
		String content = null;
		try {
			content = XMLUtil.getXML(xml);
		} catch (Exception e) {
			log.errLog("投资轮询XML拼接异常", e);
			return;
		}

		/*********************** 生成签名 ***********************/
		String sign = CFCASignUtil.sign(content);		
		log.infoLog("投资轮询XML", content);
		log.infoLog("投资轮询sign", sign);
		postMethod.setParameter("req", content);
		postMethod.setParameter("sign", sign);
		postMethod.setParameter("service", "QUERY");

		/*********************** 执行POST ***********************/
		try {
			int statusCode = client.executeMethod(postMethod);
			if (HttpStatus.SC_OK != statusCode) {
				log.errLog("投资轮询HTTP状态码异常", postMethod.getStatusLine()
						.toString());
			}

			// 易宝响应结果
			byte[] responseBody = postMethod.getResponseBody();
			recordData(responseBody, "invest");
			String respInfo = new String(responseBody, "UTF-8");
			log.infoLog("投资轮询返回数据", respInfo);
			// 响应信息
			Document respXML = DocumentHelper.parseText(respInfo);
			Map<String, String> resultMap = Dom4jUtil.xmltoMap(respInfo);
			String code = resultMap.get("code");
			// records 记录列表
			String records = resultMap.get("records");
			// 获取投资记录
			Invest invest = investService.get(to.getMarkId());

			// 返回码为1标识成功
			if ("1".equals(code) && StringUtils.isNotBlank(records)) {
				String status = respXML.selectSingleNode(
						"/response/records/record/status").getStringValue();
				if ("CONFIRM".equals(status)) {
					// 还款中
					if (MyStringUtils.equalsAnyString(invest.getStatus(),
							InvestConstants.InvestStatus.WAIT_AFFIRM,
							InvestConstants.InvestStatus.BID_SUCCESS)) {
						invest.setStatus(InvestConstants.InvestStatus.REPAYING);
						investService.update(invest);
					}
					to.setStatus(TrusteeshipConstants.Status.PASSED);
					to.setResponseData(respInfo);
					to.setResponseTime(new Date());
					trusteeshipOperationService.update(to);
				} else if ("PREAUTH".equals(status)) {
					// 投标成功
					if (InvestConstants.InvestStatus.WAIT_AFFIRM.equals(invest
							.getStatus())) {
						invest.setStatus(InvestConstants.InvestStatus.BID_SUCCESS);
						investService.update(invest);
					}
					to.setStatus(TrusteeshipConstants.Status.PASSED);
					to.setResponseData(respInfo);
					to.setResponseTime(new Date());
					trusteeshipOperationService.update(to);
				} else if ("CANCEL".equals(status)) {
					// 投标成功后流标
					if (InvestConstants.InvestStatus.BID_SUCCESS.equals(invest
							.getStatus())) {
						try {
							investService.syncInvest(invest, "S2SFail");
						} catch (Exception ex) {
							log.errLog("投资callback", ex);
							return;
						}
					}
					to.setStatus(TrusteeshipConstants.Status.PASSED);
					to.setResponseData(respInfo);
					to.setResponseTime(new Date());
					trusteeshipOperationService.update(to);
				}
			} else {
				if (InvestConstants.InvestStatus.WAIT_AFFIRM.equals(invest
						.getStatus())) {
					try {
						investService.syncInvest(invest, "S2SFail");
					} catch (Exception ex) {
						log.errLog("投资callback", ex);
						return;
					}
				}
				to.setStatus(TrusteeshipConstants.Status.REFUSED);
				to.setResponseData(respInfo);
				to.setResponseTime(new Date());
				trusteeshipOperationService.update(to);
			}
		} catch (Exception e) {
			log.errLog("投资轮询", e);
		} finally {
			postMethod.releaseConnection();
		}
	}

	/**
	 * 充值
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private void rechargeRecord(TrusteeshipOperation to) {
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);

		client.getParams().setParameter(HttpMethodParams.PROTOCOL_VERSION, HttpVersion.HTTP_1_0);
	    postMethod.getParams().setParameter(HttpMethodParams.PROTOCOL_VERSION, HttpVersion.HTTP_1_0);
	    postMethod.addRequestHeader("Connection", "Close");
		
		/*********************** XML拼接 ***********************/
		// 参数拼接
		GeneratorXML xml = new GeneratorXML();
		xml.setPlatformNo(TrusteeshipYeepayConstants.Config.MER_CODE);
		xml.setRequestNo(to.getMarkId());
		xml.setMode("RECHARGE_RECORD");

		String content = null;
		try {
			content = XMLUtil.getXML(xml);
		} catch (Exception e) {
			log.errLog("充值轮询XML拼接异常", e);
			return;
		}

		/*********************** 生成签名 ***********************/
		String sign = CFCASignUtil.sign(content);
		log.infoLog("充值轮询XML", content);
		log.infoLog("充值轮询sign", sign);
		postMethod.setParameter("req", content);
		postMethod.setParameter("sign", sign);
		postMethod.setParameter("service", "QUERY");

		/*********************** 执行POST ***********************/
		try {
			int statusCode = client.executeMethod(postMethod);
			if (HttpStatus.SC_OK != statusCode) {
				log.errLog("充值轮询HTTP状态码异常", postMethod.getStatusLine()
						.toString());
			}

			// 易宝响应结果
			byte[] responseBody = postMethod.getResponseBody();
			recordData(responseBody, "recharge");
			String respInfo = new String(responseBody, "UTF-8");
			log.infoLog("充值轮询返回数据", respInfo);
			// 响应信息
			Document respXML = DocumentHelper.parseText(respInfo);
			Map<String, String> resultMap = Dom4jUtil.xmltoMap(respInfo);
			String code = resultMap.get("code");
			String records = resultMap.get("records");
			String operator = to.getOperator();
			String payProduct = resultMap.get("payProduct");
			// 只有code为1且记录返回状态不为空时才能对对其进行处理
			if ("1".equals(code) && StringUtils.isNotBlank(records)) {
				String status = respXML.selectSingleNode(
						"/response/records/record/status").getStringValue();
				if ("SUCCESS".equals(status)) {
					rechargeService.rechargeSuccess(operator, payProduct);
					to.setStatus(TrusteeshipYeepayConstants.Status.PASSED);
					to.setResponseData(respInfo);
					to.setResponseTime(new Date());
					trusteeshipOperationService.update(to);
				} else {
					dealFail(to, respInfo, 5);
				}
			} else {
				dealFail(to, respInfo, 5);
			}
		} catch (Exception e) {
			log.errLog("充值轮询", e);
		} finally {
			postMethod.releaseConnection();
		}
	}

	/**
	 * 提现
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private void withdrawRecord(TrusteeshipOperation to) {
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);

		client.getParams().setParameter(HttpMethodParams.PROTOCOL_VERSION, HttpVersion.HTTP_1_0);
	    postMethod.getParams().setParameter(HttpMethodParams.PROTOCOL_VERSION, HttpVersion.HTTP_1_0);
	    postMethod.addRequestHeader("Connection", "Close");
		
	    /*********************** XML拼接 ***********************/
		// 参数拼接
		GeneratorXML xml = new GeneratorXML();
		xml.setPlatformNo(TrusteeshipYeepayConstants.Config.MER_CODE);
		xml.setRequestNo(to.getMarkId());
		xml.setMode("WITHDRAW_RECORD");

		String content = null;
		try {
			content = XMLUtil.getXML(xml);
		} catch (Exception e) {
			log.errLog("提现轮询XML拼接异常", e);
			return;
		}

		/*********************** 生成签名 ***********************/
		String sign = CFCASignUtil.sign(content);
		log.infoLog("提现轮询XML", content);
		log.infoLog("提现轮询sign", sign);
		postMethod.setParameter("req", content);
		postMethod.setParameter("sign", sign);
		postMethod.setParameter("service", "QUERY");

		/*********************** 执行POST ***********************/
		try {
			int statusCode = client.executeMethod(postMethod);
			if (HttpStatus.SC_OK != statusCode) {
				log.errLog("提现轮询HTTP状态码异常", postMethod.getStatusLine()
						.toString());
			}
			// 易宝响应结果
			byte[] responseBody = postMethod.getResponseBody();
			recordData(responseBody, "withdraw");
			String respInfo = new String(responseBody, "UTF-8");
			log.infoLog("提现轮询返回数据", respInfo);
			// 响应信息
			Document respXML = DocumentHelper.parseText(respInfo);
			Map<String, String> resultMap = Dom4jUtil.xmltoMap(respInfo);
			String code = resultMap.get("code");
			String records = resultMap.get("records");

			// 只有code为1且记录返回状态不为空时才能对对其进行处理
			if ("1".equals(code) && StringUtils.isNotBlank(records)) {
				String status = respXML.selectSingleNode(
						"/response/records/record/status").getStringValue();
				if ("INIT".equals(status)) {
					to.setStatus(TrusteeshipYeepayConstants.Status.PASSED);
					to.setResponseData(respInfo);
					to.setResponseTime(new Date());
					trusteeshipOperationService.update(to);
				}
				if ("SUCCESS".equals(status)) {
					String withdrawCashId = to.getOperator();
					try {
						withdrawCashBO.passWithdrawCashApply(withdrawCashId);
					} catch (Exception e) {
						return;
					}
					to.setStatus(TrusteeshipYeepayConstants.Status.PASSED);
					to.setResponseData(respInfo);
					to.setResponseTime(new Date());
					trusteeshipOperationService.update(to);
				}
			} else {
				dealFail(to, respInfo, 5);
			}
		} catch (Exception e) {
			log.errLog("提现轮询", e);
		} finally {
			postMethod.releaseConnection();
		}
	}

	/**
	 * 绑卡
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private void bindingCardRecord(TrusteeshipOperation to) {
		try {
			String userId = to.getMarkId();

			// 通过易宝直连接口查询账户信息
			String respInfo = getAccountInfo(userId, "绑卡");
			if (StringUtils.isBlank(respInfo)) {
				log.errLog("绑卡轮询易宝返回信息为空", respInfo);
				return;
			}

			/**************** 获取账户参数 ****************/
			Map<String, String> resultMap = Dom4jUtil.xmltoMap(respInfo);
			String code = resultMap.get("code");
			String cardNo = resultMap.get("cardNo");
			String cardStatus = resultMap.get("cardStatus");
			String bank = resultMap.get("bank");
			String paySwift = resultMap.get("paySwift");
			/**************** 主要业务流程 ****************/
			if (StringUtils.equals("1", code)) {
				/***** 易宝没有返回卡信息,五小时之后将TO状态改为失败 *****/
				if (StringUtils.isBlank(cardNo)
						|| StringUtils.isBlank(cardStatus)
						|| StringUtils.isBlank(bank)) {
					dealFail(to, respInfo, 5);
					return;
				}

				/***** 易宝返回卡信息，状态通过时更新卡表，更新TO表状态 *****/
				if (StringUtils.equals("VERIFIED", cardStatus)) {
					BankCard bankCard = new BankCard();
					bankCard.setId(IdGenerator.randomUUID());
					bankCard.setCardNo(cardNo);
					bankCard.setName(BankName.getBankName(bank));
					bankCard.setBank(bank);
					bankCard.setTime(new Date());
					bankCard.setAccountName(respInfo);
					bankCard.setUserId(userId);
					bankCard.setStatus(cardStatus);
					bankCard.setPaySwift(paySwift);
					bankCardService.quickBindingCard(bankCard);
					//
					to.setStatus(TrusteeshipYeepayConstants.Status.PASSED);
					to.setResponseData(respInfo);
					to.setResponseTime(new Date());
					trusteeshipOperationService.update(to);
				}
			} else {
				/***** 易宝返回失败,五小时之后将TO状态改为失败 *****/
				dealFail(to, respInfo, 5);
			}
		} catch (Exception e) {
			log.errLog("绑卡轮询异常", e);
		}

	}

	/*private void handleSendedBankCards() {
		List<BankCard> bcs = bankCardService.getCancelTheTieCard();
		if (CollectionUtils.isEmpty(bcs)) {
			return;
		}
		for (BankCard bankCard : bcs) {
			try {
				Date date = convertStringToDate(bankCard);
				if (DateUtil.calculateIntervalDays(date, new Date()) > 2) {
					log.infoLog("解绑卡", bankCard.getCardNo()+"申请时间："+bankCard.getCancelBandDingTime());
					cancelBindingCard(bankCard);
				}
			} catch (Exception e) {
				log.errLog("解绑卡异常", bankCard.toString() + e);
			}

		}
	}*/

	/**
	 * 取消绑卡主动查询
	 * 
	 * @param to
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private void cancelBindingCard(BankCard bankCard) {
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
		client.getParams().setParameter(HttpMethodParams.PROTOCOL_VERSION, HttpVersion.HTTP_1_0);
	    postMethod.getParams().setParameter(HttpMethodParams.PROTOCOL_VERSION, HttpVersion.HTTP_1_0);
	    postMethod.addRequestHeader("Connection", "Close");
		/*********************** XML拼接 ***********************/
		GeneratorXML xml = new GeneratorXML();
		xml.setPlatformNo(TrusteeshipYeepayConstants.Config.MER_CODE);
		xml.setRequestNo(bankCard.getId());
		xml.setMode("UNBIND_RECORD");
		String content = null;
		try {
			content = XMLUtil.getXML(xml);
		} catch (Exception e) {
			log.errLog("解绑卡XML拼接异常", e);
			return;
		}

		/*********************** 生成签名 ***********************/
		String sign = CFCASignUtil.sign(content);
		postMethod.setParameter("req", content);
		postMethod.setParameter("sign", sign);
		postMethod.setParameter("service", "QUERY");

		String userId = bankCard.getUserId();

		/*********************** 执行POST ***********************/
		try {
			int statusCode = client.executeMethod(postMethod);
			if (HttpStatus.SC_OK != statusCode) {
				log.errLog("解绑卡HTTP状态码异常", postMethod.getStatusLine()
						.toString());
			}

			// 易宝响应结果
			byte[] responseBody = postMethod.getResponseBody();
			recordData(responseBody, "cancelBindingCard");

			String respInfo = new String(responseBody, "UTF-8");
			log.infoLog("轮询数据记录", "cancelBindingCard:" + respInfo);
			// 响应信息
			Document respXML = DocumentHelper.parseText(respInfo);
			Map<String, String> resultMap = Dom4jUtil.xmltoMap(respInfo);
			String code = resultMap.get("code");
			String records = resultMap.get("records");
			if (StringUtils.equals("1", code)
					&& StringUtils.isNotBlank(records)) {
				String userNo = respXML.selectSingleNode(
						"/response/records/record/platformUserNo")
						.getStringValue();
				if (!userId.equals(userNo)) {
					log.errLog("解绑卡失败", "本地用户Id"+userId+",易宝返回用户Id"+userNo);
					return;
				}
				String status = respXML.selectSingleNode(
						"/response/records/record/status").getStringValue();
				if ("SUCCESS".equals(status)) {
					bankCard.setCancelStatus("解绑成功");
					bankCard.setDeleteBankCard("delete");
					DateFormat df = DateFormat.getDateTimeInstance();
					String cancelBindingCardDate = df.format(new Date());
					bankCard.setBindingPrice(cancelBindingCardDate);
					bankCardService.update(bankCard);
					log.infoLog("解绑卡", "userId:"+userId+"卡号："+bankCard.getCardNo()+"解绑成功");
					try {
						User user = new User();
						user.setUserId(userId);
						user = userDao.verifyLogin(user);
						String cardNo = bankCard.getCardNo();
						if (user != null
								&& StringUtils.isNotBlank(user
										.getMobileNumber())
								&& StringUtils.isNotBlank(cardNo)
								&& cardNo.length() > 4) {
							cardNo = cardNo.substring(cardNo.length() - 4,
									cardNo.length());
							String msg = "您尾号#{cardNo}的银行卡已解除绑定。如非本人操作请联系客服，电话400-062-1008 。";
							msg = StringUtils.replace(msg, "#{cardNo}", cardNo);
							SMSSend.batchSend(user.getMobileNumber(), msg);
							Sms sms = new Sms();
							String id = ShortUrlGenerator.shortUrl(IdGenerator
									.randomUUID())
									+ ShortUrlGenerator.shortUrl(IdGenerator
											.randomUUID());
							sms.setId(id);
							sms.setUserId(userId);
							sms.setMobileNumber(user.getMobileNumber());
							sms.setContent(msg);
							sms.setTime(new Date());
							sms.setType(SmsConstants.CANCELBINDINGCARD);
							smsDao.insert(sms);
						}
					} catch (Exception e) {
						log.errLog("解绑卡发送验证码错误", e);
					}
				}
			}
		} catch (Exception e) {
			log.errLog("解绑卡异常", e);
		}
	}

	/**
	 * 日志记录
	 * 
	 * @param responseBody
	 * @param type
	 */
	public void recordData(byte[] responseBody, String type) {
		try {
			List<String> list = new ArrayList<String>();
			list.add("duanrongcfcaConfig.properties");
			HashMap<String, Properties> loadConstantsPro = LoadConstantProterties
					.loadConstantsPro(list);
			String yeepayResponseDataPath = null;
			if (loadConstantsPro != null && loadConstantsPro.size() > 0) {
				Properties properties = loadConstantsPro
						.get("duanrongcfcaConfig.properties");
				yeepayResponseDataPath = properties
						.getProperty("yeepayResponseDataPath");
				if (properties == null || yeepayResponseDataPath == null) {
					return;
				} else {
					if (responseBody == null) {
						CFCASignUtil.writeMessage("响应数据为空" + type,
								yeepayResponseDataPath);
						log.errLog("响应数据为空", type);
					} else {
						// 创建一个要写入的文件路径,按天生成
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						String fileName = sdf.format(new Date());
						fileName = yeepayResponseDataPath
								+ "responseYeepayData" + fileName + ".txt";
						String respInfo = new String(responseBody, "UTF-8");
						CFCASignUtil.writeMessage(type + respInfo, fileName);
					}
				}
			}
		} catch (Exception e) {
			log.errLog("记录易宝响应数据TrusteeshipQuartzServiceImpl.recordData", e);
		}
	}

	/**
	 * 将字符串转换为日期
	 * 
	 * @param bankCard
	 * @return
	 */
	private Date convertStringToDate(BankCard bankCard) {
		String cancelBindingCardDate = bankCard.getCancelBandDingTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(cancelBindingCardDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public String getAccountInfo(String userId, String businessName) {
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);

		 client.getParams().setParameter(HttpMethodParams.PROTOCOL_VERSION, HttpVersion.HTTP_1_0);
	     postMethod.getParams().setParameter(HttpMethodParams.PROTOCOL_VERSION, HttpVersion.HTTP_1_0);
	     postMethod.addRequestHeader("Connection", "Close");
		
		/*********************** XML拼接 ***********************/
		GeneratorXML xml = new GeneratorXML();
		xml.setPlatformNo(TrusteeshipYeepayConstants.Config.MER_CODE);
		xml.setPlatformUserNo(userId);
		String content = null;
		try {
			content = XMLUtil.getXML(xml);
		} catch (Exception e) {
			log.errLog(append("账户查询接口XML拼接异常", "==", businessName), e);
			return null;
		}

		/*********************** 生成签名 ***********************/
		String sign = CFCASignUtil.sign(content);
		log.infoLog(append("账户查询XML", "==", businessName), content);
		log.infoLog(append("账户查询sign", "==", businessName), sign);
		postMethod.setParameter("req", content);
		postMethod.setParameter("sign", sign);
		postMethod.setParameter("service", "ACCOUNT_INFO");

		/*********************** 执行POST ***********************/
		try {
			int statusCode = client.executeMethod(postMethod);
			if (HttpStatus.SC_OK != statusCode) {
				log.infoLog(append("账户查询HTTP状态码异常", "==", businessName),
						postMethod.getStatusLine().toString());
			}

			byte[] responseBody = postMethod.getResponseBody();
			recordData(responseBody, businessName);
			String respInfo = new String(responseBody, "UTF-8");
			log.infoLog(append("账户查询返回数据", "==", businessName), respInfo);

			return respInfo;
		} catch (Exception e) {
			log.errLog(append("账户查询", "==", businessName), e);
			return null;
		} finally {
			postMethod.releaseConnection();
		}
	}

	/**
	 * 转入活期宝
	 * 
	 * @param to
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private void demandInRecord(TrusteeshipOperation to) {
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);

		/*********************** XML拼接 ***********************/
		GeneratorXML xml = new GeneratorXML();
		xml.setPlatformNo(TrusteeshipYeepayConstants.Config.MER_CODE);
		xml.setRequestNo(to.getMarkId());
		xml.setMode("CP_TRANSACTION");
		String content = null;
		try {
			content = XMLUtil.getXML(xml);
		} catch (Exception e) {
			log.errLog("投资轮询XML拼接异常", e);
			return;
		}

		/*********************** 生成签名 ***********************/
		String sign = CFCASignUtil.sign(content);
		log.infoLog("活期宝轮询XML", content);
		log.infoLog("活期宝轮询sign", sign);
		postMethod.setParameter("req", content);
		postMethod.setParameter("sign", sign);
		postMethod.setParameter("service", "QUERY");

		/*********************** 执行POST ***********************/
		try {
			int statusCode = client.executeMethod(postMethod);
			if (HttpStatus.SC_OK != statusCode) {
				log.errLog("活期宝轮询HTTP状态码异常", postMethod.getStatusLine()
						.toString());
			}

			// 易宝响应结果
			byte[] responseBody = postMethod.getResponseBody();
			recordData(responseBody, "demandIn");
			String respInfo = new String(responseBody, "UTF-8");
			log.infoLog("活期宝轮询返回数据", respInfo);
			// 响应信息
			Document respXML = DocumentHelper.parseText(respInfo);
			Map<String, String> resultMap = Dom4jUtil.xmltoMap(respInfo);
			String code = resultMap.get("code");
			String records = resultMap.get("records");
			DemandtreasureTransferIn demandIn = demandtreasureTransferInService.get(to.getMarkId());
			// 返回码为1标识成功
			if ("1".equals(code) && StringUtils.isNotBlank(records)) {
				//订单状态：PREAUTH 已授权。CONFIRM：已确讣出款。CANCEL：已取消。DIRECT：直接划转。
				String status = respXML.selectSingleNode("/response/records/record/status").getStringValue();
				//处理状态: PROCESSING：处理中。SUCCESS：成功。ERROR：异常。FAIL：失败
				String subStatus = respXML.selectSingleNode("/response/records/record/subStatus").getStringValue();
				if ("SUCCESS".equals(subStatus) && "PREAUTH".equals(status)) {
					if (DemandtreasureConstants.TransferInStatus.SENDED.equals(demandIn.getStatus())) {
						demandtreasureTransferInService.syncDemandIn(demandIn, "S2SSuccess");
					}
					to.setStatus(TrusteeshipConstants.Status.PASSED);
					to.setResponseData(respInfo);
					to.setResponseTime(new Date());
					trusteeshipOperationService.update(to);
				} if ("ERROR".equals(subStatus)||"FAIL".equals(subStatus)) {
					if (DemandtreasureConstants.TransferInStatus.SENDED.equals(demandIn.getStatus())) {
						demandtreasureTransferInService.syncDemandIn(demandIn, "S2SFail");
					}
					to.setStatus(TrusteeshipConstants.Status.REFUSED);
					to.setResponseData(respInfo);
					to.setResponseTime(new Date());
					trusteeshipOperationService.update(to);
				}
			} else {
				//当调用易宝失败时，活期宝转入表中的In状态还是SENDED时，进行同步失败操作
				if (!DemandtreasureConstants.TransferInStatus.FAIL.equals(demandIn.getStatus())) {
					try {
						demandtreasureTransferInService.syncDemandIn(demandIn, "S2SFail");
					} catch (Exception ex) {
						log.errLog("投资callback", ex);
						return;
					}
				}
				to.setStatus(TrusteeshipConstants.Status.REFUSED);
				to.setResponseData(respInfo);
				to.setResponseTime(new Date());
				trusteeshipOperationService.update(to);
			}
		} catch (Exception e) {
			log.errLog("活期宝轮询", e);
		} finally {
			postMethod.releaseConnection();
		}
	}

}