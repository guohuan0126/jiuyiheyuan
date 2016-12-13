package com.duanrong.dataAnalysis.util;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
/**
 * @Description: 生成token等
 * @Author: 林志明
 * @CreateDate: Nov 6, 2014
 */
public class SecurityUtil {

	/**
	 * 生成TOKEN
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String createToken(String key, String value) {
		StringBuilder builder = new StringBuilder();
		builder.append(key);
		builder.append(value);
		builder.append(UUID.randomUUID());
		return DigestUtils.md5Hex(builder.toString());
	}

}
