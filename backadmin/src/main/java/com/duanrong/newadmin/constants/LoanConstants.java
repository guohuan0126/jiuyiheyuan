package com.duanrong.newadmin.constants;

public class LoanConstants {

	/**
	 * 申请企业借款 状态
	 * 
	 * @author Administrator
	 * 
	 */
	public final static class ApplyEnterpriseLoanStatus {
		/**
		 * 等待审核
		 */
		public final static String WAITING_VERIFY = "等待审核";
		/**
		 * 已审核
		 */
		public final static String VERIFIED = "已审核";
	}

	/**
	 * 借款状态
	 * 
	 * @author Administrator
	 * 
	 */
	public final static class LoanStatus {
		/**
		 * 等待审核
		 */
		public final static String WAITING_VERIFY = "等待审核";
		/**
		 * 贷前公示
		 */
		public final static String DQGS = "贷前公告";
		/**
		 * 审核未通过
		 */
		public final static String VARIFY_FAIL = "审核未通过";
		/**
		 * 筹款中
		 */
		public final static String RAISING = "筹款中";
		/**
		 * 等待复核
		 */
		public final static String RECHECK = "等待复核";
		/**
		 * 撤标
		 */
		public final static String WITHDRAWAL = "撤标";
		/**
		 * 流标
		 */
		public final static String CANCEL = "流标";
		/**
		 * 还款中
		 */
		public final static String REPAYING = "还款中";
		/**
		 * 逾期
		 */
		public final static String OVERDUE = "逾期";
		/**
		 * 完成
		 */
		public final static String COMPLETE = "完成";
		/**
		 * 坏账
		 */
		public final static String BAD_DEBT = "坏账";
	};

	/**
	 * 借款类型
	 * 
	 * @author Administrator
	 * 
	 */
	public final static class LoanType {
		/**
		 * 个人借款
		 */
		public final static String PERSONAL_LOAN = "个人借款";
		/**
		 * 竞标借款
		 */
		public final static String BIDDING_LOAN = "竞标借款";
		/**
		 * 一口价
		 */
		public final static String RATE_FIXED_LOAN = "一口价";
		/**
		 * 担保借款
		 */
		public final static String GUARANTEED_LOAN = "担保借款";

		/**
		 * 企业借款
		 */
		public final static String ENTERPRISE_LOAN = "企业借款";
		/**
		 * 车贷
		 */
		public final static String CARS_LOAN = "车贷";

		/**
		 * 房贷
		 */
		public final static String HOUSE_LOAN = "房贷";

	};

	/**
	 * 还款类型
	 * 
	 * @author Administrator
	 * 
	 */
	public final static class RepayType {
		/**
		 * 等额本金
		 */
		public final static String CAM = "等额本金";
		/**
		 * 等额本息
		 */
		public final static String CPM = "等额本息";
		/**
		 * 按月付息到期还本金 先息后本 FIXME:如果上线的项目，需要更新此行代码，注意已经在进行的项目类型，一起改掉。
		 */
		public final static String RFCL = "按月付息到期还本";
		/**
		 * 一次性到期还本付息
		 */
		public final static String DQHBFX = "一次性到期还本付息";
	};

	/**
	 * 还款状态
	 * 
	 * @author Administrator
	 * 
	 */
	public final static class RepayStatus {
		/**
		 * 还款中
		 */
		public final static String REPAYING = "还款中";
		/**
		 * 逾期
		 */
		public final static String OVERDUE = "逾期";
		/**
		 * 完成
		 */
		public final static String COMPLETE = "完成";
		/**
		 * 坏账
		 */
		public final static String BAD_DEBT = "坏账";
	};

	/**
	 * 借款审核状态
	 * 
	 * @author Administrator
	 * 
	 */
	public final static class LoanVerifyStatus {
		/**
		 * 通过
		 */
		public final static String PASSED = "通过";
		/**
		 * 未通过
		 */
		public final static String FAILED = "未通过";
	};

	/**
	 * 
	 * @author SunZ 计息规则
	 */
	public final static class InterestRule {
		/**
		 * 投资次日计息
		 */
		public final static String INVEST_NEXT_DAY = "投资次日计息";
		/**
		 * 放款次日计息
		 */
		public final static String GIVE_MONEY_NEXT_DAY = "放款次日计息";
	};
}
