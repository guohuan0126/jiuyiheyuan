package com.duanrong.drpay.config;

/**
 * 业务类型
 * @author xiaoshuo
 *
 */
public enum BusinessEnum {
	
	//开户
	create_account,
	
	//激活
	activate_account,
	//绑卡
	bindcard,
	
	//解绑
	unbindcard,
	
	//重置交易密码
	reset_password,
	
	//修改预留手机号
	modify_mobile,
	
	//管理员转入
	transfer_in,
	
	//管理员转出
	transfer_out,
	
	//管理员冻结
	freeze,
	
	//管理员解冻
	unfreeze,
	
	//充值
	recharge,
	
	//充值手续费
	fee,
	
	//线下充值
	recharge_line,
	
	//提现
	withdraw_cash,

	//退款
	refund,

	//直接转账
	transfer,
	
	//投资
	invest,
	
	//投资
	auto_invest,
	
	//投资理财计划
	invest_project,
	
	//流标
	bidders,
	
	//放款
	give_money_to_borrower,
	
	//还款
	repay,
	
	//还款确认
	repay_confirm,
	
	//天天赚购买
	demand_in,
	
	//天天赚赎回(后台发起)
	demand_out,
	
	//补息
	allowance,

	//红包奖励
	reward,
	
	//奖励确认
	reward_confirm,
	
	//平台转账
	pt_transfer,
	
	//未激活换卡
	change_user_bankcard,
	
}