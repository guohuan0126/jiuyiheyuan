package com.duanrong.payment.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.eclipse.jetty.util.ajax.JSON;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Service;

import util.Log;
import util.MyCollectionUtils;
import util.XMLUtil;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.PaymentEnum;
import com.duanrong.business.account.service.PaymentAccountService;
import com.duanrong.business.account.service.PlatformAccountService;
import com.duanrong.business.bankcard.model.BankCard;
import com.duanrong.business.bankcard.service.BankCardService;
import com.duanrong.business.payMentChannel.service.PayMentChannelService;
import com.duanrong.business.paymentInstitution.model.PaymentDefrayPay;
import com.duanrong.business.paymentInstitution.service.PaymentDefrayPayService;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.business.withdraw.dao.WithdrawCashDao;
import com.duanrong.business.withdraw.model.PaymentWithdrawRecord;
import com.duanrong.business.withdraw.model.WithdrawCash;
import com.duanrong.business.withdraw.service.PaymentWithdrawRecordService;
import com.duanrong.payment.PaymentConstants;
import com.duanrong.payment.baofoo.util.HttpUtil;
import com.duanrong.payment.baofoo.util.RsaCodingUtil;
import com.duanrong.payment.baofoo.util.SecurityUtil;
import com.duanrong.payment.jd.defraypay.CodeConst;
import com.duanrong.payment.jd.defraypay.Contants;
import com.duanrong.payment.jd.defraypay.RequestUtil;
import com.duanrong.payment.model.ReapTransContent;
import com.duanrong.payment.model.ReapTransContentCallBack;
import com.duanrong.payment.model.TransContent;
import com.duanrong.payment.model.TransReqBF0040001;
import com.duanrong.payment.model.TransReqBF0040002;
import com.duanrong.payment.model.TransReqBF0040004;
import com.duanrong.payment.service.PaymentWithdrawCashService;
import com.duanrong.util.DateUtil;
import com.duanrong.util.Dom4jUtil;
import com.duanrong.util.Week;
import com.duanrong.util.client.DRHTTPClient;
import com.duanrong.util.client.DRHTTPSClient;
import com.duanrong.util.xml.JaxbXmlUtil;
import com.duanrong.yeepay.service.TrusteeshipQueryBusinessService;
import com.duanrong.yeepay.xml.GeneratorXML;
import com.duanrong.yeepaysign.CFCASignUtil;
import com.fuiou.util.MD5;

@Service
public class PaymentWithdrawCashServiceImpl implements PaymentWithdrawCashService {

	@Resource
	PaymentWithdrawRecordService paymentWithdrawRecordService;
	
	
	@Resource
	WithdrawCashDao withdrawCashDao;
	
	@Resource
	BankCardService bankCardService;
	
	@Resource
	UserService userService;
	
	@Resource
	UserMoneyService userMoneyService;
	
	@Resource
	PaymentDefrayPayService paymentDefrayPayService;
	
	@Resource
	TrusteeshipQueryBusinessService trusteeshipQueryBusinessService;
	
	@Resource
	PayMentChannelService payMentChannelService;
	
	@Resource
	PaymentAccountService paymentAccountService;
	
	@Resource
	PlatformAccountService platformAccountService;
	
	@Resource
	Log log;

	/**
	 * 验证易宝是否冻结
	 */
	@Override
	public String verifyWithdrawCash() {
		//查询是否有两个相同流水号相同的数据
		List<PaymentWithdrawRecord> list = paymentWithdrawRecordService.readRepeatRecords();
		String orderIds = "";
		if (MyCollectionUtils.isNotBlank(list)) {
			for(PaymentWithdrawRecord record : list){
				orderIds += record.getMarkId()+",";
			}
			log.errLog("京东代付校验", "流水号"+orderIds+"重复，请联系技术人员",1);
			return "流水号"+orderIds+"重复，请联系技术人员";
		}
		/***************查询今天要代付的数据，易宝是否冻结成功*****************/
		PaymentWithdrawRecord record = new PaymentWithdrawRecord();
		record.setRequestTime(new Date());
		List<PaymentWithdrawRecord> records = paymentWithdrawRecordService.readWithdrawRecords(record);
		int i = 0;
		int j = 0;
		if (MyCollectionUtils.isNotBlank(records)) {
			Date date = new Date();
			for(PaymentWithdrawRecord temrecord : records){
				if(verifyData(temrecord,date)){
					i++;
					continue;
				}
				orderIds += temrecord.getMarkId()+",";
				j++;
			}
			if(j>0){
				log.errLog("京东代付校验","本次校验成功"+i+"条,失败"+j+"条。流水号"+orderIds+"校验失败",1);
			}
			return "验证完成，成功"+i+"条，失败"+j+"条。";
		}else{
			return "未找到相应数据";
		}
	}
	
	private boolean verifyData(PaymentWithdrawRecord record,Date date) {
			String respInfo = null;
			try {
				respInfo = trusteeshipQueryBusinessService.query(record.getMarkId());
				Document respXML = DocumentHelper.parseText(respInfo);
				Map<String, String> resultMap = Dom4jUtil.xmltoMap(respInfo);
				String code = resultMap.get("code");
				String records = resultMap.get("records");
				if ("1".equals(code) && StringUtils.isNotBlank(records)) {
					String amount = respXML.selectSingleNode(
							"/response/records/record/amount").getStringValue();
					String status = respXML.selectSingleNode(
							"/response/records/record/status").getStringValue();
					if("PREAUTH".equals(status)&&StringUtils.isNotBlank(amount)
							&&Double.parseDouble(amount)==record.getMoney()){
						return true;
					}
				}
				record.setResponseData(respInfo);
				record.setResponseTime(date);
				record.setStatus("refused");//易宝的状态
				paymentWithdrawRecordService.update(record);
			} catch (Exception e) {
				log.errLog("京东代付", "校验易宝数据异常，流水号"+record.getMarkId());
			}
			return false;
	}

	/**
	 * 确认提现，进行京东代付操作
	 * 如果orderId为空，即为批量操作，需要判断时间，查询今天之前的未处理的正常数据
	 * 如果orderId有值，即为单个操作，直接进行代付操作
	 */
	@Override
	public String confirmWithdrawCash(String orderId,String drepayType) {
		/********************验证信息**********************/
		PaymentWithdrawRecord record = new PaymentWithdrawRecord();
		Date date = new Date();
		if(StringUtil.isBlank(orderId)){
			//判断时间
			Integer i = withdrawCashDao.getHolidayDate(date,"holiday");
			if(i>0){
				return "节假日不支持批量确认";
			}
			Integer j = withdrawCashDao.getHolidayDate(date,"work");
			if(j==0&&(Week.SATURDAY==DateUtil.getWeek(date)||Week.SUNDAY==DateUtil.getWeek(date))){
				return "周末不支持批量确认";
			}
			//查询昨天的数据
			record.setRequestTime(date);
		}else{
			record.setId(orderId);
			//校验
		}
			//判断走京东还是走宝付代付
			if(drepayType.equals("JD")){
				if(!verifyMoney(record,drepayType)){
					return "金额不足";
				}
				/********************查询转账数据，进行代付**********************/
				List<PaymentWithdrawRecord> list = paymentWithdrawRecordService.readWithdrawRecords(record);
				if(StringUtils.isNotBlank(orderId)&&list.size()==1){
					record = list.get(0);
					if(!verifyData(record, date)){
						return "验证失败，易宝未冻结或 冻结金额不正确";
					}
				}
				
				if (MyCollectionUtils.isNotBlank(list)) {
					return createDefrayPay(list);
				}else{
					return "未找到相应数据";
				}
			}else{
				// 宝付
				if(!verifyMoney(record,drepayType)){
					return "金额不足";
				}
				/********************查询转账数据，进行代付**********************/
				List<PaymentWithdrawRecord> list = paymentWithdrawRecordService.readWithdrawRecords(record);
				if(StringUtils.isNotBlank(orderId)&&list.size()==1){
					record = list.get(0);
					if(!verifyData(record, date)){
						return "验证失败，易宝未冻结或 冻结金额不正确";
					}
				}
				if (MyCollectionUtils.isNotBlank(list)) {
					return createBaoFooDefrayPay(list);
				}else{
					return "未找到相应数据";
				}
			}
	}
	private Map<String, String> initBaofoo(PaymentWithdrawRecord paymentWithdrawRecord) { 
		Map<String, String> map = new HashMap<String, String>();
		User user = userService.read(paymentWithdrawRecord.getUserId());
		BankCard bankCard = bankCardService.read(paymentWithdrawRecord.getCardId());
		if(user==null||null==bankCard){
			log.errLog("宝付代付", "拼接宝付代付参数，查询数据异常：userId:"+paymentWithdrawRecord.getUserId()+"，银行卡id:"+paymentWithdrawRecord.getCardId());
			return null;
		}
		map.put("trans_count","1");
		DecimalFormat df = new DecimalFormat("##0.00");
		map.put("trans_totalMoney", df.format(paymentWithdrawRecord.getMoney()));
		map.put("trans_no",paymentWithdrawRecord.getMarkId());
		map.put("trans_money", df.format(paymentWithdrawRecord.getMoney()));
		map.put("to_acc_name",user.getRealname());
		map.put("to_acc_no",bankCard.getCardNo());
		map.put("to_bank_name", bankCard.getName());
		map.put("trans_card_id", user.getIdCard());
		map.put("trans_mobile", user.getMobileNumber());
		return map;
	}
	
	
	private String createBaoFooDefrayPay(List<PaymentWithdrawRecord> list) {
		int i = 0;
		int j = 0;
		Date date = new Date();
		String orderIds = "";
		for(PaymentWithdrawRecord temrecord : list){
			try {
				Map<String,String> paramMap = initBaofoo(temrecord);//创建请求业务数据
				if(null==paramMap){
					j++;
					orderIds += temrecord.getMarkId()+",";
					continue;
				}
				PaymentDefrayPay paymentDefrayPay = paymentDefrayPayService.get(temrecord.getMarkId());
				if(paymentDefrayPay==null){
					paymentDefrayPay = new PaymentDefrayPay();
					paymentDefrayPay.setRequestNo(temrecord.getMarkId());
					paymentDefrayPay.setPaymentNo(temrecord.getMarkId());
					paymentDefrayPay.setRequestData(paramMap.toString());
					paymentDefrayPay.setRequestUrl("https://public.baofoo.com/baofoo-fopay/pay/BF0040001.do");
					paymentDefrayPay.setRequestTime(date);
					paymentDefrayPay.setUserId(temrecord.getUserId());
					paymentDefrayPay.setStatus("sended");
					paymentDefrayPay.setMoney(temrecord.getMoney());
					paymentDefrayPay.setPaymentId("BaoFoo");
					paymentDefrayPayService.insert(paymentDefrayPay);
				}
				temrecord.setIsDefrayPay("sended");
				paymentWithdrawRecordService.update(temrecord);
				if(BaoFooDefrayPay(paramMap)){//宝付代付
					i++;
				}else{  
					j++; 
					orderIds += temrecord.getMarkId()+",";
				}
			} catch (Exception e    ) { 
				j++;
				orderIds += temrecord.getMarkId()+",";
			}
		}
		if(j>0){
			log.errLog("代付操作成功","操作成功，其中："+i+"条宝付代付处理中,等待支付结果"+j+"条失败，失败流水号"+orderIds,1);
		}
		String resulet = j>0?"":",失败流水号"+orderIds;
		return "操作成功，其中："+i+"条宝付代付处理中,等待支付结果。"+j+"条失败"+resulet;
	}

	/**
	 * 宝付代付
	 */
	private boolean BaoFooDefrayPay(Map<String, String> paramMap) {
		boolean flag = false;
		try {
//			RequestUtil demoUtil = new RequestUtil();
//			String responseText = "";
			TransContent transContent = new TransContent();

			List<TransReqBF0040001> trans_reqDatas = new ArrayList<TransReqBF0040001>();
			TransReqBF0040001 transReqData = new TransReqBF0040001();
			transReqData.setTransNo(paramMap.get("trans_no"));
			transReqData.setTransMoney(paramMap.get("trans_money"));
			transReqData.setToAccName(paramMap.get("to_acc_name"));
			transReqData.setToAccNo(paramMap.get("to_acc_no"));
			transReqData.setToBankName(paramMap.get("to_bank_name"));
			transReqData.setToProName("");
			transReqData.setToCityName("");
			transReqData.setToAccDept("");
			transReqData.setTransCardId(paramMap.get("trans_card_id"));
			transReqData.setTransMobile(paramMap.get("trans_mobile"));
			transReqData.setTransSummary("");
			trans_reqDatas.add(transReqData);
			transContent.setTransReqDatas(trans_reqDatas);
			//transContent.setTrans_reqDatas(trans_reqDatas);
			String bean2XmlString = JaxbXmlUtil.objToXml(transContent);
			
			System.out.println("报文：" + bean2XmlString);
			String keyStorePath =PaymentConstants.BaofooConfig.KEYSTOREPATH;
					//"d:\\m_pri.pfx";
			String keyStorePassword = PaymentConstants.BaofooConfig.KEYSTOREPASSWORD;//"123456";
			
			String pub_key =  PaymentConstants.BaofooConfig.PUB_KEY;//"d:\\baofoo_pub.cer";
			System.out.println(keyStorePassword+"***************"+keyStorePath+"*************"+pub_key);
			String origData = bean2XmlString;
			 origData =  new String(SecurityUtil.Base64Encode(origData));//Base64.encode(origData);
				String encryptData = RsaCodingUtil.encryptByPriPfxFile(origData,
						keyStorePath, keyStorePassword);
				System.out.println("----------->【私钥加密-结果】" + encryptData);
				// 发送请求
				String requestUrl = "https://public.baofoo.com/baofoo-fopay/pay/BF0040001.do";// http://paytest.baofoo.com/baofoo-fopay/pay/BF0040004.do
				String memberId = "1000549"; // 商户号  
				String terminalId = "32486"; // 终端号
				List<NameValuePair> param = new ArrayList<>();
				param.add(new BasicNameValuePair("member_id", memberId));
				param.add(new BasicNameValuePair("terminal_id", terminalId));
				param.add(new BasicNameValuePair("data_type", "xml"));
				param.add(new BasicNameValuePair("data_content", encryptData));
				param.add(new BasicNameValuePair("version", "4.0.0"));
				String response = DRHTTPClient.sendHTTPRequestPostToString(DRHTTPClient.createSSLClientDefault(),requestUrl, new Header[0], param);
				System.out.println("宝付请求返回结果：" + response);
				TransContent str2Obj = new TransContent();
				String reslut = response;
					 reslut = RsaCodingUtil.decryptByPubCerFile(reslut, pub_key);
					 reslut = SecurityUtil.Base64Decode(reslut);
					System.out.println(reslut);
					str2Obj = JaxbXmlUtil.xmlToObj(reslut, TransContent.class);
					//业务逻辑判断 paymentw  将 提现 记录状态 代付表的状态 修改成成功
					Map<String,String> map=Dom4jUtil.xmltoMap(reslut);
					String returnCode=str2Obj.getTransHead().getReturnCode();//响应码
					String returnMsg=str2Obj.getTransHead().getReturnMsg();//响应信息
//					String orderId=str2Obj.getTransReqDatas().get(0).getTransNo();
					String orderId=paramMap.get("trans_no");
					PaymentWithdrawRecord paymentWithdrawRecord=paymentWithdrawRecordService.getPaymentWithdrawRecord(orderId);
					PaymentDefrayPay paymentDefrayPay=paymentDefrayPayService.queryPaymentDefrayPayByMarkId(orderId);
					//判断请求接口是否成功
						//判断请求接口是否成功请求代付成功
						if(returnCode.equals("200")&&returnMsg.equals("代付交易成功")){
							paymentWithdrawRecord.setIsDefrayPay("success");
							paymentDefrayPay.setStatus("success");
							String respInfo = confirmYeepay(orderId);
							// 响应信息
							if(StringUtils.isNotBlank(respInfo)){
								Map<String, String> resultMap = Dom4jUtil.xmltoMap(respInfo);
								String code = resultMap.get("code");
								if (StringUtils.equals("1", code)) {
									paymentWithdrawRecord.setStatus("confirm");
								} else {
									paymentWithdrawRecord.setIsDefrayPay("fail");
								}
							}
							paymentWithdrawRecord.setConfirmResponseData(respInfo);
							paymentWithdrawRecord.setConfirmResponseTime(new Date());
							paymentDefrayPay.setMsg("交易成功");
							flag = true;
						}else if(returnCode.equals("0000")&&returnMsg.equals("代付请求交易成功")){
							//支付账户
							paymentAccountService.transferOut(
		  							PaymentEnum.Baofoo, paymentWithdrawRecord.getMoney(),
									BusinessEnum.withdraw_cash, "提现成功",
									orderId);
							paymentAccountService.transferOut(
									PaymentEnum.Baofoo, 1,
									BusinessEnum.fee, "提现手续费",
									orderId);
							platformAccountService.transferOut(1,
									BusinessEnum.fee, "提现手续费",
									orderId);
							platformAccountService.transferIn( paymentWithdrawRecord.getMoney(),
									BusinessEnum.withdraw_cash, "代付成功",
									orderId);
							//代付中 代付状态未知trans_orderid
							String transOrderid=str2Obj.getTransReqDatas().get(0).getTransOrderid();
							WithdrawCash withdrawCash=new WithdrawCash();
							withdrawCash.setFee(1.0);
							withdrawCash.setId(orderId);
							withdrawCash.setPaymentId("BaoFoo");
							paymentWithdrawRecord.setPaymentId("BaoFoo");
							paymentDefrayPay.setPaymentNo(transOrderid);
							paymentDefrayPay.setStatus(returnCode);
							paymentDefrayPay.setMsg("等待支付结果，处理中");
							withdrawCashDao.update(withdrawCash);
							flag = true;
						}else {
								//请求代付失败
								paymentDefrayPay.setStatus("fail");
								paymentWithdrawRecord.setIsDefrayPay("fail");
								paymentDefrayPay.setMsg(returnMsg);
								flag = false;
						}
					paymentDefrayPay.setResponseData(reslut);
					paymentDefrayPay.setResponseTime(new Date());
					paymentDefrayPayService.update(paymentDefrayPay);//修改代付表
					paymentWithdrawRecordService.update(paymentWithdrawRecord);//修改流水表
				} catch (Exception e) {                                                                                                
					log.errLog("宝付代付", e+"流水号:"+paramMap.get("trans_no"));
					e.printStackTrace();
				}
					return flag;
	}
	
	
	/**
	 * 宝付代付实时交易查询
	 * @param markId
	 * @return
	 */
	public  String queryPaymentDefrayPay(String markId) {
		ReapTransContent str2Obj = new ReapTransContent();
		try {
		TransContent transContent = new TransContent();
		List<TransReqBF0040002> trans_reqDatas = new ArrayList<TransReqBF0040002>();
		TransReqBF0040002 transReqData = new TransReqBF0040002();
		transReqData.setTransNo(markId);
		trans_reqDatas.add(transReqData);
		transContent.setTransReqBF0040002TransReqDatas(trans_reqDatas);
		String bean2XmlString = JaxbXmlUtil.objToXml(transContent);
		System.out.println(bean2XmlString);
		String requestUrl = "https://public.baofoo.com/baofoo-fopay/pay/BF0040002.do";
		String memberId = "1000549"; // 商户号
		String terminalId = "32486"; // 终端号
		String keyStorePath =PaymentConstants.BaofooConfig.KEYSTOREPATH;
		//"d:\\m_pri.pfx";
		String keyStorePassword = PaymentConstants.BaofooConfig.KEYSTOREPASSWORD;//"123456";
		String pub_key =  PaymentConstants.BaofooConfig.PUB_KEY;//"d:\\baofoo_pub.cer";
		String origData = bean2XmlString;
		origData =  new String(SecurityUtil.Base64Encode(origData));//Base64.encode(origData);
		String encryptData = RsaCodingUtil.encryptByPriPfxFile(origData,
		keyStorePath, keyStorePassword);
		System.out.println("----------->【私钥加密-结果】" + encryptData);
		List<NameValuePair> param = new ArrayList<>();
		param.add(new BasicNameValuePair("member_id", memberId));
		param.add(new BasicNameValuePair("terminal_id", terminalId));
		param.add(new BasicNameValuePair("data_type", "xml"));
		param.add(new BasicNameValuePair("data_content", encryptData));
		param.add(new BasicNameValuePair("version", "4.0.0"));
		String response = DRHTTPClient.sendHTTPRequestPostToString(DRHTTPClient.createSSLClientDefault(),requestUrl, new Header[0], param);
		System.out.println(response);
		response = RsaCodingUtil.decryptByPubCerFile(response, pub_key);
		response = SecurityUtil.Base64Decode(response);
		System.out.println(response);
		return response;
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	
	
	@Override
	public boolean callback(ServletRequest request, ServletResponse response)
			throws Exception {
		return false;
	}
 
	/**
	 * 京东代付服务器通知
	 * 通知时间：金额到账时
	 * 获取返回参数
	 * 查询京东代付表数据
	 * 解析验证签名后，通知京东接收成功
	 * 交易成功，确认易宝数据
	 * 交易失败，进行人工处理
	 */
	@Override
	public String S2SCallback(HttpServletRequest request,HttpServletResponse response,String operationType)throws Exception {
		//京东回调
		String orderId = request.getParameter("out_trade_no");
		if(StringUtils.isBlank(orderId)){
			log.errLog("代付服务器通知", "返回数据orderId为空");
			return null;
		}
		Map<String, String[]> params = request.getParameterMap();  
        String data = "";  
        for (String key : params.keySet()) {  
            String[] values = params.get(key);  
            for (int i = 0; i < values.length; i++) {  
                String value = values[i];  
                data += key + "=" + value + "&";  
            }  
        }  
        data = data.substring(0, data.length() - 1); 
		PaymentDefrayPay paymentDefrayPay = paymentDefrayPayService.get(orderId);
		PaymentWithdrawRecord record = paymentWithdrawRecordService.read(orderId, orderId, "JDpay");
		if(paymentDefrayPay==null||record==null){
			log.errLog("代付服务器通知", "未找到本地数据，流水号："+orderId);
			System.out.println("未找到数据"); 
			return null;
		}
		paymentDefrayPay.setResponseData(data.toString());
		RequestUtil demoUtil = new RequestUtil();
		try {
			// 响应的参数 为XML格式
			Map<String,String> map = demoUtil.verifySingNotify(data);
			record.setConfirmResponseTime(new Date());
			if(map==null){
				log.errLog("代付服务器通知", "验证签名不成功,返回数据:"+data);
				paymentDefrayPay.setStatus("fail");  
				paymentDefrayPay.setMsg("验证签名不成功");
			      }else{
				response.getWriter().write("SUCCESS");
				log.infoLog("京东代付", "通知京东接收成功"+orderId);
				String trade_status = map.get("trade_status");
				paymentDefrayPay.setMoney(Integer.parseInt(map.get("trade_amount")));
				if(CodeConst.TRADE_FINI.equals(trade_status)){//交易成功
					paymentDefrayPay.setStatus("success");
					record.setIsDefrayPay("success");
					String respInfo = confirmYeepay(map.get("out_trade_no"));
					record.setConfirmResponseData(respInfo);
					// 响应信息
					if(StringUtils.isNotBlank(respInfo)){
						Map<String, String> resultMap = Dom4jUtil.xmltoMap(respInfo);
						String code = resultMap.get("code");
						if (StringUtils.equals("1", code)) {
							record.setStatus("confirm");
						} else {
							record.setIsDefrayPay("fail");
							log.errLog("京东代付", "易宝确认交易失败,流水号："+orderId+"，易宝返回数据："+respInfo);
						}
					}				
					paymentDefrayPay.setMsg("交易成功");
				}else if(CodeConst.TRADE_CLOS.equals(trade_status)){
					paymentDefrayPay.setStatus("fail");
					paymentDefrayPay.setMsg("交易关闭，交易失败");
					log.errLog("京东代付", "京东代付交易失败,流水号："+orderId+"，京东返回数据："+data);
				}else if(CodeConst.TRADE_WPAR.equals(trade_status)||CodeConst.TRADE_BUID.equals(trade_status)||CodeConst.TRADE_ACSU.equals(trade_status)){
					paymentDefrayPay.setStatus(trade_status);
					paymentDefrayPay.setMsg("等待支付结果，处理中");
				}
			}
		} catch (UnsupportedEncodingException e) {
			log.errLog("京东代付", "服务器通知解析异常"+e);
		}
		paymentDefrayPay.setResponseTime(new Date());
		paymentDefrayPayService.update(paymentDefrayPay);
		paymentWithdrawRecordService.update(record);
		return "";
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

	@Override
	public Map<String,String> tradeQuery(String orderId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("customer_no",PaymentConstants.JDConfig.DEFRAYPAY_NO);//提交者会员号
		Date date = new Date();
		String dateTime = DateUtil.DateToString(date, "yyyyMMdd")+"T"+DateUtil.DateToString(date, "HHmmss");
		paramMap.put("request_datetime",dateTime);//请求时间
		paramMap.put("out_trade_no",orderId);//商户订单号
		RequestUtil demoUtil = new RequestUtil();
		String responseText = "";
		Map<String,String> map = new HashMap<>();
		try {
			responseText = demoUtil.tradeRequestSSL(paramMap,"https://mapi.jdpay.com/npp10/trade_query",null);
			map = demoUtil.verifySingReturnData(responseText);
			if(map==null){
				log.errLog("京东单笔业务查询", "验证签名失败"+"流水号："+orderId);
			}else{
				log.infoLog("京东单笔业务查询", "流水号："+orderId+","+map.toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}


	private String createDefrayPay(List<PaymentWithdrawRecord> list){
		int i = 0;
		int j = 0;
		Date date = new Date();
		String orderIds = "";
		for(PaymentWithdrawRecord temrecord : list){
			try {
				Map<String,String> paramMap = init(temrecord);//创建请求业务数据
				if(null==paramMap){
					j++;
					orderIds += temrecord.getMarkId()+",";
					continue;
				}
				PaymentDefrayPay paymentDefrayPay = paymentDefrayPayService.get(temrecord.getMarkId());
				if(paymentDefrayPay==null){
					paymentDefrayPay = new PaymentDefrayPay();
					paymentDefrayPay.setRequestNo(temrecord.getMarkId());
					paymentDefrayPay.setPaymentNo(temrecord.getMarkId());
					paymentDefrayPay.setRequestData(paramMap.toString());
					paymentDefrayPay.setRequestUrl("https://mapi.jdpay.com/npp10/defray_pay");
					paymentDefrayPay.setRequestTime(date);
					paymentDefrayPay.setUserId(temrecord.getUserId());
					paymentDefrayPay.setStatus("sended");
					paymentDefrayPayService.insert(paymentDefrayPay);
				}
				temrecord.setIsDefrayPay("sended");
				paymentWithdrawRecordService.update(temrecord);
				if(defrayPay(paramMap)){//京东代付
					i++;
				}else{
					j++;
					orderIds += temrecord.getMarkId()+",";
				}
			} catch (Exception e) {
				j++;
				orderIds += temrecord.getMarkId()+",";
			}
		}
		if(j>0){
			log.errLog("代付操作成功","操作成功，其中："+i+"条京东代付处理中,等待支付结果。"+j+"条失败，失败流水号"+orderIds,1);
		}
		String resulet = j>0?"":",失败流水号"+orderIds;
		return "操作成功，其中："+i+"条京东代付处理中,等待支付结果。"+j+"条失败"+resulet;
	}
	
	/**
	 * 拼接京东代付参数
	 * @param record 
	 * @return
	 */
	private  Map<String,String> init(PaymentWithdrawRecord record){
		//查询用户，银行卡信息，用户拼接参数
		Map<String, String> map = new HashMap<String, String>();
		User user = userService.read(record.getUserId());
		BankCard bankCard = bankCardService.read(record.getCardId());
		if(user==null||null==bankCard){
			log.errLog("京东代付", "拼接京东代付参数，查询数据异常：userId:"+record.getUserId()+"，银行卡id:"+record.getCardId());
			return null;
		}
		map.put("customer_no",PaymentConstants.JDConfig.DEFRAYPAY_NO);
		Date date = new Date();
		String dateTime = DateUtil.DateToString(date, "yyyyMMdd")+"T"+DateUtil.DateToString(date, "HHmmss");
		map.put("request_datetime",dateTime);
		map.put("out_trade_no",record.getMarkId());//外部交易号
		DecimalFormat df = new DecimalFormat("##0");
		map.put("trade_amount",df.format(record.getMoney()*100));
		map.put("trade_currency",PaymentConstants.Currency.CNY);
		map.put("trade_subject","代付");
		map.put("pay_tool","TRAN");
		map.put("payee_bank_code",bankCard.getBank());
		map.put("payee_card_type","DE");
		map.put("payee_account_type","P");
		map.put("payee_account_no",bankCard.getCardNo());
		map.put("payee_account_name",user.getRealname());
		map.put("payee_id_type","ID");
		map.put("payee_id_no",user.getIdCard());
		map.put("notify_url",PaymentConstants.ResponseS2SUrl + PaymentConstants.JDConfig.DEFRAYPAY);//商户处理数据的异步通知地址
		return map;
	}
	
	/**
	 * 检验提现金额和余额
	 * @param record
	 * @return
	 */
	private boolean verifyMoney(PaymentWithdrawRecord record,String drepayType){
		double withSum = paymentWithdrawRecordService.readWithdrawMoneyPerDay(record);
		double balance = 0;
		if(drepayType.equals("JD")){
			
			balance=getJDbalance();
		}else{
			balance=getBaoFoobalance();
		}
		return balance>withSum;
	}
	
	
	@Override
	public   double getBaoFoobalance() {
		try {
			Map<String,String> PostParams = new HashMap<String,String>();
			PostParams.put("member_id", "1000549");//	商户号
			PostParams.put("terminal_id", "32462");//	终端号1
			PostParams.put("return_type", "json");//	返回报文数据类型xml 或json
			PostParams.put("trans_code", "BF0001");//	交易码
			
			PostParams.put("version", "4.0");//版本号
			PostParams.put("account_type", "1");//帐户类型--0:全部、1:基本账户、2:未结算账户、3:冻结账户、4:保证金账户、5:资金托管账户；
			String MAK = "&";//分隔符		
			String KeyString = "wpmwr7dy7ymqledj";
			String Md5AddString = "member_id="+PostParams.get("member_id")+MAK+"terminal_id="+PostParams.get("terminal_id")+MAK+"return_type="+PostParams.get("return_type")+MAK+"trans_code="+PostParams.get("trans_code")+MAK+"version="+PostParams.get("version")+MAK+"account_type="+PostParams.get("account_type")+MAK+"key="+KeyString;
			loging("Md5拼接字串:"+Md5AddString);//商户在正式环境不要输出此项以免泄漏密钥，只在测试时输出以检查验签失败问题
			String Md5Sing = SecurityUtil.MD5(Md5AddString).toUpperCase();//必须为大写
			PostParams.put("sign", Md5Sing);
			String Re_Url = "https://public.baofoo.com/open-service/query/service.do";//正式请求地址// TODO Auto-generated method stub
			String RetrunString = HttpUtil.RequestForm(Re_Url, PostParams);	
			JSONObject json = JSONObject.fromObject(JSON.parse(RetrunString));
			 String code=(String) json.getJSONObject("trans_content").getJSONObject("trans_head").get("return_code");
			 String msg=(String) json.getJSONObject("trans_content").getJSONObject("trans_head").get("return_msg");
			if(code.equals("0000")&&msg.equals("请求交易成功")){
				System.out.println(json.getJSONObject("trans_content").getJSONObject("trans_reqDatas").getJSONObject("trans_reqData").get("balance"));
				double balance=Double.parseDouble(json.getJSONObject("trans_content").getJSONObject("trans_reqDatas").getJSONObject("trans_reqData").get("balance").toString());
				return  balance;
			}else{
				log.infoLog("宝付账户查询", "查询账户余额失败 描述："+msg);
				return 0;
			}
		} catch (NumberFormatException e) {
			log.errLog("宝付账户查询", "查询账户余额失败 描述："+e);
			return 0;
		}
	}
	
	
		
	public static void loging(String msg) {
		System.out.println(getTime() + "\t: " + msg);
	}    

	private static String getTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	@Override
	public double getJDbalance(){
		Map<String,String> paramMap = new HashMap<String, String>();
		Date date = new Date();
		String dateTime = DateUtil.DateToString(date, "yyyyMMdd")+"T"+DateUtil.DateToString(date, "HHmmss");
		paramMap.put("customer_no",PaymentConstants.JDConfig.DEFRAYPAY_NO);//提交者会员号（商户会员号）
		paramMap.put("request_datetime",dateTime);
		paramMap.put("out_trade_no","demodaifu1001");
		paramMap.put("buyer_info","{\"customer_code\":\""+PaymentConstants.JDConfig.DEFRAYPAY_NO+"\",\"customer_type\":\"CUSTOMER_NO\"}");//customer_code必须和上面的会员号一致
		paramMap.put("query_type","BUSINESS_BASIC");
		paramMap.put("ledger_type","00");
		RequestUtil demoUtil = new RequestUtil();
		String responseText = "";
		try {
			responseText = demoUtil.tradeRequestSSL(paramMap,"https://mapi.jdpay.com/npp10/account_query",null);
			Map<String,String> map = demoUtil.verifySingReturnData(responseText);
			if(map==null){
				log.errLog("京东代付", "查询账户余额，验证签名失败");
			}else{
				String response_code = map.get("response_code");
				if(CodeConst.SUCCESS.equals(response_code)){
					String account_amount = map.get("account_amount")==null?"":map.get("account_amount").toString();
					String frozen_amount = map.get("frozen_amount")==null?"":map.get("frozen_amount").toString();
					long accountAmount = Long.parseLong("".equals(account_amount)?"0":account_amount);
					long frozenAmount = Long.parseLong("".equals(frozen_amount)?"0":frozen_amount);
					double jdbalance = accountAmount-frozenAmount;
					return jdbalance/100;
				}else{
					log.errLog("京东代付", "查询账户余额失败 描述："+response_code+" "+map.get("response_message"));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0.0;
	}
	
	/**
	 * 请求京东代付接口
	 * @param paramMap
	 */
	private  boolean defrayPay(Map<String,String> paramMap){
		RequestUtil demoUtil = new RequestUtil();
		String responseText = "";
		boolean flag = false;
		try {
			responseText = demoUtil.tradeRequestSSL(paramMap,"https://mapi.jdpay.com/npp10/defray_pay",Contants.encryptType_RSA);
			Map<String,String> map = demoUtil.verifySingReturnData(responseText);
			String orderId = paramMap.get("out_trade_no");//外部交易号;
			PaymentDefrayPay paymentDefrayPay = paymentDefrayPayService.get(orderId);
			PaymentWithdrawRecord record = paymentWithdrawRecordService.read(orderId, orderId, "JDpay");
			if(record==null||paymentDefrayPay==null){
				log.errLog("京东代付", "未找到流水记录："+orderId);
				return flag;
			}
			if(map==null){
				paymentDefrayPay.setStatus("fail");
				paymentDefrayPay.setMsg("验证签名不成功");
			}else{
				String responseCode = map.get("response_code");
				if(CodeConst.SUCCESS.equals(responseCode)||"OUT_TRADE_NO_EXIST".equals(responseCode)){
					String trade_status = map.get("trade_status");
					paymentDefrayPay.setMoney(Integer.parseInt(map.get("trade_amount"))/100);
					if(CodeConst.TRADE_FINI.equals(trade_status)){//交易成功
						flag = true;
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
						paymentDefrayPay.setStatus("fail");
						record.setIsDefrayPay("fail");
						paymentDefrayPay.setMsg("交易关闭，交易失败");
					}else if(CodeConst.TRADE_WPAR.equals(trade_status)||CodeConst.TRADE_BUID.equals(trade_status)||CodeConst.TRADE_ACSU.equals(trade_status)){
						paymentAccountService.transferOut(
								PaymentEnum.JDpay, record.getMoney(),
								BusinessEnum.withdraw_cash, "提现成功",
								orderId);
						paymentAccountService.transferOut(
								PaymentEnum.JDpay, 1.5,
								BusinessEnum.fee, "提现手续费",
								orderId);
						platformAccountService.transferOut(1.5,
								BusinessEnum.fee, "提现手续费",
								orderId);
						platformAccountService.transferIn( record.getMoney(),
								BusinessEnum.withdraw_cash, "代付成功",
								orderId);
						WithdrawCash withdrawCash=new WithdrawCash();
						withdrawCash.setFee(1.5);
						withdrawCash.setId(orderId);
						withdrawCash.setPaymentId("JDpay");
						withdrawCashDao.update(withdrawCash);
						record.setPaymentId("JDpay");
						paymentDefrayPay.setStatus(trade_status);
						paymentDefrayPay.setMsg("等待支付结果，处理中");
						flag = true;
					}
				}else{
					if("ILLEGAL_ARGUMENT".equals(responseCode)){
						paymentDefrayPay.setStatus("fail");
						record.setIsDefrayPay("fail");
						paymentDefrayPay.setMsg("提交的参数有误");
					}else if("SYSTEM_ERROR".equals(responseCode)){
						paymentDefrayPay.setMsg("系统错误（非失败，不能按失败处理）");
					}
				}
				
			}
			paymentDefrayPay.setResponseTime(new Date());
			paymentDefrayPayService.update(paymentDefrayPay);//修改代付表
			paymentWithdrawRecordService.update(record);//修改流水表
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog("京东代付", "请求京东代付接口失败"+e);
		}
		return flag;
	}

	@Override
	public String transferCancle(String requestNo) throws Exception {
		String request = "error";
		//单笔业务查询
		PaymentDefrayPay paymentDefrayPay = paymentDefrayPayService.get(requestNo);
		String responseData="";
		if(paymentDefrayPay != null){
		if(StringUtils.isNotBlank(paymentDefrayPay.getPaymentId())&&paymentDefrayPay.getPaymentId().equals("BaoFoo")){
			ReapTransContent str2Obj = new ReapTransContent();
			String reapTransContent=queryPaymentDefrayPay(requestNo);
			paymentDefrayPay.setResponseData(reapTransContent);
			if(StringUtils.isNotBlank(reapTransContent)){
				str2Obj = JaxbXmlUtil.xmlToObj(reapTransContent, ReapTransContent.class);
			if(str2Obj.getTransHead().getReturnCode().equals("0000")&&str2Obj.getTransHead().getReturnMsg().equals("代付请求交易成功")){//如果response_code返回0000，表示请求逻辑正常，进一步判断订单状态
				String defrauPay=str2Obj.getTransReqDatas().get(0).getState();
				paymentDefrayPay.setStatus(defrauPay);
				if("1".equals(defrauPay)){
					log.errLog("宝付代付", "取消易宝金额"+requestNo+"宝付代付已成功，请勿取消");			
					paymentDefrayPay.setMsg("宝付代付已成功，请勿取消");
					request =  "代付已成功，不能退款!";
				}else if("-1".equals(defrauPay)||"2".equals(defrauPay)){
					request = paymentWithdrawRecordService.transferCancle(requestNo);
				}else if("0".equals(defrauPay)){
					log.errLog("宝付代付", "流水号："+requestNo+"等待支付结果，处理中，请勿取消");
					paymentDefrayPay.setMsg("宝付代付已成功，请勿取消");
					request =  "代付中，不能退款!";
				}
			}else {
				log.errLog("宝付代付", "查询宝付代付失败："+requestNo+"请勿取消");
				paymentDefrayPay.setMsg("查询宝付代付失败");
				request =  "代付查询失败，请重新操作！";
			}
			}else{
				log.errLog("宝付代付", "查询宝付代付失败："+requestNo+"请勿取消");
				paymentDefrayPay.setMsg("查询宝付代付失败");
				request =  "代付查询失败，请重新操作！";
			}
		}else{
			Map<String,String> map = tradeQuery(requestNo);
				paymentDefrayPay.setResponseTime(new Date());
				if(map!=null){
					String response_code = map.get("response_code");
					paymentDefrayPay.setResponseData(map.toString());
					if(CodeConst.SUCCESS.equals(response_code)){//如果response_code返回0000，表示请求逻辑正常，进一步判断订单状态
						String trade_status = map.get("trade_status");
						paymentDefrayPay.setStatus(trade_status);
						if(CodeConst.TRADE_FINI.equals(trade_status)){
							log.errLog("京东代付", "取消易宝金额"+requestNo+"京东代付已成功，请勿取消");			
							paymentDefrayPay.setMsg("京东代付已成功，请勿取消");
							request =  "代付已成功，不能退款!";
						}else if(CodeConst.TRADE_CLOS.equals(trade_status)){
							request = paymentWithdrawRecordService.transferCancle(requestNo);
						}else if(CodeConst.TRADE_WPAR.equals(trade_status)||CodeConst.TRADE_BUID.equals(trade_status)||CodeConst.TRADE_ACSU.equals(trade_status)){
							log.errLog("京东代付", "流水号："+requestNo+"等待支付结果，处理中，请勿取消");
							paymentDefrayPay.setMsg("京东代付已成功，请勿取消");
							request =  "代付中，不能退款!";
						}
					}else if("NO_RESULT".equals(response_code)){
						request =  paymentWithdrawRecordService.transferCancle(requestNo);
					}
				}else{
					log.errLog("京东代付", "查询京东代付失败："+requestNo+"请勿取消");
					paymentDefrayPay.setMsg("查询京东代付失败");
					request =  "代付查询失败失败，请重新操作！";
				}
		}
	}else{
		request =  paymentWithdrawRecordService.transferCancle(requestNo);
	}
		
		paymentDefrayPay.setResponseData(responseData);
		paymentDefrayPayService.update(paymentDefrayPay);	
		return request;
	}

	@Override
	public String transferConfirm(String requestNo) throws Exception {
		String request = "error";
		PaymentDefrayPay paymentDefrayPay = paymentDefrayPayService.get(requestNo);
		if(paymentDefrayPay != null){
			if( StringUtils.isNotBlank(paymentDefrayPay.getPaymentId())&&paymentDefrayPay.getPaymentId().equals("BaoFoo")){
				//宝付确认
				ReapTransContent str2Obj = new ReapTransContent();
					String reapTransContent=queryPaymentDefrayPay(requestNo);
					if(StringUtils.isNotBlank(reapTransContent)){	
					str2Obj = JaxbXmlUtil.xmlToObj(reapTransContent, ReapTransContent.class);
					paymentDefrayPay.setResponseData(reapTransContent);
					String defrauPay=str2Obj.getTransReqDatas().get(0).getState();
					paymentDefrayPay.setResponseData(reapTransContent.toString());
					if(str2Obj.getTransHead().getReturnCode().equals("0000")&&str2Obj.getTransHead().getReturnMsg().equals("代付请求交易成功")){
						paymentDefrayPay.setStatus(str2Obj.getTransHead().getReturnMsg());
						if("1".equals(defrauPay)){
							paymentDefrayPay.setMsg("宝付代付已成功");
							request = paymentWithdrawRecordService.transferConfirm(requestNo);
						}else if("-1".equals(defrauPay)||"2".equals(defrauPay)){
							paymentDefrayPay.setMsg("交易失败");
							log.errLog("宝付代付", "流水号："+requestNo+"交易关闭，交易失败");
							request =  "代付失败";
						}else if("0".equals(defrauPay)){
							paymentDefrayPay.setMsg("等待支付结果，处理中，请勿取消");
							log.errLog("宝付代付", "流水号："+requestNo+"等待支付结果，处理中，请勿取消");
							request =  "代付中，不能确认!";
						}
					}else {
						request =  "代付账单不存在!";
					}
			}else{
				log.errLog("宝付代付", "查询宝付代付失败："+requestNo+"请勿取消");
				paymentDefrayPay.setMsg("查询宝付代付失败");
				request =  "代付查询失败，请重新操作！";
			}
			}else{
				//京东确认
				Map<String,String> map = tradeQuery(requestNo);	
				if(map!=null){
					String response_code = map.get("response_code");	
					paymentDefrayPay.setResponseData(map.toString());
					if(CodeConst.SUCCESS.equals(response_code)){//如果response_code返回0000，表示请求逻辑正常，进一步判断订单状态
						String trade_status = map.get("trade_status");
						paymentDefrayPay.setStatus(trade_status);
						if(CodeConst.TRADE_FINI.equals(trade_status)){
							paymentDefrayPay.setMsg("京东代付已成功");
							request = paymentWithdrawRecordService.transferConfirm(requestNo);
						}else if(CodeConst.TRADE_CLOS.equals(trade_status)){
							paymentDefrayPay.setMsg("交易关闭，交易失败");
							log.errLog("京东代付", "流水号："+requestNo+"交易关闭，交易失败");
							request =  "代付失败，不能确认！";
						}else if(CodeConst.TRADE_WPAR.equals(trade_status)||CodeConst.TRADE_BUID.equals(trade_status)||CodeConst.TRADE_ACSU.equals(trade_status)){
							paymentDefrayPay.setMsg("等待支付结果，处理中，请勿取消");
							log.errLog("京东代付", "流水号："+requestNo+"等待支付结果，处理中，请勿取消");
							request =  "代付中，不能确认!";
						}else{
							request =  map.get("response_message");
						}
					}else if("NO_RESULT".equals(response_code)){
						log.errLog("京东代付", "流水号："+requestNo+"等待支付结果，处理中，请勿取消");
						request =  "代付账单不存在!";
					}
				}else{
					paymentDefrayPay.setMsg("查询京东代付失败，请勿取消");
					log.errLog("京东代付", "查询京东代付失败："+requestNo+"请勿取消");
					request =  "代付查询失败失败，请重新操作！";
				}
			}
		}else{
			request =  "代付记录不存在！";
		}
		paymentDefrayPayService.update(paymentDefrayPay);
		return request;	
	}

	@Override
	public Map<String, String> tradeFuiouQuery(String orderId) {
			StringBuffer buf = new StringBuffer();
			String MchntCd="0001000F0305906";
			String sign=MD5.MD5Encode(MchntCd+"|"+orderId+"|miub2yxlb0vkphrbqji9s9iqi2s7o8sh");
			buf.append("<FM>");
			buf.append("<MchntCd>"+MchntCd+"</MchntCd>");
			buf.append("<OrderId>"+orderId+"</OrderId>");
			buf.append("<Sign>"+sign+"</Sign>");
			buf.append("</FM>");
			List<NameValuePair>postMethod=new ArrayList<>();
			postMethod.add(new BasicNameValuePair("FM", buf.toString()));
			String	responseBody=null;
			Map<String, String> resultMap=null;
			
			try {
				responseBody = DRHTTPSClient.sendHTTPRequestPostToString("https://mpay.fuiou.com:16128/findPay/queryOrderId.pay", new BasicHeader[0], postMethod);
				if(responseBody != null && responseBody.trim().length()>0){		
					 resultMap = Dom4jUtil.xmltoMap(responseBody);		
					 String Rcd=resultMap.get("Rcd");
					 String RDesc=resultMap.get("RDesc");
					 /*String sign=resultMap.get("Sign");*/
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return resultMap;
		}

	@Override
	public String BaoFooS2SCallback(HttpServletRequest request,
			HttpServletResponse response, String operationType) {
		try {
			String responseContent=request.getParameter("data_content");
			System.out.println("宝付请求返回结果：" + responseContent);
			log.infoLog("宝付代付服务器通知", "通知宝付接收成功"+responseContent);
			ReapTransContentCallBack str2Obj = new ReapTransContentCallBack();
			String reslut = responseContent;
			String pub_key =  PaymentConstants.BaofooConfig.PUB_KEY;
				 reslut = RsaCodingUtil.decryptByPubCerFile(reslut, pub_key);
				 reslut = SecurityUtil.Base64Decode(reslut);
				System.out.println(reslut);
				str2Obj = JaxbXmlUtil.xmlToObj(reslut, ReapTransContentCallBack.class);
				//业务逻辑判断 paymentw  将 提现 记录状态 代付表的状态 修改成成功
				String returnCode=str2Obj.getTransReqDatas().get(0).getState();//响应码
				String orderId=str2Obj.getTransReqDatas().get(0).getTrans_no();
				PaymentWithdrawRecord paymentWithdrawRecord=paymentWithdrawRecordService.getPaymentWithdrawRecord(orderId);
				PaymentDefrayPay paymentDefrayPay=paymentDefrayPayService.queryPaymentDefrayPayByMarkId(orderId);
				//判断请求接口是否成功
				if(returnCode.equals("1")){
					//给宝付响应成功
					response.getWriter().write("OK");
					//代付成功
					paymentWithdrawRecord.setIsDefrayPay("success");
					paymentDefrayPay.setStatus("success");
					String respInfo = confirmYeepay(orderId);
					// 响应信息
					if(StringUtils.isNotBlank(respInfo)){
						Map<String, String> resultMap = Dom4jUtil.xmltoMap(respInfo);
						String code = resultMap.get("code");
						if (StringUtils.equals("1", code)) {
							paymentWithdrawRecord.setStatus("confirm");
						} else {
							paymentWithdrawRecord.setIsDefrayPay("fail");
						}
					}
					paymentWithdrawRecord.setConfirmResponseData(respInfo);
					paymentWithdrawRecord.setConfirmResponseTime(new Date());
					paymentDefrayPay.setMsg("交易成功");
				}else {
					paymentDefrayPay.setStatus("fail");
					paymentWithdrawRecord.setIsDefrayPay("fail");
					paymentDefrayPay.setMsg("交易失败");
				}
				paymentDefrayPay.setResponseTime(new Date());
				paymentDefrayPay.setResponseData(reslut);
				paymentDefrayPayService.update(paymentDefrayPay);//修改代付表
				paymentWithdrawRecordService.update(paymentWithdrawRecord);//修改流水表
		} catch (Exception e) {
			log.errLog("宝付代付服务器通知",e);
			e.printStackTrace();
		}
		return "";
	}

	
	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("##0.00");
	System.out.println(df.format(100.2262));
	}
	
	 
}
