package com.duanrong.schedule;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import util.Log;
import util.PhoneNoAttributionUtils;
import util.RedisCacheKey;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.model.UserAccount;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.service.LoanService;
import com.duanrong.business.node.model.Node;
import com.duanrong.business.node.service.NodeService;
import com.duanrong.business.repay.model.RepayMail;
import com.duanrong.business.repay.service.RepayService;
import com.duanrong.business.sms.SmsConstants;
import com.duanrong.business.sms.dao.SmsDao;
import com.duanrong.business.sms.model.Sms;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.user.dao.AccountCheckingDao;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.AccountChecking;
import com.duanrong.business.user.model.Role;
import com.duanrong.business.user.model.Template;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.AllUserBillService;
import com.duanrong.business.user.service.RedPacketService;
import com.duanrong.business.user.service.RoleService;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.jsonservice.util.RegexInput;
import com.duanrong.sms.SMSSend;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.IdUtil;
import com.duanrong.util.jedis.DRJedisCacheUtil;
import com.duanrong.util.mail.MailSendInfo;
import com.duanrong.util.mail.SimpleEmailSend;

/**
 * 
 * @author xiao
 * 
 */
/*@Component*/
public class UserSchdule {

	@Resource
	AllUserBillService allUserBillService;

	@Resource
	Log log;

	@Resource
	UserService userService;

	@Resource
	RedPacketService redPacketService;

	@Resource
	RoleService roleService;

	@Resource
	SmsDao smsDao;

	@Resource
	UserDao userDao;

	@Resource
	AccountCheckingDao accountCheckingDao;

	@Resource
	UserMoneyService ubs;

	@Resource
	UserAccountService uas;
	
	@Resource
	RepayService repayService;
	
	@Resource
	NodeService nodeService;
	
	@Resource
	LoanService loanService;
	
	@Resource
	SmsService smsService;

	/**
	 * 手机号归属地 每日凌晨0点执行
	 */
	@Scheduled(cron = "0 18 * * * ?")
	public void PhoneSchdule() {
		System.out.println("=====================手机归属地调度开始=======================");
		log.infoLog(
				"schdule",
				"手机号归属地调度开始............"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date()));
		try {
			updateUsers();
		} catch (Exception e) {
			log.errLog("手机号归属地调度 userService.updateUsers()", e);
		}
		log.infoLog(
				"schdule",
				"手机号归属地调度结束............"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date()));
		System.out.println("=====================手机归属地调度结束=======================");
	}

	public void updateUsers() {
		PageInfo<User> pageInfo = userDao.getUsersPhone(1, 50);

		int totalPage = pageInfo.getTotalPage();

		int count = 0;
		for (int i = 1; i <= totalPage; i++) {
			PageInfo<User> p = userDao.getUsersPhone(i, 50);
			List<User> users = p.getResults();

			for (User user : users) {
				try {
					if (!StringUtils.isBlank(user.getMobileNumber())
							&& (RegexInput.checkMobilePhone(user
									.getMobileNumber()))) {
						String[] strs = PhoneNoAttributionUtils
								.getAttributions(user.getMobileNumber());
						if (strs[0] != null) {
							user.setPhoneNoAttribution(strs[0]);
						}
						if (strs[1] != null) {
							user.setPhoneNoCity(strs[1]);
						}
						if (StringUtils.isBlank(strs[0])
								&& StringUtils.isBlank(strs[1])) {
							continue;
						} else {
							userDao.updateUserPhoneArea(user);
							count++;
						}
					}
				} catch (Exception e) {
					log.errLog("手机号归属地调度执行", e);
				}		
			}

		}
		log.infoLog("手机号归属地调度执行完毕后更新数量：", count + "");

	}

	/**
	 * 红包过期调度 每天凌晨3点5点隔执行一次
	 */
	@Scheduled(cron = "0 0 3,5 * * ?")
	public void redpacketSchdule() {

		System.out.println("********** 红包过期调度开始  ************"
				+ new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
						.format(new Date()));
		try {
			redPacketService.updateExpired();
		} catch (Exception e) {
			log.errLog("红包过期轮询异常", e);
			e.printStackTrace();
		}
		log.infoLog(
				"schdule",
				"红包过期调度结束............"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date()));
	}

	/**
	 * 红包过到期提醒每天9点隔执行一次
	 */
	@Scheduled(cron = "0 0 9 * * ? ")
	public void redpacketExpireSchdule() {

		List<User> users = userService.getExpireRedpacket();
		for (User user : users) {
			String content = "小主，发现您有未使用的优惠券，2天后过期，记得及时使用哦，回TD退订";
			SMSSend.batchSend1(user.getMobileNumber(), content);
			Sms sms = new Sms();
			sms.setId(IdGenerator.randomUUID());
			sms.setUserId(user.getUserId());
			sms.setMobileNumber(user.getMobileNumber());
			sms.setContent(content);
			sms.setTime(new Date());
			sms.setType(SmsConstants.REDPACKET);
			smsDao.insert(sms);
		}
		log.infoLog(
				"schdule",
				"红包到期提醒............"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date()));
	}

	// @Scheduled(cron = "0/1 * * * * ? ")
	public void userInfo() {
		int pageNo = 1;
		PageInfo<User> pageLite = userService.pageLite(pageNo, 50, null);
		int totalPage = pageLite.getTotalPage();
		try {
			for (int i = 0; i < totalPage; i++) {
				List<User> users = userService.pageLite(i + 1, 50, null)
						.getResults();
				for (User user : users) {
					String userId = user.getUserId();
					try {
						// 没有实名认证的用户或者测试账户没有手机号的用户不进行查询
						/*
						 * if (StringUtils.isAnyBlank(user.getMobileNumber(),
						 * user.getRealname(), user.getIdCard())) { continue; }
						 */
						List<Role> roles = roleService.getRolesByUserId(userId);
						if (roles == null || roles.size() == 0) {
							continue;
						}
						Map<String, Object> map = ubs.queryUserMoney(userId);
						uas.initUserAccount(userId, (double) map.get("money"),
								(double) map.get("frozen"));
					} catch (Exception e) {

					}
				}
			}
		} catch (Exception e) {
			log.errLog("账户平衡调度", e);
		}
	}
	
	/**
	 * 生日当天上午11点
	 */
	@Scheduled(cron = "0 0 11 * * ?")
	public void giveMoneyToBorrowers(){
		try{
			log.infoLog("发送生日短信", "开始");
			List<User> users = userService.getBigClientBirthday();
			for(User user: users){
				redPacketService.create4Birthday(user);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.errLog("发送生日短信", e);
		}
		
	}
	/**
	 * 还款提醒调度
	 * author guohuan
	 * 2016年12月12日
	 */
	/*@Scheduled(cron = "0 0 8 * * ?")*/
	public void sendRepayRemindMail(){		
		List<RepayMail> userIds = repayService.getInterval(3);
		Template t1 = userService.getTemplateById("repay_email_header");
		Template t2 = userService.getTemplateById("repay_email_data");
		Template t3 = userService.getTemplateById("repay_email_footer");
		String header = t1 == null ? "" : t1.getContent();
		String footer = t3 == null ? "" : t3.getContent();
		
		String host = userService.getConfigById("mail_smtp").getValue();
		String from = userService.getConfigById("mail_username").getValue();
		String password = userService.getConfigById("mail_password").getValue();
		for(RepayMail rm : userIds){			
			String data = t2 == null ? "" : t2.getContent();
			String headerd = header.replace("#{userId}", rm.getUserId()+"--"+rm.getRealName());
			String rd = "";
			for(int i = 0; i<= 2; i++){
				String s = replaceData(data, rm.getUserId(), i);
				rd+=s;
			}			
			data = headerd+rd+footer;
			
			MailSendInfo  sendInfo = new MailSendInfo();
			sendInfo.setFromAddress(from);
			sendInfo.setMailServerHost(host);
			sendInfo.setPassword(password);
			sendInfo.setUserName(from);
			sendInfo.setContent(data);
			sendInfo.setSubject("还款提醒");
			sendInfo.setToAddress(rm.getEmail());			
			SimpleEmailSend send = new SimpleEmailSend();
			try {
				send.sendHtmlMail(sendInfo);
				data = "";
			} catch (MessagingException e) {
				e.printStackTrace();
				log.errLog("邮件发送失败", e);
			}
		}
	}
	private String replaceData(String data, String userId, int day){	
		List<RepayMail> repayMails = repayService.getRepayInterval(userId, day);
		if(day==0){
			data = data.replace("#{title}", "今日还款项目");
		}else if(day == 1){
			data = data.replace("#{title}", "明日还款项目");
		}else if(day == 2){
			data = data.replace("#{title}", "后天还款项目");
		}				
		String s = "";
		double total = 0;
		for(RepayMail repayMail : repayMails){
			
			String p = "";
			if(repayMail.getOperationType().equals("月")){
				p += repayMail.getDeadline()+"个月";
			}else{
				p += repayMail.getDay()+"天";
			}
			String loanMoney = repayMail.getLoanMoney() > 10000 ? ArithUtil.round(repayMail.getLoanMoney()/10000, 1)+"（万元）" : ArithUtil.round(repayMail.getLoanMoney(), 1) + "（元）";
			String corpus = repayMail.getCorpus() > 10000 ? ArithUtil.round(repayMail.getCorpus()/10000, 2)+"（万元）" : ArithUtil.round(repayMail.getCorpus(), 2) + "（元）";
			String interest = repayMail.getInterest() > 10000 ? ArithUtil.round(repayMail.getInterest()/10000, 2)+"（万元）" : ArithUtil.round(repayMail.getInterest(), 2) + "（元）";
			String money = repayMail.getCorpus() + repayMail.getInterest() > 10000 ? ArithUtil.round((repayMail.getCorpus()+repayMail.getInterest())/10000, 2)+"（万元）" : ArithUtil.round(repayMail.getCorpus()+repayMail.getInterest(), 2) + "（元）";
			total += repayMail.getCorpus() + repayMail.getInterest();
			String str = "<tr>"
					+ "<td><a href=\"https://www.duanrong.com/loanDetail/"+repayMail.getLoanId()+"\">"+repayMail.getLoanName()+"</a></td>"
					+ "<td>"+repayMail.getLoanType()+"</td><td>"+p+"</td>"
					+ "<td>"+ArithUtil.round(repayMail.getRate()*100, 1)+"%</td><td>"+loanMoney+"</td><td>"+ repayMail.getRepayType() +"</td>"
					+ "<td>"+repayMail.getPeriod()+"</td><td>"+corpus+"</td><td>"+interest+"</td>"
					+ "<td>"+money+"</td><td>"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(repayMail.getRepayDay())+"</td></tr>";
			s += str;		
		}
		String totalM = total > 10000 ? ArithUtil.round(total/10000, 2)+"（万元）" : ArithUtil.round(total, 2) + "（元）";
		s += "<tr><td colspan=\"10\" style=\"text-align: right; padding-right: 10px\">本日还款数量："+repayMails.size()+"</td><td>本日还款金额："+totalM+"</td></tr>";
		data = data.replace("#{data}", s);
		return data;
	}
	
	@Scheduled(cron = "0 0 4 * * ?")
	void sendRepayNotice(){
		log.infoLog("项目公告轮询", "项目公告轮询发送开始......"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		List<RepayMail> rms = repayService.getRepayFinishInterval(-1);
		Template t = userService.getTemplateById("repay_notice");
		String tm = t == null ? "" : t.getContent();
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		int i = 1;
		log.infoLog("项目公告轮询", "昨天共完成"+rms.size()+"个项目");
		DecimalFormat df = new DecimalFormat("####.##");
		for(RepayMail rm : rms){
			
			String notice = tm;
			notice = notice.replace("#{loanName}", rm.getLoanName());
			notice = notice.replace("#{operateTime}", format.format(rm.getOperationTime()));
			String corpus = rm.getCorpus()/10000 > 1 ? df.format(rm.getCorpus()/10000)+"万元" : df.format(rm.getCorpus())+"元";
			notice = notice.replace("#{corpus}", corpus);
			String interest = rm.getInterest()/10000 > 1 ? ArithUtil.round(rm.getInterest()/10000, 2)+"万元" : ArithUtil.round(rm.getInterest(), 2)+"元";
			notice = notice.replace("#{interest}", interest);
			notice = notice.replace("#{time}", format.format(new Date()));
			
			Node node = new Node();
			node.setId(IdUtil.randomUUID());
			String j = i < 10 ? "0"+i : ""+i;
			String title = "【结束公告】"+format.format(rm.getOperationTime())+"项目结束公告"+j;		
			node.setTitle(title);
			node.setContent(notice);
			node.setCreateTime(new Date());
			node.setCreator("admin");
			node.setUpdateTime(new Date());
			node.setLastModifyUser("admin");
			node.setStatus(""+1);
			node.setTerm("项目公告");
			node.setSeqNum(0);
			try{
				nodeService.insert(node);
			}catch(Exception e){
				e.printStackTrace();
				log.errLog("项目公告轮询", e);
			}	
			i++;
			try {
				Thread.sleep(3*1000);
			} catch (InterruptedException e) {
				
			}
		}
		DRJedisCacheUtil.del(RedisCacheKey.NOTICES_LOAN);
		log.infoLog("项目公告轮询", "项目公告轮询发送结束......"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
	}

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


}
