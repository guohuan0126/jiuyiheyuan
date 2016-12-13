package com.duanrong.newadmin.controllhelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.loan.model.RepayDate;
import com.duanrong.newadmin.utility.ArithUtil;
import com.duanrong.newadmin.utility.DateStyle;
import com.duanrong.newadmin.utility.DateUtil;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-11-24 上午11:33:34
 * @Description : drpc com.duanrong.pcweb.controllhelper RepayHelper.java
 * 
 */
public class RepayHelper {
	/**
	 * 
	 * @description 获得按天计算情况下先息后本还款某一期应得的利息
	 * @author 孙铮
	 * @time 2014-11-24 上午11:34:16
	 * @param money
	 * @param rate
	 * @param period
	 * @return
	 */
	public static double getRFCLInterestByPeriodDay(double money, double rate,
			int period) {
		double interest = ArithUtil.round(money * rate / 365 * period, 2);
		return interest;
	}

	/**
	 * 
	 * @description 获取按天计息请款下:一次性到期还本付息的利息计算
	 * @author 孙铮
	 * @time 2014-11-24 上午11:45:06
	 * @param money
	 * @param rate
	 * @param period
	 * @return
	 */
	public static double getDQHBFXInterestByPeriodDay(double money,
			double rate, int period) {
		double interest = ArithUtil.round(money * rate / 365 * period, 2);
		return interest;
	}

	/**
	 * 
	 * @description 获得按月计算情况下:按月付息到期还本,某一期的利息计算
	 * @author 孙铮
	 * @time 2014-11-24 上午11:46:53
	 * @param money
	 * @param rate
	 * @return
	 */
	public static double getRFCLInterestByPeriodMonth(double money, double rate) {
		double interest = ArithUtil.round(money * rate / 12, 2);
		return interest;
	}

	/**
	 * 
	 * @description 获得按月计算情况下:一次性到期还本付息,某一期的利息计算
	 * @author 孙铮
	 * @time 2014-11-24 上午11:48:42
	 * @param money
	 * @param rate
	 * @param deadline
	 * @return
	 */
	public static double getDQHBFXInterestByPeriodMonth(double money,
			double rate, int deadline) {
		double interest = ArithUtil.round(money * rate / 12 * deadline, 2);
		return interest;
	}

	/**
	 * 按天计算利息情况下借款用户正常还款情况下在某一期所需支付的利息
	 * 
	 * @param loan
	 * @param period
	 * @param deadline
	 * @return
	 */
	public static double getRepayInterestByPeriodDay(List<Invest> invests,
			int period, double rate) {
		double sumMoney = 0D;
		for (Invest im : invests) {
			sumMoney = ArithUtil.add(sumMoney,
					getDQHBFXInterestByPeriodDay(im.getMoney(), rate, period));
		}
		return sumMoney;
	}

//	public static void main(String[] args) throws ParseException {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date end = DateUtil.StringToDate("2015-05-26");
//		Loop(end, 90);
//	}
	
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
