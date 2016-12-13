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
public class MaxInvestRecordSchdule {
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
	 * 每天零点10分执行
	 */
	/*@Scheduled(cron = "0 10 0 * * ?")*/
	public void saveOrUpdateRecord() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.infoLog("插入/更新用户在投峰值开始:", sdf.format(new Date()));
		System.out.println("=============再投峰值开始==========");
		//查询昨日有投资的用户id
		List<String> hasInvestUserId = investService.getNewInvestUser();
		//查询昨日有还款的用户id
		List<String> hasRepayUserId = investService.getHasRepayUserId();
		//查询昨日活期有转出用户id
		List<String> hasOutUserId = demandtreasureTransferInService.getTurnOutUserId();
		hasInvestUserId.addAll(hasRepayUserId);
		hasInvestUserId.addAll(hasOutUserId);
		Set<String> newUserId= new HashSet<>(hasInvestUserId);
		System.out.println("查询数量++++++++++"+newUserId.size());
		for (String uId : newUserId) {
			try {
				// 查询用户当前定期投资金额
				Double investMoney = investService.getUserInvestMoney(uId);
				// 查询用户当前活期再投
				Double demandMoney = demandtreasureTransferInService.getDemandMoney(uId);
				// 根据用户id查询用户在投峰值记录表
				MaxInvestRecord record = maxInvestRecordService.getInvestRecord(uId);
				// 若record==null说明是新增用户
				if (record == null) {
					// 根据用户id得到注册时间与来源
					record = userService.getUserInfo(uId);
					record.setCurrentInvest(investMoney + demandMoney);
					// 设置用户在投峰值与当前在投金额相同
					record.setMaxInvest(record.getCurrentInvest());
					record.setCreateTime(new Date());
					record.setUpdateTime(new Date());
                    maxInvestRecordService.insert(record);
				} else {
					record.setCurrentInvest(investMoney + demandMoney);
					// 设置最后更新时间
					record.setUpdateTime(new Date());
					if (record.getMaxInvest() < record.getCurrentInvest()) {
						record.setMaxInvest(record.getCurrentInvest());
					}
					maxInvestRecordService.update(record);
				}
			} catch (Exception e) {
				log.errLog("插入/更新用户在投峰值出现异常", e);
				//发送邮件
				String url="http://soa-log2.duanrong.com/log/error?level=1&mail=liulina@duanrong.com";
				List<NameValuePair> params = new ArrayList<>();
				params.add(new BasicNameValuePair("title", "在投峰值记录信息错误日志"));
				params.add(new BasicNameValuePair("content", "插入/更新用户在投峰值出现异常"+e+"，错误的用户ID："+uId));
				try {
					DRHTTPClient.sendHTTPRequestPost(url,new BasicHeader[] {new BasicHeader("sign","")},params);
				} catch (IOException e1) {
					log.errLog("发送邮件异常", e1);
				}
			}
		}
		log.infoLog("插入/更新用户在投峰值结束:", sdf.format(new Date()));
		String url="http://soa-log2.duanrong.com/basic/mail/send";
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("subject", "插入/更新用户在投峰值记录"));
		params.add(new BasicNameValuePair("content", "插入/更新用户在投峰值记录成功，插入/更新数据"+newUserId.size()+"条"));
		params.add(new BasicNameValuePair("mailtos","liulina@duanrong.com"));
		try {
			DRHTTPClient.sendHTTPRequestPost(url,new BasicHeader[] {new BasicHeader("sign","")},params);
		} catch (IOException e2) {
			log.errLog("发送邮件异常", e2);
		}
		System.out.println("=============再投峰值结束================");
	}
}
