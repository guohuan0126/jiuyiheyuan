package com.duanrong.drpay.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

public class IdUtil {
	
	private static int increament = 0;
	private static int increament2 = 0;
	
	public static String generateId(ToType toType) {
		
		//服务器名称，从配置文件里面读取,长度4位
		//操作类型。长度4位。定义一个枚举类型。
		//yyyyMMddHHmmssSSS
		//4位随机数字
		String random = RandomStringUtils.random(4, false, true);
		
		//4位累加数据，从0开始，最大9999.
		String gid = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());
		increament++;
		if (increament >= 999999) {
			increament = 0;
		}
		gid += String.format("%06d", increament);
		
		String id = ConfigConstant.SERVERS+toType+random+gid;
		return id;
	}
	
	public static String generateId(String paymentID) {
		//服务器名称，从配置文件里面读取。web1。长度4位
		//操作类型。长度4位。定义一个枚举类型。
		//yyyyMMddHHmmssSSS
		//4位随机数字
		String random = RandomStringUtils.random(4, false, true);
		
		//4位累加数据，从0开始，最大9999.
		increament2++;
		if (increament2 >= 999999) {
			increament2 = 0;
		}
		String s = ""+new Date().getTime();
		s = s.substring(10);
		String id = ConfigConstant.SERVERS+paymentID+s+random+String.format("%06d", increament);
		return id;
	}
	
	/**
	 * @author yinxunzhi
	 * @return
	 */
	public static String randomUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}

}
