package com.duanrong.newadmin.constants;

/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2015-2-5 下午3:03:53
 * @Description : NewAdmin com.duanrong.newadmin.constants AwardConstants.java
 * 
 */
public class AwardConstants {
	/**
	 * 奖励项目
	 */
	public static class AwardItemStatus {
		/**
		 * 等待审批
		 */
		public static final String WAIT_VERIFY = "等待审批";
		/**
		 * 等待复核
		 */
		public static final String WAIT_RECHECK = "等待复核";
		/**
		 * 审批通过
		 */
		public static final String APPROVED = "审批通过";
		/**
		 * 复核通过
		 */
		public static final String CHECKPASS = "复核通过";
		/**
		 * 审批未通过
		 */
		public static final String UNAPPROVED = "审批未通过";
		/**
		 * 固定金额
		 */
		public static final String MONEY_TYPE_FIXED = "固定金额";
		/**
		 * 注册奖励
		 */
		public static final String ITEM_TYPE_REGISTER = "注册奖励";
		/**
		 * 奖励发送
		 */
		public static final String SENDED = "已发送";
		/**
		 * 等待发送
		 */
		public static final String WAIT_SENDED = "等待发送";
		/**
		 * 复核未通过
		 */
		public static final String UNRECHECK = "复核未通过";
		/**
		 * 撤销奖励
		 */
		public static final String REPEAL = "撤销奖励";
	}

	/**
	 * 奖励项目信息
	 */
	public static class AwardItemUserStatus {
		/**
		 * 未发送
		 */
		public static final String UNSENDED = "未发送";
		/**
		 * 发送成功
		 */
		public static final String SENDED_SUCCESS = "发送成功";
		/**
		 * 发送失败
		 */
		public static final String SENDED_FAILURE = "发送失败";
	}
}
