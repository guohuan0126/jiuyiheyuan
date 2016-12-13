package com.duanrong.util.mail;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;


/**
 * 邮件发送器
 * @author xiao
 * @date 上午11:20:43
 */
public class EmailSend {
	
	/**
	 * 身份验证
	 * @param userName
	 * @param passward
	 * @return
	 */
	protected MyAuthenticator isValidate(String userName, String passward){
		
		// 如果需要身份认证，则创建一个密码验证器 
		return new MyAuthenticator(userName, passward);
			   
	}
	
	/**
	 * 设置邮件发送基本信息
	 * @param mailSendInfo
	 * @return
	 * @throws MessagingException
	 */
	protected Message createBaseMessage(MailSendInfo mailSendInfo) throws MessagingException {
		//邮件会话属性
		Properties pro = mailSendInfo.getProperties();
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session 
		Session sendMailSession = Session.getInstance(
		pro,isValidate(mailSendInfo.getUserName(), mailSendInfo.getPassword())); 
	
		// 根据session创建一个邮件消息 
		Message mailMessage = new MimeMessage(sendMailSession);
		// 创建邮件发送者地址 		
		
		Address from = new InternetAddress(mailSendInfo.getFromAddress());
		// 设置邮件消息的发送者 
		mailMessage.setFrom(from); 
		// 创建邮件的接收者地址，并设置到邮件消息中 
		//Address to = new InternetAddress(mailSendInfo.getToAddress());		
		InternetAddress[] iaToList = InternetAddress.parse(mailSendInfo.getToAddress());     
		mailMessage.setRecipients(Message.RecipientType.TO,iaToList); //收件人     
		// 设置邮件消息发送的时间 
		mailMessage.setSentDate(new Date()); 
		// 设置邮件消息的主题 
		mailMessage.setSubject(mailSendInfo.getSubject()); 
		return mailMessage;
	}
	
	// 发送邮件
	protected void sendEmail(Message mailMessage) throws MessagingException {
		MailcapCommandMap mc = (MailcapCommandMap)CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
		Transport.send(mailMessage);
	}
	
}
