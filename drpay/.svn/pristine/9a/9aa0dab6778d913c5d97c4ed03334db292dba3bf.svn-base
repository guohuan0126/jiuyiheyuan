package com.duanrong.drpay.jsonservice.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.util.encoders.Base64;

import com.duanrong.util.security.Hmac;

/**
 * 签名工具类
 * 
 * @author xiao
 * @datetime 2016/10/19 19:00:00
 */
public class Sign {

	static Log log = LogFactory.getLog(Sign.class);

	private static final String ENCODING = "UTF-8";

	private static final byte[] lock = new byte[0];

	/**
	 * 生成签名
	 * 
	 * @param data
	 * @param privateKey
	 * @return
	 */
	public static String sign(String data, String key) {
		synchronized (lock) {
			try {
				return new String(Base64.encode(Hmac.hmacSHA256(
						data.getBytes(ENCODING), key.getBytes(ENCODING))), ENCODING);
			} catch (Exception e) {
				log.error("签名错误", e);
				return "";
			}
		}
	}

}
