package com.duanrong.cps.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;


public class ReadProperties {
	
	
 public static String getPropetiesValue(String catalog, String proKey){
	 
	    InputStreamReader in = null;
		Properties p = null;
		ClassLoader cl = null;
		String value = null;
		try {
			//获取配置文件读取器对象
			cl = ReadProperties.class.getClassLoader();
			//告诉读取器要读取的配置文件路径
			in = new InputStreamReader(cl.getResourceAsStream(catalog),"utf-8");
			p = new Properties();
			p.load(in);
			
			if(proKey == null){
				return "";
			}else{
				boolean containsKey = p.containsKey(proKey);
				if(containsKey){
					value = p.getProperty(proKey);
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
