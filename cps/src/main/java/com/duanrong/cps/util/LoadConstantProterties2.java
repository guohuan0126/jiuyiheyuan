package com.duanrong.cps.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadConstantProterties2 {
	/**
	 * 
	 * @description 该方法只读取TrusteeshipYeepayConstants.properties配置文件中的值
	 * @author 孙铮
	 * @time 2014-8-19 下午5:28:02
	 * @param proKey
	 *            TrusteeshipYeepayConstants.properties配置文件中的key
	 *            如果proKey为空,则使用默认key = servers
	 * @return
	 */
	public static String getValueByDefaultPro(String proKey) {

		InputStream in = null;
		Properties p = null;
		ClassLoader cl = null;
		String value = null;
		String defaultKey = "servers"; 
		try {
			//获取配置文件读取器对象
			cl = LoadConstantProterties2.class.getClassLoader();
			//告诉读取器要读取的配置文件路径
			in = cl.getResourceAsStream("/config.properties");
			p = new Properties();
			p.load(in);
			
			if(proKey == null){
				value = p.getProperty(defaultKey);
				return value;
			}else{
				defaultKey = proKey;
				boolean containsKey = p.containsKey(defaultKey);
				if(containsKey){
					value = p.getProperty(defaultKey);
					return value;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
