package com.duanrong.business.invest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
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

import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.sms.SmsConstants;
import com.duanrong.business.sms.dao.SmsDao;
import com.duanrong.business.sms.model.Sms;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.trusteeship.model.TrusteeshipConstants;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.dao.RedPacketDao;
import com.duanrong.business.user.model.RedPacket;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.InformationService;
import com.duanrong.business.user.service.RedPacketService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.sms.SMSSend;
import com.duanrong.util.DateUtil;
import com.duanrong.util.Dom4jUtil;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.jedis.DRJedisMQ;
import com.duanrong.util.json.FastJsonUtil;
import com.duanrong.yeepay.xml.GeneratorXML;
import com.duanrong.yeepaysign.CFCASignUtil;

/**
 * 投资活动调度
 * 
 * @author xiao
 * 
 */
/*@Component*/
public class InvestActiveSchdule {

	@Resource
	SmsService smsService;
	
	@Resource
	RedPacketService redpacketService;

	@Resource
	InvestService investService;

	@Resource
	InvestDao investDao;

	@Resource
	SmsDao smsDao;
	@Resource
	UserService userService;
	@Resource
	Log log;

	@Resource
	TrusteeshipOperationService trusteeshipOperationService;

	@Resource
	RedPacketDao redPacketDao;

	@Resource
	InformationService informationService;
	
	/**
	 * 调度发送短信 1.注册未投资（两天）用户 发送短信 （全体） 2.注册未投资（20天）用户发送红包 排除掉辅仁 比搜益 3.注册未投资用户发送短信
	 * （全体）
	 */
	@Scheduled(cron = "0 20 10 * * ?")
	public void smsServiceSchdule() {
		// 注册两天 为投资的用户手机号
		int day = 3;
		String mobileNumbers = "";
		String content = "送您5元现金红包，无使用门槛！短融网上市背景，期限灵活，收益稳定，快前往 t.cn/R4L4Usl 查看，回复TD退订。";
		List<String> mobNumberList = userService.readUserMobileNumber(day);
		log.infoLog("短信", "开始注册为投资用户3天短信");
		if (mobNumberList.size() > 0) {
			for (String mobNumber : mobNumberList) {
				try {
					mobileNumbers += mobNumber + ",";
					User user = userService.getUserByMobileNumber(mobNumber);
					createReadpacket(user,256, mobNumber, 5, 0,0, 0, "0","新人陪伴礼","money",7);
					Sms sms = new Sms();
					sms.setId(IdGenerator.randomUUID());
					sms.setUserId(user.getUserId());
					sms.setMobileNumber(mobNumber);
					sms.setContent(content);
					sms.setTime(new Date());
					sms.setType(SmsConstants.INFORMATION);
					smsDao.insert(sms);
				} catch (Exception e) {
					log.errLog("短信", e+"注册未投资用户3天短信");
					e.printStackTrace();
				}
			}
			mobileNumbers = mobileNumbers.substring(0,
					mobileNumbers.length() - 1);
			System.out.println(mobileNumbers);
			SMSSend.batchSend1(mobileNumbers, content);
			log.infoLog("短信", "结束注册未投资用户3天" + mobileNumbers + "短信");
			System.out
					.println("**********************发送短信*******************************");

		}
	}
	
	
	@Scheduled(cron = "0 30 10 * * ?")
	public void  smsServiceSchdule9ziran(){
		String content9 = "奉上100元红包+1%加息券，有效期7天！1元起投，享安全灵活理财，前去 t.cn/R4L4Usl 使用，回TD退订";
		int day = 9;
		//自然渠道
		List<String> mobNumberList9ziran = userService.readUserMobileNumberziran(day);
		List<String> userList = new ArrayList<>();
		log.infoLog("短信", "开始注册为投资用户9天红包短信");
		if (mobNumberList9ziran.size() > 0) {
			for (String mobileNumber : mobNumberList9ziran) {
				// 根据手机号 查询有没有 该手机号下没有可用的红包 没有在给发送红包
//				String mobileNumber="13731166909";
				try {
					User user = userService.getUserByMobileNumber(mobileNumber);
						createReadpacket(user,226, mobileNumber, 20, 0,2000.00, 1, "0","新手理财金","money",7);
										//用户  模板编号 手机号            红包金额 加息券额度  投资限额  投资项目限额 是否新手可用（0可用） 红包名称  现金红包 有效期
						createReadpacket(user,226, mobileNumber, 30, 0,5000.00, 1, "0","新手理财金","money",7);
						createReadpacket(user,226, mobileNumber, 50, 0,1000.00, 5, "0","新手理财金","money",7);
						//加息券
						createReadpacket(user,226,mobileNumber,  0, 0.01,0,     0, "0","新手理财金","rate",7);
						Sms sms = new Sms();
						sms.setId(IdGenerator.randomUUID());
						sms.setUserId(user.getUserId());
						sms.setMobileNumber(mobileNumber);
						sms.setContent(content9);
						sms.setTime(new Date());
						sms.setType(SmsConstants.INFORMATION);
						smsDao.insert(sms);
						System.out
								.println("**********************发送短信*******************************");
						SMSSend.batchSend1(mobileNumber, content9);
						userList.add(user.getUserId());
				} catch (Exception e) {
					log.errLog("短信", e+"注册未投资用户9天自然短信");
					e.printStackTrace();
				}
			}
			
			
			log.infoLog("短信", "结束注册未投资9天自然发送红包的用户数量：" + userList.size()
					+ "结束注册未投资9天自然发送红包的用户" + userList.toString(), 1);
		}
	}
	@Scheduled(cron = "0 40 10 * * ?")
	public void  smsServiceSchdule9tuiguang(){
		//推广
		int day = 9;
		String content9 = "奉上100元红包+1%加息券，有效期7天！1元起投，享安全灵活理财，前去 t.cn/R4L4Usl 使用，回TD退订";
		List<String> mobNumberList9tuiguang = userService.readUserMobileNumbertuiguang(day);
		List<String> userList = new ArrayList<>();
		if (mobNumberList9tuiguang.size() > 0) {
			for (String mobileNumber : mobNumberList9tuiguang) {
				// 根据手机号 查询有没有 该手机号下没有可用的红包 没有在给发送红包
//				String mobileNumber1="13731166909";
				try {
					User user = userService.getUserByMobileNumber(mobileNumber);
						createReadpacket(user,227, mobileNumber, 20, 0,3000.00, 1, "0","新手礼","money",7);
										//用户  模板编号 手机号            红包金额 加息券额度  投资限额  投资项目限额 是否新手可用（0可用） 红包名称  现金红包 有效期
						createReadpacket(user,227, mobileNumber, 30, 0,1000.00, 3, "0","新手礼","money",7);
						createReadpacket(user,227, mobileNumber, 50, 0,10000.00, 1, "0","新手礼","money",7);
						createReadpacket(user,227, mobileNumber,  0, 0.01,0,     0, "0","新手礼","rate",7);
						Sms sms1 = new Sms();
						sms1.setId(IdGenerator.randomUUID());
						sms1.setUserId(user.getUserId());
						sms1.setMobileNumber(mobileNumber);
						sms1.setContent(content9);
						sms1.setTime(new Date());
						sms1.setType(SmsConstants.INFORMATION);
						smsDao.insert(sms1);
						System.out
								.println("**********************发送短信*******************************"); 
						SMSSend.batchSend1(mobileNumber, content9);
						userList.add(user.getUserId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.errLog("短信", e+"注册未投资用户9天推广短信");
				}
			}
			log.infoLog("短信", "结束注册未投资9天推广发送红包的用户数量：" + userList.size()
					+ "结束注册未投资9天推广发送红包的用户" + userList.toString(), 1);
		}
		
	}
	
	@Scheduled(cron = "0 50 10 * * ?")
	public void  smsServiceSchdule17(){
		
			int  day = 17;	
			String content17 = "奉上60元红包+1%加息券，有效期15天！1元起投，享安全灵活理财，前去 t.cn/R4L4Usl 使用，回TD退订";
			List<String> mobNumberList17 = userService.readUserMobileNumber(day);
			List<String> userList = new ArrayList<>();
			log.infoLog("短信", "开始注册为投资用户17天红包短信");
			if (mobNumberList17.size() > 0) {
				for (String mobileNumber : mobNumberList17) {
					// 根据手机号 查询有没有 该手机号下没有可用的红包 没有在给发送红包
//					String mobileNumber2="13731166909";
						try {
							User user = userService.getUserByMobileNumber(mobileNumber);
							createReadpacket(user,228, mobileNumber, 10, 0,1000.00, 1, "0","新手专享","money",15);
							//用户  模板编号 手机号            红包金额 加息券额度  投资限额  投资项目限额 是否新手可用（0可用） 红包名称  现金红包 有效期
							createReadpacket(user,228, mobileNumber, 50, 0,1500.00, 3, "0","新手专享","money",15);
							createReadpacket(user,228,mobileNumber,  0, 0.01,0,     0, "0","新手专享","rate",15);
							Sms sms = new Sms();
							sms.setId(IdGenerator.randomUUID());
							sms.setUserId(user.getUserId());
							sms.setMobileNumber(mobileNumber);
							sms.setContent(content17);
							sms.setTime(new Date());
							sms.setType(SmsConstants.INFORMATION);
							smsDao.insert(sms);
							System.out.println("**********************发送短信*******************************"); 
							SMSSend.batchSend1(mobileNumber, content17);
							userList.add(user.getUserId());
						} catch (Exception e) {
							e.printStackTrace();
							log.errLog("短信", e+"注册未投资用户17天短信");
						}
				}
				log.infoLog("短信", "结束注册未投资17天发送红包的用户数量：" + userList.size()
						+ "结束注册未投资17天发送红包的用户" + userList.toString(), 1);
			}
		

	}
	/**
	 * 创建红包
	 * @param user 用户
	 * @param mobileNumber 手机号
	 * @param money 金额
	 * @param investMoney 
	 * @param investCycle
	 * @param useLoanType
	 * @param redpacketName 红包名称
	 * @param redpacketType 红包类型现金还是加息券
	 * @param redpacketDeadLine 有效时间
	 * @return
	 */
	public int createReadpacket(User user, int ruleId,String mobileNumber, double money,double rate,
			double investMoney, int investCycle, String useLoanType,String redpacketName,String redpacketType,int  redpacketDeadLine) {
		try {
			RedPacket redpacket = new RedPacket();
			redpacket.setName(redpacketName);
			redpacket.setMoney(money);
			redpacket.setUserId(user.getUserId());
			redpacket.setMobileNumber(mobileNumber);
			Date d = new Date();
			redpacket.setCreateTime(d);
			redpacket.setDeadLine(DateUtil.addDay(d, redpacketDeadLine));
			redpacket.setSendStatus("unused");
			redpacket.setType(redpacketType);
			redpacket.setRuleId(ruleId);
			redpacket.setUseLoanType(useLoanType);
			redpacket.setRate(rate);
			redpacket.setUsageDetail("invest");
			redpacket.setInvestMoney(investMoney);
			redpacket.setUsageRule("投资可用");
			redpacket.setInvestCycle(investCycle);
			redPacketDao.insert(redpacket);
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;

		}

	}

	@Scheduled(cron="0 0/5 * * * ?")
	public void handleSendedOperations() {
		// 查找请求表里面，等待返回的数据，且请求时间在十分钟以外。
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.infoLog("yeepay-轮询开始", sdf.format(new Date()));
		List<TrusteeshipOperation> tos = trusteeshipOperationService
				.getUnCallbackOperations(10);
		if (CollectionUtils.isEmpty(tos)) {
			log.infoLog("yeepay-轮询结束", "没有可以轮询的数据");
			return;
		}
		for (TrusteeshipOperation to : tos) {
			try {
				if (to.getType().equals(
						TrusteeshipYeepayConstants.OperationType.INVEST)
						|| to.getType()
								.equals(TrusteeshipYeepayConstants.OperationType.AUTOINVEST)) {
					// 投标
					investRecord(to);
				}
			} catch (Exception e) {
				log.errLog("轮询异常", e);
			}
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

					/**
					 * 第三方推送对列
					 */
					Map<String, Object> map = new HashMap<>();

					map.put("investId", invest.getId());
					map.put("loanId", invest.getLoanId());
					map.put("pushTime", new Date());

					DRJedisMQ
							.product("pushinvest", FastJsonUtil.objToJson(map));

					/**
					 * 十一随即返现活动
					 */
					Date investTime = invest.getTime();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date startDate = null, endDate = null;
					try {
						startDate = format.parse("2016-09-26 23:59:59");
						endDate = format.parse("2016-10-03 23:59:59");
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (investTime != null && startDate.getTime() < investTime.getTime()
							&& investTime.getTime() < endDate.getTime()) {
						DRJedisMQ
						.product("national_invest", invest.getId());
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

					/**
					 * 第三方推送队列
					 */
					Map<String, Object> map = new HashMap<>();

					map.put("investId", invest.getId());
					map.put("loanId", invest.getLoanId());
					map.put("pushTime", new Date());

					DRJedisMQ
							.product("pushinvest", FastJsonUtil.objToJson(map));
					DRJedisMQ.product("activity_invest", invest.getId());			
					
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

}
