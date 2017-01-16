package com.duanrong.business.trusteeship.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.duanrong.util.LoadConstantProterties;



public class TrusteeshipYeepayConstants {
	public static final String Package = "com.duanrong.business.yeepay.model";
	
	public static Properties p = null;
	
	static {
		List<String> list = new ArrayList<String>();
		list.add("UserConstants.properties");
		HashMap<String,Properties> loadConstantsPro = LoadConstantProterties.loadConstantsPro(list);
		if(loadConstantsPro.size()>0 && loadConstantsPro != null){
			p = loadConstantsPro.get("TrusteeshipYeepayConstants.properties");
		}
	}
	
	
	public static final class Status {
		/**
		 * 等待发送
		 */
		public static final String UN_SEND = p.getProperty("UN_SEND");
		/**
		 * 已发送
		 */
		public static final String SENDED = p.getProperty("SENDED");
		/**
		 * 通过
		 */
		public static final String PASSED = p.getProperty("PASSED");
		/**
		 * 未通过
		 */
		public static final String REFUSED = p.getProperty("REFUSED");
		/**
		 * 无响应
		 */
		public static final String NO_RESPONSE = p.getProperty("NO_RESPONSE");
	}

	public static final class OperationType {
		/**
		 * 开户
		 */
		public static final String CREATE_ACCOUNT = p.getProperty("CREATE_ACCOUNT");

		/**
		 * 充值
		 */
		public static final String RECHARGE = p.getProperty("RECHARGE");

		/**
		 * 投标
		 */
		public static final String INVEST = p.getProperty("INVEST");
		/**
		 * 直接转账
		 */
		public static final String DIRECT_TRANSACTION = p.getProperty("DIRECT_TRANSACTION");
		/**
		 * 通用转账
		 */
		public static final String TO_CP_TRANSACTION = p.getProperty("TO_CP_TRANSACTION");
	
		/**
		 * 放款
		 */
		public static final String GIVE_MOENY_TO_BORROWER = p.getProperty("GIVE_MOENY_TO_BORROWER");
		
		/**
		 * 放款2.0
		 */
		public static final String COMPLETE_TRANSACTION_INVEST = p.getProperty("COMPLETE_TRANSACTION_INVEST");
		
		/**
		 * 平台划款
		 */
		public static final String PLATFORM_TRANSFER = p.getProperty("PLATFORM_TRANSFER");

		/**
		 * 还款
		 */
		public static final String REPAY = p.getProperty("REPAY");

		/**
		 * 提现
		 */
		public static final String WITHDRAW_CASH = p.getProperty("WITHDRAW_CASH");
		/**
		 * 绑定银行卡
		 */
		public static final String BINDING_YEEPAY_BANKCARD = p.getProperty("BINDING_YEEPAY_BANKCARD");
		/**
		 * 取消绑定银行卡
		 */
		public static final String TO_Unbind_BankCard = p.getProperty("TO_Unbind_BankCard");
		
		/**
		 * 自动投标授权
		 */
		public static final String AUTHORIZEAUTOINVEST = "AUTHORIZEAUTOINVEST";
		
		/**
		 * 取消自动投标授权
		 */
		public static final String CANCEL_AUTHORIZE_AUTO_TRANSFER = "CANCEL_AUTHORIZE_AUTO_TRANSFER";
		/**
		 * 资金冻结
		 */
		public static final String FREEZE = "FREZZ";
		/**
		 * 资金解冻
		 */
		public static final String UNFREEZE = "UNFREEZE";
		
		/**
		 * 自动投标
		 */
		public static final String AUTOINVEST = p.getProperty("AUTO_INVEST");
		/**
		 * 转账确认
		 */
		public static final String THANAUTH = p.getProperty("THAN_AUTH");
		/**
		 * 代扣
		 */
		public static final String WHDEBITNOCARD = p.getProperty("WHDEBITNOCARD");
		/**
		 * 转入活期宝
		 */
		public static final String TO_RCIN = "TO_RCIN";
		/**
		 * 修改手机号
		 */
		public static final String RESET_MOBILE = "RESET_MOBILE";
		
	}

	public static final class Config {
		/**
		 * 商户号(test)
		 */
		// public static final String MER_CODE = "808801";
		/**
		 * 商户号
		 */
		public static final String MER_CODE = p.getProperty("MER_CODE");
	}

	/**
	 * 请求地址
	 * 
	 * @author Administrator
	 * 
	 */
	public static final class RequestUrl {
		/**
		 * 直连接口请求地址
		 */
		public static final String RequestDirectUrl = p.getProperty("RequestDirectUrl");
		public static final String PontractPath = p.getProperty("contractPath")+"/getBlackList?pwd=admin";
		public static final String MContractPath = p.getProperty("mcontractPath")+"/getBlackList?pwd=admin";
		public static final String MobilePath = p.getProperty("mcontractPath")+"/getMobileList?pwd=admin";
		public static final String PMobilePath = p.getProperty("contractPath")+"/getMobileList?pwd=admin";

		/**          
		 * 开户(易宝测试)
		 */
		// public static final String CREATE_YEEPAY_ACCT =
		// "http://qa.yeepay.com/member/bha/toRegister";
		/**
		 * 开户(易宝正式)
		 */
		public static final String CREATE_YEEPAY_ACCT = p.getProperty("YEEPAY_URL")+"toRegister";
		/**
		 * 绑卡(易宝test)
		 */
		// public static final String BINDING_YEEPAY_BANKCARD =
		// "http://qa.yeepay.com/member/bha/toBindBankCard";
		/**
		 * 绑卡(易宝)
		 */
		public static final String BINDING_YEEPAY_BANKCARD = p.getProperty("YEEPAY_URL")+"toBindBankCard";
		/**
		 * 充值()
		 */
		public static final String RECHARGE = p.getProperty("YEEPAY_URL")+"toRecharge";

		/**
		 * 投标
		 */
		public static final String INVEST = p.getProperty("YEEPAY_URL")+"toTransfer";
		
		/**
		 * 自动投标授权
		 */
		public static final String AUTOINVEST = p.getProperty("YEEPAY_URL")+"toAuthorizeAutoTransfer";

		/**
		 * 通用转账授权
		 */
		public static final String TO_CP_TRANSACTION = p.getProperty("YEEPAY_URL")+"toCpTransaction ";
		/**
		 * 投标(test)
		 */
		// public static final String INVEST =
		// "http://qa.yeepay.com/member/bha/toTransfer";

		/**
		 * 还款(test)
		 */
		// public static final String REPAY =
		// "http://p2p.ips.net.cn/CreditWeb/repaymentTrade.aspx";

		/**
		 * 还款
		 */
		public static final String REPAY = p.getProperty("YEEPAY_URL")+"toRepayment";

		/**
		 * 提现(test)
		 */
		// public static final String WITHDRAW_CASH =
		// "http://p2p.ips.net.cn/CreditWeb/dwTrade.aspx";

		/**
		 * 提现
		 */
		public static final String WITHDRAW_CASH = p.getProperty("YEEPAY_URL")+"toWithdraw";
		/**
		 * 取消绑卡
		 */
		public static final String TO_Unbind_BankCard = p.getProperty("YEEPAY_URL")+"toUnbindBankCard";
		
		/**
		 * 自动投标授权
		 */
		public static final String AUTHORIZEAUTOINVEST = p.getProperty("YEEPAY_URL")+"toAuthorizeAutoTransfer";
	}

	/**
	 * 返回地址（web）
	 */
	public static final class ResponseWebUrl {
		/**
		 * 地址前缀（正式）
		 */
		// public static final String PRE_RESPONSE_URL =
		// "http://115.28.246.231/trusteeship_return_web/";
		/**
		 * 地址前缀（测式）
		 */
		public static final String PRE_RESPONSE_URL = p.getProperty("ResponseWebUrl_PRE_RESPONSE_URL");
		
		public static final String PRE_RESPONSE_URL_REG = p.getProperty("PRE_RESPONSE_URL_REG");
	}

	/**
	 * 返回地址（server to server）
	 */
	public static final class ResponseS2SUrl {
		/**
		 * 地址前缀(正式)
		 */
		public static final String PRE_RESPONSE_URL = p.getProperty("ResponseS2SUrl_PRE_RESPONSE_URL");
		/**
		 * 地址前缀（测式）
		 */
		// public static final String PRE_RESPONSE_URL =
		// "http://localhost：8080/wangkun/trusteeship_return_web/";
	}

}
