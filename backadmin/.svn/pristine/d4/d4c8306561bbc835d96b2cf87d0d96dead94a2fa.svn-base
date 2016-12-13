package com.duanrong.payment.jd.defraypay;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
/**
 * 数据加密demo,此demo仅供参考
 * @author liuhuawei1
 *
 */
public class EnctyptDemo {

	
	public static void main(String[] args){
//		accountQuery();
		defrayPay();
//		tradeQuery();
		//verifySingNotify();
	}
	/**
	 * 代付交易demo
	 */
	private static void defrayPay(){
		Map<String,String> paramMap = init();//创建请求业务数据
		RequestUtil demoUtil = new RequestUtil();
		String responseText = "";
		try {
			//请求
			responseText = demoUtil.tradeRequestSSL(paramMap,"https://mapi.jdpay.com/npp10/defray_pay",Contants.encryptType_RSA);
			
			//验证数据
			Map<String,String> map = demoUtil.verifySingReturnData(responseText);
			System.out.println(map);
			if(map==null){
				System.out.println("验证签名不成功");
			}else{
				rescode(map,false);//处理返回数据
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 交易查询demo
	 */
	private static void tradeQuery(){
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("customer_no","360080002191800017");//提交者会员号
		paramMap.put("request_datetime","20150519T103700");//请求时间
		paramMap.put("out_trade_no","23456587692222");//商户订单号
//		paramMap.put("trade_no","");//代付交易号
//		paramMap.put("trade_type", "T_AGD");
		RequestUtil demoUtil = new RequestUtil();
		String responseText = "";
		try {
			//请求
			responseText = demoUtil.tradeRequestSSL(paramMap,"https://mapi.jdpay.com/npp10/trade_query",null);
			
			//验证数据
			Map<String,String> map = demoUtil.verifySingReturnData(responseText);
			System.out.println(map);
			if(map==null){
				System.out.println("验证签名不成功");
			}else{
				rescode(map,true);//处理返回数据
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//商户余额查询
	private static void accountQuery(){
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("customer_no","360080002191800017");//提交者会员号（商户会员号）
		paramMap.put("request_datetime","20151214T141800");
		paramMap.put("out_trade_no",UUID.randomUUID().toString());
		paramMap.put("out_trade_date","20151214T141800");
		paramMap.put("buyer_info","{\"customer_code\":\"360080002191800017\",\"customer_type\":\"CUSTOMER_NO\"}");//customer_code必须和上面的会员号一致
		paramMap.put("query_type","BUSINESS_BASIC");
		paramMap.put("ledger_type","00");
		RequestUtil demoUtil = new RequestUtil();
		String responseText = "";
		try {
			//请求
			responseText = demoUtil.tradeRequestSSL(paramMap,"https://mapi.jdpay.com/npp10/account_query",null);
			
			//验证数据
			Map<String,String> map = demoUtil.verifySingReturnData(responseText);
			System.out.println(map);
			if(map==null){
				System.out.println("验证签名不成功");
			}else{
				String response_code = map.get("response_code");
				if(CodeConst.SUCCESS.equals(response_code)){
					String account_amount = map.get("account_amount")==null?"":map.get("account_amount").toString();
					String frozen_amount = map.get("frozen_amount")==null?"":map.get("frozen_amount").toString();
					long accountAmount = Long.parseLong("".equals(account_amount)?"0":account_amount);
					long frozenAmount = Long.parseLong("".equals(frozen_amount)?"0":frozen_amount);
					System.out.println("可用余额="+(accountAmount-frozenAmount)+"（分）");
				}else{
					System.out.println("查询失败 描述："+response_code+" "+map.get("response_message"));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * http url 回调通知处理数据demo
	 * (用户需自行开发一个http url的通知地址，代付交易终态后会网银根据代付交易参数里notify_url的地址进行http请求将结果通知给用户，方法内data字符串是通知的数据字符串)
	 */
	private static void verifySingNotify(){
		//接收到的数据样例（代付处理完毕后通过http回调通知商户，商户从HttpRequest中获取此数据）
		//String data = "sign_type=SHA&sign_data=24CFBF93C3E85E773CA2A87BB78D5314E7EE0A27&trade_no=20150605100042000000001046&merchant_no=22318136&notify_datetime=20155705T112406369&biz_trade_no=1433476626534&customer_no=360080002212160011&out_trade_no=1433476626534&trade_class=DEFY&trade_status=CLOS&is_success=Y&card_type=DE&buyer_info={\"customerNo\":\"360080002212150012\"}&trade_subject=tixian&seller_info={\"customerNo\":\"360080002212160011\"}&trade_amount=1&category_code=20JR0131&trade_currency=CNY";
		String data = "sign_type=SHA-256&sign_data=2E8F3F09A87A7248A0774530662CD9EC0DD0B44354A2E924DA89932E4F68871E&biz_trade_no=2465481443061304785&out_trade_no=2465481443061304785&customer_no=360080000230629280&request_datetime=20150924T114003";
		RequestUtil demoUtil = new RequestUtil();
		try {
			Map<String,String> map = demoUtil.verifySingNotify(data);
			System.out.println(map);
			if(map==null){
				System.out.println("验证签名不成功");
			}else{
				tradeCode(map);//处理返回数据
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 代付返回结果判断逻辑demo  
	 * @param map 返回数据
	 * @param isQuery 是否查询接口返回结果
	 */
	private static void rescode(Map<String,String> map,boolean isQuery){
		String response_code = map.get("response_code");
		if(CodeConst.SUCCESS.equals(response_code)){//如果response_code返回0000，表示请求逻辑正常，进一步判断订单状态
			tradeCode(map);
		}else if(isQuery){//isQuery如果是true，非0000的返回编码表示查询异常，不能按失败处理
			System.out.println("查询异常，建议不做数据处理");//isQuery如果未true，非0000的返回编码表示查询异常，不能按失败处理
		}else if(!CodeConst.isContainCode(response_code)){//返回编码不包含在配置中的
			if(map.get("trade_status")==null || StringUtils.isEmpty(map.get("trade_status"))){
				System.out.println("返回编码不包含在配置中的,未知");
				//TODO 返回编码不包含在配置中的,未知处理
			}else{//如果有trade_status，按trade_status状态判断
				tradeCode(map);
			}
			
		}else if(CodeConst.SYSTEM_ERROR.equals(response_code)||CodeConst.RETURN_PARAM_NULL.equals(response_code)){
			System.out.println("未知");//需查询交易获取结果或等待通知结果
			//TODO 未知业务逻辑或查询交易结果处理
		}else if(CodeConst.OUT_TRADE_NO_EXIST.equals(response_code)){
			System.out.println("外部交易号已经存在");
			//TODO 需查询交易获取结果或等待通知结果
		}else{
			System.out.println("失败");
			//TODO 失败处理逻辑
		}
	}
	/**
	 * 交易结果处理demo
	 * @param map
	 */
	private static void tradeCode(Map<String,String> map){
		String trade_status = map.get("trade_status");
		if(CodeConst.TRADE_FINI.equals(trade_status)){
			System.out.println("交易成功");
			//TODO 成功后业务逻辑
		}else if(CodeConst.TRADE_CLOS.equals(trade_status)){
			System.out.println("交易关闭，交易失败");
			//TODO 失败后业务逻辑
		}else if(CodeConst.TRADE_WPAR.equals(trade_status)||CodeConst.TRADE_BUID.equals(trade_status)||CodeConst.TRADE_ACSU.equals(trade_status)){
			System.out.println("等待支付结果，处理中");//需查询交易获取结果或等待通知结果
			//TODO 处理中业务逻辑
		}
	}
	
	
	private static Map<String,String> init(){
		 Map<String, String> map = new HashMap<String, String>();
		 
		 map.put("customer_no","360080002191800017");
		 map.put("request_datetime","20160704T183700");
		 map.put("out_trade_no","demodaifu005");//外部交易号
		 map.put("trade_amount","1");
		 map.put("trade_currency","CNY");
		 map.put("trade_subject","test代付");
		 map.put("pay_tool","TRAN");
		 map.put("payee_bank_code","CMB");
		 map.put("payee_card_type","DE");
		 map.put("payee_account_type","P");
		 map.put("payee_account_no","6225880141603689");
		 map.put("payee_account_name","赵磊");
		 map.put("payee_id_type","ID");
		 map.put("payee_id_no","131121198810054419");
		 map.put("notify_url","http://xxx/");//商户处理数据的异步通知地址
		 
//		 map.put("payee_bank_code","ABC");
//		 map.put("customer_no","360080002191800017");
//		 map.put("extend_params","{\"ssss\":\"ssss\"}");
//		 map.put("payee_account_type","P");
//		 map.put("return_params","1234ssddffgghhj");
//		 map.put("trade_currency","CNY");
//		 map.put("pay_tool","TRAN");
//		 map.put("category_code","20jd222");
//		 map.put("payee_account_no","6222600210011817312");
//		 map.put("payee_account_name","张米克");
//		 map.put("trade_source","testetst");
//		 map.put("notify_url","http://xxx/");//商户处理数据的异步通知地址
//		 map.put("biz_trade_no","2015003456");
//		 map.put("out_trade_no","23456587692");//外部交易号
//		 map.put("seller_info","{\"customer_code\":\"360080002191800017\",\"customer_type\":\"CUSTOMER_NO\"}");
//		 map.put("out_trade_date","20150519T103700");
//		 map.put("trade_amount","1");

		 map.put("payee_bank_fullname","农业银行");
		 map.put("sign_type","SHA-256");
		 map.put("request_datetime","20150519T103700");
		 map.put("trade_subject","test代付");
		 map.put("payee_card_type","DE");
		 map.put("payee_mobile","1333333333");
//		 map.put("payee_bank_code","CMB");
//		 map.put("customer_no","360080004000838246");
//		 map.put("extend_params","{\"ssss\":\"ssss\"}");
//		 map.put("payee_account_type","P");
//		 map.put("return_params","1234ssddffgghhj");
//		 map.put("trade_currency","CNY");
//		 map.put("pay_tool","TRAN");
//		 map.put("category_code","20jd222");
//		 map.put("payee_account_no","6225880141603689");
//		 map.put("payee_account_name","张米克");
//		 map.put("trade_source","testetst");
//		 map.put("notify_url","http://xxx/");//商户处理数据的异步通知地址
//		 map.put("biz_trade_no","2016003456");
//		 map.put("out_trade_no","demodaifu003");//外部交易号
//		 map.put("seller_info","{\"customer_code\":\"360080004000838246\",\"customer_type\":\"CUSTOMER_NO\"}");
//		 map.put("out_trade_date","20160519T103700");
//		 map.put("trade_amount","1");
//		 map.put("payee_bank_fullname","招商银行");
//		 map.put("sign_type","SHA-256");
//		 map.put("request_datetime","20160704T183700");
//		 map.put("trade_subject","test代付");
//		 map.put("payee_card_type","DE");
//		 map.put("payee_mobile","15101074698");
		 return map;
	}

}
