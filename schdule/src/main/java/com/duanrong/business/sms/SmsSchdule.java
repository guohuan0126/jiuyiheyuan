package com.duanrong.business.sms;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import util.Log;

import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.RedPacketService;
import com.duanrong.business.user.service.UserService;

/**
 * 
 * @author xiao
 *
 */
@Component
public class SmsSchdule {

	
	@Resource
	SmsService smsService;
	
	@Resource
	UserService userService;
	
	@Resource
	Log log;
	
	@Resource
	RedPacketService redPacketService;
	/**
	 * 生日当天上午11点
	 */
	/*@Scheduled(cron = "0 0 11 * * ?")*/
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
}
