package com.duanrong.newadmin.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 输入验证
 * 
 * @author yinjunlu
 * 
 */
public class RegexInput {

	/**
	 * 验证email
	 * 
	 * @param email
	 * @return
	 */
	public static boolean emailFormat(String email) {
		boolean tag = true;
		final String pattern1 = "^([a-z0-9A-Z_-]+[-|\\.]?)+[a-z0-9A-Z_-]@([a-z0-9A-Z_-]+(-[a-z0-9A-Z_-]+)?\\.)+[a-zA-Z]{2,}$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}

	/**
	 * 验证数字
	 * 
	 * @param numberStr
	 * @return
	 */
	public static boolean isNumber(String numberStr) {
		boolean tag = true;
		final String number = "1234567890";
		for (int i = 0; i < numberStr.length(); i++) {
			if (number.indexOf(numberStr.charAt(i)) == -1) {
				tag = false;
				break;
			}
		}
		return tag;
	}

	/**
	 * 验证邮政编码
	 * 
	 * @param postcode
	 * @return
	 */
	public static boolean checkPostcode(String postcode) {
		if (postcode.matches("[0-9]\\d{5}(?!\\d)")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证手机
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean checkMobilePhone(String phone) {
		if (phone.matches("^[1][3,5,8]+\\d{9}")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证固话
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean checkPhone(String phone) {
		if (phone.matches("\\d{4}-\\d{8}|\\d{4}-\\d{7}|\\d{3}-\\d{8}")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 验证是否含有中文字符
	 * @param inputStr
	 * @return
	 */
	public static boolean isContainChinese(String inputStr) {
		if (StringUtils.isEmpty(inputStr)) {
			return false;
		}
		
		char[] cc = inputStr.toCharArray();
		for (char c : cc) {
			if (Character.toString(c).matches("[\\u4E00-\\u9FBF]+")) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 验证是否还有特殊符号
	 * @param inputStr
	 * @return
	 */
	public static boolean isContainPunctuation(String inputStr){
		String regEx = "[`~!@%#$^&*()=|{}':;',　\\[\\]<>/? \\.；：%……+￥（）【】‘”“'。，、？]";
		if (StringUtils.isEmpty(inputStr)) {
			return false;
		}
		
		char[] cc = inputStr.toCharArray();
		for (char c : cc) {
			if (Character.toString(c).matches(regEx)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 校验汉字
	 * @param parm
	 * @return
	 */
	public static boolean ishanzi(String parm){
		
		 String reg = "[\\u4e00-\\u9fa5]+";//表示+表示一个或多个中文，
		 boolean yz = parm.matches(reg);
			 
		return yz;
	}

	/**
	 * 校验身份证
	 * @param idCard
	 * @return
	 */
	public static boolean regexIDcard(String idCard){
		String REGEX_ID_CARD = "(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$)|(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)";
		return Pattern.matches(REGEX_ID_CARD, idCard);
	}  
}
