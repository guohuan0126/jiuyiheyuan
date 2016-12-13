package com.duanrong.newadmin.constants;

public class FundConstants {
	public static final class Status{
		/**
		 * 冻结
		 */
		public static final Integer FROZE = 0;
		/**
		 * 冻结未成功(初始状态)
		 */
		public static final Integer UNFROZE = 1;
		/**
		 * 冻结后已解冻
		 */
		public static final Integer UNFROZESUCCESS = 2;
	}
}
