package com.duanrong.business.maxinvestrecord;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import util.Log;

import com.duanrong.business.demand.service.DemandtreasureTransferInService;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.maxinvestrecord.model.MaxInvestRecord;
import com.duanrong.business.maxinvestrecord.service.MaxInvestRecordService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.util.client.DRHTTPClient;
@Component
public class InitMaxInvestRecordSchdule {
	@Resource
	MaxInvestRecordService maxInvestRecordService;
	@Resource
	UserService userService;
	@Resource
	InvestService investService;
	@Resource
	DemandtreasureTransferInService demandtreasureTransferInService;
	@Resource
	Log log;

	/**
	 * 初始化在投峰值表
	 */
	/*@Scheduled(cron = "0 15 * * * ?")*/
	public void initRecord() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.infoLog("更新用户在投峰值开始:", sdf.format(new Date()));
		System.out.println("=============初始化在投峰值开始==========");
		//查询表的总条数
		int totalCount = maxInvestRecordService.getRecordCount();
		int times = totalCount%100==0?totalCount/100:totalCount/100+1;
		for(int time=0;time<=times-1;time++){
			List<MaxInvestRecord> totalRecord = maxInvestRecordService.getRecord(time);
			for (MaxInvestRecord record : totalRecord) {
				try {
					// 查询用户当前定期投资金额
					Double investMoney = investService.getUserInvestMoney(record.getUserId());
					// 查询用户当前活期再投
					Double demandMoney = demandtreasureTransferInService.getDemandMoney(record.getUserId());
					record.setCurrentInvest(investMoney + demandMoney);
					// 设置最后更新时间
					record.setUpdateTime(new Date());
					if (record.getMaxInvest() < record.getCurrentInvest()) {
						record.setMaxInvest(record.getCurrentInvest());
					}
					maxInvestRecordService.update(record);
				} catch (Exception e) {
					e.printStackTrace();
					log.errLog("更新用户在投峰值出现异常", e);
					//发送邮件
					/*String url="http://soa-log2.duanrong.com/log/error?level=1&mail=liulina@duanrong.com";
					List<NameValuePair> params = new ArrayList<>();
					params.add(new BasicNameValuePair("title", "在投峰值记录信息错误日志"));
					params.add(new BasicNameValuePair("content", "更新用户在投峰值出现异常"+e+"，错误的用户ID："+record.getUserId()));
					try {
						DRHTTPClient.sendHTTPRequestPost(url,new BasicHeader[] {new BasicHeader("sign","")},params);
					} catch (IOException e1) {
						log.errLog("发送邮件异常", e1);
					}*/
				}
		}
		}
		log.infoLog("更新用户在投峰值结束:", sdf.format(new Date()));
		/*String url="http://soa-log2.duanrong.com/basic/mail/send";
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("subject", "更新用户在投峰值记录"));
		params.add(new BasicNameValuePair("content", "更新用户在投峰值记录成功,一共更新"+totalCount+"条数据"));
		params.add(new BasicNameValuePair("mailtos","liulina@duanrong.com"));
		try {
			DRHTTPClient.sendHTTPRequestPost(url,new BasicHeader[] {new BasicHeader("sign","")},params);
		} catch (IOException e2) {
			log.errLog("发送邮件异常", e2);
		}*/
		System.out.println("=============更新在投峰值结束================");
	}
}
