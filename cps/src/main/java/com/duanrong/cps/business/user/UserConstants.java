package com.duanrong.cps.business.user;

public class UserConstants {
	/**
	 * 用户状态
	 * 
	 * @author 尹逊志
	 * @date 2014-8-29上午11:57:06
	 */
	public final static class UserStatus {
		/**
		 * 可用
		 */
		public final static String ENABLE = "1";
		/**
		 * 不可用
		 */
		public final static String DISABLE = "0";
		/**
		 * 未激活
		 */
		public final static String NOACTIVE = "2";

	}

	/**
	 * 用户资金类型
	 * 
	 * @author 尹逊志
	 * @date 2014-8-29下午3:13:54
	 */
	public final static class UserMoneyType {
		/**
		 * 冻结
		 */
		public final static String FREEZE = "freeze";

		/**
		 * 解冻
		 */
		public final static String UNFREEZE = "unfreeze";

		/**
		 * 从余额转出 transfer out from balance
		 */
		public final static String TO_BALANCE = "to_balance";

		/**
		 * 转入到余额 tansfer into balance
		 */
		public final static String TI_BALANCE = "ti_balance";

		/**
		 * 从冻结金额中转出 transfer out frome frozen money
		 */
		public final static String TO_FROZEN = "to_frozen";

		/**
		 * 平台划款转入余额
		 */
		public final static String PT_BALANCE = "pt_balance";
		/**
		 * 借款管理费
		 */
		public final static String MANAGEMENT = "management";
	}

	/**
	 * 提现状态
	 * 
	 * @author Administrator
	 * 
	 */
	public final static class WithdrawStatus {
		/**
		 * 等待审核
		 */
		public static final String WAIT_VERIFY = "wait_verify";
		/**
		 * 提现成功
		 */
		public static final String SUCCESS = "success";
		/**
		 * 审核未通过
		 */
		public static final String VERIFY_FAIL = "verify_fail";
	}

	/**
	 * 充值状态
	 * 
	 * @author Administrator
	 * 
	 */
	public final static class RechargeStatus {
		/**
		 * 等待付款
		 */
		public static final String WAIT_PAY = "wait_pay";
		/**
		 * 充值成功
		 */
		public static final String SUCCESS = "success";
		/**
		 * 充值失败
		 */
		public static final String FAIL = "fail";
	}
	/**
	 * 开户状态
	 * 
	 * @author Administrator
	 * 
	 */
	public final static class AccountStatus {
		/**
		 * 通过
		 */
		public static final String PASSED = "passed";
	}
}
