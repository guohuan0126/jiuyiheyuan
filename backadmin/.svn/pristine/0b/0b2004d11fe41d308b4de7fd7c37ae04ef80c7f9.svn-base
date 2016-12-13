package base.schedule.job;

import javax.annotation.Resource;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;



import com.duanrong.business.loan.service.LoanService;

@Component
public class CheckLoanRepayTime implements Job {
	
	@Resource
	private LoanService loanService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
			
		String loanId = context.getJobDetail().getJobDataMap()
				.getString("loan_repay");
		
		loanService.updateOrganizationExclusiveStatus(loanId);
	}

}
