package com.duanrong.drpay.trusteeship.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import util.FastJsonUtil;
import util.Log;

import com.duanrong.drpay.business.trusteeship.model.TrusteeshipNotify;
import com.duanrong.drpay.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.drpay.trusteeship.constants.TrusteeshipServer;
import com.duanrong.drpay.trusteeship.helper.model.BizType;
import com.duanrong.drpay.trusteeship.helper.model.Details;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorAsynDetailJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorRechargeJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorWithdrawJSON;
import com.duanrong.drpay.trusteeship.service.TrusteeshipPaymentService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipRepayService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipTradeService;

/**
 * 主动通知接口 适用于业务：个人企业 审核结果、 待确认提现的确认和取消、 提现拦截、 提现的打款状态、 充值订单状态、 批量业务回调
 * 
 * @author xiao
 * @datetime 2016年12月20日 上午9:25:54
 */
@Controller
@RequestMapping(value = "/depository", method = RequestMethod.POST)
public class NotifyController {

	@Resource
	TrusteeshipPaymentService trusteeshipPaymentService;

	@Resource
	TrusteeshipTradeService trusteeshipTradeService;
	
	@Resource
	TrusteeshipRepayService trusteeshipRepayService;

	@Resource
	TrusteeshipOperationService trusteeshipOperationService;
	
	@Resource
	Log log;

	@RequestMapping(value = "/notify.do")
	public void notify(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.getWriter().write("SUCCESS");		
		String respData = request.getParameter("respData");
		String serviceName = request.getParameter("serviceName");
		String sign = request.getParameter("sign");
		try{
			TrusteeshipNotify notify = new TrusteeshipNotify();
			notify.setResponseData(respData);
			notify.setServiceName(serviceName);
			notify.setSign(sign);
			notify.setType("xmbank");
			trusteeshipOperationService.insertNotify(notify);
		}catch(Exception e){
			e.printStackTrace();
			log.errLog("记录异步通知错误", e);
		}
		if (TrusteeshipServer.WITHDRAW.name().equals(serviceName)) {
			trusteeshipPaymentService
					.withdrawCallback((GeneratorWithdrawJSON) FastJsonUtil
							.jsonToObj(respData, GeneratorWithdrawJSON.class));
		} else if (TrusteeshipServer.RECHARGE.name().equals(serviceName)) {
			try {
				trusteeshipPaymentService
						.rechargeCallback((GeneratorRechargeJSON) FastJsonUtil
								.jsonToObj(respData,
										GeneratorRechargeJSON.class));
			} catch (Exception e) {
				log.errLog("充值订单通知",
						"respData: " + respData + ExceptionUtils.getMessage(e),
						1);
			}
		} else if (TrusteeshipServer.ENTERPRISE_REGISTER.name().equals(serviceName)) {
			log.errLog("企业审核结果通知", "respData: " + respData + ", 请及时处理", 1);
			// 批量放款异步通知
		} else if (null == serviceName || serviceName.equals("")) {
			Details derails = (Details) FastJsonUtil.jsonToObj(respData, Details.class);			
			List<GeneratorAsynDetailJSON> generatorAsynDetails = derails.getDetails();
			for (GeneratorAsynDetailJSON generatorAsynDetail : generatorAsynDetails) {
				try {
					// 放款通知
					if (generatorAsynDetail.getBizType() == BizType.IP_TENDER) {
						trusteeshipTradeService
								.giveMoneyToBorrowerCallback(generatorAsynDetail);
					//还款通知
					}else if(generatorAsynDetail.getBizType() == BizType.IP_REPAYMENT){
						trusteeshipRepayService.repayCallback(generatorAsynDetail);
					}	
				} catch (Exception e) {
					log.errLog("批量异步通知", "respData: " + respData
							+ ExceptionUtils.getMessage(e), 1);
				}
			}
		}
	}
}
