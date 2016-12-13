package com.duanrong.schedule;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import util.Log;
import util.MyStringUtils;
import util.XMLUtil;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.service.PlatformAccountService;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.invest.InvestConstants;
import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.model.InvestRedpacket;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.service.LoanService;
import com.duanrong.business.platformtransfer.service.PlatformTransferService;
import com.duanrong.business.repay.dao.RepayDao;
import com.duanrong.business.repay.model.Repay;
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
import com.duanrong.util.ArithUtil;
import com.duanrong.util.DateUtil;
import com.duanrong.util.Dom4jUtil;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.InterestUtil;
import com.duanrong.util.jedis.DRJedisMQ;
import com.duanrong.util.json.FastJsonUtil;
import com.duanrong.util.mail.MailSendInfo;
import com.duanrong.util.mail.SimpleEmailSend;
import com.duanrong.yeepay.service.TrusteeshipGiveMoneyToBorrowerService;
import com.duanrong.yeepay.service.TrusteeshipPlatformTransferService;
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
	
	@Resource
	TrusteeshipPlatformTransferService trusteeshipPlatformTransferService;
	
	@Resource
	PlatformTransferService platformTransferService;
	
	@Resource
	UserAccountService userAccountService;
	
	@Resource
	PlatformAccountService platformAccountService;
	
	@Resource
	LoanService loanService;
	
	@Resource
	RepayDao repayDao;
	
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

	/*@Scheduled(cron = "0 30 8-23 * * ?")*/
	public void sendAllowanceAndRedpacketSchedule() {
		System.out.println("***************************************");
		System.out.println("***********后台补息轮询开始*************");
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date()));
		List<InvestRedpacket> irs = investService.getInvestRedpacketList();
		if (irs.isEmpty()) {
			log.infoLog("补息奖励轮询执行", "此次没有查询到相关的InvestRedpacket记录");
			return;
		}
		String host = userService.getConfigById("mail_smtp").getValue();
		String from = userService.getConfigById("mail_username").getValue();
		String password = userService.getConfigById("mail_password").getValue();
		for (InvestRedpacket ir : irs) {

			if (ir.getSendAllowanceStatus() != 1
					|| ir.getSendRedpacketStatus() != 1) {
				Invest invest = investService.getInvest(ir.getInvestId());
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					// 发放补息
					if (ir.getSendAllowanceStatus() != 1
							&& ir.getInvestAllowanceInterest() > 0) {
						Date date = ir.getGiveMoneyTime();
						if (date == null) {
							date = new Date();
						}
						Integer dayDifference = DateUtil.dayDifference(
								sdf.format(invest.getTime()), sdf.format(date));// 补多少天的利息
						String remarks = "尊敬的短融网客户：您投资" + ir.getLoanName()
								+ "项目，从投资次日至放款之日共补息" + dayDifference + "天，计"
								+ ir.getInvestAllowanceInterest()
								+ "元，现已发放到您的账户，快去交易记录查看吧。";
						String status = trusteeshipPlatformTransferService
								.platformTransferTrusteeship(
										ir.getAllowanceOrder(), ir.getUserId(),
										ir.getInvestAllowanceInterest(),
										remarks, "allowanceInterest",
										ir.getLoanId(), ir.getId());
						log.infoLog("补息奖励轮询执行",
								"补息奖励轮询执行  发送后：InvestRedpacket:" + ir.getId()
										+ "发补息状态：" + status);
						if ("success".equals(status)) {
							try {
								userAccountService
										.ptTransferIn(
												ir.getUserId(),
												ir.getInvestAllowanceInterest(),
												BusinessEnum.reward,
												ir.getLoanName()
														+ "补息："
														+ dayDifference
														+ "天共计"
														+ ir.getInvestAllowanceInterest()
														+ "元",
												MyStringUtils.append(
														"investID:",
														ir.getInvestId(),
														"  借款ID:",
														ir.getLoanId(),
														"  补息：",
														ir.getInvestAllowanceInterest()),
												ir.getAllowanceOrder());
								ir.setSendAllowanceStatus(1);
								ir.setSendAllowanceResult("发送成功");
								
								platformAccountService.transferOut(ir.getInvestAllowanceInterest(), BusinessEnum.reward, "补息", ir.getAllowanceOrder());
								
								String content = "尊敬的短融网客户：您投资"
										+ ir.getLoanName() + "项目，从投资次日至放款之日共补息"
										+ dayDifference + "天，计"
										+ ir.getInvestAllowanceInterest()
										+ "元，现已发放到您的账户，快去交易记录查看吧。";
								sendSmsAndInfo(ir.getUserId(), "满标前补息到账通知", content, "您投资"
										+ ir.getLoanName()
										+ "项目,满标前补息"
										+ dayDifference
										+ "天，金额"
										+ ir.getInvestAllowanceInterest()
										+ "元已到账，请查收。",
										SmsConstants.ALLOWANCE);
							
								// 跟新invest已获收益
								invest.setPaidInterest(invest.getPaidInterest()
										+ ir.getInvestAllowanceInterest());
								investService.update(invest);
							} catch (Exception e) {
								ir.setSendAllowanceStatus(-1);
								ir.setSendAllowanceResult(ExceptionUtils
										.getStackTrace(e));
								log.errLog("投资补息本地发放异常", e);
								String content = "投资补息本地发放异常，项目ID："
										+ ir.getLoanId() + ", 投资ID："
										+ ir.getInvestId()
										+ ", investRedpacketID：" + ir.getId()
										+ " 失败原因：" + e.toString();
								sendEmail(host, from, password, content,
										"投资补息本地发放异常");
							}
						} else {
							ir.setSendAllowanceStatus(-1);
							ir.setSendAllowanceResult(status);
							String content = "投资补息发放异常，项目ID：" + ir.getLoanId()
									+ ", 投资ID：" + ir.getInvestId()
									+ ", investRedpacketID：" + ir.getId()
									+ " 失败原因：" + status;
							sendEmail(host, from, password, content, "投资补息异常");
						}
					} else {
						ir.setSendAllowanceStatus(1);
						ir.setSendAllowanceResult("补息金额为 0");
					}
				} catch (Exception e) {
					ir.setSendAllowanceStatus(-1);
					ir.setSendAllowanceResult(ExceptionUtils.getStackTrace(e));
					log.errLog("后台还款派息 InvestRedpacket: " + ir.getId()
							+ "补息发放失败", e);
					String content = "投资补息发放异常，项目ID：" + ir.getLoanId()
							+ ", 投资ID：" + ir.getInvestId()
							+ ", investRedpacketID：" + ir.getId() + " 失败原因："
							+ e.toString();
					sendEmail(host, from, password, content, "投资补息异常");
				} finally {
					ir.setSendAllowanceTime(new Date());
				}

				try {
					if (ir.getSendRedpacketStatus() != 1
							&& ir.getRewardMoney() > 0) {
						RedPacket packet = redPacketDao.get(ir.getRepackedId());
						if (invest.getRedpacketId() <= 0) {
							ir.setSendRedpacketStatus(1);
							ir.setSendRedpacketTime(new Date());
							ir.setSendRedpacketResult("未使用红包");
							investService.updatetInvestRedpacket(ir);
							continue;
						}
						if (packet == null) {
							log.errLog("后台还款红包", "加息券不存在");
							ir.setSendRedpacketStatus(1);
							ir.setSendRedpacketTime(new Date());
							ir.setSendRedpacketResult("红包不存在");
							continue;
						}
						User user = userService.get(ir.getUserId());
						if ("used".equals(packet.getSendStatus())
								&& packet.getMobileNumber().equals(
										user.getMobileNumber())) {
							String str = "【 " + packet.getName();
							if ("rate".equals(packet.getType())) {
								str += " 加息"
										+ ArithUtil.round(
												packet.getRate() * 100, 2)
										+ "% 】加息券";
							}
							if ("money".equals(packet.getType())) {
								str += "】现金券";
							}
							String remarks = "尊敬的短融网客户：" + ir.getLoanName()
									+ "使用" + str + "所得现金奖励已发放到您短融网账户，请您注意查收。";
							String status = trusteeshipPlatformTransferService
									.platformTransferTrusteeship(
											ir.getRepackedOrder(),
											ir.getUserId(),
											ir.getRewardMoney(), remarks,
											"redpacket", ir.getLoanId(),
											ir.getId());
							log.infoLog("加息奖励 发送",
									"InvestRedpacket:" + ir.getId() + "发红包状态："
											+ status);
							if ("success".equals(status)) {
								try {
									packet.setSendStatus("sended");
									packet.setSendTime(new Date());
									redPacketDao.update(packet);
									// TODO这个地方发的红包吧？？
									ir.setSendRedpacketStatus(1);
									ir.setSendRedpacketResult("发送成功");
									DecimalFormat df = new java.text.DecimalFormat(
											"#.##");
									String s = packet.getName();
									if ("rate".equals(packet.getType())) {
										s += df.format(packet.getRate() * 100)
												+ "%加息券:投资" + ir.getLoanName();
									} else {
										s += df.format(packet.getMoney())
												+ "元现金券:投资" + ir.getLoanName();
									}
									userAccountService.ptTransferIn(
											ir.getUserId(),
											ir.getRewardMoney(),
											BusinessEnum.reward,
											s,
											"借款ID:" + ir.getLoanId() + "投资ID:"
													+ ir.getInvestId()
													+ "加息奖励:"
													+ ir.getRewardMoney(),
											ir.getRepackedOrder());
									platformAccountService.transferOut(ir.getRewardMoney(), BusinessEnum.reward, "加息奖励", ir.getRepackedOrder());
							
									String content = "尊敬的短融网用户您好，您投资"
											+ ir.getLoanName() + "项目， 使用的"
											+ str + "所获得的现金奖励"
											+ ir.getRewardMoney()
											+ "元，现已发放到您的账户，快去交易记录查看吧！";
									
									String content1 = "您投资"
											+ ir.getLoanName()
											+ "项目,使用"
											+ str
											+ "，加息收益"
											+ ir.getRewardMoney()
											+ "元已到账，请查收。";
									sendSmsAndInfo(ir.getUserId(), "优惠券奖励到账通知",
											content,content1, SmsConstants.ALLOWANCE);		
								} catch (Exception e) {
									ir.setSendRedpacketStatus(-1);
									ir.setSendRedpacketResult(ExceptionUtils
											.getStackTrace(e));
									log.errLog("加息券发放本地处理异常", e);
									String content = "加息券发放本地处理异常，项目ID："
											+ ir.getLoanId() + ", 投资ID："
											+ ir.getInvestId()
											+ ", investRedpacketID："
											+ ir.getId() + " 失败原因："
											+ ExceptionUtils.getStackTrace(e);
									sendEmail(host, from, password, content,
											"加息券发放本地处理异常");
								}
							} else {
								ir.setSendRedpacketStatus(-1);
								ir.setSendRedpacketResult(status);
								String content = "加息券发放异常，项目ID："
										+ ir.getLoanId() + ", 投资ID："
										+ ir.getInvestId()
										+ ", investRedpacketID：" + ir.getId()
										+ " 失败原因：" + status;
								sendEmail(host, from, password, content,
										"加息券发放异常");
							}
						} else {
							ir.setSendRedpacketStatus(1);
							ir.setSendRedpacketResult("该加息券不符合发送规则, redpacketId:"
									+ ir.getRepackedId()
									+ ", packetStatus:"
									+ packet.getSendStatus()
									+ ", rewardMoney:"
									+ ir.getRewardMoney());
						}
					} else {
						ir.setSendRedpacketStatus(1);
						ir.setSendRedpacketResult("红包金额为 0");
					}
				} catch (Exception e) {
					ir.setSendRedpacketStatus(-1);
					ir.setSendRedpacketResult(ExceptionUtils.getStackTrace(e));
					log.errLog("investId : " + ir.getInvestId() + "加息券奖励发送失败",
							e);
					String content = "加息券发放异常，项目ID：" + ir.getLoanId()
							+ ", 投资ID：" + ir.getInvestId()
							+ ", investRedpacketID：" + ir.getId() + " 失败原因："
							+ ExceptionUtils.getStackTrace(e);
					sendEmail(host, from, password, content, "加息券发放异常");
				} finally {
					ir.setSendRedpacketTime(new Date());
				}
			}
			investService.updatetInvestRedpacket(ir);
		}
		log.infoLog("后台还款派息结束", "后台还款派息结束");
		System.out.println("***************************************");
		System.out.println("***********后台还款补息轮询结束*************");
	}
	
	
	
	//@Scheduled(cron = "0 0/5 15 * * ?")
		public void initInvestRedpacketSchedule() {
			List<Repay> repays = repayDao.getRepayLoan();
			System.out.println(repays.size());
			for (Repay repay : repays) {
				Loan loan = repay.getLoan();
				Invest in = new Invest();
				in.setLoanId(repay.getLoanId());
				List<Invest> invests = investService.getInvestByLoan(in);
				for (Invest invest : invests) {
					// 计算加息卷金额
					double d = 0;
					if (invest.getRedpacketId() > 0) {
						d = redPacketRepay(invest.getRedpacketId(),
								loan.getOperationType(), loan.getRepayType(),
								invest.getMoney(), loan.getDay(),
								loan.getDeadline());
					}
					log.infoLog("计算加息奖励信息：", "投资明细ID" + invest.getId() + "加息券ID"
							+ invest.getRedpacketId() + "奖励金额：" + d);
					// 如果补息或者奖励金额大于0,则 创建补息和奖励记录
					if (invest.getInvestAllowanceInterest() > 0 || d > 0) {
						InvestRedpacket ir = new InvestRedpacket();
						ir.setId(IdGenerator.randomUUID());
						ir.setInvestId(invest.getId());
						ir.setLoanId(repay.getLoanId());
						if (repay.getSendAllowancStatus() == 0) {
							ir.setSendAllowanceStatus(0);
						} else {
							ir.setSendAllowanceStatus(1);
						}
						ir.setAllowanceOrder("TZBX" + invest.getId());
						ir.setInvestAllowanceInterest(ArithUtil.round(
								invest.getInvestAllowanceInterest(), 2));
						ir.setRewardMoney(d);
						ir.setRepackedOrder("JXJL" + invest.getId());
						ir.setSendRedpacketStatus(0);
						ir.setRepackedId(invest.getRedpacketId());
						ir.setCreateTime(new Date());
						ir.setUserId(invest.getInvestUserID());
						investDao.insertInvestRedpacket(ir);
					}
				}
			}
		}

		/**
		 * 生成加息券金额
		 * 
		 * @param id
		 */
		private double redPacketRepay(int redId, String operationType,
				String repayType, double money, int days, int deadLine) {
			double d = 0;
			// 大于0表示该笔投资使用加息券
			if (redId > 0) {
				RedPacket packet = redPacketDao.get(redId);
				String type = packet.getType();
				String status = packet.getSendStatus();
				double rate = packet.getRate();
				if (23 == packet.getRuleId()) {// 如果红包规则是23则金额最高计算上限是5万
					money = money > 50000 ? 50000 : money;
				}
				if ("rate".equals(type) && "used".equals(status)) {
					int periods = 0;
					if (MyStringUtils.equals("天", operationType)) {
						periods = days;
					} else {
						periods = deadLine;
					}
					d = InterestUtil.getInterestByPeriod(money, rate, periods,
							operationType, repayType);
				} else if ("money".equals(type) && "used".equals(status)) {
					// 固定金额奖励的情况下
					d = packet.getMoney();
				}
			}
			return ArithUtil.round(d, 2);
		}

	/**
	 * 发送邮件
	 * 
	 * @param host
	 * @param from
	 * @param password
	 * @param loan
	 * @param e
	 */
	private void sendEmail(String host, String from, String password,
			String content, String subject) {
		MailSendInfo sendInfo = new MailSendInfo();
		sendInfo.setFromAddress(from);
		sendInfo.setMailServerHost(host);
		sendInfo.setPassword(password);
		sendInfo.setUserName(from);
		sendInfo.setContent(content);
		sendInfo.setSubject(subject);
		SimpleEmailSend send = new SimpleEmailSend();
		try {
			sendInfo.setToAddress("guolixiao@duanrong.com");
			send.sendHtmlMail(sendInfo);
			sendInfo.setToAddress("zhangjunying@duanrong.com");
			send.sendHtmlMail(sendInfo);			
			sendInfo.setToAddress("zhaolei@duanrong.com");
			send.sendHtmlMail(sendInfo);
			 
		} catch (MessagingException ex) {
			log.errLog("邮件发送失败", ex);
		}
	}
	
	/**
	 * 发送站内信和短信
	 * 
	 * @param userId
	 * @param title
	 * @param content
	 * @param type
	 */
	private void sendSmsAndInfo(String userId, String title, String content, String infomation,
			String type) {
		// 发送短信和站内信
		try {
			smsService.sendSms(userId, content, type);
			informationService.sendInformation(userId, title, infomation);
		} catch (Exception ex) {
			log.errLog("用户ID:" + userId + "短信和站内信发送失败", "content:" + content
					+ ex);
		}

	}

}
