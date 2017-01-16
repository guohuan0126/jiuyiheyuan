package com.duanrong.drpay.jsonservice.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description: 格式校验
 * @Author: 林志明
 * @CreateDate: Nov 19, 2014
 */
public class RegexInput {

	/**
	 * 验证手机
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean checkMobilePhone(String phone) {
		if (StringUtils.isBlank(phone)) {
			return false;
		}
		if (phone.matches("^[1][3,4,5,7,8]+\\d{9}")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证email
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		if (StringUtils.isBlank(email)) {
			return false;
		}
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
	 * 校验密码
	 * 
	 * @param password
	 * @return
	 */
	public static boolean checkPassword(String password) {
		if (StringUtils.isBlank(password)) {
			return false;
		}
		boolean tag = true;
		final String pattern1 = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(password);
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
		final String number = "123456789";
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
	 * 验证用户名
	 * 
	 * @param username
	 * @return
	 */
	public static boolean checkUsername(String username) {
		if (username.matches("/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$")) {
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
	 * 
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
	 * 
	 * @param inputStr
	 * @return
	 */
	public static boolean isContainPunctuation(String inputStr) {
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
	 * 判断银行卡号是否合法，16位，18位或19位
	 * @param cardId
	 * @return
	 */
	public static boolean checkBankCard(String cardId){
		String pattern = "^(\\d{16}|\\d{17}|\\d{18}|\\d{19})$";
		if (cardId.matches(pattern)) {
			return true;
		} else {
			return false;
		}
	}

}
