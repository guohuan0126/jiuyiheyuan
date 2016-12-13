package com.duanrong.business.bankcard;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 处理银行卡简称
 * @author 林志明
 *
 */
public class BankName {
	public static Map<String, String> getBankMap() {
		Map<String, String> bankMap = new HashMap<String, String>(); 
		bankMap.put("ICBC", "中国工商银行");
		bankMap.put("BOC", "中国银行");
		bankMap.put("BOCO", "交通银行");
		bankMap.put("CEB", "中国光大银行");
		bankMap.put("SPDB", "上海浦东发展银行");
		bankMap.put("ABC", "中国农业银行");
		bankMap.put("ECITIC", "中信银行");
		bankMap.put("PAB", "中国平安银行");
		bankMap.put("CCB", "中国建设银行");
		bankMap.put("CMBC", "中国民生银行");
		bankMap.put("SDB", "深圳发展银行");
		bankMap.put("POST", "中国邮政储蓄银行");
		bankMap.put("CMBCHINA", "招商银行");
		bankMap.put("CIB", "兴业银行");
		bankMap.put("ZZYH", "郑州银行");
		bankMap.put("HKYH", "汉口银行");
		bankMap.put("CDYH", "成都银行");
		bankMap.put("HXB", "华夏银行");
		bankMap.put("GZYH", "广州银行");
		
		return bankMap;
	}
	
	public static String getBankName(String bank) {
		Map<String, String> bankMap = getBankMap();
		String bankName = bankMap.get(bank);
		if(StringUtils.isNotBlank(bankName)) {
			return bankName;
		}
		
		return bank;
	}
}
