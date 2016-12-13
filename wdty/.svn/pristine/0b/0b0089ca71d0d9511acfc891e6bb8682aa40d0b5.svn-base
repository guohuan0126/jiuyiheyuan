package com.duanrong.schedule;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.duanrong.business.dao.PushLoanDao;
import com.duanrong.business.model.PushLoan;
import com.duanrong.business.service.PushService;
import com.duanrong.util.p2pEyeUtil.P2PEyeHttpUtil;


/**
 * 网贷天眼相关定时任务
 * @author bj
 *
 */
@Component
public class P2pEyeSchedule {

	private static Logger logger = Logger.getLogger(P2pEyeSchedule.class);
	@Autowired
	private PushService pushService;

	@Autowired
	public PushLoanDao pushLoanDao;
	
    /**
     * 推送交易信息  
     */
    @Scheduled(cron=" 0 */1 * * * ? ")
	public void pushInvestPerson(){
		logger.info("##############天眼自动推送任务启动#########");
		//1.推送投资人信息
		logger.info("############################1.推送投资人信息#######################");
		pushService.pushInvestPerson();
		//2.推送项目状态
		logger.info("############################2.推送项目状态#######################");
		List<PushLoan> loanList = this.pushLoanDao.getPushLoanStatusList();
		pushService.pushLoanStatusInfo(loanList);
		logger.info("##############天眼自动推送任务执行完毕#########");
	}


}
