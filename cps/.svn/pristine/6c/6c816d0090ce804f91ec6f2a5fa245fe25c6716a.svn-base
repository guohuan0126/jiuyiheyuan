package com.duanrong.cps.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.duanrong.util.jedis.DRJedisMQ;
import com.duanrong.util.jedis.ICustomer;


@Controller
public class RedisCallBackController extends BaseController {

	@Autowired
	private ICustomer RedisCallBackServiceImpl;
	@PostConstruct
	public void callRedis() throws Exception {
//		ApplicationContext ctx = new ClassPathXmlApplicationContext(
//				"beans-*.xml");
 		final ICustomer  redisCallBackService = RedisCallBackServiceImpl;
		//RedisCallBackServiceImpl redisCallBackService=new RedisCallBackServiceImpl();
		new Thread(new Runnable() {
			@Override
			public void run() {
				DRJedisMQ.customer("pushinvest", redisCallBackService);
			}
		}).start();
    	
	}

}
