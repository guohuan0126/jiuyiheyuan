package com.duanrong.drpay.config;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 业务类型标示
 * 用于生产流水号标示
 * @Author : 
 * @CreateTime : 2014-8-20 上午10:32:51
 * @Description : jdp2p com.esoft.core.util ToType.java
 * 
 */
public enum ToType {
	/**
	 * 开户
	 */
	OPAC,
	/**
	 * 激活
	 */
	ACTI,
	/**
	 * 充值
	 */
	RECH,
	/**
	 * 投资
	 */
	INVE,
	
	/**
	 * 投资理财计划
	 */
	IVPJ,
	
	/**
	 * 投资子标
	 */
	IVSB,
	/**
	 * 放款
	 */
	GMTB,
	/**
	 * 平台划款
	 */
	PLTR,
	/**
	 * 还款
	 */
	REPA,
	/**
	 * 提现
	 */
	WICA,
	/**
	 * 绑定银行卡
	 */
	BYBC,
	/**
	 * 取消绑定银行卡
	 */
	TUBC,
	/**
	 * 自动投标授权
	 */
	AAIT,
	/**
	 * 取消自动投标授权
	 */
	CAAT,
	/**
	 * 资金冻结
	 */
	FROZ,
	/**
	 * 资金解冻
	 */
	UNFR,
	/**
	 * 自动投标
	 */
	ZDTB,
	/**
	 * 重置密码
	 */
	RPWD,
	/**
	 * 校验密码
	 */
	CPWD,
	/**
	 * 直接转账
	 */
	DTSC,
	/**
	 * 通用转账
	 */
	TSCA,	
	/**
	 * 修改手机号
	 */
	MDPN,
	
	/**
	 * 转入活期宝
	 */
	RCIN,
	/**
	 * 转出活期宝
	 */
	RCUT,
	/**
	 * 批量还款
	 */
	PLRE,
	/**
	 * 批量还款明细
	 */
	PLMX,
	/**
	 * 未激活换卡
	 */
	CUBC,
}