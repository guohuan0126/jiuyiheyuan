package com.duanrong.business.msg.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import util.Log;
import util.MyStringUtils;
import base.pagehelper.PageInfo;

import com.duanrong.business.msg.dao.EmailDao;
import com.duanrong.business.msg.model.Email;
import com.duanrong.business.msg.service.EmailService;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.util.mail.MailSendInfo;
import com.duanrong.util.mail.SimpleEmailSend;

@Service
public class EmailServiceImpl implements EmailService {

	@Resource
	EmailDao emailDao;
	
	@Resource
	Log log;

	@Override
	public PageInfo<Email> findPageInfo(int pageNo, int pageSize, Map map) {
		return emailDao.pageInfo(pageNo, pageSize, map);
	}

	@Override
	public List<Email> getEmailByLoan(String loanId) {
		return emailDao.getEmailByLoadId(loanId);
	}

	@Override
	public boolean deleteEmailByLoan(String loanId) {
		
		return emailDao.deleteEmailByLoadId(loanId) > 0;
	}

	@Override
	public void insertEmial(Email email) {
		emailDao.insert(email);		
	}

	@Override
	public Email getEmailById(String id) {
		
		return emailDao.getEmailById(id);
	}

	
	private String getEmailConfigById(String id) {		
		return emailDao.getConfigById(id);
	}

	@Override
	public String sendEmail(String loanId, String userId) {
		String mailSmtp = getEmailConfigById("mail_smtp");
		String mailUserName = getEmailConfigById("mail_username");
		String mailPassword = getEmailConfigById("mail_password");
		String mailPersonal = getEmailConfigById("mail_personal");
		if(MyStringUtils.isNotAnyBlank(loanId, mailSmtp, mailUserName, mailPassword)){
			List<Email> emails = getSendEmailByLoan(loanId);
			int i = 0, j = 0;
			for(Email email : emails){
				if(MyStringUtils.isNotAnyBlank(email.getEmail())){
					try{
						MailSendInfo mailSendInfo = new MailSendInfo();
						mailSendInfo.setMailServerHost(mailSmtp);
						mailSendInfo.setUserName(mailUserName);
						mailSendInfo.setFromAddress(mailUserName);
						mailSendInfo.setToAddress(email.getEmail());
						mailSendInfo.setPassword(mailPassword);
						mailSendInfo.setSubject(mailPersonal+"   "+email.getTitle());
						mailSendInfo.setValidate(true);
						String content = email.getContent();
						content = content.replace("sendtime", DateUtil.DateToString(new Date(), "yyyy-MM-dd"));
						mailSendInfo.setContent(content);							
						SimpleEmailSend emailSend = new SimpleEmailSend();
						emailSend.sendHtmlMail(mailSendInfo);
						//修改email为'已发送'
						email.setSendUserID(userId);
						email.setStatus("已发送");
						email.setSendTime(new Date());
						emailDao.sendEamil(email);
						i++;
					}catch(Exception e){	
						e.printStackTrace();
						log.errLog("mailSend    smtp:"+mailSmtp+", 发送邮箱:"+mailUserName+",  项目："+ loanId+ ", 用户："+email.getUserId()+", mail:"+email.getEmail()+"投资确认函发送失败！", e);
						email.setSendUserID(userId);
						email.setStatus("发送失败");						
						emailDao.sendEamil(email);
						j++;
					}					
				}				
			}
			return "共发送"+(i+j)+"封邮件, "+i+"封发送成功,"+j+"封发送失败";
		}else{
			return "邮件发送失败";
		}
				
	}

	@Override
	public List<Email> getSendEmailByLoan(String loanId) {		
		return emailDao.getSendEmailByLoanId(loanId);
	}

}
