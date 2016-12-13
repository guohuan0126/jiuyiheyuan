package com.duanrong.cps.util;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/***
 * 字符串工具栏
 * @author Administrator
 *
 */
public class StringUtil {
	
	/**
	 * 判断arg是否是数字、字母或者数字+字母
	 * @param arg
	 * @return
	 */
public static boolean isNumOrStr(String arg){
		
		String patten = "([A-Za-z]*[0-9]*[A-Za-z0-9]*)[^a-zA-Z0-9]*([A-Za-z0-9]$)";	
		if(Pattern.matches(patten, arg))return true;
		
		return false;
}
/**
 * 日期比较是否相等
 * @param arg
 * @return
 */
public static boolean comparString(Date date,String arg){
	String str=DateUtil.DateToString(date, "yyyy年MM月");
	if(str.equals(arg)) {
		return true;
	}else{
		return false;
	}
}
/**
 * 只允许字母和数字        
 * 清除掉所有特殊字符   
 * @param str
 * @return
 */
public  static  String StringFilter(String   str) {      
	if (str == null || str.trim().length() <=0 ) {
		return str;
	}
	String regEx="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";   
	Pattern   p   =   Pattern.compile(regEx);      
	Matcher   m   =   p.matcher(str);   
	
	return   m.replaceAll("").trim();      
}

public static boolean Postcode(String postcode){
String regExp = "^[1-9][0-9]{5}$";
	
Pattern p = Pattern.compile(regExp);
Matcher m = p.matcher(postcode);
return m.find();//boolean

}
}
