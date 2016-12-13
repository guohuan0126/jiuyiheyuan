package com.duanrong.newadmin.utility;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class LoadConstantProterties {
	/**
	 * @param protertieNames 要加载的配置文件名称集合,可以传null,如果传null参数,则默认去加载"constant/TrusteeshipYeepayConstants.properties"
	 * @return 返回的集合自己去遍历根据条件获取常量
	 */
	public static HashMap<String , Properties> loadConstantsPro(List<String> protertieNames){
		List<String> defaultProtertieNames = new ArrayList<String>();
		defaultProtertieNames.add("TrusteeshipYeepayConstants.properties");
		ClassLoader cl = null;
		InputStream in = null;
		Properties p = null;
		HashMap<String , Properties> propertiesList = new HashMap<String, Properties>();
		if(protertieNames != null && protertieNames.size()>0){
			for (String protertieName : protertieNames) {
				defaultProtertieNames.add(protertieName);
			}
		}
		if(defaultProtertieNames.size()>0 && defaultProtertieNames != null){
			for (String protertieName : defaultProtertieNames) {
				try {
					cl = LoadConstantProterties.class.getClassLoader();
					in = cl.getResourceAsStream("constant/"+protertieName);
					p = new Properties();
					p.load(in);
				} catch (IOException e) {
					e.printStackTrace();
				}
				propertiesList.put(protertieName, p);
			}
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return propertiesList;
	}
}
