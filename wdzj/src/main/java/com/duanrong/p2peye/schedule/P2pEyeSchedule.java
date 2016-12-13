package com.duanrong.p2peye.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.duanrong.business.push.dao.PushLoanDao;
import com.duanrong.business.push.model.PushLoan;
import com.duanrong.business.push.service.PushService;


/**
 * 网贷天眼相关定时任务
 * @author bj
 *
 */
@Component
public class P2pEyeSchedule {

	
	@Autowired
	private PushService pushService;

	@Autowired
	public PushLoanDao pushLoanDao;
	
    /**
     * 推送交易信息  
     */
    @Scheduled(cron=" 0 */5 * * * ? ")
	public void pushInvestPerson(){
		System.out.println("##############推送任务启动#########");
		//1.推送投资人信息
		Integer pushCode = pushService.pushInvestPerson();
		if(pushCode == 200 || pushCode == 0){
			System.out.println("投资人信息推送成功："+pushCode);
		}else{
			System.out.println("投资人信息推送失败："+pushCode);
		}
		//2.推送项目状态
		List<PushLoan> loanList = this.pushLoanDao.getPushLoanStatusList();
		System.out.println("需要推送的项目数量："+loanList.size());
		pushCode = pushService.pushLoanStatusInfo(loanList);
		if(pushCode == 200 || pushCode == 0){
			System.out.println("项目状态推送成功："+pushCode);
		}else{
			System.out.println("项目状态推送失败："+pushCode);
		}
		System.out.println("##############推送任务执行完毕#########");
	}


}
