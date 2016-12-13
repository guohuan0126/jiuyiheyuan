package com.duanrong.newadmin.utility;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.duanrong.business.loan.model.RepayDate;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-8-27 下午12:29:57
 * @Description : Utils com.duanrong.util ArithUtil.java 工具类
 */
public class ArithUtil {
	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	public ArithUtil() {

	}

	/**
	 * 
	 * @description 提供精确的加法运算
	 * @author 孙铮
	 * @time 2014-8-27 下午12:31:43
	 * @param 被加数
	 * @param 加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		double doubleValue = b1.add(b2).doubleValue();
		return ArithUtil.round(doubleValue, 2);
	}

	/**
	 * 
	 * @description 提供精确的减法运算
	 * @author 孙铮
	 * @time 2014-8-27 下午12:32:36
	 * @param 被减数
	 * @param 减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 
	 * @description 提供精确的乘法运算
	 * @author 孙铮
	 * @time 2014-8-27 下午12:33:13
	 * @param 被乘数
	 * @param 乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 
	 * @description 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入
	 * @author 孙铮
	 * @time 2014-8-27 下午1:54:50
	 * @param 被除数
	 * @param 除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 
	 * @description 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入
	 * @author 孙铮
	 * @time 2014-8-27 下午1:55:18
	 * @param 被除数
	 * @param 除数
	 * @param 表示表示需要精确到小数点以后几位
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("参数scale必须为整数为零!");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 
	 * @description 提供精确的小数位四舍五入处理
	 * @author 孙铮
	 * @time 2014-8-27 下午1:55:55
	 * @param 需要四舍五入的数字
	 * @param 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("参数scale必须为整数或零!");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 
	 * @description 提供精确的类型转换(Float)
	 * @author 孙铮
	 * @time 2014-8-27 下午1:56:20
	 * @param 需要被转换的数字
	 * @return 返回转换结果
	 */
	public static float convertsToFloat(double v) {
		BigDecimal b = new BigDecimal(v);
		return b.floatValue();
	}

	/**
	 * 
	 * @description 提供精确的类型转换(Int)不进行四舍五入
	 * @author 孙铮
	 * @time 2014-8-27 下午1:56:45
	 * @param 需要被转换的数字
	 * @return 返回转换结果
	 */
	public static int convertsToInt(double v) {
		BigDecimal b = new BigDecimal(v);
		return b.intValue();
	}

	/**
	 * 
	 * @description 提供精确的类型转换(Long)
	 * @author 孙铮
	 * @time 2014-8-27 下午1:57:04
	 * @param 需要被转换的数字
	 * @return 返回转换结果
	 */
	public static long convertsToLong(double v) {
		BigDecimal b = new BigDecimal(v);
		return b.longValue();
	}

	/**
	 * 
	 * @description 返回两个数中大的一个值
	 * @author 孙铮
	 * @time 2014-8-27 下午1:57:31
	 * @param 需要被对比的第一个数
	 * @param 需要被对比的第二个数
	 * @return 返回两个数中大的一个值
	 */
	public static double returnMax(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.max(b2).doubleValue();
	}

	/**
	 * 
	 * @description 返回两个数中小的一个值
	 * @author 孙铮
	 * @time 2014-8-27 下午1:57:58
	 * @param 需要被对比的第一个数
	 * @param 需要被对比的第二个数
	 * @return 返回两个数中小的一个值
	 */
	public static double returnMin(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.min(b2).doubleValue();
	}

	/**
	 * 
	 * @description 精确比较两个数字
	 * @author 孙铮
	 * @time 2014-8-27 下午1:58:23
	 * @param 需要被对比的第一个数
	 * @param 需要被对比的第二个数
	 * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
	 */
	public static int compareTo(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.compareTo(b2);
	}

	/**
	 * 
	 * @description 获取数字小数位数
	 * @author 孙铮
	 * @time 2014-8-27 下午1:58:52
	 * @param number
	 *            数字.
	 * @return 小数位数
	 */
	public static int getDecimals(double number) {
		DecimalFormat decimalFormat = new DecimalFormat("#.####");
		String numberString = decimalFormat.format(number);
		if (numberString.indexOf(".") > 0) {
			return numberString.length() - String.valueOf(number).indexOf(".")
					- 1;
		} else {
			return 0;
		}
	}

	/**
	 * 
	 * @description 获取数字小数位数
	 * @author 孙铮
	 * @time 2014-8-27 下午1:59:19
	 * @param number
	 *            数字.
	 * @return 小数位数
	 */
	public static int getDecimals(float number) {
		DecimalFormat decimalFormat = new DecimalFormat("#.####");
		String numberString = decimalFormat.format(number);
		if (numberString.indexOf(".") > 0) {
			return numberString.length() - String.valueOf(number).indexOf(".")
					- 1;
		} else {
			return 0;
		}
	}

	/**
	 * 
	 * @description 对double数据进行取精度
	 * @author 孙铮
	 * @time 2014-8-27 下午1:59:43
	 * @param value
	 *            double数据.
	 * @param scale
	 *            精度位数(保留的小数位数)
	 * @param roundingMode
	 *            精度取值方式
	 * @return 精度计算后的数据
	 */
	public static double round(double value, int scale, int roundingMode) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale, roundingMode);
		double d = bd.doubleValue();
		bd = null;
		return d;
	}

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

}
