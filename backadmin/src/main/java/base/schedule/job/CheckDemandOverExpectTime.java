package base.schedule.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import util.Log;

import com.duanrong.business.demand.model.AvailableMoneyRecord;
import com.duanrong.business.demand.model.Demandtreasure;
import com.duanrong.business.demand.model.DemandtreasureLoan;
import com.duanrong.business.demand.service.AvailableMoneyRecordService;
import com.duanrong.business.demand.service.DemandTreasureLoanService;
import com.duanrong.business.demand.service.DemandtreasureService;

@Component
public class CheckDemandOverExpectTime implements Job{

	@Resource
	AvailableMoneyRecordService availableMoneyRecordService;
	
	@Resource
	DemandtreasureService demandtreasureService;
	@Resource
	DemandTreasureLoanService demandTreasureLoanService;
	@Resource
	Log log;
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		System.out.println("**************活期宝预计执行任务***************");
		String demandID = context.getJobDetail().getJobDataMap()
				.getString("demandID");
		System.out.println(demandID);
		AvailableMoneyRecord availableMoneyRecord=null;		
		try{
			availableMoneyRecord = availableMoneyRecordService.read(demandID);
			List<Demandtreasure> list = demandtreasureService.readAll();
			if (list.size() > 0) {		
				Demandtreasure demand = list.get(0);			
				demand.setUpdateTime(new Date());
				demand.setAvailableMoney(demand.getAvailableMoney() + availableMoneyRecord.getMoney());
				demandtreasureService.update(demand);
			}	
			availableMoneyRecord.setStatus(1);
		}catch(Exception e){			
			e.printStackTrace();
			
			log.errLog("活期宝预期执行调度", e);
			availableMoneyRecord.setStatus(-1);
		}
		availableMoneyRecordService.update(availableMoneyRecord);		
	}

}