package com.duanrong.payment.jd.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

import com.duanrong.util.LoadConstantProterties2;

/**
 * 支付机构流水号生成规则类
 * @author Administrator
 *
 */
public class PaymentIDGenerator {
	private static int increament = 0;

	public static String generateId(String paymentID) {
		//服务器名称，从配置文件里面读取。web1。长度4位
		String serviceName = LoadConstantProterties2.getValueByDefaultPro(null);
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
		
		String id = serviceName+paymentID+random+gid;
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
