package com.duanrong.dataAnalysis.business.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.duanrong.dataAnalysis.business.cashData.model.payAndCashData;
import com.duanrong.dataAnalysis.business.cashData.service.cashDataService;
import com.duanrong.dataAnalysis.business.loanDataT.model.LoanDataT;
import com.duanrong.dataAnalysis.business.loanDataT.service.LoanDataTService;
import com.duanrong.dataAnalysis.business.mail.MailSenderInfo;
import com.duanrong.dataAnalysis.business.mail.SimpleMailSender;
import com.duanrong.dataAnalysis.business.turnOverT.model.TurnOverT;
import com.duanrong.dataAnalysis.business.turnOverT.service.TurnOverTService;
import com.duanrong.dataAnalysis.business.userDataToday.model.userDataT;
import com.duanrong.dataAnalysis.business.userDataToday.service.userDataTService;
import com.duanrong.dataAnalysis.business.userSource.model.UserSource;
import com.duanrong.dataAnalysis.business.userSource.service.userSourceService;
import com.duanrong.dataAnalysis.util.FreeMarkerUtil;
import com.duanrong.dataAnalysis.util.GetTimeUtil;
import com.duanrong.dataAnalysis.util.LoadConstantProterties2;
import com.duanrong.dataAnalysis.util.LogUtil;


/**
 * 能在quartz的job中，注入Spring的对象
 * 
 * @author Lin Zhiming
 * @version Feb 27, 2015 11:21:43 AM
 */
@Component
public class quartz  {
	@Resource
	private userDataTService userDataTService;
	@Resource
	private TurnOverTService turnOverTService;
	@Resource
	private LoanDataTService loanDataTService;
	@Autowired
	userSourceService userSourceService;
	@Resource
	private cashDataService cashDataService;
	
	
	
	@Scheduled(cron = "0 0 4 * * *")
	private void sendEmailByTime(){
		LogUtil.infoLog("邮件发送", "邮件发送前");
		Map<String, Object> root=new HashMap<String,Object>();
		userDataT userDataT =userDataTService.getUserDataT();
		TurnOverT turnOverT =turnOverTService.getTurnOverMoney();
		LoanDataT loanDataT= loanDataTService.getLoanDataT();
		UserSource userSource=userSourceService.getUserCount();
		payAndCashData payAndCashData=cashDataService.getDataByMail();
		root.put("userDataT", userDataT);
		root.put("turnOverT", turnOverT);
		root.put("loanDataT", loanDataT);
		root.put("userSource", userSource);
		root.put("payAndCashData", payAndCashData);
		String[] addrs=LoadConstantProterties2.getValueByDefaultPro("emailAddress").split(",");
		LogUtil.infoLog("发送邮件", "数据查询");
		for (String addr : addrs) {  
			String tittle = "数据统计" + GetTimeUtil.getLastTime();
		     sendMail("itmailservice@duanrong.com", addr, tittle, root);
		     
		     LogUtil.infoLog("发送邮件", "邮件发送完");
		}
	};
	
	
	public static void sendMail(String userName,String toUserName,String tittle,Map<String, Object> root){
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	     //这个类主要是设置邮件   
	     MailSenderInfo mailInfo = new MailSenderInfo();   
	     mailInfo.setMailServerHost("smtp.duanrong.com");   
	     mailInfo.setMailServerPort("25");   
	     mailInfo.setValidate(true);   
	     mailInfo.setUserName(userName);   
	     String password=LoadConstantProterties2.getValueByDefaultPro("password");
	     mailInfo.setPassword(password);//您的邮箱密码   
	     String sendMail=LoadConstantProterties2.getValueByDefaultPro("sendMail");
	     mailInfo.setFromAddress(sendMail);   
	     mailInfo.setToAddress(toUserName);   	
	     mailInfo.setSubject(tittle); 
	     String content=FreeMarkerUtil.analysisTemplate("templ01.html",root);
	     mailInfo.setContent(content);
	     MailcapCommandMap mc = (MailcapCommandMap) CommandMap
	                .getDefaultCommandMap();
	        mc
	                .addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_html");
	        mc
	                .addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
	        mc
	                .addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
	        mc
	                .addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
	        mc
	                .addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
	        CommandMap.setDefaultCommandMap(mc);
	     
	     //这个类主要来发送邮件  
	     SimpleMailSender sms = new SimpleMailSender();  
	         //sms.sendTextMail(mailInfo);//发送文体格式   
	     try{	    	 
	    	 sms.sendHtmlMail(mailInfo);//发送html格式  
	    	 System.out.println("发送成功");
	     }catch(Exception e){
	    	 
	    	 LogUtil.errLog("错误描述", e);
	     }
	        
	     	
	        
	       
		
	} 
	
}
