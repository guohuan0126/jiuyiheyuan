package com.duanrong.drpay.business.repay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;



import util.DateStyle;
import util.DateUtil;


/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-11-24 上午11:33:34
 * @Description : drpc com.duanrong.pcweb.controllhelper RepayHelper.java
 * 
 */
public class RepayHelper {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 该方法返回的map集合,多少个key就代表有多少期,每个key对应的就是第几期,value对应的是该期是多少天
	 * 
	 * @param start
	 *            放款日期
	 * @param dayAmount
	 *            借多少天
	 * @return
	 * @throws ParseException
	 */
	public static HashMap<Integer, RepayDate> Loop(Date start, Integer dayAmount)
			throws ParseException {
		HashMap<Integer, RepayDate> hm = new HashMap<Integer, RepayDate>();
		

		int loop = 1;
		int count = 0;

		Date end = new Date();
		Date endd = null;
		while (count < dayAmount) {

			int MonthDay = DateUtil.dayDifference(sdf.format(start),
					sdf.format(DateUtil.getNextMouth(start)));
			int DayEx = 5;// 如果是大于一个月，而且多余的天数大于5天，生成新的分期
			int termDays = MonthDay + DayEx;

			int dayDiff = dayAmount - count;

			if (dayDiff >= termDays)// 如果是大于一个月，而且多余的天数大于5天，生成新的分期
			{
				end = DateUtil.MonthAdd(start, 1);
			} else if (dayDiff >= 31 && dayDiff <= termDays)// 如果是大于一个月，而且多余的天数小于于5天，划入到上一期进行还款
			{
				end = DateUtil.DayAdd(start, dayDiff);
			} else if (dayDiff <= 28) {
				end = DateUtil.DayAdd(start, dayDiff);
			} else if (dayDiff == 29 || dayDiff == 30) {
				end = DateUtil.DayAdd(start, dayDiff);
			}

			count = DateUtil.DayDifference(sdf.format(end), sdf.format(start))
					+ count;

			RepayDate rd = new RepayDate();
			endd = end;
			end = DateUtil.addDay(end, -1);
			rd.setRepayDate(end);
			rd.setDayAmount(DateUtil.DayDifference(
					sdf.format(DateUtil.addDay(end, 1)), sdf.format(start)));
			hm.put(loop, rd);

			start = endd;

			loop += 1;
		}
		return hm;
	}
	
	public static String generateRepayId(String loanId, int index) {
		String gid = DateUtil.DateToString(new Date(), DateStyle.YYYYMMDD);
		return gid + loanId + String.format("%04d", index);
	}

	
}
