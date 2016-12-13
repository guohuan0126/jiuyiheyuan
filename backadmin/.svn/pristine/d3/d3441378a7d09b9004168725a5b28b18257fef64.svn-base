package com.duanrong.payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.duanrong.util.LoadConstantProterties;



public class PaymentConstants {
	
	
	public static Properties p = null;
	
	static {
		List<String> list = new ArrayList<String>();
		list.add("PaymentConstants.properties");
		HashMap<String,Properties> loadConstantsPro = LoadConstantProterties.loadConstantsPro(list);
		p = loadConstantsPro.get("PaymentConstants.properties");
	}
	
	//状态
	public static final class Channel {
		public static final String YEEPAY = p.getProperty("YEEPAY");//发送中
		public static final String JDPAY = p.getProperty("JDPAY");//未执行
		public static final String FUIOU = p.getProperty("FUIOU");//失败
	}
	//状态
	public static final class Status {
		public static final String SENDED = p.getProperty("SENDED");//发送中
		public static final String PASSED = p.getProperty("PASSED");//通过
		public static final String REFUSED = p.getProperty("REFUSED");//拒绝
		public static final String UNDEAL = p.getProperty("UNDEAL");//未执行
		public static final String FAIL = p.getProperty("FAIL");//失败
	}
	
	//方式
	public static final class Mode {
		public static final String GATEWAY = p.getProperty("GATEWAY");//网关
		public static final String QUICK = p.getProperty("QUICK");//快捷
		public static final String PC = p.getProperty("PC");//快捷
		public static final String MOBILE= p.getProperty("MOBILE");//移动快捷
	}
	
	//富友配置
	
	public static final class FuiouConfig{
		/**
		 * 秘钥
		 */
		public static final String MER_MD5_KEY = p.getProperty("MER_MD5_KEY_FUIOU");
		/**
		 * 商户号
		 */
		public static final String MER_MCHNTCD=p.getProperty("MER_MCHNTCD_FUIOU");
		/**
		 * 请求地址 支付
		 */
		 public static final String FUIOU_PAY_URL=p.getProperty("FUIOU_PAY_URL");
		 
		 /**
		  * 请求地址 支付
		  */
		public static final String FUIOU_QUERY_URL=p.getProperty("FUIOU_QUERY_URL");
		/**
		 * 公钥
		 */
		public static final String MER_PUB_KEY_FUIOU=p.getProperty("MER_PUB_KEY_FUIOU");
		/**
		 * 私钥
		 */
		public static final String MER_PRI_KEY_FUIOU=p.getProperty("MER_PRI_KEY_FUIOU");
	}
		
	//京东配置
	public static final class JDConfig{
		public static final String VERSION = p.getProperty("VERSION_JD");
		/**
		 * 名称
		 */
		public static final String MER_NAME = p.getProperty("MER_NAME_JD");
		/**
		 * 快捷支付-PC -商户号
		 */
		public static final String MER_NUM_PC = p.getProperty("MER_NUM_PC_JD");
		/**
		 * 快捷支付-MOBILE -商户号
		 */
		public static final String MER_NUM_MOBILE = p.getProperty("MER_NUM_MOBILE_JD");
		/**
		 * 网关 -商户号
		 */
		public static final String MER_NUM_GATEWAY = p.getProperty("MER_NUM_WEB_JD");
		/**
		 * DESKEY
		 */
		public static final String MER_DES_KEY = p.getProperty("MER_DES_KEY_JD");
		/**
		 * MD5KEY
		 */
		public static final String MER_MD5_KEY = p.getProperty("MER_MD5_KEY_JD");
		/**
		 * PRIVITE_KEY(私钥)
		 */
		public static final String MER_PRI_KEY = p.getProperty("MER_PRI_KEY_JD");
		/**
		 * PUBLIC_KEY(公钥)
		 */
		public static final String MER_PUB_KEY = p.getProperty("MER_PUB_KEY_JD");
		/**
		 * 充值
		 */
		public static final String RECHARGE = p.getProperty("RECHARGE_JD");
		/**
		 * 代付
		 */
		public static final String DEFRAYPAY = p.getProperty("DEFRAYPAY_JD");
		/**
		 * 查询
		 */
		public static final String QUERY = p.getProperty("QUERY_JD");
		
		/**
		 * 请求地址
		 */
		public static final String REQUEST_URL_PC = p.getProperty("REQUEST_URL_PC_JD");
		/**
		 * 请求地址
		 */
		public static final String REQUEST_URL_MOBILE = p.getProperty("REQUEST_URL_MOBILE_JD");
		/**
		 * 代付证书名称
		 */
		public static final String DEFRAYPAY_NAME =  p.getProperty("DEFRAYPAY_NAME_JD");
		/**
		 * 代付会员号
		 */
		public static final String DEFRAYPAY_NO =  p.getProperty("DEFRAYPAY_NO_JD");
	}
	
	public static final class BaofooConfig{
		/**
		 * 秘钥
		 */
		public static final String PFX = p.getProperty("pfx.pwd");
		/**
		 * 商户号
		 */
		public static final String MEMBER=p.getProperty("member.id");
		/**
		 * 请求地址 支付
		 */
		 public static final String TERMINAL=p.getProperty("terminal.id");
		 
		 /**
		  * 请求地址 支付
		  */
		 public static final String BAOFOO_PAY_URL=p.getProperty("baoFooUrl");
		 /**
		  * 请求地址 支付
		  */
		 public static final String TXNTYPE=p.getProperty("txnType");
		 /**
		  * 请求地址 支付
		  */
		public static final String DATATYPE=p.getProperty("data.type");
		/**
		 * 公钥
		 */
		public static final String PFXNAME=p.getProperty("pfx.name");
		
		//代付公钥
		public static final String KEYSTOREPATH=p.getProperty("keystorepath");
		//代付密码
		public static final String KEYSTOREPASSWORD=p.getProperty("keystorepassword");
		//代付私钥
		public static final String PUB_KEY=p.getProperty("pub_key");
		// 代付
		public static final String DEFRAYPAY="BFdefrayPay";
		/**
		 * 私钥
		 */
		public static final String CERNAME=p.getProperty("cer.name");
		public static final String QuickBindCard = "01";
		public static final String CancelBindCard = "02";
		public static final String QueryBindCard = "03";
		public static final String QueryRecharge = "06";
		public static final String PreBindCard = "11";
		public static final String ConfirmBindCard = "12";
		public static final String PreRecharge = "15";
		public static final String ConfirmRecharge="16";
		
	}	
	//币种
	public static final class Currency {
		/**
		 * 人民币
		 */
		public static final String CNY = p.getProperty("CNY");
	}
	//操作类型（中文描述）
	public static final class OperationType {
		/**
		 * 充值
		 */
		public static final String RECHARGE = p.getProperty("RECHARGE");
	}
	/**
	 * S2S地址前缀
	 */
	public static final String ResponseS2SUrl = p.getProperty("RESPONSE_URL");
	/**
	 * 证书地址
	 */
	public static final String CertificateAddr = p.getProperty("Certificate_Addr");
}
