package com.duanrong.business.invest;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.Log;
import util.MyStringUtils;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.service.PlatformAccountService;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.model.PassThrough;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.platformtransfer.model.PlatformTransfer;
import com.duanrong.business.platformtransfer.service.PlatformTransferService;
import com.duanrong.business.sms.SmsConstants;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.user.service.InformationService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.jedis.ICustomer;
import com.duanrong.util.jedis.RollbackException;
import com.duanrong.util.mail.MailSendInfo;
import com.duanrong.util.mail.SimpleEmailSend;
import com.duanrong.yeepay.service.TrusteeshipPlatformTransferService;

@Service
public class DoubleElevenInvestActive implements ICustomer {

	@Resource
	UserService userService;

	@Resource
	InvestService investService;

	@Resource
	TrusteeshipPlatformTransferService trusteeshipPlatformTransferService;

	@Resource
	PlatformAccountService platformAccountService;	
	
	@Resource
	Log log;

	@Resource
	SmsService smsService;

	@Resource
	InformationService informationService;

	@Resource
	UserAccountService userAccountService;

	@Resource
	PlatformTransferService platformTransferService;

	double execute(String userId, int flag) {
		double awardMoney = 0;
		PassThrough passThrough = new PassThrough();
		passThrough = userService.getDoubleElevenPrize(userId);
		String id = MyStringUtils.append(UUID.randomUUID().toString()
				.replaceAll("-", ""), MyStringUtils.multipleLetter(4));
		passThrough.setUserId(userId);
		if (passThrough == null) {
			int investMoney = investService
					.getCurrentInvestMoney4DoubleElevec1(userId);
			passThrough.setInvestTotalMoney(investMoney);
			passThrough.setId(id);
			passThrough.setWhetherReward("0");
			userService.insert4DoubleEleven(passThrough);
		} else if (passThrough != null
				&& StringUtils.isBlank(passThrough.getRecommendedId())) {
			List<PassThrough> resultList1 = userService
					.getRecommendedPrize(userId);
			int investMoney = 0;
			investMoney = investService
					.getCurrentInvestMoney4DoubleElevec1(userId);
			if (resultList1.size() > 0) {
				for (PassThrough passThrough2 : resultList1) {
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					investMoney = investMoney
							+ investService.getCurrentInvestMoney4DoubleElevec(
									passThrough2.getUserId(),
									df.format(passThrough2.getCreateTime()));
				}
			}
			passThrough.setInvestTotalMoney(investMoney);
			userService.update4DoubleEleven(passThrough);

			String str = passThrough.getWhetherReward();
			String st[] = new String[str.length()];
			st = str.split(",");
			List<String> list = Arrays.asList(st);
			Invest invest = new Invest();
			invest.setId(userId + "Checkpoint" + flag);
			invest.setInvestUserID(userId);
			if (flag == 1) {
				if (!list.contains("1")) {
					String str1 = passThrough.getWhetherReward() + ",1";
					passThrough.setWhetherReward(str1);
					PassThrough passThrough1 = userService
							.getDoubleElevenPrize(userId);
					Double rewardMoney = passThrough1.getRewardMoney();
					Double rewardMoney1 = rewardMoney + 16;
					passThrough.setRewardMoney(rewardMoney1);
					userService.update4DoubleElevenReward(passThrough);
					awardMoney = 16;
					sendAward(null, invest, awardMoney);
					informationService.sendInformation(userId, "双11，过关奖励到账通知",
							"恭喜您，顺利闯下第一关，16元现金奖品已到账，请查收");
					try {
						smsService.sendSms(userId, "恭喜你，顺利攻下东岭关，16元现金奖励已到账，您可登录官网—我的账户—交易记录查询，再接再厉哦，回TD退订。",
								SmsConstants.AWARD);
					} catch (Exception ex) {
						
					}
				}
			} else if (flag == 2) {
				if (!list.contains("2")) {
					String str1 = passThrough.getWhetherReward() + ",2";
					passThrough.setWhetherReward(str1);
					PassThrough passThrough1 = userService
							.getDoubleElevenPrize(userId);
					Double rewardMoney = passThrough1.getRewardMoney();
					Double rewardMoney1 = rewardMoney + 36;
					passThrough.setRewardMoney(rewardMoney1);
					userService.update4DoubleElevenReward(passThrough);
					awardMoney = 36;
					sendAward(null, invest, awardMoney);
					informationService.sendInformation(userId, "双11，过关奖励到账通知",
							"恭喜您，顺利闯下第二关，36元现金奖品已到账，请查收");
					try {
						smsService.sendSms(userId, "恭喜你，顺利攻下洛阳关，36元现金奖励已到账，您可登录官网—我的账户—交易记录查询，再接再厉哦，回TD退订。",
								SmsConstants.AWARD);
					} catch (Exception ex) {
						
					}
				}
			} else if (flag == 3) {
				if (!list.contains("3")) {
					String str1 = passThrough.getWhetherReward() + ",3";
					passThrough.setWhetherReward(str1);
					PassThrough passThrough1 = userService
							.getDoubleElevenPrize(userId);
					Double rewardMoney = passThrough1.getRewardMoney();
					Double rewardMoney1 = rewardMoney + 50;
					passThrough.setRewardMoney(rewardMoney1);
					userService.update4DoubleElevenReward(passThrough);
					awardMoney = 50;
					sendAward(null, invest, awardMoney);
					informationService.sendInformation(userId, "双11，过关奖励到账通知",
							"恭喜您，顺利闯下第三关，50元现金奖品已到账，请查收");
					try {
						smsService.sendSms(userId, "恭喜你，顺利攻下汜水关，50元现金奖励已到账，您可登录官网—我的账户—交易记录查询，再接再厉哦，回TD退订。",
								SmsConstants.AWARD);
					} catch (Exception ex) {
						
					}
				}
			} else if (flag == 4) {
				if (!list.contains("4")) {
					String str1 = passThrough.getWhetherReward() + ",4";
					passThrough.setWhetherReward(str1);
					PassThrough passThrough1 = userService
							.getDoubleElevenPrize(userId);
					Double rewardMoney = passThrough1.getRewardMoney();
					Double rewardMoney1 = rewardMoney + 66;
					passThrough.setRewardMoney(rewardMoney1);
					userService.update4DoubleElevenReward(passThrough);
					awardMoney = 66;
					sendAward(null, invest, awardMoney);
					informationService.sendInformation(userId, "双11，过关奖励到账通知",
							"恭喜您，顺利闯下第四关，66元现金奖品已到账，请查收");
					try {
						smsService.sendSms(userId, "恭喜你，顺利攻下荥阳关，66元现金奖励已到账，您可登录官网—我的账户—交易记录查询，再接再厉哦，回TD退订。",
								SmsConstants.AWARD);
					} catch (Exception ex) {
						
					}
				}
			} else if (flag == 5) {
				if (!list.contains("5")) {
					String str1 = passThrough.getWhetherReward() + ",5";
					passThrough.setWhetherReward(str1);
					PassThrough passThrough1 = userService
							.getDoubleElevenPrize(userId);
					Double rewardMoney = passThrough1.getRewardMoney();
					Double rewardMoney1 = rewardMoney + 200;
					passThrough.setRewardMoney(rewardMoney1);
					userService.update4DoubleElevenReward(passThrough);
					awardMoney = 200;
					sendAward(null, invest, awardMoney);
					informationService.sendInformation(userId, "双11，过关奖励到账通知",
							"恭喜您，顺利闯下第五关，200元现金奖品已到账，请查收");
					try {
						smsService.sendSms(userId, "恭喜你，顺利攻下滑州黄河渡口，200元现金奖励已到账，您可登录官网—我的账户—交易记录查询，回TD退订。",
								SmsConstants.AWARD);
					} catch (Exception ex) {
						
					}
				}
			}

		}

		return ArithUtil.round(awardMoney, 0);
	}

	@Override
	public void customer(String arg0, String arg1) throws RollbackException {
		String st[] = new String[arg1.length()];
		st = arg1.split(",");
		List<String> list = Arrays.asList(st);
		execute(list.get(0), Integer.valueOf(list.get(1)));
	}

	/**
	 * 发送奖励
	 * 
	 * @param invest
	 * @param awardMoney
	 */
	private void sendAward(InvestActive investActive, Invest invest,
			double awardMoney) {
		try {
			PlatformTransfer platformTransfer = new PlatformTransfer();
			platformTransfer.setOrderId("doubleEleven-" + invest.getId());
			platformTransfer.setType("双十一活动");
			platformTransfer.setStatus("平台划款成功");
			List<PlatformTransfer> ps = platformTransferService
					.getList(platformTransfer);
			if (ps == null || ps.isEmpty()) {
				String status = trusteeshipPlatformTransferService
						.platformTransferTrusteeship(
								platformTransfer.getOrderId(),
								invest.getInvestUserID(),
								ArithUtil.round(awardMoney, 2), "",
								platformTransfer.getType(), "", null);
				if ("success".equals(status)) {
					try{
						userAccountService.ptTransferIn(invest.getInvestUserID(),
								ArithUtil.round(awardMoney, 2),
								BusinessEnum.reward, "双十一活动",
								"investId:" + invest.getId() + ",双十一活动："
										+ ArithUtil.round(awardMoney, 2) + "元",
								platformTransfer.getOrderId());
						platformAccountService.transferOut(ArithUtil.round(awardMoney, 2), BusinessEnum.reward, "活动奖励", platformTransfer.getOrderId());
						informationService.sendInformation(
								invest.getInvestUserID(), "双十一活动", "");
					}catch(Exception e){
						
					}				
				}

			}
		} catch (Exception e) {
			System.out.println("###########################errorMSG:"
					+ e.toString());
			log.errLog("双十一活动" + "investId:" + invest.getId(), e);
			String content = "双十一活动奖励发送失败请及时处理，ID：" + invest.getId()
					+ ", 失败原因：" + e.toString();
			sendEmail("smtp.duanrong.com", "itmailservice@duanrong.com",
					"DRdrITmail159!@#", content, "双十一活动奖发送失败");
		}
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
			
		} catch (MessagingException ex) {
			log.errLog("邮件发送失败", ex);
		}
	}
}
