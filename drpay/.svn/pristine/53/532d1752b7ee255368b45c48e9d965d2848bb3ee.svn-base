package com.duanrong.drpay.business.user;

import com.duanrong.util.security.HexConvers;
import com.duanrong.util.security.SHA;

/**
 * 登陆密码加密
 * @author xiao
 * @datetime 2016年12月3日 下午4:38:31
 */
public class PasswordEncode {

	private static final char hex_digits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e', 'f'};

	public synchronized static String encode(String password) {
		return HexConvers.hexDigits(SHA.encodeSHA1(password.getBytes()), hex_digits);
	}
}
