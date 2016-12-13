package com.duanrong.business.lostcustomer;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import util.Log;
import base.pagehelper.PageInfo;

import com.duanrong.business.lostcustomer.model.LostCustomer;
import com.duanrong.business.lostcustomer.service.LostCustomerService;
import com.duanrong.util.client.DRHTTPClient;

@Component
public class LostCustomerSchdule {

	@Resource
	LostCustomerService lostCustomerService ;
	@Resource
	Log log ;
	/**
	 * 获取流失预警客户，每天早上6点轮询一次
	 * @author guohuan
	 * @param
	 * @return
	 */
	/*@Scheduled(cron="0 54 * * * ?")*/
	public void findAndUpdateAllLostCustomer(){
		log.infoLog("流失客户预警",
				"流失客户预警调度开始............"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); 
		long start = System.currentTimeMillis();
		try {
			List<LostCustomer> allLostCustomers = lostCustomerService.readAllLostCustomer();
			List<LostCustomer> lostCustomers = new ArrayList<>();
			for (int i = 0; i < allLostCustomers.size(); i++) {
				LostCustomer lostCustomer = allLostCustomers.get(i);
				LostCustomer lost = lostCustomerService.readLostCustomerByUserId(lostCustomer.getUserId());
				if (lost==null) {
					lostCustomers.add(lostCustomer);
				}else if (lost!=null&&lost.getStatus()==1) {
					lostCustomerService.insertLostCustomerToHistoryAndUpdate(lost);
					/*lostCustomers.add(lostCustomer);*/
				}
			}
			if (lostCustomers.size()!=0) {
				lostCustomerService.insertAllLostCustomer(lostCustomers);
			}
			long end = System.currentTimeMillis();
			long excuteTime=(end-start)/1000;
			log.infoLog("流失客户流预警","插入流失预警客户"+lostCustomers.size()+"人"+"。执行时间："+excuteTime+"秒。");
			String url="http://soa-log2.duanrong.com/basic/mail/send";
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("subject", "插入流失预警客户"));
			params.add(new BasicNameValuePair("content", "插入流失预警客户成功，插入数据"+lostCustomers.size()+"条"+"。执行时间："+excuteTime+"秒。"));
			params.add(new BasicNameValuePair("mailtos","guohuan@duanrong.com"));
			try {
				DRHTTPClient.sendHTTPRequestPost(url,new BasicHeader[] {new BasicHeader("sign","")},params);
			} catch (IOException e2) {
				log.errLog("发送邮件异常", e2);
			}
		} catch (Exception e) {
			log.errLog("流失客户预警调度轮询异常", e);
			e.printStackTrace();
			String url="http://soa-log2.duanrong.com/log/error?level=1&mail=guohuan@duanrong.com";
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("title", "插入流失预警客户"));
			params.add(new BasicNameValuePair("content", "插入流失预警客户出现异常"+e));
			try {
				DRHTTPClient.sendHTTPRequestPost(url,new BasicHeader[] {new BasicHeader("sign","")},params);
			} catch (IOException e1) {
				log.errLog("发送邮件异常", e1);
			}
		}
		log.infoLog("流失客户流预警","流失客户预警调度结束............"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
	
	
	/*@Scheduled(cron = "0 0 7 * * ?")*/
	public void updateLostCustomerSchdule() {
		log.infoLog(
				"schdule",
				"流失客户更新调度开始............"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date()));
			Map<String, Object> param = new HashMap<String, Object>();
			Date dBefore = null;

			Calendar calendar = Calendar.getInstance(); // 得到日历
			calendar.setTime(new Date());// 把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, -15); // 设置为前15天
			dBefore = calendar.getTime(); // 得到前一天的时间

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
			String finishTime = sdf.format(dBefore);
			param.put("finishTime", finishTime);
			List<LostCustomer> lostCustomers = lostCustomerService.readPageInfo(param);
			int count = 0;
				for (LostCustomer lostCustomer : lostCustomers) {
					try {
						lostCustomer.setStatus(1);
						if (lostCustomer.getReturnInvestTime()!=null) {
							lostCustomer.setReturnTime(lostCustomer.getReturnInvestTime());
						}
						lostCustomerService.update(lostCustomer);
						count++;
					} catch (Exception e) {
						log.errLog("流失客户更新调度异常，异常客户"+lostCustomer.getUserId(), e);
						e.printStackTrace();
						String url="http://soa-log2.duanrong.com/log/error?level=1&mail=guohuan@duanrong.com";
						List<NameValuePair> params = new ArrayList<>();
						params.add(new BasicNameValuePair("title", "流失客户更新调度"));
						params.add(new BasicNameValuePair("content","流失客户更新调度异常，异常客户"+lostCustomer.getUserId()+e));
						try {
							DRHTTPClient.sendHTTPRequestPost(url,new BasicHeader[] {new BasicHeader("sign","")},params);
						} catch (IOException e1) {
							log.errLog("发送邮件异常", e1);
						}
					}		
				}
				List<LostCustomer> lostCustomers1 = lostCustomerService.readPageInfo1(param);
				for (LostCustomer lostCustomer : lostCustomers1) {
					try {
						lostCustomer.setStatus(1);
						if (lostCustomer.getReturnTotalDemand()!=null) {
							lostCustomer.setReturnTime(lostCustomer.getReturnDemandTime());
						}
						lostCustomerService.update(lostCustomer);
						count++;
					} catch (Exception e) {
						log.errLog("流失客户更新调度异常，异常客户"+lostCustomer.getUserId(), e);
						e.printStackTrace();
						String url="http://soa-log2.duanrong.com/log/error?level=1&mail=guohuan@duanrong.com";
						List<NameValuePair> params = new ArrayList<>();
						params.add(new BasicNameValuePair("title", "流失客户更新调度"));
						params.add(new BasicNameValuePair("content","流失客户更新调度异常，异常客户"+lostCustomer.getUserId()+e));
						try {
							DRHTTPClient.sendHTTPRequestPost(url,new BasicHeader[] {new BasicHeader("sign","")},params);
						} catch (IOException e1) {
							log.errLog("发送邮件异常", e1);
						}
					}		
				}
		log.infoLog("流失客户更新调度结束更新数量：", count +""
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date()));
		String url="http://soa-log2.duanrong.com/basic/mail/send";
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("subject", "失客户更新调度"));
		params.add(new BasicNameValuePair("content", "更新流失预警客户成功，更新客户"+count+"名"));
		params.add(new BasicNameValuePair("mailtos","guohuan@duanrong.com"));
		try {
			DRHTTPClient.sendHTTPRequestPost(url,new BasicHeader[] {new BasicHeader("sign","")},params);
		} catch (IOException e2) {
			log.errLog("发送邮件异常", e2);
		}
	}
	
}