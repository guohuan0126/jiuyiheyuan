package com.duanrong.drpay.business.invest;

public class InvestConstants {

	/**
	 * 投资状态
	 * 
	 * @author Administrator
	 * 
	 */
	public final static class InvestStatus {
		
		/**
		 * 第三方资金托管确认中
		 */
		public final static String WAIT_AFFIRM = "等待确认";

		/**
		 * 投标成功
		 */
		public final static String BID_SUCCESS = "投标成功";
	
		/**
		 * 流标
		 */
		public final static String CANCEL = "流标";
		/**
		 * 还款中
		 */
		public final static String REPAYING = "还款中";
		
		/**
		 * 完成
		 */
		public final static String COMPLETE = "完成";
		
	};
	
	
	/**
	 * 自动投标
	 * 
	 * @author Administrator
	 * 
	 */
	public final static class AutoInvest {
		/**
		 * 状态
		 * @author Administrator
		 *
		 */
		public final static class Status {
			/**
			 * 开启
			 */
			public final static String ON = "on";
			/**
			 * 关闭
			 */
			public final static String OFF = "off";
			
		}
	};

}
