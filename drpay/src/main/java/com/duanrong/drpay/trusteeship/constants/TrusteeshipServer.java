package com.duanrong.drpay.trusteeship.constants;

/**
 * 存管通接口枚举
 * @author xiao
 * @datetime 2016年12月7日 下午1:40:43
 */
public enum  TrusteeshipServer {

	//个人注册/绑卡
	PERSONAL_REGISTER,
	
	//企业注册/绑卡 
	ENTERPRISE_REGISTER,
	
	//个人绑卡
	PERSONAL_BIND_BANKCARD,
	
	//企业绑卡
	ENTERPRISE_BIND_BANKCARD,
	
	//充值
	RECHARGE,
	
	//提现
	WITHDRAW,
	
	//提现确认
	CONFIRM_WITHDRAW,
	
	//取消提现
	CANCEL_WITHDRAW,
	
	//创建标的
	ESTABLISH_PROJECT,
	
	//变更标的
	MODIFY_PROJECT,
	
	//用户预处理
	USER_PRE_TRANSACTION,
	
	//平台预处理
	PLATFORM_PRE_TRANSACTION,
	
	//预处理取消
	CANCEL_PRE_TRANSACTION,
	
	//放款确认
	CONFIRM_LOAN,
	
	//还款确认
	CONFIRM_REPAYMENT,
	
	//代偿确认
	CONFIRM_COMPENSATORY,
	
	//代偿还款确认
	CONFIRM_COMPENSATORY_REPAYMENT,
	
	//债权出让
	DEBENTURE_SALE,
	
	//取消债权出让
	CANCEL_DEBENTURE_SALE,
	
	//债权转让确认
	BATCH_CONFIRM_DEBENTURE_TRANSFER,
	
	//平台营销款确认
	CONFIRM_PLATFORM_MARKETING,
	
	//修改密码
	RESET_PASSWORD,
	
	//资金冻结
	FREEZE,
	
	//资金解冻
	UNFREEZE,
	
	//用户授权
	USER_AUTHORIZATION,
	
	//取消用户授权
	CANCEL_USER_AUTHORIZATION,
	
	//授权预处理
	USER_AUTO_PRE_TRANSACTION,
	
	//预留手机号更新
	MODIFY_MOBILE,
	
	//解绑银行卡
	UNBIND_BANKCARD_DIRECT,
	
	//验证密码
	CHECK_PASSWORD,
	
	//资金回退充值
	BACKROLL_RECHARGE,
	
	//自动提现
	AUTO_WITHDRAW,
	
	//企业信息修改
	ENTERPRISE_INFORMATION_UPDATE,
	
	//创建批量投标计划
	ESTABLISH_INTELLIGENT_PROJECT,
	
	//创建批量投标请求
	PURCHASE_INTELLIGENT_PROJECT,
	
	//批量放款
	INTELLIGENT_PROJECT_LOAN,
	
	//批量还款
	INTELLIGENT_PROJECT_REPAYMENT,
	
	//批量债权出让
	INTELLIGENT_PROJECT_DEBENTURE_SALE,
	
	//批量债权转让
	INTELLIGENT_PROJECT_DEBENTURE_TRANSFER,
	
	//批量投标解冻
	INTELLIGENT_PROJECT_UNFREEZE,
	
	//佣金扣除
	COMMISSION_DEDUCTING,
	
	//验密扣费
	VERIFY_DEDUCT,
	
	//用户信息查询
	QUERY_USER_INFORMATION,
	
	//平台信息查询
	QUERY_PLATFORM_INFORMATION,
	
	//单笔业务查询
	QUERY_TRANSACTION,
	
	//标的信息查询
	QUERY_PROJECT_INFORMATION,
	
	//查询批量投标流水
	QUERY_INTELLIGENT_PROJECT_ORDER,
	
	//会员信息导入
	IMPORT_PLATFORM_USER,
	
	//企业会员导入
	IMPORT_ENTERPRISE_PLATFORM_USER,
	
	//会员激活
	ACTIVATE_IMPORT_USER,
	
	//可修改手机号的激活激活接口
	ACTIVATE_STOCKED_USER,
	
	//存量标导入
	IMPORT_PROJECT,
	
	//批量投标请求流水号导入
	IMPORT_INTELLIGENT_PROJECT_ORDER,
	
	//未激活换卡
	CHANGE_USER_BANKCARD,
}
