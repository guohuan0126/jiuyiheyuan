package base.schedule.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class CheckDemandInOverExpectTime implements Job {

/*	private DemandtreasureTransferInService demandtreasureTransferInService;;
	
	private Log log;*/
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
//		try {
//		log.infoLog("活期宝qrtz", "调度开始");
//		
//		demandtreasureTransferInService = (DemandtreasureTransferInService) SpringBeanUtil
//				.getBeanByType(DemandtreasureTransferInService.class);
//		log  = SpringBeanUtil.getBeanByType(Log.class);
//		String id = context.getJobDetail().getJobDataMap()
//				.getString("demandin");
//		log.infoLog("活期宝qrtz", "调度开始"+id);
//		DemandtreasureTransferIn demandIn = demandtreasureTransferInService.get(id);
//		
//		if("sended".equals(demandIn.getStatus())){
//			try {
//				/*trusteeshipOperationService = (TrusteeshipOperationService) SpringBeanUtils
//						.getBeanByType(TrusteeshipOperationService.class);
//				TrusteeshipOperation to = trusteeshipOperationService.query(
//						TrusteeshipYeepayConstants.OperationType.TO_RCIN, id,
//						id, "yeepay");*/
//				//demandInRecord(to,demandIn);
//			} catch (Exception e) {
//				log.errLog("活期宝qrtz", "调度异常"+e);
//			}
//		}else{
//			log.infoLog("活期宝qrtz", "状态异常"+demandIn.getStatus());
//		}
//		} catch (Exception e) {
//			log.errLog("活期宝qrtz", "调度异常"+e);
//		}
	}

	//private void demandInRecord(TrusteeshipOperation to,DemandtreasureTransferIn demandIn) {
		/*HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
		*//*********************** XML拼接 ***********************//*
		GeneratorXML xml = new GeneratorXML();
		xml.setPlatformNo(TrusteeshipYeepayConstants.Config.MER_CODE);
		xml.setRequestNo(to.getMarkId());
		xml.setMode("CP_TRANSACTION");
		String content = null;
		try {
			content = XMLUtil.getXML(xml);
		} catch (Exception e) {
			log.errLog("投资轮询XML拼接异常", e);
			return;
		}*/

		/*********************** 生成签名 ***********************/
		/*String sign = CFCASignUtil.sign(content);
		log.infoLog("活期宝qrtz:"+to.getMarkId(),"content:"+ content+",sign:"+sign);
		postMethod.setParameter("req", content);
		postMethod.setParameter("sign", sign);
		postMethod.setParameter("service", "QUERY");*/

		/*********************** 执行POST ***********************/
		/*try {
			int statusCode = client.executeMethod(postMethod);
			if (HttpStatus.SC_OK != statusCode) {
				log.errLog("活期宝轮询HTTP状态码异常", postMethod.getStatusLine()
						.toString());
			}

			// 易宝响应结果
			byte[] responseBody = postMethod.getResponseBody();
			recordData(responseBody, "demandIn");
			String respInfo = new String(responseBody, "UTF-8");
			log.infoLog("活期宝qrtz:"+to.getMarkId(), respInfo);
			// 响应信息
			Document respXML = DocumentHelper.parseText(respInfo);
			Map<String, String> resultMap = Dom4jUtil.xmltoMap(respInfo);
			String code = resultMap.get("code");
			String records = resultMap.get("records");
			// 返回码为1标识成功
			if ("1".equals(code) && StringUtils.isNotBlank(records)) {
				//订单状态：PREAUTH 已授权。CONFIRM：已确讣出款。CANCEL：已取消。DIRECT：直接划转。
				String status = respXML.selectSingleNode("/response/records/record/status").getStringValue();
				//处理状态: PROCESSING：处理中。SUCCESS：成功。ERROR：异常。FAIL：失败
				String subStatus = respXML.selectSingleNode("/response/records/record/subStatus").getStringValue();
				if ("SUCCESS".equals(subStatus) && "PREAUTH".equals(status)) {
					if (DemandtreasureConstants.TransferInStatus.SENDED.equals(demandIn.getStatus())) {
						demandtreasureTransferInService.syncDemandIn(demandIn, "S2SSuccess");
					}
					to.setStatus(TrusteeshipConstants.Status.PASSED);
					to.setResponseData(respInfo);
					to.setResponseTime(new Date());
					trusteeshipOperationService.update(to);
				} if ("ERROR".equals(subStatus)||"FAIL".equals(subStatus)||"CANCEL".equals(status)) {
					if (DemandtreasureConstants.TransferInStatus.SENDED.equals(demandIn.getStatus())) {
						demandtreasureTransferInService.syncDemandIn(demandIn, "S2SFail");
					}
					to.setStatus(TrusteeshipConstants.Status.REFUSED);
					to.setResponseData(respInfo);
					to.setResponseTime(new Date());
					trusteeshipOperationService.update(to);
				}
			} else {
				//当调用易宝失败时，活期宝转入表中的In状态还是SENDED时，进行同步失败操作
				if (DemandtreasureConstants.TransferInStatus.SENDED.equals(demandIn.getStatus())) {
					try {
						demandtreasureTransferInService.syncDemandIn(demandIn, "S2SFail");
					} catch (Exception ex) {
						log.errLog("活期宝qrtz:"+to.getMarkId(), ex);
						return;
					}
				}
				to.setStatus(TrusteeshipConstants.Status.REFUSED);
				to.setResponseData(respInfo);
				to.setResponseTime(new Date());
				trusteeshipOperationService.update(to);
			}
		} catch (Exception e) {
			log.errLog("活期宝qrtz调度流标"+to.getMarkId(), e);
		} finally {
			postMethod.releaseConnection();
		}*/
	//}
	
}
