package com.duanrong.schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import base.pagehelper.PageInfo;

import com.duanrong.business.account.model.UserAccount;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.user.dao.AccountCheckingDao;
import com.duanrong.business.user.model.AccountChecking;
import com.duanrong.business.user.service.AllUserBillService;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.mail.MailSendInfo;
import com.duanrong.util.mail.SimpleEmailSend;

import util.Log;

/**
 * 账户平衡调度
 * 
 * @author  guohuan
 * @version 2016年12月12日
 */
/*@Component*/
public class AccountBalanceSchedule {
	
	@Resource
	AllUserBillService allUserBillService;
	
	@Resource
	Log log;
	
	@Resource
	UserService userService;
	
	@Resource
	UserMoneyService ubs;
	
	@Resource
	UserAccountService uas;
	
	@Resource
	AccountCheckingDao accountCheckingDao;
	
	/**
	 * 账户平衡调度 每日凌晨0点执行
	 */
	@Scheduled(cron = "0 5 0 * * ?")
	public void AccountCheckingSchdule() {
		System.out.println("********** 每日账户平衡调度开始  ************");
		log.infoLog(
				"schdule",
				"账户平衡调度开始............"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date()));
		try {
			allUserBillService.accountChecking();
		} catch (Exception e) {
			log.errLog("用户平衡调度 allUserBillService.accountChecking()", e);
		}
		log.infoLog(
				"schdule",
				"账户平衡调度结束............"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date()));
		System.out.println("********** 每日账户平衡调度结束  ************");
	}
	
	/**
	 * 本地账户平衡调度 每日8,11,13,15,18,20点执行
	 */
	@Scheduled(cron = "0 0 8,11,13,15,18,20 * * ? ")
	public void LocalAccountCheckingSchdule() {
		List<UserAccount> u = new ArrayList<>();
		PageInfo<UserAccount> pageLite = uas.getUserAccounts(1, 100, null);
		int totalPage = pageLite.getTotalPage();
		try {
			for (int i = 0; i < totalPage; i++) {
				List<UserAccount> accounts = uas.getUserAccounts(i, 100, null)
						.getResults();
				for (UserAccount account : accounts) {
					String userId = account.getUserId();
					Map<String, Object> map = (Map<String, Object>) ubs
							.queryUserMoney(userId);
					if (map != null) {
						double money = (double) map.get("money");
						double frozen = (double) map.get("frozen");
						if (money != account.getAvailableBalance()
								|| frozen != account.getFreezeAmount()
								|| ArithUtil.round(
										account.getAvailableBalance()
												+ account.getFreezeAmount(), 2) != account
										.getBalance()) {
							account.setMoney(money + frozen);
							account.setFreeze(frozen);
							u.add(account);
						}
					} else if (account.getAvailableBalance() != 0
							|| account.getFreezeAmount() != 0) {
						account.setMoney(0D);
						account.setFreeze(0D);
						u.add(account);
					}

				}
			}

			String host = userService.getConfigById("mail_smtp").getValue();
			String from = userService.getConfigById("mail_username").getValue();
			String password = userService.getConfigById("mail_password")
					.getValue();
			StringBuffer sb = new StringBuffer();
			sb.append("["
					+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())
					+ "日], 本地账户检查<br>");
			sb.append("<table style=\"border:solid 1px #E8F2F9\">");
			sb.append("<tr style=\"background-color: #428BCA; color:#ffffff\"><th>userId</th><th>UserAccount总额</th><th>>UserAccount冻结金额</th><th>UserBill总额</th><th>UserBill冻结金额</th></tr>");
			if (!u.isEmpty()) {
				for (UserAccount ua : u) {
					sb.append("<tr><td>" + ua.getUserId() + "</td><td>"
							+ ArithUtil.round(ua.getBalance(), 2) + "</td>");
					sb.append("<td>" + ua.getFreezeAmount() + "</td>");
					sb.append("<td>" + ua.getMoney() + "</td>");
					sb.append("<td>" + ua.getFreeze() + "</td>");
					sb.append("</tr>");
				}
			}
			sendEmail(host, from, password, sb.toString(), "本地账户检查");
		} catch (Exception e) {
			log.errLog("本地账户检查", e);
		}
	}
	
	/**
	 * 账户不平衡用户信息 每天9点隔执行一次
	 */
	@Scheduled(cron = "0 0 9 * * ? ")
	public void account() {
		String host = userService.getConfigById("mail_smtp").getValue();
		String from = userService.getConfigById("mail_username").getValue();
		String password = userService.getConfigById("mail_password").getValue();
		StringBuffer sb = new StringBuffer();
		sb.append("[" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())
				+ "日], 账户不平衡信息<br>");
		List<AccountChecking> accounts = accountCheckingDao
				.getAccountListByWhere();
		sb.append("<table style=\"border:solid 1px #E8F2F9\">");
		sb.append("<tr style=\"background-color: #428BCA; color:#ffffff\"><th>userId</th><th>本地账户总额</th><th>易宝账户总额</th><th>本地账户余额</th><th>易宝账户余额</th><th>本地冻结金额</th><th>易宝冻结金额</th></tr>");
		for (AccountChecking account : accounts) {
			sb.append("<tr><td>" + account.getUserId() + "</td><td>"
					+ ArithUtil.round(account.getAvailableAmount(), 2)
					+ "</td>");
			sb.append("<td>" + account.getEbaoAvailableAmount() + "</td>");
			sb.append("<td>" + ArithUtil.round(account.getBalance(), 2)
					+ "</td>");
			sb.append("<td>" + account.getEbaoBalance() + "</td>");
			sb.append("<td>" + ArithUtil.round(account.getFreezeAmount(), 2)
					+ "</td>");
			sb.append("<td>" + account.getEbaoFreezeAmount() + "</td></tr>");
		}
		sendEmail(host, from, password, sb.toString(), "账户不平衡信息");
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

}
