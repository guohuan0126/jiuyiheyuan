package com.duanrong.cps.util;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * Http请求工具类
 * @author Qfh
 *
 */
public class HttpUtil {
	
	static Log log = LogFactory.getLog(HttpUtil.class);
	
	public static JSONObject executePost(JSONObject json,String httpUrl){
		
		String resultStr = null; //请求返回的字符串
		try{
			//参数
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			String fieldName = null;
			String fieldNameUpper = null;
			Method getMethod = null;
			String value = null;
			Set<String> keys = json.keySet();
			Iterator<String> it = keys.iterator(); 
			while(it.hasNext()){
				fieldName = it.next();
				value = json.getString(fieldName);
				if(value != null) {
					params.add(new BasicNameValuePair(fieldName, value)); 
				}
			}
			HttpPost httppost = new HttpPost(httpUrl);
			
			// 设置参数的编码UTF-8
			httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
			HttpClient httpclient = HttpClients.createDefault();
			HttpResponse response = httpclient.execute(httppost);
			
			StatusLine statusLine=response.getStatusLine();
			Integer code=statusLine.getStatusCode();
			if(code==302){
				String redirectUrl="";
				Header[] message=response.getAllHeaders();
				for(int i=0; i<message.length; i++){
					if("Location".equals(message[i].getName())){
						redirectUrl=message[i].getValue();
						break;
					}
				}
				
				JSONObject obj=new JSONObject();
				obj.put("redirectUrl", redirectUrl);
				obj.put("code", 302);
				System.out.println(httpUrl+"请求返回值："+obj.toString());
				return obj;
			}
			HttpEntity entity= response.getEntity();
			if(entity != null) {
				resultStr=EntityUtils.toString(entity);
			}
			httpclient.getConnectionManager().shutdown();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		JSONObject result = null;  //返回值转换
		if(resultStr != null){
			try{
				System.out.println(httpUrl+"请求返回值："+resultStr);
				result = JSONObject.parseObject(resultStr);
			}catch(Exception e){
				result = new JSONObject();
				result.put("errcode","404");
				result.put("httpUrl",httpUrl);
				result.put("errorInfo",resultStr);
			}
		}else{
			result.put("error",httpUrl+"请求返回null值！");
		}
		return result;
	}
	
	
	
	
	public static JSONObject platformExecutePost(JSONObject json,String httpUrl){
		
		String resultStr = null; //请求返回的字符串
		JSONObject result = new JSONObject();  //返回值转换
		try{
			//参数
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			String fieldName = null;
			String fieldNameUpper = null;
			Method getMethod = null;
			String value = null;
			Set<String> keys = json.keySet();
			Iterator<String> it = keys.iterator(); 
			while(it.hasNext()){
				fieldName = it.next();
				value = json.getString(fieldName);
				if(value != null) {
					params.add(new BasicNameValuePair(fieldName, value)); 
				}
			}
			HttpPost httppost = new HttpPost(httpUrl);
			
			// 设置参数的编码UTF-8
			httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
			HttpClient httpclient = HttpClients.createDefault();
			HttpResponse response = httpclient.execute(httppost);
			
			StatusLine statusLine=response.getStatusLine();
			Integer code=statusLine.getStatusCode();
			result.put("code", code);
			
			HttpEntity entity= response.getEntity();
			if(entity != null) {
				resultStr=EntityUtils.toString(entity);
				log.info(resultStr);
			}
			httpclient.getConnectionManager().shutdown();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		if(resultStr != null){
			try{
			    //System.out.println(httpUrl+"请求返回值："+resultStr);
				result.put("content", JSONObject.parse(resultStr));
			}catch(Exception e){
				result.put("errcode","404");
				result.put("httpUrl",httpUrl);
				result.put("errorInfo",resultStr);
			}
		}else{
			result.put("error",httpUrl+"请求返回null值！");
		}
		return result;
	}
	
	
}