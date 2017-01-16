package com.dr.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import util.XMLUtil;

import com.duanrong.business.repay.dao.RepayInvestDao;
import com.duanrong.business.repay.model.Repay;
import com.duanrong.business.repay.model.RepayInvest;
import com.duanrong.business.repay.service.RepayService;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.sms.SMSSend;
import com.duanrong.util.MD5Encry;
import com.duanrong.util.client.DRHTTPClient;
import com.duanrong.yeepay.xml.GeneratorXML;



public class Test {
	public static void main(String[] args) throws Exception {
		SMSSend.batchSend1("13261356043", "太棒啦，您获得奥运标项目的“快投手奖”，5.8元现金已经发送到您的账户，点击  t.cn/R4L4Usl 查看，回复TD退订。");
		//System.out.println(MD5Encry.Encry("duanrongsoa:3e0771589fd443fa99983733d5c60228duanrong!@#"));
	}
}
