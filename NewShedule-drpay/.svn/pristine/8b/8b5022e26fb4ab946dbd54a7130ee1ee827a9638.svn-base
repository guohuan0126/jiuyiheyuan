package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description: 日期工具类
 * @Author: 林志明
 * @CreateDate: Nov 21, 2014
 */
public class DateUtilPlus {

	public static Long getTimeInMillis(final Date date1, final Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}

		final Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		final Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		long timeInMillis1 = cal1.getTimeInMillis();
		long timeInMillis2 = cal2.getTimeInMillis();
		long ms;
		ms = timeInMillis1 - timeInMillis2;
		return ms;
	}

	/**
	 * 为指定日期增加指定天数
	 * 
	 * @param date
	 * @param amount
	 */
	public static Date DateAdd4DayOfYear(final Date date, final int amount) {
		if (date == null) return null;
		if(amount == 0) return date;
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, amount);
		return cal.getTime();
	}

	public static int getDay(final Date date1, final Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}

		final Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		final Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		return calcDay(cal1, cal2);
	}

	/**
	 * 格式化为本地【日期时间】格式
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTimeLoaclString(Date date) {
		if (date == null) {
			return null;
		}
		DateFormat dtf = DateFormat.getDateTimeInstance();
		return dtf.format(date);
	}

	/**
	 * 格式化为本地【日期】格式
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateLoaclString(Date date) {
		if (date == null) {
			return null;
		}
		DateFormat dtf = DateFormat.getDateInstance();
		return dtf.format(date);
	}

	public static int calcDay(final Calendar cal1, final Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}

		long timeInMillis1 = cal1.getTimeInMillis();
		long timeInMillis2 = cal2.getTimeInMillis();
		long ms;
		if (timeInMillis1 > timeInMillis2) {
			ms = timeInMillis1 - timeInMillis2;
		} else if (timeInMillis1 < timeInMillis2) {
			ms = timeInMillis2 - timeInMillis1;
		} else {
			return 0;
		}

		Double s = (double) (ms / (1000 * 60 * 60 * 24));
		int day = s.intValue();
		if (day == 0) {
			if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
				if (cal1.get(Calendar.DAY_OF_YEAR)
						- cal2.get(Calendar.DAY_OF_YEAR) == 1) {
					return 1;
				}
			} else {
				return 1;
			}
		} else if (day == 1) {
			if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
				if (cal1.get(Calendar.DAY_OF_YEAR)
						- cal2.get(Calendar.DAY_OF_YEAR) == 2) {
					return 2;
				}
			} else {
				return 2;
			}
		}

		return day;
	}
	/**
	 * 格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date) {
		if (date == null) {
			return null;
		}
		DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dtf.format(date);
	}
}