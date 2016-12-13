package com.duanrong.newadmin.utility;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.DigestException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
/*import org.apache.http.client.HttpClient;*/
import org.apache.commons.httpclient.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.commons.httpclient.methods.PostMethod; 
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.Header;

/**
 * Http请求工具类
 * @author Qfh
 *
 */
public class HttpUtil {

	private static String udeskhost = "";  //主动域名
	private static String yeepayhost = "";  //主动域名
	private static String webkey = "";  //Webkey
	private static String ip = ""; //当前服务器IP地址
	
    static{
    	udeskhost = ReadProperties.getPropetiesValue("constant/UdeskPlatform.properties", "udeskhost");		
    	yeepayhost = LoadConstantProterties2.getValueByDefaultPro("yeepayhost");
    	webkey = ReadProperties.getPropetiesValue("constant/UdeskPlatform.properties", "udesk_key");		
    	try {
			ip = InetAddress.getLocalHost().getHostAddress();
		}catch(UnknownHostException e) {
			ip = "无法获得IP";
		}
    }
	
	
	/**
	 * 发送post请求
	 * @param obj
	 * @param httpUrl
	 * @return
	 * @throws Exception
	 */
	public static JSONObject sendPost(JSONObject json,String url ){
		String sign=MD5Encry.Encry(webkey);
		json.put("sign",sign);			
		String httpUrl="";
		httpUrl = udeskhost+url;
		System.out.println(httpUrl+"请求参数："+json.toJSONString());
		JSONObject object = null;
		try {
			long time = System.currentTimeMillis();
			object = executePost(json,httpUrl);
			time = System.currentTimeMillis() - time;
			if(object == null){
				object = JSONObject.parseObject("{\"error\":\"null\",\"ip\":\""+ip+"\",\"url\":\""+url+"\"}");
				System.out.println("调用"+url+"异常！返回null,请检查soa服务器!!!");
			}else{
				System.out.println(url+"请求耗时"+time+"毫秒"+",请求IP:"+ip);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	

public static JSONObject executePost(JSONObject json,String httpUrl){
		
		String resultStr = null; //请求返回的字符串
		try{
			
			HttpClient httpclient = new HttpClient();			
			PostMethod  httppost = new PostMethod (httpUrl);
			Header header=new Header();
			header.setName("content-type");
			header.setValue("application/json");
			httppost.addRequestHeader(header);
			//InputStream in=new ByteArrayInputStream(json.getBytes("UTF-8"));
		    String params=json.toString();
			ByteArrayInputStream  in=new ByteArrayInputStream(params.getBytes("UTF-8"));
			httppost.setRequestBody(in);
			httpclient.executeMethod(httppost);
			byte[] result =httppost.getResponseBody();
			resultStr = new String(result, "UTF-8");		
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

	public static JSONObject sendGet(JSONObject json,String url ){	
		String sign=MD5Encry.Encry(webkey);
		json.put("sign",sign);		
		String httpUrl="";
		httpUrl = udeskhost+url;
		System.out.println(httpUrl+"请求参数："+json.toJSONString());
		JSONObject object = null;
		try {
			long time = System.currentTimeMillis();
			object = executeGet(json,httpUrl);
			time = System.currentTimeMillis() - time;
			if(object == null){
				object = JSONObject.parseObject("{\"error\":\"null\",\"ip\":\""+ip+"\",\"url\":\""+url+"\"}");
				System.out.println("调用"+url+"异常！返回null,请检查soa服务器!!!");
			}else{
				System.out.println(url+"请求耗时"+time+"毫秒"+",请求IP:"+ip);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

  private static JSONObject executeGet(JSONObject json,String httpUrl){
		
		String resultStr = null; //请求返回的字符串
		String requestParamStr="";
		try{
			for(String key:json.keySet()){
				String value=json.getString(key);
				requestParamStr=requestParamStr+key+"="+value+"&";
			}
			requestParamStr=requestParamStr.substring(0, requestParamStr.lastIndexOf("&"));
			HttpGet Httpget = new HttpGet(httpUrl+"?"+requestParamStr);
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpResponse response=new DefaultHttpClient().execute(Httpget);
			if(response.getStatusLine().getStatusCode()==200)
			{
			HttpEntity entity=response.getEntity(); 
			// 设置参数的编码UTF-8
			if(entity != null) {
				resultStr=EntityUtils.toString(entity, HTTP.UTF_8);
			}
			httpclient.getConnectionManager().shutdown();			
			}						
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
	
}