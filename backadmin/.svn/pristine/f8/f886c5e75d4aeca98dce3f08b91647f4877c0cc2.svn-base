package com.duanrong.business.msg;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 邮件发送身份验证期
 * @author xiao
 * @date 2015/03/18 上午11:16:51
 */
public class MyAuthenticator extends Authenticator {
	
	String userName=null;
	String password=null;

	public MyAuthenticator(){
		
	}
	
	public MyAuthenticator(String username, String password) { 
		
		this.userName = username; 
		this.password = password; 
	} 
	
	protected PasswordAuthentication getPasswordAuthentication(){	 
		return new PasswordAuthentication(userName, password);
	}

}
