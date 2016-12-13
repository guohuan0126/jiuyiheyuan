package com.duanrong.yeepay.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.Log;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.trusteeship.model.TrusteeshipConstants;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.yeepay.constants.GeneralTransferConstants;
import com.duanrong.business.yeepay.model.TransactionAuthorization;
import com.duanrong.business.yeepay.model.TransactionAuthorizationDetail;
import com.duanrong.business.yeepay.service.GeneralTransactionService;
import com.duanrong.util.Dom4jUtil;
import com.duanrong.yeepay.service.TrusteeshipTransactionAuthorizationService;

@Service
public class TrusteeshipTransactionAuthorizationServiceImpl implements
		TrusteeshipTransactionAuthorizationService {

	@Resource
	TrusteeshipOperationService trusteeshipOperationService;

	@Resource
	UserAccountService userAccountService;

	@Resource
	GeneralTransactionService generalTransactionService;

	@Resource
	Log log;

	@Override
	public void S2SCallback(HttpServletRequest request,
			HttpServletResponse response) {
		// 响应的参数 为XML格式
		String notifyXML = request.getParameter("notify");

		/********** 参数解析和对象获取 **********/
		Map<String, String> resultMap = Dom4jUtil.xmltoMap(notifyXML);
		String code = resultMap.get("code");
		String requestNo = resultMap.get("requestNo");
		String description = resultMap.get("status");

		TrusteeshipOperation to = trusteeshipOperationService.read(
				TrusteeshipYeepayConstants.OperationType.TO_CP_TRANSACTION,
				requestNo, requestNo, "yeepay");
		if (to == null) {
			return;
		}
		to.setResponseTime(new Date());

		log.infoLog("转张授权resp", code);
		/********** 授权成功 **********/
		if (StringUtils.equals("1", code)) {
			try {
				TransactionAuthorization transactionAuthorization = new TransactionAuthorization();
				transactionAuthorization.setId(requestNo);
				transactionAuthorization
						.setStatus(GeneralTransferConstants.TransferStatus.PREAUTH);
				generalTransactionService.updateTA(transactionAuthorization);
				TransactionAuthorizationDetail transactionAuthorizationDetail = new TransactionAuthorizationDetail();
				transactionAuthorizationDetail
						.setTransactionAuthorizationId(requestNo);
				transactionAuthorizationDetail
						.setStatus(GeneralTransferConstants.TransferStatus.PREAUTH);
				generalTransactionService
						.updateTAD(transactionAuthorizationDetail);

				TransactionAuthorization ta = generalTransactionService
						.readTA(requestNo);
				if (!ta.getUserId().equals("10012401196")) {
					userAccountService.freeze(ta.getUserId(), ta.getAmount(),
							BusinessEnum.transfer, "通用转账",
							"通用转账  转账id:" + ta.getId(), requestNo);
				}
			} catch (Exception e) {
				return;
			}
			to.setStatus(TrusteeshipConstants.Status.PASSED);
			to.setResponseData(notifyXML);
		} else {
			to.setStatus(TrusteeshipConstants.Status.REFUSED);
			to.setResponseData(description);
		}

		trusteeshipOperationService.update(to);
	}

}