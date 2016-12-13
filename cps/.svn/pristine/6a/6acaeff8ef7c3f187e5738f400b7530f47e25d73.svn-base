package com.duanrong.cps.util;


import org.apache.commons.lang3.StringUtils;


/**
 * @Description: 字符串工具类
 * @Author: 林志明
 * @CreateDate: Nov 19, 2014
 */
public class CharSequenceUtil {

	

	/**
	 * 生成指定数量的随机字母组合
	 * 
	 * @param count
	 *            字母组合长度
	 * @return
	 */
	public static String multipleLetter(int count) {
		String str = "abcdefghijklmnopqrstuvwxyz";
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < count; i++) {
			stringBuffer.append(str.charAt((int) (Math.random() * 26)));
		}
		return stringBuffer.toString();
	}

	
}
