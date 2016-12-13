package base.schedule.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import base.schedule.util.SpringBeanUtils;

import com.duanrong.business.yeepay.service.FreezeService;

/**
 * 检查项目是否到预计执行时间
 * 
 * @author Lin Zhiming
 * @version Mar 2, 2015 11:50:34 AM
 */
@Component
public class CheckFreezeOverExpectTime implements Job {

	public static final String FREEZE_ID = "freezeId";

	private FreezeService freezeService;

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		freezeService = (FreezeService) SpringBeanUtils
				.getBeanByType(FreezeService.class);
		String freezeId = context.getJobDetail().getJobDataMap()
				.getString(FREEZE_ID);
		freezeService.dealOverExpectTime(freezeId);
	}
}