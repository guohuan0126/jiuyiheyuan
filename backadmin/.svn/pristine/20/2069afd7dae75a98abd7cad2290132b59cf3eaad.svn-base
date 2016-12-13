package base.schedule.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import base.schedule.util.SpringBeanUtils;

import com.duanrong.business.loan.service.LoanService;

/**
 * 检查项目是否到预计执行时间
 * 
 * @author Lin Zhiming
 * @version Mar 2, 2015 11:50:34 AM
 */
@Component
public class CheckLoanOverExpectTime implements Job {

	public static final String LOAN_ID = "loanId";

	private LoanService loanService;

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		loanService = (LoanService) SpringBeanUtils
				.getBeanByType(LoanService.class);
		String loanId = context.getJobDetail().getJobDataMap()
				.getString(LOAN_ID);
		loanService.dealOverExpectTime(loanId);
	}
}
