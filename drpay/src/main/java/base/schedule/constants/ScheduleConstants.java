package base.schedule.constants;

/**
 * Schedule constants.
 * 
 */
public class ScheduleConstants {
	/**
	 * job name
	 */
	public static class JobName {
		/**
		 * 项目到期自动还款，自动改项目、投资 状态，等等。
		 */
		public static final String AUTO_REPAYMENT = "auto_repayment";
		/**
		 * 第三方资金托管，主动查询
		 */
		public static final String REFRESH_TRUSTEESHIP_OPERATION = "refresh_trusteeship_operation";
		/**
		 * 账户资金平衡
		 */
		public static final String ACCOUNT_CHECKING = "account_checking";
	}

	/**
	 * job group
	 */
	public static class JobGroup {
		/**
		 * 检查项目是否到预计执行时间
		 */
		public static final String CHECK_LOAN_OVER_EXPECT_TIME = "check_loan_over_expect_time";
		public static final String CHECK_FREEZE_OVER_EXPECT_TIME = "check_freeze_over_expect_time";

		/**
		 * 自动投标
		 */
		public static final String AUTO_INVEST_AFTER_LOAN_PASSED = "auto_invest_after_loan_passed";

		/**
		 * 检查债权转让是否到期
		 */
		public static final String CHECK_INVEST_TRANSFER_OVER_EXPECT_TIME = "check_invest_transfer_over_expect_time";

		/**
		 * 项目到期自动还款，自动改项目、投资 状态，等等。
		 */
		public static final String AUTO_REPAYMENT = "auto_repayment";
		/**
		 * 第三方资金托管，主动查询
		 */
		public static final String REFRESH_TRUSTEESHIP_OPERATION = "refresh_trusteeship_operation";
		/**
		 * 账户资金平衡
		 */
		public static final String ACCOUNT_CHECKING = "account_checking";

		/**
		 * 检查投资流标
		 */
		public static final String CHECK_INVEST_OVER_EXPECT_TIME = "check_invest_over_expect_time";
		
		/**
		 * 检查活期宝投资流标
		 */
		public static final String CHECK_DEMANDIN_OVER_EXPECT_TIME = "check_demandin_over_expect_time";

	}

	/**
	 * trigger name
	 */
	public static class TriggerName {
		/**
		 * 项目到期自动还款，自动改项目、投资 状态，等等。
		 */
		public static final String AUTO_REPAYMENT = "auto_repayment";
		/**
		 * 第三方资金托管，主动查询
		 */
		public static final String REFRESH_TRUSTEESHIP_OPERATION = "refresh_trusteeship_operation";
		/**
		 * 账户资金平衡
		 */
		public static final String ACCOUNT_CHECKING = "account_checking";
	}

	/**
	 * trigger group
	 */
	public static class TriggerGroup {
		/**
		 * 检查项目是否到预计执行时间
		 */
		public static final String CHECK_LOAN_OVER_EXPECT_TIME = "check_loan_over_expect_time";
		/**
		 * 检查自动解冻时间
		 */
		public static final String CHECK_FREEZE_OVER_EXPECT_TIME = "check_freeze_over_expect_time";

		/**
		 * 自动投标
		 */
		public static final String AUTO_INVEST_AFTER_LOAN_PASSED = "auto_invest_after_loan_passed";

		/**
		 * 检查债权转让是否到期
		 */
		public static final String CHECK_INVEST_TRANSFER_OVER_EXPECT_TIME = "check_invest_transfer_over_expect_time";

		/**
		 * 项目到期自动还款，自动改项目、投资 状态，等等。
		 */
		public static final String AUTO_REPAYMENT = "auto_repayment";
		/**
		 * 第三方资金托管，主动查询
		 */
		public static final String REFRESH_TRUSTEESHIP_OPERATION = "refresh_trusteeship_operation";
		/**
		 * 账户资金平衡
		 */
		public static final String ACCOUNT_CHECKING = "account_checking";
		
		/**
		 * 检查投资流标
		 */
		public static final String CHECK_INVEST_OVER_EXPECT_TIME = "check_invest_over_expect_time";
		
		/**
		 * 检查活期宝投资流标
		 */
		public static final String CHECK_DEMANDIN_OVER_EXPECT_TIME = "check_demandin_over_expect_time";

	}

}
