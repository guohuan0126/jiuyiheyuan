package com.duanrong.cps.util;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * 格式化工具类
 * @author Qfh
 *
 */
public class FormatUtil {
   
	/**
	 * 金额格式化
	 * @param money
	 * @return
	 */
	public static String formatMoney1(Double money){
		DecimalFormat moneyDF = new DecimalFormat("###,###,###,###.##");
		String moenyStr = "";
        if(money > 10000){
        	moenyStr = moneyDF.format(money/10000)+"万";
		}else{
			moenyStr = moneyDF.format(money) + "元";
		}
        return moenyStr;
	}
	
	/**
	 * 格式化日期yyyy-MM-dd HH:mm:ss
	 * @param calendar
	 * @return
	 */
	public static String formatDate1(XMLGregorianCalendar calendar){
		if(calendar == null){
			return "";
		}
		Date date = calendar.toGregorianCalendar().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	
	/**
	 * 格式化日期yyyy-MM-dd
	 * @param calendar
	 * @return
	 */
	public static String formatDate2(XMLGregorianCalendar calendar){
		if(calendar == null){
			return "";
		}
		Date date = calendar.toGregorianCalendar().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	/**
	 * 格式化日期yyyy-MM
	 * @param calendar
	 * @return
	 */
	public static String formaYM(XMLGregorianCalendar calendar){
		if(calendar == null){
			return "";
		}
		Date date = calendar.toGregorianCalendar().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		return sdf.format(date);
	}
	
	/**
	 * 隐藏身份证号中间8位
	 * @param idCard
	 * @return
	 */
	public static String formatIdCard(String idCard){
		if(idCard != null && idCard.length() >= 18){
			return idCard.substring(0,6)+"********"+idCard.substring(14);
		}else{
			return idCard;
		}
		
	}
	
	/**
	 * 隐藏手机号中间8位
	 * @param idCard
	 * @return
	 */
	public static String formatPhone(String phone){
		if(phone != null && phone.length() >= 11){
			return phone.substring(0,3)+"****"+phone.substring(7);
		}else{
			return phone;
		}
		
	}
	
	/**
	 * 格式化姓名 x先生
	 * @param name
	 * @return
	 */
	public static String formatName(String name){
		if(name != null && name.length() >= 1){
			return name.substring(0,1)+"先生";
		}else{
			return name;
		}
	
	}
	
}
