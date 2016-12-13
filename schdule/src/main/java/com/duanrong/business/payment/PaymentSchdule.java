package com.duanrong.business.payment;

import static util.MyStringUtils.append;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import sun.misc.BASE64Decoder;
import util.Log;
import util.XMLUtil;

import com.duanrong.business.payment.model.PaymentDefrayPay;
import com.duanrong.business.payment.model.PaymentRechargeRecord;
import com.duanrong.business.payment.model.PaymentWithdrawRecord;
import com.duanrong.business.payment.model.PaymentYeepayTransferUser;
import com.duanrong.business.payment.service.PaymentDefrayPayService;
import com.duanrong.business.payment.service.PaymentRechargeRecordService;
import com.duanrong.business.payment.service.PaymentWithdrawRecordService;
import com.duanrong.business.payment.service.PaymentYeepayTransferUserService;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.trusteeship.model.TrusteeshipConstants;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.withdraw.service.WithdrawCashService;
import com.duanrong.payment.PaymentConstants;
import com.duanrong.payment.baofoo.util.HttpUtil;
import com.duanrong.payment.baofoo.util.JXMConvertUtil;
import com.duanrong.payment.baofoo.util.MapToXMLString;
import com.duanrong.payment.baofoo.util.RsaCodingUtil;
import com.duanrong.payment.baofoo.util.SecurityUtil;
import com.duanrong.payment.jd.defraypay.CodeConst;
import com.duanrong.payment.jd.model.BaseResponseDto;
import com.duanrong.payment.jd.model.QueryResultTradeEntity;
import com.duanrong.payment.jd.model.TradeQueryEntity;
import com.duanrong.payment.jd.utils.ByteUtil;
import com.duanrong.payment.jd.utils.FormatUtil;
import com.duanrong.payment.jd.utils.HttpsClientUtil;
import com.duanrong.payment.jd.utils.JsonUtil;
import com.duanrong.payment.jd.utils.PaymentIDGenerator;
import com.duanrong.payment.jd.utils.RSACoder;
import com.duanrong.payment.jd.utils.SHA256Util;
import com.duanrong.payment.jd.utils.SHAUtil;
import com.duanrong.payment.jd.utils.TDESUtil;
import com.duanrong.util.Dom4jUtil;
import com.duanrong.util.client.DRHTTPSClient;
import com.duanrong.util.json.FastJsonUtil;
import com.duanrong.yeepay.service.TrusteeshipQueryBusinessService;
import com.duanrong.yeepay.xml.GeneratorXML;
import com.duanrong.yeepaysign.CFCASignUtil;
import com.fuiou.util.MD5;

/**
 * 
 * @author xiao
 * 
 */
@Component
public class PaymentSchdule {

	@Resource
	PaymentRechargeRecordService paymentRechargeRecordService;

	@Resource
	PaymentWithdrawRecordService paymentWithdrawRecordService;
	
	@Resource
	TrusteeshipOperationService trusteeshipOperationService;
	
	@Resource
	TrusteeshipQueryBusinessService trusteeshipQueryBusinessService;
	
	@Resource
	PaymentYeepayTransferUserService paymentYeepayTransferUserService;
	
	@Resource
	WithdrawCashService withdrawCashService;
	
	@Resource
	PaymentDefrayPayService paymentDefrayPayService;

	
	@Resource
	SmsService smsService;
	
	@Resource
	Log log;

	/*@Scheduled(cron="0 0/5 * * * *")*/
	public void handleSendedOperations() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.infoLog("payment-轮询开始", sdf.format(new Date()));
		System.out.println("=========Spring Task=========");
		List<PaymentRechargeRecord> list = paymentRechargeRecordService.getUnCallbackOperations(10);
		if (CollectionUtils.isEmpty(list)) {
			log.infoLog("payment-轮询结束", "没有可以轮询的数据");
			return;
		}
		log.infoLog("payment-轮询开始", "轮询条数"+list.size());
		for (PaymentRechargeRecord record : list) {
			try {
				String paymentId = record.getPaymentId();
				if(PaymentConstants.Channel.JDPAY.equals(paymentId)){
					queryOrderInfo(record);
				}else if(PaymentConstants.Channel.FUIOU.equals(paymentId)){
					queryFuiouOrderInfo(record);
				}else if("Baofoo".equals(paymentId)){
					queryBaofooOrderInfo(record);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void queryBaofooOrderInfo(PaymentRechargeRecord record) {
		try {
			Map<String, Object> rechargeStatusResult = BaofooApi(record.getMarkId(), "06");
			//订单查询
			if(rechargeStatusResult.get("resp_code").equals("0000")&&rechargeStatusResult.get("resp_msg").equals("交易成功")){
				//订单查询 支付成功
				 record.setStatus(TrusteeshipConstants.Status.PASSED);
				 record.setResponseData(rechargeStatusResult.toString());
				 record.setResponseTime(new Date());
				 paymentRechargeRecordService.update(record);
				 log.errLog("宝付单笔订单查询-调度", "流水号："+record.getMarkId()+"成功,本地未成功，需要补单。",1);
			}else{
				log.infoLog("宝付单笔订单查询-调度", FastJsonUtil.objToJson(rechargeStatusResult));
				if(rechargeStatusResult.get("resp_msg").equals("订单未支付")||rechargeStatusResult.get("resp_msg").equals("交易失败")){
					record.setStatus(TrusteeshipConstants.Status.REFUSED);
					record.setResponseData(FastJsonUtil.objToJson(rechargeStatusResult));
					record.setResponseTime(new Date());
					paymentRechargeRecordService.update(record);
				}else{
					dealFail(record, rechargeStatusResult.toString());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.errLog("宝付调度查询", "查询异常"+record.toString()+e);
		}
		
	}
	public Map<String,Object> BaofooApi(String orderId,String txnSubType) throws IOException{
		Map<String,Object> map=new HashMap<>();
		
		Map<String,String> HeadPostParam = new HashMap<String,String>();
        HeadPostParam.put("version", "4.0.0.0");
        HeadPostParam.put("member_id", PaymentConstants.BaofooConfig.MEMBER);
        HeadPostParam.put("terminal_id", PaymentConstants.BaofooConfig.TERMINAL);
        HeadPostParam.put("txn_type", PaymentConstants.BaofooConfig.TXNTYPE);
        HeadPostParam.put("txn_sub_type", txnSubType);
        HeadPostParam.put("data_type", PaymentConstants.BaofooConfig.DATATYPE);
        
        String request_url = PaymentConstants.BaofooConfig.BAOFOO_PAY_URL;//
        
        String  pfxpath = PaymentConstants.BaofooConfig.PFXNAME;//商户私钥
        String  cerpath =  PaymentConstants.BaofooConfig.CERNAME;//宝付公钥
        String trade_date=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());//交易日期        
        Map<String,Object> XMLArray = new HashMap<String,Object>();
        
        /************************公共参数*********************/
        XMLArray.put("txn_sub_type", HeadPostParam.get("txn_sub_type"));
        XMLArray.put("biz_type", "0000");
        XMLArray.put("terminal_id", HeadPostParam.get("terminal_id"));
        XMLArray.put("member_id", HeadPostParam.get("member_id"));
        XMLArray.put("trans_serial_no",PaymentIDGenerator.generateId("BFQR"));//流水号
        XMLArray.put("trade_date", trade_date);
        XMLArray.put("additional_info", "附加信息");
        XMLArray.put("req_reserved", "保留");
        XMLArray.put("orig_trans_id", orderId);
        Map<Object,Object> ArrayToObj = new HashMap<Object,Object>();
		 String XmlOrJson = "";		 
		 if(HeadPostParam.get("data_type").equals("xml")){
			 ArrayToObj.putAll(XMLArray);
			 XmlOrJson = MapToXMLString.converter(ArrayToObj,"data_content");
		 }else{
			 JSONObject jsonObjectFromMap = JSONObject.fromObject(XMLArray);
			 XmlOrJson = jsonObjectFromMap.toString();
		 }
		String base64str = SecurityUtil.Base64Encode(XmlOrJson);
		String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str,pfxpath,PaymentConstants.BaofooConfig.PFX);	
		HeadPostParam.put("data_content", data_content);
		String PostString  = HttpUtil.RequestForm(request_url, HeadPostParam);	
		
		PostString = RsaCodingUtil.decryptByPubCerFile(PostString,cerpath);
		
		if(PostString.isEmpty()){//判断解密是否正确。如果为空则宝付公钥不正确
			return map;
		}
		
		PostString = SecurityUtil.Base64Decode(PostString);		 
	
		 if(HeadPostParam.get("data_type").equals("xml")){
				PostString = JXMConvertUtil.XmlConvertJson(PostString);		    
		}
		//将JSON转化为Map对象。
		 map = JXMConvertUtil.JsonConvertHashMap(PostString);//将JSON转化为Map对象。
		
		return map;
	}
	private void queryFuiouOrderInfo(PaymentRechargeRecord record) {
		if(record.getTransferWay().contains(PaymentConstants.Mode.PC)){
			queryFuiouPcOrderInfo(record.getMarkId());
		}else{
			queryFuiouMobileOrderInfo(record);
		}
	}

	
	private void queryFuiouPcOrderInfo(String orderId) {
//		StringBuffer buf = new StringBuffer();
//		String MchntCd=PaymentConstants.FuiouConfig.MER_MCHNTCD;
//		String pageNotifyUrl = "";
//		String backNotifyUrl = "";
//		String signDataStr = MchntCd+"|"+orderId+"|"+pageNotifyUrl+"|"+backNotifyUrl;
//		byte[] bytesKey = (new BASE64Decoder()).decodeBuffer("");
//		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(bytesKey);
//		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
//		Signature signature = Signature.getInstance("MD5WithRSA");
//		signature.initSign(rsa_prikey);
//		signature.update(signDataStr.getBytes("GBK"));
//		String RSA = (new BASE64Encoder()).encodeBuffer(signature.sign());
//		buf.append("<mchnt_cd>"+MchntCd+"</mchnt_cd>");
//		buf.append("<order_id>"+orderId+"</order_id>");
//		buf.append("<page_notify_url>"+orderId+"</page_notify_url>");
//		buf.append("<back_notify_url>"+orderId+"</back_notify_url>");
//		buf.append("<RSA>"+orderId+"</RSA>");
//		List<NameValuePair>postMethod=new ArrayList<>();
//		postMethod.add(new BasicNameValuePair("FM", buf.toString()));
//		String	responseBody=null;
//		Map<String, String> resultMap=null;
		
	}

	/**
	 * 单笔业务查询
	 * @param orderInfo
	 * @return
	 */
	public Map<String,String> queryFuiouMobileOrderInfo(PaymentRechargeRecord record){
		PaymentYeepayTransferUser transfer = paymentYeepayTransferUserService.getByRequestNo(record.getMarkId());
		if(transfer==null||StringUtils.isBlank(transfer.getPaymentNo())){
			log.errLog("富友调度", "未查到记录："+record.getMarkId());
			dealFail(record, "未查到记录："+record.getMarkId());
			return null;
		}
		log.infoLog("富友调度", transfer.getPaymentNo());
		StringBuffer buf = new StringBuffer();
		String MchntCd=PaymentConstants.FuiouConfig.MER_MCHNTCD;
		String sign=MD5.MD5Encode(MchntCd+"|"+transfer.getPaymentNo()+"|"+PaymentConstants.FuiouConfig.MER_MD5_KEY);
		buf.append("<FM>");
		buf.append("<MchntCd>"+MchntCd+"</MchntCd>");
		buf.append("<OrderId>"+transfer.getPaymentNo()+"</OrderId>");
		buf.append("<Sign>"+sign+"</Sign>");
		buf.append("</FM>");
		List<NameValuePair>postMethod=new ArrayList<>();
		postMethod.add(new BasicNameValuePair("FM", buf.toString()));
		String	responseBody=null;
		Map<String, String> resultMap=null;
		
		try {
			responseBody = DRHTTPSClient.sendHTTPRequestPostToString(PaymentConstants.FuiouConfig.FUIOU_QUERY_MOBILE_URL, new BasicHeader[0], postMethod);
			if(responseBody != null && responseBody.trim().length()>0){		
				 resultMap = Dom4jUtil.xmltoMap(responseBody);		
				 String Rcd=resultMap.get("Rcd");
				 String RDesc=resultMap.get("RDesc");
				 /*String sign=resultMap.get("Sign");*/
				 if(Rcd.equals("5185")&&RDesc.equals("订单已支付")){
					 record.setStatus(TrusteeshipConstants.Status.PASSED);
 					 record.setResponseData(responseBody);
 					 record.setResponseTime(new Date());
// 					 record.setCardNo(qrte.getCardNo());
 					 paymentRechargeRecordService.update(record);
					 log.errLog("富友单笔订单查询", "该"+RDesc+"富友订单号："+transfer.getPaymentNo()+",本地流水号："+record.getMarkId()+"未成功",1);
				 }else{
 					dealFail(record, responseBody);
 				}
				 
			}else{
				dealFail(record, responseBody);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	/*@Scheduled(cron="0 0/5 * * * ?")*/
	public void transferSendedOperations() {
		// 查找请求表里面，等待返回的数据，且请求时间在十分钟以外。
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.infoLog("yeepay-代付转账轮询开始", sdf.format(new Date()));
		List<PaymentWithdrawRecord> tos = paymentWithdrawRecordService.getYeeapyOperations(10);
		if (CollectionUtils.isEmpty(tos)) {
			log.infoLog("yeepay-代付转账轮询结束", "没有可以轮询的数据");
			return;
		}
		for (PaymentWithdrawRecord record : tos) {
			try {
				TrusteeshipOperation to = 
						trusteeshipOperationService.get("transfer", record.getMarkId(), record.getMarkId(), null);
				log.infoLog("RefreshTrusteeshipOperation",append("markID:", to.getMarkId()));			
				String respInfo = trusteeshipQueryBusinessService.query(to.getMarkId());
				Document respXML = DocumentHelper.parseText(respInfo);
				Map<String, String> resultMap = Dom4jUtil.xmltoMap(respInfo);
				if(resultMap != null){
					String code = resultMap.get("code");
					String records = resultMap.get("records");
					if(code != null && code.equals("1") && StringUtils.isNotBlank(records)){
						String status = respXML.selectSingleNode(
								"/response/records/record/status").getStringValue();
						if("PREAUTH".equals(status) && record.getStatus().equals("sended")){
							paymentWithdrawRecordService.transferFrozeLocal(to, record);													
						}else if("CONFIRM".equals(status) && record.getStatus().equals("frozen")){						
							paymentWithdrawRecordService.transferLocal(to, record);
						}/*else if("CANCEL".equals(status) && to.getStatus().equals("frozen")){
							to.setStatus("cancle");
							trusteeshipOperationService.update(to);							
							if( record.getIsDefrayPay().equals("success")){
								record.setStatus("fail");
							}else{
								record.setStatus("refused");
							}
							record.setConfirmResponseData(respInfo);
							record.setResponseTime(new Date());
							paymentWithdrawRecordService.update(record);
							
						}*/
					}else{
						dealFail(to, null);
					}
				}else{
					dealFail(to, null);
				}		
			} catch (Exception e) {
				e.printStackTrace();
				log.errLog("轮询异常", e);
			}
		}
		log.infoLog("yeepay-代付转账轮询结束", sdf.format(new Date()));
	}

	
	
	
	public  boolean  queryOrderInfo(PaymentRechargeRecord record) {
		
        QueryResultTradeEntity qrte = new QueryResultTradeEntity();
        try {
        	String merNum ="" ;
        	if(PaymentConstants.Mode.QUICK.equals(record.getType())){
    			if("pc".equals(record.getTransferWay())){
    				merNum = PaymentConstants.JDConfig.MER_NUM_PC;
    			}else{
    				merNum = PaymentConstants.JDConfig.MER_NUM_MOBILE;
    			}
    		}
//        	else if(operationType.contains(PaymentConstants.Mode.QUICK_MOBILE)){
//    			merNum = PaymentConstants.JDConfig.MER_NUM_MOBILE;
//    		}
            String tradeJsonData = "{\"tradeNum\": \"" + record.getMarkId() + "\"}";
            //1.对交易信息进行3DES加密
            String threeDesData = TDESUtil.encrypt2HexStr(RSACoder.decryptBASE64(PaymentConstants.JDConfig.MER_DES_KEY), tradeJsonData);

            //2.对3DES加密的数据进行签名
            String sha256Data = SHAUtil.Encrypt(threeDesData, null);
            byte[] rsaResult = RSACoder.encryptByPrivateKey(sha256Data.getBytes(), PaymentConstants.JDConfig.MER_PRI_KEY);
            String merchantSign = RSACoder.encryptBASE64(rsaResult);

            //3.构造最终交易查询请求json
            TradeQueryEntity queryTradeDTO = new TradeQueryEntity();
            queryTradeDTO.setVersion("1.0.5");
            queryTradeDTO.setMerchantNum(merNum);
            queryTradeDTO.setMerchantSign(FormatUtil.stringBlank(merchantSign));
            queryTradeDTO.setData(threeDesData);
            String json = JsonUtil.write2JsonStr(queryTradeDTO);
            log.infoLog("JD支付单笔业务调度查询", record.getMarkId()+"请求参数"+json);
            //4.发送请求
            String resultJsonData = HttpsClientUtil.sendRequest(PaymentConstants.JDConfig.QUERY, json);
            //5.验签返回数据
            log.infoLog("JD支付单笔业务调度查询", record.getMarkId()+"返回参数"+resultJsonData);
            BaseResponseDto<Map<String, Object>> result = (BaseResponseDto<Map<String, Object>>) JsonUtil.json2Object(resultJsonData, BaseResponseDto.class);
            //查询状态 成功
            if (result.getResultCode() == 0) {
                Map<String, Object> mapResult = result.getResultData();
                //有返回数据
                if (null != mapResult) {
                    String data = mapResult.get("data").toString();
                    String sign = mapResult.get("sign").toString();
                    //1.解密签名内容
                    byte[] decryptBASE64Arr = new BASE64Decoder().decodeBuffer(sign);
                    byte[] decryptArr = RSACoder.decryptByPublicKey(decryptBASE64Arr, PaymentConstants.JDConfig.MER_PUB_KEY);
                    String decryptStr = ByteUtil.byte2HexString(decryptArr);
                    //2.对data进行sha256摘要加密
                    String sha256SourceSignString = ByteUtil.byte2HexLowerCase(SHA256Util.encrypt(data.getBytes("UTF-8")));
                    //3.比对结果
                    if (decryptStr.equals(sha256SourceSignString)) {
                        /**
                         * 验签通过
                         */
                        //解密data
                        String decrypData = TDESUtil.decrypt4HexStr(RSACoder.decryptBASE64(PaymentConstants.JDConfig.MER_DES_KEY), data);
                        //注意 结果为List集合
                        List<Map<String, Object>> resultList = JsonUtil.jsonArray2List(decrypData);
                        if(resultList.size()==1){
                        	Map<String, Object> m = resultList.get(0);
                        	 qrte.setTradeCurrency(m.get("tradeCurrency") + "");
                             qrte.setTradeDate(m.get("tradeDate") + "");
                             qrte.setTradeTime(m.get("tradeTime") + "");
                             qrte.setTradeAmount(Integer.parseInt(m.get("tradeAmount") + ""));
                             qrte.setTradeNote(m.get("tradeNote") + "");
                             qrte.setTradeNum(m.get("tradeNum") + "");
                             qrte.setTradeStatus(m.get("tradeStatus") + "");
                             qrte.setCardNo(m.get("cardNo") + "");
                             qrte.setCardHolderMobile(m.get("cardHolderMobile") + "");
                             qrte.setCardHolderName(m.get("cardHolderName") + "");
                             qrte.setCardHolderId(m.get("cardHolderId") + "");
                             qrte.setPayAmount(m.get("payAmount") + "");
                             qrte.setBankCode(m.get("bankCode") + "");
                             qrte.setCardType(m.get("cardType") + "");
                        }
        				if("0".equals(qrte.getTradeStatus())){
        					record.setStatus(TrusteeshipConstants.Status.PASSED);
        					record.setResponseData(decrypData);
        					record.setResponseTime(new Date());
        					record.setCardNo(qrte.getCardNo());
        					record.setCardType(qrte.getCardType());
        					record.setBankCode(qrte.getBankCode());
        					paymentRechargeRecordService.update(record);
        			        log.errLog("payment-轮询", "流水号："+record.getMarkId()+"，JD支付已成功，但本地没有成功",1);
        					return true;
        				}else{
        					dealFail(record, decrypData);
        				}
                    } else {
                    	log.errLog("JD支付", "验签失败");
                        /**
                         * 验签失败  不受信任的响应数据
                         * 终止
                         */
                    	dealFail(record, "验签失败");
                    }
                }
            }else {
               log.errLog("JD支付", "单笔业务查询失败"+result.getResultMsg());
               dealFail(record, result.getResultMsg());
            }

        } catch (Exception e) {
            log.errLog("JD支付调度", "单笔业务查询失败"+record.getMarkId()+e);
        }
        return false;
	}
	
	private void dealFail(PaymentRechargeRecord to, String respInfo) {
		Calendar calendarTO = Calendar.getInstance();
		calendarTO.setTime(to.getRequestTime());
		calendarTO.add(Calendar.HOUR, 8);
		Calendar calendarNOW = Calendar.getInstance();
		calendarNOW.setTime(new Date());
		if (calendarNOW.getTimeInMillis() > calendarTO.getTimeInMillis()) {
			to.setStatus(TrusteeshipConstants.Status.REFUSED);
			to.setResponseData(respInfo);
			to.setResponseTime(new Date());
			paymentRechargeRecordService.update(to);
		}
	}

	private void dealFail(TrusteeshipOperation to, String respInfo) {
		Calendar calendarTO = Calendar.getInstance();
		calendarTO.setTime(to.getRequestTime());
		calendarTO.add(Calendar.HOUR, 4);
		Calendar calendarNOW = Calendar.getInstance();
		calendarNOW.setTime(new Date());
		if (calendarNOW.getTimeInMillis() > calendarTO.getTimeInMillis()) {
			to.setStatus(TrusteeshipConstants.Status.REFUSED);
			to.setResponseData(respInfo);
			to.setResponseTime(new Date());
			trusteeshipOperationService.update(to);
			PaymentWithdrawRecord record  = paymentWithdrawRecordService.query(
					to.getMarkId(), to.getMarkId(), "JDpay");
			if(record != null){
				String status = record.getIsDefrayPay();
				if(status.equals("success")){
					record.setStatus("fail");
				}else{
					record.setStatus("refused");
				}
				record.setConfirmResponseData(respInfo);
				record.setResponseTime(new Date());
				paymentWithdrawRecordService.update(record);
			}
		}
	}
	
	
	/*@Scheduled(cron="0 0/5 * * * ?")*/
	public void transferSendedDefrayPay() {
		// 查找请求表里面，代付中的数据，且请求时间在十分钟以外。
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.infoLog("京东代付轮询", "开始："+sdf.format(new Date()));
		List<PaymentWithdrawRecord> tos = paymentWithdrawRecordService.getDefraypayOperations(120);
		if (CollectionUtils.isEmpty(tos)) {
			log.infoLog("京东代付轮询结束", "没有可以轮询的数据");
			return;
		}
		log.infoLog("京东代付轮询", "问题条数："+tos.size());
		for (PaymentWithdrawRecord record : tos) {
			Date date = new Date();
			try {
				String orderId = record.getMarkId();
				PaymentDefrayPay paymentDefrayPay = paymentDefrayPayService.get(orderId);
				if(paymentDefrayPay==null){
					log.infoLog("京东代付轮询", "未找到代付数据，流水号"+orderId);
					return ;
				}
				log.infoLog("京东代付轮询", "流水号："+paymentDefrayPay.getRequestNo());
				Map<String,String> map = paymentWithdrawRecordService.tradeQuery(orderId);
				
				if(map!=null){
					log.infoLog("京东代付轮询", "查询结果："+map.toString());
					String trade_status = map.get("trade_status");
					paymentDefrayPay.setMoney(Integer.parseInt(map.get("trade_amount"))/100);
					paymentDefrayPay.setResponseData(map.toString());
					paymentDefrayPay.setResponseTime(date);
					if(CodeConst.TRADE_FINI.equals(trade_status)){//交易成功
						record.setIsDefrayPay("success");
						paymentDefrayPay.setStatus("success");
						String respInfo = confirmYeepay(map.get("out_trade_no"));
						// 响应信息
						if(StringUtils.isNotBlank(respInfo)){
							Map<String, String> resultMap = Dom4jUtil.xmltoMap(respInfo);
							String code = resultMap.get("code");
							if (StringUtils.equals("1", code)) {
								record.setStatus("confirm");
								
							} else {
								record.setIsDefrayPay("fail");
							}
						}
						record.setConfirmResponseData(respInfo);
						record.setConfirmResponseTime(new Date());
						paymentDefrayPay.setMsg("交易成功");
					}else if(CodeConst.TRADE_CLOS.equals(trade_status)){
						record.setIsDefrayPay("fail");
						paymentDefrayPay.setStatus("fail");
						paymentDefrayPay.setMsg("交易关闭，交易失败");
					}else if(CodeConst.TRADE_WPAR.equals(trade_status)||CodeConst.TRADE_BUID.equals(trade_status)||CodeConst.TRADE_ACSU.equals(trade_status)){
						paymentDefrayPay.setStatus(trade_status);
						paymentDefrayPay.setMsg("等待支付结果，处理中");
					}
					paymentWithdrawRecordService.update(record);
					paymentDefrayPayService.update(paymentDefrayPay);
				}else{
					log.infoLog("京东代付轮询", "查询结果为空");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.infoLog("京东代付轮询", e);
			}
		}
		log.infoLog("京东代付轮询结束", sdf.format(new Date()));
	}
	
	private String confirmYeepay(String orderId){
		try{
			GeneratorXML xml = new GeneratorXML();
			xml.setPlatformNo(TrusteeshipYeepayConstants.Config.MER_CODE);
			xml.setRequestNo(orderId);
			xml.setMode("CONFIRM");
			xml.setNotifyUrl(TrusteeshipYeepayConstants.ResponseS2SUrl.PRE_RESPONSE_URL
					+ TrusteeshipYeepayConstants.OperationType.THANAUTH);			
			String content = null;
			try {
				content = XMLUtil.getXML(xml);
			} catch (Exception e) {
				log.errLog("创建转账确认XML拼接异常", e);
			}

			String sign = CFCASignUtil.sign(content);
			// 包装参数
			Map<String, String> params = new HashMap<String, String>();
			params.put("req", content);
			params.put("sign", sign);

			log.infoLog("转账确认XML", content);
			log.infoLog("转账确认sign", sign);
			// 创建直连请求
			HttpClient client = new HttpClient();
			PostMethod postMethod = new PostMethod(
					TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
			postMethod.setParameter("req", content);
			postMethod.setParameter("sign", sign);		
			postMethod.setParameter("service", "COMPLETE_TRANSACTION");
			int statusCode = client.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				log.errLog("Class PaymentWithdrawCashServiceImpl.transferInConfirm", "Method failed: " + postMethod.getStatusLine());
			}
			// 获得返回的结果
			byte[] responseBody = postMethod.getResponseBody();
			String respInfo = new String(responseBody, "UTF-8");
			log.infoLog("京东代付", "易宝确认:orderId"+orderId+","+respInfo);
			return  respInfo;
		}catch (Exception ex) {
			log.errLog("京东代付", "易宝转账确认操作异常"+ex);
		}
		return null;
	}
	
}