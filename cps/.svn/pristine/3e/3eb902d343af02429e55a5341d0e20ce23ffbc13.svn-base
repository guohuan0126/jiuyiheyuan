package com.duanrong.cps.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.fastjson.JSONObject;

public class ThiredPlatformHttpUtil {
	
	private static String ip = ""; //当前服务器IP地址
    static{
    	try {
			ip = InetAddress.getLocalHost().getHostAddress();
		}catch(UnknownHostException e) {
			ip = "无法获得IP";
		}
    }
	
	
	public static JSONObject executePost(JSONObject json,String httpUrl){
		
		String resultStr = null; //请求返回的字符串
		try{
			HttpClient httpclient = new HttpClient();
			PostMethod  httppost = new PostMethod (httpUrl);
			
			Header header=new Header();
			header.setName("content-type");
			header.setValue("application/json");
			httppost.setRequestHeader(header);
			
			//InputStream in=new ByteArrayInputStream(json.getBytes("UTF-8"));
		    String params=json.toString();
			ByteArrayInputStream  in=new ByteArrayInputStream(params.getBytes("UTF-8"));
			httppost.setRequestBody(in);
			httpclient.executeMethod(httppost);
			resultStr=ThiredPlatformHttpUtil.inputStreamConver(httppost.getResponseBodyAsStream());
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
				result.put("serverIp",ip);
				result.put("httpUrl",httpUrl);
				result.put("errorInfo",resultStr);
			}
		}else{
			result.put("error",httpUrl+"请求返回null值！");
			result.put("serverIp",ip);
		}
		return result;
	}
	
	
 
	public static JSONObject executePostEntity(JSONObject json,String httpUrl){
		
		String resultStr = null; //请求返回的字符串
		try{
			HttpClient httpclient = new HttpClient();
			PostMethod  httppost = new PostMethod (httpUrl);
			httppost.setRequestHeader("content-type", "application/json");
			HttpMethodParams par=new HttpMethodParams();	
			for(String key :json.keySet()){
				par.setParameter(key, json.getString(key));
			}
			httppost.setParams(par);	
			httpclient.executeMethod(httppost);
			resultStr=ThiredPlatformHttpUtil.inputStreamConver(httppost.getResponseBodyAsStream());
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
				result.put("serverIp",ip);
				result.put("httpUrl",httpUrl);
				result.put("errorInfo",resultStr);
			}
		}else{
			result.put("error",httpUrl+"请求返回null值！");
			result.put("serverIp",ip);
		}
		return result;
	}
	
	
  
  
  private static String inputStreamConver(InputStream is) {
		StringBuilder sb = new StringBuilder();
		String line;
		BufferedReader bf;
		if(is != null ){
			try {
				bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				while ((line = bf.readLine()) != null) {
					sb.append(line); 
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return sb.toString();
		}
		return "";
	}
	
	
}
