package com.duanrong.newadmin.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ReadProperties {
	
	
 public static String getPropetiesValue(String catalog, String proKey){
	 
	    InputStream in = null;
		Properties p = null;
		ClassLoader cl = null;
		String value = null;
		try {
			//获取配置文件读取器对象
			cl = ReadProperties.class.getClassLoader();
			//告诉读取器要读取的配置文件路径
			in = cl.getResourceAsStream(catalog);
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
