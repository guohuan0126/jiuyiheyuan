package com.duanrong.drpay.business.transaction.constants;

/**
 * 通用转账
 * 
 * @author Lin Zhiming
 * @version May 27, 2015 11:44:04 AM
 */
public class GeneralTransferConstants {

	/**
	 * 转账的各种状态
	 */
	public final static class TransferStatus {
		/**
		 * 等待返回授权结果
		 */
		public final static String WAIT = "WAIT";
		/**
		 * 预处理成功
		 */
		public final static String PREAUTH = "SENDED";
		/**
		 * 解冻后完成资金划转
		 */
		public final static String CONFIRM = "CONFIRM";
		
		/**
		 * 转账失败
		 */
		public final static String FAIL = "FAIL";
	}
}
