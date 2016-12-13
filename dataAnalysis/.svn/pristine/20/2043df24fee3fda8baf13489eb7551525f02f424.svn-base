package com.duanrong.dataAnalysis.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 
 * @author 尹逊志
 * @date 2014-10-21下午4:08:56
 */
public class CookieUtil {
	public static String getCookie(String key, HttpServletRequest request,
			HttpServletResponse response) {
		if (key == null){
			return null;
		}
		Cookie[] cookies = request.getCookies();
		if(cookies == null){
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(key)) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
	/**
	 * 添加cookie
	 * @author:yinxunzhi
	 * @time:2014-10-21下午4:15:30
	 * @param key
	 * @param value
	 * @param period default three days
	 * @param response
	 */
	public static void addCookie(String key, String value, int period,
			HttpServletResponse response) {
		if (key == null || value == null) {
			return;
		}
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(period);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}
}
