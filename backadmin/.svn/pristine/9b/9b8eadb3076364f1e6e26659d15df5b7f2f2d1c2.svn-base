package com.duanrong.newadmin.controllhelper;

import java.util.HashMap;
import java.util.Map;

import com.duanrong.business.bankcard.model.BankCard;


/**
 * 处理银行卡简称
 * @author 林志明
 *
 */
public class BankName {
	public Map<String, String> getBankMap() {
		Map<String, String> bankMap = new HashMap<String, String>(); 
		bankMap.put("ICBC", "中国工商银行");
		bankMap.put("BC", "中国银行");
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
		
		return bankMap;
	}
	
	public String getBankName(Object bank) {
		Map<String, String> bankMap = new BankName().getBankMap();
		BankCard bankCard = (BankCard) bank;
		String bank2 = bankCard.getBank();
		String bankName = bankMap.get(bank2);
		if(bankName != null) {
			return bankName;
		}
		
		return bank2;
	}
}
