package com.duanrong.util.mail;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

;

/**
 * 简单邮件（不带附件的邮件）发送器  
 * @author xiao
 * @date 2015/03/19 下午12:01:20
 */
public class SimpleEmailSend extends EmailSend {	
	
	/** 
	  * 以文本格式发送邮件 
	  * @param mailSendInfo 待发送的邮件的信息
	 * @throws MessagingException
	  */ 
	 public void sendTextMail(MailSendInfo mailSendInfo) throws MessagingException {
		 
		 Message mailMessage = createBaseMessage(mailSendInfo);
		 // 设置邮件消息的主要内容   
		 mailMessage.setText(mailSendInfo.getContent()); 
		 // 发送邮件 	  
		 sendEmail(mailMessage); 
	 } 

	 /** 
	   * 以HTML格式发送邮件 
	   * @param mailSendInfo 待发送的邮件信息
	   * @throws MessagingException
	   */ 
	 public void sendHtmlMail(MailSendInfo mailSendInfo) throws MessagingException {
	  	
		 Message mailMessage = createBaseMessage(mailSendInfo);
		 // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象 
		 Multipart mainPart = new MimeMultipart();
		 // 创建一个包含HTML内容的MimeBodyPart 
		 BodyPart html = new MimeBodyPart();
		 // 设置HTML内容 
		 html.setContent(mailSendInfo.getContent(), "text/html; charset=utf-8"); 
		 mainPart.addBodyPart(html); 
		 // 将MiniMultipart对象设置为邮件内容 
		 mailMessage.setContent(mainPart); 		  
		 sendEmail(mailMessage); 
	 }
}
