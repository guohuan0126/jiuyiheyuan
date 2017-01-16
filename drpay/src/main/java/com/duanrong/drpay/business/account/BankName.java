package com.duanrong.drpay.business.account;

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
//		bankMap.put("ICBC", "工商银行");
//		bankMap.put("BOC", "中国银行");
//		bankMap.put("BOCO", "交通银行");
//		bankMap.put("CEB", "光大银行");
//		bankMap.put("SPDB", "浦发银行");
//		bankMap.put("ABC", "农业银行");
//		bankMap.put("ECITIC", "中信银行");
//		bankMap.put("PAB", "平安银行");
//		bankMap.put("CCB", "建设银行");
//		bankMap.put("CMBC", "民生银行");
//		bankMap.put("SDB", "深圳发展银行");
//		bankMap.put("POST", "邮政银行");
//		bankMap.put("CMBCHINA", "招商银行");
//		bankMap.put("CMB", "招商银行");
//		bankMap.put("CIB", "兴业银行");
//		bankMap.put("ZZYH", "郑州银行");
//		bankMap.put("HKYH", "汉口银行");
//		bankMap.put("CDYH", "成都银行");
//		bankMap.put("HXB", "华夏银行");
//		bankMap.put("GZYH", "广州银行");
		
		
		/*bankMap.put("ICBC","工商银行");
		bankMap.put("CMB","招商银行");
		bankMap.put("ABC","农业银行");
		bankMap.put("CCB","建设银行");
		bankMap.put("BOC","中国银行");
		bankMap.put("PSBC","邮政银行");
		bankMap.put("HXB","华夏银行");
		bankMap.put("BOCO","交通银行");
		bankMap.put("BOB","北京银行");
		bankMap.put("SPDB","浦发银行");
		bankMap.put("GDB","广发银行");
		bankMap.put("CNCB","中信银行");
		bankMap.put("NJYH","南京银行");
		bankMap.put("CIB","兴业银行");
		bankMap.put("PAB","平安银行");
		bankMap.put("CEB","光大银行");
		bankMap.put("CMBC","民生银行");
		bankMap.put("BRCB","北京农商银行");
		bankMap.put("HZYH","杭州银行");
		bankMap.put("BOZZ","郑州银行");
		bankMap.put("HKBEA","东亚银行");
		bankMap.put("HKYH","汉口银行");
		bankMap.put("BOJS","江苏银行");
		bankMap.put("NBB","宁波银行");
		bankMap.put("CDYH","成都银行");
		bankMap.put("GZYH","广州银行");
		bankMap.put("SZCB","深圳市商业银行");
		bankMap.put("BOXM","厦门银行");
		bankMap.put("BODL","大连银行");
		bankMap.put("CQRCB","重庆农商行");
		bankMap.put("BOSJ","盛京银行");
		bankMap.put("ZJTLCB","浙江泰隆商行");
		bankMap.put("HKB","汉口银行");
		bankMap.put("YTCB","烟台银行");*/
		
		bankMap.put("ABOC","中国农业银行");
		bankMap.put("BKCH","中国银行");
		bankMap.put("CIBK","中信银行");
		bankMap.put("EVER","中国光大银行");
		bankMap.put("FJIB","兴业银行");
		bankMap.put("GDBK","广发银行");
		bankMap.put("HXBK","华夏银行");
		bankMap.put("ICBK","中国工商银行");
		bankMap.put("MSBC","中国民生银行");
		bankMap.put("PCBC","中国建设银行");
		bankMap.put("PSBC","中国邮政储蓄银行");
		bankMap.put("SZDB","平安银行");
		bankMap.put("SPDB","浦发银行");
		bankMap.put("BJCN","北京银行");
		bankMap.put("CMBC","招商银行");
		bankMap.put("COMM","交通银行");
		bankMap.put("CBXM","厦门银行");

		return bankMap;
	}
	
	
	public static Map<String, String> getBankCodeMap() {
		Map<String, String> bankMap = new HashMap<String, String>(); 
		/*bankMap.put("工商银行","ICBC");
		bankMap.put("招商银行","CMB");
		bankMap.put("农业银行","ABC");
		bankMap.put("建设银行","CCB");
		bankMap.put("中国银行","BOC");
		bankMap.put("邮政银行","PSBC");
		bankMap.put("华夏银行","HXB");
		bankMap.put("交通银行","BOCO");
		bankMap.put("北京银行","BOB");
		bankMap.put("浦发银行","SPDB");
		bankMap.put("广发银行","GDB");
		bankMap.put("中信银行","CNCB");
		bankMap.put("南京银行","NJYH");
		bankMap.put("兴业银行","CIB");
		bankMap.put("平安银行","PAB");
		bankMap.put("光大银行","CEB");
		bankMap.put("民生银行","CMBC");
		bankMap.put("北京农商银行","BRCB");
		bankMap.put("杭州银行","HZYH");
		bankMap.put("郑州银行","BOZZ");
		bankMap.put("东亚银行","HKBEA");
		bankMap.put("汉口银行","HKYH");
		bankMap.put("江苏银行","BOJS");
		bankMap.put("宁波银行","NBB");
		bankMap.put("成都银行","CDYH");
		bankMap.put("广州银行","GZYH");
		bankMap.put("深圳市商业银行","SZCB");
		bankMap.put("厦门银行","BOXM");
		bankMap.put("大连银行","BODL");
		bankMap.put("重庆农商行","CQRCB");
		bankMap.put("盛京银行","BOSJ");
		bankMap.put("浙江泰隆商行","ZJTLCB");
		bankMap.put("汉口银行","HKB");
		bankMap.put("烟台银行","YTCB");*/
		bankMap.put("中国农业银行","ABOC");
		bankMap.put("中国银行","BKCH");
		bankMap.put("中信银行","CIBK");
		bankMap.put("中国光大银行","EVER");
		bankMap.put("兴业银行","FJIB");
		bankMap.put("广发银行","GDBK");
		bankMap.put("华夏银行","HXBK");
		bankMap.put("中国工商银行","ICBK");
		bankMap.put("中国民生银行","MSBC");
		bankMap.put("中国建设银行","PCBC");
		bankMap.put("中国邮政储蓄银行","PSBC");
		bankMap.put("平安银行","SZDB");
		bankMap.put("浦发银行","SPDB");
		bankMap.put("北京银行","BJCN");
		bankMap.put("招商银行","CMBC");
		bankMap.put("交通银行","COMM");
		bankMap.put("厦门银行","CBXM");
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
	
	public static String getBankCode(String bank) {
		Map<String, String> bankMap = getBankCodeMap();
		String code = bankMap.get(bank);
		if(StringUtils.isNotBlank(code)) {
			return code;
		}
		
		return bank;
	}
}
