package com.dr.test;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;




import util.PhoneNoAttributionUtils;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.lostcustomer.LostCustomerSchdule;


public class aa {

	
	public static void main(String[] args){
		/*String[] mobile = {};
		try {
			mobile = PhoneNoAttributionUtils.getAttributions("15041150660");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(mobile);*/
		
	/*ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	InvestService investService = ctx.getBean(InvestService.class);
		Invest invest = new Invest();
		invest.setLoanId("160729152205783001");
		//invest.setIsAutoInvest(true);
		List<Invest> invests = investService.getInvestByLoan(invest);*/
		
		
	//List<InvestRedpacket> irs = investService.getInvestRedpacketList();
	
	/*String title = "";
	String content = "";
	InformationService informationService = ctx.getBean(InformationService.class);
	
	UserDao userDao = ctx.getBean(UserDao.class);

	User u = userDao.getUserByMobileNumber("13261356043");
	
	informationService.sendInformation(u.getUserId(), "test", "测试");*/
	
	//RedPacketService r = ctx.getBean(RedPacketService.class);
	//r.create("13439967989iucz");
		
	//SMSSend.batchSend("13261356043", "尊敬的短融网客户：您投资aaa项目，从投资次日至放款之日共补息3天，计1元，现已发放到您的账户，快去交易记录查看吧。");
	
	}
	
}
