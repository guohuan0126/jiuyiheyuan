package com.duanrong.cps.business.invest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.duanrong.cps.business.invest.model.Invest;

public class InvestConstants {
	/**
	 * Package name.
	 */
	public final static String Package = "com.esoft.archer.investment";

	public final static class View {
		// public final static String INVESTMENT_LIST = "/admin/link/linkList";
	}
	
	/**
	 * 债权转让状态
	 * 
	 * @author Administrator
	 * 
	 */
	public final static class InvestTransferStatus {
		/**
		 * 转让中
		 */
		public final static String TRANSFERING = "转让中";
		/**
		 * 转让成功
		 */
		public final static String TRANSFED = "转让成功";
		/**
		 * 流标
		 */
		public final static String CANCEL = "流标";
	};

	/**
	 * 投资状态
	 * 
	 * @author Administrator
	 * 
	 */
	public final static class InvestStatus {
		/**
		 * 竞标中
		 */
		public final static String BIDDING = "竞标中";
		
		/**
		 * 第三方资金托管确认中
		 */
		public final static String WAIT_AFFIRM = "等待确认";
		/**
		 * 竞标失败
		 */
		public final static String BID_FAILED = "竞标失败";
		/**
		 * 投标成功
		 */
		public final static String BID_SUCCESS = "投标成功";
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
		 * 债权转让
		 */
		public final static String TRANSFER = "债权转让";
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
	 * 投资类型
	 * 
	 * @author Administrator
	 * 
	 */
	public final static class InvestType {
		/**
		 * 无
		 */
		public final static String NONE = "无";
		/**
		 * 本息保障
		 */
		public final static String PRINCIPAL_PROTECTION = "本息保障";
//		/**
//		 * 本息保障
//		 */
//		public final static String PRINCIPAL_INTEREST_PROTECTION = "本息保障";
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
			/**
			 * 等待响应 中间状态
			 */
			public static final String WAIT_AFFIRM = "wait";			
		}
	};

	/**
	 * 获取所有投标成功及其以后的状态的投资（投标成功，还款中，逾期，完成，坏账）
	 * 
	 * @param invests
	 * @return
	 */
	public static List<Invest> getSuccessInvests(
			List<Invest> invests) {
		List<Invest> investmentsR = new ArrayList<Invest>();
		for (Invest investment : invests) {
			if (!StringUtils.equals(investment.getStatus(), InvestStatus.WITHDRAWAL) && !StringUtils.equals(investment.getStatus(), InvestStatus.CANCEL)) {
				investmentsR.add(investment);
			}
		}
		return investmentsR;
	}

}
