package com.duanrong.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * properties 文件加载器
 * @author xiao
 *
 */
public class PropReader {
	
	private Properties p;
	
    //加载文件
    public PropReader(String file){             
        Properties properties = new Properties();
        InputStream in;
        try {
            in = getClass().getResourceAsStream(file);
            properties.load(in);
            in.close();           
        } catch (IOException e) {
            e.printStackTrace();
        }
        p = properties;
    }
    
    public String readProperty(String key){
    	return p.getProperty(key);
    }
    
}