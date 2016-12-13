package com.duanrong.cps.business.touzhija.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TouZhiJiaQuartz {
	
	/**
	 *定时调用投之家查询用户接口，同步用户信息
	 */
	//@Scheduled(cron = "0 12 10 ? * *")// 每天上午10点，下午2点，4点    0 0 10,14,16 * * ?
	public void getTouzhijiaUserInfo(){
		System.out.println("AAAAAAAAAA");
		
	}

}
