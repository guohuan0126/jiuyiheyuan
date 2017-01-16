package com.duanrong.business.invest;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import util.Log;
import util.SpringBeanUtil;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.service.PlatformAccountService;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.platformtransfer.model.PlatformTransfer;
import com.duanrong.business.platformtransfer.service.PlatformTransferService;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.user.service.InformationService;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.PropReader;
import com.duanrong.util.jedis.ICustomer;
import com.duanrong.util.jedis.RollbackException;
import com.duanrong.util.mail.MailSendInfo;
import com.duanrong.util.mail.SimpleEmailSend;
import com.duanrong.yeepay.service.TrusteeshipPlatformTransferService;

@Component
public class InvestCustomer implements ICustomer {

	@Resource
	Log log;

	@Resource
	InvestService investService;

	@Resource
	UserAccountService userAccountService;

	@Resource
	PlatformAccountService platformAccountService;
	
	@Resource
	SmsService smsService;

	@Resource
	InformationService informationService;

	@Resource
	PlatformTransferService platformTransferService;

	@Resource
	TrusteeshipPlatformTransferService trusteeshipPlatformTransferService;

	String[] beans;

	org.apache.commons.logging.Log l = LogFactory.getLog(InvestCustomer.class);

	@PostConstruct
	void init() {
		PropReader pd = new PropReader("/activeConfig.properties");
		beans = pd.readProperty("activeBean").split(",");
	}

	@Override
	public void customer(String arg0, String arg1) throws RollbackException {
		if (beans.length > 0) {
			for (String bean : beans) {
				InvestActive investActive = (InvestActive) SpringBeanUtil
						.getBeanByName(bean);
				l.debug(investActive.getActiveId());
				log.infoLog(investActive.getActiveId() + "活动开始", "investId:"
						+ arg1);
				PlatformTransfer platformTransfer = new PlatformTransfer();
				System.out.println("###############OrderId:"+investActive.getActiveId() + arg1);
				platformTransfer.setOrderId(investActive.getActiveId() + arg1);
				platformTransfer.setStatus("平台划款成功");
				List<PlatformTransfer> ps = platformTransferService
						.getList(platformTransfer);
				if (ps == null || ps.isEmpty()) {
					Invest invest = investService.getInvest(arg1);
					System.out.println("##########if:"+(invest != null
							&& (invest.getStatus().equals("投标成功") || invest
									.getStatus().equals("筹款中"))));
					if (invest != null
							&& (invest.getStatus().equals("投标成功") || invest
									.getStatus().equals("筹款中"))) {
						System.out.println("##########邀请活动investCustomer:");
						double awardMoney = investActive.execute(invest);
						System.out.println("##########邀请活动investCustomer:awardMoney"+awardMoney);
						if (awardMoney > 0) {
							sendAward(investActive, invest, awardMoney);
						}
					}
				}
			}
		}
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
			platformTransfer.setOrderId(investActive.getActiveId()
					+ invest.getId());
			platformTransfer.setType(investActive.getActiveId());
			platformTransfer.setStatus("平台划款成功");
			
		   System.out.println("#########sendAward:OrderId:"+platformTransfer.getOrderId()+"InvestUserID:"
		                      +invest.getInvestUserID()+"awardMoney:"+ArithUtil.round(awardMoney, 2)+"investActive.getMsg():"+investActive.getMsg()+
		                      " platformTransfer.getType():"+ platformTransfer.getType()+"invest.getLoanId():"+invest.getLoanId());
			String status = trusteeshipPlatformTransferService
					.platformTransferTrusteeship(platformTransfer.getOrderId(),
							invest.getInvestUserID(),
							ArithUtil.round(awardMoney, 2),
							investActive.getMsg(), platformTransfer.getType(),
							invest.getLoanId(), null);
			if ("success".equals(status)) {
				userAccountService.ptTransferIn(
						invest.getInvestUserID(),
						ArithUtil.round(awardMoney, 2),
						BusinessEnum.reward,
						investActive.getTitle(),
						"loanId:" + invest.getLoanId() + ", investId:"
								+ invest.getId() + ","
								+ investActive.getTitle() + "："
								+ ArithUtil.round(awardMoney, 2) + "元",
						platformTransfer.getOrderId());
				
				platformAccountService.transferOut(ArithUtil.round(awardMoney, 2), BusinessEnum.reward, "活动奖励", platformTransfer.getOrderId());
				if (null != investActive.getMsg()
						&& !("").equals(investActive.getMsg())) {
					smsService.sendSms(invest.getInvestUserID(),
							investActive.getMsg(), null);
				}
				String[] infomations = investActive.getInfomation();
				if(infomations!=null){
					for(String infomation : infomations){
						if (null != infomation
								&& !("").equals(infomation)) {
							informationService.sendInformation(
									invest.getInvestUserID(), investActive.getTitle(),
									infomation);
						}
					}
				}
				
			}else{
				throw new RollbackException();
			}
		} catch (Exception e) {
			log.errLog(
					investActive.getActiveId() + "活动" + "investId:"
							+ invest.getId(), e);
			String content = investActive.getTitle() + "奖励发送失败请及时处理，LoanID："
					+ invest.getLoanId() + ", InvestID：：" + invest.getId()
					+ ", 失败原因：" + e.toString();
			sendEmail("smtp.duanrong.com", "itmailservice@duanrong.com",
					"DRdrITmail159!@#", content, investActive.getTitle()
							+ "活动奖发送失败");
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
			/*
			 * sendInfo.setToAddress("zhouwen@duanrong.com");
			 * send.sendHtmlMail(sendInfo);
			 */
		} catch (MessagingException ex) {
			log.errLog("邮件发送失败", ex);
		}
	}

}
