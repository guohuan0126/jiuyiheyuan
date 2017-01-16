package com.duanrong.drpay.business.demand;

public class DemandtreasureConstants {
	
	/**
	 * 活期宝转入状态
	 * 
	 * @author Administrator
	 * 
	 */
	public final static class TransferInStatus {
		/**
		 * 发送 
		 */
		public static final String SENDED = "sended";
		/**
		 * 冻结
		 */
		public static final String FREEZE = "freeze";
		/**
		 * 确认
		 */
		public static final String CONFIRM = "confirm";
		/**
		 * 取消
		 */
		public static final String CANCEL = "cancel";
		/**
		 * 取消
		 */
		public static final String FAIL = "fail";
	}
	
	/**
	 * 活期宝转出状态
	 * 
	 * @author Administrator
	 * 
	 */
	public final static class TransferOutStatus {
		/**
		 * 发送 
		 */
		public static final String SENDED = "sended";
		/**
		 * 冻结
		 */
		public static final String SUCCESS = "success";
	}
	/**
	 * 活期宝流水表状态
	 * @author JD
	 *
	 */
	public final static class DemandBillStatus {
		/**
		 * 转入
		 */
		public static final String TRANIN = "in";
		/**
		 * 转出本金
		 */
		public static final String TRANOUT = "out";
		/**
		 * 转出利息
		 */
		public static final String OUTINTEREST = "outinterest";
		/**
		 * 派息
		 */
		public static final String INTEREST = "interest";
		/**
		 * 转出成功
		 */
		public static final String SUCCESS = "success";
	}
}
