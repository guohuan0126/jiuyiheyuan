package com.duanrong.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import util.Log;
import util.MyStringUtils;

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
import com.duanrong.business.repay.model.Repay;
import com.duanrong.business.sms.SmsConstants;
import com.duanrong.business.sms.dao.SmsDao;
import com.duanrong.business.sms.model.Sms;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.dao.RedPacketDao;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.RedPacket;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.model.UserOtherInfo;
import com.duanrong.business.user.service.InformationService;
import com.duanrong.business.user.service.RedPacketService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.sms.SMSSend;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.DateUtil;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.InterestUtil;
import com.duanrong.util.mail.MailSendInfo;
import com.duanrong.util.mail.SimpleEmailSend;

/**
* 
* @description 红包奖励短信相关调度
* @author guohuan
* @time 2016年12月14日上午10:10:59
 */
@Component
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
	PlatformTransferService platformTransferService;
	
	@Resource
	UserAccountService userAccountService;
	
	@Resource
	PlatformAccountService platformAccountService;
	
	@Resource
	LoanService loanService;
	
	@Resource
	RepayDao repayDao;
	
	@Resource 
	UserDao userDao; 
	
	/**
	 * 完成首次投资后，第3天
	 * 每天的11点25执行
	 */
	@Scheduled(cron = "0 0 10 * * ?")
	public void smsFirstInvestAfter(){
		System.out.println("##########完成首次投资后，第3天");
		int day=3;
		String content="上市集团背景安全可靠，1个月起，收益9-13%，多一份安全多一分收益，点击 t.cn/R4L4Usl 开始赚钱，回复TD退订。";
		Map<String,Object>param=new HashMap<String,Object>();
		param.put("day", day);
		List<Invest>investList=investDao.getFirstInvestAfter(param);
		log.infoLog("短信", "完成首次投资后，第3天");
		String mobileNumbers="";
		for(int i=0; i<investList.size(); i++){
			String mobile="";
			try {
				User user=userService.get(investList.get(i).getInvestUserID());
				mobile=user.getMobileNumber();
				mobileNumbers += user.getMobileNumber()+",";
				Sms sms = new Sms();
				sms.setId(IdGenerator.randomUUID());
				sms.setUserId(user.getUserId());
				sms.setMobileNumber(user.getMobileNumber());
				sms.setContent(content);
				sms.setTime(new Date());
				sms.setType(SmsConstants.INFORMATION);
				smsDao.insert(sms);
			} catch (Exception e) {
				log.errLog("短信", mobile+ "完成首次投资后，第3天"+e);
				e.printStackTrace();
			}
		}
		try {
			if(!"".equals(mobileNumbers)){
				mobileNumbers = mobileNumbers.substring(0,
						mobileNumbers.length() - 1);
				System.out.println(mobileNumbers);
				SMSSend.batchSend1(mobileNumbers, content);
				log.infoLog("短信", "完成首次投资后，第3天" + mobileNumbers + "短信");
				System.out
				.println("**********************发送短信*******************************");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 绑定手机号第二天没注册的用户短信
	 */
	@Scheduled(cron = "0 10 10 * * ?")
	//@Scheduled(cron = "0 0/6 * * * ?")
	public void smsSericeNoRegist(){
		System.out.println("##########绑定手机号第二天没注册的用户短信");
		int day=2; 
		String content="获A股上市集团辅仁药业3.9亿B轮融资，下载APP完成注册，即享680元红包，点 t.cn/R2vkjj6 领取。回复TD退订";
		List<String> mobileList=redPacketDao.getSmsSericeNoRegist(day, 249);
		log.infoLog("短信", "绑定手机号第二天没注册的用户短信");
		String mobileNumbers="";
		for(int i=0; i<mobileList.size(); i++){
			try {
				mobileNumbers += mobileList.get(i) + ",";
				Sms sms = new Sms();
				sms.setId(IdGenerator.randomUUID());
				sms.setUserId("");
				sms.setMobileNumber(mobileList.get(i)+"");
				sms.setContent(content);
				sms.setTime(new Date());
				sms.setType(SmsConstants.INFORMATION);
				smsDao.insert(sms);
			} catch (Exception e) {
				log.errLog("短信", mobileList.get(i)+ "绑定手机号第二天没注册的用户短信"+e);
				e.printStackTrace();
			}
		}
		
		try {
			if(!"".equals(mobileNumbers)){
				mobileNumbers = mobileNumbers.substring(0,
						mobileNumbers.length() - 1);
				System.out.println(mobileNumbers);
				SMSSend.batchSend1(mobileNumbers, content);
				log.infoLog("短信", "绑定手机号第二天没注册的用户短信" + mobileNumbers + "短信");
				System.out
				.println("**********************发送短信*******************************");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 调度发送短信 1.注册未投资（两天）用户 发送短信 （全体） 2.注册未投资（20天）用户发送红包 排除掉辅仁 比搜益 3.注册未投资用户发送短信
	 * （全体）
	 * 注册后第3天无投资
	 */
	@Scheduled(cron = "0 20 10 * * ?")
	//@Scheduled(cron = "0 0/3 * * * ?")
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
					User user = userService.getUserByMobileNumber(mobNumber);

					Map<String,Object>param=new HashMap<String,Object>();
					param.put("userId", user.getUserId());
					UserOtherInfo userOtherInfo=userDao.getUserOtherInfoById(param);
					String userSource="";
					if(userOtherInfo!=null){
						userSource=userOtherInfo.getUserSource();
						if(userSource==null){
							userSource="";
						}
					}
					if(!userSource.contains("TouZhiJia")&&!userSource.contains("aiyouqian")
							&&!userSource.contains("p2peye")&&!userSource.contains("fengchelicai")
							&&!userSource.contains("lixinzk")){
						mobileNumbers += mobNumber + ",";
						createReadpacket(user,274, mobNumber, 5, 0,0.00, 1, "0","新人陪伴礼","money",7);
		                //用户  模板编号 手机号      红包金额 加息券额度  投资限额  投资项目限额 是否新手可用（0可用） 红包名称  现金红包 有效期
						Sms sms = new Sms();
						sms.setId(IdGenerator.randomUUID());
						sms.setUserId(user.getUserId());
						sms.setMobileNumber(mobNumber);
						sms.setContent(content);
						sms.setTime(new Date());
						sms.setType(SmsConstants.INFORMATION);
						smsDao.insert(sms);
					}

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
