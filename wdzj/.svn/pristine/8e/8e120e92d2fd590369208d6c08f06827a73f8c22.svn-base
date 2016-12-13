package com.duanrong.util.p2pEyeUtil;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Http请求工具类
 * @author Qfh
 *
 */
public class P2PEyeHttpUtil {

	private static String appid = "";  //商户号
	private static String p2peyeURL = ""; //天眼测试服务器IP
	private static String ip = ""; //当前服务器IP地址
	private static String key = "";
	
    static{
    	appid = LoadConstantProterties2.getValueByDefaultPro("p2peye_appid");
    	p2peyeURL = LoadConstantProterties2.getValueByDefaultPro("p2peye_url");
    	key = LoadConstantProterties2.getValueByDefaultPro("p2peye_key");
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
	public static int sendPost(JSONObject param,String mod){
		JSONObject json = new JSONObject();
		param.put("send_time",System.currentTimeMillis()/1000);
		System.out.println("推送前数据："+param);
		String data = paramFormat(param);
		System.out.println("拼接好的参数："+data);
		String signKey = "";
		try {
			signKey = MD5.getMd5(data.getBytes("UTF-8")); //MD5
			data = URLEncoder.encode(TripleDesUtil.encrypt(data,key), "UTF-8");
			System.out.println("线上加密："+data);
      		System.out.println("线上解密："+TripleDesUtil.decrypt(URLDecoder.decode(data,"UTF-8"),key));
		} catch (Exception e) {
			e.printStackTrace();
		}
		json.put("data",data);
		json.put("signkey",signKey);
		json.put("appid",appid);
		System.out.println(json.toJSONString());
		String httpUrl = p2peyeURL+mod;
		
		try {
			long time = System.currentTimeMillis();
			JSONObject object = executePost(json,httpUrl);
			time = System.currentTimeMillis() - time;
			System.out.println(httpUrl+"请求耗时"+time+"毫秒"+",请求IP:"+ip);
			System.out.println("返回值："+object);
			return object.getIntValue("code");

		} catch (Exception e) {
			e.printStackTrace();
			return 500;
		}
	}
	
	/**
	 * 拼接参数
	 * @param json
	 * @return
	 */
	public static String paramFormat(JSONObject json){
		
	    String paramStr = "";
	    //1.拼装参数
		String fieldName = null;
		String value = null;
		Set<String> keys = json.keySet();
		Iterator<String> it = keys.iterator(); 
		while(it.hasNext()){
			fieldName = it.next();
			value = json.getString(fieldName);
			if(value != null) {
				paramStr += fieldName+"="+value;
				if(it.hasNext()){
					paramStr += "&";
				}
			}
		}
		paramStr = MD5.paramSort(paramStr); //排序
        try {
        	paramStr = new String(paramStr.getBytes("UTF-8"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paramStr;
		
	}
	

	private static JSONObject executePost(JSONObject json,String httpUrl){
		
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
			httppost.addHeader("Content-Type","text/html;charset=UTF-8");
			HttpClient httpclient = HttpClients.createDefault();
			HttpResponse response = httpclient.execute(httppost);
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
