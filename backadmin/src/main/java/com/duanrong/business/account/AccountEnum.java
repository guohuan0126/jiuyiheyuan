package com.duanrong.business.account;

/**
 * 账户类型
 * @author xiao
 * 
 */
public enum AccountEnum {

	//转入
	ti_balance,
	
	//转出
	to_balance,
	
	//平台转入
	pt_balance,
	
	//冻结
	freeze,
	
	//解冻
	unfreeze,
	
	//从冻结中转出
	to_frozen,
	
}