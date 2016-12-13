package com.duanrong.payment.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import util.Log;
import util.MyCollectionUtils;

import com.duanrong.business.paymentInstitution.model.PaymentAdvancefund;
import com.duanrong.business.paymentInstitution.model.PaymentRechargeRecord;
import com.duanrong.business.paymentInstitution.service.PaymentCompanyService;
import com.duanrong.business.recharge.service.RechargeService;
import com.duanrong.business.user.service.RoleService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.payment.PaymentConstants;
import com.duanrong.payment.baofoo.util.HttpUtil;
import com.duanrong.payment.baofoo.util.JXMConvertUtil;
import com.duanrong.payment.baofoo.util.MapToXMLString;
import com.duanrong.payment.baofoo.util.RsaCodingUtil;
import com.duanrong.payment.baofoo.util.SecurityUtil;
import com.duanrong.payment.fuiou.service.FuiouService;
import com.duanrong.payment.jd.model.QueryResultTradeEntity;
import com.duanrong.payment.jd.utils.PaymentIDGenerator;
import com.duanrong.payment.service.JDRechargeService;
import com.duanrong.payment.service.PaymentService;
import com.duanrong.util.ArithUtil;
import com.duanrong.yeepay.service.TrusteeshipPlatformTransferService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Resource
	PaymentCompanyService paymentCompanyService;
	
	@Resource
	UserService userService;
	
	@Resource
	RoleService roleService;
	
	@Resource
	JDRechargeService jdRechargeService;
	
	@Resource
	RechargeService rechargeService;
	
	@Resource
	TrusteeshipPlatformTransferService platformTransferService;
	
	@Resource
	FuiouService fuiouService;
	
	@Resource
	Log log;
	

	@Override
	public String supplementOrder(String orderId,String transferWay){
		String msg = "";
		try {

			List<PaymentRechargeRecord> records = paymentCompanyService.query(orderId, orderId);
			if (MyCollectionUtils.isNotBlank(records)) {
				PaymentRechargeRecord record = records.get(0);
				if(PaymentConstants.Status.PASSED.equals(record.getStatus())&&PaymentConstants.Status.PASSED.equals(record.getIsYeepayTransfer())){
					log.infoLog("第三方支付", "流水表状态已成功，流水号："+orderId);
					msg = "流水表状态已成功,无需再次操作";
					return msg;
				}
				if(PaymentConstants.Channel.JDPAY.equals(record.getPayMentId())){
					// JD充值
					//进行一次单笔业务查询
					QueryResultTradeEntity result = jdRechargeService.queryOrderInfo(orderId,transferWay);
					if("0".equals(result.getTradeStatus())){
						//修改阀值
						PaymentAdvancefund advancefund= paymentCompanyService.readPaymentAdvancefund();
						log.infoLog("第三方支付", "修改阀值，流水号："+orderId+",原有金额："+advancefund.getMoney()+",充值金额："+record.getMoney());
						double money = ArithUtil.round(advancefund.getMoney()-record.getMoney(),2);
						//发送邮件
						if(money<advancefund.getWarnMoney()){
							log.errLog("垫付资金不足提醒", "剩余金额："+money+"，告警金额："+advancefund.getWarnMoney(),1);
							msg = "垫付资金不足";
							return msg;
						}
						paymentCompanyService.updateAdvancefundMoney(money);
						//然后调用易宝转账接口

						record.setStatus(PaymentConstants.Status.PASSED);
						record.setResponseData(result.toString());
						record.setResponseTime(new Date());
						record.setCardNo(result.getCardNo());
						record.setCradType(result.getCardType());
						record.setBankCode(result.getBankCode());
						boolean f = platformTransferService.supplementOrder(record);
						if(f){
							record.setIsYeepayTransfer(PaymentConstants.Status.PASSED);
							msg = "补单成功";
						}else{
							record.setIsYeepayTransfer(PaymentConstants.Status.FAIL);
							msg = "划款失败";
						}
						
					}else{
						log.errLog("JD支付", "单笔业务查询失败，流水号："+orderId+",result:"+result.toString());
//						record.setResponseData(result.toString());
//						record.setResponseTime(new Date());
//						record.setStatus(PaymentConstants.Status.REFUSED);
						msg = "补单失败，查询京东状态为失败";
						
					}
				}else if(PaymentConstants.Channel.FUIOU.equals(record.getPayMentId())){
					// JD充值
					//进行一次单笔业务查询
					//TODO 获取第三方流水ID
					Map<String,String> result = fuiouService.queryOrderInfoByOrderId(orderId);
					if(result != null){
						//修改阀值
						PaymentAdvancefund advancefund= paymentCompanyService.readPaymentAdvancefund();
						log.infoLog("第三方支付", "修改阀值，流水号："+orderId+",原有金额："+advancefund.getMoney()+",充值金额："+record.getMoney());
						double money = ArithUtil.round(advancefund.getMoney()-record.getMoney(),2);
						//发送邮件
						if(money<advancefund.getWarnMoney()){
							log.errLog("垫付资金不足提醒", "剩余金额："+money+"，告警金额："+advancefund.getWarnMoney(),1);
							msg = "垫付资金不足";
							return msg;
						}
						paymentCompanyService.updateAdvancefundMoney(money);
						//然后调用易宝转账接口

						record.setStatus(PaymentConstants.Status.PASSED);
						record.setResponseData(result.toString());
						record.setResponseTime(new Date());
						boolean f = platformTransferService.supplementOrder(record);
						if(f){
							record.setIsYeepayTransfer(PaymentConstants.Status.PASSED);
							msg = "补单成功";
						}else{
							record.setIsYeepayTransfer(PaymentConstants.Status.FAIL);
							msg = "划款失败";
						}
						
					
					}else{
//						record.setResponseData(result.toString());
//						record.setResponseTime(new Date());
//						record.setStatus(PaymentConstants.Status.REFUSED);
						msg = "补单失败，查询富友状态为失败";
						
					}
				}else if("Baofoo".equals(record.getPayMentId())){
					// JD充值
					//进行一次单笔业务查询
					//TODO 获取第三方流水ID
					try {
						Map<String, Object> result = BaofooApi(record.getMarkId(), "06");
						//订单查询
						if(result.get("resp_code").equals("0000")&&result.get("resp_msg").equals("交易成功")){
							//订单查询 支付成功
							//修改阀值
							PaymentAdvancefund advancefund= paymentCompanyService.readPaymentAdvancefund();
							log.infoLog("第三方支付", "修改阀值，流水号："+orderId+",原有金额："+advancefund.getMoney()+",充值金额："+record.getMoney());
							double money = ArithUtil.round(advancefund.getMoney()-record.getMoney(),2);
							//发送邮件
							if(money<advancefund.getWarnMoney()){
								log.errLog("垫付资金不足提醒", "剩余金额："+money+"，告警金额："+advancefund.getWarnMoney(),1);
								msg = "垫付资金不足";
								return msg;
							}
							paymentCompanyService.updateAdvancefundMoney(money);
							//然后调用易宝转账接口

							record.setStatus(PaymentConstants.Status.PASSED);
							record.setResponseData(result.toString());
							record.setResponseTime(new Date());
							boolean f = platformTransferService.supplementOrder(record);
							if(f){
								record.setIsYeepayTransfer(PaymentConstants.Status.PASSED);
								msg = "补单成功";
							}else{
								record.setIsYeepayTransfer(PaymentConstants.Status.FAIL);
								msg = "划款失败";
							}
						}else{
//							record.setResponseData(result.toString());
//							record.setResponseTime(new Date());
//							record.setStatus(PaymentConstants.Status.REFUSED);
							msg = "补单失败，查询富友状态为失败";
							
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				paymentCompanyService.updatePaymentRechargeRecord(record);
				return msg;
			}else{
				log.errLog("第三方支付", "未找到流水记录，流水号："+orderId);
				msg = "补单失败，未找到流水号";
			}
		} catch (Exception e) {
			log.errLog("第三方支付", "transferWay"+transferWay+",Exception"+e);
			e.printStackTrace();
			msg = "异常";
		}	
		return msg;
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
        XMLArray.put("trans_serial_no",PaymentIDGenerator.generateId("BF"));//流水号
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
//		log("加密前数据："+XMLArray.toString());
		String base64str = SecurityUtil.Base64Encode(XmlOrJson);
		String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str,pfxpath,PaymentConstants.BaofooConfig.PFX);	

		HeadPostParam.put("data_content", data_content);
//		log("请求参数："+HeadPostParam.toString());
		String PostString  = HttpUtil.RequestForm(request_url, HeadPostParam);	
//		log("请求返回："+ PostString);
		
		PostString = RsaCodingUtil.decryptByPubCerFile(PostString,cerpath);
		
		if(PostString.isEmpty()){//判断解密是否正确。如果为空则宝付公钥不正确
//			log("=====检查解密公钥是否正确！");
			return map;
		}
		
		PostString = SecurityUtil.Base64Decode(PostString);		 
//	 	log("=====返回查询数据解密结果:"+PostString);
	
		 if(HeadPostParam.get("data_type").equals("xml")){
				PostString = JXMConvertUtil.XmlConvertJson(PostString);		    
//			log("=====返回结果转JSON:"+PostString);
		}
		//将JSON转化为Map对象。
		 map = JXMConvertUtil.JsonConvertHashMap(PostString);//将JSON转化为Map对象。
		
		return map;
	}
	
}
