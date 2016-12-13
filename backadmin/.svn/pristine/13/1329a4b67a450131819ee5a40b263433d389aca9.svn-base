package com.duanrong.newadmin.utility;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import util.MyBeanUtils;
import util.MyStringUtils;


public class FormUtil {
	
	

	/**
	 * 查询参数封装
	 * @param obj
	 * @return str
	 */
	public static String getForParam(Object obj){
		StringBuffer sb = new StringBuffer();
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for(int i = 0; i < fields.length; i++){
           try {
        	   if(!fields[i].getName().equals("serialVersionUID")){
	               PropertyDescriptor pd = new PropertyDescriptor(fields[i].getName(), clazz); 
	               Object object = pd.getReadMethod().invoke(obj);
	               if((object instanceof String && MyStringUtils.isNotAnyBlank()) || object != null){
	            	   sb.append("&"+fields[i].getName()+"="+pd.getReadMethod().invoke(obj));
	               }       
        	   }
            } catch (Exception e) {
                  e.printStackTrace();
            }
         }
		return sb.toString().trim();
	}
	
	/**
	 * 查询参数封装
	 * @param obj
	 * @return bean
	 */
	public static Object getForParamToBean(Object obj){		
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for(int i = 0; i < fields.length; i++){
           try {
        	   if(!fields[i].getName().equals("serialVersionUID") && fields[i].getType() == String.class){
	               PropertyDescriptor pd = new PropertyDescriptor(fields[i].getName(), clazz);   
	               String param = (String)pd.getReadMethod().invoke(obj);
	               if(MyStringUtils.isNotAnyBlank(param)){
	            	   Object o = null;
	            	   if(EncodingUtil.getEncoding(param).equals("ISO-8859-1")){
	            		   o =  new String(param.getBytes("ISO-8859-1"), "UTF-8");
	            	   }else{
	            		   o = URLDecoder.decode(param, "UTF-8");
	            	   }
	            	  pd.getWriteMethod().invoke(obj, new Object[]{o});
	               }       
        	   }
            } catch (Exception e) {
                  e.printStackTrace();
            }
         }
		return obj;
	}
	
	
	
	/**
	 * 查询参数封装 
	 * @param map
	 * @return str
	 */
	public static String getForParam(Map<String, Object> map){
		StringBuffer sb = new StringBuffer();
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			if(MyStringUtils.isNotAnyBlank(entry.getKey()) && MyBeanUtils.nonNull(entry.getValue())){
				sb.append("&"+entry.getKey()+"="+entry.getValue());			
			}
		}
		return sb.toString().trim();
	}
	
	/**
	 * 查询参数封装 
	 * @param map
	 * @return map
	 * @throws UnsupportedEncodingException 
	 */
	public static Map<String, Object> getForParamToBean(Map<String, Object> map) throws UnsupportedEncodingException{
		Map<String, Object> obj = new HashMap<>();
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			if(entry.getKey() != null && !("").equals(entry.getKey()) && entry.getValue() != null && !("").equals(entry.getValue())){
				if(entry.getValue() instanceof String){
					 if(EncodingUtil.getEncoding(entry.getValue().toString()).equals("ISO-8859-1")){
						 obj.put(entry.getKey(), new String(((String)entry.getValue()).getBytes("ISO-8859-1"), "UTF-8"));	
	            	   }else{
	            		 obj.put(entry.getKey(), URLDecoder.decode((String)entry.getValue(), "UTF-8"));	
	            	   }
					
				}
			}else{
				obj.put(entry.getKey(), entry.getValue());
			}
		}
		return obj;
	}

}
