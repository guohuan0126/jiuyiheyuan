package com.duanrong.business.dao;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.duanrong.business.model.PushLoan;
import com.duanrong.business.service.PushService;
import com.duanrong.util.p2pEyeUtil.AESUtil;
import com.duanrong.util.p2pEyeUtil.MD5;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/resources/applicationContext.xml"})
public class PushTest {
	
	@Autowired
	private PushService pushService;

	@Autowired
	public PushLoanDao pushLoanDao;
	
    /**
     * 推送交易信息  
     */
	@Test
	public void pushInvestPerson(){
		System.out.println("##############天眼自动推送任务启动#########");
		//1.推送投资人信息
		pushService.pushInvestPerson();
		//2.推送项目状态
		List<PushLoan> loanList = this.pushLoanDao.getPushLoanStatusList();
		System.out.println("需要更新状态的项目数量："+loanList.size());
		pushService.pushLoanStatusInfo(loanList);
		System.out.println("##############天眼自动推送任务执行完毕#########");
	}
	
	@Test
	public void testJiami() throws NoSuchAlgorithmException{
		//6DCD9501BB84C92CC60DBCF6936A2C679B2A6F5E124A4B4821CCD5136D60CF169162383FCDCBB82EEC1C8F340E58B17E
		//KsU5NvFEni54343TCsZ1WOBtyWqgUGpthKRih0jjspLPAmh0mzOUOQ==
		System.out.println(AESUtil.encode("dmsoINVE952020160601184913715000001"));
		System.out.println(MD5.getMd5("dmsoINVE952020160601184913715000001".getBytes()));
	}
	

}
