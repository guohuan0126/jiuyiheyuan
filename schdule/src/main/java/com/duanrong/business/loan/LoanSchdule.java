package com.duanrong.business.loan;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import util.Log;
import util.MyStringUtils;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.service.PlatformAccountService;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.model.InvestRedpacket;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.service.LoanService;
import com.duanrong.business.platformtransfer.service.PlatformTransferService;
import com.duanrong.business.repay.dao.RepayDao;
import com.duanrong.business.repay.dao.RepayInvestDao;
import com.duanrong.business.repay.model.Repay;
import com.duanrong.business.repay.service.RepayService;
import com.duanrong.business.sms.SmsConstants;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.user.dao.RedPacketDao;
import com.duanrong.business.user.model.RedPacket;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.InformationService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.InterestUtil;
import com.duanrong.util.mail.MailSendInfo;
import com.duanrong.util.mail.SimpleEmailSend;
import com.duanrong.yeepay.service.TrusteeshipGiveMoneyToBorrowerService;
import com.duanrong.yeepay.service.TrusteeshipPlatformTransferService;

/**
 * 
 * @author xiao
 * 
 */
/*@Component*/
public class LoanSchdule {

	@Resource
	LoanService loanService;

	@Resource
	SmsService smsService;

	@Resource
	UserService userService;

	@Resource
	TrusteeshipGiveMoneyToBorrowerService trusteeshipGiveMoneyToBorrowerService;

	@Resource
	RepayService repayService;

	@Resource
	RepayDao repayDao;

	@Resource
	RepayInvestDao repayInvestDao;

	@Resource
	InvestService investService;

	@Resource
	InvestDao investDao;

	@Resource
	TrusteeshipPlatformTransferService trusteeshipPlatformTransferService;

	@Resource
	PlatformTransferService platformTransferService;

	@Resource
	UserAccountService userAccountService;
	
	@Resource
	PlatformAccountService platformAccountService;
	
	@Resource
	RedPacketDao redPacketDao;

	@Resource
	InformationService informationService;

	@Resource
	Log log;

	/**
	 * 放款提醒短信，18到23点 整点发送 轮询loan表，今天和昨天等待复核的项目
	 */
	//@Scheduled(cron = "0 0 18-23 * * ?")
	public void giveMoneyToBorrowwersNotice() {
		log.infoLog(
				"放款提醒",
				"放款提醒轮询开始....."
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date()));
		List<Loan> loans = loanService.getLoanForGaveMoneyToBorrower(1);
		String loanNames = "";
		String loanIds = "";
		for (Loan loan : loans) {
			loanNames += loan.getName() + "、";
			loanIds += loanIds + "、";
		}
		log.infoLog("放款提醒", "共有" + loans.size() + "等待放款|||" + loanIds);
		if (loanNames != null && loanNames.trim().length() > 0) {
			if (loanNames.endsWith("、")) {
				loanNames = loanNames.substring(0, loanNames.length() - 1);
			}
			smsService.sendSmsForMobile("15810239451", "项目" + loanNames
					+ "已募集完成，请尽快放款。", null);
		}
		log.infoLog("放款提醒", "放款提醒轮询结束.....");

	}

	/**
	 * 自动放款轮询 整点轮询
	 */
	/*@Scheduled(cron = "0 10,30,50 7-23 * * ?")*/
	public void giveMoneyToBorrowers() {
		String host = userService.getConfigById("mail_smtp").getValue();
		String from = userService.getConfigById("mail_username").getValue();
		String password = userService.getConfigById("mail_password").getValue();

		List<Loan> loans = loanService.getLoanForGaveMoneyToBorrower(10);
		log.infoLog(
				"自动放款",
				"自动放款轮询开始..... ,共轮询出 size："
						+ loans.size()
						+ "条待放款项目，"
						+ new SimpleDateFormat("yyyy-MM-dd dd:mm:ss")
								.format(new Date()));
		for (Loan loan : loans) {
			String result = "";
			try {
				result = trusteeshipGiveMoneyToBorrowerService
						.giveMoneyToBorrower("system", loan.getId());
				if (!"放款成功".equals(result)) {
					log.errLog("自动放款失败 loanId:" + loan.getId(), result);
					String content = "自动放款失败，项目ID：" + loan.getId() + ", 项目名称："
							+ loan.getName() + ", 失败原因：" + result;
					sendEmail(host, from, password, content, "自动放款失败");
				}
			} catch (Exception e) {
				log.errLog("自动放款失败 loanId:" + loan.getId(), e);
				String content = "自动放款失败，项目ID：" + loan.getId() + ", 项目名称："
						+ loan.getName() + ", 失败原因：" + e.toString();
				sendEmail(host, from, password, content, "自动放款失败");
			}			
		
		}
		log.infoLog(
				"自动放款",
				"自动放款轮询结束....."
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date()));
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
	 * 补息和加息奖励发送
	 */
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
