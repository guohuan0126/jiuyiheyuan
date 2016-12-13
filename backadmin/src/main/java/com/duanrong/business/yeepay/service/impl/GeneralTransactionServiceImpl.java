package com.duanrong.business.yeepay.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Service;

import util.Log;
import util.XMLUtil;
import base.exception.InsufficientFreezeAmountException;
import base.exception.NoOpenAccountException;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.yeepay.constants.GeneralTransferConstants;
import com.duanrong.business.yeepay.dao.TransactionAuthorizationDao;
import com.duanrong.business.yeepay.dao.TransactionAuthorizationDetailDao;
import com.duanrong.business.yeepay.model.TransactionAuthorization;
import com.duanrong.business.yeepay.model.TransactionAuthorizationDetail;
import com.duanrong.business.yeepay.service.GeneralTransactionService;
import com.duanrong.util.Dom4jUtil;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.MapUtil;
import com.duanrong.yeepay.xml.GeneratorXML;
import com.duanrong.yeepaysign.CFCASignUtil;

@Service
public class GeneralTransactionServiceImpl implements GeneralTransactionService {

	@Resource
	TransactionAuthorizationDao transactionAuthorizationDao;

	@Resource
	TransactionAuthorizationDetailDao transactionAuthorizationDetailDao;
	@Resource
	Log log;
	@Resource
	TrusteeshipOperationService trusteeshipOperationService;
	@Resource
	UserAccountService userAccountService;
	
	@Resource
	SmsService smsService;

	@Override
	public PageInfo<TransactionAuthorization> readPageInfo(int pageNo,
			int pageSize,Map map) {
		return transactionAuthorizationDao.pageInfo(pageNo, pageSize, map);
	}

	@Override
	public List<TransactionAuthorizationDetail> readDetails(String id) {
		TransactionAuthorizationDetail d=new TransactionAuthorizationDetail();
		d.setTransactionAuthorizationId(id);
		return transactionAuthorizationDetailDao.find(d);
	}

	
	@Override
	public void authConfirm(String tranid, String flag) throws IOException, NoOpenAccountException, InsufficientFreezeAmountException {
		
		   TransactionAuthorization tran=transactionAuthorizationDao.get(tranid);
		   List<TransactionAuthorizationDetail> list=readDetails(tranid);
		   if(tran==null){
			   return;
		   }else{
			// 拼接请求字符串
				GeneratorXML xml = new GeneratorXML();
				xml.setPlatformNo(TrusteeshipYeepayConstants.Config.MER_CODE);
				xml.setRequestNo(tranid);
				xml.setMode(flag);
				xml.setNotifyUrl(TrusteeshipYeepayConstants.ResponseS2SUrl.PRE_RESPONSE_URL
						+ TrusteeshipYeepayConstants.OperationType.THANAUTH);			
				String content = null;
				try {
					content = XMLUtil.getXML(xml);
				} catch (Exception e) {
					log.errLog("创建转账确认XML拼接异常", e);
					return;
				}

				String sign = CFCASignUtil.sign(content);
				// 包装参数
				Map<String, String> params = new HashMap<String, String>();
				params.put("req", content);
				params.put("sign", sign);

				log.infoLog("转账确认XML", content);
				log.infoLog("转账确认sign", sign);
				
				TrusteeshipOperation to = new TrusteeshipOperation();
				to.setId(IdGenerator.randomUUID());
				to.setMarkId(tranid);
				to.setOperator(tranid);
				to.setRequestUrl(TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
				to.setRequestData(MapUtil.mapToString(params));
				to.setRequestTime(new Date());
				to.setStatus(TrusteeshipYeepayConstants.Status.SENDED);
				to.setType(TrusteeshipYeepayConstants.OperationType.THANAUTH);
				to.setUserId(tran.getUserId());
				to.setTrusteeship("yeepay");
				trusteeshipOperationService.insert(to);
				// 创建直连请求
				HttpClient client = new HttpClient();
				PostMethod postMethod = new PostMethod(
						TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
				
									
				postMethod.setParameter("req", content);
				postMethod.setParameter("sign", sign);		
				postMethod.setParameter("service", "COMPLETE_TRANSACTION");
				int statusCode = client.executeMethod(postMethod);
				if (statusCode != HttpStatus.SC_OK) {
					log.errLog("Class GeneralTransactionServiceImpl.authConfirm", "Method failed: " + postMethod.getStatusLine());
				}
				// 获得返回的结果
				byte[] responseBody = postMethod.getResponseBody();
				log.infoLog(tranid + "转账确认", new String(
						responseBody, "UTF-8"));			
				@SuppressWarnings("unchecked")
				Map<String, String> respMap = Dom4jUtil.xmltoMap(new String(
						responseBody, "UTF-8"));
				String code = respMap.get("code");
				to.setResponseData(new String(responseBody, "UTF-8"));
				to.setResponseTime(new Date());
				if ("1".equals(code)) {
					if (GeneralTransferConstants.TransferStatus.CONFIRM.equals(flag)) {
						tran.setStatus(GeneralTransferConstants.TransferStatus.CONFIRM);
						transactionAuthorizationDao.update(tran);					
						
						if(!tran.getUserId().equals("10012401196")){
							userAccountService.tofreeze(tran.getUserId(), tran.getAmount(), BusinessEnum.transfer, "用户："+tran.getUserId()+ "成功!转出金额",
									"转账ID：" +  tran.getId(), tranid);
						}
						for(TransactionAuthorizationDetail temp:list){
							temp.setStatus(GeneralTransferConstants.TransferStatus.CONFIRM);
							transactionAuthorizationDetailDao.update(temp);
							if(!temp.getUserId().equals("10012401196"))//不能是商户平台编号
							{
								userAccountService.transferIn(temp.getUserId(), temp.getAmount(), BusinessEnum.transfer, "用户："+temp.getUserId()+ "被用户"+tran.getDuserId()+"成功通过转账!转入金额", "转账ID：" +  tran.getId(), tran.getId());
							}
						}
						
					}else{
						tran.setStatus(GeneralTransferConstants.TransferStatus.CANCEL);
						transactionAuthorizationDao.update(tran);
						
						if(!tran.getUserId().equals("10012401196")){
							userAccountService.unfreeze(tran.getUserId(),
									tran.getAmount(), BusinessEnum.transfer, "取消转账解冻转账金额" , "转账ID：" +tran.getDuserId(), tran.getDuserId());
						}
						for(TransactionAuthorizationDetail temp:list){
							temp.setStatus(GeneralTransferConstants.TransferStatus.CANCEL);
							transactionAuthorizationDetailDao.update(temp);
						}
					}
					to.setStatus(TrusteeshipYeepayConstants.Status.PASSED);
					trusteeshipOperationService.update(to);	
				} else {
						
						to.setStatus(TrusteeshipYeepayConstants.Status.REFUSED);
						trusteeshipOperationService.update(to);
					}
				}
			}

	@Override
	public void insertGeneralTransaction(TransactionAuthorization model) {
		transactionAuthorizationDao.insert(model);
		List<TransactionAuthorizationDetail> listDs = model.getTransactionAuthorizationDetails();
		if(listDs !=null && listDs.size() >0)
		{
			transactionAuthorizationDetailDao.insert(model.getTransactionAuthorizationDetails().get(0));
		}
	}

	@Override
	public void updateTA(TransactionAuthorization transactionAuthorization) {
		transactionAuthorizationDao.update(transactionAuthorization);
	}

	@Override
	public void updateTAD(
			TransactionAuthorizationDetail transactionAuthorizationDetail) {
		transactionAuthorizationDetailDao
				.update(transactionAuthorizationDetail);
	}
	
	@Override
	public TransactionAuthorization readTA(String id) {
		return transactionAuthorizationDao.get(id);
	}

}
