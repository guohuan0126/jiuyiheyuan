package base.schedule.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import util.Log;
import util.SpringBeanUtil;
import base.exception.AccountException;
import base.exception.TradeException;

import com.duanrong.drpay.business.invest.InvestConstants;
import com.duanrong.drpay.business.invest.model.Invest;
import com.duanrong.drpay.business.invest.service.InvestService;
import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.trusteeship.service.TrusteeshipTransactionQueryService;

public class CheckInvestOverExpectTime implements Job {

	private InvestService investService;
	
	private TrusteeshipTransactionQueryService trusteeshipTransactionQueryService;

	private Log log;

	/**
	 * 处理五分钟调度
	 * 1、判断状态是否为等待确认
	 * 2、去单笔业务查询是否成功
	 * 3、修改本地数据
	 */
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		investService = (InvestService) SpringBeanUtil
				.getBeanByType(InvestService.class);
		trusteeshipTransactionQueryService = (TrusteeshipTransactionQueryService) SpringBeanUtil
				.getBeanByType(TrusteeshipTransactionQueryService.class);
		String id = context.getJobDetail().getJobDataMap().getString("invest");
		log = SpringBeanUtil.getBeanByType(Log.class);
		log.infoLog("投资qrtz", "调度开始" + id);
		Invest invest = investService.get(id);
		if (InvestConstants.InvestStatus.WAIT_AFFIRM.equals(invest.getStatus())) {
			try {
				trusteeshipTransactionQueryService.queryTransaction(invest.getId(), BusinessEnum.invest,1);
			} catch (TradeException | AccountException e) {
				e.printStackTrace();
				log.errLog("投资调度异常", "investId:"+id);
			}
		}
	}

}
