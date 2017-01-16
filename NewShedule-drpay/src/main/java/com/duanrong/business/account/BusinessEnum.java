package com.duanrong.business.account;

/**
 * 业务类型
 * @author xiaoshuo
 *
 */
public enum BusinessEnum {
	
	//充值
	recharge,
	
	//充值手续费
	recharge_fee,
	
	//退款
	refund,
	
	//提现
	withdraw_cash,
	
	//提现手续费
	withdraw_fee,
	
	//直接转账
	transfer,
	
	//投资
	invest,
	
	//流标
	bidders,
	
	//放款
	give_money_to_borrower,
	
	//还款
	repay,
	
	//天天赚购买
	demand_in,
	
	//天天赚赎回
	demand_out,
	
	//补息
	allowance,

	//红包奖励
	reward,
	
	//平台转账
	pt_transfer
}
